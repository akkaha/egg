package cc.akkaha.egg.db.service.impl;

import cc.akkaha.egg.db.model.CarOrder;
import cc.akkaha.egg.db.client.CarOrderMapper;
import cc.akkaha.egg.db.service.CarOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CarOrderServiceImpl extends ServiceImpl<CarOrderMapper, CarOrder> implements CarOrderService {
    
}
