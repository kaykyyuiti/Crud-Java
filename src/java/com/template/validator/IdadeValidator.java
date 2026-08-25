package com.template.validator;

public class IdadeValidator implements Validator<String> {
    private final String nomeCampo;
    private final String valor;

    public IdadeValidator(String nomeCampo, String valor) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
    }

    @Override
    public boolean validar(String valorAtual) {
        if (this.valor == null || this.valor.trim().isEmpty()) return true;
        try {
            int idade = Integer.parseInt(this.valor);
            return idade >= 18 && idade <= 100; // Valida idade mínima/máxima
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getMensagemErro() {
        return "O campo " + nomeCampo + " deve conter uma idade válida (entre 18 e 100 anos).";
    }

    @Override
    public String getValor() {
        return this.valor;
    }
}