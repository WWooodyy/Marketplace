package com.woodycreations.marketplace.spigot.database.dao.marketplace.entity;

import com.woodycreations.marketplace.spigot.state.MarketState;
import com.woodycreations.marketplace.spigot.util.ItemUtil;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

@Builder()
@Getter()
@Entity("market_listing")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MarketEntity {
    @Id private UUID key;
    @Builder.Default private final long listTime = System.currentTimeMillis();
    @Builder.Default private final MarketState status = MarketState.ACTIVE;
    private UUID seller;
    private byte[] item;
    private double price;

    public static class MarketEntityBuilder {
        public MarketEntityBuilder() {
            this.key = UUID.randomUUID();
        }

        public @NotNull MarketEntityBuilder item(@NotNull ItemStack item) {
            this.item = ItemUtil.serializeItem(item);
            return this;
        }

        public @NotNull MarketEntityBuilder seller(@NotNull Player player) {
            this.seller = player.getUniqueId();
            return this;
        }
    }
}
