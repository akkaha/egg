package cc.akkaha.egg.model;

import cc.akkaha.egg.util.JsonUtils;

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


    public static void main(String[] args) {
        TreeMap<BigDecimal, BigDecimal> map = new TreeMap<>();
        map.put(new BigDecimal("3.0"), new BigDecimal("3.0"));
        map.put(new BigDecimal("2.0"), new BigDecimal("2.0"));
        map.put(new BigDecimal("1.0"), new BigDecimal("1.0"));

        System.out.println("map = " + JsonUtils.stringfy(map));
        BigDecimal w = new BigDecimal("2");
        System.out.println("map.ceilingEntry(w) = " + map.ceilingEntry(w));
        System.out.println("map.floorEntry(w) = " + map.floorEntry(w));
        System.out.println("map.get(w) = " + map.get(w));
    }
}
