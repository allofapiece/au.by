package entity.lot;

import entity.AuctionType;
import entity.Product;

import java.util.Date;

/**
 * Class describes the lot of auction. Lot is the main part of the subject
 * area.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public abstract class Lot {
    protected long id;
    protected String name;
    protected String description;
    protected LotStatus status;
    protected AuctionType auctionType;
    protected Date startTime;
    protected Date endTime;
    protected Product product;
    protected int productAmount;
    protected double beginPrice;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LotStatus getStatus() {
        return status;
    }

    public void setStatus(LotStatus status) {
        this.status = status;
    }

    public AuctionType getAuctionType() {
        return auctionType;
    }

    public void setAuctionType(AuctionType auctionType) {
        this.auctionType = auctionType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public double getBeginPrice() {
        return beginPrice;
    }

    public void setBeginPrice(double beginPrice) {
        this.beginPrice = beginPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lot lot = (Lot) o;

        if (id != lot.id) return false;
        if (productAmount != lot.productAmount) return false;
        if (Double.compare(lot.beginPrice, beginPrice) != 0) return false;
        if (name != null ? !name.equals(lot.name) : lot.name != null) return false;
        if (description != null ? !description.equals(lot.description) : lot.description != null) return false;
        if (status != lot.status) return false;
        if (auctionType != lot.auctionType) return false;
        if (startTime != null ? !startTime.equals(lot.startTime) : lot.startTime != null) return false;
        if (endTime != null ? !endTime.equals(lot.endTime) : lot.endTime != null) return false;
        return product != null ? product.equals(lot.product) : lot.product == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (auctionType != null ? auctionType.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + productAmount;
        temp = Double.doubleToLongBits(beginPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
