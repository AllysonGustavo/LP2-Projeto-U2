import java.util.Scanner;
import java.util.ArrayList;

class Estoque {
  private ArrayList<ItemEstoque> itens;

  public Estoque() {
    itens = new ArrayList<>();
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