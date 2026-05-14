package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public record NMIKitchenwareCookMessage(NMIRecipe cuisine, int time, BlockPos blockPos) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<NMIKitchenwareCookMessage> TYPE = new CustomPacketPayload.Type<>(NeoMystiasIzakaya.id("kitchenware_cook"));

    public static final StreamCodec<RegistryFriendlyByteBuf, NMIKitchenwareCookMessage> STREAM_CODEC = StreamCodec.composite(
            NMIRecipe.STREAM_CODEC, NMIKitchenwareCookMessage::cuisine,
            ByteBufCodecs.VAR_INT, NMIKitchenwareCookMessage::time,
            BlockPos.STREAM_CODEC, NMIKitchenwareCookMessage::blockPos,
            NMIKitchenwareCookMessage::new
    );
    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
