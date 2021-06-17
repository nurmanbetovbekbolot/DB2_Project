package dbproject.dto;

import java.util.Date;

public class Order {
    private Integer orderId;
    private String name;
    private String description;
    private Integer clientId;
    private Integer managerId;
    private Integer paketId;
    private Date createdDate;

    public Order() {
    }

    public Order(Integer orderId, String name, String description, Integer clientId, Integer managerId, Integer paketId) {
        this.orderId = orderId;
        this.name = name;
        this.description = description;
        this.clientId = clientId;
        this.managerId = managerId;
        this.paketId = paketId;
    }

    public Order(String name, String description, Integer clientId, Integer managerId, Integer paketId) {
        this.name = name;
        this.description = description;
        this.clientId = clientId;
        this.managerId = managerId;
        this.paketId = paketId;
    }

    public Order(Integer orderId, String name, String description, Integer clientId, Integer managerId, Integer paketId, Date createdDate) {
        this.orderId = orderId;
        this.name = name;
        this.description = description;
        this.clientId = clientId;
        this.managerId = managerId;
        this.paketId = paketId;
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

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getPaketId() {
        return paketId;
    }

    public void setPaketId(Integer paketId) {
        this.paketId = paketId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}

