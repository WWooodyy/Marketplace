package com.woodycreations.marketplace.spigot.util;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemUtil {
    public static byte[] serializeItem(@NotNull ItemStack item) {
        return item.serializeAsBytes();
    }

    public static @NotNull ItemStack deserializeItem(byte[] data) {
        return ItemStack.deserializeBytes(data);
    }
}