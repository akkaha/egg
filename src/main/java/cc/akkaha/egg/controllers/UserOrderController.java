package cc.akkaha.egg.controllers;

import cc.akkaha.egg.constants.OrderStatus;
import cc.akkaha.egg.controllers.model.QueryUserOrder;
import cc.akkaha.egg.db.model.CarOrder;
import cc.akkaha.egg.db.model.OrderItem;
import cc.akkaha.egg.db.model.UserOrder;
import cc.akkaha.egg.db.service.OrderItemService;
import cc.akkaha.egg.db.service.UserOrderService;
import cc.akkaha.egg.model.ApiRes;
import cc.akkaha.egg.model.OrderBill;
import cc.akkaha.egg.service.BillService;
import cc.akkaha.egg.util.DateUtils;
import cc.akkaha.egg.util.JsonUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/egg/user-order")
public class UserOrderController {

    @Autowired
    private UserOrderService userOrderService;
    @Autowired
    private BillService billService;
    @Autowired
    private OrderItemService orderItemService;

    @PostMapping("/query")
    public Object query(@RequestBody QueryUserOrder query) {
        ApiRes res = new ApiRes();
        HashMap<String, Object> data = new HashMap<>();
        res.setData(data);
        Wrapper userOrderWrapper = new EntityWrapper<UserOrder>();
        if (StringUtils.isNotEmpty(query.getSeller())) {
            userOrderWrapper.eq(UserOrder.SELLER, query.getSeller());
        }
        if (StringUtils.isNotEmpty(query.getPhone())) {
            userOrderWrapper.eq(UserOrder.PHONE, query.getPhone());
        }
        if (StringUtils.isNotEmpty(query.getStatus())) {
            userOrderWrapper.eq(UserOrder.STATUS, query.getStatus());
        }
        Page page = userOrderService.selectPage(new Page<UserOrder>(query.getCurrent(), query
                        .getSize(),
                        UserOrder.CREATED_AT, false),
                userOrderWrapper);
        data.put("list", page.getRecords());
        data.put("total", page.getTotal());
        EntityWrapper<OrderItem> orderItemWrapper = new EntityWrapper<>();
        List<UserOrder> records = page.getRecords();
        if (null != records && !records.isEmpty()) {
            List<Integer> userOrderIds = records.stream().map(UserOrder::getId).collect(Collectors
                    .toList());
            orderItemWrapper
                    .setSqlSelect(OrderItem.USER + ",count(" + OrderItem.WEIGHT + ") count")
                    .in(OrderItem.USER, userOrderIds)
                    .groupBy(OrderItem.USER);
            HashMap<Object, Object> countMap = new HashMap<>();
            orderItemService.selectMaps(orderItemWrapper).forEach(map -> {
                countMap.put(map.get(OrderItem.USER), map.get("count"));
            });
            data.put("count", countMap);
        }
        res.setData(data);
        return res;
    }

    @PostMapping("/insert")
    public Object insert(@RequestBody UserOrder order) {
        ApiRes res = new ApiRes();
        order.setStatus(OrderStatus.STATUS_NEW);
        boolean ret = order.insert();
        if (ret) {
            res.setData(order);
        } else {
            res.setMsg("创建失败!");
        }
        return res;
    }

    @PostMapping("/update")
    public Object update(@RequestBody UserOrder order) {
        ApiRes res = new ApiRes();
        boolean ret = order.updateById();
        if (ret) {
            res.setData(order);
        } else {
            res.setMsg("更新失败!");
        }
        return res;
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody UserOrder order) {
        ApiRes res = new ApiRes();
        boolean ret = order.deleteById();
        if (ret) {
            res.setData(order);
        } else {
            res.setMsg("操作失败!");
        }
        return res;
    }

    @GetMapping("/detail/{id}")
    public Object detail(@PathVariable("id") String id) {
        ApiRes res = new ApiRes();
        UserOrder order = userOrderService.selectById(id);
        HashMap<String, Object> data = new HashMap<>();
        data.put("order", order);
        OrderItem orderItem = new OrderItem();
        EntityWrapper<OrderItem> wrapper = new EntityWrapper<>();
        wrapper.eq(OrderItem.USER, id).orderBy(OrderItem.CREATED_AT, true);
        List items = orderItem.selectList(wrapper);
        data.put("items", items);
        if (null != order.getCar()) {
            CarOrder carOrder = new CarOrder();
            carOrder.setId(order.getCar());
            data.put("car", carOrder.selectById());
        }
        res.setData(data);
        return res;
    }

    @GetMapping("/pay/{id}")
    public Object pay(@PathVariable("id") String id,
                      @RequestParam(value = "date", required = false) String date) {
        ApiRes res = new ApiRes();
        UserOrder order = userOrderService.selectById(id);
        HashMap<String, Object> data = new HashMap<>();
        data.put("order", order);
        if (null != order.getCar()) {
            CarOrder carOrder = new CarOrder();
            carOrder.setId(order.getCar());
            data.put("car", carOrder.selectById());
        }
        if (OrderStatus.STATUS_FINISHED.equals(order.getStatus())) {
            data.put("bill", JsonUtils.parse(order.getBill(), OrderBill.class));
        } else {
            if (StringUtils.isEmpty(date)) {
                date = DateUtils.currentDate();
            }
            OrderBill orderBill = billService.payUserOrder(order.getId(), date);
            data.put("bill", orderBill);
            data.put("priceExtra", orderBill.getInner().priceExtra);
        }
        res.setData(data);
        return res;
    }
}
