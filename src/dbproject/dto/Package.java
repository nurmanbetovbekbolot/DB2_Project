package dbproject.dto;

import java.math.BigDecimal;

public class Package {
    private Integer packageId;
    private String name;
    private String description;
    private Double price;
    private Double discountPrice;

    public Package() {
    }

    public Package(String name, String description, Double price, Double discountPrice) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.discountPrice = discountPrice;
    }

    public Package(Integer packageId, String name, String description, Double price, Double discountPrice) {
        this.packageId = packageId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discountPrice = discountPrice;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price.doubleValue();
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice.doubleValue();
    }

    @Override
    public String toString() {
        return name+" "+price+" "+discountPrice;
    }
}
