package dbproject.dto;

import java.util.Date;

public class Task {
    private Integer taskId;
    private String name;
    private String description;
    private Integer service;
    private Date createdDate;

    public Task() {
    }

    public Task(Integer taskId, String name, String description, Integer service) {
        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.service = service;
    }

    public Task(String name, String description, Integer service) {
        this.name = name;
        this.description = description;
        this.service = service;
    }

    public Task(Integer taskId, String name, String description, Integer service, Date createdDate) {
        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.service = service;
        this.createdDate = createdDate;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
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

    public Integer getService() {
        return service;
    }

    public void setService(Integer service) {
        this.service = service;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
