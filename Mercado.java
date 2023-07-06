// Executar código: sh -c javac -classpath .:target/dependency/* -d . $(find . -type f -name '*.java')
// Aluno: Allyson Gustavo Silva do Carmo
// Matricula: 20210051717
// Turma: DIM0116 - LINGUAGEM DE PROGRAMAÇÃO II - T02 (2023.1)

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;

abstract class ItemEstoque {
  private String nome;
  private double preco;

  public ItemEstoque(String nome, double preco) {
    this.nome = nome;
    this.preco = preco;
  }

  public String getNome() {
    return nome;
  }

  public double getPreco() {
    return preco;
  }

  public abstract void exibirDetalhes();
}

class Produto extends ItemEstoque {
  private int quantidade;

  public Produto(String nome, double preco, int quantidade) {
    super(nome, preco);
    this.quantidade = quantidade;
  }

  public int getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(int quantidade) {
    this.quantidade = quantidade;
  }

  public void exibirDetalhes() {
    System.out.println("Nome: " + getNome());
    System.out.println("Preço: " + getPreco());
    System.out.println("Quantidade: " + getQuantidade());
  }
}

interface Vendavel {
  void vender(int quantidade);
}

class ProdutoVendavel extends Produto implements Vendavel {
  public ProdutoVendavel(String nome, double preco, int quantidade) {
    super(nome, preco, quantidade);
  }

  public void vender(int quantidade) {
    if (getQuantidade() >= quantidade) {
      setQuantidade(getQuantidade() - quantidade);
      System.out.println("Itens adicionados ao carrinho com sucesso!");
    } else {
      System.out.println("Não há estoque suficiente para realizar a venda.");
    }
  }
}

class Estoque {
  private List < ItemEstoque > itens;

  public Estoque() {
    itens = new ArrayList < > ();
  }

  public void adicionarItem(ItemEstoque item) {
    itens.add(item);
  }

  public void exibirItens() {
    for (int i = 0; i < itens.size(); i++) {
      ItemEstoque item = itens.get(i);
      System.out.println((i + 1) + ". " + item.getNome() + " - Quantidade: " + ((Produto) item).getQuantidade());
    }

    System.out.println("Pressione qualquer tecla para continuar...");
    Scanner scanner = new Scanner(System.in);
    scanner.nextLine();
    Mercado.limparConsole();
  }

  public ItemEstoque getItem(int indice) {
    return itens.get(indice);
  }

  public int getItemCount() {
    return itens.size();
  }

  public void exibirItensComPreco() {
    for (int i = 0; i < itens.size(); i++) {
      ItemEstoque item = itens.get(i);
      if (item instanceof ProdutoVendavel) {
        System.out.println(i + ". " + item.getNome() + " - Preço: " + item.getPreco());
      }
    }
  }
}

public class Mercado {
  public static void main(String[] args) {
    Estoque estoque = new Estoque();

    Produto extintor = new Produto("Extintor", 103, 2);
    ProdutoVendavel arrozBrancoCamil = new ProdutoVendavel("Arroz Branco Camil", 5.99, 11);
    ProdutoVendavel feijao = new ProdutoVendavel("Feijão", 3.99, 13);
    ProdutoVendavel sabaoEmPo = new ProdutoVendavel("Sabão em pó", 9.99, 18);

    estoque.adicionarItem(extintor);
    estoque.adicionarItem(arrozBrancoCamil);
    estoque.adicionarItem(feijao);
    estoque.adicionarItem(sabaoEmPo);

    Scanner scanner = new Scanner(System.in);
    int opcao;
    boolean encerrarPrograma = false;
    List < ItemEstoque > carrinho = new ArrayList < > ();

    while (!encerrarPrograma) {
      exibirMenuPrincipal();
      try {
        opcao = scanner.nextInt();
        scanner.nextLine();

        // Limpar o console
        limparConsole();

        switch (opcao) {
        case 1:
          estoque.exibirItens();
          break;
        case 2:
          comprarItens(scanner, estoque, carrinho);
          break;
        case 3:
          exibirCarrinho(carrinho);
          removerItemCarrinho(scanner, carrinho);
          break;
        case 4:
          exibirCarrinho(carrinho);
          encerrarPrograma = encerrarCompra(scanner);
          break;
        case 5:
          exibirSobre();
          break;
        default:
          System.out.println("Opção inválida. Por favor, selecione uma opção válida.");
          break;
        }
      } catch (InputMismatchException e) {
        scanner.nextLine(); // Descarta a entrada inválida
      } catch (NoSuchElementException e) {
        scanner.nextLine(); // Descarta a entrada inválida
      }
    }
  }

