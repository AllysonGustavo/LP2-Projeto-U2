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