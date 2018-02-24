package cc.akkaha.egg.controllers.model;

import java.util.List;

public class BatchUpdateItemCar {

    private boolean isByUser;
    private Integer car;
    private List<Integer> ids;

    public boolean getIsByUser() {
        return isByUser;
    }

    public void setIsByUser(boolean byUser) {
        isByUser = byUser;
    }

    public Integer getCar() {
        return car;
    }

    public void setCar(Integer car) {
        this.car = car;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
