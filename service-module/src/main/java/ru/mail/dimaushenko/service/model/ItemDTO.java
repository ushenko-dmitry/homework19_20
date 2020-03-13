package ru.mail.dimaushenko.service.model;

public class ItemDTO {

    private Long id;
    private String name;
    private ItemStatusDTO status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStatusDTO getStatus() {
        return status;
    }

    public void setStatus(ItemStatusDTO status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ItemDTO{" + "id=" + id + ", name=" + name + ", status=" + status + '}';
    }

}
