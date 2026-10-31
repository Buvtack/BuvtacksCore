package com.buvtack.buvtackscore.client.gui.screens;

import com.buvtack.buvtackscore.Constants;
import com.buvtack.buvtackscore.client.graphics.ModColors;
import com.buvtack.buvtackscore.client.gui.Alignment;
import com.buvtack.buvtackscore.client.gui.ModTask;
import com.buvtack.buvtackscore.client.gui.components.*;
import com.buvtack.buvtackscore.client.util.ClientModHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;

import java.util.List;

public class TestLibraryScreen extends ModScreen {

    private ModButton testButton;
    private ModLabel testLabel;
    private ModIcon testIcon;

    private ModPanel panel;
    private ModScrollBar bar;

    public TestLibraryScreen(Component title) {
        super(title);
    }

    @Override
    public void init() {
        super.init();

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

        bar = ModScrollBar.builder(0, 0, 10, this.height)
                .outerHeight(1000)
                .background(ModColors.DARK)
                .pivot(ModColors.VANILLA_GRAY, ModColors.VANILLA_AQUA, 4, 1.0F, ModColors.VANILLA_AQUA.desaturate(0.7F))
                .build();

        //modWidgets.add(bar);

        panel = ModPanel.builder(100, 100, 250, 150)
                .backgroundColor(ModColors.VANILLA_RED)
                .modWidgets(bar, testButton)
                .build();

        modWidgets.add(panel);
    }
}
