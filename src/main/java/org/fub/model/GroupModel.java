package org.fub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity(name = "user_group")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupModel {
    @Id
    @Column(name = "group_id")
    private Long groupId;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "initial_amount")
    private double initialAmount;
    @Column(name = "created_date",updatable = false)
    @CreatedDate
    private Date createdDate;
    @Column(name = "created_by",updatable = false)
    private String createdBy;
    @Column(name = "modified_date")
    private Date modifiedDate;
    @Column(name = "modified_by")
    private String modifiedBy;
}
