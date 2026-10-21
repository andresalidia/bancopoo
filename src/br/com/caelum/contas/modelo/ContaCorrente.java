package br.com.caelum.contas.modelo;

public class ContaCorrente extends Conta {
    public ContaCorrente(String titular) { super(titular); }
    @Override public String getTipo() { return "Conta Corrente"; }

    // Conta-corrente cobra R$ 0,10 por saque.
    @Override public boolean saca(double valor) {
        if (valor <= 0 || valor + 0.10 > saldo) return false;
        saldo -= valor + 0.10;
        return true;
    }
}
