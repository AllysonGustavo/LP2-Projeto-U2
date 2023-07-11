import java.util.ArrayList;

class Estoque {
  private ArrayList<ItemEstoque> itens;

  public Estoque() {
    itens = new ArrayList<>();
    // Adicionar os itens ao estoque
    Produto extintor = new Produto("Extintor", 103, 2);
    ProdutoVendivel arrozBrancoCamil = new ProdutoVendivel("Arroz Branco Camil", 5.99, 11);
    ProdutoVendivel feijao = new ProdutoVendivel("Feijão", 3.99, 13);
    ProdutoVendivel sabaoEmPo = new ProdutoVendivel("Sabão em pó", 9.99, 18);

    adicionarItem(extintor);
    adicionarItem(arrozBrancoCamil);
    adicionarItem(feijao);
    adicionarItem(sabaoEmPo);
  }

  public void adicionarItem(ItemEstoque item) {
    itens.add(item);
  }

  public String getItensString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < itens.size(); i++) {
      ItemEstoque item = itens.get(i);
      sb.append((i + 1)).append(". ").append(item.getNome()).append(" - ");
      if (item instanceof ProdutoVendivel) {
        ProdutoVendivel produto = (ProdutoVendivel) item;
        sb.append("Quantidade: ").append(produto.getQuantidade());
      } else if (item instanceof Produto) {
        Produto produto = (Produto) item;
        sb.append("Quantidade: ").append(produto.getQuantidade());
      } else {
        sb.append("Quantidade: N/A");
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  public ItemEstoque getItem(int indice) {
    return itens.get(indice);
  }

  public int getItemCount() {
    return itens.size();
  }

  public String getItensComPrecoString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < itens.size(); i++) {
      ItemEstoque item = itens.get(i);
      if (item instanceof ProdutoVendivel) {
        sb.append(i + 1).append(". ").append(item.getNome()).append(" - R$").append(item.getPreco()).append("\n");
      }
    }
    return sb.toString();
  }

  public String toString() {
    return getItensString();
  }
}
