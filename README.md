# LP2-Projeto-U2
Trabalho desenvolvido por: Allyson Gustavo Silva do Carmo
Turma: DIM0116 - LINGUAGEM DE PROGRAMAÇÃO II - T02 (2023.1)

Essa aplicação é um programa de gerenciamento de estoque para um mercado fictício chamado "Mercadinho Vermelhinho", inspirado no mercado real do meu pai. O código em Java define várias classes e interfaces relacionadas ao estoque, produtos e vendas.

A classe ItemEstoque é uma classe abstrata que representa um item genérico do estoque. Ela possui atributos como nome e preço, além de um método abstrato exibirDetalhes().

A classe Produto é uma subclasse de ItemEstoque e representa um produto específico. Além dos atributos herdados, ela possui um atributo adicional chamado quantidade, que indica a quantidade disponível desse produto no estoque. A classe também implementa o método exibirDetalhes() para mostrar as informações do produto.

A interface Vendavel define o contrato para itens que podem ser vendidos. Ela possui um único método vender(int quantidade) que permite vender uma determinada quantidade desse item.

A classe ProdutoVendavel é uma subclasse de Produto que implementa a interface Vendavel. Ela adiciona a funcionalidade de venda ao produto, verificando se há quantidade suficiente em estoque para realizar a venda e atualizando a quantidade disponível.

A classe Estoque é responsável por gerenciar a lista de itens em estoque. Ela possui métodos para adicionar itens ao estoque, exibir os itens disponíveis com suas quantidades e preços, e obter informações sobre os itens.

A classe Mercado contém o método main() que serve como ponto de entrada do programa. Nesse método, são criados alguns produtos e adicionados ao estoque. O programa exibe um menu principal com opções para verificar o estoque, comprar itens, remover itens do carrinho, encerrar a compra e exibir informações sobre o programa. O usuário pode interagir com o programa selecionando as opções desejadas.

O programa permite que o usuário compre produtos, adicionando-os a um carrinho de compras. O carrinho é uma lista de itens do estoque. O usuário pode remover itens do carrinho e, quando desejar, encerrar a compra. Ao encerrar a compra, o programa exibe o valor total da compra e encerra a execução.

Além disso, o programa possui métodos auxiliares para limpar o console, encontrar um produto específico no carrinho e exibir informações sobre o programa.

Essa aplicação permite a gestão básica de um estoque de produtos, realização de vendas e exibição de informações para um mercado fictício.
