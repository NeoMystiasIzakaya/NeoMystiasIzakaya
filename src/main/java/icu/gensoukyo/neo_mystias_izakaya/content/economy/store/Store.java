/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.economy.store;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import lombok.Getter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.Map;

@Getter
public class Store {

    private final Identifier id;
    private final List<StoreItem> items;
    private final Map<Identifier,StoreItem> itemMap;

    public Store(Identifier id, List<StoreItem> items) {
        this.id = id;
        this.items = List.copyOf(items);
        this.itemMap = items.stream().collect(java.util.stream.Collectors.toMap(StoreItem::id, item -> item));
    }


    public static final Codec<Store> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            Identifier.CODEC.fieldOf("id").forGetter(Store::getId),
            StoreItem.CODEC.listOf().fieldOf("items").forGetter(Store::getItems)
    ).apply(ins, Store::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, Store> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC,
            Store::getId,
            ByteBufCodecs.<RegistryFriendlyByteBuf, StoreItem>list().apply(StoreItem.STREAM_CODEC),
            Store::getItems,
            Store::new
    );

    public static final Identifier ALL = NeoMystiasIzakaya.id("all");
    public static final Identifier INGREDIENTS = NeoMystiasIzakaya.id("ingredients");
    public static final Identifier BEVERAGES = NeoMystiasIzakaya.id("beverages");

    public static final Store EMPTY = new Store(NeoMystiasIzakaya.id("empty"), List.of());
}
