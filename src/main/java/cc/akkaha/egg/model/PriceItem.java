package cc.akkaha.egg.model;

public class PriceItem {

    private String weight;
    private String price;

    public PriceItem() {
    }

    public PriceItem(String weight, String price) {
        this.weight = weight;
        this.price = price;
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
}
