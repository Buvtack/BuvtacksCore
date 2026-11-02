package com.buvtack.buvtackscore.client.gui.screens;

import com.buvtack.buvtackscore.Constants;
import com.buvtack.buvtackscore.client.graphics.ModColor;
import com.buvtack.buvtackscore.client.graphics.ModColors;
import com.buvtack.buvtackscore.client.gui.Alignment;
import com.buvtack.buvtackscore.client.gui.ModTask;
import com.buvtack.buvtackscore.client.gui.builders.*;
import com.buvtack.buvtackscore.client.gui.components.*;
import com.buvtack.buvtackscore.client.util.ClientModHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class TestLibraryScreen extends ModScreen {

    private ModButton testButton;
    private ModLabel testLabel;
    private ModIcon testIcon;

    private ModPanel panel;
    private ModScrollBar bar;
    private ModEntry entry;
    private ModEntryList<ModEntry> entryList;

    private int color = 0xFF000000;

    public TestLibraryScreen(Component title) {
        super(title);
    }

    @Override
    public void init() {
        super.init();
        modWidgets.clear();
        children().clear();

        ModButtonBuilder testButtonBuilder = new ModButtonBuilder();
        testButtonBuilder.bounds(width / 2 - 75, height / 4, 150, 20);
        testButtonBuilder.withVanillaBackground();
        testButtonBuilder.withVanillaHover();
        testButtonBuilder.padding(4);
        testButtonBuilder.border(ModColors.VANILLA_GOLD);
        testButtonBuilder.hover(ModColors.VANILLA_GOLD.transparensify(0.6F), 3, 1.0F);
        testButtonBuilder.pressed(ModColors.VANILLA_GOLD.transparensify(0.85F));
        testButtonBuilder.clickSound(SoundEvents.WITHER_SPAWN);
        testButtonBuilder.onClick(() -> {Constants.LOG.info("THE MONSTER HAS BEEN SUMMONED");});
        testButtonBuilder.modWidgets(List.of(() -> {
                    ModLabelBuilder labelBuilder = new ModLabelBuilder();
                    labelBuilder.coords(75, 10 - ClientModHelper.font().lineHeight / 2);
                    labelBuilder.content(Component.literal("Friendly Button"));
                    labelBuilder.alignment(Alignment.CENTER);
                    labelBuilder.textColor(ModColors.VANILLA_DARK_RED);
                    labelBuilder.hoverTextColor(ModColors.VANILLA_DARK_RED.brighten(0.15F));
                    labelBuilder.pressedTextColor(ModColors.VANILLA_DARK_RED.brighten(0.3F));
                    labelBuilder.focusedTextColor(ModColors.VANILLA_DARK_RED.brighten(0.45F));
                    ModLabel label = labelBuilder.build();
                    label.renderTasks.addTask("highlightText", new ModTask<>(((graphics, mouseX, mouseY, partialTick) -> {
                        if (testButton.hovered(mouseX, mouseY))
                            label.setTextColor(ModColors.VANILLA_YELLOW.brighten(0.5F));
                        else
                            label.setTextColor(ModColors.VANILLA_DARK_RED);
                    })));
                    return label;
                }
            )
        );
        testButton = testButtonBuilder.build();
        modWidgets.add(testButton);
    }

//    private ModEntry entry(ModColor background) {
//        ModEntryBuilder builder = new ModEntryBuilder();
//        builder.bounds(0, 0, 150, 30);
//        builder.background(background);
//        builder.hover(ModColors.VANILLA_DARK_RED, 2, 1.0F);
//        builder.modWidgets(List.of(() -> {
//                ModLabelBuilder labelBuilder = new ModLabelBuilder();
//                labelBuilder.coords(0, 0);
//                labelBuilder.content(Component.literal("Entry Text"));
//                labelBuilder.alignment(Alignment.CENTER);
//                return labelBuilder.build();
//                }
//            )
//        );
//        return builder.build();
//    }
}
