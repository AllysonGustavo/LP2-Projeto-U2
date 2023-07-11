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

  public abstract int getQuantidade();

  public abstract void exibirDetalhes();
}