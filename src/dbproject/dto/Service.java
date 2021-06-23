package dbproject.dto;

public class Service {
    private Integer serviceId;
    private String name;

    public Service() {
    }

    public Service(Integer serviceId, String name) {
        this.serviceId = serviceId;
        this.name = name;
    }

    public Service(String name) {
        this.name = name;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return serviceId+" "+name;
    }
}
