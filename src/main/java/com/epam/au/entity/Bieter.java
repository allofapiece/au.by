package com.epam.au.entity;

import java.sql.Timestamp;
import java.util.Objects;

public class Bieter {
    long id;
    long userId;
    long lotId;
    Timestamp joinTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getLotId() {
        return lotId;
    }

    public void setLotId(long lotId) {
        this.lotId = lotId;
    }

    public Timestamp getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Timestamp joinTime) {
        this.joinTime = joinTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bieter bieter = (Bieter) o;
        return id == bieter.id &&
                userId == bieter.userId &&
                lotId == bieter.lotId &&
                Objects.equals(joinTime, bieter.joinTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, lotId, joinTime);
    }
}
