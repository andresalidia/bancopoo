package br.com.caelum.contas.gui;

import javax.swing.SwingUtilities;

public class SistemaBancario {
    public static void mostraTela(boolean mostra) {
        if (mostra) SwingUtilities.invokeLater(() -> new TelaDeContas().setVisible(true));
    }
    public static void main(String[] args) { mostraTela(true); }
}
