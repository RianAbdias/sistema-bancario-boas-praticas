public class ContaPoupanca extends ContaBancaria{

    public ContaPoupanca(String numeroConta, Cliente cliente, double saldoInicial) {
        super(numeroConta, cliente, saldoInicial);
    }

    @Override
    public void aplicarRendimento(double rendimento) {
        //logica de rendimento
    }
    @Override
    public String stringTipoConta() {
        return "Conta poupança";
    }
}
