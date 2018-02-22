package cc.akkaha.egg.controllers;

import cc.akkaha.egg.constants.OrderStatus;
import cc.akkaha.egg.controllers.model.QueryCarOrder;
import cc.akkaha.egg.db.model.CarOrder;
import cc.akkaha.egg.db.model.OrderItem;
import cc.akkaha.egg.db.service.CarOrderService;
import cc.akkaha.egg.db.service.OrderItemService;
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
@RequestMapping("/egg/car-order")
public class CarOrderController {

    @Autowired
    private CarOrderService carOrderService;
    @Autowired
    private BillService billService;
    @Autowired
    private OrderItemService orderItemService;

    @PostMapping("/query")
    public Object query(@RequestBody QueryCarOrder query) {
        ApiRes res = new ApiRes();
        HashMap<String, Object> data = new HashMap<>();
        res.setData(data);
        Wrapper carOrderWrapper = new EntityWrapper<CarOrder>();
        if (StringUtils.isNotEmpty(query.getSerial())) {
            carOrderWrapper.eq(CarOrder.SERIAL, query.getSerial());
        }
        if (StringUtils.isNotEmpty(query.getDriver())) {
            carOrderWrapper.eq(CarOrder.DRIVER, query.getDriver());
        }
        if (StringUtils.isNotEmpty(query.getDriverPhone())) {
            carOrderWrapper.eq(CarOrder.DRIVER_PHONE, query.getDriverPhone());
        }
        if (StringUtils.isNotEmpty(query.getStatus())) {
            carOrderWrapper.eq(CarOrder.STATUS, query.getStatus());
        }
        Page page = carOrderService.selectPage(new Page<CarOrder>(query.getCurrent(),
                        query.getSize(),
                        CarOrder.CREATED_AT, false),
                carOrderWrapper);
        data.put("list", page.getRecords());
        data.put("total", page.getTotal());
        EntityWrapper<OrderItem> orderItemWrapper = new EntityWrapper<>();
        List<CarOrder> records = page.getRecords();
        if (null != records && !records.isEmpty()) {
            List<Integer> carOrderIds = records.stream()
                    .map(CarOrder::getId)
                    .collect(Collectors.toList());
            orderItemWrapper
                    .setSqlSelect(OrderItem.CAR + ",count(" + OrderItem.WEIGHT + ") count")
                    .in(OrderItem.CAR, carOrderIds)
                    .groupBy(OrderItem.CAR);
            HashMap<Object, Object> countMap = new HashMap<>();
            orderItemService.selectMaps(orderItemWrapper).forEach(map -> {
                countMap.put(map.get(OrderItem.CAR), map.get("count"));
            });
            data.put("count", countMap);
        }
        return res;
    }

    @PostMapping("/insert")
    public Object insert(@RequestBody CarOrder order) {
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
    public Object update(@RequestBody CarOrder order) {
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
    public Object delete(@RequestBody CarOrder order) {
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
        CarOrder order = carOrderService.selectById(id);
        HashMap<String, Object> data = new HashMap<>();
        data.put("order", order);
        OrderItem orderItem = new OrderItem();
        EntityWrapper<OrderItem> wrapper = new EntityWrapper<>();
        wrapper.eq(OrderItem.CAR, id).orderBy(OrderItem.CREATED_AT, true);
        List items = orderItem.selectList(wrapper);
        data.put("items", items);
        res.setData(data);
        return res;
    }

    @GetMapping("/pay/{id}")
    public Object pay(@PathVariable("id") String id,
                      @RequestParam(value = "date", required = false) String date) {
        ApiRes res = new ApiRes();
        CarOrder order = carOrderService.selectById(id);
        HashMap<String, Object> data = new HashMap<>();
        data.put("order", order);
        if (OrderStatus.STATUS_FINISHED.equals(order.getStatus())) {
            data.put("bill", JsonUtils.parse(order.getBill(), OrderBill.class));
        } else {
            if (StringUtils.isEmpty(date)) {
                date = DateUtils.currentDate();
            }
            OrderBill orderBill = billService.payCarOrder(order.getId(), date);
            data.put("bill", orderBill);
            data.put("priceExtra", orderBill.getInner().priceExtra);
        }
        res.setData(data);
        return res;
    }
}
