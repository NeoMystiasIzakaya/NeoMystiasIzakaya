package icu.gensoukyo.neo_mystias_izakaya.datagen;

import com.google.gson.JsonObject;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIDrinkItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIFoodItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIIngredientItems;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

public class NMILanguageProvider implements DataProvider {
    private final Map<String, String> enData = new TreeMap<>();
    private final Map<String, String> cnData = new TreeMap<>();
    private final PackOutput output;
    private final String locale;

    public NMILanguageProvider(PackOutput output, String locale) {
        this.output = output;
        this.locale = locale;
    }

    private void addTranslations() {
        this.add(NeoMystiasIzakaya.MODID, "Neo Mystia's Izakaya", "新夜雀食堂");

    }

    private void addItemTranslations() {
        this.add(NMIIngredientItems.BAMBOO_SHOOTS.asItem(), "Bamboo Shoots", "竹笋");
        this.add(NMIIngredientItems.BLACK_SALT.asItem(), "Black Salt", "黑盐");
        this.add(NMIIngredientItems.BLACK_PORK.asItem(), "Black Pork", "黑猪肉");
        this.add(NMIIngredientItems.BROCCOLI.asItem(), "Broccoli", "西兰花");
        this.add(NMIIngredientItems.VENISON.asItem(), "Venison", "鹿肉");
        this.add(NMIIngredientItems.BUTTER.asItem(), "Butter", "黄油");
        this.add(NMIIngredientItems.CAPSAICIN.asItem(), "Capsaicin", "辣椒素");
        this.add(NMIIngredientItems.CHEESE.asItem(), "Cheese", "奶酪");
        this.add(NMIIngredientItems.CHESTNUT.asItem(), "Chestnut", "栗子");
        this.add(NMIIngredientItems.CHILI.asItem(), "Chili", "辣椒");
        this.add(NMIIngredientItems.CRAB.asItem(), "Crab", "螃蟹");
        this.add(NMIIngredientItems.CREAM.asItem(), "Cream", "奶油");
        this.add(NMIIngredientItems.CUCUMBER.asItem(), "Cucumber", "黄瓜");
        this.add(NMIIngredientItems.DEW.asItem(), "Dew", "露水");
        this.add(NMIIngredientItems.FLOUR.asItem(), "Flour", "面粉");
        this.add(NMIIngredientItems.FLOWERS.asItem(), "Flowers", "食用花");
        this.add(NMIIngredientItems.FICUS_MICROCARPA.asItem(), "Ficus Microcarpa", "薜茘");
        this.add(NMIIngredientItems.GINKGO.asItem(), "Ginkgo", "白果");
        this.add(NMIIngredientItems.GRAPE.asItem(), "Grape", "葡萄");
        this.add(NMIIngredientItems.HAGFISH.asItem(), "Hagfish", "八目鳗");
        this.add(NMIIngredientItems.LEMON.asItem(), "Lemon", "柠檬");
        this.add(NMIIngredientItems.LOTUS_NUTS.asItem(), "Lotus Nuts", "莲子");
        this.add(NMIIngredientItems.MOONFLOWER.asItem(), "Moonflower", "月光花");
        this.add(NMIIngredientItems.OCTOPUS.asItem(), "Octopus", "章鱼");
        this.add(NMIIngredientItems.ONION.asItem(), "Onion", "洋葱");
        this.add(NMIIngredientItems.PEACH.asItem(), "Peach", "桃子");
        this.add(NMIIngredientItems.PINE_NUT.asItem(), "Pine Nut", "松子");
        this.add(NMIIngredientItems.PLUM.asItem(), "Plum", "李子");
        this.add(NMIIngredientItems.PUFF_YO_FRUIT.asItem(), "Puff Yo Fruit", "噗噗哟果");
        this.add(NMIIngredientItems.RED_BEANS.asItem(), "Red Beans", "红豆");
        this.add(NMIIngredientItems.SALMON.asItem(), "Salmon", "三文鱼");
        this.add(NMIIngredientItems.SEA_URCHIN.asItem(), "Sea Urchin", "海胆");
        this.add(NMIIngredientItems.SHRIMP.asItem(), "Shrimp", "虾");
        this.add(NMIIngredientItems.CICADA_SHELL.asItem(), "Cicada Shell", "蝉蜕");
        this.add(NMIIngredientItems.UDUMBARA.asItem(), "Udumbara", "幻昙华");
        this.add(NMIIngredientItems.STICKY_RICE.asItem(), "Sticky Rice", "糯米");
        this.add(NMIIngredientItems.SUPREME_TUNA.asItem(), "Supreme Tuna", "极上金枪鱼");
        this.add(NMIIngredientItems.SWEET_POTATO.asItem(), "Sweet Potato", "红薯");
        this.add(NMIIngredientItems.TOFU.asItem(), "Tofu", "豆腐");
        this.add(NMIIngredientItems.TOMATO.asItem(), "Tomato", "番茄");
        this.add(NMIIngredientItems.TOON.asItem(), "Toon", "香椿");
        this.add(NMIIngredientItems.TREMELLA.asItem(), "Tremella", "银耳");
        this.add(NMIIngredientItems.TROUT.asItem(), "Trout", "鳟鱼");
        this.add(NMIIngredientItems.TRUFFLE.asItem(), "Truffle", "松露");
        this.add(NMIIngredientItems.TUNA.asItem(), "Tuna", "金枪鱼");
        this.add(NMIIngredientItems.TWIN_LOTUS.asItem(), "Twin Lotus", "并蒂莲");
        this.add(NMIIngredientItems.WAGYU_BEEF.asItem(), "Wagyu Beef", "和牛");
        this.add(NMIIngredientItems.WHITE_RADISH.asItem(), "White Radish", "白萝卜");
        this.add(NMIIngredientItems.WILD_BOAR_MEAT.asItem(), "Wild Boar Meat", "野猪肉");
        this.add(NMIFoodItems.ALL_MEAT_FEAST.asItem(), "All Meat Feast", "全肉盛宴");
        this.add(NMIFoodItems.ARCTIC_SWEET_SHRIMP_AND_PEACH_SALAD.asItem(), "Arctic Sweet Shrimp and Peach Salad", "北极甜虾蜜桃色拉");
        this.add(NMIFoodItems.ASSORTED_TEMPURA.asItem(), "Assorted Tempura", "什锦天妇罗");
        this.add(NMIFoodItems.A_LITTLE_SWEET_POISON.asItem(), "A Little Sweet Poison", "小小的甜蜜「毒药」");
        this.add(NMIFoodItems.BAKED_CRAB_WITH_CREAM.asItem(), "Baked Crab with Cream", "奶油焗蟹");
        this.add(NMIFoodItems.BAKED_SWEET_POTATOES.asItem(), "Baked Sweet Potatoes", "烤地瓜");
        this.add(NMIFoodItems.BAMBOO_SHOOTS_FRIED_MEAT.asItem(), "Bamboo Shoots Fried Meat", "竹笋炒肉");
        this.add(NMIFoodItems.BAMBOO_SHOOTS_STEWED_IN_STONE_POT.asItem(), "Bamboo Shoots Stewed in Stone Pot", "石锅竹笋炖肉");
        this.add(NMIFoodItems.BAMBOO_STEAMED_EGG.asItem(), "Bamboo Steamed Egg", "竹筒蒸蛋");
        this.add(NMIFoodItems.BAMBOO_TUBE_ROASTED_DRUNKEN_SHRIMP.asItem(), "Bamboo Tube Roasted Drunken Shrimp", "竹筒烧醉虾");
        this.add(NMIFoodItems.BAMBOO_TUBE_STEAMED_PORK.asItem(), "Bamboo Tube Steamed Pork", "竹筒粉蒸肉");
        this.add(NMIFoodItems.BEAR_PAW.asItem(), "Bear Paw", "熊掌");
        this.add(NMIFoodItems.BEEF_HOT_POT.asItem(), "Beef Hot Pot", "牛肉鸳鸯火锅");
        this.add(NMIFoodItems.BEEF_RICE.asItem().asItem(), "Beef Rice", "牛肉盖浇饭");
        this.add(NMIFoodItems.BEEF_WELLINGTON.asItem(), "Beef Wellington", "惠灵顿牛排");
        this.add(NMIFoodItems.BEETLE_STEAMED_CAKE.asItem(), "Beetle Steamed Cake", "兜甲蒸糕");
        this.add(NMIFoodItems.BURNT_PUDDING.asItem(), "Burnt Pudding", "燃尽布丁");
        this.add(NMIFoodItems.BISCAY_BISCUITS.asItem(), "Biscay Biscuits", "比斯开湾饼干");
        this.add(NMIFoodItems.BOILED_FISH.asItem(), "Boiled Fish", "水煮鱼");
        this.add(NMIFoodItems.BRAISED_EEL.asItem(), "Braised Eel", "红烧鳗鱼");
        this.add(NMIFoodItems.BRAISED_PORK_WITH_PEACH.asItem(), "Braised Pork with Peach", "桃子红烧肉");
        this.add(NMIFoodItems.BUTTER_STEAK.asItem(), "Butter Steak", "黄油牛排");
        this.add(NMIFoodItems.CANDIED_CHESTNUTS.asItem(), "Candied Chestnuts", "糖栗子");
        this.add(NMIFoodItems.CANDIED_SWEET_POTATO.asItem(), "Candied Sweet Potato", "拔丝地瓜");
        this.add(NMIFoodItems.CATS_PLAYING_IN_WATER.asItem(), "Cats Playing in Water", "猫咪戏水");
        this.add(NMIFoodItems.CAT_FOOD.asItem(), "Cat Food", "猫饭");
        this.add(NMIFoodItems.CAT_KULULI.asItem(), "Cat Kululi", "猫咕噜哩");
        this.add(NMIFoodItems.CAT_PIZZA.asItem(), "Cat Pizza", "猫咪披萨");
        this.add(NMIFoodItems.CHEESE_EGG.asItem(), "Cheese Egg", "芝士蛋");
        this.add(NMIFoodItems.COLD_DISH_CARVING.asItem(), "Cold Dish Carving", "凉菜雕花");
        this.add(NMIFoodItems.COLD_TOFU.asItem(), "Cold Tofu", "冷豆腐");
        this.add(NMIFoodItems.COLORFUL_JADE_FRIED_BUNS.asItem(), "Colorful Jade Fried Buns", "华光玉煎包");
        this.add(NMIFoodItems.COOKING_TOFU.asItem(), "Cooking Tofu", "煮豆腐");
        this.add(NMIFoodItems.CREAM_STEW.asItem(), "Cream Stew", "奶油炖菜");
        this.add(NMIFoodItems.CRISP_CYCLONE.asItem(), "Crisp Cyclone", "脆旋风");
        this.add(NMIFoodItems.DARK_CUISINE.asItem(), "Dark Cuisine", "黑暗物质");
        this.add(NMIFoodItems.DEEP_FRIED_CICADA_SHELLS.asItem(), "Deep Fried Cicada Shells", "炸蝉蜕");
        this.add(NMIFoodItems.DEPRESSED_CHEESE_STICKS.asItem(), "Depressed Cheese Sticks", "忧郁芝士条");
        this.add(NMIFoodItems.DEW_BOILED_EGGS.asItem(), "Dew Boiled Eggs", "露水煮蛋");
        this.add(NMIFoodItems.DORAYAKI.asItem(), "Dorayaki", "铜锣烧");
        this.add(NMIFoodItems.DUMPLING.asItem(), "Dumpling", "饺子");
        this.add(NMIFoodItems.EEL_EGG_DONBURI.asItem(), "Eel Egg Donburi", "鳗鱼蛋盖饭");
        this.add(NMIFoodItems.EGGS_BENEDICT.asItem(), "Eggs Benedict", "班尼迪克蛋");
        this.add(NMIFoodItems.ENERGY_STRING.asItem(), "Energy String", "能量串");
        this.add(NMIFoodItems.FAILING_SAKURA_SNOW.asItem(), "Failing Sakura Snow", "樱落雪");
        this.add(NMIFoodItems.FANTASY_IS_ALL_THE_RAGE.asItem(), "Fantasy Is All the Rage", "幻想风靡");
        this.add(NMIFoodItems.FISH_LEAPS_OVER_DRAGON_GATE.asItem(), "Fish Leaps Over Dragon Gate", "鱼跃龙门");
        this.add(NMIFoodItems.FLOWERS_BIRDS_WIND_AND_MOON.asItem(), "Flowers Birds Wind and Moon", "花鸟风月");
        this.add(NMIFoodItems.FLOWING_WATER_NOODLES.asItem(), "Flowing Water Noodles", "流水素面");
        this.add(NMIFoodItems.FRIED_HAGFISH.asItem(), "Fried Hagfish", "炸八目鳗");
        this.add(NMIFoodItems.FRIED_PORK_CUTLET.asItem(), "Fried Pork Cutlet", "炸猪排");
        this.add(NMIFoodItems.FRIED_PORK_SHREDS.asItem(), "Fried Pork Shreds", "炒肉丝");
        this.add(NMIFoodItems.FRIED_SHRIMP_TEMPURA.asItem(), "Fried Shrimp Tempura", "炸虾天妇罗");
        this.add(NMIFoodItems.FRIED_TOFU.asItem(), "Fried Tofu", "油豆腐");
        this.add(NMIFoodItems.FRIED_TOMATO_STRIPS.asItem(), "Fried Tomato Strips", "炸番茄条");
        this.add(NMIFoodItems.FRIGHT_ADVENTURE.asItem(), "Fright Adventure", "惊吓！大冒险");
        this.add(NMIFoodItems.GAME_SOUP.asItem(), "Game Soup", "野味杂烩汤");
        this.add(NMIFoodItems.GENSOKYO_BUDDHA_JUMPS_OVER_THE_WALL.asItem(), "Gensokyo Buddha Jumps Over the Wall", "幻想佛跳墙");
        this.add(NMIFoodItems.GENSOKYO_STAR_LOTUS_SHIP.asItem(), "Gensokyo Star Lotus Ship", "幻想星莲船");
        this.add(NMIFoodItems.GIANT_TAMAGOYAKI.asItem(), "Giant Tamagoyaki", "巨人玉子烧");
        this.add(NMIFoodItems.GINKGO_AND_RADISH_PORK_RIB_SOUP.asItem(), "Ginkgo and Radish Pork Rib Soup", "银杏萝卜排骨汤");
        this.add(NMIFoodItems.GLOOMY_FRUIT_PIE.asItem(), "Gloomy Fruit Pie", "忧郁水果派");
        this.add(NMIFoodItems.GLUTINOUS_RICE_BALLS.asItem(), "Glutinous Rice Balls", "汤圆");
        this.add(NMIFoodItems.GOLDEN_CRISPY_FISH_CAKE.asItem(), "Golden Crispy Fish Cake", "金黄酥鱼饼");
        this.add(NMIFoodItems.GRAND_BANQUET.asItem(), "Grand Banquet", "大奢宴");
        this.add(NMIFoodItems.GREEN_BAMBOO_WELCOMES_SPRING.asItem(), "Green Bamboo Welcomes Spring", "翠竹迎春");
        this.add(NMIFoodItems.GREEN_FAIRY_MUSHROOM.asItem(), "Green Fairy Mushroom", "绿野仙菇");
        this.add(NMIFoodItems.GRILLED_HAGFISH.asItem(), "Grilled Hagfish", "烤八目鳗");
        this.add(NMIFoodItems.GRILLED_PORK_RICE_BALLS.asItem(), "Grilled Pork Rice Balls", "炙猪肉饭团");
        this.add(NMIFoodItems.HEART_PORRIDGE_GRUEL.asItem(), "Heart Porridge Gruel", "养心粥");
        this.add(NMIFoodItems.HELL_THRILL_WARNING.asItem(), "Hell Thrill Warning", "地狱激辛警告！");
        this.add(NMIFoodItems.HOLY_WHITE_LOTUS_SEED_CAKE.asItem(), "Holy White Lotus Seed Cake", "圣白莲子糕");
        this.add(NMIFoodItems.HONEY_BBQ_PORK.asItem(), "Honey BBQ Pork", "蜜汁叉烧");
        this.add(NMIFoodItems.HORAI_DAMA_NO_EDA.asItem(), "Horai Dama no Eda", "蓬莱玉枝");
        this.add(NMIFoodItems.HOT_WAFFLES.asItem(), "Hot Waffles", "热华夫饼");
        this.add(NMIFoodItems.HULA_SOUP.asItem(), "Hula Soup", "呼啦汤");
        this.add(NMIFoodItems.LION_HEAD.asItem(), "Lion Head", "狮子头");
        this.add(NMIFoodItems.LONGYIN_PEACH.asItem(), "Longyin Peach", "龙吟桃子");
        this.add(NMIFoodItems.LOOKING_UP_AT_THE_CEILING_FRUIT_PIE.asItem(), "Looking Up at the Ceiling Fruit Pie", "仰望天花板派");
        this.add(NMIFoodItems.LOTUS_FISH_RICE_BOWL.asItem(), "Lotus Fish Rice Bowl", "荷花鱼米盏");
        this.add(NMIFoodItems.LUOHAN_VEGETARIAN.asItem(), "Luohan Vegetarian", "罗汉上素");
        this.add(NMIFoodItems.MAD_HATTER_TEA_PARTY.asItem(), "Mad Hatter Tea Party", "疯帽子茶会");
        this.add(NMIFoodItems.MAGMA.asItem(), "Magma", "岩浆");
        this.add(NMIFoodItems.MAOYU_LAVA_TOFU.asItem(), "Maoyu Lava Tofu", "茂羽岩浆豆腐");
        this.add(NMIFoodItems.MAOYU_TRICOLOR_ICE_CREAM.asItem(), "Maoyu Tricolor Ice Cream", "茂羽三色冰淇淋");
        this.add(NMIFoodItems.MAPO_TOFU.asItem(), "Mapo Tofu", "麻婆豆腐");
        this.add(NMIFoodItems.MILKY_MUSHROOM_SOUP.asItem(), "Milky Mushroom Soup", "奶香蘑菇汤");
        this.add(NMIFoodItems.MOCHI.asItem(), "Mochi", "麻薯");
        this.add(NMIFoodItems.MOLECULAR_EGG.asItem(), "Molecular Egg", "分子蛋");
        this.add(NMIFoodItems.MOONLIGHT_DUMPLINGS.asItem(), "Moonlight Dumplings", "月光团子");
        this.add(NMIFoodItems.MOONLIGHT_OVER_LOTUS_POND.asItem(), "Moonlight Over Lotus Pond", "荷塘月色");
        this.add(NMIFoodItems.MOON_CAKE.asItem(), "Moon Cake", "月饼");
        this.add(NMIFoodItems.MOON_LOVERS.asItem(), "Moon Lovers", "月之恋人");
        this.add(NMIFoodItems.MUSHROOM_GIRLS_DANCE_STEW.asItem(), "Mushroom Girls Dance Stew", "蘑女的舞踏烩");
        this.add(NMIFoodItems.MUSHROOM_MEAT_SLICES.asItem(), "Mushroom Meat Slices", "香菇肉片");
        this.add(NMIFoodItems.NIGIRI_SUSHI.asItem(), "Nigiri Sushi", "握寿司");
        this.add(NMIFoodItems.OEDO_BOAT_FESTIVAL.asItem(), "Oedo Boat Festival", "大江户船祭");
        this.add(NMIFoodItems.OKONOMIYAKI.asItem(), "Okonomiyaki", "御好烧");
        this.add(NMIFoodItems.ONE_HIT_KILL.asItem(), "One Hit Kill", "一击必杀");
        this.add(NMIFoodItems.ORDINARY_SMALL_CAKE.asItem(), "Ordinary Small Cake", "普通小蛋糕");
        this.add(NMIFoodItems.PAN_FRIED_MUSHROOM_MEAT_ROLL.asItem(), "Pan Fried Mushroom Meat Roll", "香煎双菇肉卷");
        this.add(NMIFoodItems.PAN_FRIED_SALMON.asItem(), "Pan Fried Salmon", "煎三文鱼");
        this.add(NMIFoodItems.PEACH_BLOSSOM_GLAZE_ROLL.asItem(), "Peach Blossom Glaze Roll", "桃花琉璃卷");
        this.add(NMIFoodItems.PEACH_BLOSSOM_SOUP.asItem(), "Peach Blossom Soup", "桃花羹");
        this.add(NMIFoodItems.PHOENIX.asItem(), "Phoenix", "不死鸟");
        this.add(NMIFoodItems.PICKLED_CUCUMBERS.asItem(), "Pickled Cucumbers", "腌黄瓜");
        this.add(NMIFoodItems.PIG_DEER_BUTTERFLY.asItem(), "Pig Deer Butterfly", "猪鹿蝶");
        this.add(NMIFoodItems.PINE_NUT_CAKE.asItem(), "Pine Nut Cake", "松子糕");
        this.add(NMIFoodItems.PIRATE_BACON.asItem(), "Pirate Bacon", "海盗熏肉");
        this.add(NMIFoodItems.PLUM_TEA_RICE.asItem(), "Plum Tea Rice", "梅子茶泡饭");
        this.add(NMIFoodItems.POETRY_AND_GINKGO.asItem(), "Poetry and Ginkgo", "诗礼银杏");
        this.add(NMIFoodItems.POISONOUS_GARDEN.asItem(), "Poisonous Garden", "毒瘴花园");
        this.add(NMIFoodItems.PORK_AND_TROUT_SMOKED.asItem(), "Pork and Trout Smoked", "猪肉鳟鱼熏");
        this.add(NMIFoodItems.PORK_RICE.asItem(), "Pork Rice", "猪肉盖浇饭");
        this.add(NMIFoodItems.POTATO_CROQUETTES.asItem(), "Potato Croquettes", "土豆可乐饼");
        this.add(NMIFoodItems.PSEUDO_JIRITAMA.asItem(), "Pseudo Jiritama", "拟尻子玉");
        this.add(NMIFoodItems.PUMPKIN_SHRIMP_CAKE.asItem(), "Pumpkin Shrimp Cake", "南瓜虾盅");
        this.add(NMIFoodItems.RAPUNZEL.asItem(), "Rapunzel", "长发公主");
        this.add(NMIFoodItems.REAL_SEAFOOD_MISO_SOUP.asItem(), "Real Seafood Miso Soup", "真·海鲜味噌汤");
        this.add(NMIFoodItems.RED_BEAN_DAIFUKU.asItem(), "Red Bean Daifuku", "红豆大福");
        this.add(NMIFoodItems.REFRESHING_PUDDING.asItem(), "Refreshing Pudding", "提神布丁");
        this.add(NMIFoodItems.REVERSING_THE_WORLD.asItem(), "Reversing the World", "逆转天地！");
        this.add(NMIFoodItems.RICE_BALL.asItem(), "Rice Ball", "饭团");
        this.add(NMIFoodItems.RISOTTO.asItem(), "Risotto", "意式烩饭");
        this.add(NMIFoodItems.ROASTED_MUSHROOMS.asItem(), "Roasted Mushrooms", "烤蘑菇");
        this.add(NMIFoodItems.SAKURA_PUDDING.asItem(), "Sakura Pudding", "樱花布丁");
        this.add(NMIFoodItems.SALMON_TEMPURA.asItem(), "Salmon Tempura", "三文鱼天妇罗");
        this.add(NMIFoodItems.SASHIMI_PLATTER.asItem(), "Sashimi Platter", "刺身拼盘");
        this.add(NMIFoodItems.SCARLET_DEVILS_CAKE.asItem(), "Scarlet Devil's Cake", "红魔蛋糕");
        this.add(NMIFoodItems.SCONES.asItem(), "Scones", "司康饼");
        this.add(NMIFoodItems.SCREAMING_ODEN.asItem(), "Screaming Oden", "绝叫关东煮");
        this.add(NMIFoodItems.SEAFOOD_MISO_SOUP.asItem(), "Seafood Miso Soup", "海鲜味噌汤");
        this.add(NMIFoodItems.SEA_URCHIN_SHINGEN_PANCAKE.asItem(), "Sea Urchin Shingen Pancake", "海胆信玄饼");
        this.add(NMIFoodItems.SEA_URCHIN_SASHIMI.asItem(), "Sea Urchin Sashimi", "海胆刺身");
        this.add(NMIFoodItems.SECRET_DRIED_FISH.asItem(), "Secret Dried Fish", "秘制小鱼干");
        this.add(NMIFoodItems.SECRET_MUSHROOM_CASSEROLE.asItem(), "Secret Mushroom Casserole", "秘制蘑菇煲");
        this.add(NMIFoodItems.SEVEN_COLORED_YOKAN.asItem(), "Seven Colored Yokan", "七彩羊羹");
        this.add(NMIFoodItems.SHIRAGA_SADAMATSU.asItem(), "Shiraga Sadamatsu", "白鹿贞松");
        this.add(NMIFoodItems.SKINNY_HORSE_DUMPLING.asItem(), "Skinny Horse Dumpling", "瘦马团子");
        this.add(NMIFoodItems.SNOW_WHITE.asItem(), "Snow White", "白雪公主");
        this.add(NMIFoodItems.STEAMED_EGG_WITH_SEA_URCHIN.asItem(), "Steamed Egg with Sea Urchin", "海胆蒸蛋");
        this.add(NMIFoodItems.STINKY_TOFU.asItem(), "Stinky Tofu", "臭豆腐");
        this.add(NMIFoodItems.STRENGTH_SOUP.asItem(), "Strength Soup", "力量汤");
        this.add(NMIFoodItems.SUPERME_SEAFOOD_NOODLES.asItem(), "Supreme Seafood Noodles", "至尊海鲜面");
        this.add(NMIFoodItems.TAICHI_BAGUA_FISH_MAW.asItem(), "Taichi Bagua Fish Maw", "太极八卦鱼肚");
        this.add(NMIFoodItems.TAKETORIHIME.asItem(), "Taketorihime", "辉夜姬");
        this.add(NMIFoodItems.TAKOYAKI.asItem(), "Takoyaki", "章鱼烧");
        this.add(NMIFoodItems.THE_BEAUTY_OF_HAN_PALACE.asItem(), "The Beauty of Han Palace", "汉宫藏娇");
        this.add(NMIFoodItems.THE_DREAM.asItem(), "The Dream", "幽梦");
        this.add(NMIFoodItems.THE_MARS.asItem(), "The Mars", "火星料理");
        this.add(NMIFoodItems.THE_SOURCE_OF_LIFE.asItem(), "The Source of Life", "生命之源");
        this.add(NMIFoodItems.TIANSHI_BRAISED_CHESTNUT_MUSHROOMS.asItem(), "Tianshi Braised Chestnut Mushrooms", "天师板栗焖菇");
        this.add(NMIFoodItems.TOFU_MISO.asItem(), "Tofu Miso", "豆腐味噌");
        this.add(NMIFoodItems.TOFU_POT.asItem(), "Tofu Pot", "豆腐锅");
        this.add(NMIFoodItems.TONKOTSU_RAMEN.asItem(), "Tonkotsu Ramen", "豚骨拉面");
        this.add(NMIFoodItems.TOON_PANCAKES.asItem(), "Toon Pancakes", "香椿煎饼");
        this.add(NMIFoodItems.TWO_HEAVENS_ONE_STYLE.asItem(), "Two Heavens One Style", "二天一流");
        this.add(NMIFoodItems.UDUMBARA_CAKE.asItem(), "Udumbara Cake", "幻昙花糕");
        this.add(NMIFoodItems.UNCONSCIOUS_MONSTER_MOUSSE.asItem(), "Unconscious Monster Mousse", "无意识怪物慕斯");
        this.add(NMIFoodItems.VEGETABLE_SPECIAL.asItem(), "Vegetable Special", "蔬菜特辑");
        this.add(NMIFoodItems.WARM_RICE_BALL.asItem(), "Warm Rice Ball", "温热饭团");
        this.add(NMIFoodItems.WHITE_PEACH_EIGHT_BRIDGE.asItem(), "White Peach Eight Bridge", "白桃八桥");
        this.add(NMIFoodItems.YUNSHAN_COTTON_CANDY.asItem(), "Yunshan Cotton Candy", "云山棉花糖");
        this.add(NMIFoodItems.ZHAJI.asItem(), "Zhaji", "炸脊");
        this.add(NMIDrinkItems.GREEN_TEA.asItem(), "Green Tea", "绿茶");
        this.add(NMIDrinkItems.FRUITY_HIGH_BALL.asItem(), "Fruity High Ball", "果味High Ball");
        this.add(NMIDrinkItems.FRUITY_SOUR.asItem(), "Fruity Sour", "果味Sour");
        this.add(NMIDrinkItems.QI.asItem(), "Qi", "淇");
        this.add(NMIDrinkItems.BEER.asItem(), "Beer", "超ZUN啤酒");
        this.add(NMIDrinkItems.SUN_MOON_STAR.asItem(), "Sun Moon Star", "日月星");
        this.add(NMIDrinkItems.PLUM_WINE.asItem(), "Plum Wine", "梅酒");
        this.add(NMIDrinkItems.TENGU_DANCE.asItem(), "Tengu Dance", "天狗踊");
        this.add(NMIDrinkItems.SCARLET_DEVIL.asItem(), "Scarlet Devil", "猩红恶魔");
        this.add(NMIDrinkItems.GODS_WHEAT.asItem(), "God's Wheat", "神之麦");
        this.add(NMIDrinkItems.OTTER_FESTIVAL.asItem(), "Otter Festival", "水獭祭");
        this.add(NMIDrinkItems.DAWN.asItem(), "Dawn", "水獭祭");
        this.add(NMIDrinkItems.SPARROW_SAKE.asItem(), "Sparrow Sake", "雀酒");
        this.add(NMIDrinkItems.SCARLET_DEVIL_MANSION_BLACK_TEA.asItem(), "Scarlet Devil Mansion Black Tea", "红魔馆红茶");
        this.add(NMIDrinkItems.AFFGADO.asItem(), "Affogato", "阿芙加朵");
        this.add(NMIDrinkItems.RED_MIST.asItem(), "Red Mist", "红雾");
        this.add(NMIDrinkItems.NEGRONI.asItem(), "Negroni", "尼格罗尼");
        this.add(NMIDrinkItems.GODFATHER.asItem(), "Godfather", "教父");
        this.add(NMIDrinkItems.BLESSING_WIND.asItem(), "Blessing Wind", "风祝");
        this.add(NMIDrinkItems.WINTER_BREW.asItem(), "Winter Brew", "冬酿");
        this.add(NMIDrinkItems.FOURTEENTH_NIGHT.asItem(), "Fourteenth Night", "十四夜");
        this.add(NMIDrinkItems.FIRE_RAT_FUR.asItem(), "Fire Rat Fur", "火鼠裘");
        this.add(NMIDrinkItems.GYOKURO_TEA.asItem(), "Gyokuro Tea", "玉露茶");
        this.add(NMIDrinkItems.MOON_ROCKET.asItem(), "Moon Rocket", "月面火箭");
        this.add(NMIDrinkItems.MILK.asItem(), "Milk", "牛奶");
        this.add(NMIDrinkItems.RED_GRAPEFRUIT_JUICE.asItem(), "Red Grapefruit Juice", "红柚果汁");
        this.add(NMIDrinkItems.SODA.asItem(), "Soda", "波子汽水");
        this.add(NMIDrinkItems.ICEBERG_MAPLE_FROZEN_LEMON.asItem(), "Iceberg Maple Frozen Lemon", "冰山毛玉冻柠");
        this.add(NMIDrinkItems.BIG_POPSICLE.asItem(), "Big Popsicle", "“大冰棍儿！”");
        this.add(NMIDrinkItems.DAIGINJO.asItem(), "Daiginjo", "大吟酿");
        this.add(NMIDrinkItems.COFFEE.asItem(), "Coffee", "咖啡");
        this.add(NMIDrinkItems.FAIRY_RAIN.asItem(), "Fairy Rain", "妖精雨露");
        this.add(NMIDrinkItems.PALEO_CREAMY_SMOOTHIE.asItem(), "Paleo Creamy Smoothie", "古法奶油冰沙");
        this.add(NMIDrinkItems.ORDINARY_FITNESS_TEA.asItem(), "Ordinary Fitness Tea", "普通健身茶");
        this.add(NMIDrinkItems.DEMON_SLAYER.asItem(), "Demon Slayer", "鬼杀");
        this.add(NMIDrinkItems.QI_HEALTH.asItem(), "Qi Health", "气保健");
        this.add(NMIDrinkItems.KOMEIJI_ICE_CREAM.asItem(), "Komeiji Ice Cream", "古明地冰激凌");
        this.add(NMIDrinkItems.MANGO_POMELO_SAGO.asItem(), "Mango Pomelo Sago", "杨枝甘露");
        this.add(NMIDrinkItems.QILIN.asItem(), "Qilin", "麒麟");
        this.add(NMIDrinkItems.HEAVEN_AND_EARTH_ARE_USELESS.asItem(), "Heaven and Earth Are Useless", "天地无用");
        this.add(NMIDrinkItems.DRUNK_ACTOR.asItem(), "Drunk Actor", "伶人醉");
        this.add(NMIDrinkItems.DAUGHTER_OF_THE_SEA.asItem(), "Daughter of the Sea", "海的女儿");
        this.add(NMIDrinkItems.DEMONIC_COFFEE.asItem(), "Demonic Coffee", "魔界咖啡");
        this.add(NMIDrinkItems.MOJITO_BURST_BALL.asItem(), "Mojito Burst Ball", "莫吉托爆浆球");
        this.add(NMIDrinkItems.SPACE_BEER.asItem(), "Space Beer", "太空啤酒");
        this.add(NMIDrinkItems.SATELLITE_ICED_COFFEE.asItem(), "Satellite Iced Coffee", "卫星冰咖啡");
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        this.addTranslations();
        this.addItemTranslations();
        Path path = this.output.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                .resolve(NeoMystiasIzakaya.MODID).resolve("lang");
        if (this.locale.equals("en_us") && !this.enData.isEmpty()) {
            return this.save(this.enData, cache, path.resolve("en_us.json"));
        }

        if (this.locale.equals("zh_cn") && !this.cnData.isEmpty()) {
            return this.save(this.cnData, cache, path.resolve("zh_cn.json"));
        }

        return CompletableFuture.allOf();
    }

    private CompletableFuture<?> save(Map<String, String> data, CachedOutput cache, Path target) {
        JsonObject json = new JsonObject();
        data.forEach(json::addProperty);
        return DataProvider.saveStable(cache, json, target);
    }

    public void add(Block key, String en, String cn) {
        this.add(key.getDescriptionId(), en, cn);
    }

    public void add(DeferredBlock<@NotNull Block> key, String en, String cn) {
        this.add(key.get().getDescriptionId(), en, cn);
    }

    public void add(Item key, String en, String cn) {
        this.add(key.getDescriptionId(), en, cn);
    }

    public void add(DeferredItem<@NotNull Item> key, String en, String cn) {
        this.add(key.get().getDescriptionId(), en, cn);
    }

    private void add(String key, String en, String cn) {
        if (this.locale.equals("en_us") && !this.enData.containsKey(key)) {
            this.enData.put(key, en);
        } else if (this.locale.equals("zh_cn") && !this.cnData.containsKey(key)) {
            this.cnData.put(key, cn);
        }
    }

    @Override
    public @NotNull String getName() {
        return "language:" + this.locale;
    }
}
