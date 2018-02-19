package cc.akkaha.egg.service.impl;

import cc.akkaha.egg.db.model.OrderItem;
import cc.akkaha.egg.db.model.Price;
import cc.akkaha.egg.db.service.OrderItemService;
import cc.akkaha.egg.db.service.PriceExtraService;
import cc.akkaha.egg.db.service.PriceService;
import cc.akkaha.egg.model.OrderBill;
import cc.akkaha.egg.service.BillService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private PriceExtraService priceExtraService;

    @Override
    public OrderBill payUserOrder(Integer id, String date) {
        TreeMap<BigDecimal, BigDecimal> priceRange = getPriceRange(date);
        EntityWrapper<OrderItem> orderItemWrapper = new EntityWrapper<>();
        orderItemWrapper.eq(OrderItem.USER, id).orderBy(OrderItem.WEIGHT, true);
        List<OrderItem> orderItems = orderItemService.selectList(orderItemWrapper);
        EntityWrapper<cc.akkaha.egg.db.model.PriceExtra> priceExtraWrapper =
                new EntityWrapper<>();
        priceExtraWrapper.eq(cc.akkaha.egg.db.model.PriceExtra.DAY, date);
        cc.akkaha.egg.db.model.PriceExtra priceExtra =
                priceExtraService.selectOne(priceExtraWrapper);
        return OrderBill.parse(date, orderItems, priceRange, priceExtra);
    }


    @Override
    public OrderBill payCarOrder(Integer id, String date) {
        TreeMap<BigDecimal, BigDecimal> priceRange = getPriceRange(date);
        EntityWrapper<OrderItem> orderItemWrapper = new EntityWrapper<>();
        orderItemWrapper.eq(OrderItem.CAR, id).orderBy(OrderItem.WEIGHT, true);
        List<OrderItem> orderItems = orderItemService.selectList(orderItemWrapper);
        EntityWrapper<cc.akkaha.egg.db.model.PriceExtra> priceExtraWrapper =
                new EntityWrapper<>();
        priceExtraWrapper.eq(cc.akkaha.egg.db.model.PriceExtra.DAY, date);
        cc.akkaha.egg.db.model.PriceExtra priceExtra =
                priceExtraService.selectOne(priceExtraWrapper);
        return OrderBill.parse(date, orderItems, priceRange, priceExtra);
    }

    private TreeMap<BigDecimal, BigDecimal> getPriceRange(String date) {
        TreeMap<BigDecimal, BigDecimal> tree = new TreeMap();
        EntityWrapper<Price> priceWrapper = new EntityWrapper<>();
        priceWrapper.eq(Price.DAY, date);
        priceService.selectList(priceWrapper).forEach(price -> {
            tree.put(price.getWeight(), price.getPrice());
        });
        return tree;
    }
}
