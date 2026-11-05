package com.buvtack.buvtackscore.client.gui.screens;

import com.buvtack.buvtackscore.Constants;
import com.buvtack.buvtackscore.client.graphics.ModColor;
import com.buvtack.buvtackscore.client.graphics.ModColors;
import com.buvtack.buvtackscore.client.gui.Alignment;
import com.buvtack.buvtackscore.client.gui.DefaultTaskPriorities;
import com.buvtack.buvtackscore.client.gui.ModTask;
import com.buvtack.buvtackscore.client.gui.builders.*;
import com.buvtack.buvtackscore.client.gui.components.*;
import com.buvtack.buvtackscore.client.util.ClientModHelper;
import net.minecraft.client.gui.components.CycleButton;
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
        testButtonBuilder.pressed(ModColors.VANILLA_GOLD.transparensify(0.5F));
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
        testButton.tickTasks.addTask("shrink", new ModTask<>(() -> {
            if (testButton.pressed()) {
                testButton.setPadding(1);
            } else
                testButton.setPadding(3);
        }));
        //modWidgets.add(testButton);

        ModLabelBuilder testLabelBuilder = new ModLabelBuilder();
        testLabelBuilder.coords(width / 2, height / 2);
        testLabelBuilder.alignment(Alignment.CENTER);
        testLabelBuilder.content(Component.literal("Fuck OFF"));
        testLabelBuilder.padding(3);
        testLabelBuilder.border(ModColors.VANILLA_GREEN);
        testLabelBuilder.hoverBorder(ModColors.VANILLA_AQUA);
        testLabel = testLabelBuilder.build();
        //modWidgets.add(testLabel);

        ModIconBuilder testIconBuilder = new ModIconBuilder();
        testIconBuilder.bounds(width / 2 - 8, (int) (height * 0.75), 16, 16);
        testIconBuilder.icon(Identifier.fromNamespaceAndPath("minecraft", "textures/item/nether_star.png"));
        //modWidgets.add(testIconBuilder.build());

        ModPanelBuilder testPanelBuilder = new ModPanelBuilder();
        testPanelBuilder.bounds(10, 10, 100, 200);
        testPanelBuilder.background(ModColors.VANILLA_RED.saturate(0.5F));
        testPanelBuilder.vanillaWidgets(List.of(() -> {
            return CycleButton.onOffBuilder(false).create(10, 10, 80, 20, Component.literal("shit"));
        }));
        //modWidgets.add(testPanelBuilder.build());

        ModScrollBarBuilder testBarBuilder = new ModScrollBarBuilder();
        testBarBuilder.bounds(width - 10, 0, 10, height);
        testBarBuilder.pivot(ModColors.VANILLA_GRAY, ModColors.VANILLA_GRAY.brighten(0.15F), 4, 1.0F, ModColors.VANILLA_GRAY.brighten(0.4F));
        testBarBuilder.outerHeight(1000);
        testBarBuilder.background(ModColors.VANILLA_RED);
       // modWidgets.add(testBarBuilder.build());

        ModEntryBuilder testEntryBuilder = new ModEntryBuilder();
        testEntryBuilder.bounds(width / 2 - 50, height - 50, 100, 40);
        testEntryBuilder.hoverBorder(ModColors.VANILLA_DARK_PURPLE);
        testEntryBuilder.background(ModColors.VANILLA_DARK_GREEN);
        testEntryBuilder.modWidgets(List.of(() -> {
            ModLabelBuilder entryLabelBuilder = new ModLabelBuilder();
            entryLabelBuilder.coords(50, 20 - ClientModHelper.font().lineHeight / 2);
            entryLabelBuilder.alignment(Alignment.CENTER);
            entryLabelBuilder.content(Component.literal("Entry Label"));
            entryLabelBuilder.textColor(ModColors.VANILLA_LIGHT_PURPLE.saturate(0.75F));
            return entryLabelBuilder.build();
        }));
        //modWidgets.add(testEntryBuilder.build());

        ModScrollBarBuilder listBar = new ModScrollBarBuilder();
        listBar.background(ModColors.VANILLA_RED.brighten(0.2F));
        listBar.hover(ModColors.VANILLA_BLACK.brighten(0.4F), 2, 1.0F);
        listBar.pivot(ModColors.VANILLA_GRAY, ModColors.VANILLA_GRAY.brighten(0.15F), 2, 1.0F, ModColors.VANILLA_GRAY.brighten(0.4F));
        listBar.bounds(0, 0, 10, 100);
        listBar.outerHeight(1000);

        ModEntryListBuilder<ModEntry> testListBuilder = new ModEntryListBuilder<>();
        testListBuilder.bar(listBar.build(), Alignment.RIGHT);
        testListBuilder.gap(3);
        testListBuilder.innerMargin(10);
        testListBuilder.bounds(width / 2 - 100, 40, 200, height - 80);
        testListBuilder.entries(List.of(
                () -> entry(ModColors.VANILLA_BLUE.darken(1.0F).blueify(0.0F)),
                () -> entry(ModColors.VANILLA_BLUE.darken(1.0F).blueify(0.05F)),
                () -> entry(ModColors.VANILLA_BLUE.darken(1.0F).blueify(0.1F)),
                () -> entry(ModColors.VANILLA_BLUE.darken(1.0F).blueify(0.15F)),
                () -> entry(ModColors.VANILLA_BLUE.darken(1.0F).blueify(0.2F)),
                () -> entry(ModColors.VANILLA_BLUE.darken(1.0F).blueify(0.25F)),
                () -> entry(ModColors.VANILLA_BLUE.darken(1.0F).blueify(0.3F)),
                () -> entry(ModColors.VANILLA_BLUE.darken(1.0F).blueify(0.35F)),
                () -> entry(ModColors.VANILLA_BLUE.darken(1.0F).blueify(0.4F)),
                () -> entry(ModColors.VANILLA_BLUE.darken(1.0F).blueify(0.45F)),
                () -> entry(ModColors.VANILLA_BLUE.darken(1.0F).blueify(0.5F)),
                () -> entry(ModColors.VANILLA_BLUE.darken(1.0F).blueify(0.55F)),
                () -> entry(ModColors.VANILLA_BLUE.darken(1.0F).blueify(0.6F)),
                () -> entry(ModColors.VANILLA_BLUE.darken(1.0F).blueify(0.65F)),
                () -> entry(ModColors.VANILLA_BLUE.darken(1.0F).blueify(0.7F)),
                () -> entry(ModColors.VANILLA_BLUE.darken(1.0F).blueify(0.75F)),
                () -> entry(ModColors.VANILLA_BLUE.darken(1.0F).blueify(0.8F)),
                () -> entry(ModColors.VANILLA_BLUE.darken(1.0F).blueify(0.85F)),
                () -> entry(ModColors.VANILLA_BLUE.darken(1.0F).blueify(0.9F)),
                () -> entry(ModColors.VANILLA_BLUE.darken(1.0F).blueify(0.95F)),
                () -> entry(ModColors.VANILLA_BLUE.darken(1.0F).blueify(1.0F))
        ));
        entryList = testListBuilder.build();
//        for (int i = 0; i < 30; i++) {
//            ModEntry entry = entry(new ModColor(color));
//            entryList.addEntries(List.of(() -> entry));
//            color += 8;
//        }
        modWidgets.add(entryList);
    }

    private ModEntry entry(ModColor background) {
        ModEntryBuilder builder = new ModEntryBuilder();
        builder.bounds(0, 0, 150, 30);
        builder.background(background);
        builder.hover(ModColors.VANILLA_DARK_RED, 2, 1.0F);
        builder.modWidgets(List.of(() -> {
                ModLabelBuilder labelBuilder = new ModLabelBuilder();
                labelBuilder.coords(0, 0);
                labelBuilder.content(Component.literal("Entry Text"));
                labelBuilder.alignment(Alignment.CENTER);
                return labelBuilder.build();
                }
            )
        );
        return builder.build();
    }
}
