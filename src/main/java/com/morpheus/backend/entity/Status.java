package com.morpheus.backend.entity;

public enum Status {
    PENDING,
    MODIFIED,
    APPROVED,
    REJECTED;

    public String toString(){
        switch (this) {
            case PENDING:
                return "PENDING";
            case MODIFIED:
                return "MODIFIED";
            case APPROVED:
                return "APPROVED";
            case REJECTED:
                return "REJECTED";
            default: 
                return "";
        }
    }
}
