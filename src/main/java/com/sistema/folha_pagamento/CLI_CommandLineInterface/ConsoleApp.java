package com.sistema.folha_pagamento.cli;

import com.sistema.folha_pagamento.model.Colaborador;
import com.sistema.folha_pagamento.service.ColaboradorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Scanner;

@Component
public class ConsoleApp implements CommandLineRunner {
    private final ColaboradorService colaboradorService;

    public ConsoleApp(ColaboradorService colaboradorService) {
        this.colaboradorService = colaboradorService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("\n==================================================");
        System.out.println("   SISTEMA DE GESTÃO DE FOLHA DE PAGAMENTO - CLI  ");
        System.out.println("==================================================");

        while (running) {
            exibirMenu();
            String opcao = scanner.nextLine().trim();

            try{
                switch (option){
                    case "1":
                        cadastrarColaborador(scanner);
                        break;
                    case "2":
                        consultarColaboradores();
                        break;
                    case "3":
                        gerarFolhaPagamento();
                        break;
                    case "4":
                        emitirResumoFolhaPagamento();
                        break;
                    case "0":
                        running = false;
                        System.out.println("Encerrando sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Ocorreu um erro: " + e.getMessage());
            }
            if (running) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
        }
        scanner.close();
    }
    private void exibirMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Cadastrar Novo Colaborador (UC01)");
        System.out.println("2. Consultar Colaboradores (UC02)");
        System.out.println("3. Gerar Folha de Pagamento Detalhada (UC03)");
        System.out.println("4. Emitir Resumo da Folha de Pagamento (UC04)");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");

        String type = scanner.nextLine().trim();

        switch (type) {
            case "1":
                colaboradorService.cadastrarPadrao(id, nome, salarioBase);
                break;
            case "2":
                System.out.println("Digite o valor (R$) das vendas: ");
                BigDecimal vendas = new BigDecimal(scanner.nextLine().trim());
                System.out.println("Digite o percentual de comissao (em decimal): ");
                BigDecimal percentual = new BigDecimal(scanner.nextLine().trim());
                colaboradorService.cadastrarComissionado(id, nome, salarioBase, vendas, percentual);
                break;
            case "3":
                System.out.println("Digite a quantidade produzida: ");
                int quantidadeProduzida = Integer.parseInt(scanner.nextLine().trim());
                System.out.println("Digite o valor por peça produzida (R$): ");
                BigDecimal valorUnidade = new BigDecimal(scanner.nextLine().trim());
                colaboradorService.cadastrarProducao(id, nome, salarioBase, quantidadeProduzida, valorUnidade);
                break;
            default:
                throw new IllegalArgumentException("Tipo de colaborador desconhecido. Tente novamente.");
        }
        System.out.println("\n [SUCESSO] Colaborador cadastrado e salvo na base com sucesso!");
    }
    private void consultarColaboradores() {
        System.out.println("\n--- LISTA DE COLABORADORES CADASTRADOS ---");
        var listaColaboradores = colaboradorService.listarTodos();

        if(listaColaboradores.isEmpty()) {
            System.out.println("Nenhum colaborador encontrado.");
            return;
    }

    for (Colaborador c : listaColaboradores) {
        System.out.println"--------------------------------------------------");
        System.out.println("Matrícula: " + c.getMatricula() + " | Nome: " + c.getNome());
        System.out.println("Tipo: " + c.getClass().getSimpleName() + " | Salário Base: R$ " + c.getSalarioBase());
        System.out.println("Salário Final: R$ " + c.calcularSalarioFinal());
        
        System.out.println("--------------------------------------------------");
    }

    private void gerarFolhaPagamento() {
        System.out.println("\n--- FOLHA DE PAGAMENTO DETALHADA ---");
        System.out.println("Funcionalidade em desenvolvimento. Em breve estará disponível.");
        
//        var listaColaboradores = colaboradorService.listarTodos();
//        if(listaColaboradores.isEmpty()) {

//System.out.println("Nenhum colaborador encontrado.");
//            return;
//        }

//        for (Colaborador c : listaColaboradores) {
//            System.out.println("--------------------------------------------------");
//            System.out.println("Matrícula: " + c.getMatricula() + " | Nome: " + c.getNome());
//            System.out.println("Tipo: " + c.getClass().getSimpleName() + " | Salário Base: R$ " + c.getSalarioBase());
//            System.out.println("Salário Final: R$ " + c.calcularSalarioFinal());
//            System.out.println("--------------------------------------------------");
//        }
    }
}