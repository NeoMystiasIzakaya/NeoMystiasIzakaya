package icu.gensoukyo.neo_mystias_izakaya.common.resource;

import icu.gensoukyo.neo_mystias_izakaya.common.menu.DishServingMenu;
import icu.gensoukyo.neo_mystias_izakaya.common.network.ServerPayloadSender;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIVanillaTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ResourceHandlerUtil {

    public static List<ItemResourceWithCount> extractCuisinesItemResourceList(ResourceHandler<ItemResource> resourceHandler) {
        return extractItemResourceListByTag(resourceHandler, NMIVanillaTags.CUISINES);
    }

    public static List<ItemResourceWithCount> extractBeveragesItemResourceList(ResourceHandler<ItemResource> resourceHandler) {
        return extractItemResourceListByTag(resourceHandler, NMIVanillaTags.BEVERAGES);
    }

    public static List<ItemResourceWithCount> extractIngredientItemResourceList(ResourceHandler<ItemResource> resourceHandler) {
        return extractItemResourceListByTag(resourceHandler, NMIVanillaTags.INGREDIENT);
    }

    public static List<ItemResourceWithCount> extractItemResourceListByTag(ResourceHandler<ItemResource> resourceHandler, TagKey<Item> tagKey) {
        Map<ItemResource, Long> itemResourceLongHashMap = new HashMap<>();
        for (int i = 0; i < resourceHandler.size(); i++) {
            Long count = itemResourceLongHashMap.getOrDefault(resourceHandler.getResource(i), 0L);
            itemResourceLongHashMap.put(resourceHandler.getResource(i), count == Long.MAX_VALUE ? count : count + resourceHandler.getAmountAsLong(i));
        }
        return itemResourceLongHashMap.keySet().stream().filter(i->i.is(tagKey)).map(itemResource -> new ItemResourceWithCount(itemResource, itemResourceLongHashMap.get(itemResource))).toList();
    }


    public static void extractItemToPlayerHand(ServerPlayer serverPlayer, ItemResource itemResource,ResourceHandler<ItemResource> resourceHandler) {
        if (!(serverPlayer.containerMenu instanceof DishServingMenu dishServingMenu)) {
            return;
        }
        try (Transaction transaction = Transaction.openRoot()) {
            if (resourceHandler.extract(itemResource, 1, transaction) == 1 && dishServingMenu.getCarried().isEmpty()) {
                dishServingMenu.setCarried(itemResource.toStack());
            }
            transaction.commit();
            dishServingMenu.broadcastChanges();
        }
    }
}
