package sistemabancario;

import sistemabancario.Conexao;
import sistemabancario.ContaCorrente;
import sistemabancario.ContaBancaria;
import sistemabancario.ContaPoupanca;
//import com.google.gson.Gson;
import java.awt.Dimension;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Banco {

    static HashMap contasGuardar = new HashMap<Banco, Banco>();

    public void InserirConta(int conta, Object objeto) {
        contasGuardar.put(conta, objeto);
    }

    public static void ApagarConta(int conta) {
        contasGuardar.remove(conta);
    }

    public static ContaBancaria ProcurarConta(int conta) {

        Iterator<Map.Entry<Object, Object>> itr = contasGuardar.entrySet().iterator();
        if (itr.hasNext()) {

            while (itr.hasNext()) {
                Map.Entry<Object, Object> valores = itr.next();
                if (valores.getKey().equals(conta)) {
                    return (ContaBancaria) valores.getValue();
                }
            }
        }

        return null;

    }

    public static void MostrarTodosDados() {
        Iterator<Map.Entry<Integer, String>> itr = contasGuardar.entrySet().iterator();
        String clientes = "";
        if (itr.hasNext()) {
            while (itr.hasNext()) {
                Map.Entry<Integer, String> valores = itr.next();

                Object conta = Banco.ProcurarConta(valores.getKey());
                if (conta instanceof ContaCorrente) {
                    clientes += "Conta corrente: \n" + ((ContaCorrente) conta).MostrarDados() + "\n\n";
                } else if (conta instanceof ContaPoupanca) {
                    clientes += "Conta poupança: \n" + ((ContaPoupanca) conta).MostrarDados() + "\n\n";
                }

            }
            JTextArea textArea = new JTextArea(clientes);
            JScrollPane scrollPane = new JScrollPane(textArea);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(false);
            textArea.setEditable(false);
            scrollPane.setPreferredSize(new Dimension(500, 500));
            JOptionPane.showMessageDialog(null, scrollPane, "RELATÓRIO GERAL", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "NÃO HÁ CLIENTES REGISTRADOS", "ERROR", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void salvar(Integer cont, String tipo, double saldo) {
        Conexao conecta = new Conexao();
        conecta.getConnection();

        try {
            PreparedStatement pst = conecta.conn.prepareStatement("INSERT INTO Clientes VALUES (?,?,?)");
            pst.setString(1, null);
            pst.setString(2, tipo);
            pst.setDouble(3, saldo);
            pst.execute();

            System.out.println("Conta: " + cont + " inserida.");
        } catch (Exception e) {
            System.out.println("Erro ao inserir conta. Tente novamente.");
        }
    }

    public static void deletarConta(int id) {
        Conexao conecta = new Conexao();
        conecta.getConnection();

        try {
            PreparedStatement pst = conecta.conn.prepareStatement("DELETE FROM Clientes WHERE id = ?");
            pst.setInt(1, id);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Dados apagados");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao apagar dados");
        }
    }

    public static void carregarDados() {
        Conexao conecta = new Conexao();
        conecta.getConnection();

        try {
            PreparedStatement pst = conecta.conn.prepareStatement("SELECT * FROM Clientes");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int cont = Integer.parseInt(rs.getString("id"));
                String tipo = rs.getString("Tipo");
                double saldo = rs.getDouble("saldo");

                if (tipo.equals("Corrente")) {
                    Cadastro.cadastrarCorrente(cont, saldo);
                } else {
                    Cadastro.cadastrarPoupanca(cont, saldo);
                }
            }
            JOptionPane.showMessageDialog(null, "Dados carregados");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Banco de dados offline. Versão do app offline. ");
        }
    }

    public static void atualizarSaldo(int id, double saldo) {
        Conexao conecta = new Conexao();
        conecta.getConnection();

        try {
            PreparedStatement pst = conecta.conn.prepareStatement("UPDATE Clientes SET saldo = ? WHERE id = ?");
            pst.setDouble(1, saldo);
            pst.setInt(2, id);
            pst.execute();

            System.out.println("Saldo atualizado.");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar dados");
        }
    }
}