  public static void removerItemCarrinho(Scanner scanner, List < ItemEstoque > carrinho) {
    System.out.println("--- Remover Item do Carrinho ---");
    if (carrinho.isEmpty()) {
      System.out.println("Pressione qualquer tecla para continuar...");
      Scanner scannerr = new Scanner(System.in);
      scannerr.nextLine();
    } else {
      for (int i = 0; i < carrinho.size(); i++) {
        ItemEstoque item = carrinho.get(i);
        System.out.println((i + 1) + ". " + item.getNome());
      }
      System.out.println("");
      System.out.println("0. Voltar ao menu principal");
      System.out.println("Selecione o número do item para remover:");
      int opcao = scanner.nextInt();
      scanner.nextLine();
      if (opcao >= 1 && opcao <= carrinho.size()) {
        ItemEstoque itemRemovido = carrinho.remove(opcao - 1);
        System.out.println(itemRemovido.getNome() + " removido do carrinho.");
      } else if (opcao == 0) {
        System.out.println("Nenhum item foi removido do carrinho.");
      } else {
        System.out.println("Opção inválida. Por favor, selecione uma opção válida.");
      }
    }
    System.out.println(""); // Quebra de linha
  }

  public static void exibirMenuPrincipal() {
    limparConsole();
    System.out.println("--- Menu Principal ---");
    System.out.println("1. Verificar estoque");
    System.out.println("2. Comprar itens");
    System.out.println("3. Remover itens");
    System.out.println("4. Encerrar compra");
    System.out.println("5. Sobre");
    System.out.println("Digite o número da opção desejada:");
  }

  public static void comprarItens(Scanner scanner, Estoque estoque, List < ItemEstoque > carrinho) {
    int opcao;
    do {
      System.out.println("--- Comprar Itens ---");
      estoque.exibirItensComPreco();
      System.out.println("");
      System.out.println("0. Voltar ao menu principal");
      System.out.println("Selecione o número do item para adicionar ao carrinho:");
      opcao = scanner.nextInt();
      scanner.nextLine();
      limparConsole();
      if (opcao > 0 && opcao <= estoque.getItemCount()) {
        ItemEstoque itemSelecionado = estoque.getItem(opcao);
        if (itemSelecionado instanceof ProdutoVendavel) {
          ProdutoVendavel produtoSelecionado = (ProdutoVendavel) itemSelecionado;
          System.out.println("Digite a quantidade desejada:");
          int quantidade = scanner.nextInt();
          scanner.nextLine();
          limparConsole();
          if (quantidade > produtoSelecionado.getQuantidade()) {
            System.out.println("Quantidade insuficiente em estoque.");
          } else {
            ProdutoVendavel produtoCarrinho = findProdutoInCarrinho(carrinho, produtoSelecionado.getNome());
            if (produtoCarrinho == null) {
              produtoCarrinho = new ProdutoVendavel(
                produtoSelecionado.getNome(),
                produtoSelecionado.getPreco(),
                quantidade);
              carrinho.add(produtoCarrinho);
            } else {
              produtoCarrinho.setQuantidade(produtoCarrinho.getQuantidade() + quantidade);
            }
            produtoSelecionado.vender(quantidade);
            System.out.println(produtoCarrinho.getNome() + " adicionado ao carrinho.");
          }
        } else {
          System.out.println("Opção inválida. Por favor, selecione um item válido.");
        }
      } else if (opcao != 0) {
        System.out.println("Opção inválida. Por favor, selecione uma opção válida.");
      }
    } while (opcao != 0);
  }

