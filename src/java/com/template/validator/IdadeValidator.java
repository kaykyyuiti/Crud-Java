package com.template.validator;

public class IdadeValidator {

    public static boolean validarIdade(String idade) {

        if (idade == null || idade.isEmpty()) {
            return false;
        }

        try {
            int idadeNumerica = Integer.parseInt(idade);

            return idadeNumerica > 0 && idadeNumerica <= 100;

        } catch (NumberFormatException erro) {
            return false;
        }
    }
}