package hellojpa.ex;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ex_Product {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "exProduct")
    private List<Ex_MemberProduct> exMemberProducts = new ArrayList<>();

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
}
