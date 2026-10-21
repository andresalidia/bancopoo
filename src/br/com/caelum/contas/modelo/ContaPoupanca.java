package br.com.caelum.contas.modelo;

public class ContaPoupanca extends Conta {
    public ContaPoupanca(String titular) { super(titular); }
    @Override public String getTipo() { return "Conta Poupança"; }
}
