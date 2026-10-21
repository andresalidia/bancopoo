package br.com.caelum.contas.gui;

import br.com.caelum.contas.controle.ManipuladorDeContas;
import br.com.caelum.contas.modelo.Conta;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaDeContas extends JFrame {
    private final ManipuladorDeContas manipulador = new ManipuladorDeContas();
    private final DefaultTableModel tabelaModel = new DefaultTableModel(new Object[]{"Titular", "Número", "Agência", "Saldo", "Tipo"}, 0) {
        @Override public boolean isCellEditable(int row, int col) { return false; }
    };
    private final JTable tabela = new JTable(tabelaModel);

    public TelaDeContas() {
        super("Contas - Sistema Bancário");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 360); setLocationRelativeTo(null); setLayout(new BorderLayout(8,8));
        add(new JTabbedPane(), BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        JPanel botoes = new JPanel();
        JButton nova = new JButton("Nova Conta"); JButton salvar = new JButton("Salvar Dados");
        JButton operacoes = new JButton("Operações");
        botoes.add(nova); botoes.add(operacoes); botoes.add(salvar); add(botoes, BorderLayout.SOUTH);
        nova.addActionListener(e -> criarConta());
        operacoes.addActionListener(e -> operarConta());
        salvar.addActionListener(e -> JOptionPane.showMessageDialog(this, "As contas ficam disponíveis enquanto a aplicação estiver aberta. Persistência em arquivo não faz parte desta atividade."));
    }

    private void criarConta() {
        JTextField titular = new JTextField(); JTextField numero = new JTextField(); JTextField agencia = new JTextField();
        JRadioButton corrente = new JRadioButton("Conta Corrente", true);
        JRadioButton poupanca = new JRadioButton("Conta Poupança");
        ButtonGroup tipos = new ButtonGroup(); tipos.add(corrente); tipos.add(poupanca);
        JPanel radios = new JPanel(new FlowLayout(FlowLayout.LEFT)); radios.add(corrente); radios.add(poupanca);
        JPanel painel = new JPanel(new GridLayout(0,2,6,6));
        painel.add(new JLabel("Titular:")); painel.add(titular); painel.add(new JLabel("Número:")); painel.add(numero);
        painel.add(new JLabel("Agência:")); painel.add(agencia); painel.add(new JLabel("Tipo:")); painel.add(radios);
        int op = JOptionPane.showConfirmDialog(this, painel, "Nova Conta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (op != JOptionPane.OK_OPTION) return;
        try {
            if (titular.getText().trim().isEmpty() || numero.getText().trim().isEmpty() || agencia.getText().trim().isEmpty())
                throw new IllegalArgumentException("Preencha todos os campos.");
            Conta c = manipulador.criaConta(titular.getText().trim(), Integer.parseInt(numero.getText().trim()), agencia.getText().trim(), corrente.isSelected()?"Conta Corrente":"Conta Poupança");
            atualizarTabela(); JOptionPane.showMessageDialog(this, "Conta criada! Identificador: " + c.getIdentificador());
        } catch (RuntimeException ex) { JOptionPane.showMessageDialog(this, "Não foi possível criar a conta: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); }
    }

    private Conta contaSelecionada() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) { JOptionPane.showMessageDialog(this, "Selecione uma conta na lista."); return null; }
        return manipulador.getContas().get(tabela.convertRowIndexToModel(linha));
    }
    private void operarConta() {
        Conta c = contaSelecionada(); if (c == null) return;
        String[] opcoes = {"Depositar", "Sacar", "Transferir", "Detalhes"};
        String op = (String)JOptionPane.showInputDialog(this, "Escolha uma operação:", "Operações da conta", JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);
        if (op == null) return;
        try {
            if (op.equals("Detalhes")) { JOptionPane.showMessageDialog(this, c.recuperaDadosParaImpressao()); return; }
            String texto = JOptionPane.showInputDialog(this, "Valor da operação:"); if (texto == null) return;
            double valor = Double.parseDouble(texto.trim().replace(',', '.'));
            boolean ok;
            if (op.equals("Depositar")) ok = manipulador.deposita(c, valor);
            else if (op.equals("Sacar")) ok = manipulador.saca(c, valor);
            else {
                String[] destinos = manipulador.getContas().stream().filter(x -> x != c).map(x -> x.getIdentificador()+" - "+x.getTitular()).toArray(String[]::new);
                if (destinos.length == 0) { JOptionPane.showMessageDialog(this, "Crie outra conta para transferir."); return; }
                String destino = (String)JOptionPane.showInputDialog(this, "Conta de destino:", "Transferência", JOptionPane.PLAIN_MESSAGE, null, destinos, destinos[0]);
                if (destino == null) return;
                int id = Integer.parseInt(destino.split(" - ")[0]); ok = manipulador.transfere(c, manipulador.buscaPorIdentificador(id), valor);
            }
            JOptionPane.showMessageDialog(this, ok ? "Operação realizada com sucesso." : "Operação não realizada. Verifique o saldo e o valor."); atualizarTabela();
        } catch (RuntimeException ex) { JOptionPane.showMessageDialog(this, "Valor inválido.", "Erro", JOptionPane.ERROR_MESSAGE); }
    }
    private void atualizarTabela() {
        tabelaModel.setRowCount(0);
        for (Conta c : manipulador.getContas()) tabelaModel.addRow(new Object[]{c.getTitular(), c.getNumero(), c.getAgencia(), String.format("R$ %.2f",c.getSaldo()), c.getTipo()});
    }
}
