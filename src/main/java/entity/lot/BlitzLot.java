package entity.lot;

/**
 * Concrete implementation of lot. Blitz auction is one of the
 * auction types.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class BlitzLot extends Lot {
    private int minPeopleAmount;
    private int maxPeopleAmount;
    private double blitzPrice;
    private int roundAmount;
    private long roundTime;
    private int outgoingAmount;

    public int getMinPeopleAmount() {
        return minPeopleAmount;
    }

    public void setMinPeopleAmount(int minPeopleAmount) {
        this.minPeopleAmount = minPeopleAmount;
    }

    public int getMaxPeopleAmount() {
        return maxPeopleAmount;
    }

    public void setMaxPeopleAmount(int maxPeopleAmount) {
        this.maxPeopleAmount = maxPeopleAmount;
    }

    public double getBlitzPrice() {
        return blitzPrice;
    }

    public void setBlitzPrice(double blitzPrice) {
        this.blitzPrice = blitzPrice;
    }

    public int getRoundAmount() {
        return roundAmount;
    }

    public void setRoundAmount(int roundAmount) {
        this.roundAmount = roundAmount;
    }

    public long getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(long roundTime) {
        this.roundTime = roundTime;
    }

    public int getOutgoingAmount() {
        return outgoingAmount;
    }

    public void setOutgoingAmount(int outgoingAmount) {
        this.outgoingAmount = outgoingAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        BlitzLot blitzLot = (BlitzLot) o;

        if (minPeopleAmount != blitzLot.minPeopleAmount) return false;
        if (maxPeopleAmount != blitzLot.maxPeopleAmount) return false;
        if (Double.compare(blitzLot.blitzPrice, blitzPrice) != 0) return false;
        if (roundAmount != blitzLot.roundAmount) return false;
        if (roundTime != blitzLot.roundTime) return false;
        return outgoingAmount == blitzLot.outgoingAmount;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + minPeopleAmount;
        result = 31 * result + maxPeopleAmount;
        temp = Double.doubleToLongBits(blitzPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + roundAmount;
        result = 31 * result + (int) (roundTime ^ (roundTime >>> 32));
        result = 31 * result + outgoingAmount;
        return result;
    }
}