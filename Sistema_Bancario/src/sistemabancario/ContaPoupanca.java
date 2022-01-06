package sistemabancario;

import javax.swing.JOptionPane;

public class ContaPoupanca extends ContaBancaria implements MostrarDados {

    static double limite = -3000;

    @Override
    public void depositar(double valor) {
        this.setSaldo(this.getSaldo() + valor);
    }

    @Override
    public String MostrarDados() {
        return "Número da conta: " + this.getNumeroConta() + "\n" + "Saldo da conta: " + this.getSaldo() + "\n" + "Limite: " + ((this.getSaldo() > 0) ? (-this.limite) : (-this.limite + (this.getSaldo())));

    }

    @Override
    public void sacar(double valor) {
        if (((this.getSaldo() - valor) >= this.limite)) {

            this.setSaldo(this.getSaldo() - valor);

        } else {
            JOptionPane.showMessageDialog(null, "Limite excedito! Limite disponível para saque: " + ((-this.limite) + this.getSaldo()));
        }
    }

}
