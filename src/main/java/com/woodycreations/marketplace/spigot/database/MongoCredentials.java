package com.woodycreations.marketplace.spigot.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoCredential;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Builder
@Getter
public class MongoCredentials {
    @Builder.Default private String host = "localhost";
    @Builder.Default private int port = 27017;
    @Builder.Default private final String collection = "marketplace";
    @Builder.Default private final String authenticationDatabase = "admin";
    @Builder.Default private final String username = "admin";
    @Builder.Default private final String password = "pa55w0rd";

    public @NotNull ConnectionString getConnectionString() {
        return new ConnectionString(this.getUri());
    }

    public @NotNull MongoCredential getCredentials() {
        return MongoCredential.createCredential(this.getUsername(), this.getAuthenticationDatabase(), this.getPassword().toCharArray());
    }

    public @NotNull String getUri() {
        return "mongodb://" + this.getHost() + ":" + this.getPort();
    }
}
