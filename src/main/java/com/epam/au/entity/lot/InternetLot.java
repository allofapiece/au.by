package com.epam.au.entity.lot;

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
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(blitzPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (betTime ^ (betTime >>> 32));
        return result;
    }
}
