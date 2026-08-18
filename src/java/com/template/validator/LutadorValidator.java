package com.template.validator;

public class LutadorValidator {

    public static String validarLutador(String nome, String categoria, String genero, String idade, String sequencia) {

        if (nome == null || nome.isBlank()) {
            return "Preencha o nome do lutador.";
        }

        if (categoria == null || categoria.isBlank()) {
            return "Selecione uma categoria.";
        }

        if (genero == null || genero.isBlank()) {
            return "Selecione o gênero.";
        }

        if (idade == null || idade.isBlank()) {
            return "Preencha a idade.";
        }

        if (sequencia == null || sequencia.isBlank()) {
            return "Preencha a sequência de vitórias.";
        }

        try {
            int idadeNumerica = Integer.parseInt(idade);

            if (idadeNumerica <= 0) {
                return "A idade deve ser maior que zero.";
            }

        } catch (NumberFormatException erro) {
            return "A idade deve conter apenas números.";
        }

        try {
            int sequenciaNumerica = Integer.parseInt(sequencia);

            if (sequenciaNumerica < 0) {
                return "A sequência de vitórias não pode ser negativa.";
            }

        } catch (NumberFormatException erro) {
            return "A sequência de vitórias deve conter apenas números.";
        }

        return null;
    }

    public static String validarSelecaoParaExcluir(String id) {

        if (id == null || id.isBlank()) {
            return "Selecione um lutador na tabela para excluir.";
        }

        return null;
    }
}