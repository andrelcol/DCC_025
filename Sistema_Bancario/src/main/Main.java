package main;

import sistemabancario.Menu;
import sistemabancario.ContaCorrente;
import sistemabancario.Relatorio;
import sistemabancario.ContaPoupanca;

public class Main {

    public static void main(String[] args) {
        ContaCorrente contaCorrenteA = new ContaCorrente();
        ContaPoupanca contaPoupancaA = new ContaPoupanca();
        Relatorio obRelatorioA = new Relatorio();
        Menu jf = new Menu();
        jf.setVisible(true);
    }
}