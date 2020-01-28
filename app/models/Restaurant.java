package models;

public class Restaurant {

    private long id;
    private String name;
    private String legalEntity;
    private String inn;
    private String address;

    public Restaurant(long id, String name, String legalEntity, String inn, String address) {
        this.id = id;
        this.name = name;
        this.legalEntity = legalEntity;
        this.inn = inn;
        this.address = address;
    }

    public Restaurant() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(String legalEntity) {
        this.legalEntity = legalEntity;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
