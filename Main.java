import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SistemaBanco banco = new SistemaBanco();
        Scanner input = new Scanner(System.in);
        
        while (true) {
            System.out.println("=== Sistema Bancário ===\n");
            System.out.println("Escolha uma opção: \n" + 
                        "|| (1)Cadastrar cliente   |   (2)Criar conta   |   (3)Depositar                ||\n" + 
                        "||       (4)Sacar         |   (5)Transferir    |  (6)Consultar saldo           ||\n" +
                        "|| (7)Aplicar rendimento  |  (8)Listar contas  |  (9)Relatorio de consolidação ||\n" +
                        "||                               (0)Sair                                       || \n");
            
            int opcao = -1;
            try {
                System.out.print("Opção: ");
                opcao = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite apenas números.");
                input.nextLine();
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome do cliente: ");
                    String nome = input.nextLine();
                    System.out.print("Digite o CPF do cliente: ");
                    String cpf = input.nextLine();
                    banco.adicionarCliente(nome, cpf);
                    break;
                    
                case 2:
                    System.out.print("Digite o número da conta: ");
                    String numero = input.nextLine();
                    System.out.print("Digite o tipo da conta (Corrente/Poupança): ");
                    String tipo = input.nextLine();
                    System.out.print("Digite o CPF do cliente: ");
                    String cpfCliente = input.nextLine();
                    System.out.println("Digite o saldo inicial:");
                    Double saldoInicial = input.nextDouble();
                    
                    try {
                        TipoConta tipoConta = TipoConta.fromString(tipo);
                        banco.criarConta(numero, tipoConta, cpfCliente, saldoInicial);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Tipo de conta inválido! Use 'Corrente' ou 'Poupança'.");
                    }
                    break;

                case 3:
                    System.out.print("Digite o número da conta: ");
                    String contaDeposito = input.nextLine();
                    System.out.print("Digite o valor do depósito: ");
                    double valorDeposito = input.nextDouble();
                    banco.depositar(contaDeposito, valorDeposito);
                    break;
                    
                case 4:
                    System.out.print("Digite o número da conta: ");
                    String contaSaque = input.nextLine();
                    System.out.print("Digite o valor do saque: ");
                    double valorSaque = input.nextDouble();
                    banco.sacar(contaSaque, valorSaque);
                    break;
                    
                case 5:
                    System.out.print("Digite o número da conta de origem: ");
                    String contaOrigem = input.nextLine();
                    System.out.print("Digite o número da conta de destino: ");
                    String contaDestino = input.nextLine();
                    System.out.print("Digite o valor da transferência: ");
                    double valorTransferencia = input.nextDouble();
                    banco.transferir(contaOrigem, contaDestino, valorTransferencia);
                    break;
                    
                case 6:
                    System.out.print("Digite o número da conta: ");
                    String contaConsulta = input.nextLine();
                    banco.consultarSaldo(contaConsulta);
                    break;

                case 7:
                    System.out.print("Digite a taxa de rendimento(ex: digite 5 para 5%): ");
                    double taxa = input.nextDouble();
                    banco.aplicarRendimentoPoupancas(taxa);
                    break;
                    
                case 8:
                    banco.listarContas();
                    break;
                    
                case 9:
                    banco.relatorioDeConsolidacao();
                    break;
                    
                case 0:
                    System.out.println("Saindo do sistema");
                    input.close();
                    return;
                    
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
            
            System.out.println("\n ===Pressione Enter Para Continuar=== \n");
            input.nextLine();
        }
    }
}