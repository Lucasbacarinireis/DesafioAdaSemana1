package com.cielo.desafio01.enums;

public enum FeedbackStatus {
    RECEBIDO("Recebido"),
    EM_PROCESSAMENTO("Em Processamento"),
    FINALIZADO("Finalizado");

    private final String descricao;

    FeedbackStatus(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}