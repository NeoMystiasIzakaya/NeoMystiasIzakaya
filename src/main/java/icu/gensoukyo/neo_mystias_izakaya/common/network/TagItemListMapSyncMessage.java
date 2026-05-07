package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemListMap;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record TagItemListMapSyncMessage(TagItemListMap map) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<TagItemListMapSyncMessage> TYPE = new CustomPacketPayload.Type<>(NeoMystiasIzakaya.id("tag_item_list_map_sync"));

    public static final StreamCodec<ByteBuf, TagItemListMapSyncMessage> STREAM_CODEC = StreamCodec.composite(
            TagItemListMap.STREAM_CODEC, TagItemListMapSyncMessage::map,
            TagItemListMapSyncMessage::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
