package cc.akkaha.egg.model;

public class PriceExtra {

    private String weightAdjust;

    public PriceExtra() {
    }

    public PriceExtra(String weightAdjust) {
        this.weightAdjust = weightAdjust;
    }

    public String getWeightAdjust() {
        return weightAdjust;
    }

    public void setWeightAdjust(String weightAdjust) {
        this.weightAdjust = weightAdjust;
    }
}
