/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.economy.store;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Cart {

    private List<CartItem> items;

    public Cart(List<CartItem> items) {
        this.items = items;
    }

    public static final Codec<Cart> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            CartItem.CODEC.listOf().fieldOf("items").forGetter(Cart::getItems)
    ).apply(ins, Cart::new));

    public static final StreamCodec<ByteBuf, Cart> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.<ByteBuf, CartItem>list().apply(CartItem.STREAM_CODEC),
            Cart::getItems,
            Cart::new
    );

    public void addItem(CartItem item) {
        for (CartItem cartItem : items) {
            if (cartItem.id() == item.id()) {
                CartItem mergedItem = new CartItem(cartItem.id(), cartItem.count() + item.count());
                items.set(items.indexOf(cartItem), mergedItem);
                return;
            }
        }
        items.add(item);
    }

    public void removeItem(CartItem item) {
        for (CartItem cartItem : items) {
            if (cartItem.id() == item.id()) {
                int newCount = cartItem.count() - item.count();
                if (newCount > 0) {
                    CartItem updatedItem = new CartItem(cartItem.id(), newCount);
                    items.set(items.indexOf(cartItem), updatedItem);
                }else {
                    items.remove(cartItem);
                }
                return;
            }
        }
    }

    public static Cart empty() {
        return new Cart(new ArrayList<>());
    }
}
