package hellojpa.jpql;

import javax.persistence.*;

@Entity
public class J_Member {

    @Id @GeneratedValue
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private J_Team j_team;

    public void changeTeam(J_Team j_team){
        this.j_team = j_team;
        j_team.getMembers().add(this);
    }

    @Enumerated(EnumType.STRING)
    private J_MemberType j_type;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public J_Team getJ_team() {
        return j_team;
    }

    public void setJ_team(J_Team j_team) {
        this.j_team = j_team;
    }

    // 양방향 지우자 (j_team 삭제)
    @Override
    public String toString() {
        return "J_Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
