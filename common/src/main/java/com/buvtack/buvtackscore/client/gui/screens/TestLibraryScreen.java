package com.buvtack.buvtackscore.client.gui.screens;

import com.buvtack.buvtackscore.Constants;
import com.buvtack.buvtackscore.client.graphics.ModColor;
import com.buvtack.buvtackscore.client.graphics.ModColors;
import com.buvtack.buvtackscore.client.gui.Alignment;
import com.buvtack.buvtackscore.client.gui.ModTask;
import com.buvtack.buvtackscore.client.gui.components.*;
import com.buvtack.buvtackscore.client.util.ClientModHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;

import java.util.List;
import java.util.Map;

public class TestLibraryScreen extends ModScreen {

    private ModButton testButton;
    private ModLabel testLabel;
    private ModIcon testIcon;

    private ModPanel panel;
    private ModScrollBar bar;
    private ModEntry entry;
    private ModEntryList<ModEntry> entryList;

    public TestLibraryScreen(Component title) {
        super(title);
    }

    @Override
    public void init() {
        super.init();
        modWidgets.clear();
        children().clear();

        testLabel = ModLabel.builder()
                .alignment(Alignment.LEFT)
                .coords(10, 10)
                .content(Component.literal("Test Label"))
                .background(ModColors.NEUTRAL)
                .border(ModColors.VANILLA_DARK_RED)
                .build();

        testIcon = ModIcon.builder()
                .icon(Identifier.fromNamespaceAndPath("minecraft", "textures/item/diamond.png"))
                .bounds(50, 10, 16, 16)
                .build();

        testButton = ModButton.builder(200, 200, 100, 40)
                //.background(ModColors.VANILLA_GOLD.transparensify(0.75F))
                //.hover(ModColors.VANILLA_GOLD.transparensify(0.5F), 12, 0.5F)
                .withVanillaButtonTexture()
                .withVanillaButtonHighlight()
                .padding(1)
                .onClick(() -> Constants.LOG.info("Button clicked!"))
                .clickSound(SoundEvents.SHEEP_DEATH)
                .build();

        ModLabel buttonLabel = ModLabel.builder()
                .coords(10, 10)
                .alignment(Alignment.CENTER)
                .content(Component.literal("Test Label"))
                .build();

        ModIcon buttonIcon = ModIcon.builder()
                .bounds(0, 0, 16, 16)
                .icon(Identifier.fromNamespaceAndPath("minecraft", "textures/item/emerald.png"))
                .alignment(Alignment.CENTER)
                .build();

        testButton.label(buttonLabel, 0, 0);
        testButton.icon(buttonIcon);
        testButton.onClick(() -> {
            Constants.LOG.info("label width: " + buttonLabel.width);
            Constants.LOG.info("label alignment: " + buttonLabel.alignment.name());
        });

        modWidgets.addAll(List.of(testLabel, testIcon, testButton));

        entry = ModEntry.newBuilder(50, 50, 150, 30)
                .backgroundColor(ModColors.VANILLA_DARK_GREEN.transparensify(0.0F))
                .hover(ModColors.VANILLA_DARK_RED, 1F, 1.0F)
                .modWidgets(
                        ModButton.builder(10, 10, 90, 25)
                                .background(ModColors.VANILLA_BLUE)
                                .hover(ModColors.VANILLA_GREEN, 3, 1.0F)
                                .build()
                )
                .build();

        //modWidgets.add(entry);

        ModEntry otherEntry = ModEntry.newBuilder(0, 0, 150, 30)
                .backgroundColor(ModColors.VANILLA_GREEN)
                .hover(ModColors.VANILLA_DARK_RED, 2, 1.0F)
                .modWidgets(
                        ModLabel.builder().content(Component.literal("Entry Text")).alignment(Alignment.CENTER).build()
                )
                .build();

        bar = ModScrollBar.builder(0, 0, 10, this.height)
                .outerHeight(1000)
                .background(ModColors.DARK)
                .pivot(ModColors.VANILLA_GRAY, ModColors.VANILLA_AQUA, 4, 1.0F, ModColors.VANILLA_AQUA.desaturate(0.7F))
                .build();

        int color = 0xFF000000;
        ModEntry[] entries = new ModEntry[30];
        for (int i = 0; i < 30; i++) {
            entries[i] = entry(new ModColor(color));
            color += 8;
        }

        entryList = ModEntryList.newBuilder(width / 2 - 100, 25, 200, height - 50)
                .bar(bar, Alignment.RIGHT)
                .entries(entries)
                .gap(3)
                .build();

        modWidgets.add(entryList);
    }

    private ModEntry entry(ModColor background) {
        return ModEntry.newBuilder(0, 0, 150, 30)
                .backgroundColor(background)
                .hover(ModColors.VANILLA_DARK_RED, 2, 1.0F)
                .modWidgets(
                        ModLabel.builder().coords(0, 0).content(Component.literal("Entry Text")).alignment(Alignment.CENTER).build()
                )
                .build();
    }
}
