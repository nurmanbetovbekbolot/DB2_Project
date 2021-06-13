package dbproject.dto;

public class Dienst {
    private Integer dienstId;
    private String name;

    public Dienst() {
    }

    public Dienst(Integer dienstId, String name) {
        this.dienstId = dienstId;
        this.name = name;
    }

    public Integer getDienstId() {
        return dienstId;
    }

    public void setDienstId(Integer dienstId) {
        this.dienstId = dienstId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
