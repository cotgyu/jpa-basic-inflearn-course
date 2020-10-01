package hellojpa.ex;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ADDRESS")
public class Ex_AddressEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Ex_Address address;

    public Ex_AddressEntity(String city, String street, String zipcode) {
        this.address = new Ex_Address(city, street, zipcode);
    }

    public Ex_AddressEntity(Ex_Address address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ex_Address getAddress() {
        return address;
    }

    public void setAddress(Ex_Address address) {
        this.address = address;
    }
}
