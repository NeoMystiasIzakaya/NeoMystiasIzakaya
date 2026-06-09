/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.util.holder;

import java.util.function.Supplier;

public class DoubleHolder implements Supplier<Double> {

    private double value;

    public DoubleHolder(double value) {
        this.value = value;
    }

    public void set(double value) {
        this.value = value;
    }

    @Override
    public Double get() {
        return value;
    }
}
