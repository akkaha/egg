package cc.akkaha.egg.model;

import cc.akkaha.egg.db.model.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class OrderBill {

    private String date;
    private Integer totalCount;
    private String totalWeight;
    private String meanWeight;
    private String totalPrice;
    private String meanPrice;
    private PriceExtra priceExtra;
    private List<BillItem> items = new ArrayList<>();
    private TreeMap<String, String> priceRange = new TreeMap<>();
    private String remark = StringUtils.EMPTY;

    @JsonIgnore
    private OrderBillInner inner = new OrderBillInner();

    // TODO price extra weight adjust
    public static OrderBill parse(String date, List<OrderItem> orderItems,
                                  TreeMap<BigDecimal, BigDecimal> priceRange,
                                  cc.akkaha.egg.db.model.PriceExtra priceExtra) {
        OrderBill bill = new OrderBill();
        bill.getInner().priceExtra = priceExtra;
        if (null != priceExtra) {
            bill.setPriceExtra(new PriceExtra(
                    priceExtra.getWeightAdjust().stripTrailingZeros().toPlainString()));
        }
        bill.setDate(date);
        bill.setTotalCount(orderItems.size());
        if (null != priceRange) {
            TreeMap<String, String> billPriceRange = bill.getPriceRange();
            priceRange.forEach((k, v) ->
                    billPriceRange.put(
                            k.stripTrailingZeros().toPlainString(),
                            v.stripTrailingZeros().toPlainString()
                    )
            );
        }
        BigDecimal totalWeight = BigDecimal.ZERO;
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItem item : orderItems) {
            BigDecimal itemWeight = item.getWeight();
            totalWeight = totalWeight.add(itemWeight);
            BigDecimal itemPrice = calcItemPrice(itemWeight, priceRange);
            if (null != itemPrice) {
                bill.items.add(new BillItem(itemWeight.stripTrailingZeros().toPlainString(),
                        itemPrice.stripTrailingZeros().toPlainString(),
                        item.getUser(), item.getCar()));
                totalPrice = totalPrice.add(itemPrice);
            } else {
                bill.items.add(new BillItem(itemWeight.stripTrailingZeros().toPlainString(),
                        StringUtils.EMPTY, item.getUser(), item.getCar()));
            }
        }
        bill.setTotalWeight(totalWeight.stripTrailingZeros().toPlainString());
        bill.setTotalPrice(totalPrice.stripTrailingZeros().toPlainString());
        if (orderItems.isEmpty()) {
            bill.setMeanWeight(BigDecimal.ZERO.stripTrailingZeros().toPlainString());
            bill.setMeanPrice(BigDecimal.ZERO.stripTrailingZeros().toPlainString());
        } else {
            BigDecimal divisor = new BigDecimal(bill.getTotalCount());
            bill.setMeanWeight(totalWeight.divide(divisor, 2, BigDecimal.ROUND_HALF_UP)
                    .stripTrailingZeros().toPlainString());
            bill.setMeanPrice(totalPrice.divide(divisor, 2, BigDecimal.ROUND_HALF_UP)
                    .stripTrailingZeros().toPlainString());
        }
        return bill;
    }

    /**
     * Pl: floor price, Pc: ceiling price, Wf: floor weight, Wc: ceiling weight, Wn: current weight
     * Pl + (Pc - Pl) / (Wc - Wl) * (Wn - Wf)
     */
    public static BigDecimal calcItemPrice(BigDecimal weight, TreeMap<BigDecimal, BigDecimal>
            priceRange) {
        if (null != priceRange) {
            Map.Entry<BigDecimal, BigDecimal> ceilingEntry = priceRange.ceilingEntry(weight);
            Map.Entry<BigDecimal, BigDecimal> floorEntry = priceRange.floorEntry(weight);
            if (null == ceilingEntry || null == floorEntry) {
                return null;
            } else {
                BigDecimal ceilingPrice = ceilingEntry.getValue();
                BigDecimal floorPrice = floorEntry.getValue();
                if (ceilingPrice.equals(floorPrice)) {
                    return ceilingPrice;
                }
                BigDecimal ceilingWeight = ceilingEntry.getKey();
                BigDecimal floorWeight = floorEntry.getKey();
                BigDecimal price = floorPrice.add(
                        ceilingPrice.subtract(floorPrice)
                                .divide(ceilingWeight.subtract(floorWeight), 2,
                                        BigDecimal.ROUND_HALF_UP)
                                .multiply(weight.subtract(floorWeight))
                );
                return price;
            }
        } else {
            return null;
        }
    }

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

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getMeanWeight() {
        return meanWeight;
    }

    public void setMeanWeight(String meanWeight) {
        this.meanWeight = meanWeight;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getMeanPrice() {
        return meanPrice;
    }

    public void setMeanPrice(String meanPrice) {
        this.meanPrice = meanPrice;
    }

    public List<BillItem> getItems() {
        return items;
    }

    public void setItems(List<BillItem> items) {
        this.items = items;
    }

    public TreeMap<String, String> getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(TreeMap<String, String> priceRange) {
        this.priceRange = priceRange;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public PriceExtra getPriceExtra() {
        return priceExtra;
    }

    public void setPriceExtra(PriceExtra priceExtra) {
        this.priceExtra = priceExtra;
    }

    public OrderBillInner getInner() {
        return inner;
    }

    public void setInner(OrderBillInner inner) {
        this.inner = inner;
    }
}