  public static ProdutoVendavel findProdutoInCarrinho(List < ItemEstoque > carrinho, String nome) {
    for (ItemEstoque item: carrinho) {
      if (item instanceof ProdutoVendavel && item.getNome().equals(nome)) {
        return (ProdutoVendavel) item;
      }
    }
    return null;
  }

  public static void exibirCarrinho(List < ItemEstoque > carrinho) {
    System.out.println("--- Carrinho ---");
    if (carrinho.isEmpty()) {
      System.out.println("O carrinho está vazio.");
    } else {
      double total = 0;
      for (ItemEstoque item: carrinho) {
        item.exibirDetalhes();
        if (item instanceof ProdutoVendavel) {
          ProdutoVendavel produto = (ProdutoVendavel) item;
          total += produto.getPreco() * produto.getQuantidade();
        }
        System.out.println();
      }
      System.out.println(String.format("Valor total: R$ %.2f", total)); // Coloquei para apenas 2 casas decimais
      System.out.println(""); // Quebra de linha
    }
  }

  public static boolean encerrarCompra(Scanner scanner) {
    System.out.println("--- Encerrar Compra ---");
    System.out.println("Selecione uma opção:");
    System.out.println("1. Menu Principal");
    System.out.println("2. Encerrar Compra");
    int opcao = scanner.nextInt();
    scanner.nextLine();

    // Limpar o console
    limparConsole();

    if (opcao == 2) {
      System.out.println("Obrigado pela compra! Tenha um ótimo dia!");
      System.out.println("Mercadinho Vermelhinho agradece!");
      System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣄⡈⠙⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⡿⠛⢉⣀⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣦⡈⠹⣿⣿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣿⡟⠁⣠⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣆⠘⣿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⡏⢠⣾⣿⣿⣿⡿⠟⠋⢉⣉⣀⣈⣉⠙⠻⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⠛⢉⣉⣀⣈⣉⠙⠛⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠋⣠⣴⣿⣿⣿⣿⣿⣿⣿⣶⣄⡈⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠋⣀⣴⣾⣿⣿⣿⣿⣿⣿⣷⣤⡈⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⠏⢠⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣄⠘⢿⣿⣿⣿⣿⣿⣿⣿⣿⠟⢀⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣦⠈⢿⣿⣿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣿⣿⠃⣰⣿⣿⣿⣿⣿⡿⠿⠿⠿⠿⣿⣿⣿⣿⣿⣧⠈⢿⣿⣿⣿⣿⣿⣿⠏⢠⣿⣿⣿⣿⣿⡿⠿⠿⠿⠿⣿⣿⣿⣿⣿⣧⡀⢻⣿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣿⠇⢠⣿⣿⣿⣿⣿⣋⣀⡀⠄⠄⠄⠈⠙⣿⣿⣿⣿⣧⠈⣿⣿⣿⣿⣿⡏⢠⣿⣿⣿⣿⡟⠉⠄⠄⠄⠄⣀⣈⣻⣿⣿⣿⣿⣧⠄⢿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⡟⠄⣿⣿⣿⣿⣿⣿⣿⣿⣿⣆⠄⠄⠄⠄⠈⢿⣿⣿⣿⡆⢸⣿⣿⣿⣿⠄⣾⣿⣿⣿⠏⠄⠄⠄⠄⢀⣾⣿⣿⣿⣿⣿⣿⣿⣿⡆⠸⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⡇⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠄⠄⠄⠄⠄⠘⣿⣿⣿⣧⠄⣿⣿⣿⡇⢰⣿⣿⣿⡟⠄⠄⠄⠄⠄⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠄⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⠁⢸⣿⣿⣿⡇⢿⣿⣿⣿⣿⡿⠄⠄⠄⠄⠄⠄⣿⣿⣿⣿⠄⣿⣿⣿⡇⢸⣿⣿⣿⡇⠄⠄⠄⠄⠄⠸⣿⣿⣿⣿⣿⠏⣿⣿⣿⣿⠄⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⡄⢸⣿⣿⣿⡇⠈⠻⢿⠿⠟⠁⠄⠄⠄⠄⠄⠄⣿⣿⣿⣿⠄⣿⣿⣿⡇⢸⣿⣿⣿⡇⠄⠄⠄⠄⠄⠄⠙⠿⣿⠿⠋⠄⣿⣿⣿⣿⠄⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⡇⠸⣿⣿⣿⣿⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⢰⣿⣿⣿⡏⠄⣿⣿⣿⣇⠘⣿⣿⣿⣧⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⢸⣿⣿⣿⡿⠄⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣷⠄⢿⣿⣿⣿⣧⠄⠄⠄⠄⠄⠄⠄⠄⠄⢠⣿⣿⣿⣿⠃⢸⣿⣿⣿⣿⠄⢻⣿⣿⣿⣧⠄⠄⠄⠄⠄⠄⠄⠄⠄⢠⣿⣿⣿⣿⠇⢸⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣿⣇⠘⣿⣿⣿⣿⣷⣄⠄⠄⠄⠄⠄⢀⣴⣿⣿⣿⣿⡏⢠⣿⣿⣿⣿⣿⣇⠈⣿⣿⣿⣿⣷⣄⠄⠄⠄⠄⠄⢀⣴⣿⣿⣿⣿⡟⢀⣿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣿⣿⣆⠘⣿⣿⣿⣿⣿⣿⣷⣶⣶⣾⣿⣿⣿⣿⣿⠏⢀⣾⣿⣿⣿⣿⣿⣿⣆⠘⢿⣿⣿⣿⣿⣿⣷⣶⣶⣿⣿⣿⣿⣿⣿⡟⢀⣾⣿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣿⣿⣉⡀⠈⠛⠛⠻⠿⢿⣿⣿⣿⣿⣿⣿⣿⡿⠋⣠⣿⣿⣿⣿⣿⣿⣿⣿⣿⣦⡈⠻⣿⣿⣿⣿⣿⣿⣿⣿⠿⠿⠛⠛⠋⠄⣈⣹⣿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣶⣦⣤⣈⠉⠻⢿⡿⠟⠋⣠⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣦⡈⠛⠿⣿⠿⠋⢉⣠⣤⣶⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣦⡄⠄⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⡆⠄⣴⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⠋⢹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣟⠙⠛⠿⢿⣿⣿⡿⠿⠟⠛⠉⠁⠄⠄⠄⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⡀⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡄⠄⠄⠄⠄⠄⠄⢀⣠⣤⡄⢀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⠄⠄⠄⠄⣀⣴⣿⣿⣿⠁⣸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠄⠄⠄⣾⣿⣿⣿⡿⠃⣰⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣄⡀⠉⠉⢉⣁⣤⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
      System.out.println("⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿");
      return true;
    }

    return false;
  }

  public static void exibirSobre() {
    List < String > informacoes = new ArrayList < > ();
    informacoes.add("Programa de Gerenciamento de Estoque do mercadinho Vermelhinho");
    informacoes.add("Desenvolvido por: Allyson Gustavo");
    informacoes.add("Trabalho sem fins lucrativos.");

    System.out.println("--- Sobre ---");
    for (String informacao: informacoes) {
      System.out.println(informacao);
    }
    System.out.println("Pressione qualquer tecla para continuar...");
    Scanner scanner = new Scanner(System.in);
    scanner.nextLine();
  }

  public static void limparConsole() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}