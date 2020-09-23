package hellojpa.ex;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ex_Member extends Ex_BaseEntity{

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name ="USERNAME")
    private String username;

//    @Column(name ="TEAM_ID")
//    private Long teamId;

    @ManyToOne // 팀이 1, 멤버가 N
    @JoinColumn(name = "TEAM_ID")
    private Ex_Team exTeam;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Ex_Locker exLocker;

    @OneToMany(mappedBy = "exMember")
    private List<Ex_MemberProduct> exMemberProducts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Ex_Team getTeam() {
        return exTeam;
    }

    public void setTeam(Ex_Team team) {
        this.exTeam = team;
    }
}
