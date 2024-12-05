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
abstract class Conta {
    protected String numero;
    protected double saldo;

    public Conta(String numero, double saldoInicial) {
        this.numero = numero;
        this.saldo = saldoInicial;
    }

    public String getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public abstract boolean sacar(double valor);

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
        }
    }
}

class ContaCorrente extends Conta {
    public ContaCorrente(String numero, double saldoInicial) {
        super(numero, saldoInicial);
    }

    @Override
    public boolean sacar(double valor) {
        if (saldo >= valor) {
            saldo -= valor;
            return true;
        }
        return false;
    }
}

class ContaEspecial extends Conta {
    private double limite;

    public ContaEspecial(String numero, double saldoInicial, double limite) {
        super(numero, saldoInicial);
        this.limite = limite;
    }

    @Override
    public boolean sacar(double valor) {
        if (saldo + limite >= valor) {
            saldo -= valor;
            return true;
        }
        return false;
    }
}

class ContaPoupanca extends Conta {
    private double rendimento;

    public ContaPoupanca(String numero, double saldoInicial, double rendimento) {
        super(numero, saldoInicial);
        this.rendimento = rendimento;
    }

    public void aplicarRendimento() {
        saldo += saldo * rendimento / 100;
    }

    @Override
    public boolean sacar(double valor) {
        if (saldo >= valor) {
            saldo -= valor;
            return true;
        }
        return false;
    }
}
