package com.assessment.authentication.client.dto;

import java.io.Serializable;
import java.util.UUID;

public class AccountDto implements Serializable {

    public static final long serialVersionUID = -1883665823274855385L;

    private String iban;
    private UUID ownerId;

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }
}
