public class ContaBancaria {
    private String numeroConta;
    private double saldo;
    private TipoConta tipoConta;
    private Cliente cliente;
    
    public ContaBancaria(String numeroConta, TipoConta tipo, Cliente cliente) {
        this.numeroConta = numeroConta;
        this.tipoConta = tipo;
        this.cliente = cliente;
        this.saldo = 0.0;
    }
    public String getNumeroConta() {
        return numeroConta;
    }
    public double getSaldo() {
        return saldo;
    }
    public TipoConta getTipoConta() {
        return tipoConta;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public String getNomeCliente() {
        return cliente.getNome();
    }

    public void setSaldo(double novoSaldo) {
        saldo = novoSaldo;
    }

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

