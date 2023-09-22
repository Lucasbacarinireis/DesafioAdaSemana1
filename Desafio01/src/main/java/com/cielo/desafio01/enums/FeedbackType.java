package com.cielo.desafio01.enums;

public enum FeedbackType {
    SUGESTAO("Sugestão"),
    ELOGIO("Elogio"),
    CRITICA("Crítica");

    private final String descricao;

    FeedbackType(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}