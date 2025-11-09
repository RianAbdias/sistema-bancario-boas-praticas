public class ContaBancaria {
    private String numeroConta;
    private double saldo;
    private TipoConta tipoConta;
    private Cliente cliente;
    
    public ContaBancaria(String numeroConta, TipoConta tipo, Cliente cliente, double saldoInicial) {
        this.numeroConta = numeroConta;
        this.tipoConta = tipo;
        this.cliente = cliente;
        this.saldo = saldoInicial;
    }
    public String getNumeroConta() {
        return numeroConta;
    }
    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(double novoSaldo) {
        saldo = novoSaldo;
    }
    public TipoConta getTipoConta() {
        return tipoConta;
    }
    public Cliente getCliente() {
        return cliente;
    }

    void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            System.out.println("Depósito realizado.");
        } else {
            System.out.println("Valor inválido.");
        }
    }
    void sacar(double valor) {
        if (valor > 0) {
            if (valor <= saldo) {
                saldo -= valor;
                System.out.println("Saque realizado.");
            } else {
                System.out.println("Saldo insuficiente.");
            }
        } else {
            System.out.println("Valor inválido.");
        }
    }
}
