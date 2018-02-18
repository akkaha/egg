package cc.akkaha.egg.db.service.impl;

import cc.akkaha.egg.db.model.Price;
import cc.akkaha.egg.db.client.PriceMapper;
import cc.akkaha.egg.db.service.PriceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl extends ServiceImpl<PriceMapper, Price> implements PriceService {
    
}
