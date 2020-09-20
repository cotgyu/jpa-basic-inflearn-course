package hellojpa.ex;

import hellojpa.domain.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Ex_MemberProduct {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Ex_Member exMember;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Ex_Product exProduct;

    private int count;
    private int price;

    private LocalDateTime orderDateTime;
}
