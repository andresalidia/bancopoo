package br.com.caelum.contas.modelo;

public class Conta {
    private static int contadorIdentificadores = 0;
    private final int identificador;
    private int numero;
    private String titular;
    private String agencia;
    protected double saldo;
    private final Data dataAbertura;

    public Conta() { this(""); }

    public Conta(String titular) {
        this.identificador = ++contadorIdentificadores;
        this.titular = titular;
        this.saldo = 0.0;
        this.dataAbertura = new Data();
    }

    public int getIdentificador() { return identificador; }
    public String getTitular() { return titular; }
    public void setTitular(String titular) { this.titular = titular; }
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }
    public String getAgencia() { return agencia; }
    public void setAgencia(String agencia) { this.agencia = agencia; }
    public double getSaldo() { return saldo; }
    public Data getDataAbertura() { return dataAbertura; }
    public String getTipo() { return "Conta"; }

    public void deposita(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("O depósito deve ser maior que zero.");
        saldo += valor;
    }

    public boolean saca(double valor) {
        if (valor <= 0 || valor > saldo) return false;
        saldo -= valor;
        return true;
    }

    public boolean transfere(double valor, Conta destino) {
        if (destino == null || destino == this || valor <= 0 || valor > saldo) return false;
        if (saca(valor)) { destino.deposita(valor); return true; }
        return false;
    }

    public double getRendimento() { return saldo * 0.1; }

    public String recuperaDadosParaImpressao() {
        return "Identificador: " + identificador
            + "\nTitular: " + titular
            + "\nNúmero: " + numero
            + "\nAgência: " + agencia
            + "\nTipo: " + getTipo()
            + "\nSaldo: R$" + String.format("%.2f", saldo)
            + "\nData de Abertura: " + dataAbertura.formatada();
    }
}
