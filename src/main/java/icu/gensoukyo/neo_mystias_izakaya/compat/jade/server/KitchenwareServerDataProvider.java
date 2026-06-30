package icu.gensoukyo.neo_mystias_izakaya.compat.jade.server;

import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.KitchenwareBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.compat.jade.MystiaJadePlugin;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.StreamServerDataProvider;

import java.util.Optional;

public class KitchenwareServerDataProvider implements StreamServerDataProvider<BlockAccessor, KitchenwareData> {

    public static final KitchenwareServerDataProvider INSTANCE = new KitchenwareServerDataProvider();

    @Override
    public @Nullable KitchenwareData streamData(BlockAccessor blockAccessor) {
        if (!(blockAccessor.getBlockEntity() instanceof KitchenwareBlockEntity entity)) return null;
        return new KitchenwareData(entity.getResultItem().isEmpty()? Optional.empty():Optional.of(entity.getResultItem())
                ,entity.getTargetItem().isEmpty()? Optional.empty():Optional.of(entity.getTargetItem())
                , entity.getCookingTime(), entity.getTotalCookingTime());
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, KitchenwareData> streamCodec() {
        return KitchenwareData.STREAM_CODEC;
    }

    @Override
    public Identifier getUid() {
        return MystiaJadePlugin.KITCHENWARE_DATA_PROVIDER;
    }
}
