package com.epam.au.entity.lot;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Concrete impl of lot. Internet auction is one of the
 * auction types.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class InternetLot extends Lot {
    private double blitzPrice;
    private long betTime;

    public double getBlitzPrice() {
        return blitzPrice;
    }

    public void setBlitzPrice(double blitzPrice) {
        this.blitzPrice = blitzPrice;
    }

    public long getBetTime() {
        return betTime;
    }

    public void setBetTime(long betTime) {
        this.betTime = betTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        InternetLot that = (InternetLot) o;

        if (Double.compare(that.blitzPrice, blitzPrice) != 0) return false;
        return betTime == that.betTime;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), blitzPrice, betTime);
    }
}
