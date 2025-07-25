package org.fub.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "crew")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrewModel {
    @Id
    @Column(name = "crew_id")
    private Long crewId;
    @Column(name = "crew_name")
    private String crewName;
    @Column(name = "amount")
    private double amount;
    @Column(name = "created_date",updatable = false)
    @CreatedDate
    private Date createdDate;
    @Column(name = "created_by",updatable = false)
    private String createdBy;
    @Column
    private int maxMember;
    @Column
    private int totalMember;
    @Column(name = "modified_date")
    private Date modifiedDate;
    @Column(name = "modified_by")
    private String modifiedBy;
    @Column(name = "month")
    private int months;

    @ManyToMany(targetEntity = UserModel.class)
    @JoinTable(
            name = "crew_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "crew_id")
    )
    private Set<UserModel> users = new HashSet<>();

    public void addUser(UserModel user){
        users.add(user);
    }

}
