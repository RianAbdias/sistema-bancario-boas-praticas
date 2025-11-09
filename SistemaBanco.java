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

    public void criarConta(String numeroConta, TipoConta tipoConta, String cpfCliente) {
        Cliente clienteEncontrado = buscarClientePorCpf(cpfCliente);
        
        if (clienteEncontrado == null) {
            System.out.println("Erro: Cliente com esse CPF não encontrado!");
            return;
        }
        if (buscarContaPorNumero(numeroConta) != null) {
            System.out.println("Erro: Conta com esse número já existe!");
            return;
        }

        ContaBancaria novaConta = new ContaBancaria(numeroConta, tipoConta, clienteEncontrado);
        contas.add(novaConta);
        System.out.println("Conta criada com sucesso");
    }

    public void depositar(String numeroConta, double valor) {
        ContaBancaria conta = buscarContaPorNumero(numeroConta);
        if (conta == null) {
            System.out.println("Erro: Conta não encontrada!");
            return;
        }
        conta.depositar(valor);
    }
    public void sacar(String numeroConta, double valor) {
        ContaBancaria conta = buscarContaPorNumero(numeroConta);
        if (conta == null) {
            System.out.println("Erro: Conta não encontrada!");
            return;
        }
        conta.sacar(valor);
    }
    public void transferir(String numeroContaOrigem, String numeroContaDestino, double valor) {
        ContaBancaria contaOrigem = buscarContaPorNumero(numeroContaOrigem);
        if (contaOrigem == null) {
            System.out.println("Erro: Conta de origem não encontrada!");
            return;
        }
        ContaBancaria contaDestino = buscarContaPorNumero(numeroContaDestino);
        if (contaDestino == null) {
            System.out.println("Erro: Conta de destino não encontrada!");
            return;
        }
        contaOrigem.sacar(valor);
        contaDestino.depositar(valor);
    }

    public void aplicarRendimentoPoupancas(double rendimento) {
        for (ContaBancaria conta : contas) {    
            if (conta.getTipoConta() == TipoConta.POUPANCA) {
                conta.setSaldo(conta.getSaldo() * (rendimento/100.0 + 1));
            }
        }
    }

    public void listarContas() {
        List<ContaBancaria> contasOrdenadas = new ArrayList<>(contas);
        Collections.sort(contasOrdenadas, Comparator.comparingDouble(ContaBancaria::getSaldo).reversed());
        for (ContaBancaria c : contasOrdenadas) {
            System.out.println("Nome: " + c.getCliente().getNome() + " | Número: "+ c.getNumeroConta() + " | Tipo: " + c.getTipoConta() + " | Saldo: " + c.getSaldo());
        }
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
