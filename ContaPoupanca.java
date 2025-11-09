public class ContaPoupanca extends ContaBancaria{

    public ContaPoupanca(String numeroConta, Cliente cliente, double saldoInicial) {
        super(numeroConta, cliente, saldoInicial);
    }

    @Override
    public void aplicarRendimento(double rendimento) {
        double novoSaldo = getSaldo() * (1+rendimento/100);
        setSaldo(novoSaldo);
    }
    @Override
    public String stringTipoConta() {
        return "Conta poupança";
    }
}
