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

    @ManyToOne(fetch = FetchType.EAGER) // 팀이 1, 멤버가 N
    @JoinColumn(name = "TEAM_ID")
    private Ex_Team exTeam;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Ex_Locker exLocker;

    @OneToMany(mappedBy = "exMember")
    private List<Ex_MemberProduct> exMemberProducts = new ArrayList<>();

    // Period
    @Embedded
    private Ex_Period workPeriod;

    // 주소
    @Embedded
    private Ex_Address homeAddress;

    // 주소
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "WORK_CITY")),
            @AttributeOverride(name = "street", column = @Column(name = "WORK_STREET")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "WORK_ZIPCODE"))
            }
    )
    private Ex_Address workAddress;



    public Ex_Team getExTeam() {
        return exTeam;
    }

    public void setExTeam(Ex_Team exTeam) {
        this.exTeam = exTeam;
    }

    public Ex_Locker getExLocker() {
        return exLocker;
    }

    public void setExLocker(Ex_Locker exLocker) {
        this.exLocker = exLocker;
    }

    public List<Ex_MemberProduct> getExMemberProducts() {
        return exMemberProducts;
    }

    public void setExMemberProducts(List<Ex_MemberProduct> exMemberProducts) {
        this.exMemberProducts = exMemberProducts;
    }

    public Ex_Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Ex_Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Ex_Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Ex_Address homeAddress) {
        this.homeAddress = homeAddress;
    }

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
