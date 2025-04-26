package com.morpheus.backend.entity;

public enum Status {
    PENDING("Pendente", "Pending"),
    APPROVED("Aprovado", "Approved"),
    UNDER_ANALYSIS("Em Análise", "Under Analysis"),
    REJECTED("Reprovado", "Rejected"),
    NO_SOLUTION("Sem Solução", "No Solution");

    private final String portugueseValue;
    private final String englishValue;

    Status(String portugueseValue, String englishValue) {
        this.portugueseValue = portugueseValue;
        this.englishValue = englishValue;
    }

    public String getPortugueseValue() {
        return portugueseValue;
    }

    public String getEnglishValue() {
        return englishValue;
    }

    @Override
    public String toString() {
        return portugueseValue;
    }

    public static Status fromPortuguese(String value) {
        for (Status status : Status.values()) {
            if (status.getPortugueseValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + value);
    }

    public static String fromEnglishToPortuguese(String englishValue) {
        for (Status status : Status.values()) {
            if (status.getEnglishValue().equalsIgnoreCase(englishValue)) {
                return status.getPortugueseValue();
            }
        }
        throw new IllegalArgumentException("Status inválido em inglês: " + englishValue);
    }
}
