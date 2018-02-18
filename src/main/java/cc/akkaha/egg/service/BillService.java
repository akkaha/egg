package cc.akkaha.egg.service;

import cc.akkaha.egg.model.OrderBill;

public interface BillService {

    OrderBill payUserOrder(Integer id, String date);

    OrderBill payCarOrder(Integer id, String date);
}
