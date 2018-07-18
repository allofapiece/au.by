package com.epam.au.entity.lot;

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
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(stepPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (betTime ^ (betTime >>> 32));
        return result;
    }
}
