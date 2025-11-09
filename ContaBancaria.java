public abstract class ContaBancaria {
    private String numeroConta;
    private double saldo;
    private Cliente cliente;
    
    public ContaBancaria(String numeroConta, Cliente cliente, double saldoInicial) {
        this.numeroConta = numeroConta;
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
    public Cliente getCliente() {
        return cliente;
    }

    abstract void aplicarRendimento(double rendimento);
    abstract String stringTipoConta();

    public boolean sacar(double valor) {
    if (valor <= 0) return false;
    if (valor > saldo) return false;
    saldo -= valor;
    return true;
    }

    public boolean depositar(double valor) {
        if (valor <= 0) return false;
        saldo += valor;
        return true;
    }
}
