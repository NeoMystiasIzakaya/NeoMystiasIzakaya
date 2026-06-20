/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.cooking;

import com.mojang.serialization.Codec;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.consts.NMICuisinesTags;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIVanillaTags;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public enum KitchenwareType {
    BOILING_POT(NMIVanillaTags.BOILING_POT, NMIMainItems.BOILING_POT.get(), NMICuisinesTags.Boiling_Pot),
    CUTTING_BOARD(NMIVanillaTags.CUTTING_BOARD, NMIMainItems.CUTTING_BOARD.get(), NMICuisinesTags.Cutting_Board),
    FRYING_PAN(NMIVanillaTags.FRYING_PAN, NMIMainItems.FRYING_PAN.get(), NMICuisinesTags.Frying_Pan),
    GRILL(NMIVanillaTags.GRILL, NMIMainItems.GRILL.get(), NMICuisinesTags.Grill),
    STEAMER(NMIVanillaTags.STEAMER, NMIMainItems.STEAMER.get(), NMICuisinesTags.Steamer);

    public final TagKey<Block> KITCHENWARE_TYPE;
    public final Identifier KITCHENWARE_TAG;
    public final Item KITCHENWARE_ITEM;

    KitchenwareType(TagKey<Block> blockTagKey, Item kitchenwareItem, Identifier kitchenwareTag) {
        this.KITCHENWARE_TYPE = blockTagKey;
        this.KITCHENWARE_ITEM = kitchenwareItem;
        this.KITCHENWARE_TAG = kitchenwareTag;
    }

    public static final Codec<KitchenwareType> CODEC = Codec.STRING.xmap(KitchenwareType::valueOf, KitchenwareType::name);

    public static final StreamCodec<ByteBuf,KitchenwareType> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, KitchenwareType::name,
            KitchenwareType::valueOf
    );
}
