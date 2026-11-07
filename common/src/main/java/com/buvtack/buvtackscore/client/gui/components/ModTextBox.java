package com.buvtack.buvtackscore.client.gui.components;

import com.buvtack.buvtackscore.Constants;
import com.buvtack.buvtackscore.client.graphics.ModColor;
import com.buvtack.buvtackscore.client.graphics.ModColors;
import com.buvtack.buvtackscore.client.gui.DefaultTaskPriorities;
import com.buvtack.buvtackscore.client.gui.ModTask;
import com.buvtack.buvtackscore.client.gui.ModTickAction;
import com.buvtack.buvtackscore.client.gui.builders.ModPanelBuilder;
import com.buvtack.buvtackscore.client.util.ClientModHelper;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.HashMap;
import java.util.Map;

public class ModTextBox extends DimensionalModWidget {

    private String text;
    private boolean clickable;
    private ModPanel caret;
    private int caretIndex;
    private int selectionAnchor;
    private ModColor textColor;

    private Map<Integer, ModTask<ModTickAction>> keyboardInputTasks;

    public ModTextBox(int x, int y, int width, int height) {
        super(x, y, width, height);
        keyboardInputTasks = new HashMap<>();
        tickTasks.addTask("focusUnfocus", new ModTask<>(this::focusUnfocus));
        text = "";
        textColor = ModColors.WHITE;
        ModPanelBuilder caretBuilder = new ModPanelBuilder();
        caretBuilder.bounds(x + 5, centerY() - ClientModHelper.font().lineHeight / 2 - 1, 1, ClientModHelper.font().lineHeight + 2);
        caret = caretBuilder.build();
        caretIndex = 0;
        selectionAnchor = 0;

        renderTasks.addTask("renderText", new ModTask<>(DefaultTaskPriorities.CONTENT, (graphics, mouseX, mouseY, partialTick) -> {
            int y0 = centerY() - ClientModHelper.font().lineHeight / 2;
            int y1 = centerY() + ClientModHelper.font().lineHeight / 2;
            graphics.enableScissor(x + 5, y0, contentRight() - 5, y1);
            graphics.text(ClientModHelper.font(), text, x + 5, centerY() - ClientModHelper.font().lineHeight / 2, textColor.color);
            graphics.disableScissor();
        }));

        renderTasks.addTask("renderSelection", new ModTask<>(DefaultTaskPriorities.CONTENT, (graphics, mouseX, mouseY, partialTick) -> {
            if (hasSelectedText()) {
                int y0 = centerY() - ClientModHelper.font().lineHeight / 2;
                int y1 = centerY() + ClientModHelper.font().lineHeight / 2;

                graphics.enableScissor(x + 5, y0, contentRight() - 5, y1);
                if (selectionAnchor < caretIndex) {
                    graphics.fill(getXFromIndex(selectionAnchor), y0 - 1, getXFromIndex(caretIndex), y1 + 1, ModColors.WHITE.color);
                    String selectedSubstring = text.substring(selectionAnchor, caretIndex);
                    graphics.text(ClientModHelper.font(), selectedSubstring, getXFromIndex(selectionAnchor), y0, ModColors.BLUE.darken(0.2F).color, false);
                }
                else {
                    graphics.fill(getXFromIndex(caretIndex), y0 - 1, getXFromIndex(selectionAnchor), y1 + 1, ModColors.WHITE.color);
                    String selectedSubstring = text.substring(caretIndex, selectionAnchor);
                    graphics.text(ClientModHelper.font(), selectedSubstring, getXFromIndex(caretIndex), y0, ModColors.BLUE.darken(0.2F).color, false);
                }
                graphics.disableScissor();
            }
        }));

        renderTasks.addTask("renderCaret", new ModTask<>(DefaultTaskPriorities.CONTENT + 1, (graphics, mouseX, mouseY, partialTick) -> {
            boolean shown = (System.currentTimeMillis() / 500) % 2 == 0;
            if (focused && shown)
                graphics.fill(caret.left(), caret.top(), caret.right(), caret.bottom(), textColor.color);
        }));

        tickTasks.addTask("handleInput", new ModTask<>(this::tickTextInput));
        tickTasks.addTask("moveCaret", new ModTask<>(this::moveCaret));
        tickTasks.addTask("syncCaretXWithIndex", new ModTask<>(this::syncCaretXWithIndex));

        keyboardInputTasks.put(GLFW.GLFW_KEY_BACKSPACE, new ModTask<>(this::deleteText));
        keyboardInputTasks.put(GLFW.GLFW_KEY_DELETE, new ModTask<>(this::deleteText));
        keyboardInputTasks.put(GLFW.GLFW_KEY_LEFT, new ModTask<>(this::moveLeft));
        keyboardInputTasks.put(GLFW.GLFW_KEY_RIGHT, new ModTask<>(this::moveRight));
        keyboardInputTasks.put(GLFW.GLFW_KEY_ESCAPE, new ModTask<>(this::escape));
        keyboardInputTasks.put(GLFW.GLFW_KEY_A, new ModTask<>(this::selectAll));
        keyboardInputTasks.put(GLFW.GLFW_KEY_END, new ModTask<>(this::skipToEnd));
        keyboardInputTasks.put(GLFW.GLFW_KEY_HOME, new ModTask<>(this::skipToBeginning));
        keyboardInputTasks.put(GLFW.GLFW_KEY_C, new ModTask<>(this::copy));
        keyboardInputTasks.put(GLFW.GLFW_KEY_V, new ModTask<>(this::paste));
    }

