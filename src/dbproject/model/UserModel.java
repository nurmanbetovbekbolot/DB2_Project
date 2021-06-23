package dbproject.model;

public class UserModel {
    Integer id;
    String role;

    public UserModel() {
    }

    public UserModel(Integer id, String role) {
        this.id = id;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}
