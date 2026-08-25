package com.template.validator;

public class NumeroValidator implements Validator<String> {
    private final String nomeCampo;
    private final String valor;

    public NumeroValidator(String nomeCampo, String valor) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
    }

    @Override
    public boolean validar(String valorAtual) {
        if (this.valor == null || this.valor.trim().isEmpty()) return true; // Deixa o CampoObrigatorio tratar se for vazio
        try {
            Integer.parseInt(this.valor);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getMensagemErro() {
        return "O campo " + nomeCampo + " deve conter apenas números inteiros válidos.";
    }

    @Override
    public String getValor() {
        return this.valor;
    }
}