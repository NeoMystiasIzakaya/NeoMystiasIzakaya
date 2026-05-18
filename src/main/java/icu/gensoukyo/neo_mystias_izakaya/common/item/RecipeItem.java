package icu.gensoukyo.neo_mystias_izakaya.common.item;

import icu.gensoukyo.neo_mystias_izakaya.client.util.NMIClientUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class RecipeItem extends Item {
    public RecipeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            NMIClientUtil.openRecipeScreen();
        }
        return InteractionResult.SUCCESS;
    }
}
