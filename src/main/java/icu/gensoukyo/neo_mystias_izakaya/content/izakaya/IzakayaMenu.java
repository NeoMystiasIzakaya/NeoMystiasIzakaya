/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.izakaya;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

public record IzakayaMenu(List<Identifier> cuisines, List<Identifier> beverages) {

    public static final Codec<IzakayaMenu> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC.listOf().fieldOf("cuisine").forGetter(IzakayaMenu::cuisines),
                    Identifier.CODEC.listOf().fieldOf("beverage").forGetter(IzakayaMenu::beverages)
            ).apply(instance, IzakayaMenu::new)
    );

    public static final MapCodec<IzakayaMenu> MAP_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Identifier.CODEC.listOf().fieldOf("cuisine").forGetter(IzakayaMenu::cuisines),
                    Identifier.CODEC.listOf().fieldOf("beverage").forGetter(IzakayaMenu::beverages)
            ).apply(instance, IzakayaMenu::new)
    );

    public static final StreamCodec<ByteBuf, IzakayaMenu> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.<ByteBuf, Identifier>list().apply(Identifier.STREAM_CODEC), IzakayaMenu::cuisines,
            ByteBufCodecs.<ByteBuf, Identifier>list().apply(Identifier.STREAM_CODEC), IzakayaMenu::beverages,
            IzakayaMenu::new
    );

    public static final IzakayaMenu EMPTY = new IzakayaMenu(List.of(), List.of());

    public IzakayaMenu copy() {
        return new IzakayaMenu(new ArrayList<>(cuisines), new ArrayList<>(beverages));
    }
}
