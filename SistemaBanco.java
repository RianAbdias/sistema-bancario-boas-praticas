import java.util.*;

public class SistemaBanco {
    private List<Cliente> clientes = new ArrayList<>();
    private List<ContaBancaria> contas = new ArrayList<>();

    public ContaBancaria buscarContaPorNumero(String numeroConta) {
        for (ContaBancaria conta : contas) {
            if (conta.getNumeroConta().equals(numeroConta)) {
                return conta;
            }
        }
        return null;
    }
    public Cliente buscarClientePorCpf(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    public void consultarSaldo(String numeroConta) {
        ContaBancaria conta = buscarContaPorNumero(numeroConta);
        if (conta != null) {
            System.out.println("Saldo da conta " + numeroConta + ": R$" + conta.getSaldo());
        } else {
            System.out.println("Conta não encontrada!");
        }
    }

    public void adicionarCliente(String nome, String cpf) {
        if (buscarClientePorCpf(cpf) != null) {
            System.out.println("Erro: Cliente com esse CPF já existe!");
            return;
        }

        Cliente novoCliente = new Cliente(nome, cpf);
        clientes.add(novoCliente);
        System.out.println("Cliente cadastrado com sucesso: " + nome);
    }
    
    public void criarConta(String numeroConta, String cpfCliente, String tipoConta) {
        criarConta(numeroConta, cpfCliente, tipoConta, 0.0);
    }

    public void criarConta(String numeroConta, String cpfCliente, String tipoConta, double saldoInicial) {
        Cliente clienteEncontrado = buscarClientePorCpf(cpfCliente);
        
        if (clienteEncontrado == null) {
            System.out.println("Erro: Cliente com esse CPF não encontrado!");
            return;
        }
        if (buscarContaPorNumero(numeroConta) != null) {
            System.out.println("Erro: Conta com esse número já existe!");
            return;
        }

        ContaBancaria novaConta;

        switch (tipoConta.trim().toLowerCase()) {
            case "corrente":
                novaConta = new ContaCorrente(numeroConta, clienteEncontrado, saldoInicial);
                break;
            case "poupança":
            case "poupanca":
                novaConta = new ContaPoupanca(numeroConta, clienteEncontrado, saldoInicial);
                break;
            default:
                System.out.println("Erro: Tipo de conta inválido!");
                return;
        }

        contas.add(novaConta);
        System.out.println("Conta criada com sucesso");
    }

    public void sacar(String numeroConta, double valor) {
    ContaBancaria conta = buscarContaPorNumero(numeroConta);
    if (conta == null) {
        System.out.println("Erro: Conta não encontrada!");
        return;
    }

    if (conta.sacar(valor)) {
        System.out.println("Saque realizado com sucesso.");
    } else {
        System.out.println("Erro: valor inválido ou saldo insuficiente.");
    }
    }
    public void depositar(String numeroConta, double valor) {
        ContaBancaria conta = buscarContaPorNumero(numeroConta);
        if (conta == null) {
            System.out.println("Erro: Conta não encontrada!");
            return;
        }

        if (conta.depositar(valor)) {
            System.out.println("Depósito realizado com sucesso.");
        } else {
            System.out.println("Erro: valor inválido para depósito.");
        }
    }
    public void transferir(String numeroContaOrigem, String numeroContaDestino, double valor) {
        ContaBancaria origem = buscarContaPorNumero(numeroContaOrigem);
        ContaBancaria destino = buscarContaPorNumero(numeroContaDestino);

        if (origem == null || destino == null) {
            System.out.println("Erro: Conta de origem ou destino não encontrada!");
            return;
        }

        if (!origem.sacar(valor)) {
            System.out.println("Erro: valor inválido ou saldo insuficiente na conta de origem.");
            return;
        }

        if (!destino.depositar(valor)) {
            origem.depositar(valor);
            System.out.println("Erro: não foi possível depositar na conta de destino. Operação cancelada.");
            return;
        }

        System.out.println("Transferência realizada com sucesso.");
    }

    public void aplicarRendimentoPoupancas(double rendimento) {
        for (ContaBancaria conta : contas) {
            if (conta instanceof ContaPoupanca) {
                ((ContaPoupanca) conta).aplicarRendimento(rendimento);
            }
        }
        System.out.println("Rendimento aplicado às contas poupança com taxa de " + rendimento + "%.");
    }


    public void listarContas() {
        List<ContaBancaria> contasOrdenadas = new ArrayList<>(contas);
        Collections.sort(contasOrdenadas, Comparator.comparingDouble(ContaBancaria::getSaldo).reversed());
        for (ContaBancaria c : contasOrdenadas) {
            System.out.println("Nome: " + c.getCliente().getNome() + " | Número: "+ c.getNumeroConta() + " | Tipo: " + c.stringTipoConta() + " | Saldo: " + c.getSaldo());
        }
    }
    public void relatorioDeConsolidacao() {
        Map<String, double[]> relatorio = new HashMap<>();

        for (ContaBancaria conta : contas) {
            String tipo = conta.stringTipoConta();

            relatorio.putIfAbsent(tipo, new double[2]);
            double[] valores = relatorio.get(tipo);

            valores[0]++;
            valores[1] += conta.getSaldo();
        }

        double saldoGeral = 0;
        int totalContas = 0;

        System.out.println("Relatório de Consolidação:");
        for (Map.Entry<String, double[]> entry : relatorio.entrySet()) {
            String tipo = entry.getKey();
            double[] v = entry.getValue();
            System.out.println("Tipo: " + tipo + " - Quantidade: " + (int)v[0] + ", Saldo Total: R$" + v[1]);
            saldoGeral += v[1];
            totalContas += v[0];
        }

        System.out.println("Total de contas: " + totalContas + ", Saldo Geral: R$" + saldoGeral);
    }

}
