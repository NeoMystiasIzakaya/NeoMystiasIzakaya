/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.rei.recipe;

import icu.gensoukyo.neo_mystias_izakaya.content.cooking.Kitchenware;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIKitchenware;
import lombok.Getter;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.List;

@Getter
public class NMIRecipeReiCategory<T extends Display> implements DisplayCategory<T> {

    private final Component title;
    private final Renderer icon;

    private final CategoryIdentifier<T> category;
    private final Kitchenware kitchenware;

    public NMIRecipeReiCategory(DeferredHolder<Kitchenware, ?> kitchenware) {
        this.title = Component.translatable(kitchenware.getId().toLanguageKey("rei"));
        this.icon = EntryStacks.of(kitchenware.get().kitchenwareItem());
        this.category = CategoryIdentifier.of(kitchenware.getId());
        this.kitchenware = kitchenware.get();
    }

    @Override
    public CategoryIdentifier<T> getCategoryIdentifier() {
        return category;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public Renderer getIcon() {
        return icon;
    }

    @Override
    public List<Widget> setupDisplay(T display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getMinX() + 6, bounds.getMinY() + 10);
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        for (int i = 0; i < display.getInputEntries().size(); i++) {
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 24 * i, startPoint.y)).entries(display.getInputEntries().get(i)).markInput());
        }
        for (int i = display.getInputEntries().size(); i < 5; i++) {
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 24 * i, startPoint.y)).entry(EntryStack.empty()).markInput());
        }
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 118, startPoint.y)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 148, startPoint.y)).entries(display.getOutputEntries().get(0)).markOutput());
        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 36;
    }

    @Override
    public int getDisplayWidth(T display) {
        return 176;
    }

    public static class CuttingBoard extends NMIRecipeReiCategory<NMIRecipeReiDisplay.CuttingBoard> {
        public CuttingBoard() {
            super(NMIKitchenware.CUTTING_BOARD);
        }
    }

    public static class BoilingPot extends NMIRecipeReiCategory<NMIRecipeReiDisplay.BoilingPot> {
        public BoilingPot() {
            super(NMIKitchenware.BOILING_POT);
        }
    }

    public static class FryingPan extends NMIRecipeReiCategory<NMIRecipeReiDisplay.FryingPan> {
        public FryingPan() {
            super(NMIKitchenware.FRYING_PAN);
        }
    }

    public static class Steamer extends NMIRecipeReiCategory<NMIRecipeReiDisplay.Steamer> {
        public Steamer() {
            super(NMIKitchenware.STEAMER);
        }
    }

    public static class Grill extends NMIRecipeReiCategory<NMIRecipeReiDisplay.Grill> {
        public Grill() {
            super(NMIKitchenware.GRILL);
        }
    }

}