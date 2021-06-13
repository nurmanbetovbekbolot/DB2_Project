package dbproject.dto;

import java.util.Date;

public class Task {
    private Integer taskId;
    private String name;
    private String description;
    private Dienst dienst;
    private Date createdDate;

    public Task() {
    }

    public Task(Integer taskId, String name, String description, Dienst dienst, Date createdDate) {
        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.dienst = dienst;
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

    public Dienst getDienst() {
        return dienst;
    }

    public void setDienst(Dienst dienst) {
        this.dienst = dienst;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
