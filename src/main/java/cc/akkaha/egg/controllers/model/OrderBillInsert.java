package cc.akkaha.egg.controllers.model;

import cc.akkaha.egg.model.PriceItem;

import java.util.List;

public class OrderBillInsert {

    List<PriceItem> items;
    PriceExtraUpdate priceExtra;

    public List<PriceItem> getItems() {
        return items;
    }

    public void setItems(List<PriceItem> items) {
        this.items = items;
    }

    public PriceExtraUpdate getPriceExtra() {
        return priceExtra;
    }

    public void setPriceExtra(PriceExtraUpdate priceExtra) {
        this.priceExtra = priceExtra;
    }
}
