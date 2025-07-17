package org.fub.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserGroupsResponse {
    private String userId;
    private List<CrewResponse> crews= new ArrayList<>();

    public void addCrew(CrewResponse response){
        crews.add(response);
    }
}
