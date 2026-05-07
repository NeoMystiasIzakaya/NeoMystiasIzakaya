package icu.gensoukyo.neo_mystias_izakaya.datagen;

import com.google.gson.JsonObject;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.NMIDrinkTags;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.NMIFoodTags;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIDrinkItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIFoodItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIIngredientItems;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
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

    private void addTagTranslations() {
        this.addTag(NMIFoodTags.Aquatic, "Aquatic", "水产");
        this.addTag(NMIFoodTags.Aura_Bursting, "Aura Bursting", "燃起来了");
        this.addTag(NMIFoodTags.Chinese, "Chinese", "中华");
        this.addTag(NMIFoodTags.Cultural_Heritage, "Cultural Heritage", "文化底蕴");
        this.addTag(NMIFoodTags.Divine_Punishment, "Divine Punishment", "天罚");
        this.addTag(NMIFoodTags.Dreamy, "Dreamy", "梦幻");
        this.addTag(NMIFoodTags.Economical, "Economical", "实惠");
        this.addTag(NMIFoodTags.Expensive, "Expensive", "昂贵");
        this.addTag(NMIFoodTags.Filling, "Filling", "饱腹");
        this.addTag(NMIFoodTags.Fresh, "Fresh", "鲜");
        this.addTag(NMIFoodTags.Fruity, "Fruity", "果味");
        this.addTag(NMIFoodTags.Fungus, "Fungus", "菌类");
        this.addTag(NMIFoodTags.Good_With_Alcohol, "Good With Alcohol", "下酒");
        this.addTag(NMIFoodTags.Greasy, "Greasy", "重油");
        this.addTag(NMIFoodTags.Grilled, "Grilled", "烧烤");
        this.addTag(NMIFoodTags.Homecooking, "Homecooking", "家常");
        this.addTag(NMIFoodTags.Hot, "Hot", "灼热");
        this.addTag(NMIFoodTags.Japanese, "Japanese", "和风");
        this.addTag(NMIFoodTags.Large_Portion, "Large Portion", "大份");
        this.addTag(NMIFoodTags.Legendary, "Legendary", "传说");
        this.addTag(NMIFoodTags.Meat, "Meat", "肉");
        this.addTag(NMIFoodTags.Mild, "Mild", "清淡");
        this.addTag(NMIFoodTags.Mountain_Delicacy, "Mountain Delicacy", "山珍");
        this.addTag(NMIFoodTags.Peculiar, "Peculiar", "猎奇");
        this.addTag(NMIFoodTags.Photogenic, "Photogenic", "适合拍照");
        this.addTag(NMIFoodTags.Poison, "Poison", "毒药");
        this.addTag(NMIFoodTags.Premium, "Premium", "高级");
        this.addTag(NMIFoodTags.Raw, "Raw", "生");
        this.addTag(NMIFoodTags.Refreshing, "Refreshing", "凉爽");
        this.addTag(NMIFoodTags.Salty, "Salty", "咸");
        this.addTag(NMIFoodTags.Sea_Delicacy, "Sea Delicacy", "海味");
        this.addTag(NMIFoodTags.Signature, "Signature", "招牌");
        this.addTag(NMIFoodTags.Small_Portion, "Small Portion", "小巧");
        this.addTag(NMIFoodTags.Soup, "Soup", "汤羹");
        this.addTag(NMIFoodTags.Sour, "Sour", "酸");
        this.addTag(NMIFoodTags.Specialty, "Specialty", "特产");
        this.addTag(NMIFoodTags.Spicy, "Spicy", "辣");
        this.addTag(NMIFoodTags.Strength_Boosting, "Strength Boosting", "力量涌现");
        this.addTag(NMIFoodTags.Sweet, "Sweet", "甜");
        this.addTag(NMIFoodTags.Vegetarian, "Vegetarian", "素");
        this.addTag(NMIFoodTags.Western, "Western", "西式");
        this.addTag(NMIFoodTags.Wonderful, "Wonderful", "不可思议");

        this.addTag(NMIFoodTags.Boiling_Pot, "Boiling Pot", "煮锅");
        this.addTag(NMIFoodTags.Cutting_Board, "Cutting Board", "料理台");
        this.addTag(NMIFoodTags.Frying_Pan, "Frying Pan", "油锅");
        this.addTag(NMIFoodTags.Grill, "Grill", "烧烤架");
        this.addTag(NMIFoodTags.Streamer, "Streamer", "蒸锅");

        this.addTag(NMIFoodTags.Trend_Popular, "Trend - Popular", "趋势-流行");
        this.addTag(NMIFoodTags.Trend_Unpopular, "Trend - Unpopular", "趋势-厌恶");


        this.addTag(NMIDrinkTags.Beer, "Beer", "啤酒");
        this.addTag(NMIDrinkTags.Bitter, "Bitter", "苦");
        this.addTag(NMIDrinkTags.Chillable, "Chillable", "可加冰");
        this.addTag(NMIDrinkTags.Cocktail, "Cocktail", "鸡尾酒");
        this.addTag(NMIDrinkTags.Dry, "Dry", "辛");
        this.addTag(NMIDrinkTags.Fruity, "Fruity", "水果");
        this.addTag(NMIDrinkTags.Heatable, "Heatable", "可加热");
        this.addTag(NMIDrinkTags.High_Alcohol, "High Alcohol", "高酒精");
        this.addTag(NMIDrinkTags.Liquor, "Liquor", "利口酒");
        this.addTag(NMIDrinkTags.Low_Alcohol, "Low Alcohol", "低酒精");
        this.addTag(NMIDrinkTags.Mid_Alcohol, "Mid Alcohol", "中酒精");
        this.addTag(NMIDrinkTags.Modern, "Modern", "现代");
        this.addTag(NMIDrinkTags.Neat, "Neat", "直饮");
        this.addTag(NMIDrinkTags.No_Alcohol, "No Alcohol", "无酒精");
        this.addTag(NMIDrinkTags.Sake, "Sake", "清酒");
        this.addTag(NMIDrinkTags.Shochu, "Shochu", "烧酒");
        this.addTag(NMIDrinkTags.Soda, "Soda", "气泡");
        this.addTag(NMIDrinkTags.Stimulating, "Stimulating", "提神");
        this.addTag(NMIDrinkTags.Sweet, "Sweet", "甘");
        this.addTag(NMIDrinkTags.Vintage, "Vintage", "古典");
        this.addTag(NMIDrinkTags.Western, "Western", "西洋酒");

    }

    private void addItemTranslations() {
        this.add(NMIIngredientItems.BAMBOO_SHOOT, "Bamboo Shoot", "竹笋");
        this.add(NMIIngredientItems.BINGDI_LOTUS, "Bingdi Lotus", "并蒂莲");
        this.add(NMIIngredientItems.BLACK_SALT, "Black Salt", "黑盐");
        this.add(NMIIngredientItems.BOAR_MEAT, "Boar Meat", "野猪肉");
        this.add(NMIIngredientItems.BROCCOLI, "Broccoli", "西兰花");
        this.add(NMIIngredientItems.BUTTER, "Butter", "黄油");
        this.add(NMIIngredientItems.CHEESE, "Cheese", "芝士");
        this.add(NMIIngredientItems.CHESTNUT, "Chestnut", "板栗");
        this.add(NMIIngredientItems.CHILI, "Chili", "辣椒");
        this.add(NMIIngredientItems.CICADA_SLOUGH, "Cicada Slough", "蝉蜕");
        this.add(NMIIngredientItems.CRAB, "Crab", "螃蟹");
        this.add(NMIIngredientItems.CREAM, "Cream", "奶油");
        this.add(NMIIngredientItems.CREEPING_FIG, "Creeping Fig", "薜荔");
        this.add(NMIIngredientItems.CUCUMBER, "Cucumber", "黄瓜");
        this.add(NMIIngredientItems.DEW, "Dew", "露水");
        this.add(NMIIngredientItems.FLOUR, "Flour", "面粉");
        this.add(NMIIngredientItems.FLOWER, "Flower", "鲜花");
        this.add(NMIIngredientItems.GINKGO_NUT, "Ginkgo Nut", "白果");
        this.add(NMIIngredientItems.GRAPES, "Grapes", "葡萄");
        this.add(NMIIngredientItems.IBERICO_PORK, "Iberico Pork", "黑毛猪肉");
        this.add(NMIIngredientItems.JIGURU_BERRY, "Jiguru Berry", "噗噗呦果");
        this.add(NMIIngredientItems.LAMPREY, "Lamprey", "八目鳗");
        this.add(NMIIngredientItems.LEMON, "Lemon", "柠檬");
        this.add(NMIIngredientItems.LOTUS_SEED, "Lotus Seed", "莲子");
        this.add(NMIIngredientItems.LUNAR_HERB, "Lunar Herb", "月光草");
        this.add(NMIIngredientItems.OCTOPUS, "Octopus", "章鱼");
        this.add(NMIIngredientItems.ONION, "Onion", "洋葱");
        this.add(NMIIngredientItems.PEACH, "Peach", "桃子");
        this.add(NMIIngredientItems.PINE_NUT, "Pine Nut", "松子");
        this.add(NMIIngredientItems.PLUM, "Plum", "梅子");
        this.add(NMIIngredientItems.PREMIUM_TUNA, "Premium Tuna", "极上金枪鱼");
        this.add(NMIIngredientItems.RADISH, "Radish", "萝卜");
        this.add(NMIIngredientItems.RED_BEAN, "Red Bean", "红豆");
        this.add(NMIIngredientItems.RED_TOON, "Red Toon", "香椿");
        this.add(NMIIngredientItems.SALMON, "Salmon", "三文鱼");
        this.add(NMIIngredientItems.SEA_URCHIN, "Sea Urchin", "海胆");
        this.add(NMIIngredientItems.SHRIMP, "Shrimp", "虾");
        this.add(NMIIngredientItems.SNOW_FUNGUS, "Snow Fungus", "银耳");
        this.add(NMIIngredientItems.STICKY_RICE, "Sticky Rice", "糯米");
        this.add(NMIIngredientItems.STRONG_CAPSAICIN, "Strong Capsaicin", "强效辣椒素");
        this.add(NMIIngredientItems.SWEET_POTATO, "Sweet Potato", "地瓜");
        this.add(NMIIngredientItems.TOFU, "Tofu", "豆腐");
        this.add(NMIIngredientItems.TOMATO, "Tomato", "西红柿");
        this.add(NMIIngredientItems.TROUT, "Trout", "鳟鱼");
        this.add(NMIIngredientItems.TRUFFLE, "Truffle", "松露");
        this.add(NMIIngredientItems.TUNA, "Tuna", "金枪鱼");
        this.add(NMIIngredientItems.UDUMBARA, "Udumbara", "幻昙华");
        this.add(NMIIngredientItems.VENISON, "Venison", "鹿肉");
        this.add(NMIIngredientItems.WAGYU_BEEF, "Wagyu Beef", "和牛");

        this.add(NMIFoodItems.ALL_MEAT_FEAST, "All-Meat Feast", "全肉盛宴");
        this.add(NMIFoodItems.PEACH_SHRIMP_SALAD, "Peach & Shrimp Salad", "北极甜虾蜜桃色拉");
        this.add(NMIFoodItems.TEMPURA_PLATTER, "Tempura Platter", "什锦天妇罗");
        this.add(NMIFoodItems.LITTLE_SWEET_POISON, "Little Sweet \"Poison\"", "小小的甜蜜「毒药」");
        this.add(NMIFoodItems.CREAMY_CRAB, "Creamy Crab", "奶油焗蟹");
        this.add(NMIFoodItems.BAKED_SWEET_POTATO, "Baked Sweet Potato", "烤地瓜");
        this.add(NMIFoodItems.PORK_BAMBOO_SHOOTS_STIR_FRY, "Pork & Bamboo Shoots Stir Fry", "竹笋炒肉");
        this.add(NMIFoodItems.BAMBOO_MEAT_POT, "Bamboo Meat Pot", "石锅竹笋炖肉");
        this.add(NMIFoodItems.STEAMED_EGG_BAMBOO_SHOOTS, "Steamed Egg & Bamboo Shoots", "竹筒蒸蛋");
        this.add(NMIFoodItems.DRUNK_SHRIMP_IN_BAMBOO, "Drunk Shrimp in Bamboo", "竹筒烧醉虾");
        this.add(NMIFoodItems.RICE_POWDER_MEAT, "Rice Powder Meat", "竹筒粉蒸肉");
        this.add(NMIFoodItems.IMITATION_BEAR_PAW, "Imitation Bear Paw", "赛熊掌");
        this.add(NMIFoodItems.TWO_FLAVOR_BEEF_HOTPOT, "Two-flavor Beef Hotpot", "牛肉鸳鸯火锅");
        this.add(NMIFoodItems.BEEF_BOWL, "Beef Bowl", "牛肉盖浇饭");
        this.add(NMIFoodItems.BEEF_WELLINGTON, "Beef Wellington", "惠灵顿牛排");
        this.add(NMIFoodItems.KABUTO_STEAMED_CAKE, "Kabuto Steamed Cake", "兜甲蒸糕");
        this.add(NMIFoodItems.BURN_OUT_PUDDING, "Burn-out Pudding", "燃尽布丁");
        this.add(NMIFoodItems.BISCAY_BISCUITS, "Biscay Biscuits", "比斯开湾饼干");
        this.add(NMIFoodItems.SICHUAN_BOILED_FISH, "Sichuan Boiled Fish", "水煮鱼");
        this.add(NMIFoodItems.KABAYAKI_LAMPREYS, "Kabayaki Lampreys", "红烧鳗鱼");
        this.add(NMIFoodItems.PEACH_BRAISED_PORK, "Peach Braised Pork", "蜜桃红烧肉");
        this.add(NMIFoodItems.CLASSIC_STEAK, "Classic Steak", "黄油牛排");
        this.add(NMIFoodItems.HONEYED_CHESTNUT, "Honeyed Chestnut", "蜜饯栗子");
        this.add(NMIFoodItems.CANDIED_SWEET_POTATO, "Candied Sweet Potato", "拔丝地瓜");
        this.add(NMIFoodItems.KITTENS_WATER_PLAY, "Kitten's Water Play", "猫咪戏水");
        this.add(NMIFoodItems.NEKO_MANMA, "Neko-manma", "猫饭");
        this.add(NMIFoodItems.KITTEN_CANELE, "Kitten Canele", "猫咪可露丽");
        this.add(NMIFoodItems.KITTEN_PIZZA, "Kitten Pizza", "猫咪披萨");
        this.add(NMIFoodItems.CHEESE_OMELETTE, "Cheese Omelette", "芝士蛋");
        this.add(NMIFoodItems.CARVED_ROSE_SALAD, "Carved Rose Salad", "凉菜雕花");
        this.add(NMIFoodItems.FRESH_TOFU, "Fresh Tofu", "冷豆腐");
        this.add(NMIFoodItems.RAINBOW_PAN_FRIED_PORK_BUNS, "Rainbow Pan-fried Pork Buns", "华光玉煎包");
        this.add(NMIFoodItems.BOILED_TOFU, "Boiled Tofu", "煮豆腐");
        this.add(NMIFoodItems.CREAMY_VEGETABLE_CHOWDER, "Creamy Vegetable Chowder", "奶油炖菜");
        this.add(NMIFoodItems.CRISPY_SPIRALS, "Crispy Spirals", "脆旋风");
        this.add(NMIFoodItems.DARK_MATTER, "Dark Matter", "黑暗物质");
        this.add(NMIFoodItems.FRIED_CICADA_SLOUGHS, "Fried Cicada Sloughs", "香炸蝉蜕");
        this.add(NMIFoodItems.MISERY_CHEESE_STICKS, "Misery Cheese Sticks", "丧气芝士条");
        this.add(NMIFoodItems.DEW_RUNNY_EGGS, "Dew Runny Eggs", "露水煮蛋");
        this.add(NMIFoodItems.DORAYAKI, "Dorayaki", "铜锣烧");
        this.add(NMIFoodItems.DUMPLINGS, "Dumplings", "水饺");
        this.add(NMIFoodItems.EEL_BOWL_WITH_EGG, "Eel Bowl with Egg", "鳗鱼嫩蛋丼");
        this.add(NMIFoodItems.EGGS_BENEDICT, "Eggs Benedict", "班尼迪克蛋");
        this.add(NMIFoodItems.ENERGY_SKEWER, "Energy Skewer", "能量串");
        this.add(NMIFoodItems.FALLING_BLOSSOMS, "Falling Blossoms", "樱落雪");
        this.add(NMIFoodItems.SCRUMPTIOUS_STORM, "Scrumptious Storm", "幻想风靡");
        this.add(NMIFoodItems.DRAGON_CARP, "Dragon Carp", "鱼跃龙门");
        this.add(NMIFoodItems.NATURES_BEAUTY, "Nature's Beauty", "花鸟风月");
        this.add(NMIFoodItems.FLOWING_SOMEN, "Flowing Somen", "流水素面");
        this.add(NMIFoodItems.FRIED_LAMPREYS, "Fried Lampreys", "炸八目鳗");
        this.add(NMIFoodItems.FRIED_PORK_CUTLET, "Fried Pork Cutlet", "炸猪肉排");
        this.add(NMIFoodItems.PORK_STIR_FRY, "Pork Stir Fry", "炒肉丝");
        this.add(NMIFoodItems.DEEP_FRIED_SHRIMP_TEMPURA, "Deep Fried Shrimp Tempura", "炸虾天妇罗");
        this.add(NMIFoodItems.DEEP_FRIED_TOFU, "Deep Fried Tofu", "油豆腐");
        this.add(NMIFoodItems.TOMATO_FRIES, "Tomato Fries", "炸番茄条");
        this.add(NMIFoodItems.FRIGHT_ADVENTURE, "Heart-Throbbing Surprise!", "惊吓！大冒险");
        this.add(NMIFoodItems.HUNTERS_CASSEROLE, "Hunter's Casserole", "野味加农");
        this.add(NMIFoodItems.BUDDHA_JUMPS_OVER_THE_WALL, "Buddha Jumps Over The Wall", "幻想佛跳墙");
        this.add(NMIFoodItems.STAR_LOTUS_SHIP, "Star Lotus Ship", "幻想星莲船");
        this.add(NMIFoodItems.GIANT_TAMAGOYAKI, "Giant Tamagoyaki", "巨人玉子烧");
        this.add(NMIFoodItems.GOLDEN_RIBS_SOUP, "Golden Ribs Soup", "白果萝卜排骨汤");
        this.add(NMIFoodItems.GLOOMY_FRUIT_PIE, "Gloomy Fruit Pie", "阴郁水果派");
        this.add(NMIFoodItems.TANGYUAN, "Tangyuan", "汤圆");
        this.add(NMIFoodItems.GOLDEN_CRISPY_FISH_CAKES, "Golden Crispy Fish Cakes", "黄金酥鱼饼");
        this.add(NMIFoodItems.DAIMYOS_FEAST, "Daimyo's Feast", "大奢宴");
        this.add(NMIFoodItems.BAMBOO_SPRING, "Bamboo Spring", "翠竹迎春");
        this.add(NMIFoodItems.MUSHROOM_HERB_ROAD, "Mushroom Herb Road", "绿野仙菇");
        this.add(NMIFoodItems.GRILLED_LAMPREY, "Grilled Lamprey", "烤八目鳗");
        this.add(NMIFoodItems.PORK_RICE_BALL, "Pork Rice Ball", "炙猪肉饭团");
        this.add(NMIFoodItems.HEART_WARMING_CONGEE, "Heart-warming Congee", "养心粥");
        this.add(NMIFoodItems.HELL_THRILL_WARNING, "CAUTION!! Hellish Spice!", "地狱激辛警告！");
        this.add(NMIFoodItems.PURE_WHITE_LOTUS, "Pure White Lotus", "圣白莲子糕");
        this.add(NMIFoodItems.CANTONESE_CHAR_SIU, "Cantonese Char Siu", "蜜汁叉烧");
        this.add(NMIFoodItems.HOURAI_BRANCH, "Hourai Branch", "蓬莱玉枝");
        this.add(NMIFoodItems.PANCAKES_WITH_SYRUP, "Pancakes with Syrup", "热松饼");
        this.add(NMIFoodItems.HOT_PEPPER_SOUP, "Hot Pepper Soup", "胡辣汤");
        this.add(NMIFoodItems.LIONS_HEAD, "Lion's Head", "狮子头");
        this.add(NMIFoodItems.DRAGONSONG_PEACH, "Dragonsong Peach", "龙吟桃子");
        this.add(NMIFoodItems.CEILING_LONGING_PIE, "Ceiling-Longing Pie", "仰望天花板派");
        this.add(NMIFoodItems.LOTUS_FISH_LAMPS, "Lotus Fish Lamps", "荷花鱼米盏");
        this.add(NMIFoodItems.BUDDHAS_DELIGHT, "Buddha's Delight", "罗汉上素");
        this.add(NMIFoodItems.MAD_HATTERS_TEA_PARTY, "Mad Hatter's Tea Party", "疯帽子茶会");
        this.add(NMIFoodItems.FUJIS_LAVA, "Fuji's Lava", "岩浆");
        this.add(NMIFoodItems.CUBIC_KEDAMA_VOLCANIC_TOFU, "Cubic Kedama Volcanic Tofu", "毛玉熔岩豆腐");
        this.add(NMIFoodItems.CUBIC_KEDAMA_ICE_CREAM, "Cubic Kedama Ice Cream", "毛玉三色冰激凌");
        this.add(NMIFoodItems.MAPO_TOFU, "Mapo Tofu", "麻婆豆腐");
        this.add(NMIFoodItems.CREAM_OF_MUSHROOM_SOUP, "Cream of Mushroom Soup", "奶香蘑菇汤");
        this.add(NMIFoodItems.MOCHI, "Mochi", "麻薯");
        this.add(NMIFoodItems.MOLECULAR_EGG, "Molecular Egg", "分子蛋");
        this.add(NMIFoodItems.LUNAR_DANGO, "Lunar Dango", "月光团子");
        this.add(NMIFoodItems.MOONLIGHT_OVER_THE_LOTUS_POND, "Moonlight over the Lotus Pond", "荷塘月色");
        this.add(NMIFoodItems.MOON_CAKE, "Moon Cake", "月饼");
        this.add(NMIFoodItems.LUNAR_LOVER_BISCUITS, "Lunar Lover Biscuits", "月之恋人");
        this.add(NMIFoodItems.MUSHROOM_MAIDENS_TIP_TAP_POT, "Mushroom Maiden's Tip Tap Pot", "蘑女的舞踏烩");
        this.add(NMIFoodItems.PORK_MUSHROOM_STIR_FRY, "Pork & Mushroom Stir Fry", "蘑菇肉片");
        this.add(NMIFoodItems.NIGIRI_SUSHI, "Nigiri Sushi", "手握寿司");
        this.add(NMIFoodItems.OEDO_BOAT_FEAST, "Oedo Boat Feast", "大江户船祭");
        this.add(NMIFoodItems.OKONOMIYAKI, "Okonomiyaki", "大阪烧");
        this.add(NMIFoodItems.INSTANTDEATH, "Instant☆Death", "一击☆必杀");
        this.add(NMIFoodItems.ORDINARY_EAT_ME_CUPCAKE, "Ordinary \"Eat Me\" Cupcake", "普通小蛋糕");
        this.add(NMIFoodItems.GOLDEN_TWO_SHROOM_WRAP, "Golden Two-Shroom Wrap", "香煎双菇肉卷");
        this.add(NMIFoodItems.SALMON_STEAK, "Salmon Steak", "香煎三文鱼");
        this.add(NMIFoodItems.PEACH_FLOWER_CRYSTAL_ROLL, "Peach Flower Crystal Roll", "桃花琉璃卷");
        this.add(NMIFoodItems.PEACH_TAPIOCA, "Peach Tapioca", "桃花羹");
        this.add(NMIFoodItems.IMMORTAL_TURKEY, "Immortal Turkey", "不死鸟");
        this.add(NMIFoodItems.PICKLES, "Pickles", "腌黄瓜");
        this.add(NMIFoodItems.INO_SHIKA_CHOU, "Ino-Shika-Chou", "猪鹿蝶");
        this.add(NMIFoodItems.PINE_NUT_CAKE, "Pine Nut Cake", "松子糕");
        this.add(NMIFoodItems.SMOKED_BUCCANEER, "Smoked Buccaneer", "海盗熏肉");
        this.add(NMIFoodItems.PLUM_TEA_RICE, "Plum Tea Rice", "梅子茶泡饭");
        this.add(NMIFoodItems.SCHOLARS_GINKGO, "Scholar's Ginkgo", "诗礼银杏");
        this.add(NMIFoodItems.MIASMA_GARDEN, "Miasma Garden", "毒瘴花园");
        this.add(NMIFoodItems.PORK_TROUT_KEBAB, "Pork & Trout Kebab", "猪肉鳟鱼熏");
        this.add(NMIFoodItems.PORK_BOWL, "Pork Bowl", "猪肉盖浇饭");
        this.add(NMIFoodItems.POTATO_CROQUETTES, "Potato Croquettes", "土豆可乐饼");
        this.add(NMIFoodItems.IMITATION_SHIRIKODAMA, "Imitation Shirikodama", "拟尻子玉");
        this.add(NMIFoodItems.SHRIMP_STUFFED_PUMPKIN, "Shrimp-Stuffed Pumpkin", "南瓜虾盅");
        this.add(NMIFoodItems.LONG_HAIR_PRINCESS, "Long Hair Princess", "长发公主");
        this.add(NMIFoodItems.REAL_SEAFOOD_MISO_SOUP, "Real・Seafood Miso Soup", "真·海鲜味噌汤");
        this.add(NMIFoodItems.RED_BEAN_DAIFUKU, "Red Bean Daifuku", "红豆大福");
        this.add(NMIFoodItems.ENERGY_PUDDING, "Energy Pudding", "提神布丁");
        this.add(NMIFoodItems.REVERSING_THE_WORLD, "Against The World!", "逆转天地！");
        this.add(NMIFoodItems.RICE_BALL, "Rice Ball", "饭团");
        this.add(NMIFoodItems.ITALIAN_RISOTTO, "Italian Risotto", "意式烩饭");
        this.add(NMIFoodItems.ROASTED_MUSHROOM, "Roasted Mushroom", "烤蘑菇");
        this.add(NMIFoodItems.SAKURA_PUDDING, "Sakura Pudding", "樱花布丁");
        this.add(NMIFoodItems.SALMON_TEMPURA, "Salmon Tempura", "三文鱼天妇罗");
        this.add(NMIFoodItems.SASHIMI_PLATTER, "Sashimi Platter", "刺身拼盘");
        this.add(NMIFoodItems.SCARLET_DEVIL_CAKE, "Scarlet Devil Cake", "猩红恶魔蛋糕");
        this.add(NMIFoodItems.SCONE, "Scone", "司康饼");
        this.add(NMIFoodItems.AGONY_ODEN, "Agony Oden", "绝叫关东煮");
        this.add(NMIFoodItems.SEAFOOD_MISO_SOUP, "Seafood Miso Soup", "海鲜味噌汤");
        this.add(NMIFoodItems.URCHIN_RAINDROP_CAKE, "Urchin Raindrop Cake", "海胆信玄饼");
        this.add(NMIFoodItems.SEA_URCHIN_SASHIMI, "Sea Urchin Sashimi", "海胆刺身");
        this.add(NMIFoodItems.SECRET_DRIED_FISH_CRISPS, "Secret Dried Fish Crisps", "秘制小鱼干");
        this.add(NMIFoodItems.SECRET_SAVORY_MUSHROOM_HOTPOT, "Secret Savory Mushroom Hotpot", "秘制鲜菌煲");
        this.add(NMIFoodItems.SEVEN_COLORED_YOKAN, "Seven-Colored Yokan", "七色羊羹");
        this.add(NMIFoodItems.WHITE_DEER_UNYIELDING_PINE, "White Deer, Unyielding Pine", "白鹿贞松");
        this.add(NMIFoodItems.YASHOUMA_DANGO, "Yashouma Dango", "瘦马团子");
        this.add(NMIFoodItems.SHIRAYUKI, "Shirayuki", "白雪");
        this.add(NMIFoodItems.URCHIN_STEAMED_EGG, "Urchin Steamed Egg", "海胆蒸蛋");
        this.add(NMIFoodItems.STINKY_TOFU, "Stinky Tofu", "臭豆腐");
        this.add(NMIFoodItems.POWER_SOUP, "Power Soup", "力量汤");
        this.add(NMIFoodItems.SUPREME_SEAFOOD_NOODLES, "Supreme Seafood Noodles", "至尊海鲜面");
        this.add(NMIFoodItems.EIGHT_TRIGRAM_FISH_MAWS, "Eight Trigram Fish Maws", "太极八卦鱼肚");
        this.add(NMIFoodItems.KAGUYA_HIME, "Kaguya-hime", "竹取姬");
        this.add(NMIFoodItems.TAKOYAKI, "Takoyaki", "章鱼烧");
        this.add(NMIFoodItems.PALACE_OF_THE_HAN, "Palace of the Han", "汉宫藏娇");
        this.add(NMIFoodItems.FAINT_DREAM, "Faint Dream", "幽梦");
        this.add(NMIFoodItems.PLANET_MARS, "Planet Mars", "火星");
        this.add(NMIFoodItems.ORIGIN_OF_LIFE, "Origin of Life", "生命之源");
        this.add(NMIFoodItems.TIANSHIS_STEWED_MUSHROOMS, "Tianshi's Stewed Mushrooms", "天师板栗焖菇");
        this.add(NMIFoodItems.MISO_TOFU, "Miso Tofu", "豆腐味噌");
        this.add(NMIFoodItems.TOFU_STEW, "Tofu Stew", "豆腐锅");
        this.add(NMIFoodItems.TONKOTSU_RAMEN, "Tonkotsu Ramen", "豚骨拉面");
        this.add(NMIFoodItems.TOON_PANCAKE, "Toon Pancake", "香椿煎饼");
        this.add(NMIFoodItems.NITEN_ICHIRYU, "Niten Ichiryu", "二天一流");
        this.add(NMIFoodItems.UDUMBARA_CAKE, "Udumbara Cake", "幻昙花糕");
        this.add(NMIFoodItems.UNCONSCIOUS_YOUKAI_MOUSSE, "Unconscious Youkai Mousse", "无意识妖怪慕斯");
        this.add(NMIFoodItems.VEGETABLE_SALAD, "Vegetable Salad", "蔬菜专辑");
        this.add(NMIFoodItems.PINK_RICE_BALL, "Pink Rice Ball", "温暖饭团");
        this.add(NMIFoodItems.PEACH_YATSUHASHI, "Peach Yatsuhashi", "白桃生八桥");
        this.add(NMIFoodItems.UNZAN_COTTON_CANDY, "Unzan Cotton Candy", "云山棉花糖");
        this.add(NMIFoodItems.HODGEPODGE, "Hodgepodge", "杂炊");

        this.add(NMIDrinkItems.GREEN_TEA, "Green Tea", "绿茶");
        this.add(NMIDrinkItems.FRUITY_HIGH_BALL, "Fruity High Ball", "果味High Ball");
        this.add(NMIDrinkItems.FRUITY_SOUR, "Fruity Sour", "果味Sour");
        this.add(NMIDrinkItems.QI, "Qi", "淇");
        this.add(NMIDrinkItems.BEER, "Beer", "超ZUN啤酒");
        this.add(NMIDrinkItems.SUN_MOON_STAR, "Sun Moon Star", "日月星");
        this.add(NMIDrinkItems.PLUM_WINE, "Plum Wine", "梅酒");
        this.add(NMIDrinkItems.TENGU_DANCE, "Tengu Dance", "天狗踊");
        this.add(NMIDrinkItems.SCARLET_DEVIL, "Scarlet Devil", "猩红恶魔");
        this.add(NMIDrinkItems.GODS_WHEAT, "God's Wheat", "神之麦");
        this.add(NMIDrinkItems.OTTER_FESTIVAL, "Otter Festival", "水獭祭");
        this.add(NMIDrinkItems.DAWN, "Dawn", "水獭祭");
        this.add(NMIDrinkItems.SPARROW_SAKE, "Sparrow Sake", "雀酒");
        this.add(NMIDrinkItems.SCARLET_DEVIL_MANSION_BLACK_TEA, "Scarlet Devil Mansion Black Tea", "红魔馆红茶");
        this.add(NMIDrinkItems.AFFGADO, "Affogato", "阿芙加朵");
        this.add(NMIDrinkItems.RED_MIST, "Red Mist", "红雾");
        this.add(NMIDrinkItems.NEGRONI, "Negroni", "尼格罗尼");
        this.add(NMIDrinkItems.GODFATHER, "Godfather", "教父");
        this.add(NMIDrinkItems.BLESSING_WIND, "Blessing Wind", "风祝");
        this.add(NMIDrinkItems.WINTER_BREW, "Winter Brew", "冬酿");
        this.add(NMIDrinkItems.FOURTEENTH_NIGHT, "Fourteenth Night", "十四夜");
        this.add(NMIDrinkItems.FIRE_RAT_FUR, "Fire Rat Fur", "火鼠裘");
        this.add(NMIDrinkItems.GYOKURO_TEA, "Gyokuro Tea", "玉露茶");
        this.add(NMIDrinkItems.MOON_ROCKET, "Moon Rocket", "月面火箭");
        this.add(NMIDrinkItems.MILK, "Milk", "牛奶");
        this.add(NMIDrinkItems.RED_GRAPEFRUIT_JUICE, "Red Grapefruit Juice", "红柚果汁");
        this.add(NMIDrinkItems.SODA, "Soda", "波子汽水");
        this.add(NMIDrinkItems.ICEBERG_MAPLE_FROZEN_LEMON, "Iceberg Maple Frozen Lemon", "冰山毛玉冻柠");
        this.add(NMIDrinkItems.BIG_POPSICLE, "Big Popsicle", "“大冰棍儿！”");
        this.add(NMIDrinkItems.DAIGINJO, "Daiginjo", "大吟酿");
        this.add(NMIDrinkItems.COFFEE, "Coffee", "咖啡");
        this.add(NMIDrinkItems.FAIRY_RAIN, "Fairy Rain", "妖精雨露");
        this.add(NMIDrinkItems.PALEO_CREAMY_SMOOTHIE, "Paleo Creamy Smoothie", "古法奶油冰沙");
        this.add(NMIDrinkItems.ORDINARY_FITNESS_TEA, "Ordinary Fitness Tea", "普通健身茶");
        this.add(NMIDrinkItems.DEMON_SLAYER, "Demon Slayer", "鬼杀");
        this.add(NMIDrinkItems.QI_HEALTH, "Qi Health", "气保健");
        this.add(NMIDrinkItems.KOMEIJI_ICE_CREAM, "Komeiji Ice Cream", "古明地冰激凌");
        this.add(NMIDrinkItems.MANGO_POMELO_SAGO, "Mango Pomelo Sago", "杨枝甘露");
        this.add(NMIDrinkItems.QILIN, "Qilin", "麒麟");
        this.add(NMIDrinkItems.HEAVEN_AND_EARTH_ARE_USELESS, "Heaven and Earth Are Useless", "天地无用");
        this.add(NMIDrinkItems.DRUNK_ACTOR, "Drunk Actor", "伶人醉");
        this.add(NMIDrinkItems.DAUGHTER_OF_THE_SEA, "Daughter of the Sea", "海的女儿");
        this.add(NMIDrinkItems.DEMONIC_COFFEE, "Demonic Coffee", "魔界咖啡");
        this.add(NMIDrinkItems.MOJITO_BURST_BALL, "Mojito Burst Ball", "莫吉托爆浆球");
        this.add(NMIDrinkItems.SPACE_BEER, "Space Beer", "太空啤酒");
        this.add(NMIDrinkItems.SATELLITE_ICED_COFFEE, "Satellite Iced Coffee", "卫星冰咖啡");
    }

    private void addItemDescTranslations(){
        
        this.addDescription(NMIDrinkItems.AFFGADO, "Ice cream with coffee is great for people who are afraid of bitterness but still need caffeine. §n \"It's not like I can't handle the bitterness, I just need a boost.\"§n ——Patchouli", "将冰淇淋融化在咖啡中的做法，对于怕苦又需要咖啡提神的人来说是再好不过的饮料。§n \"我只是需要提神，并不是怕苦。\" §n——帕秋莉");
        this.addDescription(NMIDrinkItems.BEER, "The product of a side hustle of a great figure who is deeply connected with Gensokyo. Surprisingly popular.", "某位和幻想乡很有渊源的大人物作为副业的产品，虽然是出于兴趣而研制的啤酒，但意外地十分有人气。");
        this.addDescription(NMIDrinkItems.BIG_POPSICLE, "A simple yet substantial large ice cube. Sweet and minty. It can revive you with full HP on a hot summer day! A precious treat from the world of the \"Three Fairies' Hoppin' Flappin' Great Journey!\"", "简单又富有重量感的大冰块，有梦幻的甜蜜和薄荷的调味，夏天解暑、让所有人满血复活神奇冰品！是「三妖精的蹦蹦跳跳讨伐大作」战世界传来的珍贵饮品。");
        this.addDescription(NMIDrinkItems.BLESSING_WIND, "Minty and creamy. More like a dessert than a drink.§n\"That damn Moriya Shrine Maiden brought this drink from the Outside World and named it after herself! Can she even drink in the Outside World? It's horrible anyway, no wonder why she likes it, tisk.\"§n——An Anonymous Miko", "轻松愉快的餐后酒，薄荷和奶油为主的口感。比起酒精饮料更像甜品。§n\"好像是守矢的巫女从外面带过来的配方，堂而皇之地冠了自己的名字!说起来那家伙在外界能喝酒吗?啊，这玩意儿也完全不像酒就是了，怪不得她会喜欢，啧。“§n——不愿透露姓名的巫女");
        this.addDescription(NMIDrinkItems.COFFEE, "A drink made by grinding rare coffee beans into a powder using modern technology. It is a fantastic drink that can boost mental concentration, making it an essential drink for intellectuals and night owls.", "用现代磨制工艺将稀少的咖啡豆磨成粉末制成的饮品，能够奇妙的提升精神和集中力，是脑力劳动者不可或缺的神奇饮料。");
        this.addDescription(NMIDrinkItems.DAIGINJO, "The highest quality of sake with a great flavor and fruity aroma. Avoid exposing it to sunlight, as its color will darken quickly in the sun.", "最高级的清酒，口感极佳而且有水果的香味。必须避光，在太阳的照射下颜色会迅速变深。");
        this.addDescription(NMIDrinkItems.DAUGHTER_OF_THE_SEA, "Legend has it that the Daughter of the Sea eventually turned into foam for love. This cocktail of unique taste might be the tears she shed at that time, returned to the sea on her behalf.", "传说海的女儿最终为爱化为泡沫,这杯有着独特口味的鸡尾酒,或许就是她当时流下的、替她回归海中的眼泪。");
        this.addDescription(NMIDrinkItems.DAWN, "One of the treasures of the Kiketsu Family. It randomly god this name because Otter Spirits placed enemy loot on the floor, resembling a ritual. It's actually a high-quality sake.", "鬼杰组的战利品之一，因为水獭灵把敌组的战利品摆在地上的样子仿佛祭典，于是随便取的名字。实际上好像是相当高级的纯米大吟酿。");
        this.addDescription(NMIDrinkItems.DEMON_SLAYER, "The legendary wine said to make even the oni who can hold a seemingly infinite amount of liquor blackout drunk... Though from what I've seen... they all drink it like it's plain water?! The legend isn't reliable at all.", "传说一杯就能让酒量无底洞一般的贵族醉生梦死的传说之酒......但我见到的是......鬼明明都当作凉白开来喝的？！传说一点都不靠谱啊。");
        this.addDescription(NMIDrinkItems.DEMONIC_COFFEE, "After adding hot coffee to Makai spirit and stirring till well-mixed, the drink is topped with a dollop of fine cream, offering layered texture of milk, wine and coffee. Not only does it have a mellow taste, it also takes the chill off the body.", "在魔界的烈酒里加入热咖啡,搅拌到融化后,再在顶部盖上一团细腻的奶油,由奶香到酒香再到咖啡香层次分明,口感醇厚,还能驱除一身的寒意。");
        this.addDescription(NMIDrinkItems.DRUNK_ACTOR, "Alcohol brewed with peach blossoms. There's a rumor amongst the fairies that if you drink a cup of peach blossom wine and fall asleep in the flowers, you'll encounter a saint of peach blossoms. Though, I'm pretty sure that's just them being drunk.", "桃花酿的酒。妖精之间有着饮一壶桃花酒,醉卧花间,就会遇到桃花仙的传说。这怎么看都是喝醉了吧?");
        this.addDescription(NMIDrinkItems.FAIRY_RAIN, "Fairies often gather dew to create a sweet drink mixed with honey. Since they often forget the product of their labor, it's often taken by animals or humans. Legend says that is has the ability to cure all diseases and even bring back to the dead, but those claims are unfounded.", "妖精们采集露水、和花蜜混合的甘甜饮料。由于妖精们会忘记自己的劳动产品，所以常被动物或者人类采走。传说可以治愈百病甚至起死回生，但似乎是谣言。");
        this.addDescription(NMIDrinkItems.FIRE_RAT_FUR, "A super spicy shochu that can burn your mouth off if it hadn't been served in the Fire Robe. Almost no one can handle its spiciness.", "如果不使用火鼠裘来承装也许就会烧起来的烈酒，几乎无人能承受其辣度的超级辣口烈酒。");
        this.addDescription(NMIDrinkItems.FOURTEENTH_NIGHT, "Perhaps the anticipation of seeing the full moon during the eve is a stronger feeling than viewing it? This premium sake captured this sensation perfectly. A worthy drink for the moon in the Bamboo Forest of the Lost.", "比起十五夜的月亮是满月，也许更想留下十四夜时期待的心情。以这样的感觉酿造的高级清酒，也许只有它才配得上迷途竹林所见到的月亮吧。");
        this.addDescription(NMIDrinkItems.FRUITY_HIGH_BALL, "A simple blend of whiskey, juice, and soda, all commonly available in izakayas. Now everyone can enjoy this drink thanks to the reduced alcohol content.", "居酒屋常见的，使用威士忌和果汁，苏打勾兑的简单调酒，降低了酒精度之后成为了谁都可以享受的饮料。");
        this.addDescription(NMIDrinkItems.FRUITY_SOUR, "A simple blend of shochu, juice, and soda, all commonly available at izakayas. Compared to the Highball, this one is more like a Japanese drink.", "居酒屋常见的，使用烧酒和果汁，苏打勾兑的简单调酒，比起HighBall更加有日式风格。");
        this.addDescription(NMIDrinkItems.GODFATHER, "A classic cocktail, made out of scotch whisky and amaretto. It is so strong that even the average youkai cannot handle it.§n\"My mistress heard that all the ferocious youkai liked this drink, so she asked me to make one for her. Her eyes were wet when she finished it, but she insisted it was great.\"§n ——The Anonymous Chef Maid", "浓烈的北方威士忌和杏仁利口酒混合，非常古典的调酒。口感也相当硬汉，普通的妖怪应付不来。§n\"大小姐听说凶猛的妖怪喜欢喝这种，派我去学，喝完都快哭出来了还要强作威严地说好喝.\"§n——不愿透露姓名的女仆长");
        this.addDescription(NMIDrinkItems.GODS_WHEAT, "This shochu is made from barley blessed by the Goddesses of Autumn at the Youkai Mountain.", "使用妖怪之山上被秋天的神明们所庇佑的大麦所酿造的大麦烧酒。");
        this.addDescription(NMIDrinkItems.GREEN_TEA, "The most common drink for the weak youkai who can't even take a single drop of alcohol.", "最普通的饮料，给一滴酒都不能沾的弱小妖怪准备的。");
        this.addDescription(NMIDrinkItems.GYOKURO_TEA, "Almost the best Japanese tea. It needs to be brewed at a lower water temperature, and offers a sweet and fragrant flavor with a distinct taste.", "几乎是日本茶中最高级的茶叶，需要用较低的水温来冲泡，甘醇飘香，口感独特。");
        this.addDescription(NMIDrinkItems.HEAVEN_AND_EARTH_ARE_USELESS, "Alcohol brewed by Seija Kijin herself, with alcohol content so high it's said to even make an oni drunk. It doesn't have a market in the village, and its not the cheapest drink, so she has to rely on her underlings using scare tactics to sell it.", "鬼人正邪亲自酿造的酒精浓度超级高、据说连鬼都能醉倒的无牌酒。在村子没有销路,价格还不便宜,只能靠手下以半吓半卖的方式销售出去。");
        this.addDescription(NMIDrinkItems.ICEBERG_MAPLE_FROZEN_LEMON, "Melt the Cubic Kedama into a delicious smoothie and add frozen lemonade. There is no telling how many lives it has saved during the hot summer months. Customers who have visited MC Gensokyo say a trip is pointless without trying it.", "仿佛是融化在可口的冰沙中的毛玉，佐以冻柠口味，在炎热的夏季，不知拯救了多少人命，去MC幻想乡旅游过的客人这样评价:没喝过冰山毛玉冻柠，MC幻想乡等于白去。");
        this.addDescription(NMIDrinkItems.KOMEIJI_ICE_CREAM, "The Palace of the Earth Spirits' limited-time commemorative dessert! Cute ice cream designed after the two satori living in the palace, very popular amount youkai in the Underworld.", "地灵殿的限定纪念甜品！以地灵殿的觉妖怪姐妹为原型设计的可爱甜筒，在地底妖怪中有很大的人气。");
        this.addDescription(NMIDrinkItems.MANGO_POMELO_SAGO, "Myouren Temple specialty. Based on the legends where Guanyin would hold a willow branch in her right hand and a bottle of honeydew in the other, bestowing fortune by sprinkling honeydew with the willow branch. Many disciples who come on account of its reputation will drink it at least once.", "命莲寺特产。传说观音菩萨右手持杨枝，左手持盛有甘露的净瓶，用杨柳枝洒下甘露会带来好运。许多慕名而来的信徒都会饮上一杯。");
        this.addDescription(NMIDrinkItems.MILK, "A warm, smooth, and white-colored beverage that is suitable for both children and adults. The benefits are too numerous to mention.", "温润纯白的饮品，无论小孩还是大人都适合饮用，好处多到说不完。");
        this.addDescription(NMIDrinkItems.MOJITO_BURST_BALL, "Utilizing the reaction between sodium alginate solution and calcium chloride solution to form a film of sodium alginate gel, which wraps around the blended mojitos to make a spherical transparent gel. The Mojito Sphere pops with a delicate bite, presenting a double surprise of visual and taste that looks very tempting.", "利用海藻酸钠溶液和氯化钙溶液反应形成一层海藻酸钠凝胶薄膜,将调好的莫吉托酒包表在内,做成一个球状的透明凝胶。咬一口就爆浆,达到视觉和味觉的双重惊喜,看起来非常诱人。");
        this.addDescription(NMIDrinkItems.MOON_ROCKET, "Premium soda made with Lunar Capital's advanced technology. Sparkles like a rocket. All it needs is a slice of lemon and it'll be perfect.", "使用月之都先进技术制作的高级气泡水，迸发的口感有如火箭一般，只需要加一片柠檬就是完美的饮品。");
        this.addDescription(NMIDrinkItems.NEGRONI, "Slightly bitter with an orange aroma and goes down smoothly. Pretty popular recently.§n\"After hearing that it's popular in the Outside World, my mistress asked me to make one. But she couldn't stand the bitterness and kept asking me to add more orange and vermouth. It's basically orange syrup now.\"§n ——The Anonymous Chef Maid", "微苦的橙味为主，香气扑鼻，入口柔顺。最近非常流行。§n\"听说现世很流行，大小姐派我去学，结果受不了苦味让我加大橙子和味美思的剂量，最后完全变成橙汁糖浆了…\"§n——不愿透露姓名的女仆长");
        this.addDescription(NMIDrinkItems.ORDINARY_FITNESS_TEA, "An herbal tea developed by a witch that supposedly can make you lose weight extremely quickly. The label says \"Drink Me\". Although it's a little suspicious, it's surprisingly rich in flavor.", "魔女研制的药茶，据说瘦身功效拔群。标签上写着“喝掉我”，虽然有点可疑但它神奇的丰富味道着实令人惊叹。	");
        this.addDescription(NMIDrinkItems.OTTER_FESTIVAL, "One of the treasures of the Kiketsu Family. It randomly god this name because Otter Spirits placed enemy loot on the floor, resembling a ritual. It's actually a high-quality sake.", "鬼杰组的战利品之一，因为水獭灵把敌组的战利品摆在地上的样子仿佛祭典，于是随便取的名字。实际上好像是相当高级的纯米大吟酿。");
        this.addDescription(NMIDrinkItems.PALEO_CREAMY_SMOOTHIE, "Ice cubes from the kappa's ice bunker are crushed together with milk, sugar, and other ingredients in an iron bucket and stirred quickly. The resulting product is ice cream. The Outside World no longer uses this traditional method, but it is still commonly used in Gensokyo.", "河童们窖藏的天然冰块，和牛乳、糖等配料一起在铁桶里打碎，加速搅拌得到的产物，是古法制造的冰激凌。虽然现世已经不再用这种传统方法制作，但在幻想乡作为特色依旧很受欢迎。");
        this.addDescription(NMIDrinkItems.PLUM_WINE, "Home-brew plum wine from the Human Village. The sweetness hides the alcohol content, and amateurs often pass out without even realizing it.", "人间之里的人类自酿的梅子酒，因为喝起来很甜，所以经常有不了解的生物被它的后劲儿击倒。");
        this.addDescription(NMIDrinkItems.QI, "Fizzy sake with extra bubbles! It's sweet and sour and has a low alcohol content, making it very popular among girls. Can be considered a champagne variant of sake.", "在加入了大量的苏打后，这种起泡清酒酸甜的口感，以及较低的酒精度，使其很受女性的欢迎；可以说是清酒的香槟版本。");
        this.addDescription(NMIDrinkItems.QI_HEALTH, "A knock-off of some energy-boosting drink from the Outside World the kappas are selling. Apparently anyone can make it if you take the main ingredients and change the name... Is this really okay though?", "河童贩卖的外面世界某种提神功能性饮料的山寨品，据说提取了主要成分、换个名字谁都可以制作......但这样真的没问题吗？");
        this.addDescription(NMIDrinkItems.QILIN, "The Taoists utilize techniques from the Outside World to extract only the finest wort for their beer, as such it doesn't have the bitterness of ordinary beers, tasting much smoother and richer.", "道士们采用外界的技术，只提取第一道麦汁酿造的啤酒，因此没有一般啤酒的涩味，口感更纯更顺。");
        this.addDescription(NMIDrinkItems.RED_GRAPEFRUIT_JUICE, "Supposedly a popular drink from the Outside World. Extracted from red grapefruit, it is cool and refreshing, leaving a sweet aftertaste. Perfect for hot summer nights.", "据说是来自外界的人气饮料，用红色柚子这种水果榨汁，尤其是盛夏饮用，健康祛暑，让人回甘无穷。");
        this.addDescription(NMIDrinkItems.RED_MIST, "A signature red wine from Sakuya. Expressive, and always has a faint mist around it. Seems to be produced under an unusual time flow.", "洋馆的女仆特制的红葡萄酒，酒体丰满，因为好像不是正常的时间下酿造出来的，总有一种雾气般的朦胧感。");
        this.addDescription(NMIDrinkItems.SATELLITE_ICED_COFFEE, "Momentarily exposing coffee to a vacuum, it boils instantly from the excessively low air pressure, and the water that is constantly bringing out from the vaporization phenomenon will eventually solidify. This simultaneously boiling and freezing coffee is a Lunarian Capital specialty drink.", "咖啡被瞬间暴露于真空中,会因为过低的气压导致瞬间涕腾,因汽化现象而被不断带走热量的水最终会在沸腾的过程中凝固。这种一边沸腾一边冰冻的咖啡是月都的特色饮料。");
        this.addDescription(NMIDrinkItems.SCARLET_DEVIL, "A blend of vodka, tomato juice, lemon slices, and celery root. The drink consists of a combination of sweet, sour, bitter, and spicy flavors. It is so named because its scarlet-colored liquid reminds people of the Scarlet Devil and her mansion.", "由伏特加、番茄汁、柠檬片、芹菜根混合调制，甜、酸、苦、辣四味俱全。因血红的颜色让人联想到某洋馆的吸血鬼，故得此名。");
        this.addDescription(NMIDrinkItems.SCARLET_DEVIL_MANSION_BLACK_TEA, "The drink has a unique aroma thanks to lemon, bergamot, and a variety of black tea carefully selected by Sakuya. Named after the majestic Scarlet Devil Mansion.", "使用柠檬，佛手柑，和女仆长精心挑选的红茶品种烘焙出带有独特香味的红茶饮料，为了扩张红魔馆的威严，非常普通地以红魔馆命名。");
        this.addDescription(NMIDrinkItems.SODA, "A drink with a magical marble. Push the marble into the drink, and it'll cause the drink to violently carbonate. Drink up all the fizzing cold bubbles in one go and you'll feel the entire summer in your stomach.", "瓶口有弹珠的设计，只要向下按压将弹珠打入汽水内，引起二氧化碳的剧烈反应。此时将瓶中沸腾又冰冷的气泡一饮而尽的话，你会在胃中感受到整个夏天。");
        this.addDescription(NMIDrinkItems.SPACE_BEER, "Brought to space by spacecraft, the brewing yeast has undergone delicate development by scientists, while bringing together space yeast technology and modern beer craftsmanship, this unique beer of refreshing and mellow taste, fine and long-lasting foam, and full of peach flavor is brewed.", "由航天器搭载到太空的酿酒酵母,经过科学家们精心研制,将空间酵母技术与现代啤酒工艺相结合,酿造出这款口感清爽醇厚、泡沫细密持久、饱含桃花香味的独特啤酒。");
        this.addDescription(NMIDrinkItems.SPARROW_SAKE, "Supposedly made by sparrows. Rice is put into hollowed bamboo and when water flows into it, a wonderful sake is made over time. Rumor says drinking this wine makes you dance endlessly. What a cursed delicacy.", "传说中由麻雀酿的酒。将米粒藏入截断的竹中，当竹中有水时，日经月累便成佳酿。据说喝了此酒会舞无休止，是带着“诅咒”的美味呢。");
        this.addDescription(NMIDrinkItems.SUN_MOON_STAR, "With the blessing of the Three Fairies, this sake has become a popular choice at izakayas: mid-alcohol, smooth taste, and affordable price. No wonder why.", "纯米酒，有着妖精的祝福。度数不高，口感柔顺，价格平易近人，是居酒屋受欢迎的选择。");
        this.addDescription(NMIDrinkItems.TENGU_DANCE, "A tengu would dance happily after drinking this delicious sake. I'll debunk this myth if I could open my izakaya at Youkai Mountain even once. Unlike other clear sake, this one has a light amber color.", "传说连天狗喝了也会开心地跳舞的美味清酒，在妖怪之山开一次店就可以验证了吧。不同于其他无色的清酒，带有淡淡的琥珀色。");
        this.addDescription(NMIDrinkItems.WINTER_BREW, "Brewed in the winter, according to the custom of an ancient country. It's golden sweet, refreshing, and has an olive fragrance. It's delicious whether it's hot or cold.", "来自某个古国的习俗，在冬至日酿造的酒，色泽金黄，清甜甘冽，加上桂花的香气，无论冰着喝还是热着喝都非常可口。");
        this.addDescription(NMIFoodItems.LITTLE_SWEET_POISON, "A dessert modeled after the little poisonous doll—Medicine Melancholy. Its cute appearance is accompanied by strange colors, not too dissimilar from the charmingly beautiful poisonous mushrooms in nature, tempting people to harvest them. But in actuality, it's not poisonous, just like Medicine~", "这是专门给小小的毒人偶一一梅蒂欣制作的印象甜品。可爱中带着诡异的色彩,犹如自然、界中徇丽的毒蘑菇勾人采摘。实际是没有毒的,梅蒂欣也是~");
        this.addDescription(NMIFoodItems.ALL_MEAT_FEAST, "A dish consisting of a mountainous pile of premium grilled meat. Simple yet intimidating in its appearance and portion size. It's a recipe that will easily KO diners with a keen sense of smell.", "将各种高级烤肉堆成小山的料理,无论视觉还是分量上都简单暴力,对于嗅觉灵敏的食客是秒杀级的食谱。");
        this.addDescription(NMIFoodItems.PEACH_SHRIMP_SALAD, "A high-class dish made from the freshest shrimp and peaches. Supposedly, they are only served at banquets in the Outside World.", "选用品质最好、肉质最鲜的虾和桃子加工而成的高级料理，据说在外界只有在宴席上才有机会一见。");
        this.addDescription(NMIFoodItems.TEMPURA_PLATTER, "Who said fried food must be birds? In land, in sea or under the ground, anything can become a crispy, mouth-watering delight once smothered in flour and deep fried. Finish with lunar herbs as garnish to subtly neutralize the greasiness of fried foods.", "谁说炸物就必须得是鸟类呢?地上跑的、土里长的、水里游的都可以裹上面粉放到油锅里炸一炸,出锅皆是香味四溢、酥脆爽口。最后以梦幻的月光草为缀,巧妙地中和了炸物拼盘的油腻。");
        this.addDescription(NMIFoodItems.CREAMY_CRAB, "Even before opening the lid, the aroma of the Creamy Crab is already irresistible. However, this is just the beginning. Cracking open a pincer reveals white and juicy flesh that when eaten together with the soup, forms a harmonious combination of delicious flavor.", "奶油焗的螃蟹在没升盖之前就已经香气四溢,然而这香气只是前戏。撕开蟹钳,弹出白嫩饱满的肉质,此时再细细地吸入汤汁,螃蟹固有的鲜味才彻底的融合与释放。");
        this.addDescription(NMIFoodItems.BAKED_SWEET_POTATO, "A timeless street snack popular everywhere, especially during winter times. Who would be able to resist the sight of a warm stove with this yellow-colored goodness? But don't be greedy, eating too much will upset your stomach.", "无论什么时候都大受欢迎的民间小吃。尤其在寒冷的冬天,看到热气腾腾的烤炉,想到那红皮黄额的颜色、热乎甜软的口感,谁能忍得住呢?但不能贪嘴,吃太多容易导致胃腹不适。");
        this.addDescription(NMIFoodItems.PORK_BAMBOO_SHOOTS_STIR_FRY, "The simplest way to cook bamboo shoots, with the greasiness of the pork acting as foil for the delicious freshness of the bamboo shoots.", "最朴素的竹笋的吃法，由猪肉的油光引出的竹笋的鲜美。");
        this.addDescription(NMIFoodItems.BAMBOO_MEAT_POT, "A home-cooked dish featuring beef and bamboo shoot. Meat stewed with fresh and tender bamboo shoot whets the appetite and improves digestion. Plate it with bamboo and you get a wonderful blend of flavor and color.", "以牛肉、竹笋为主要食材的一道家常菜品。鲜嫩的竹笋炖肉具有开胃、促进消化的作用,再以竹子作为摆盘,可谓是色香味俱全。");
        this.addDescription(NMIFoodItems.STEAMED_EGG_BAMBOO_SHOOTS, "A type of egg custard that uses a bamboo barrel as the container, giving the dish a unique taste.", "竹子作为容器蒸出来的茶碗蒸，别有一番风味。");
        this.addDescription(NMIFoodItems.DRUNK_SHRIMP_IN_BAMBOO, "Freshly-caught shrimp marinated in sake, bathed in ice, and topped with spices. A doubled experience combined with both shrimp's umami and sake's fragrance. Delicious!", "把新鲜的虾放进醇香的酒中浸泡,再冰镇后淋上香料食用。既可以尝到虾的鲜香,同时也可以尝到酒的洌香,十分可口。");
        this.addDescription(NMIFoodItems.RICE_POWDER_MEAT, "The most tender parts of pork, cut into sections of bamboo with small holes poked in, filled with ingredients and spice, and steamed under high heat until the unique fragrance of bamboo emerges. If time allows it, adding in water during the marinating process will give the meat a shine and makes it easier to swallow.", "将猪身上最嫩的部位,以新鲜的竹简锯留成节,通小孔,灌上菜料和香料,开大火蒸熟后飘出竹子的独特清香。有时间的条件下,在腌渍肉片的时候加点清水会更加晶莹润口。");
        this.addDescription(NMIFoodItems.IMITATION_BEAR_PAW, "The weirdest dish in Gensokyo! Since I can't defeat a bear, it's not made with real bear paws. But with its unforgettable fragrance and delicious taste, it's a hundred times better than the real thing!", "黑不溜秋的怪异美食之首！香飘万里，让人回味无穷。因为打不过熊，没法直接用熊掌做，但是比真正的熊掌还要鲜美百倍。");
        this.addDescription(NMIFoodItems.TWO_FLAVOR_BEEF_HOTPOT, "Makai pepper spicy broth on the one side, daikon beef bone broth fills the other, a red-and-white specialty beef hotpot. Divided yet coherent together, smells like family.", "一边是魔界辣椒汤底,一边是萝牛骨汤底,兼具红白两种汤汁的特色双锅牛肉。一家人,一个锅,两种口味团团圆圆,仿佛有它就有家的气息。");
        this.addDescription(NMIFoodItems.BEEF_BOWL, "A common family dish. The aromatic sauce drizzled over soft rice blends with the taste of beef. Makes anyone hungry.", "常见的家常菜，看上去颗颗饭粒饱满，淋上的香酱与牛肉的口感融为一体，令人口味倍增。");
        this.addDescription(NMIFoodItems.BEEF_WELLINGTON, "Wrap seasoned beef and truffles in a puffy pastry and bake it. It's buttery, crispy, and juicy. Every ingredient is in perfect harmony. This dish is famously difficult, even in the Outside World.", "将牛排和松露这两种鲜美的食材调味后包裹在酥皮中进行烘焙，让黄油酥皮的香味和牛排蘑菇的鲜美充分融合的极致菜肴。工序繁复，在外界是出了名的难做。");
        this.addDescription(NMIFoodItems.KABUTO_STEAMED_CAKE, "Whether on the surface or the Underworld, the kabuto beetle is a sign of strength and supremacy! This is a dish made in its image to celebrate its prowess!", "无论是地上还是地下,兜角甲虫都是力量和无敌的象征!这是懂憬它的力量而诞生的查甲料理!");
        this.addDescription(NMIFoodItems.BISCAY_BISCUITS, "Trapped on a deserted island, the survivors of a shipwreck had no choice but to mix seawater-soaked flour and cheese into \"batter\" and bake it under the sun. Unexpectedly, it tasted very crispy and delicious, so the recipe was passed down.", "从海难中逃到荒多的幸存者,万般无奈地将已被海水浸湿的面粉和芝士混合成的“面糊”放在阳光下烤,没想到味道变得十分香脆可口。于是这个做法流传了下来。");
        this.addDescription(NMIFoodItems.SICHUAN_BOILED_FISH, "An authentic Chinese Sichuan dish. Fresh fish filet boiled with red chili peppers will result in exploding flavors!", "正宗的四川中华料理。鲜嫩肥美的鱼肉与犹如半天朱霞的辣椒一起翻滚，煮成了一道鱼香四溢，椒味袭人的绝味。");
        this.addDescription(NMIFoodItems.KABAYAKI_LAMPREYS, "One of my signature dishes. A juicy appetizer made by cooking lampreys with special sauces. The smell alone can make you drool.", "本店招牌。将鳗鱼用特殊酱料进行烧制后肉汁四溢，光闻着香味便让人垂涎不已。");
        this.addDescription(NMIFoodItems.PEACH_BRAISED_PORK, "Soft tender meat on top of aromatic peach, a dish you'll never get sick of even if eaten by itself. Cook with a dash of honey to further enhance the flavor. Pairs exceedingly well with alcohol.", "软糯的肉加上香甜的桃子,即使白嘴吃也不会觉得腻。淋上蜂蜜一起翻炒,更是红润添香,非常适合下酒。");
        this.addDescription(NMIFoodItems.BURN_OUT_PUDDING, "The ultimate forbidden dessert with sourness cranked up to the max. Just one is enough to trigger an adrenaline rush and make a youkai dance for an entire night, but after the rush you can't help but say \"I'm all burnt out.\"", "究极加料、一颗就调动起身上包括纪胺肾上腺激素等多神兴奋元素疯狂舞动的禁忌甜食,妖怪食用后可以疯狂舞蹈一整夜,但是兴奋过后会不由地感到“我燃尽了”。");
        this.addDescription(NMIFoodItems.CLASSIC_STEAK, "Simple yet difficult. This dish can change drastically at different temperatures or with different beef. By the way, the mistress of the Scarlet Devil Mansion prefers medium rare.", "简单而复杂，根据火候和食材的选择，呈现出不同感觉到基础西餐。顺带一提红魔馆的那位§m指的应该还是蕾米莉亚·斯卡雷特§r喜欢的是三分熟。");
        this.addDescription(NMIFoodItems.HONEYED_CHESTNUT, "The result of simmering chestnuts in honey. The chestnut's delicate yet rich flavor effectively balances out the sweetness.", "把果子用蜂蜜熬煮之后的成品。栗子里温和而浓郁的味道,可以有效地平衡外在的甜味。");
        this.addDescription(NMIFoodItems.CANDIED_SWEET_POTATO, "Deep fry sweet potatoes until golden brown and coat them in a layer of sugar, then serve after rolling in a greased plate. A delicious snack which won't get stuck in your teeth. But don't get greedy, eating too much will upset your stomach.", "将番薯下锅炸至金黄,再裹上能拉出细丝的糖衣,最后在抹过油的盘子上滚一圈,既可口又不粘牙。但不能贪嘴,吃太多容易导致胃腹不适。");
        this.addDescription(NMIFoodItems.NEKO_MANMA, "Orin said Satori used to make this simple dish for her when she first took her in. The warm taste of velveted flour sprinkled on top of fish has always been remembered fondly by her.", "据说是阿燐刚刚被觉收养时、觉常做给她的简易盖浇饭,面粉勾芡后淋在鱼肉上,那种温柔的味道一直留在燐的记忆里。");
        this.addDescription(NMIFoodItems.KITTEN_CANELE, "Mellow heart covered in crispy caramel coat. An astounding experience is only possible through such changing textures filled with flavors. No wonder they called it \"Angel's Bell\". . . and check out what's in the bell! It's a kitten!", "迷人的焦糖脆外壳与软糯香浓的内心,无论在口感层次变化或者味觉体验都堪称一绝,不愧是“天使的铜铃”一一咦?铃铛里居然还藏了只小猫咪!");
        this.addDescription(NMIFoodItems.KITTEN_PIZZA, "Pizza with various toppings, a perfect balance between nutrition and impressions, especially the rich flavor coming from caramelized onions offering layers of joy for your tongue. However, biting into a cute kitten-shaped pizza has been proven difficult.", "在发酵的圆面饼上面覆盖各种配料烤制而成,完美兼顾了营养和品相,焦糖洋葱的醇香滋味更是为其带来了多重的舌尖享受。可爱的猫咪外形让人有些不忍下嘴呢~");
        this.addDescription(NMIFoodItems.KITTENS_WATER_PLAY, "Kitten-themed stuffed toast. First, scoop out the loaf from the inside, then layer with cream, peach jam, cream again, and cover it with toast. Next, add in the crystally jelly, make it like a wave of water by covering it with cream dyed blue, and finish it with the last piece - a chocolate kitten. Now you have an interesting dish where it looks like a kitten playing on water.", "猫咪主题的吐司盒。把一整个方块吐司挖空,底部涂上一层奶油,放入一层桃子果酱,再涂上一层奶油,铺一片吐司,加入晶莹剔透的白凉粉,最后再用调色奶油映照出蓝色的水面效果,放入巧克力做的猫咪,就成了一副有趣的猫咪戏水图了。");
        this.addDescription(NMIFoodItems.CHEESE_OMELETTE, "Okuu said Satori used to make this dish for her when she first took her in. Creamy rich cheese mixed into an omelette creates and irresistible snack.", "据说是阿空刚刚被觉收养时、觉常做给她的料理,在蛋饼里混入浓香的芝士,让人无法拒绝的小吃。");
        this.addDescription(NMIFoodItems.CARVED_ROSE_SALAD, "Fresh fruits and vegetables carved into the shape of a rose. What makes this dish difficult is not the ingredients, but the requisite knife skills.", "将鲜果蔬菜雕刻成鲜花的模样，虽然材料简单，但却非常考验刀工。");
        this.addDescription(NMIFoodItems.FRESH_TOFU, "An appetizer for cooling off in summer. Simple and refreshing.", "夏天的消暑下酒菜，简单爽口。");
        this.addDescription(NMIFoodItems.RAINBOW_PAN_FRIED_PORK_BUNS, "Pan-fried buns with a rainbow aura! Some hardcore enthusiasts say only pork filling is authentic.", "散发着七彩的气场的高级生煎包。据说有些生煎原教旨主义者尖锐地反对加入猪肉以外食材的做法。");
        this.addDescription(NMIFoodItems.BOILED_TOFU, "A common family dish. In order to make the tofu as tender as possible, the heat must be carefully controlled when cooking.", "常见的家常菜，但也讲究烧制的火候，才能将豆腐的鲜嫩口感得到最大展现。 ");
        this.addDescription(NMIFoodItems.CREAMY_VEGETABLE_CHOWDER, "An easy to make creamy soup. It tastes great regardless of whether you eat it with bread or as is.", "制作家常奶油浓汤，制作方法简单，无论是蘸面包还是当做炖菜来吃都是非常不错的料理。");
        this.addDescription(NMIFoodItems.CRISPY_SPIRALS, "Crush the shell of insects and mix them into noodles for a crunchy and delicious dish. A strange and unique dish with a sense of otherworldly wonder.", "将虫类的甲壳磨成大碎块,拌入面中,吃起来香脆又下火,有一种别样的异世界猎奇感的奇怪料理。");
        this.addDescription(NMIFoodItems.DARK_MATTER, "The embodiment of \"failures.\" An indescribable cluster of mess with a dark aura. No one in their right mind would want to eat it, right?", "烹饪失误、散发着黑色气场的不明物质，不会有人想吃这种东西……吧？ ");
        this.addDescription(NMIFoodItems.FRIED_CICADA_SLOUGHS, "The molted shells of cicadas are used as medicine to clear the throat and improve eyesight. Becomes crispy after frying and is quite popular.", "蝉科的昆虫黑蚱羽化后的蜕壳，可以入药，有利咽开音，明目退翳之效，香炸后口感酥脆，颇受欢迎。");
        this.addDescription(NMIFoodItems.MISERY_CHEESE_STICKS, "A snack popular among gloomy youkai. The uniquely bitter ginkgo and creamy rich cheese blend together to create a distinctly sweet aftertaste. I have no idea why though.", "在一些阴郁的妖怪中流行的零食,在浓郁的芝士中混有白果独有的苦味,能让味觉产生强烈的回甘后味,但我完全无法理解。");
        this.addDescription(NMIFoodItems.DEW_RUNNY_EGGS, "Eggs boiled with dew are sweeter than regular boiled eggs. If you can get the timing right, you can get a tender yolk that breaks with the slightest shake.", "采集了清晨的露珠煮成的蛋，比一般的水煮蛋多出了一种甘甜的味道，为了保持鲜嫩只煮到半熟，蛋黄水嫩得似乎稍加晃动就会流出来。");
        this.addDescription(NMIFoodItems.DORAYAKI, "A confection where read bean paste is sandwiched between two pancake-like patties. Its name is derived from \"dora\", or \"gong\" in Japanese, referring to how it looks like two gongs merged together.", "一种烤制面皮、内置红豆沙夹心的甜点。因由两块像铜锣一样的饼合起来的,故而得名铜锣烧。");
        this.addDescription(NMIFoodItems.DUMPLINGS, "One of the famous recipe from Miss Meiling's hometown from the other side of the ocean! They don't look impressive, but I promise you that they have a lot to show inside! First, you need to repeatedly knead the dough until it's chewy and no longer sticky. Then add whatever ingredients you want in them. Seal them, and then boil them! It's almost magical how delicious they are!", "在海的另一边，红美铃小姐的家乡的著名的食谱，用面粉制作且来的筋道的面皮，在此之中将任意喜欢的食材往里面包入，再往沸腾的热的水那里放入且煮熟，就往超级美味的食物这个给成为了，只要将外表给看的话是朴素无华，内里把世间的宝藏给包着。");
        this.addDescription(NMIFoodItems.EEL_BOWL_WITH_EGG, "Thick slices of eel coated with gourmet soy sauce, topped with a layer of silky raw eggs. Try mixing the egg with rice and giving it a golden look, so that every of your taste buds will feel satisfied!", "厚切的鳗鱼块抹上特调酱汁,再覆盖一层软嫩的生蛋,在食用前将其搅拌得金灿灿的样子,吃下去超级满足!");
        this.addDescription(NMIFoodItems.EGGS_BENEDICT, "Poached eggs on freshly toasted muffins. A great choice for brunch.", "流黄的水波蛋和大口的碳水，是早午餐的常见选择。");
        this.addDescription(NMIFoodItems.ENERGY_SKEWER, "A skewer made of beef, onion, and pumpkin. The onion's tartness and pumpkin's sweetness mitigates the greasy beef, giving the skewer a refreshing taste.", "牛肉搭配洋葱、南瓜烤成的串串。巧妙地利用了洋葱的刺激与南瓜的甜味去除肉质的油腻，食之更加清爽。");
        this.addDescription(NMIFoodItems.FALLING_BLOSSOMS, "A premium sushi dish. The pink tuna upon the white rice resembles sakura petals on white snow, making this an incredibly beautiful dish.", "高级寿司的一种，粉红色的高级生鱼片盖在白米饭上，就如樱花飘落在白雪上，有着不可思议的美感。");
        this.addDescription(NMIFoodItems.SCRUMPTIOUS_STORM, "A dish which requires unwavering focus and a very precise control over the flame to cook the meat into a perfect medium-rare. Its dark red hue laid in the shape of a destructive tornado brings out an oppressive sense of foreboding. Eating it makes you feel like you're on top of the world.", "对火候要求极其苛刻的一道菜,需聚息凝神地将肉品烤至完美的三分熟。暗红的色调以及极具破坏性的龙卷风形状,带来了山海欲来的压迫感。一口下去,有种仿佛征服了天下的快感。");
        this.addDescription(NMIFoodItems.DRAGON_CARP, "A leaping carp on the outside, precious treasures on the inside. The ultimate dish no feline can resist.", "外表是鲤鱼起跳的造型,剖开后尽是梦幻的宝藏,是猫科动物无法抵抗的究极美食。");
        this.addDescription(NMIFoodItems.NATURES_BEAUTY, "A literal manifestation of the phrase \"flower, bird, wind, and moon\", as well as a triangular cake made in Miss Yuuka's image. Its just as elegant as her, but with one of my own feathers on top of it, it should still be a pretty interesting dish. . . Maybe?", "牵丝攀藤地解升“花鸟风月”一词,同时想象着幽香小姐做出来的三角蛋糕。整体风格就像她本人一样风雅,不过上面插了一根我的羽毛,应该还算有趣吧?");
        this.addDescription(NMIFoodItems.FLOWING_SOMEN, "Rather than being tasty, these noodles are more about being fun.", "比起好吃，流水素面更多的是好玩。");
        this.addDescription(NMIFoodItems.FRIED_LAMPREYS, "One of my signature dishes. These bizarre-looking lampreys were once the talk of Gensokyo. After frying, they gain a smooth and crispy texture, making them a very popular dish.", "本店招牌。长相怪异的八目鳗在喜欢尝鲜的幻想乡曾一度成为话题，油炸后爽滑酥嫩，深受大众喜爱。");
        this.addDescription(NMIFoodItems.FRIED_PORK_CUTLET, "A common family dish made by coating pork with flour and deep frying it. Very popular among children.", "常见的家常菜，以猪肉为主要材料，裹以面粉一炸，邻居家的孩子都馋哭了。");
        this.addDescription(NMIFoodItems.PORK_STIR_FRY, "A home dish made using pork, chili pepper, and other ingredients. It has a strong flavor.", "以猪肉作为主要食材制作而成的家常菜，口味偏重。");
        this.addDescription(NMIFoodItems.DEEP_FRIED_SHRIMP_TEMPURA, "An iconic Japanese dish of the Outside World. Fresh shrimps are coated in flour and deep-fried until they are golden and crispy. Just the sight of it is enough to make one drool!", "“天妇罗”又名“天麸罗”。将新鲜大虾暑上面粉做的罗衣,放入油锅炸至金黄酥脆,再控油捞出,隔壁家的童子都馋哭了。");
        this.addDescription(NMIFoodItems.DEEP_FRIED_TOFU, "A common family dish. Supposedly the favorite food of Inari gods and fox youkai.", "常见的家常菜，传说中是稻荷神的狐狸使者最喜欢的食物。");
        this.addDescription(NMIFoodItems.TOMATO_FRIES, "\"Why are potato fries and ketchup forever the norm? I want fries made from tomato! Not only that, I also want the sauce to be made from potato!\"§n-- Seija§nDeep fried tomato covered with flour and drizzled with homemade potato sauce gives it a distinguishing taste.", "”为什么炸土豆条和番茄酱永远是标配?我不仅偏要炸番茄条,我还要独独给它淋上土豆酱!”§n-- 鬼人正邪§n把西红柿裹上面粉后放到油锅炸一炸,出锅后淋上自制土豆酱,尝起来也算是别有一番趣味。");
        this.addDescription(NMIFoodItems.FRIGHT_ADVENTURE, "Mushrooms dyed with udumbara extract surround a treasure chest, like a rainbow patch of flowers. What surprise does this chest hold? Thinking that, you open the chest and out jumps a silly umbrella with a big tongue! Surprised?", "从幻县华中提取色素,给一个个蘑菇伞染上颜色,将之铺在宝箱周围就像被锦花簇拥着。宝箱里究竟藏有什么惊喜呢?怀着这样的想法打开,跳出来的却是伸着大舌头的滑稽的伞!吓一跳了吧一一?\"");
        this.addDescription(NMIFoodItems.HUNTERS_CASSEROLE, "A stew made with vegetables and high-quality Iberico pork. Full of flavor, fragrant yet not greasy. Arguably the best peasant dish.", "用农家蔬菜佐以优质黑毛猪肉炖煮的烩锅，口感饱满，香浓却不油腻，是农家人最高级的大菜。");
        this.addDescription(NMIFoodItems.BUDDHA_JUMPS_OVER_THE_WALL, "A variation of an iconic cuisine from an ancient Eastern civilization. It is said that even a monk would walk away from enlightenment, jump over a wall to taste this dish. It is really that amazing!", "由东方文明古国最强料理改造而来,据说得道的真佛也会因为它的气味夺墙而走,摒弃斋戒,真的是很神奇!");
        this.addDescription(NMIFoodItems.STAR_LOTUS_SHIP, "The pumpkin ship sails into fantasy with a crew of fantastical ingredients on the river of lotus seeds. It's said that anyone who consumes this dish will be spirited away into a dream. As for what kind of dream, that depends on the person.", "以南瓜做的船,承载着如梦似幻的食材,在莲子铺成的河上驶入幻想。据说每每享用完这道料理,都会有如梦方醒的感觉。至于究竟是何种梦境,便是因人而异了。");
        this.addDescription(NMIFoodItems.GIANT_TAMAGOYAKI, "Enlarge a regular tamagoyaki by several times and you get the Giant Tamagoyaki! Popular among the oni, its magnificent presence alone is enough to get people excited!", "将普通的玉子烧做大许多倍,就是这款在鬼族中流行的巨人玉子烧了!其澎湃的存在感让豪迈之人为其燃烧!");
        this.addDescription(NMIFoodItems.GOLDEN_RIBS_SOUP, "A soup that originates from Meiling's homeland. Not only is it the perfect blend of looks, smell, and taste, but it is also very nutritious.  How to Unlock", "来自红美铃老家的㷛汤技巧，色香味俱全，益气补血。");
        this.addDescription(NMIFoodItems.GLOOMY_FRUIT_PIE, "A delectable feast for those who love sour, hot lava for those who don't! A strange dish popular only among a minority of youkai.", "对喜欢酸味的人来说是味蕾的盛宴,但不喜欢酸味的人就是牙齿和舌头的炸弹!只在一些小众的妖怪中流行的奇怪做法。");
        this.addDescription(NMIFoodItems.TANGYUAN, "One of the famous recipe from Miss Meiling's hometown from the other side of the ocean! They don't look impressive, but I promise you that there's a lot of sweetness inside! You need to use sticky rice as flour and make a small sphere, hide some sweet ingredients inside and boil them in hot water, and they can become a super tasty dessert!", "在海的另一边，红美铃小姐的家乡的著名的食谱，用糯米揉且成的小的团，在此之中将甜的食材给包入，再往沸腾的热的水那里放入且煮熟，就往超级甜又可口的小吃成为，只要将外表给看的话是朴素无华，内里把世间的甜蜜给包着。");
        this.addDescription(NMIFoodItems.GOLDEN_CRISPY_FISH_CAKES, "Don't underestimate these \"cakes\" made out of sweet honey and fish paste. When deep fried, they will turn golden brown!", "在鱼馆内加入适量蜂蜜搅拌后,碾压成鱼饼,投入油锅炸至金黄色,随炸随食。");
        this.addDescription(NMIFoodItems.DAIMYOS_FEAST, "All premium ingredients, braised under exquisite heat control, which enhances the flavor further! The meat is tender and juicy. Those who tried it will never forget it!", "奢侈地选用了一系列高级食材炖煮成烩锅，通过火候的精妙控制，将食材之间的特点全部提炼了出来，肉质鲜嫩多汁，香滑入味，令人其味无穷。");
        this.addDescription(NMIFoodItems.BAMBOO_SPRING, "A variety of fresh and tender ingredients steamed inside a bamboo with multiple sections create a dish which sends a message. The fresh, jade-colored bamboo gives guests a new experience in terms of taste and sight in addition to providing a grand welcome for spring.", "将各种鲜嫩的食材放入药节竹简蒸熟,寓意节节高升。而春竹翠绿娇艳的样子,既给客人带来视觉味觉上的新意,还蕴含着迎春的美好寓意。");
        this.addDescription(NMIFoodItems.MUSHROOM_HERB_ROAD, "Essentially a mix of mushrooms and herbs. The name gives a strange sense of wonder to it, however.", "本质上是野菜拌蘑菇,但因为这个名字多了一些奇妙的童话感。");
        this.addDescription(NMIFoodItems.GRILLED_LAMPREY, "One of Mystia's signature dishes. I wanted to break the stereotype that \"restaurants with red lanterns all sell grilled birds.\" Did you know that lampreys can effective treat night blindness? They used to be a rare delicacy.", "本店招牌。为了打破红灯笼店就是烤鸟肉店的成见，特意选择了对夜盲症有奇效的八目鳗，据说在过去还被视作珍宝。");
        this.addDescription(NMIFoodItems.PORK_RICE_BALL, "A common and affordable rice ball. By adding grilled pork to the rice, it gains a chewy texture and a delicious new taste.", "常见的平价饭团，在饭团中放入烤制后的猪肉，为其增加了一份香浓的嚼劲。");
        this.addDescription(NMIFoodItems.HEART_WARMING_CONGEE, "Tremella congee with lotus seed. Slightly sweeter than regular congee. Moisten the throat with no aftertaste. A comfort food full of nutrients that are good for the heart and mind.", "口味偏甜、营养丰富的银耳莲子粥。滋润而不腻滞,还具有养心·安神的功效。");
        this.addDescription(NMIFoodItems.HELL_THRILL_WARNING, "The spiciest of spicy beef curries! They say only a fool or someone with extremely high tolerance would try this dish!", "超超超级辣加倍的牛肉咖喱饭!据说只有拥有极限忍时力的人和傻瓜才会尝试这道料理!");
        this.addDescription(NMIFoodItems.PURE_WHITE_LOTUS, "Shell and core a fresh lotus seed, boil until soft, add butter and flour, mix until even, stir-fry and imprint the pattern of a lotus with a mold. A pure, white lotus.", "将新鲜莲子剥壳去芯,煮至软烂,再将黄油与面粉搅拌均匀,最后混合翻炒,用磨具压出美丽的莲花图案,看起来神圣洁白。");
        this.addDescription(NMIFoodItems.CANTONESE_CHAR_SIU, "A special dish from Meiling's homeland. While making it is hard, the unique and unforgettable taste made it all worth it.", "来自红美铃老家的特殊做法，制作工序有点繁复，但是口感独一无二，令人难忘。");
        this.addDescription(NMIFoodItems.HOURAI_BRANCH, "To put it simply, it's just a skewer with all kinds of premium meat.", "简单地说就是使用竹签串起各种高级肉类，一口吃个饱的料理。但是在辉夜小姐的胁迫下，成为了高级的冠名料理。");
        this.addDescription(NMIFoodItems.PANCAKES_WITH_SYRUP, "A simple breakfast item. Simply heat the prepared batter until done. Add honey, and voilà!", "早餐的简单选择，将准备好的面糊煎熟，浇上蜂蜜就可以吃了。");
        this.addDescription(NMIFoodItems.HOT_PEPPER_SOUP, "Ingredients include many herbs used in Chinese medicine, black pepper, and chili pepper, then brewed in beef bone broth. One spoon is enough to defeat sleepiness.", "由多种天然中草药按比例配制的汤料,再加入胡椒和辣椒,又用骨头汤做底料的牛肉胡辣汤,喝一口就能驱散瞌睡虫。");
        this.addDescription(NMIFoodItems.LIONS_HEAD, "The go-to dish of every wine lover! The wild and majestic head of a lion accompanied by a strong liquor makes it an essential part of an oni's breakfast!", "爱酒之人无不喜欢的基础下酒菜!气派又野性的狮子头配上烈性的酒,是鬼族起床的早餐!");
        this.addDescription(NMIFoodItems.DRAGONSONG_PEACH, "Essentially, it's about making a peach anew with peach. Mince the peach, then recreate the shape of the peach using the minced peach. On the surface, this may seem to be a simplistic approach, but the actual making process is insanely complex and delicate.", "本质上就是用桃子来制作桃子。将桃子作为原材料打碎,最后再还原出最初始的形状,从表面来看似乎是贯彻了一种返璞归真的理念,但制作过程极其繁琐考究。");
        this.addDescription(NMIFoodItems.CEILING_LONGING_PIE, "The head of a fish juts out through the pie, as if it were looking at the Underworld's ceiling. A dish filled with despair, in contrast to Hell.", "在水果派里探出一个鱼头,仿佛看着地底的天花板,充满了与地狱相衬的绝望气息。");
        this.addDescription(NMIFoodItems.LOTUS_FISH_LAMPS, "A snow-white dish adorned with dark green mint leaves, crystal dew rolls around in the leaves, complimented by a white lotus in the center to give it a \"hermit-like\" touch. Each lotus lamp consists of a fresh pink lotus petal as the base, carrying an ensemble of mashed tuna and lotus seeds. Healthy and delicious.", "洁白的盘中铺上碧绿的荷叶,叶上还滚动着晶莹的水珠,盘中央一朵白荷则增添了“仙气”。每一盏里都以鲜嫩的粉色荷花花瓣为底,盛着极上金枪鱼、莲子组成的“鱼米盏”,又好吃又健康。");
        this.addDescription(NMIFoodItems.BUDDHAS_DELIGHT, "A vegetarian dish from Buddhist cuisine. It's said that authentic Buddha's Delight is made with 18 different ingredients with complex and elaborate procedures. The finished dish is a colorful blend of elegance, with a light and fragrant taste. One could say this is the most luxurious dish in Buddhist cuisine.", "源自佛门的斋菜。传闻正宗的罗汉斋用十八种原料制成,工序复杂考究,成菜色泽缤纷雅致,味道清淡香郁。堪称佛门最奢华的一道素菜。");
        this.addDescription(NMIFoodItems.MAD_HATTERS_TEA_PARTY, "Dessert takes inspiration from Makai's tea party. At first glance, it's simply a teapot made of chocolate, but inside the teapot, it's filled with layered cream cakes, decorated with mushroom and broccoli, just like the candy house from the fantasy. A dessert of happiness, filled with dreams and mystery.", "以魔界茶会为印象制作的甜品。表面看起来是一个巧克力做的茶壶,茶壶的肚子里是层层堆叠的奶油蛋糕,周围装饰着蘑菇和西蓝花,就像某个故事里的巧克力房子一样,是充满了梦幻和不可思议的幸福甜品。");
        this.addDescription(NMIFoodItems.FUJIS_LAVA, "A stew made from premium beef and truffles. Super hot and spicy! Got the name because the stew is bubbling hot like lava. A non-spicy version also exists for weaklings.", "用高级牛肉和松露炖煮而成的烩锅，最初以麻辣为特色，因炖煮中冒出的气泡如岩浆而得名，款款而起的香味更是让人食指大动。改良后也增加了不辣的版本。");
        this.addDescription(NMIFoodItems.CUBIC_KEDAMA_VOLCANIC_TOFU, "Looks like it's on fire! A famous delicacy in MC Gensokyo, attracting many to try its tongue-tingling flavor.", "方形的火山毛玉造型，仿佛着了火的熔岩豆腐，是MC幻想乡非常著名的特色料理，受到喜欢舌苔刺激的人的追捧。");
        this.addDescription(NMIFoodItems.CUBIC_KEDAMA_ICE_CREAM, "Very appealing from color to flavor. It can't be missed when traveling in MC Gensokyo.", "方形的三色毛玉冰淇淋，从颜色到口味上都非常惹人喜爱。去MC幻想乡旅游时，几乎是人手一份的招牌甜品。");
        this.addDescription(NMIFoodItems.MAPO_TOFU, "A Chinese dish famous in Japan. Be careful, it's super spicy and addictive when you eat it with rice!", "在日本很有名的中华料理。使用独特的豆腐烹饪技巧烹制而成的辛辣料理，用它来拌饭吃可是会上瘾的哦～");
        this.addDescription(NMIFoodItems.CREAM_OF_MUSHROOM_SOUP, "A wonderful soup with a rich creamy base that you can't stop eating once you taste it. Easy to make, the ingredients are readily available. Oh, it's also very nutritious.", "浓郁的奶香汤底煮出来的魔性之汤,尝下第一口就无法停下来。制作简单、材料随处可见,还拥有极高的营养价值。");
        this.addDescription(NMIFoodItems.MOCHI, "The most simple type of mochi dumplings. A Japanese snack that everyone likes.", "最普通的糯米团子，大家都喜欢的和风甜食。");
        this.addDescription(NMIFoodItems.MOLECULAR_EGG, "Egg white made of less watery firm tofu, with egg yolk made of mellow pumpkin, locked in an eggshell made of chocolate, perfectly recreating both the shape and texture of a chicken egg. An idea only a genius would've thought of.", "选用水分较少的北豆腐作为蛋白,甜糯的南瓜作为蛋黄,再用白巧克力做成蛋壳,还原出鸡蛋的模样与口感,让人意想不到呢。");
        this.addDescription(NMIFoodItems.MOON_CAKE, "Ei??Why you got it....Have fun with the game!", "诶 ?! 这是什么?你为什么会获得这个?嗯。。。祝你玩得愉快");
        this.addDescription(NMIFoodItems.LUNAR_LOVER_BISCUITS, "After hearing the popularity of \"Koibito\" cookies in the Outside World, Eientei invented their own.", "外界似乎很流行使用□□恋人作为地区伴手礼，永远亭也不甘落后潮流，推出了自己的版本！");
        this.addDescription(NMIFoodItems.LUNAR_DANGO, "A special Eientei mochi. They \"taste like moonlight\" because they are made with the rare moonlight grass.", "永远亭特产改良的麻薯团子，加入了高级食材月光草，造型可爱的同时，还有“月光一样的口感”。");
        this.addDescription(NMIFoodItems.MOONLIGHT_OVER_THE_LOTUS_POND, "Refreshing grape-flavored oolong tea jelly, dressed in white cream mousse! The collision of cream's silkiness and grape's sharpness creates a new dimension of flavors. The Shine Muscat dressed across the jelly fills the mouth with a delicate, fruity smell. The interesting arrangement of raindrops on the lotus pad adds even more aesthetics to the dish.", "清爽细腻的葡萄乌龙冻,色色的是奶油慕斯!奶油的绵密与葡萄的清爽在舌尖碰撞出別样滋味,茶冻间点缀片片青提,馥郁果香充盈唇齿。菏泽上的小露珠也相当有趣~");
        this.addDescription(NMIFoodItems.MUSHROOM_MAIDENS_TIP_TAP_POT, "A dish where mushrooms are the protagonist, accompanied by a variety of fresh and tender ingredients. Its golden glow cannot be ignored, and its fragrant taste can instantly evaporate a person's soul.", "蘑菇为主角,佐以各种鲜嫩食材的强力料理,金灿灿的光芒散发着强烈的存在感,香浓的口感可以瞬间蒸发人的灵魂,一发沉沦。");
        this.addDescription(NMIFoodItems.PORK_MUSHROOM_STIR_FRY, "A basic home-cooked dish: just mushrooms and sliced pork stir-fry in a pan.", "将蘑菇和肉切片后，混入锅里一起炒，是非常基本的家庭料理。");
        this.addDescription(NMIFoodItems.NIGIRI_SUSHI, "One of the most quintessential Japanese dishes. Freshly sliced fish topped over hand-pressed rice balls. Filling and delicious. No wonder why it has a long history behind it.", "日本最传统的料理之一。将鱼切片后盖在手握的饭团上，解饿又鲜美，拥有很长的历史。");
        this.addDescription(NMIFoodItems.OEDO_BOAT_FEAST, "With its fabulous festival boat donned with sashimi and kept fresh by the surrounding icy mist, this dish is absolutely guaranteed to be the focus of the party!", "用华丽的祭典船造型、摆满上好的鱼刺身、周围散发着保鲜而制作的冰雾,是真真真正的宴会的焦点!");
        this.addDescription(NMIFoodItems.OKONOMIYAKI, "A popular street food of the Outside World. Made by mixing batter and various other ingredients and cooked on a flat iron grill. Crisp and rich, it is loved by many for its versatility and affordability.", "听说的为根据，面糊和各种各样的食材给使用且混合之后在铁板烧制而成的，又脆又香又丰富，万变和平价的特征为因被所有人喜欢涨了。");
        this.addDescription(NMIFoodItems.INSTANTDEATH, "A super skewer with additional spices and ingredients added to further stimulate the tasted buds. Okuu gave it a really imposing name, which resulted in its inexplicable popularity among wild youkai.", "加入了极为刺激的食材和辅料,对味觉的刺激满点的超级烤串,阿空亲自为它起了一个中二度满满的名字,结果在保持野性的妖怪中莫名的有人气。");
        this.addDescription(NMIFoodItems.ORDINARY_EAT_ME_CUPCAKE, "A small snack containing a surprising amount of calories. Supposedly can make one stronger. It says \"Eat Me\" on it, which is very tempting. Be careful if you eat too many, you might gain weight...", "有着惊人热量的小点心,据说能迅速强健体魄。上面写着“吃掉我”,非常诱人。吃多了估计会长胖吧 ……");
        this.addDescription(NMIFoodItems.GOLDEN_TWO_SHROOM_WRAP, "A common but delicious delicacy with a balanced mix of meat and vegetables. Two different mushrooms wrapped in carefully selected meat creates a layered flavor for the taste buds.", "荤素搭配均匀,味道也很好的常见佳肴。用两种不同口感的菇类,包裹着精挑细选的嫩肉,给味蕾带来层次感十足的享受。");
        this.addDescription(NMIFoodItems.SALMON_STEAK, "Made by pan-frying a whole salmon until the skin is crispy and the inside is tender. I heard they use asparagus in the Outside World, but I guess we'll make do with fresh bamboo shoots. Just call it Gensokyo-style!", "将整块带皮的三文鱼煎至外焦里嫩，配上鲜嫩的竹笋——不过外界这个菜谱一般是芦笋，这也算是幻想乡式的融合菜吧。");
        this.addDescription(NMIFoodItems.PEACH_FLOWER_CRYSTAL_ROLL, "Cute and pink peach flowers turned into crystally jelly, thanks to gelation technology. The mellow red bean paste inside preserves the sweet romance of the season.", "通过胶凝化技术,把粉嫩可爱的桃花制成晶莹剔透的琉璃果冻外皮,裹着糯叽叽的豆沙馅,记录季节的浪漫与甜蜜。");
        this.addDescription(NMIFoodItems.PEACH_TAPIOCA, "A recipe from Heaven, made from fresh peach and morning dew. Not only is this dish sweet and fragrant, but it's also photogenic and healthy.", "来自天上的配方，采摘新鲜的桃花，配以清晨的甘露水煮而成，不仅芳香清甜，而且具有祛病美容的神奇功效。");
        this.addDescription(NMIFoodItems.IMMORTAL_TURKEY, "\"Huh, so that's what's inside that turkey's belly.\" -- Kaguya Houraisan§nA flour turkey with everyday ingredients as filling and honey brushed over. Has a crispy surface and juicy interior.", "用面粉烘焙出烤火鸡的形状，在表面刷上蜂蜜，肚子里塞满食材，进行充分的烘烤。出炉的假烤鸡有着香脆的外皮和多汁的内馅。");
        this.addDescription(NMIFoodItems.PICKLES, "By cutting and marinating cucumbers in brine and other seasonings, they gain a magical and mouth-watering taste.", "将黄瓜切段后用盐等调味腌制,出现了神奇而难以抗拒的风味。");
        this.addDescription(NMIFoodItems.INO_SHIKA_CHOU, "Inspired by the hanafuda combo, pork and venison are stewed in clear soup and seasoned with flowers to bring out the freshness of the ingredients.", "脱胎于花礼的猪鹿蝶牌型，将猪肉和鹿肉清炖，佐以花朵引出食材本身鲜味的精致料理。");
        this.addDescription(NMIFoodItems.PINE_NUT_CAKE, "A medicinal dish made with glutinous rice as the main ingredient and pine nuts as supplement. The powder of pine nut case is soft, delicate and delicious, in addition to a refreshing pine nut flavor. A favorite among Taoist priests.", "以糯米为主料、辅以松子制作的药膳。松子糕的粉质细腻,柔软可口,并有清香的松子味,深受道士们喜爱。");
        this.addDescription(NMIFoodItems.SMOKED_BUCCANEER, "Apparently the word \"buccaneer\" originated from smoking meat over an open fire. Caribbean natives would smoke meat to sell to caribbean pirates, which sounds as strange as eggs on scrambled eggs.", "据说“海盗”这个词的来源是在明火上烹制的熏肉。加勒比本地人就是用这种做法来处理肉类,然后卖给海盗。听起来就和米饭盖浇米饭一样奇怪。");
        this.addDescription(NMIFoodItems.PLUM_TEA_RICE, "Piping hot white rice soaked in broth! The broth used in tea over rice is usually infused tea, roasted tea, or sweet and aromatic fish stock. A plum sits on top, adding color to this otherwise simple dish.", "用汤汁和热腾腾的白饭制作的茶泡饭!茶泡饭所用的汤汁通常为煎茶、烘焙茶或富有柴鱼香气的高汤,最后加入的梅子给这道素淡的菜品增添了一抹艳色。");
        this.addDescription(NMIFoodItems.SCHOLARS_GINKGO, "Named after the ginkgo trees in the Temple of Confucius, they are fragrant, sweet, chewy, and can even cure coughing and hangovers.", "以选用孔庙“诗礼堂”前银杏树所结果实烹制而得名，清香甜美，柔韧筋道，可解酒止咳。");
        this.addDescription(NMIFoodItems.MIASMA_GARDEN, "A hodgepodge of various \"poisonous\" ingredients. Precise handling of each ingredient allow for the retention of \"poison\" in the ingredients to varying degrees, letting every guest experience the exact level of thrill they wish for.", "用各种“毒食材”混合炖煮的杂炊。通过细致而精巧的食材处理手段,能够不同程度地保留食材中的“毒性”,让不同需求的客人均能体验到他们想要的刺激。");
        this.addDescription(NMIFoodItems.PORK_TROUT_KEBAB, "A dish made with smoked pork and trout. Simple to make and absolutely delicious.", "把猪肉鳟鱼放在一起熏制而成，是简单好吃的肉食料理，也比较能保存。");
        this.addDescription(NMIFoodItems.PORK_BOWL, "A common family dish. The aromatic sauce drizzled over soft rice blends with the taste of the pork. Makes anyone hungry.", "常见的家常菜，看上去颗颗饭粒饱满，淋上的香酱与猪肉的口感融为一体，令人口感倍增。");
        this.addDescription(NMIFoodItems.POTATO_CROQUETTES, "Made primarily from potatoes, these croquettes are crispy on the outside and soft on the inside. It's a popular fried food.", "主要由土豆制成的，外表酥脆内在绵软可口，在油炸类食品中有较高的人气。");
        this.addDescription(NMIFoodItems.IMITATION_SHIRIKODAMA, "It is said that the shirikodama is the tailbone of a human and kappa's favorite food. In the past, it was taken from humans through a bloody method. Over time, we created something similar, though it comes at a premium cost.", "据说尻子玉是人类进化后残存的尾骨,是河童一族最喜欢的食物。从前用血腥的方式从人类那里夺取,但是随着时代的进步、已经有料理的方式来替代,只是成本昂贵。");
        this.addDescription(NMIFoodItems.SHRIMP_STUFFED_PUMPKIN, "Hollow out a small pumpkin and fill it with shrimp and tofu. Steam it, and you got yourself a sweet, delicious, and very healthy meal.", "掏空小南瓜,用鲜嫩的虾肉和豆腐填充,再进行蒸制,香甜可口,又非常健康。");
        this.addDescription(NMIFoodItems.LONG_HAIR_PRINCESS, "Delicately plated spaghetti with shrimp and pumpkin. The sweetness and mellow texture of low-fat low-carole pumpkin make a great replacement for butter, enriching both flavor and nutrition.", "摆盘精致的虾仁南瓜意面。南瓜蒸熟后又甜又糯,用它代替奶油做出来的意面低脂低卡,味道浓郁,营养十足!");
        this.addDescription(NMIFoodItems.REAL_SEAFOOD_MISO_SOUP, "Same old miso soup, but with REAL seafood this time! Savory and without the fish stink.", "选用新鲜鳟鱼与海带㷛煮而成的味增汤，浓浓的鲜味四处飘荡，鲜而不腥。");
        this.addDescription(NMIFoodItems.RED_BEAN_DAIFUKU, "Yummy red bean paste wrapped in a skin of gluttonous rice. The amount of filling is just as much if not more than its wrapping, giving it its smooth and round appearance. In Japanese, \"fuku\" is the word for \"luck\", so it symbolizes luck and good fortune.", "用糯米制成的外皮,里头包着饱满的带皮红小豆馅。馅料的量跟饼皮的量一样甚至更多,使得大福的外型圆浑有致。据说大福就因为这样的外型而被称为“大腹饼”,后人取其吉祥的谐音改称“大福”。");
        this.addDescription(NMIFoodItems.ENERGY_PUDDING, "A pudding which was made terrifyingly sour, boosting one's energy even more. Just one bite is enough to rid a tired mind of any fatigue. But due to how intense the ingredients are, it's not good for oral health.", "大大提升了酸度的可怕布丁,改良了提神的效果,只要吃一口,就能让困倦的身体打起精神,但因为其用料过于凶猛,很不利于口腔健康 ……");
        this.addDescription(NMIFoodItems.REVERSING_THE_WORLD, "Molecular gastronomy recipe made with revolutionary technology; said to have originated from the moon. Many unknowns were encountered during the cooking process, therefore a certain amount of redesigning had to be done. It ultimately became an \"earthly\" dish, fusing what is perceived to be \"elegant\" and \"conventional\", as well as an embodiment of Seija's wish to mush up the world.", "使用革新技术制作的分子料理,据说是来自月都的食谱。在制作过程中存在许多无法理解之处,所以经过一定程度的再创作,最终成为这样一个结合了世人眼中的“雅”与“俗”之物的地上料理,也寄托着正邪想要搅混天下的意愿。");
        this.addDescription(NMIFoodItems.RICE_BALL, "The simplest rice ball, made using nothing more than seaweed and rice. Convenient and cheap.", "最普通的饭团，加点海带随便捏捏就可以了，超便捷的经典。");
        this.addDescription(NMIFoodItems.ITALIAN_RISOTTO, "After frying the ingredients, uncooked rice is poured in, which soaks the aroma into the rice. Supposedly originated from a peninsula from the Outside World.", "将食材炒熟之后倒入生米，充分混合米粒和食材香味的外界某个半岛的做法。");
        this.addDescription(NMIFoodItems.ROASTED_MUSHROOM, "A skewer made from mushrooms. While it's a vegetarian dish, it actually kind of tastes like meat after marinating, breading, and grilling.", "采用蘑菇为原料，经过腌渍、裹粉、油炸，虽然是素食，但味道和肉不相上下。");
        this.addDescription(NMIFoodItems.SAKURA_PUDDING, "A cute pink dessert! It's soft and bouncy, sweet and fragrant! No girl in the world can withstand this irresistible dessert.", "粉色的可爱甜品,Q软又富有弹性,香蜜的甜美气息使它成为世界上所有的女孩子都无法拒绝的无敌甜品。");
        this.addDescription(NMIFoodItems.SALMON_TEMPURA, "Surround a salmon with egg white and flour, then deep-fry until golden. With each bite juicier than the last, it'll make any cat hungry!", "将三文鱼裹上蛋液和面粉,炸至通体金黄,咬一口汁水四溢,隔壁的猫猫都馋哭啦!");
        this.addDescription(NMIFoodItems.SASHIMI_PLATTER, "A quintessential Japanese dish. The premium-grade salmon and tuna are served with wasabi and soy sauce, leading to a wonderful melding of fresh flavors.", "作为和风料理的代表，将刺身级的三文鱼和金枪鱼鱼生配上芥末和酱油，引出鲜味的绝妙料理。");
        this.addDescription(NMIFoodItems.SCARLET_DEVIL_CAKE, "A dreamy cake modeled after the hat of the Scarlet Devil. Sweet jam oozes out like blood when cut. A rare dessert from the world of Three Fairies' Hoppin' Flappin' Great Journey!", "以猩红恶魔头上的帽子为原型制作的梦幻甜食，切开会有仿佛血液一般的甜美酱料流出，是「三妖精的蹦蹦跳跳讨伐大作战」世界传来的珍贵料理。");
        this.addDescription(NMIFoodItems.SCONE, "A common British afternoon tea, crisp on the outside and soft on the inside. Usually eaten with jam or cream.", "英式下午茶的常客，外酥内软。一般蘸着果酱或者奶油一起吃。");
        this.addDescription(NMIFoodItems.AGONY_ODEN, "Former Hell's most popular party snack. Various ingredients are mixed in with a dash of spice, resulting in a magical dish where you can't stop eating even when drenched in sweat.", "旧地狱中最流行的聚会小吃,将各神食材置入后,加上辣椒刺激,让人汗流满面的同时,却无法停下,是魔力十足的料理。");
        this.addDescription(NMIFoodItems.SEA_URCHIN_SASHIMI, "Unexpectedly simple to prepare but difficult to obtain the ingredients. It is said to be a popular premium cuisine of the present world, and is considered a delicacy worldwide.", "制作意外的简单,但食材的获取却非常的困难,据说是现世流行的超高级料理,是每个人的梦想。");
        this.addDescription(NMIFoodItems.URCHIN_RAINDROP_CAKE, "Crystal-clear raindrop cake made from tuna broth, combined with sea urchins, forming an elegant view of yellow like premium champagne. Sesame sauce at the bottom further enhances its texture. It almost feels bad to think about its fate being a dessert rather than a delicate, fine-crystal art.", "以金枪鱼高汤制作的饼身清透干净,与新鲜海胆组成高级的香槟色系搭配,底层的胡麻酱更增一丝醇厚的口感。如此玲珑的甜品,让人不忍破坏这晶莹剔透的美丽。");
        this.addDescription(NMIFoodItems.SEAFOOD_MISO_SOUP, "Super common in izakayas. When seaweed first appeared in Gensokyo, someone tried boiling it and discovered that it was very savory. Since then, it's been popular all across Gensokyo.", "居酒屋常见的快手汤羹，来历不明却随处可见的海带在幻想乡出现之初，有人好奇水煮了一下，结果意外地发现有种异样的鲜味，从此便在幻想乡流行开了。");
        this.addDescription(NMIFoodItems.SECRET_DRIED_FISH_CRISPS, "Sun-dried fish marinated with secret spices. They are crispy, delicious, and easy to store, making them a popular snack at homes.", "用秘制的香料将小鱼干腌制后晒干，酥脆想口的同时又易于保存，寻常人家都喜欢在家中保存一份。");
        this.addDescription(NMIFoodItems.SECRET_SAVORY_MUSHROOM_HOTPOT, "A secret recipe created by a human magician studying fungi for a long time. The ingredients are freshly picked mushrooms from the Forest of Magic, which are said to possess a higher nutritional value than those found elsewhere.", "长期专研于蘑菇学的人类魔法使所创的秘制菜谱。食材均选自魔法森林所产的新鲜菌类,据说较其他地方产出的菌类有更高营养价值。");
        this.addDescription(NMIFoodItems.SEVEN_COLORED_YOKAN, "Classic Japanese treats. With special preparation methods, it gains a dreamy color. Too beautiful to eat. Almost. A single bite will leave you with an unforgettable experience.", "经典甜食辅以特殊的处理,呈现梦幻的色彩,让人敬畏、只敢远观不敢亵玩,能吃上一次,终生难忘。");
        this.addDescription(NMIFoodItems.WHITE_DEER_UNYIELDING_PINE, "The white deer and the pine tree are both symbols of longevity, held in high esteem by Taoists who seek a longer life, hence the painting of a deer and a pine tree. This is a dish made in the painting's image.", "白鹿和松树都有长寿的寓意,深受追求长生的道教人士的推崇,于是有“鹿寿松贞”之画,即一只白鹿立于松树之下。这道菜就是以这幅画为印象创作出来的~");
        this.addDescription(NMIFoodItems.YASHOUMA_DANGO, "Looks like elongated dango on the outside. Cutting it open reveals a variety of pictures, breathing a fresh sense of life into dango. Apparently Yashouma Dango symbolizes sarira. Isn't that the Buddha's ashes?", "外表看起来像是拉长了的团子,切开后中间裹着各式各样的图案,大大增加了团子的新鲜感。据说瘦马团子象征佛舍利,那不就是佛祖的骨灰吗?");
        this.addDescription(NMIFoodItems.SHIRAYUKI, "A premium stew made with lamprey, pufferfish, and seaweed. The name means \"white snow.\" The most luxurious family dish, if you can call it that.", "使用鲜美的八目鳗与河豚，在佐以海苔炖煮而成的高级烩锅，由于煮的过程中会飘出纯白的泡沫而得名，是非常高级的家庭料理。");
        this.addDescription(NMIFoodItems.URCHIN_STEAMED_EGG, "A simple dish which can be made by steaming egg and sea urchin until the sea urchin eggs change color. Dig in with a spoon to reveal the delectable smell of sea urchin and steamed egg guaranteed to linger on your senses.", "将鸡蛋加入海胆蒸至海胆黃变色就可以出锅的简单料理。吃的时候用勺子挖下去,一口一勺,海胆和蛋羹互相融合濟透的美味就在味蕾上蔓延了。");
        this.addDescription(NMIFoodItems.STINKY_TOFU, "Fermented black tofu with a weird flavor. Those who never tried it often wonder \"Is this even edible?\" Those who do try say you can't have enough.", "少见的黑色豆腐，散发着令人难以接近的味道——让人不禁怀疑：这真的可以吃吗？但是实际吃过的人表示根本停不下来。");
        this.addDescription(NMIFoodItems.POWER_SOUP, "A soup with a perfect balance between meat and vegetables and seaweed and boar meat. It can quickly supply your body with all the energy you need.", "荤素搭配的美味汤羹，使用了野猪肉和海带㷛煮而成，能最快捷地补充身体所需能量。");
        this.addDescription(NMIFoodItems.SUPREME_SEAFOOD_NOODLES, "Al dente noodles stewed in seafood broth, filled with seafood umami, a luxurious family dish. One bowl is enough to recall the deep memory of umami hidden by the sea", "以海鲜汤料煨煮的汤面,劲道爽滑,汤浓鲜美,是一道非常豪华的家常菜。凭一碗海鲜面,便能让人记住深藏着的大海的味道。");
        this.addDescription(NMIFoodItems.EIGHT_TRIGRAM_FISH_MAWS, "A classic Taoist dish requiring very precise steps. The fish maw must be cut into thin slices-the thinner the better-the trigrams must be constructed meticulously, paying close attention to the placement of the lines. Carefully steam it and you get a vivid image of the eight trigrams with soft and delicious fish maws.", "做法极其讲究的道教经典名菜。鱼肚片片,要愈薄愈好;太极图则要注意造型圆整、八卦形等距,最后再经过细心蒸煮,才能呈现出这道形象生动、鱼肚软糯的道家名菜。");
        this.addDescription(NMIFoodItems.KAGUYA_HIME, "\"The so-called Princess Kaguya is no different from this dish!\" -- Fujiwara no Mokou§nActually, it's just steamed bamboo with sticky rice, meat, and truffle. Surprisingly, the bamboo's fragrance mitigates the greasy meat.", "在永远亭就地取材，使用新鲜的食材和米饭一起塞进竹筒中蒸熟。饭被竹子的清香充分浸润后，中和了山猪肉带来的油腻。");
        this.addDescription(NMIFoodItems.TAKOYAKI, "It's apparently a popular street food of the Outside World. Wrapping a layer of batter unlocks the wonders of the octopus. The chewy elastic tentacles coupled with a soft and crispy coating easily puts a smile on anyone's face.", "听说也是外界流行的街边小吃,在特制的面糊中表上引发奇迹的章鱼脚,糯脆的外衣之下Q弹的章鱼脚产生让人幸福的感觉。");
        this.addDescription(NMIFoodItems.PALACE_OF_THE_HAN, "Pure white tofu symbolizing Diao Chan's purity and a pond loach hinting at Dong Zhuo's treachery. A dish that reminds one of Wang Yun's scheme with Diao Chan, using her beauty to depose the tyrant Dong Zhuo, naturally imbuing a sense of culture into the dish.", "用豆腐的洁白来形容貂婵的纯洁,以泥鳅的钻营来影射量卓的奸滑。让人在品尝中,想到王允献貂婵,巧使美人计而除奸贼董卓的故事,自然为美食增添了文化的含量。");
        this.addDescription(NMIFoodItems.FAINT_DREAM, "A two-layered cake themed after flowers. A bouquet of flowers affixed upon delicate, soft cream, oozing with sweet fragrance. A blue butterfly gently rests atop, never leaving—it's not a dream, and yet it's beyond a dream.", "以花卉为主题的双层奶油蛋糕。锦猴花团铺在细腻的奶油上,散发出丝丝香甜,蓝色的蝴蝶轻轻驻足,久久不归。这不是梦境,却胜似梦境。");
        this.addDescription(NMIFoodItems.PLANET_MARS, "Bottom of the plate solely designed to recreate the surface of Mars, topped with a premium mix of crab jelly and white wine grape jelly, only possible through molecular gastronomy. A delicacy combining the refreshing, chilly texture of the crab and the raindrop-like fruit jelly filled with a white wine aroma. Metaphorically, it's a droplet of water on planet Mars, representing the last hope.", "特别定制的盘底如火星地表一般,上面盛放着顶级的螃蟹冻以及白葡萄分子技术制作的果凝。螃蟹口感冰凉顺滑不腻,果凝如水滴一般,而且带着淡淡酒香。寓意火星上一滴水珠,代表着最后的希望。");
        this.addDescription(NMIFoodItems.ORIGIN_OF_LIFE, "At first sight, it's a mysterious planet covered with layers of clouds, but it's only a raw egg once the \"planet\" is opened up! The \"raw egg\" is actually tremella soup \"egg \" white with pumpkin paste \"egg \" yolk. Refreshing taste with rich pumpkin flavor.", "初见就像是云山雾罩的神秘星球,打开一看“星球”里竟然摆了个生鸡蛋?仔细看蛋液其实是银耳汤,蛋黄则是南瓜泥。吃起来口感清爽,还有香浓的南瓜味。");
        this.addDescription(NMIFoodItems.TIANSHIS_STEWED_MUSHROOMS, "The Divine Spirit Mausoleum is located in Senkai, so the chestnuts there are said to contain divine energy. I can't really tell the difference though... Wouldn't it be more practical to stew chestnuts with mushrooms and turn this intangible divine energy into a pot of sweet and savory stew?", "神灵庙位处仙界,所栽神的栗树据说都蕴含仙气。但我是分辨不出啦 …… 将栗子佐以蘑菇焖煮,把看不见摸不着的仙气浓缩成一锅鲜甜味美又解馋的杂烩,岂不是更实在?");
        this.addDescription(NMIFoodItems.MISO_TOFU, "An easily prepared soup commonly found in izakayas. A clean and simple delicacy that uses tofu to highlight its freshness.", "居酒屋常见的快手汤羹，使用了豆腐来提鲜，最简单又最原始的美味。");
        this.addDescription(NMIFoodItems.TOFU_STEW, "A cheap stew made of tofu. It's the most common stew in the izakaya thanks to the smooth and tender texture and its rich nutrients.", "由豆腐炖煮而成的烩锅，滑嫩的口感再加上其本身具有的较高营养价值，使这道平价料理成为居酒屋最常见的烩锅。");
        this.addDescription(NMIFoodItems.TONKOTSU_RAMEN, "The broth, made by boiling pork and vegetables, is the essence of ramen. The smooth noodles are enriched by the flavorful and aromatic pork broth, filling and satisfying.", "用猪肉和蔬菜经过长时间熬制出来的高汤，堪称整碗拉面的精髓和灵魂所在。香浓醇厚的豚骨汤底配上香弹可口的拉面，饱腹之余也让舌尖得到最大的满足。");
        this.addDescription(NMIFoodItems.TOON_PANCAKE, "A pancake made with the \"poisonous\" red toon. It tastes bitter at first, but leaves a sweet aftertaste in the mouth. REMEMBER: blanch the red toon in water before serving it to normal guests!", "用有“毒”的野菜一一香椿制成的煎饼。初尝有种苦苦涩涩的味道,嚼下去后口齿留甘。牢记!给普通客人食用须先将香椿用开水焯过一遍!");
        this.addDescription(NMIFoodItems.NITEN_ICHIRYU, "According to legends, a human swordsman invented this dish when making a bet with an oni and won with it. Made using grilled wild game, eating this dish gives you a massive strength boost and wakes your primal instinct.", "传说中与鬼立下赌约并获胜的人类剑士所创下的烤串流派，特别使用了野性十足的肉类烧制而成，食之有种冲天的气魄，让人惊叹不已。");
        this.addDescription(NMIFoodItems.UDUMBARA_CAKE, "A cake made from an ancient miraculous flower. The cake has a faint sweetness and leaves behind a lingering fragrance said to help one remember their fondest memories.", "用上古时期便存在的奇迹之花制作的糕点，不仅甜而不腻，食后更是齿颊留香，据说能勾起人心中最想怀念的回忆。");
        this.addDescription(NMIFoodItems.UNCONSCIOUS_YOUKAI_MOUSSE, "A precious mousse modeled after the hat of an unconscious youkai. Gaze into the infinite darkness upon cutting it and let it etch an unforgettable taste into your very soul. A rare dessert from the world of Three Fairies' Hoppin' Flappin' Great Journey!", "以无意识妖怪的帽子为原型制作的深沉甜食，即使切开感受到的也是无尽的黑暗，但香醇程度令人流连忘返，是「三妖精的蹦蹦跳跳讨伐大作战」世界传来的珍贵料理。");
        this.addDescription(NMIFoodItems.VEGETABLE_SALAD, "A salad made of fresh vegetables. The low calories make it the perfect diet food for young girls.", "用新鲜的蔬菜生拌而成的沙拉，口感清新，可以去除嘴里的油腻。不知为何被年轻的姑娘们奉为减肥圣餐。");
        this.addDescription(NMIFoodItems.PINK_RICE_BALL, "A simple and cheap rice ball with trout and onions, merging the juicy seafood with the tart onion. Nutritious and delicious.", "常见的平价饭团，内陷加入了鳟鱼和洋葱，融合了海鲜的细腻口感与洋葱的炽热，无论是营养还是口感都属上佳。");
        this.addDescription(NMIFoodItems.PEACH_YATSUHASHI, "A classic Japanese dessert shaped like a koto. The peach filling colors make them pink. Cute and inviting.", "状似外界古筝的经典和果子，加入白桃内陷之后呈现淡粉色，非常诱人。");
        this.addDescription(NMIFoodItems.UNZAN_COTTON_CANDY, "Among Myouren Temple's disciples is a shapeshifting nyuudou whose face easily reminds one of cotton candy. A sweet but not too sweet cotton candy with added peach juice. Its interesting shape makes it very popular with kids.", "命莲寺的弟子中有个能够变幻形态的入道,只要见过他的样子,就很容易联想到棉花糖。加入桃汁制作的棉花糖甜而不腻,造型生动有趣,深受小孩子的喜爱。");
        this.addDescription(NMIFoodItems.HODGEPODGE, "A stew made with leftovers. Delicious without being wasteful, the best of both worlds.", "使用一些边角料食材杂烩而成的烩锅，享受美味的同时还能避免浪费，可谓一举两得。");
        this.addDescription(NMIIngredientItems.BAMBOO_SHOOT, "Common bamboo shoots you can find in Bamboo Forest of the Lost.", "从野外采回来的竹笋，较为常见");
        this.addDescription(NMIIngredientItems.IBERICO_PORK, "Pork from pigs that breed deep in the mountains. Very high-grade.", "在高海拔深山中圈养的黑毛猪肉，非常高级。");
        this.addDescription(NMIIngredientItems.BLACK_SALT, "Composed of volcanic energy accumulated for thousands of years; Made just to purify the dirtiness of your soul... That's what the advertising slogan reads, but it's in fact just ordinary volcanic rock salt.", "火山能量，积累上千万年；只为净化，你灵魂的尘埃......广告词这样写着，其实就是普通的火山岩盐。");
        this.addDescription(NMIIngredientItems.BROCCOLI, "Broccoli grown from Makai soil has a slightly different flower bulb color than the Earthly ones, as insufficient light causes their undergrowth.", "魔界土壤培育出来的西蓝花,因为光照不足植株徒长,花球颜色和地上也略微有些不同。");
        this.addDescription(NMIIngredientItems.BUTTER, "Commonly used in Western cuisines. Brings an overwhelming aroma with a small amount.", "西餐常用的食材，可以轻松地给食物增加难以抗拒的香味");
        this.addDescription(NMIIngredientItems.STRONG_CAPSAICIN, "Ultra spicy product made with Lunarian technology, capable of leaving horrible memories for those who dared to try it. Not even sure if it's made with chili. . .", "使用月之科技制作的极辣品,能让食用者留下恐怖的回忆。是不是真的用辣椒做的就说不清了……");
        this.addDescription(NMIIngredientItems.CHEESE, "A precious ingredient made by fermenting cream. Melt some over a dish to add a creamy and delicious flavor.", "浓郁的奶油熟成后的珍贵食材，取一片加热融化就能让料理变得奶香浓郁，美味十足。");
        this.addDescription(NMIIngredientItems.CHESTNUT, "It's said that while the founder of Taoism was training, he planted many chestnut trees due to a dislike of meat, substituting rice with chestnuts. They have fat kernels and are highly nutritious; Taoists hold them in high esteem, and even farmers frequently use them as a substitute for grain.", "相传道教创始人修炼时，由于不爱荤腥，便栽了很多板栗树，以栗代饭。栗子种仁肥厚，营养丰富，不仅受到许多道教人士的推崇，也经常被农民用来代替粮食。");
        this.addDescription(NMIIngredientItems.CHILI, "An ingredient for adding spiciness to food, but opinions of it are polarized.", "用于增加辣味的食材，评价非常两极");
        this.addDescription(NMIIngredientItems.CICADA_SLOUGH, "The shell of a cicada that can be found on tree trunks.", "在树干上常常可以采集的昆虫外壳，较为常见");
        this.addDescription(NMIIngredientItems.CRAB, "An eight-legged fool that thinks it can scurry around with its armored shell. By simply steaming, it can be turned into an ultimate delicacy! Just be careful of its pincers...", "以为有了盔甲就可以横行霸道的八脚笨蛋，只需要简单的清蒸就能成为究极美味！不过要小心它的钳子......");
        this.addDescription(NMIIngredientItems.CREAM, "A dairy product processed in a special way that is guaranteed to instantly explode the taste buds of those with a sweet tooth!", "用非常特殊的方法处理的奶制品，无论什么时候都能秒杀甜品嗜好者的味蕾！");
        this.addDescription(NMIIngredientItems.CUCUMBER, "The kappa's favorite! Although it is no different from other vegetables... Why exactly are kappa so addicted to it...?", "河童的嗜好物。虽然用来做菜和普通蔬菜没有区别，但却能让河童无比上瘾。究竟是其中的什么成分在起作用......");
        this.addDescription(NMIIngredientItems.DEW, "Dew that can only be collected in the early morning. Somewhat rare.", "清晨采回来的露水，有点珍贵");
        this.addDescription(NMIIngredientItems.CREEPING_FIG, "A medicinal and food plant. Creeping fig seeds, when soaked and then rubbed, will solidify into a crystalline gel after refrigeration. They can be used to make jellies and those alike, which are brilliant desserts to beat the summer heat.", "一种药食两用植物。将薛荔籽浸泡后搓汁,冷藏后会凝固成晶莹剔透的胶状物,可以用来制作果冻和凉粉等,是消暑神品。");
        this.addDescription(NMIIngredientItems.FLOUR, "A common ingredient that can be used in many ways.", "有多种用途，较为常见");
        this.addDescription(NMIIngredientItems.FLOWER, "A bouquet of colorful and gorgeous flowers. We must show our respects to the task flowers must complete even at the cost of their own lives!", "一朵朵绚丽烂漫的鲜花扎成的花束。花儿们以生命为代价也要完成的事,必须要对此付出敬意!");
        this.addDescription(NMIIngredientItems.GINKGO_NUT, "Common ginkgo nut that grows from ginkgo trees.", "白果树的果实，较为常见");
        this.addDescription(NMIIngredientItems.GRAPES, "Grapes cultivated at the Scarlet Devil Mansion, which are commonly used for alcohol brewing.", "在红魔馆种植的用于酿酒的葡萄");
        this.addDescription(NMIIngredientItems.LAMPREY, "A type of migratory ocean fish that can somehow be found in the rivers and lakes of Gensokyo. Somewhat common.", "一种洄游性海鱼但却在幻想乡的河流湖泊随处可见，较为常见");
        this.addDescription(NMIIngredientItems.LEMON, "A strange fruit that only grows on a special tree near the bridge. It's said that no one can eat one while keeping a straight face. Its extremely sour taste means it can only be used as flavoring by extracting its juices.", "仅在桥附近特别的树上产出的奇怪果实，据说没有人能够无表情板着脸吃完一整颗，有着强大的“酸”味，只能取其果汁来调味。");
        this.addDescription(NMIIngredientItems.LOTUS_SEED, "The seeds of a very old aquatic plant-the lotus. Lotus seeds have very bitter cores, so accidentally mixing them into a customer's dish must be avoided at all costs. Careful handling is a must.", "非常古老的水生植物--莲的种子。莲子的芯特别苦，千万得处理好，别混到料理给客人吃了。");
        this.addDescription(NMIIngredientItems.LUNAR_HERB, "A specialty of Eientei. A plant that grows under the full moon and absorbs its very essence. Very rare.", "永远亭的特产，由满月之夜的月光凝聚，非常珍贵。");
        this.addDescription(NMIIngredientItems.OCTOPUS, "A squishy and lovely sea creature, but there are no seas in Gensokyo... Its tentacles are great ingredients and have an overwhelmingly chewy texture. By simply roasting them on a fire, you can enjoy their tenderness!", "鲜嫩可爱的海洋生物，但幻想乡没有海......它的脚是宝贝，有压倒性的肉质感，而且只要用最简单的火烤，就能享受弹牙的美味！");
        this.addDescription(NMIIngredientItems.ONION, "Common onion grown in the Human Village.", "人里农田产的洋葱，较为常见");
        this.addDescription(NMIIngredientItems.PEACH, "Common peach that grows from peach trees.", "桃子树的果实，较为常见");
        this.addDescription(NMIIngredientItems.PINE_NUT, "Seeds from red pine trees. Apparently an endangered species in the Outside World, but still very common in Gensokyo. Said to promote longevity, it's a vital part of every fasting Taoist priest's diet.", "红松树的种子，据说在外界已经被列为濒危物种，但在幻想乡仍然很常见。传说松子有延年益寿的功能，是古代道士辟谷时的常备之物。");
        this.addDescription(NMIIngredientItems.PLUM, "Fruit from a plum tree which can be dried or salted. Dried plums develop a unique taste of saltiness with a hint of sour. Combined with its savory sweet aftertaste, they make delightful appetizers.", "果梅树结的果实,可以盐渍或干制。做成梅干后有神咸中带酸的特殊口感,能够大大激起食欲,且品尝后唇齿留甘,让人回味无穷。");
        this.addDescription(NMIIngredientItems.JIGURU_BERRY, "Weird fruits seen everywhere in Makai. Apparently grants different effects based on the type of person consuming it, which is very peculiar.", "魔界里到处可见的怪异果子。似乎会根据食用者的类型产生不同的食用效果,十分奇特。");
        this.addDescription(NMIIngredientItems.RED_BEAN, "A herbaceous plant that's very adaptable. Eating it can boost one's immune system and prevent diseases. People of ancient times believed diseases were caused by demons, hence legends that say red beans can overcome demons.", "又称赤豆,是适应能力较强的草本植物,食用能够起到增强自身免疫力和抗病能力的效果。古代人觉得中风得病都是疫鬼作柴,所以有“赤豆打鬼”的传说。");
        this.addDescription(NMIIngredientItems.SALMON, "A type of highly migratory fish that can somehow be found in the rivers and lakes of Gensokyo. Somewhat rare.  ", "一种高度洄游海鱼但在幻想乡的河流湖泊可见，有点珍贵");
        this.addDescription(NMIIngredientItems.SEA_URCHIN, "Believed to be a legendary ingredient in the Outside World. A single one is enough to pin the deliciousness of the entire ocean in one's mind... Is it true though? It does look kinda weird...", "据说在外界被称作传说级的食材，只要一小颗就能让人感受到整个海洋的美味，会是真的吗？样子长得倒是挺奇怪的......");
        this.addDescription(NMIIngredientItems.SHRIMP, "Freshwater shrimp that inhabits Misty Lake and Genbu Ravine. Rather common.", "分布在雾之湖和玄武泽的淡水虾，较为常见");
        this.addDescription(NMIIngredientItems.STICKY_RICE, "Very sticky rice. Cuisines made of it are either soft as cloud or tender as cream.", "很有粘性的米，制作出来的料理或绵软适口，或鲜嫩弹牙");
        this.addDescription(NMIIngredientItems.PREMIUM_TUNA, "The highest quality of tuna. Very valuable.", "金枪鱼中的顶级品种，非常珍贵");
        this.addDescription(NMIIngredientItems.SWEET_POTATO, "The world's most reliable ingredient! Sweet, soft and effectively getting rid of hunger, it even warms your hands during winter.", "世上最实在的食材！不仅香甜软糯，而且能强效地应对饥饿，在冬天还有暖手的效果。");
        this.addDescription(NMIIngredientItems.TOFU, "Tofu sold in the Human Village. Rather common.", "人里有卖的豆腐，较为常见");
        this.addDescription(NMIIngredientItems.TOMATO, "A brightly colored berry. So bright that it was once called the \"wolf peach\", thought to be highly poisonous and could only be used for decoration. Mass production started after they were discovered to be edible.", "颜色鲜艳的浆果,曾经还因为太鲜艳被视为“狐狸的果实”,觉得它具有剧毒,所以只用来观赏。在发现可以食用以后就开始大面积种植了。");
        this.addDescription(NMIIngredientItems.RED_TOON, "A common wild vegetable. Poisonous; eating too much is not recommended. Must be blanched before consuming.", "一种常见的野菜。具有一定的毒性,不建议过量食用,并且食用前须焯水烹饪。");
        this.addDescription(NMIIngredientItems.SNOW_FUNGUS, "A fungus as beautiful as a flower. It is said that royalty through the ages look upon snow fungus as a life-prolonging product, yet it seems very common in Lunar Capital. Its immaculate white fits Lunar Capital well.", "一种像花儿一样美丽的菌类。据说历代皇家贵族把银耳看作延年益寿之品,但在月都似乎很常见。这种看起来一尘不染的洁白,和月都的感觉很相衬。");
        this.addDescription(NMIIngredientItems.TROUT, "A type of cold-water fish found in fresh water. Rather common.", "栖息于淡水中的冷水鱼，较为常见");
        this.addDescription(NMIIngredientItems.TRUFFLE, "A prized truffle collected from the Forest of Magic. Unable to be cultivated artificially, which makes it very precious.", "从魔法之森采回来的品相良好的松露，无法人工种植，非常珍贵");
        this.addDescription(NMIIngredientItems.TUNA, "A type of oceanodromous migratory fish that can somehow be found in the rivers and lakes of Gensokyo. Somewhat rare.", "一种大洋性洄游海鱼但在幻想乡的河流湖泊可见，有点珍贵");
        this.addDescription(NMIIngredientItems.BINGDI_LOTUS, "Its roots grow near bridges, blooming on the water's surface when mature. It possesses a light pink hue and a subtle fragrance, a high-quality ingredient used as festive decorations in the Underworld.", "根茎生长在桥的附近，花朵成熟后会破水而出，淡红色并有淡香味，高级食材，也是地底人的节日装饰。");
        this.addDescription(NMIIngredientItems.UDUMBARA, "A miraculous flower that grows in marshlands. Very precious.", "生长在沼泽中的奇迹之花，非常珍贵");
        this.addDescription(NMIIngredientItems.VENISON, "Venison hunted from the mountains. Somewhat rare.", "猎人们在山间猎回来的鹿肉，有点珍贵");
        this.addDescription(NMIIngredientItems.WAGYU_BEEF, "Legendary top-quality beef; also called \"snow beef\". Very high-grade.", "传闻是超优质的肉牛品种，又称雪花肉，非常高级");
        this.addDescription(NMIIngredientItems.RADISH, "Common radish grown in the Human Village.", "人里农田产的萝卜，较为常见");
        this.addDescription(NMIIngredientItems.BOAR_MEAT, "Boar meat hunted from the mountains. Somewhat rare and tastes of wildness.", "猎人们在山间猎回来的野猪肉，充满野性");

    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        this.addTranslations();
        this.addTagTranslations();
        this.addItemTranslations();
        this.addItemDescTranslations();
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
        this.add(key.asItem().getDescriptionId(), en, cn);
    }

    public void add(Item key, String en, String cn) {
        this.add(key.getDescriptionId(), en, cn);
    }

    public void add(DeferredItem<@NotNull Item> key, String en, String cn) {
        this.add(key.asItem().getDescriptionId(), en, cn);
    }

    private void add(String key, String en, String cn) {
        if (this.locale.equals("en_us") && !this.enData.containsKey(key)) {
            this.enData.put(key, en);
        } else if (this.locale.equals("zh_cn") && !this.cnData.containsKey(key)) {
            this.cnData.put(key, cn);
        }
    }

    private void add(Identifier key, String en, String cn) {
        this.add(key.toLanguageKey(), en, cn);
    }

    private void addTag(Identifier key, String en, String cn) {
        this.add(key.toLanguageKey("tag"), en, cn);
    }

    public void addDescription(Block key, String en, String cn) {
        this.add(key.getDescriptionId() + ".desc", en, cn);
    }

    public void addDescription(DeferredBlock<@NotNull Block> key, String en, String cn) {
        this.add(key.asItem().getDescriptionId() + ".desc", en, cn);
    }

    public void addDescription(Item key, String en, String cn) {
        this.add(key.getDescriptionId() + ".desc", en, cn);
    }

    public void addDescription(DeferredItem<@NotNull Item> key, String en, String cn) {
        this.add(key.asItem().getDescriptionId() + ".desc", en, cn);
    }

    @Override
    public @NotNull String getName() {
        return "language:" + this.locale;
    }
}
