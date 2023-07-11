class ProdutoVendivel extends Produto implements Vendivel {
  public ProdutoVendivel(String nome, double preco, int quantidade) {
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

  public void aumentarQuantidade(int quantidade) {
    setQuantidade(getQuantidade() + quantidade);
  }
}
