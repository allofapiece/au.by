package com.epam.au.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class Bet {
    long id;
    long lotId;
    long userId;
    double price;
    Timestamp time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLotId() {
        return lotId;
    }

    public void setLotId(long lotId) {
        this.lotId = lotId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bet bet = (Bet) o;
        return id == bet.id &&
                lotId == bet.lotId &&
                userId == bet.userId &&
                Double.compare(bet.price, price) == 0 &&
                Objects.equals(time, bet.time);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, lotId, userId, price, time);
    }
}
