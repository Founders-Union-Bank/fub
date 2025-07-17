package org.fub.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrewResponse {
    private Long crewId;
    private String crewName;
    private double amount;
    private Date createdDate;
    private String createdBy;
    private Date modifiedDate;
    private String modifiedBy;
    private int totalMember;
    private int maxMember;
    private int months;
//    private List<UserResponse> users = new ArrayList<>();
//
//    public void addUser(UserResponse user){
//        users.add(user);
//    }
//
//    public void addUsers(List<UserResponse> user){
//        users.addAll(user);
//    }
}
