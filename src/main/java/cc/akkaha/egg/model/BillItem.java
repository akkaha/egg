package cc.akkaha.egg.model;

import java.math.BigDecimal;

public class BillItem {

    private BigDecimal weight;
    private BigDecimal price;

    public BillItem() {

    }

    public BillItem(BigDecimal weight, BigDecimal price) {
        this.weight = weight;
        this.price = price;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
