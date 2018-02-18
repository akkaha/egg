package cc.akkaha.egg.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;

public class OrderBill {

    private String date;
    private Integer totalCount;
    private BigDecimal totalWeight;
    private BigDecimal meanWeight;
    private BigDecimal totalPrice;
    private BigDecimal meanPrice;
    private List<BillItem> items;
    private BigDecimal extraPlus;
    private BigDecimal extraMinus;
    private TreeMap<BigDecimal, BigDecimal> priceRange;
    private String remark;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }

    public BigDecimal getMeanWeight() {
        return meanWeight;
    }

    public void setMeanWeight(BigDecimal meanWeight) {
        this.meanWeight = meanWeight;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getMeanPrice() {
        return meanPrice;
    }

    public void setMeanPrice(BigDecimal meanPrice) {
        this.meanPrice = meanPrice;
    }

    public List<BillItem> getItems() {
        return items;
    }

    public void setItems(List<BillItem> items) {
        this.items = items;
    }

    public BigDecimal getExtraPlus() {
        return extraPlus;
    }

    public void setExtraPlus(BigDecimal extraPlus) {
        this.extraPlus = extraPlus;
    }

    public BigDecimal getExtraMinus() {
        return extraMinus;
    }

    public void setExtraMinus(BigDecimal extraMinus) {
        this.extraMinus = extraMinus;
    }

    public TreeMap<BigDecimal, BigDecimal> getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(TreeMap<BigDecimal, BigDecimal> priceRange) {
        this.priceRange = priceRange;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
