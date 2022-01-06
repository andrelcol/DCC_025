package sistemabancario;

public class ContaCorrente extends ContaBancaria implements MostrarDados {

    static double taxaOperacao = 8;

    @Override
    public void sacar(double valor) {
        this.setSaldo(this.getSaldo() - (valor + (taxaOperacao / 100)));
    }

    @Override
    public void depositar(double valor) {
        this.setSaldo(this.getSaldo() + valor - (taxaOperacao / 100));
    }

    @Override
    public String MostrarDados() {
        return "Número da conta: " + this.getNumeroConta() + "\n " + "Saldo da conta: " + this.getSaldo() + "\n" + "Taxa de Operação: " + this.taxaOperacao;

    }
}
