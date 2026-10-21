package br.com.caelum.contas.controle;

import br.com.caelum.contas.modelo.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManipuladorDeContas {
    private final List<Conta> contas = new ArrayList<>();
    public List<Conta> getContas() { return Collections.unmodifiableList(contas); }

    public Conta criaConta(String titular, int numero, String agencia, String tipo) {
        Conta conta;
        if ("Conta Corrente".equals(tipo)) conta = new ContaCorrente(titular);
        else if ("Conta Poupança".equals(tipo)) conta = new ContaPoupanca(titular);
        else throw new IllegalArgumentException("Selecione um tipo de conta.");
        conta.setNumero(numero);
        conta.setAgencia(agencia);
        contas.add(conta);
        return conta;
    }

    public boolean saca(Conta conta, double valor) { return conta != null && conta.saca(valor); }
    public boolean deposita(Conta conta, double valor) {
        if (conta == null || valor <= 0) return false;
        conta.deposita(valor); return true;
    }
    public boolean transfere(Conta origem, Conta destino, double valor) {
        return origem != null && origem.transfere(valor, destino);
    }
    public Conta buscaPorIdentificador(int id) {
        for (Conta c : contas) if (c.getIdentificador() == id) return c;
        return null;
    }
}
