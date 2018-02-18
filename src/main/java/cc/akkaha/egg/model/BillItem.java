package cc.akkaha.egg.model;

public class BillItem {

    private String weight;
    private String price;
    private Integer user;
    private Integer car;

    public BillItem() {

    }

    public BillItem(String weight, String price, Integer user, Integer car) {
        this.weight = weight;
        this.price = price;
        this.user = user;
        this.car = car;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getCar() {
        return car;
    }

    public void setCar(Integer car) {
        this.car = car;
    }
}
