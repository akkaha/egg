package cc.akkaha.egg.controllers.model;

import cc.akkaha.egg.db.model.OrderItem;

public class QueryOrderItem extends OrderItem {

    private Integer current = 1;
    private Integer size = 10;

    public Integer getCurrent() {
        if (null == this.current) {
            return 10;
        } else {
            return this.current;
        }
    }

    public Integer getSize() {
        if (null == this.size) {
            return 10;
        } else {
            return this.size;
        }
    }
}
