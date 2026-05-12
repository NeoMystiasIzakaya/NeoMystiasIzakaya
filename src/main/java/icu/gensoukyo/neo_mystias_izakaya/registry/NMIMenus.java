package icu.gensoukyo.neo_mystias_izakaya.registry;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.KitchenwareMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class NMIMenus {
    public static final DeferredRegister<MenuType<?>> MENU_TYPE = DeferredRegister.create(Registries.MENU, NeoMystiasIzakaya.MODID);
    public static final Supplier<MenuType<KitchenwareMenu>> KITCHENWARE_MENU = MENU_TYPE.register("grill", () -> IMenuTypeExtension.create(KitchenwareMenu::new));
}
