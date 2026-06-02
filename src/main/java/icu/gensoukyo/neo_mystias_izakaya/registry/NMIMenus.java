/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.registry;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.menu.DishServingMenu;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.menu.KitchenwareMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NMIMenus {
    public static final DeferredRegister<MenuType<?>> MENU_TYPE = DeferredRegister.create(Registries.MENU, NeoMystiasIzakaya.MODID);
    public static final Supplier<MenuType<KitchenwareMenu>> KITCHENWARE_MENU = MENU_TYPE.register("kitchenware", () -> IMenuTypeExtension.create(KitchenwareMenu::new));
    public static final Supplier<MenuType<DishServingMenu>> DISH_SERVING_MENU = MENU_TYPE.register("dish_serving", () -> IMenuTypeExtension.create(DishServingMenu::new));
}
