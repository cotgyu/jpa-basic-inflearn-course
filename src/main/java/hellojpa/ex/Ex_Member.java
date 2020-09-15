package hellojpa.ex;

import javax.persistence.*;

@Entity
public class Ex_Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name ="USERNAME")
    private String username;

//    @Column(name ="TEAM_ID")
//    private Long teamId;

    @ManyToOne // 팀이 1, 멤버가 N
    @JoinColumn(name = "TEAM_ID")
    private Ex_Team team;

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
        return team;
    }

    public void setTeam(Ex_Team team) {
        this.team = team;
    }
}
