package com.morpheus.backend.entity;

public enum Status {
    PENDING,
    APPROVED,
    UNDER_ANALYSIS,
    REJECTED,
    NO_SOLUTION;

    public String toString(){
        switch (this) {
            case PENDING:
                return "PENDING";
            case UNDER_ANALYSIS:
                return "UNDER_ANALYSIS";
            case APPROVED:
                return "APPROVED";
            case REJECTED:
                return "REJECTED";
            case NO_SOLUTION:
                return "NO_SOLUTION";
            default: 
                return "";
        }
    }
}
