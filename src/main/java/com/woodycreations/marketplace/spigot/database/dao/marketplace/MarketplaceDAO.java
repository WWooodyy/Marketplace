package com.woodycreations.marketplace.spigot.database.dao.marketplace;

import com.google.common.collect.Maps;
import com.woodycreations.marketplace.spigot.database.MongoDB;
import com.woodycreations.marketplace.spigot.database.dao.marketplace.entity.MarketEntity;
import com.woodycreations.marketplace.spigot.state.MarketState;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public class MarketplaceDAO {
    private final MongoDB mongoDB;
    @Getter private final Map<UUID, MarketEntity> cache = Maps.newHashMap();

    public MarketplaceDAO(@NotNull MongoDB mongoDB) {
        this.mongoDB = mongoDB;

        this.cacheListings();
    }

    public void listOrUpdateItem(@NotNull MarketEntity entity) {
        this.cache.put(entity.getKey(), entity);
        this.mongoDB.getDatastore().save(entity);
    }

    public void removeListing(@NotNull MarketEntity entity) {
        this.cache.remove(entity.getKey());
        this.mongoDB.getDatastore().delete(entity);
    }

    private void cacheListings() {
        this.cache.clear();

        for (final MarketEntity entity : this.getActiveListingsFromMongo()) {
            this.cache.put(entity.getKey(), entity);
        }

        System.out.println("Cached " + this.cache.size() + " active listings");
    }

    public @NotNull MarketEntity getListing(@NotNull UUID key) {
        return this.cache.get(key);
    }

    public @NotNull List<MarketEntity> getActiveListingsFromMongo() {
        final Datastore datastore = this.mongoDB.getDatastore();
        return datastore.find(MarketEntity.class)
            .filter(Filters.eq("status", MarketState.ACTIVE.name()))
            .stream()
            .toList();
    }
}
