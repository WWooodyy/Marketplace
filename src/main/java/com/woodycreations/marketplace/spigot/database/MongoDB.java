package com.woodycreations.marketplace.spigot.database;

import com.google.common.collect.Maps;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.MapperOptions;
import java.util.Map;
import lombok.Getter;
import org.bson.UuidRepresentation;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;

@Getter
public class MongoDB {
    private final MongoDatabase mongoDatabase;
    private final Datastore datastore;
    private final Map<Class<?>, Object> daos = Maps.newHashMap();

    public MongoDB(@NotNull MongoCredentials credentials) {
        final MongoClient client = MongoClients.create(MongoClientSettings.builder()
            .applicationName("Marketplace")
            .uuidRepresentation(UuidRepresentation.STANDARD)
            .credential(credentials.getCredentials())
            .applyConnectionString(credentials.getConnectionString())
            .build());

        this.mongoDatabase = client.getDatabase(credentials.getCollection());
        this.datastore = Morphia.createDatastore(client, credentials.getCollection(), MapperOptions
            .builder()
            .storeNulls(true)
            .storeEmpties(true)
            .ignoreFinals(false)
            .build());

        this.ensureConnections();
    }

    private void ensureConnections() {
        final long start = System.currentTimeMillis();
        final Bson ping = new BasicDBObject("ping", "1");

        if (!this.mongoDatabase.runCommand(ping).containsKey("ok")) {
            throw new IllegalStateException("Failed to connect to MongoDB");
        } else {
            final long ms = System.currentTimeMillis() - start;
            System.out.println("Successfully connected to MongoDB: " + ms + "ms");
        }
    }

    public void registerDAO(@NotNull Object dao) {
        this.daos.put(dao.getClass(), dao);
    }

    public <T> @NotNull T getDAO(@NotNull Class<T> daoClass) {
        final T dao = daoClass.cast(this.daos.get(daoClass));

        if (dao == null) {
            throw new IllegalStateException("DAO not registered");
        }

        return dao;
    }
}
