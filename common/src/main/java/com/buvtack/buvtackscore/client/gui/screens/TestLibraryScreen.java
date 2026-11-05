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

        ModScrollBarBuilder listBar = new ModScrollBarBuilder();
        listBar.background(ModColors.VANILLA_RED.brighten(0.2F));
        listBar.hover(ModColors.VANILLA_BLACK.brighten(0.4F), 2, 1.0F);
        listBar.pivot(ModColors.VANILLA_GRAY, ModColors.VANILLA_GRAY.brighten(0.15F), 2, 1.0F, ModColors.VANILLA_GRAY.brighten(0.4F));
        listBar.bounds(0, 0, 10, 100);
        listBar.outerHeight(1000);
        //ModScrollBar bar = listBar.build();
        //modWidgets.add(bar);
        //bar.setOuterHeight(300);

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
        modWidgets.add(entryList);
    }

    private ModEntry entry(ModColor background) {
        ModEntryBuilder builder = new ModEntryBuilder();
        builder.bounds(0, 0, 150, 30);
        builder.background(background);
        builder.hover(ModColors.VANILLA_DARK_RED, 2, 1.0F);
        builder.modWidgets(List.of(() -> {
                ModLabelBuilder labelBuilder = new ModLabelBuilder();
                labelBuilder.coords(75, 15 - ClientModHelper.font().lineHeight / 2);
                labelBuilder.content(Component.literal("Entry Text"));
                labelBuilder.alignment(Alignment.CENTER);
                return labelBuilder.build();
                }
            )
        );
        return builder.build();
    }
}
