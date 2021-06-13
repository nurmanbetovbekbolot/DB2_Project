package dbproject.dto;

import java.util.Date;

public class Order {
    private Integer orderId;
    private String description;
    private User client;
    private User manager;
    private Package paket;
    private Date createdDate;

    public Order() {
    }

    public Order(Integer orderId, String description, User client, User manager, Package paket, Date createdDate) {
        this.orderId = orderId;
        this.description = description;
        this.client = client;
        this.manager = manager;
        this.paket = paket;
        this.createdDate = createdDate;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public Package getPaket() {
        return paket;
    }

    public void setPaket(Package paket) {
        this.paket = paket;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}

