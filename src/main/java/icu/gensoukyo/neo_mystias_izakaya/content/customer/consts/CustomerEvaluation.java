/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.customer.consts;

import lombok.Getter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

/**
 * 顾客评价枚举，定义五级评价及其价格倍率。
 *
 * <h3>倍率表</h3>
 * <table>
 * <tr><th>评价</th><th>倍率</th></tr>
 * <tr><td>{@link #EX_BAD}</td><td>0.8</td></tr>
 * <tr><td>{@link #BAD}</td><td>0.9</td></tr>
 * <tr><td>{@link #NORM}</td><td>1.0</td></tr>
 * <tr><td>{@link #GOOD}</td><td>1.1</td></tr>
 * <tr><td>{@link #EX_GOOD}</td><td>1.2</td></tr>
 * </table>
 */
@Getter
public enum CustomerEvaluation {

    EX_BAD("ex_bad", 0.8f),
    BAD("bad", 0.9f),
    NORM("norm", 1.0f),
    GOOD("good", 1.1f),
    EX_GOOD("ex_good", 1.2f);

    /**
     * 网络流编解码器，按枚举序号传输。
     */
    public static final StreamCodec<FriendlyByteBuf, CustomerEvaluation> STREAM_CODEC =
            NeoForgeStreamCodecs.enumCodec(CustomerEvaluation.class);
    private final String level;
    private final float priceMultiplier;

    CustomerEvaluation(String level, float priceMultiplier) {
        this.level = level;
        this.priceMultiplier = priceMultiplier;
    }

    /**
     * 从 {@link CustomerEvaluationLevels} 的字符串常量转换为枚举。
     *
     * @param level 评价等级字符串（如 {@code "ex_good"}）
     * @return 对应的枚举值，未匹配时返回 {@link #NORM}
     */
    public static CustomerEvaluation fromLevel(String level) {
        for (CustomerEvaluation eval : values()) {
            if (eval.level.equals(level)) {
                return eval;
            }
        }
        return NORM;
    }

    /**
     * 对价格应用评价倍率。
     *
     * @param basePrice 基础价格
     * @return 修正后的价格（四舍五入取整）
     */
    public int apply(int basePrice) {
        return Math.round(basePrice * priceMultiplier);
    }

    /**
     * 获取可本地化的翻译键。
     *
     * @return 评价翻译键，如 {@code evaluation.neo_mystias_izakaya.ex_good}
     */
    public String getTranslationKey() {
        return "evaluation.neo_mystias_izakaya." + level;
    }

    /**
     * 判断评价是否不为负面（即 {@link #NORM} 及以上）。
     *
     * @return {@code true} 若评价为 NORM、GOOD 或 EX_GOOD
     */
    public boolean isNotNegative() {
        return this == NORM || this == GOOD || this == EX_GOOD;
    }
}
