package hellojpa.jpql;

import javax.persistence.*;

@Entity
@Table(name = "ORDERS")
public class J_Order {

    @Id @GeneratedValue
    private Long id;
    private int orderAmount;

    @Embedded
    private J_Address j_address;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private J_Product j_product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public J_Address getJ_address() {
        return j_address;
    }

    public void setJ_address(J_Address j_address) {
        this.j_address = j_address;
    }

    public J_Product getJ_product() {
        return j_product;
    }

    public void setJ_product(J_Product j_product) {
        this.j_product = j_product;
    }
}
