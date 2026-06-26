/*
 * Copyright 2026 Kaguya154
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.kaguya.util;

public final class KaguyaFormatUtil {

    public static String formatCount(long count){
        if (count == Long.MAX_VALUE){
            return "Inf";
        }
        if (count<1_000){
            return String.valueOf(count);
        }
        if (count < 100_000){
            return String.format("%.1fK", count/1_000.0);
        }
        if (count < 1_000_000){
            return String.format("%dK", count/1_000);
        }
        if (count < 100_000_000) {
            return String.format("%.1fM", count / 1_000_000.0);
        }
        if (count < 1_000_000_000){
            return String.format("%dM", count/1_000_000);
        }
        return String.format("%.1fB", count/1_000_000_000.0);
    }
}
