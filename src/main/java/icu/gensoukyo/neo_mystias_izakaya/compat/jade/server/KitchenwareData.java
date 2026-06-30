package icu.gensoukyo.neo_mystias_izakaya.compat.jade.server;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public record KitchenwareData(Optional<ItemStack> result, Optional<ItemStack> target, int cookingTime, int totalCookingTime) {

    public static final StreamCodec<RegistryFriendlyByteBuf, KitchenwareData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.optional(ItemStack.STREAM_CODEC),
            KitchenwareData::result,
            ByteBufCodecs.optional(ItemStack.STREAM_CODEC),
            KitchenwareData::target,
            ByteBufCodecs.VAR_INT,
            KitchenwareData::cookingTime,
            ByteBufCodecs.VAR_INT,
            KitchenwareData::totalCookingTime,
            KitchenwareData::new
    );
}
