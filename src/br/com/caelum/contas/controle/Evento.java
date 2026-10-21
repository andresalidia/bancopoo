package br.com.caelum.contas.controle;

import javax.swing.*;
import java.util.Map;

/** Pequeno adaptador para os campos da interface, inspirado na API Evento do material. */
public class Evento {
    private final Map<String, JComponent> campos;
    public Evento(Map<String, JComponent> campos) { this.campos = campos; }
    public String getString(String nome) {
        JComponent c = campos.get(nome);
        if (c instanceof JTextField) return ((JTextField)c).getText().trim();
        if (c instanceof JComboBox) return String.valueOf(((JComboBox<?>)c).getSelectedItem());
        return "";
    }
    public int getInt(String nome) { return Integer.parseInt(getString(nome)); }
    public double getDouble(String nome) { return Double.parseDouble(getString(nome).replace(',', '.')); }
    public String getSelecionadoRadio(String nome) {
        JComponent c = campos.get(nome);
        return getString(nome);
    }
}
