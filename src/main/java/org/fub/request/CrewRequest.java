package org.fub.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrewRequest {
    @NotNull
    private String crewName;
    @NotNull
    private double amount;
    @NotNull
    private String createdBy;
    @NotNull
    private int maxMember;
    private int months;
}
