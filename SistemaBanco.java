import java.util.*;

public class SistemaBanco {
    private final List<Cliente> clientes = new ArrayList<>();
    private final List<ContaBancaria> contas = new ArrayList<>();

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

    
    public void adicionarCliente(String nome, String cpf) {
        if (buscarClientePorCpf(cpf) != null) {
            System.out.println("Erro: Cliente com esse CPF já existe!");
            return;
        }

        clientes.add(new Cliente(nome, cpf));
        System.out.println("Cliente cadastrado com sucesso: " + nome);
    }
    
    public void criarConta(String numeroConta, TipoConta tipoConta, String cpfCliente, double saldoInicial) {
        Cliente clienteEncontrado = buscarClientePorCpf(cpfCliente);
        
        if (clienteEncontrado == null) {
            System.out.println("Erro: Cliente com esse CPF não encontrado!");
            return;
        }
        if (buscarContaPorNumero(numeroConta) != null) {
            System.out.println("Erro: Conta com esse número já existe!");
            return;
        }

        contas.add(new ContaBancaria(numeroConta, tipoConta, clienteEncontrado, saldoInicial));
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
            if (conta.getTipoConta() == TipoConta.POUPANCA) {
                conta.setSaldo(conta.getSaldo() * (rendimento/100.0 + 1));
            }
        }
    }

    public void consultarSaldo(String numeroConta) {
        ContaBancaria conta = buscarContaPorNumero(numeroConta);
        if (conta != null) {
            System.out.println("Saldo da conta " + numeroConta + ": R$" + conta.getSaldo());
        } else {
            System.out.println("Conta não encontrada!");
        }
    }
    public void listarContas() {
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada.");
            return;
        }

        contas.stream()
            .sorted(Comparator.comparingDouble(ContaBancaria::getSaldo).reversed())
            .forEach(c -> System.out.printf(
                "Nome: %s | Número: %s | Tipo: %s | Saldo: R$%.2f%n",
                c.getNomeCliente(), c.getNumeroConta(), c.getTipoConta(), c.getSaldo()
            ));
    }
    public void relatorioDeConsolidacao() {
        double saldoTotalPoucancas = 0;
        int quantidadePoupancas = 0;
        double saldoTotalCorrentes = 0;
        int quantidadeCorrentes = 0;

        for (ContaBancaria c : contas) {
            if (c.getTipoConta() == TipoConta.POUPANCA) {
                saldoTotalPoucancas += c.getSaldo();
                quantidadePoupancas++;
            } else if (c.getTipoConta() == TipoConta.CORRENTE) {
                saldoTotalCorrentes += c.getSaldo();
                quantidadeCorrentes++;
            }
        }
        System.out.println("Relatório de Consolidação:");
        System.out.println("Contas Poupança - Quantidade: " + quantidadePoupancas + ", Saldo Total: R$" + saldoTotalPoucancas);
        System.out.println("Contas Corrente - Quantidade: " + quantidadeCorrentes + ", Saldo Total: R$" + saldoTotalCorrentes);
        System.out.println("Quantidade de contas geral: " + (quantidadePoupancas + quantidadeCorrentes) + ", Saldo Total Geral: R$" + (saldoTotalPoucancas + saldoTotalCorrentes));
    }
}
