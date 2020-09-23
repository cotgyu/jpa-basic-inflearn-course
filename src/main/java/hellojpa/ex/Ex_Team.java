package hellojpa.ex;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ex_Team extends Ex_BaseEntity{

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "exTeam")
    private List<Ex_Member> members = new ArrayList<>();

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

    public List<Ex_Member> getMembers() {
        return members;
    }

    public void setMembers(List<Ex_Member> members) {
        this.members = members;
    }
}
