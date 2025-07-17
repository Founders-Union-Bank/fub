package org.fub.utils;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PENDING("Pending"), COMPLETED("Completed");

    String name;

    PaymentStatus(String name) {
        this.name = name;
    }
}