    private void focusUnfocus() {
        if (ClientModHelper.isNotLeftClicking())
            clickable = true;

        if (pressed() && clickable) {
            focused = true;
            clickable = false;
        }
        else if (ClientModHelper.isLeftClicking() && !hovered())
            focused = false;
    }

    private void syncCaretXWithIndex() {
        if (caretIndex >= text.length()) {
            caretIndex = text.length();
            caret.x = x + 5 + ClientModHelper.font().width(text) + 1;
            return;
        }

        caret.x = x + 5 + ClientModHelper.font().width(text.substring(0, caretIndex));
    }

    private int getXFromIndex(int index) {
        if (index >= text.length()) {
            return x + 5 + ClientModHelper.font().width(text) + 1;
        }

        return x + 5 + ClientModHelper.font().width(text.substring(0, index));
    }

    private void moveCaret() {
        if (pressed() && focused) {
            Constants.LOG.info("Moving Caret");
            if (ClientModHelper.mouseX() - x - 5 >= ClientModHelper.font().width(text))
                caretIndex = text.length() + 1;
            else {
                for (int i = 1; i < text.length() + 1; i++) {
                    String substring = text.substring(0, i);
                    if (ClientModHelper.font().width(substring) > ClientModHelper.mouseX() - x - 5) {
                        caretIndex = i - 1;
                        break;
                    }
                }
            }
        }
    }

    private void tickTextInput() {
        if (!focused)
            return;

        typeChar();

        if (ClientModHelper.pressedKey()) {
            for (int key : ClientModHelper.getPressedKeys()) {
                ModTask<ModTickAction> task = keyboardInputTasks.get(key);
                if (task != null)
                    task.action.tick();
            }
        }
    }

    private void typeChar() {
        if (ClientModHelper.enteredChar()) {
            for (char ch : ClientModHelper.getEnteredChars()) {
                String enteredChar = Character.toString(ch);
                String substring = text.substring(0, caretIndex);
                String rest = text.substring(caretIndex);
                text = substring + enteredChar + rest;
                caretIndex++;
                selectionAnchor = caretIndex;
                caret.x += ClientModHelper.font().width(enteredChar);
            }
        }
    }

    private void deleteText() {
        if (!text.isEmpty() && (caretIndex > 0 || hasSelectedText())) {
            if (!hasSelectedText()) {
                caretIndex--;
                selectionAnchor = caretIndex;
                String substring = text.substring(0, caretIndex);
                String rest = text.substring(caretIndex + 1);
                text = substring + rest;
            } else {
                if (selectionAnchor < caretIndex) {
                    String firstSubstring = text.substring(0, selectionAnchor);
                    String secondSubstring = text.substring(caretIndex);
                    text = firstSubstring + secondSubstring;
                    caretIndex = selectionAnchor;
                } else {
                    // caretIndex is smaller than selectionAnchor
                    String firstSubstring = text.substring(0, caretIndex);
                    String secondSubstring = text.substring(selectionAnchor);
                    text = firstSubstring + secondSubstring;
                    selectionAnchor = caretIndex;
                }
            }
        }
    }

    private void escape() {
        focused = false;
    }

