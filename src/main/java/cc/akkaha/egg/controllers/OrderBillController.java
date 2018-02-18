package cc.akkaha.egg.controllers;

import cc.akkaha.egg.db.model.Price;
import cc.akkaha.egg.db.service.PriceService;
import cc.akkaha.egg.model.ApiCode;
import cc.akkaha.egg.model.ApiRes;
import cc.akkaha.egg.model.PriceItem;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/egg/order-bill")
public class OrderBillController {

    @Autowired
    private PriceService priceService;

    @GetMapping("/query/{day}")
    public Object query(@PathVariable("day") String day) {
        ApiRes res = new ApiRes();
        EntityWrapper<Price> wrapper = new EntityWrapper<>();
        wrapper.eq(Price.DAY, day).orderBy(Price.ID, true);
        List data = priceService.selectList(wrapper);
        res.setData(data);
        return res;
    }

    @PostMapping("/insert/{day}")
    public Object insert(@PathVariable("day") String day, @RequestBody List<PriceItem> items) {
        ApiRes res = new ApiRes();
        if (null != items && !items.isEmpty()) {
            ArrayList<Price> prices = new ArrayList<>();
            res.setData(prices);
            items.forEach(item -> {
                if (StringUtils.isNoneEmpty(item.getPrice(), item.getWeight())) {
                    Price price = new Price();
                    price.setDay(day);
                    price.setPrice(new BigDecimal(item.getPrice()));
                    price.setWeight(new BigDecimal(item.getWeight()));
                    prices.add(price);
                }
            });
            if (!prices.isEmpty()) {
                EntityWrapper<Price> delWrapper = new EntityWrapper<>();
                delWrapper.eq(Price.DAY, day);
                boolean ret = priceService.delete(delWrapper);
                if (ret) {
                    ret = priceService.insertBatch(prices);
                }
                if (!ret) {
                    res.setCode(ApiCode.ERROR);
                    res.setMsg("操作失败");
                }
            }
        } else {
            EntityWrapper<Price> delWrapper = new EntityWrapper<>();
            delWrapper.eq(Price.DAY, day);
            boolean ret = priceService.delete(delWrapper);
            if (!ret) {
                res.setCode(ApiCode.ERROR);
                res.setMsg("操作失败");
            }
        }
        return res;
    }
}