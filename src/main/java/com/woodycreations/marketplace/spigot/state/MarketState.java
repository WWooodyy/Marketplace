package com.woodycreations.marketplace.spigot.state;

import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

public enum MarketState {
    ACTIVE(price -> price),
    SOLD_MARKETPLACE(price -> price),
    SOLD_BLACK_MARKET(price -> price / 2);

    private final Function<Double, Double> priceModifier;

    MarketState(@NotNull Function<Double, Double> priceModifier) {
        this.priceModifier = priceModifier;
    }

    public @NotNull Function<Double, Double> getPriceModifier() {
        return this.priceModifier;
    }
}