    private void moveRight() {
        if (!hasSelectedText() || ClientModHelper.isShiftPressed()) {
            if (ClientModHelper.isControlPressed()) {
                boolean isInitiallyInSpace = text.charAt(Math.min(caretIndex, text.length() - 1)) == ' ';
                if (isInitiallyInSpace) {
                    boolean noLongerInSpace = false;
                    int newCaretIndex = caretIndex;
                    for (int i = Math.min(caretIndex + 1, text.length()); i <= text.length(); i++) {
                        char current = text.charAt(Math.min(i, text.length() - 1));
                        if (current != ' ')
                            noLongerInSpace = true;

                        if (i == text.length()) {
                            newCaretIndex = text.length();
                            break;
                        }

                        if (noLongerInSpace && current == ' ') {
                            newCaretIndex = i;
                            break;
                        }
                    }
                    caretIndex = newCaretIndex;
                } else {
                    int newCaretIndex = caretIndex;
                    for (int i = Math.min(caretIndex + 1, text.length()); i <= text.length(); i++) {
                        if (i == text.length() || text.charAt(i) == ' ') {
                            newCaretIndex = i;
                            break;
                        }
                    }
                    caretIndex = newCaretIndex;
                }
            } else
                caretIndex = Math.min(caretIndex + 1, text.length());
        }

        if (!ClientModHelper.isShiftPressed())
            selectionAnchor = caretIndex;
        else {
            Constants.LOG.info("Didn't move selection anchor");
        }
    }

    private void moveLeft() {
        if (!hasSelectedText() || ClientModHelper.isShiftPressed()) {
            if (ClientModHelper.isControlPressed()) {
                boolean isInitiallyInSpace = text.charAt(Math.min(caretIndex, text.length() - 1)) == ' ';
                if (isInitiallyInSpace) {
                    boolean noLongerInSpace = false;
                    int newCaretIndex = caretIndex;
                    for (int i = Math.max(0, caretIndex - 1); i >= 0; i--) {
                        char current = text.charAt(i);
                        if (current != ' ')
                            noLongerInSpace = true;

                        if (i == 0) {
                            newCaretIndex = i;
                            break;
                        }

                        if (noLongerInSpace && current == ' ') {
                            newCaretIndex = i + 1;
                            break;
                        }
                    }
                    caretIndex = newCaretIndex;
                } else {
                    int newCaretIndex = caretIndex;
                    boolean spaceNextToIt = caretIndex != 0 && text.charAt(caretIndex - 1) == ' ';
                    for (int i = Math.max(0, caretIndex - 1); i >= 0; i--) {
                        if (spaceNextToIt) {
                            spaceNextToIt = i != 0 && text.charAt(i - 1) == ' ';
                            continue;
                        }

                        if (i == 0 || text.charAt(i - 1) == ' ') {
                            newCaretIndex = i;
                            break;
                        }
                    }
                    caretIndex = newCaretIndex;
                }
            } else
                caretIndex = Math.max(0, caretIndex - 1);
        }

        if (!ClientModHelper.isShiftPressed())
            selectionAnchor = caretIndex;
        else {
            Constants.LOG.info("Didn't move selection anchor");
        }
    }

    private void skipToEnd() {
        caretIndex = text.length();
        if (!ClientModHelper.isShiftPressed())
            selectionAnchor = caretIndex;
    }

    private void skipToBeginning() {
        caretIndex = 0;
        if (!ClientModHelper.isShiftPressed())
            selectionAnchor = caretIndex;
    }

    private void copy() {
        if (!ClientModHelper.isControlPressed())
            return;

        Minecraft.getInstance().keyboardHandler.setClipboard(getSelectedText());
    }

    private void paste() {
        if (!ClientModHelper.isControlPressed())
            return;

        try {
            if (hasSelectedText())
                deleteText();

            String first = text.substring(0, caretIndex);
            String second = text.substring(caretIndex);
            String pasted = Minecraft.getInstance().keyboardHandler.getClipboard();
            text = first + pasted + second;
            caretIndex += pasted.length();
            selectionAnchor = caretIndex;
        } catch (Exception e) {

        }
    }

    private String getSelectedText() {
        if (hasSelectedText()) {
            if (caretIndex < selectionAnchor)
                return text.substring(caretIndex, selectionAnchor);
            return text.substring(selectionAnchor, caretIndex);
        }
        return "";
    }

    private void selectAll() {
        if (ClientModHelper.isControlPressed()) {
            selectionAnchor = 0;
            caretIndex = text.length();
        }
    }

    private boolean hasSelectedText() {
        return caretIndex != selectionAnchor;
    }

    public void setTextColor(ModColor color) {
        textColor = color;
    }
}
