package icu.gensoukyo.neo_mystias_izakaya.registry.item;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class NMIIngredientItems {
    public static final List<DeferredItem<Item>> ITEM_LIST = new ArrayList<>();
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(NeoMystiasIzakaya.MODID);

    public static DeferredItem<Item> registerItem(
            DeferredRegister.Items register,
            String name,
            Function<Item.Properties, Item> func,
            UnaryOperator<Item.Properties> properties
    ) {
        DeferredItem<Item> item = register.registerItem(name, func, properties);
        ITEM_LIST.add(item);
        return item;
    }

    public static final DeferredItem<Item> BAMBOO_SHOOTS = registerItem(ITEMS, "ingredient/bamboo_shoots", Item::new, props -> props);

    public static final DeferredItem<Item> BLACK_PORK = registerItem(ITEMS, "ingredient/black_pork", Item::new, props -> props);

    public static final DeferredItem<Item> BLACK_SALT = registerItem(ITEMS, "ingredient/black_salt", Item::new, props -> props);

    public static final DeferredItem<Item> BROCCOLI = registerItem(ITEMS, "ingredient/broccoli", Item::new, props -> props);

    public static final DeferredItem<Item> BUTTER = registerItem(ITEMS, "ingredient/butter", Item::new, props -> props);

    public static final DeferredItem<Item> CAPSAICIN = registerItem(ITEMS, "ingredient/capsaicin", Item::new, props -> props);

    public static final DeferredItem<Item> CHEESE = registerItem(ITEMS, "ingredient/cheese", Item::new, props -> props);

    public static final DeferredItem<Item> CHESTNUT = registerItem(ITEMS, "ingredient/chestnut", Item::new, props -> props);

    public static final DeferredItem<Item> CHILI = registerItem(ITEMS, "ingredient/chili", Item::new, props -> props);

    public static final DeferredItem<Item> CICADA_SHELL = registerItem(ITEMS, "ingredient/cicada_shell", Item::new, props -> props);

    public static final DeferredItem<Item> CRAB = registerItem(ITEMS, "ingredient/crab", Item::new, props -> props);

    public static final DeferredItem<Item> CREAM = registerItem(ITEMS, "ingredient/cream", Item::new, props -> props);

    public static final DeferredItem<Item> CUCUMBER = registerItem(ITEMS, "ingredient/cucumber", Item::new, props -> props);

    public static final DeferredItem<Item> DEW = registerItem(ITEMS, "ingredient/dew", Item::new, props -> props);

    public static final DeferredItem<Item> FICUS_MICROCARPA = registerItem(ITEMS, "ingredient/ficus_microcarpa", Item::new, props -> props);

    public static final DeferredItem<Item> FLOUR = registerItem(ITEMS, "ingredient/flour", Item::new, props -> props);

    public static final DeferredItem<Item> FLOWERS = registerItem(ITEMS, "ingredient/flowers", Item::new, props -> props);

    public static final DeferredItem<Item> GINKGO = registerItem(ITEMS, "ingredient/ginkgo", Item::new, props -> props);

    public static final DeferredItem<Item> GRAPE = registerItem(ITEMS, "ingredient/grape", Item::new, props -> props);

    public static final DeferredItem<Item> HAGFISH = registerItem(ITEMS, "ingredient/hagfish", Item::new, props -> props);

    public static final DeferredItem<Item> LEMON = registerItem(ITEMS, "ingredient/lemon", Item::new, props -> props);

    public static final DeferredItem<Item> LOTUS_NUTS = registerItem(ITEMS, "ingredient/lotus_nuts", Item::new, props -> props);

    public static final DeferredItem<Item> MOONFLOWER = registerItem(ITEMS, "ingredient/moonflower", Item::new, props -> props);

    public static final DeferredItem<Item> OCTOPUS = registerItem(ITEMS, "ingredient/octopus", Item::new, props -> props);

    public static final DeferredItem<Item> ONION = registerItem(ITEMS, "ingredient/onion", Item::new, props -> props);

    public static final DeferredItem<Item> PEACH = registerItem(ITEMS, "ingredient/peach", Item::new, props -> props);

    public static final DeferredItem<Item> PINE_NUT = registerItem(ITEMS, "ingredient/pine_nut", Item::new, props -> props);

    public static final DeferredItem<Item> PLUM = registerItem(ITEMS, "ingredient/plum", Item::new, props -> props);

    public static final DeferredItem<Item> PUFF_YO_FRUIT = registerItem(ITEMS, "ingredient/puff_yo_fruit", Item::new, props -> props);

    public static final DeferredItem<Item> RED_BEANS = registerItem(ITEMS, "ingredient/red_beans", Item::new, props -> props);

    public static final DeferredItem<Item> SALMON = registerItem(ITEMS, "ingredient/salmon", Item::new, props -> props);

    public static final DeferredItem<Item> SEA_URCHIN = registerItem(ITEMS, "ingredient/sea_urchin", Item::new, props -> props);

    public static final DeferredItem<Item> SHRIMP = registerItem(ITEMS, "ingredient/shrimp", Item::new, props -> props);

    public static final DeferredItem<Item> STICKY_RICE = registerItem(ITEMS, "ingredient/sticky_rice", Item::new, props -> props);

    public static final DeferredItem<Item> SUPREME_TUNA = registerItem(ITEMS, "ingredient/supreme_tuna", Item::new, props -> props);

    public static final DeferredItem<Item> SWEET_POTATO = registerItem(ITEMS, "ingredient/sweet_potato", Item::new, props -> props);

    public static final DeferredItem<Item> TOFU = registerItem(ITEMS, "ingredient/tofu", Item::new, props -> props);

    public static final DeferredItem<Item> TOMATO = registerItem(ITEMS, "ingredient/tomato", Item::new, props -> props);

    public static final DeferredItem<Item> TOON = registerItem(ITEMS, "ingredient/toon", Item::new, props -> props);

    public static final DeferredItem<Item> TREMELLA = registerItem(ITEMS, "ingredient/tremella", Item::new, props -> props);

    public static final DeferredItem<Item> TROUT = registerItem(ITEMS, "ingredient/trout", Item::new, props -> props);

    public static final DeferredItem<Item> TRUFFLE = registerItem(ITEMS, "ingredient/truffle", Item::new, props -> props);

    public static final DeferredItem<Item> TUNA = registerItem(ITEMS, "ingredient/tuna", Item::new, props -> props);

    public static final DeferredItem<Item> TWIN_LOTUS = registerItem(ITEMS, "ingredient/twin_lotus", Item::new, props -> props);

    public static final DeferredItem<Item> UDUMBARA = registerItem(ITEMS, "ingredient/udumbara", Item::new, props -> props);

    public static final DeferredItem<Item> VENISON = registerItem(ITEMS, "ingredient/venison", Item::new, props -> props);

    public static final DeferredItem<Item> WAGYU_BEEF = registerItem(ITEMS, "ingredient/wagyu_beef", Item::new, props -> props);

    public static final DeferredItem<Item> WHITE_RADISH = registerItem(ITEMS, "ingredient/white_radish", Item::new, props -> props);

    public static final DeferredItem<Item> WILD_BOAR_MEAT = registerItem(ITEMS, "ingredient/wild_boar_meat", Item::new, props -> props);

}
