/*
 * Copyright (C) 2024 Daniel Douglas <danieldouglas26@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package capsgastos;

import java.util.Scanner;
import javax.swing.event.ListDataEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;

/**
 *
 * @author Daniel Douglas <danieldouglas26@outlook.com>
 * @date 11/12/2023
 * @brief Class Main
 */
public class Main {

    public static void limpaTela() throws InterruptedException, IOException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

    }

    // public static String[][] gFixosCadastro;
    public static void CadastroUsuario(int[] gFixos, String[] gfNomes) throws InterruptedException, IOException {
        Scanner s = new Scanner(System.in);
        Scanner ss = new Scanner(System.in);
        boolean bUser;

        int tCadastro = 0;
        String tCadastroConveret = "";
        System.out.println("");
        System.out.println("Cadastro de usuário");
        System.out.println("");

        // Coleta as informações do usuário
        System.out.print("Informe o seu nome: ");
        String nome = s.nextLine();

        System.out.println();

        // System.out.println("Informe gastos fixos (Até 5 opções): ");
        bUser = true;
        do {
            try {
                System.out.print("Quantos gastos fixos deseja adicionar? ");
                tCadastroConveret = s.next();
                tCadastro = Integer.parseInt(tCadastroConveret);
                if (tCadastro > 0) {
                    bUser = true;
                } else {
                    System.out.println("Insira um valor maior que 0!");
                    Thread.sleep(1800);
                    bUser = false;
                }

            } catch (Exception e) {
                System.out.println("Insira um número válido!");
                s.nextLine(); // Limpar o buffer do scanner para evitar loops infinitos
                Thread.sleep(1800);
                bUser = false;
            }
        } while (!bUser);

        String[][] gFixosCadastro = new String[tCadastro][3];
        double Valor = 0;
        bUser = true;

        for (int i = 0; i < tCadastro; i++) {
            System.out.print(i + 1 + " - informe o nome referente ao gasto: ");
            gFixosCadastro[i][0] = ss.nextLine(); // Nome do gasto
            // gfNomes[i] = ss.nextLine();

            do {
                try {
                    System.out.print(i + 1 + " - Informe um valor: ");
                    Valor = s.nextDouble();

                    if (Valor > 0) {
                        gFixosCadastro[i][1] = String.valueOf(Valor); // Valor do gasto
                        bUser = true;
                    } else {
                        System.out.println("Insira um valor maior que 0!");
                        Thread.sleep(1800);
                        bUser = false;
                    }

                } catch (Exception e) {
                    System.out.println("Insira um número válido!");
                    s.nextLine();
                    Thread.sleep(1800);
                    bUser = false;
                }
            } while (!bUser);
            ;

            // gFixos[i] = s.nextInt();
        }

        // Armazena as informações do usuário em um CSV
        try {
            FileWriter arq = new FileWriter("usuarios.txt", true);
            arq.write(nome + ";");
            for (int i = 0; i < tCadastro; i++) {
                arq.write(gFixosCadastro[i][0] + ";" + gFixosCadastro[i][1] + ";");
            }
            arq.write("\n");
            arq.close();
            /*
			 * arq.write(nome + ";" + gFixos[0] + ";" + gfNomes[0] + ";" + gFixos[1] + ";" +
			 * gfNomes[1] + ";"
			 * + gFixos[2] + ";" + gfNomes[2] + ";" + gFixos[3] + ";" + gfNomes[3] + ";" +
			 * gFixos[4]
			 * + ";"
			 * + gfNomes[4] + "\n");
			 * arq.close();
             */
        } catch (IOException e) {
            System.out.println("Erro ao gravar arquivo.");
        }

        System.out.println("Usuário cadastrado com sucesso!");
    }

    public static void CalculoUsuario(int opcaoUsersPrincipal, double[] gFixosCalc, List<String> linhas)
            throws InterruptedException, IOException {

        Scanner ler = new Scanner(System.in);
        Scanner ss = new Scanner(System.in);
        Scanner s = new Scanner(System.in);

        int opcao = 0, opcaoUsers = 0;
        String[] valores;
        String LinhaUser;
        String lerop = "";
        String espera = "";
        gFixosCalc[8] = 0.0;

        for (int i = 0; i <= linhas.size(); i++) {

            if (i == opcaoUsersPrincipal) {

                LinhaUser = linhas.get(i - 1);
                valores = LinhaUser.split(";");
                // String [] [] ExibirVal = new String[valores.length][2];
                String[] ExibirVal = new String[valores.length];

                // for (i = 0; i < ExibirVal.length; i++) {
                // ExibirVal [i] = "";
                // }
                System.out.println("(" + valores[0] + ")");
                System.out.println("");

                for (i = 1; i < valores.length; i = i + 2) {
                    ExibirVal[i] = valores[i];
                    // System.out.println(valores[i] /*+ ": R$ " + valores[i]*/);
                }

                for (i = 2; i < valores.length; i = i + 2) {
                    ExibirVal[i] = valores[i];
                    // break;
                    // System.out.println(": R$ " + valores[i]);
                }

                System.out.println("Gastos fixos (" + ExibirVal.length / 2 + "):");

                Double[] gFixCalc = new Double[valores.length / 2];

                // for (i = 0; i < gFixCalc.length; i++) {
                // gFixCalc [i] = 0.0;
                // }
                int indexGFixCalc = 0;
                for (i = 2; i < ExibirVal.length; i = i + 2) {
                    System.out.println(ExibirVal[i - 1] + ": R$ " + ExibirVal[i]);
                    gFixCalc[indexGFixCalc] = Double.parseDouble(ExibirVal[i]);
                    indexGFixCalc++;
                }

                // System.out.println();
                for (i = 0; i < gFixCalc.length; i++) {
                    // gFixCalc[i] = Double.parseDouble(ExibirVal[i]);
                    // System.out.println( i + " - " + gFixCalc [i]);
                    gFixosCalc[8] += gFixCalc[i];

                }

                /*
				 * System.out.println("(" + valores[0] + ")");
				 * System.out.println("");
				 * System.out.println("Gastos fixos:");
				 * System.out.println(valores[1] + ": R$ " + valores[2]);
				 * System.out.println(valores[3] + ": R$ " + valores[4]);
				 * System.out.println(valores[5] + ": R$ " + valores[6]);
				 * System.out.println(valores[7] + ": R$ " + valores[8]);
				 * System.out.println(valores[9] + ": R$ " + valores[10]);
				 * gFixosCalc[0] = Integer.parseInt(valores[2]);
				 * gFixosCalc[1] = Integer.parseInt(valores[4]);
				 * gFixosCalc[2] = Integer.parseInt(valores[6]);
				 * gFixosCalc[3] = Integer.parseInt(valores[8]);
				 * gFixosCalc[4] = Integer.parseInt(valores[10]);
				 * break;
                 */
                break;
            }

        }

        // gFixosCalc[8] = gFixosCalc[0] + gFixosCalc[1] + gFixosCalc[2] + gFixosCalc[3]
        // + gFixosCalc[4];
        System.out.println("Total de gastos fixos: R$ " + gFixosCalc[8]);
        System.out.println("");

        boolean bUser;

        do {
            try {
                System.out.print("Dige o salário do mês: ");
                lerop = ss.next();
                // gFixosCalc[12] = ler.nextFloat();
                gFixosCalc[12] = Double.parseDouble(lerop); // Valor do gasto

                if (gFixosCalc[12] > 0) {
                    // gFixosCalc[12] = String.valueOf(Valor); // Valor do gasto
                    bUser = true;
                    break;
                } else {
                    System.out.println("Insira um valor maior que 0!");
                    Thread.sleep(1800);
                    bUser = false;
                    // break;
                }

            } catch (Exception e) {
                System.out.println("Insira um número válido!");
                ss.nextLine();
                Thread.sleep(1800);
                bUser = false;

            }
        } while (!bUser);

        gFixosCalc[7] = gFixosCalc[12] - gFixosCalc[8];

        gFixosCalc[9] = 100 * gFixosCalc[8] / gFixosCalc[12];

        System.out.println();
        System.out.printf("(Os gastos fixos representam %.2f%% do seu salário).", gFixosCalc[9]);
        System.out.println();

        System.out.println();
        System.out.println("Você deseja adicionar algum gasto variável?");
        System.out.println("1 - Sim | 2 - Não");
        //System.out.println("2 - Não");
        bUser = true;
        lerop = "";
        opcaoUsers = 0;

        do {
            try {
                System.out.print("Opcão: ");
                lerop = ss.next();
                opcaoUsers = Integer.parseInt(lerop);
                if (opcaoUsers > 0 && opcaoUsers <= 2) {
                    bUser = true;
                    break;
                } else {
                    if (opcaoUsers > 2) {
                        System.out.println("Insira um número entre 1 e 2!");
                        Thread.sleep(1800);
                        bUser = false;
                        // break;
                    } else {

                        System.out.println("Insira um valor maior que 0!");
                        Thread.sleep(1800);
                        bUser = false;
                        // break;
                    }

                }

            } catch (Exception e) {
                System.out.println("Insira um número válido!");
                // s.nextLine(); // Limpar o buffer do scanner para evitar loops infinitos
                Thread.sleep(1800);
                bUser = false;
            }
        } while (!bUser);

        System.out.println("");

        switch (opcaoUsers) {

            case 1:

                lerop = "";
                // opcaoUsers = 0;

                bUser = true;
                do {
                    try {
                        System.out.print("Digite quantos gastos variáveis você deseja adiconar: ");
                        lerop = ss.next();
                        opcaoUsers = Integer.parseInt(lerop);
                        if (opcaoUsers > 0) {
                            bUser = true;
                        } else {
                            System.out.println("Insira um valor maior que 0!");
                            Thread.sleep(1800);
                            bUser = false;
                        }

                    } catch (Exception e) {
                        System.out.println("Insira um número válido!");
                        ss.nextLine(); // Limpar o buffer do scanner para evitar loops infinitos
                        Thread.sleep(1800);
                        bUser = false;
                    }
                } while (!bUser);

                System.out.println();

                gFixosCalc[11] = 0;
                bUser = true;
                double valor = 0.0;

                // Algoritimo para subtrair os gastos váriaveis pelo salário líquido do usuário
                if (opcaoUsers != 0) {

                    for (int i = 0; i < opcaoUsers; i++) {

                        do {
                            try {
                                System.out.print(i + 1 + " - Informe um valor: ");
                                valor = ss.nextDouble();

                                if (valor > 0) {
                                    gFixosCalc[11] += valor;
                                    bUser = true;
                                } else {
                                    System.out.println("Insira um valor maior que 0!");
                                    Thread.sleep(1800);
                                    bUser = false;
                                }

                            } catch (Exception e) {
                                System.out.println("Insira um número válido!");
                                ss.nextLine();
                                Thread.sleep(1800);
                                bUser = false;
                            }
                        } while (!bUser);
                        ;

                        //System.out.print(i + 1 + " - Informe um valor: ");
                        //double valor = ler.nextDouble();
                        //gFixosCalc[11] += valor;
                    }

                    gFixosCalc[10] = gFixosCalc[7] - gFixosCalc[11];

                    if (gFixosCalc[10] > 0) {

                        System.out.println();
                        // Salário Bruto
                        System.out.println("O seu sálario bruto, contando com os gastos fixos e variáveis é de: R$ "
                                + gFixosCalc[10]);
                        System.out.print("");

                        // Porcentagem que o usuário deseja economizar do salário bruto
                        bUser = true;
                        //System.out.print("Digite a porcentagem que deseje ecnonomizar do restante (0 para nada): ");
                        gFixosCalc[13] = 0.0;
                        bUser = true;

                        do {
                            try {
                                System.out.print("Digite a porcentagem que deseje ecnonomizar do restante (0 para nada): ");
                                gFixosCalc[13] = ss.nextFloat();

                                if (gFixosCalc[13] >= 0 && gFixosCalc[13] <= 100.00) {
                                    bUser = true;
                                } else {
                                    System.out.println("Insira um valor maior ou igual a 0 e menor ou igual a 100!");
                                    Thread.sleep(1800);
                                    bUser = false;
                                }

                            } catch (Exception e) {
                                System.out.println("Insira um número válido!");
                                ss.nextLine();
                                Thread.sleep(1800);
                                bUser = false;

                            }
                            //System.out.println();
                        } while (!bUser);
                        ;

                        // Calculo da porcentagem
                        gFixosCalc[5] = gFixosCalc[10] * (1 - (gFixosCalc[13] / 100));
                        gFixosCalc[6] = gFixosCalc[10] - gFixosCalc[5];

                        System.out.println();

                        System.out.printf("Você pode gastar: R$ %.2f%n", gFixosCalc[5]);
                        System.out.printf("Você irá economizar no fim do mês: R$ %.2f%n", gFixosCalc[6]);

                        Thread.sleep(2000);

                        System.out.println();

                        System.out.print("Aparte qualquer tecla para voltar ao inicio... ");
                        espera = ss.nextLine();
                        s.nextLine();
                        break;
                    } else {
                        System.out.println();
                        // Salário Bruto
                        System.out.printf("O seu sálario bruto, contando com os gastos fixos e variáveis é de: R$ %.2f%n ",
                                gFixosCalc[10]);
                        System.out.println("Situação está complicada!");
                        System.out.print("");

                        Thread.sleep(2000);

                        System.out.println();

                        System.out.print("Aparte qualquer tecla para voltar ao inicio... ");
                        espera = ss.nextLine();
                        s.nextLine();
                        break;
                    }
                }

                break;

            case 2:

                if (gFixosCalc[7] > 0) {

                    // Salário Bruto
                    System.out.printf("O seu sálario bruto, contando com os gastos fixos é de: R$ %.2f%n ", gFixosCalc[7]);
                    //System.out.print("");

                    // Porcentagem que o usuário deseja economizar do salário bruto
                    gFixosCalc[13] = 0.0;
                    bUser = true;

                    do {
                        try {
                            System.out.print("Digite a porcentagem que deseje ecnonomizar do restante (0 para nada): ");
                            gFixosCalc[13] = ss.nextFloat();

                            if (gFixosCalc[13] >= 0 && gFixosCalc[13] <= 100.00) {
                                bUser = true;
                            } else {
                                System.out.println("Insira um valor maior ou igual a 0 e menor ou igual a 100!");
                                Thread.sleep(1800);
                                bUser = false;
                            }

                        } catch (Exception e) {
                            System.out.println("Insira um número válido!");
                            ss.nextLine();
                            Thread.sleep(1800);
                            bUser = false;
                        }
                        System.out.println();
                    } while (!bUser);
                    ;

                    // Calculo da porcentagem
                    gFixosCalc[5] = gFixosCalc[7] * (1 - (gFixosCalc[13] / 100));
                    gFixosCalc[6] = gFixosCalc[7] - gFixosCalc[5];

                    System.out.printf("Você pode gastar: R$ %.2f%n", gFixosCalc[5]);
                    System.out.printf("Você irá economizar no fim do mês: R$ %.2f%n", gFixosCalc[6]);

                    Thread.sleep(2000);

                    System.out.println();

                    System.out.print("Aparte qualquer tecla para voltar ao inicio... ");
                    espera = ss.nextLine();
                    s.nextLine();
                    break;

                } else {

                    // Salário Bruto
                    System.out.printf(
                            "O seu sálario bruto, contando com os gastos fixos é de: R$ %.2f%n ", gFixosCalc[7]);
                    System.out.println("(Gastos fixos estão excedendo o seu faturamento mensal)");
                    System.out.print("");

                    Thread.sleep(2000);

                    System.out.println();

                    System.out.print("Aparte qualquer tecla para voltar ao inicio... ");
                    espera = ss.nextLine();
                    s.nextLine();

                }
                break;

            default:
                System.out.println("Opcão Invalida!");
                Thread.sleep(2000);
                break;

        }

    }

    public static void CalculoRapido(int opcaoUsers, double[] gFixosCalc) throws InterruptedException, IOException {
        Scanner ler = new Scanner(System.in);
        Scanner ss = new Scanner(System.in);
        // Coleta informações do usuário

        String lerop = "";
        boolean bUser = true;

        do {
            try {
                System.out.print("Dige o salário do mês: ");
                lerop = ss.next();
                // gFixosCalc[12] = ler.nextFloat();
                gFixosCalc[12] = Double.parseDouble(lerop); // Valor do gasto

                if (gFixosCalc[12] > 0) {
                    // gFixosCalc[12] = String.valueOf(Valor); // Valor do gasto
                    bUser = true;
                    break;
                } else {
                    System.out.println("Insira um valor maior que 0!");
                    Thread.sleep(1800);
                    bUser = false;
                    // break;
                }

            } catch (Exception e) {
                System.out.println("Insira um número válido!");
                ss.nextLine();
                Thread.sleep(1800);
                bUser = false;

            }
        } while (!bUser);

        System.out.println();

        lerop = "";
        opcaoUsers = 0;

        bUser = true;
        do {
            try {
                System.out.print("Digite quantos gastos variáveis você deseja adiconar: ");
                lerop = ss.next();
                opcaoUsers = Integer.parseInt(lerop);
                if (opcaoUsers > 0) {
                    bUser = true;
                } else {
                    System.out.println("Insira um valor maior que 0!");
                    Thread.sleep(1800);
                    bUser = false;
                }

            } catch (Exception e) {
                System.out.println("Insira um número válido!");
                ss.nextLine(); // Limpar o buffer do scanner para evitar loops infinitos
                Thread.sleep(1800);
                bUser = false;
            }
        } while (!bUser);
        ;
        System.out.println();

        gFixosCalc[11] = 0;

        bUser = true;

        // Algoritimo para subtrair os gastos váriaveis pelo salário líquido do usuário
        if (opcaoUsers != 0) {

            for (int i = 0; i < opcaoUsers; i++) {

                do {
                    try {
                        System.out.print(i + 1 + " - Informe um valor: ");
                        Double valor = ss.nextDouble();

                        if (valor > 0) {
                            gFixosCalc[11] += valor;
                            bUser = true;
                        } else {
                            System.out.println("Insira um valor maior que 0!");
                            Thread.sleep(1800);
                            bUser = false;
                        }

                    } catch (Exception e) {
                        System.out.println("Insira um número válido!");
                        ss.nextLine();
                        Thread.sleep(1800);
                        bUser = false;
                    }
                } while (!bUser);
                ;

                // System.out.print(i + 1 + " - Informe um valor: ");
                // int valor = ler.nextInt();
                // gFixosCalc[11] += valor;
            }

            gFixosCalc[10] = gFixosCalc[12] - gFixosCalc[11];

            gFixosCalc[9] = 100 * gFixosCalc[11] / gFixosCalc[12];

            System.out.println();
            System.out.printf("(Os gastos variáveis representam %.2f%% do seu salário).", gFixosCalc[9]);
            System.out.println();

            System.out.println();
            // Salário Bruto
            System.out.println(
                    "O seu sálario bruto, contando com os gastos variáveis é de: R$ " + gFixosCalc[10]);
            System.out.print("");

            // Porcentagem que o usuário deseja economizar do salário bruto
            System.out.print("Digite a porcentagem que deseje ecnonomizar do restante (0 para nada): ");
            gFixosCalc[13] = ler.nextFloat();

            // Calculo da porcentagem que ele deseja economizar
            gFixosCalc[5] = gFixosCalc[10] * (1 - (gFixosCalc[13] / 100));
            gFixosCalc[6] = gFixosCalc[10] - gFixosCalc[5];

            System.out.println();

            System.out.printf("Você pode gastar: R$ %.2f%n", gFixosCalc[5]);
            System.out.printf("Você irá economizar no fim do mês: R$ %.2f%n", gFixosCalc[6]);

            Thread.sleep(2000);

            System.out.println();

            System.out.print("Aparte qualquer tecla para voltar ao inicio... ");
            String espera = ss.nextLine();
            ;
            ss.nextLine();

        }
    }

    public static void MostrarBoletos(String[] cPNomes, double[] cPGastos, String[] cPData)
            throws InterruptedException, IOException {
        Scanner ler = new Scanner(System.in);

        int opcao = 0, opcaoUsers = 0;
        String[] ContasFixas;
        String LinhaUser;
        boolean validacao;
        int DataConverter;
        int DataCSV;
        int DataPAG;
        String DataAtual;

        String mensagem = "Não existem contas cadastradas!";

        //List<String> linhas = null;
        List<String> linhas = new ArrayList<>();
        try {
            linhas = Files.readAllLines(Paths.get("boletos.txt"));
        } catch (IOException e) {
            System.out.println(mensagem);
            System.out.println(
                    "CSV não foi localizado também, crie um arquivo usuarios.txt dentro da pasta Data ou crie um usuário utilizando o menu");
            Thread.sleep(4000);

            validacao = false;
            limpaTela();
            return;
        }

        int numero = 1;

        if (linhas.isEmpty()) {
            System.out.println(mensagem);
            Thread.sleep(2000);
            validacao = false;
            limpaTela();
            return;

        }
        /*
			 * else {
			 * for (String linha : linhas) {
			 * System.out.println(numero + " - " + linha.split(";")[0]);
			 * numero++;
			 * validacao = true;
			 * }
			 * }
         */

        System.out.println("Contas fixas:");
        //System.out.println();

        // int[] DataConverter = new int[5];
        String Situacao = "";
        //System.out.println("************************************************************************");
        //System.out.println("* Nome * Valor * Situação * Tempo para vencimento * Data de vencimento *");
        //System.out.println("************************************************************************");
        // System.out.println("Nome / Valor / Qtd de dias para vencimento / Data de
        // vencimento");
        System.out.println();

        try {

            Date atual = new Date(System.currentTimeMillis());
            // SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            SimpleDateFormat fmt = new SimpleDateFormat("dd");
            DataAtual = fmt.format(atual);
            DataConverter = Integer.parseInt(DataAtual);

            Date atual2 = new Date(System.currentTimeMillis());
            // SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            SimpleDateFormat fmt2 = new SimpleDateFormat("MM");
            String DataAtual2 = fmt2.format(atual);
            // int DataConverter2 = Integer.parseInt(DataAtual);

            for (int i = 0; i < linhas.size(); i++) {

                LinhaUser = linhas.get(i);
                ContasFixas = LinhaUser.split(",");

                DataCSV = Integer.parseInt(ContasFixas[2]);
                DataPAG = DataCSV - DataConverter;
                if (DataPAG == 1) {
                    Situacao = "Vence Amanhã!";
                    // vence amanhã
                }
                if (DataPAG < 0) {
                    Situacao = "Vencida";
                    //Situacao = "Conta já venceu!";
                    // venceu a tantos dias
                } else {
                    Situacao = "Em aberto";
                    //Situacao = "Vencimento em";
                }

                //System.out.println(ContasFixas[0] + ": R$ " + ContasFixas[1] + " - " + Situacao + " -> " + DataPAG			+ " dia(s)" + " - (" + ContasFixas[2] + "/" + DataAtual2 + ")");
                System.out.println("Nome: " + ContasFixas[0]);
                System.out.println("Valor: R$ " + ContasFixas[1]);
                System.out.println("Situação: " + Situacao);
                System.out.println("Tempo para vencimento: " + DataPAG + " dia(s)");
                System.out.println("Data de vencimento: (" + ContasFixas[2] + "/" + DataAtual2 + ")");

                System.out.println();
                // gFixosCalc[4] = Integer.parseInt(valores[9]);

            }
            System.out.println();
            System.out.print("Aparte qualquer tecla para voltar ao inicio... ");
            ler.nextLine();
            limpaTela();
        } catch (Exception e) {
            System.out.println("Erro!");
            Thread.sleep(3000);
        }

    }

    public static void CadastroContas(String[] cPNomes, double[] cPGastos, String[] cPData)
            throws InterruptedException, IOException {

        String tBoletosConveret;
        int tBoletos = 0;
        String opContasConvert = "";
        int opContas = 0;
        boolean bUser;
        boolean bUser2;
        Scanner s = new Scanner(System.in);
        System.out.println("Cadastro de contas");
        System.out.println("");

        System.out.println("1 - Conta fixa");
        System.out.println("2 - Conta variavel - (Em desenvolvimento)");
        System.out.println();
        // System.err.print("Opcão: ");
        // opContasConvert = s.next();
        // opContas = Integer.parseInt(opContasConvert);

        do {
            try {
                System.out.print("Opcão: ");
                opContasConvert = s.next();
                opContas = Integer.parseInt(opContasConvert);
                bUser = true;
            } catch (Exception e) {
                System.out.println("Insira um número válido!");
                Thread.sleep(1800);
                limpaTela();
                CadastroContas(cPData, cPGastos, cPData);
                bUser = false;
            }
        } while (!bUser);

        switch (opContas) {
            case 1:
                /*
				 * Coleta as informações do usuário
				 * System.out.print("Informe apenas o seu primeiro nome: ");
				 * String nome = s.nextLine();
                 */
                System.out.println();
                // System.out.print("Quantas contas fixas deseja adicionar? ");
                bUser = true;
                do {
                    try {
                        System.out.print("Quantas contas fixas deseja adicionar? ");
                        tBoletosConveret = s.next();
                        tBoletos = Integer.parseInt(tBoletosConveret);
                        if (tBoletos > 0) {
                            bUser = true;
                        } else {
                            System.out.println("Insira um valor maior que 0!");
                            Thread.sleep(1800);
                            bUser = false;
                        }

                    } catch (Exception e) {
                        System.out.println("Insira um número válido!");
                        s.nextLine(); // Limpar o buffer do scanner para evitar loops infinitos
                        Thread.sleep(1800);
                        bUser = false;
                    }
                } while (!bUser);

                // tBoletosConveret = s.next();
                // tBoletos = Integer.parseInt(tBoletosConveret);
                Date DTVAL = new Date();
                boolean DTVALValida = false;
                String sdtValidade = "";
                // SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat fmt = new SimpleDateFormat("dd");
                fmt.setLenient(false);
                String sDataAtual = fmt.format(new Date());

                // teste
                String[][] contas = new String[tBoletos][3];
                double Valor;
                bUser = true;
                Scanner ss = new Scanner(System.in);
                for (int i = 0; i < tBoletos; i++) {

                    // Solicita o nome da conta
                    System.out.print(i + 1 + " - informe o nome referente a conta: ");
                    contas[i][0] = ss.nextLine();

                    // Solicita o valor da conta
                    do {
                        try {
                            System.out.print(i + 1 + " - Informe um valor: ");
                            Valor = s.nextDouble();

                            if (Valor > 0) {
                                contas[i][1] = String.valueOf(Valor);
                                // Valor = Integer.parseInt(contas[i][1]); // Esta linha parece desnecessária ou
                                // pode estar substituindo o valor lido anteriormente
                                // String strValor = String.valueOf(Valor);
                                // strValor = contas[i][1]; // Estas linhas parecem desnecessárias
                                bUser = true;
                            } else {
                                System.out.println("Insira um valor maior que 0!");
                                Thread.sleep(1800);
                                bUser = false;
                            }

                        } catch (Exception e) {
                            System.out.println("Insira um número válido!");
                            s.nextLine();
                            Thread.sleep(1800);
                            bUser = false;
                        }
                    } while (!bUser);
                    ;

                    // Solicita a data de vencimento da conta
                    do {
                        try {
                            // System.out.println(i + 1 + " - Informe a data de vencimento (DD/MM/YYYY)");
                            System.out.print(i + 1 + " - Informe o dia de vencimento (DD): ");
                            contas[i][2] = s.next();
                            DTVAL = fmt.parse(contas[i][2]);
                            /*
							 * if (DTVAL.after(new Date())) {
							 * DTVALValida = true;
							 * }
                             */
                            DTVALValida = true;
                        } catch (Exception e) {
                            DTVALValida = false;
                        }

                    } while (!DTVALValida);
                }

                // Salva os dados das contas no arquivo txt
                try {
                    FileWriter arq = new FileWriter("boletos.txt", true);
                    for (int i = 0; i < tBoletos; i++) {
                        arq.write(contas[i][0] + "," + contas[i][1] + "," + contas[i][2] + "\n");
                    }
                    arq.close();
                } catch (IOException e) {
                    System.out.println("Erro ao gravar arquivo.");
                }

                // fim teste

                /*
				 * 
				 * for (int i = 0; i < tBoletos; i++) {
				 * System.out.print(i + 1 +" - informe o nome referente a conta: ");
				 * cPNomes[i] = s.next();
				 * System.out.print(i + 1 + " - Informe um valor: ");
				 * cPGastos[i] = s.nextInt();
				 * 
				 * do {
				 * try {
				 * System.out.println(i + 1 + " - Informe a data de vencimento (DD/MM/YYYY)");
				 * cPData[i] = s.next();
				 * DTVAL = fmt.parse(cPData[i]);
				 * if (DTVAL.after(new Date())) {
				 * DTVALValida = true;
				 * }
				 * } catch (Exception e) {
				 * DTVALValida = false;
				 * }
				 * 
				 * } while (!DTVALValida);
				 * 
				 * 
				 * }
				 * 
				 * // Armazena as informações do usuário em um CSV
				 * 
				 * try {
				 * FileWriter arq = new FileWriter("boletos.txt", true);
				 * //List<String> linhas = null;
				 * //linhas = Files.readAllLines(Paths.get("usuarios.txt"));
				 * for (int i = 0; i <= tBoletos; i++) {
				 * arq.write(cPNomes[i]+ ";" + cPGastos[i] + ";" + cPData[i] + "\n");
				 * arq.close();
				 * }
				 * 
				 * } catch (IOException e) {
				 * System.out.println("Erro ao gravar arquivo.");
				 * }
				 * 
                 */
                if (tBoletos == 1) {
                    System.out.println();
                    System.out.println("Conta cadastrada com sucesso!");
                    Thread.sleep(1200);
                } else {
                    System.out.println();
                    System.out.println("Contas cadastradas com sucesso!");
                    Thread.sleep(1200);
                }
                limpaTela();
                // MenuContas(cPNomes, cPGastos, cPData);
                break;

            case 2:
                limpaTela();
                CadastroContas(cPData, cPGastos, cPData);
                break;

            default:
                limpaTela();
                CadastroContas(cPData, cPGastos, cPData);
                break;
        }

    }

    public static void exibirUsersBoleto(int opcaoUsersPrincipal, double[] gFixosCalc)
            throws InterruptedException, IOException {
        Scanner ler = new Scanner(System.in);
        boolean validacao;
        String sMenu = "";

        String mensagem = "Não existem usuários cadastrados!";

        List<String> linhas = null;

        try {
            linhas = Files.readAllLines(Paths.get("boletos.txt"));
        } catch (IOException e) {
            System.out.println(mensagem);
            System.out.println(
                    "CSV não foi localizado também, crie um arquivo boletos.txt dentro da pasta Data ou adicione um boleto utilizando o menu");
            Thread.sleep(4000);
            validacao = false;
        }

        int numero = 1;

        if (linhas.isEmpty()) {
            System.out.println(mensagem);
            Thread.sleep(3000);
            validacao = false;
        } else {
            for (String linha : linhas) {
                System.out.println(numero + " - " + linha.split(";")[0]);
                numero++;
                validacao = true;
            }
        }

        System.out.println();

        sMenu = ler.next();

    }

    public static void ValidarUsersMenu(int opcaoUsersPrincipal, double[] gFixosCalc)
            throws InterruptedException, IOException {
        Scanner ler = new Scanner(System.in);
        boolean validacao;
        String sMenu = "";

        String mensagem = "Não existem usuários cadastrados!";

        // List<String> linhas = null;
        List<String> linhas = new ArrayList<>();
        try {
            linhas = Files.readAllLines(Paths.get("usuarios.txt"));
        } catch (IOException e) {
            // System.out.println(mensagem);
            System.out.println(
                    "Usuarios.txt não foi localizado! (Crie um usuário pelo menu ou um arquivo usuarios.txt dentro da pasta data)");
            Thread.sleep(4000);
            // limpaTela();
            // MenuPrincipal();
            validacao = false;
            return;

        }

        int numero = 1;

        if (linhas.isEmpty()) {
            System.out.println(mensagem);
            Thread.sleep(2000);
            validacao = false;
            // limpaTela();
            // MenuPrincipal();
            return;
        } else {
            System.out.println("Usuários:");
            System.out.println();
            System.out.println("0 - Voltar ao início");
            for (String linha : linhas) {
                System.out.println(numero + " - " + linha.split(";")[0]);
                numero++;
                validacao = true;
            }
            //System.out.println();

        }

        System.out.println();
        boolean bUser = true;

        do {

            try {
                System.out.print("Escolha a opção: ");
                sMenu = ler.next();

                opcaoUsersPrincipal = Integer.parseInt(sMenu);

                if (opcaoUsersPrincipal <= linhas.size() && opcaoUsersPrincipal > 0) {
                    System.out.println();
                    //limpaTela();
                    CalculoUsuario(opcaoUsersPrincipal, gFixosCalc, linhas);
                    limpaTela();
                    // ValidarUsersMenu(opcaoUsersPrincipal, gFixosCalc);
                    bUser = true;
                    // return
                    break;
                } else {
                    if (opcaoUsersPrincipal == 0) {
                        return;
                    }
                    if (opcaoUsersPrincipal < 0) {
                        System.out.println("Informe um valor maior ou igual a 0!");
                        Thread.sleep(2000);
                        bUser = false;

                    } else {
                        System.out.println("Usuário não cadastrado!");
                        Thread.sleep(2000);
                        // limpaTela();
                        // ValidarUsersMenu(opcaoUsersPrincipal, gFixosCalc);
                        bUser = false;
                        validacao = false;
                    }

                }

            } catch (Exception e) {
                System.out.println("Insira um número válido!");
                Thread.sleep(2000);
                // limpaTela();
                // ValidarUsersMenu(opcaoUsersPrincipal, gFixosCalc);
                bUser = false;
            }

        } while (!bUser);

        System.out.println();
        // ler.close();
        return;

    }

    public static void MenuContas(String[] cPNomes, double[] cPGastos, String[] cPData)
            throws InterruptedException, IOException {
        Scanner ler = new Scanner(System.in);
        String sMenu = "";
        boolean bUser = true;

        int opcao = 0;
        System.out.println("1 - Mostrar contas");
        System.out.println("2 - Cadastrar contas");
        System.out.println("3 - Sair");
        System.out.println();

        do {
            try {
                System.out.print("Opcão: ");
                sMenu = ler.next();
                opcao = Integer.parseInt(sMenu);
                bUser = true;

                if (opcao > 0 && opcao <= 3) {
                    bUser = true;
                    break;
                } else {
                    System.out.println("Opcão Invalida!");
                    Thread.sleep(1800);
                    // limpaTela();
                    // MenuContas(cPNomes, cPGastos, cPData);
                    bUser = false;
                    // return;

                }

            } catch (Exception e) {
                System.out.println("Insira um número válido!");
                Thread.sleep(1800);
                // limpaTela();
                // MenuContas(cPNomes, cPGastos, cPData);
                bUser = false;
            }
        } while (!bUser);

        switch (opcao) {
            case 1:
                limpaTela();
                MostrarBoletos(cPNomes, cPGastos, cPData);
                MenuContas(cPNomes, cPGastos, cPData);
                limpaTela();
                break;

            case 2:

                limpaTela();
                CadastroContas(cPNomes, cPGastos, cPData);
                MenuContas(cPNomes, cPGastos, cPData);
                limpaTela();
                break;

            case 3:
                limpaTela();
                return;
            // break;

            default:
                break;
        }

    }

    public static int MenuPrincipal() throws InterruptedException, IOException {
        Scanner ler = new Scanner(System.in);

        String sMenu = "";
        boolean bUser = true;
        int opcao = 0;
        System.out.println();
        System.out.println("   ------------------------------------------------------------------------------");
        System.out.println("   |                     Calculo de gastos do mês: BY CAPS                      |");
        System.out.println("   ------------------------------------------------------------------------------");
        Date DTVAL = new Date();
        boolean DTVALValida = false;
        String sdtValidade = "";
        SimpleDateFormat fmt = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm");
        // SimpleDateFormat fmt = new SimpleDateFormat("dd");
        fmt.setLenient(false);
        String sDataAtual = fmt.format(new Date());
        // Date now = new Date(System.currentTimeMillis());
        System.out.println("   |    " + sDataAtual + "                                                   |");
        System.out.println("   |    v1.0.111223                                                             |");

        System.out.println("   |                                                                            |");

        // Menu inicial do programa
        System.out.println("   |      Escolha a opção:                                                      |");
        System.out.println("   |      1 - Escolher usuário                                                  |");
        System.out.println("   |      2 - Criar usuário                                                     |");
        System.out.println("   |      3 - Cálculo rápido                                                    |");
        System.out.println("   |      4 - Contas a pagar                                                    |");
        System.out.println("   |      5 - Exportar para EXEL - (Em desenvolvimento)                         |");
        System.out.println("   |      6 - Sair                                                              |");
        System.out.println("   ------------------------------------------------------------------------------");
        System.out.println("");
        boolean entradaValida = false;

        do {
            try {
                System.out.print(" Opcão: ");
                sMenu = ler.next();
                ler.nextLine();
                opcao = Integer.parseInt(sMenu);
                // bUser = true;

                if (opcao > 0 && opcao <= 6) {
                    bUser = true;
                    break;
                } else {
                    if (opcao < 0) {
                        System.out.println("Informe um valor maior que 0!");
                        Thread.sleep(1800);
                    } else if (opcao > 5 || opcao == 0) {
                        System.out.println("Informe um valor entre 1 e 6");
                        Thread.sleep(1800);
                    }

                    // limpaTela();
                    // MenuPrincipal();
                    bUser = true;
                    ;

                }

            } catch (Exception e) {
                System.out.println("Insira um número válido!");
                Thread.sleep(1800);
                opcao = 7;
                // limpaTela();
                // MenuPrincipal();
                bUser = true;

            }
        } while (!bUser);

        return opcao;
    }

    public static void main(String[] args) throws InterruptedException, IOException {

        Scanner ler = new Scanner(System.in);
        int voltarmenu = 0;
        String[] valores;
        String LinhaUser;
        int opcao = 0, opcaoUsers = 0, opcaoUsersPrincipal = 0;
        // rrayList<String[]> gFixosCadastro = new ArrayList<String[]>();
        String[] cPNomes = new String[99];
        double[] cPGastos = new double[99];
        String[] cPData = new String[99];
        int[] gFixos = new int[5];
        String[] gfNomes = new String[5];
        double[] gFixosCalc = new double[14];
        /*
		 * gFixosCalc [0] = fix1
		 * gFixosCalc [1] = fix2
		 * gFixosCalc [2] = fix3
		 * gFixosCalc [3] = fix4
		 * gFixosCalc [4] = fix5
		 * gFixosCalc [5] = econ1
		 * gFixosCalc [6] = econ2
		 * gFixosCalc [7] = Total
		 * gFixosCalc [8] = TotalFix *
		 * gFixosCalc [8] = TotalFix
		 * gFixosCalc [9] = procentsalario
		 * gFixosCalc [10] = tVari
		 * gFixosCalc [11] = gVari
		 * gFixosCalc [12] = sala
		 * gFixosCalc [13] = porcent
         */

        while (opcao != 6) {
            limpaTela();
            opcao = MenuPrincipal();
            limpaTela();

            // Switch principal relacionado a seção que o usuário escolheu pelo menu inicial
            switch (opcao) {

                case 1:

                    ValidarUsersMenu(opcaoUsersPrincipal, gFixosCalc);

                    break;

                // Switch responsável por cadastrar usuários
                case 2:
                    CadastroUsuario(gFixos, gfNomes);
                    Thread.sleep(2000);
                    break;

                // Switch responsável por fazer o calculo dos gastos do usuário sem a
                // nescessidade de armazenar em um CSV
                case 3:
                    limpaTela();
                    CalculoRapido(opcaoUsers, gFixosCalc);
                    break;
                // Switch responsavel pelos boletos a vencer, de acordo com cada usuário
                case 4:
                    limpaTela();
                    MenuContas(cPNomes, cPGastos, cPData);

                    break;
                // Switch responsavel por exportar os dados do usuário para uma planilha no
                // formato XLSX, tendo o CSV como base
                case 5:

                    break;

                // Swtich sem atribuição, apenas para realizar o encerramento do programa.
                case 6:
                    break;

                default:
                    // System.out.println("Opção invalida!");

                    // Thread.sleep(2000);
                    break;

            }

        }

    }

}
