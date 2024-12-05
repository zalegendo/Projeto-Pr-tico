/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancoguirework;

/**
 *
 * @author Yulo
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class GUI extends JFrame {
    private ArrayList<Conta> contas = new ArrayList<>();
    private JTable table;
    private DefaultTableModel tableModel;

    public GUI() {
        setTitle("Banco - Sistema de Contas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Configuração da tabela
        tableModel = new DefaultTableModel(new Object[]{"Número", "Saldo", "Tipo"}, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel panel = new JPanel();
        JButton criarContaButton = new JButton("Criar Conta");
        JButton depositarButton = new JButton("Depositar");
        JButton sacarButton = new JButton("Sacar");
        JButton rendimentoButton = new JButton("Aplicar Rendimento");

        panel.add(criarContaButton);
        panel.add(depositarButton);
        panel.add(sacarButton);
        panel.add(rendimentoButton);

        add(panel, BorderLayout.SOUTH);

        // Ações dos botões
        criarContaButton.addActionListener(e -> criarConta());
        depositarButton.addActionListener(e -> depositar());
        sacarButton.addActionListener(e -> sacar());
        rendimentoButton.addActionListener(e -> aplicarRendimento());
    }

    private void criarConta() {
        String[] tipos = {"Corrente", "Poupança", "Especial"};
        String tipo = (String) JOptionPane.showInputDialog(this, "Selecione o tipo de conta:",
                "Criar Conta", JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);

        if (tipo != null) {
            String numero = JOptionPane.showInputDialog(this, "Número da conta:");
            double saldoInicial = Double.parseDouble(JOptionPane.showInputDialog(this, "Saldo inicial:"));

            Conta conta = null;
            switch (tipo) {
                case "Corrente":
                    conta = new ContaCorrente(numero, saldoInicial);
                    break;
                case "Poupança":
                    double rendimento = Double.parseDouble(JOptionPane.showInputDialog(this, "Rendimento (%):"));
                    conta = new ContaPoupanca(numero, saldoInicial, rendimento);
                    break;
                case "Especial":
                    double limite = Double.parseDouble(JOptionPane.showInputDialog(this, "Limite especial:"));
                    conta = new ContaEspecial(numero, saldoInicial, limite);
                    break;
                default:
                    break;
            }

            if (conta != null) {
                contas.add(conta);
                tableModel.addRow(new Object[]{numero, saldoInicial, tipo});
            }
        }
    }

    private void depositar() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            double valor = Double.parseDouble(JOptionPane.showInputDialog(this, "Valor a depositar:"));
            Conta conta = contas.get(selectedRow);
            conta.depositar(valor);
            tableModel.setValueAt(conta.getSaldo(), selectedRow, 1);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma conta na tabela.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sacar() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            double valor = Double.parseDouble(JOptionPane.showInputDialog(this, "Valor a sacar:"));
            Conta conta = contas.get(selectedRow);
            if (conta.sacar(valor)) {
                tableModel.setValueAt(conta.getSaldo(), selectedRow, 1);
            } else {
                JOptionPane.showMessageDialog(this, "Saldo insuficiente ou limite excedido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma conta na tabela.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void aplicarRendimento() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Conta conta = contas.get(selectedRow);
            if (conta instanceof ContaPoupanca) {
                ((ContaPoupanca) conta).aplicarRendimento();
                tableModel.setValueAt(conta.getSaldo(), selectedRow, 1);
            } else {
                JOptionPane.showMessageDialog(this, "Apenas contas poupança têm rendimento.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma conta na tabela.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI bancoGUI = new GUI();
            bancoGUI.setVisible(true);
        });
    }
}