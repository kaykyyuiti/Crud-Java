package com.template.validator;

import com.template.util.DialogUtil;

public class LutadorValidator {

    public static boolean validarLutador(String nome, String categoria, String genero, String idade, String sequencia) {

        if (nome.isEmpty() || categoria.isEmpty() || genero.isEmpty() || idade.isEmpty() || sequencia.isEmpty()) {
            DialogUtil.mostrarErro("Campos Obrigatórios", "Preencha todos os campos antes de prosseguir.");
            return false;
        }

        return true;
    }

    public static boolean validarSelecaoParaExcluir(String id) {

        if (id.isEmpty()) {
            DialogUtil.mostrarErro("Seleção Inválida", "Selecione um lutador na tabela para excluir.");
            return false;
        }

        return true;
    }
}