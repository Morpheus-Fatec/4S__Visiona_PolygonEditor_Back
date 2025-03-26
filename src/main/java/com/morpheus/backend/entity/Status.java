package com.morpheus.backend.entity;

public enum Status {
    PENDING("Pendente"),
    APPROVED("Aprovado"),
    UNDER_ANALYSIS("Em Analise"),
    REJECTED("Reprovado"),
    NO_SOLUTION("Sem Solução");

    private final String portugueValue;

    Status(String portugueValue) {
        this.portugueValue = portugueValue;
    }

    public String getPortugueseValue(){
        return portugueValue;
    }

    @Override
    public String toString() {
        return portugueValue;
    }
}
