package com.woodycreations.marketplace.spigot.database.dao.user.entity;

import com.google.common.collect.Sets;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import java.util.Set;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Entity(value = "market_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id private UUID uuid;
    private Set<UUID> transactions = Sets.newHashSet();

    public UserEntity(@NotNull UUID uuid) {
        this.uuid = uuid;
    }

    public void addTransaction(@NotNull UUID transaction) {
        this.transactions.add(transaction);
    }
}
