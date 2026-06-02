package icu.gensoukyo.neo_mystias_izakaya.client.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jspecify.annotations.NonNull;

public record OpenDishServingMessage() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<OpenDishServingMessage> TYPE = new CustomPacketPayload.Type<>(NeoMystiasIzakaya.id("open_dish_serving"));
    private static final OpenDishServingMessage INSTANCE = new OpenDishServingMessage();
    public static final StreamCodec<ByteBuf, OpenDishServingMessage> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
