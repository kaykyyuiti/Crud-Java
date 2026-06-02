package com.template;

public class CampeaoBrasileiroDTO {

    private int id;
    private String nome;
    private String categoria;
    private String genero;
    private int idade;
    private int sequenciaVitorias;

    public CampeaoBrasileiroDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getSequenciaVitorias() {
        return sequenciaVitorias;
    }

    public void setSequenciaVitorias(int sequenciaVitorias) {
        this.sequenciaVitorias = sequenciaVitorias;
    }
}