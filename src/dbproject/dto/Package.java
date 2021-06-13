package dbproject.dto;

public class Package {
    private Integer packageId;
    private String name;
    private String description;
    private Double price;
    private Double discountprice;

    public Package() {
    }

    public Package(Integer packageId, String name, String description, Double price, Double discountprice) {
        this.packageId = packageId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discountprice = discountprice;
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

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscountprice() {
        return discountprice;
    }

    public void setDiscountprice(Double discountprice) {
        this.discountprice = discountprice;
    }
}
