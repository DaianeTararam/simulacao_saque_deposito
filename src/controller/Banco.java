package controller;

import java.util.concurrent.Semaphore;

//Um banco deve controlar Saques e Depósitos.
//O sistema pode permitir um Saque e um Depósito Simultâneos, mas nunca 2 Saques ou 2
//Depósitos Simultâneos. Para calcular a transação (Saque ou Depósito), o método deve
//receber o código da conta, o saldo da conta e o valor a ser transacionado. Deve-se montar
//um sistema que considera 20 transações simultâneas enviadas ao sistema (aleatoriamente,
//essas transações podem ser qualquer uma das opções) e tratar todas as transações. Como
//são transações aleatórias, podem ser 20 saques e 0 depósitos ou 19 saques e 1 depósito ou
//18 saques e 2 depósitos ou .... ou 1 saque e 19 depósitos ou 0 saque e 20 depósitos.

public class Banco extends Thread{
	private int codigo;
	private float saldo;
	private float valor;
	private static Semaphore saque = new Semaphore(1);
	private static Semaphore deposito = new Semaphore(1);
	private static Semaphore transacoes = new Semaphore(20);
	private boolean sacando;
	
	
	public Banco(int codigo, float saldo, float valor, boolean sacando) {
		this.codigo = codigo;
		this.valor = valor;
		this.saldo = saldo;
		this.sacando = sacando;
	}

	@Override
	public void run() {
		try {
			transacoes.acquire();
			transacao();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			transacoes.release();
		}
	}

	private void transacao() {
        try {
            if (sacando) {
                saque.acquire();
                if (saldo >= valor) {
                    saldo -= valor;
                    System.out.println("A conta: " + codigo + " sacou R$" + valor);
                } else {
                    System.out.println("Usuário da conta: " + codigo + " tentou sacar R$" + valor + ", mas saldo insuficiente.");
                }                
            } else {
                deposito.acquire();
                saldo += valor;
                System.out.println("A conta: " + codigo + " depositou R$" + valor);
            }
            Thread.sleep(1000);
            System.out.println("O novo saldo da conta: " + codigo + " é de R$" + saldo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
        	saque.release();
        	deposito.release();
        }
	}

}
