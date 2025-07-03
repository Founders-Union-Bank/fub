package org.fub.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupRequest {
    @NotNull
    private String groupName;
    private double initialAmount;
}
