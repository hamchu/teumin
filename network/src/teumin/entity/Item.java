package teumin.entity;

import java.io.Serializable;

public class Item implements Serializable {
    private String truckName;
    private String name;
    private Integer price;
    private Bytes image;
    private String introduction;
    private String explanation;

    public Item(String truckName, String name, Integer price, Bytes image, String introduction, String explanation) {
        this.truckName = truckName;
        this.name = name;
        this.price = price;
        this.image = image;
        this.introduction = introduction;
        this.explanation = explanation;
    }

    public String getTruckName() {
        return truckName;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Bytes getImage() {
        return image;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setTruckName(String truckName) {
        this.truckName = truckName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setImage(Bytes image) {
        this.image = image;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
