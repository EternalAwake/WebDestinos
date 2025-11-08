package com.senai.desafio_II.model;

public class DestinoModel {

    private Long id;
    private String nome;
    private String localizacao;
    private String descricao;
    private Double precoMedioPacote;
    private Double notaMedia;
    private Integer totalAvaliacoes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPrecoMedioPacote() {
        return precoMedioPacote;
    }

    public void setPrecoMedioPacote(Double precoMedioPacote) {
        this.precoMedioPacote = precoMedioPacote;
    }

    public Double getNotaMedia() {
        return notaMedia;
    }

    public void setNotaMedia(Double notaMedia) {
        this.notaMedia = notaMedia;
    }

    public Integer getTotalAvaliacoes() {
        return totalAvaliacoes;
    }

    public void setTotalAvaliacoes(Integer totalAvaliacoes) {
        this.totalAvaliacoes = totalAvaliacoes;
    }

    public DestinoModel(Long id, String nome, String localizacao, String descricao, Double precoMedioPacote) {
        this.id = id;
        this.nome = nome;
        this.localizacao = localizacao;
        this.descricao = descricao;
        this.precoMedioPacote = precoMedioPacote;
        this.notaMedia = 0.0;
        this.totalAvaliacoes = 0;
    }

}
