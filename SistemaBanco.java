import java.util.*;

public class SistemaBanco {
    private List<Cliente> clientes = new ArrayList<>();
    private List<ContaBancaria> contas = new ArrayList<>();

    public void adicionarCliente(String nome, String cpf) {
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                System.out.println("Erro: Um cliente com esse CPF já existe!");
                return;
            }
        }

        Cliente novoCliente = new Cliente(nome, cpf);
        clientes.add(novoCliente);
        System.out.println("Cliente cadastrado com sucesso: " + nome);
    }

    public void criarConta(String numeroConta, TipoConta tipoConta, String cpfCliente) {
        Cliente clienteEncontrado = null;
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpfCliente)) {
                clienteEncontrado = c;
                break;
            }
        }

        if (clienteEncontrado == null) {
            System.out.println("Erro: Cliente com esse CPF não encontrado!");
            return;
        }
        for (ContaBancaria conta : contas) {
            if (conta.getNumeroConta().equals(numeroConta)) {
                System.out.println("Erro: Conta com número " + numeroConta + " já existe!");
                return;
            }
        }

        ContaBancaria novaConta = new ContaBancaria(numeroConta, tipoConta, clienteEncontrado);
        contas.add(novaConta);
        System.out.println("Conta criada com sucesso");
    }

    public void transferir(double valor, ContaBancaria ContaOrigem, ContaBancaria ContaDestino) {
        ContaOrigem.sacar(valor);
        ContaDestino.depositar(valor);
        System.out.println("Transferência de R$" + valor + " efetuada com sucesso!");
    }

    public void listarContas() {
        List<ContaBancaria> contasOrdenadas = new ArrayList<>(contas);
        Collections.sort(contasOrdenadas, Comparator.comparingDouble(ContaBancaria::getSaldo).reversed());
        for (ContaBancaria c : contasOrdenadas) {
            System.out.println("Nome: " + c.getCliente().getNome() + " | Número: "+ c.getNumeroConta() + " | Tipo: " + c.getTipoConta() + " | Saldo: " + c.getSaldo());
        }
    }
}
