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