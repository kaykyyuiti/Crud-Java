package com.template.validator;

public class TextoValidator implements Validator<String> {
    private final String nomeCampo;
    private final String valor;

    public TextoValidator(String nomeCampo, String valor) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
    }

    @Override
    public boolean validar(String valorAtual) {
        if (this.valor == null || this.valor.trim().isEmpty()) return true;
        return this.valor.matches("^[a-zA-ZÀ-ÿ\\s]+$");
    }

    @Override
    public String getMensagemErro() {
        return "O campo " + nomeCampo + " deve conter apenas letras.";
    }

    @Override
    public String getValor() {
        return this.valor;
    }
}