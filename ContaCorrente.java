public class ContaCorrente extends ContaBancaria {

    public ContaCorrente(String numeroConta, Cliente cliente, double saldoInicial) {
        super(numeroConta, cliente, saldoInicial);
    }

    @Override
    public void aplicarRendimento(double rendimento) {
    }
    @Override
    public String stringTipoConta() {
        return "Conta corrente";
    }
}
