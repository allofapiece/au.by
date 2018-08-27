package com.epam.au.entity.lot;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Concrete impl of lot. English auction is one of the
 * auction types.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class EnglishLot extends Lot {
    private double stepPrice;
    private long betTime;

    public double getStepPrice() {
        return stepPrice;
    }

    public void setStepPrice(double stepPrice) {
        this.stepPrice = stepPrice;
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

        EnglishLot that = (EnglishLot) o;

        if (Double.compare(that.stepPrice, stepPrice) != 0) return false;
        return betTime == that.betTime;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), stepPrice, betTime);
    }
}
