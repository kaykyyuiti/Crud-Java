package com.template.validator;

import com.template.util.DialogUtil;
import java.util.ArrayList;
import java.util.List;

public class LutadorValidator implements ILutadorValidator{

    public boolean validarLutador(String nome, String categoria, String genero, String idade, String sequenciaVitorias) {
        List<Validator<String>> validadores = new ArrayList<>();

        validadores.add(new CampoObrigatorioValidator("Nome", nome));
        validadores.add(new CampoObrigatorioValidator("Categoria", categoria));
        validadores.add(new CampoObrigatorioValidator("Gênero", genero));
        validadores.add(new CampoObrigatorioValidator("Idade", idade));
        validadores.add(new CampoObrigatorioValidator("Sequência de Vitórias", sequenciaVitorias));

        validadores.add(new NumeroValidator("Idade", idade));
        validadores.add(new NumeroValidator("Sequência de Vitórias", sequenciaVitorias));

        validadores.add(new IdadeValidator("Idade", idade));

        validadores.add(new TextoValidator("Nome", nome));
        validadores.add(new TextoValidator("Categoria", categoria));

        for (Validator<String> validador : validadores) {
            if (!validador.validar(validador.getValor())) {
                DialogUtil.mostrarErro("Erro de Validação", validador.getMensagemErro());
                return false;
            }
        }
        return true;
    }

    public static boolean validarSelecaoParaExcluir(Object itemSelecionado) {
        if (itemSelecionado == null) {
            DialogUtil.mostrarErro("Erro de Seleção", "Selecione um lutador na tabela para realizar a exclusão.");
            return false;
        }
        return true;
    }
}