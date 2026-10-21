package br.com.caelum.contas.modelo;

import java.time.LocalDate;

public class Data {
    int dia;
    int mes;
    int ano;

    public Data() {
        LocalDate hoje = LocalDate.now();
        this.dia = hoje.getDayOfMonth();
        this.mes = hoje.getMonthValue();
        this.ano = hoje.getYear();
    }

    public String formatada() {
        return String.format("%02d/%02d/%04d", dia, mes, ano);
    }
}
