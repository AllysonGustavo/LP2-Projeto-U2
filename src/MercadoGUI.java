import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MercadoGUI extends JFrame {
  private Estoque estoque;
  private ArrayList<ItemEstoque> carrinho;

  private JTextArea textArea;

  public MercadoGUI() {
    estoque = new Estoque();
    carrinho = new ArrayList<>();

    // Configurar a janela principal
    setTitle("Mercado");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // Configurar a área de texto
    textArea = new JTextArea();
    textArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(textArea);
    add(scrollPane, BorderLayout.CENTER);

    // Configurar o painel de botões
    JPanel buttonPanel = new JPanel(new GridLayout(1, 5));

    JButton estoqueButton = new JButton("Verificar estoque");
    estoqueButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        exibirEstoque();
      }
    });
    buttonPanel.add(estoqueButton);

    JButton comprarButton = new JButton("Comprar itens");
    comprarButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        comprarItens();
      }
    });
    buttonPanel.add(comprarButton);

    JButton removerButton = new JButton("Remover itens");
    removerButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        removerItemCarrinho();
      }
    });
    buttonPanel.add(removerButton);

    JButton encerrarButton = new JButton("Encerrar compra");
    encerrarButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        encerrarCompra();
      }
    });
    buttonPanel.add(encerrarButton);

    JButton sobreButton = new JButton("Sobre");
    sobreButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        exibirSobre();
      }
    });
    buttonPanel.add(sobreButton);

    add(buttonPanel, BorderLayout.SOUTH);

    // Ajustar o tamanho da janela
    pack();

    // Abrir a janela em fullscreen ao ligar o programa
    setExtendedState(JFrame.MAXIMIZED_BOTH);
  }

  public void exibirEstoque() {
    textArea.setText(estoque.getItensString());

    // Atualizar o título da janela
    setTitle("Mercado - Verificar Estoque");
  }

  public void comprarItens() {
    String itensComPreco = estoque.getItensComPrecoString();
    String[] itens = itensComPreco.split("\n");

    // Criar a janela personalizada
    JDialog dialog = new JDialog(this, "Comprar itens", true);
    JPanel panel = new JPanel(new GridLayout(0, 1));

    // Adicionar combobox para selecionar o produto
    JComboBox<String> produtoComboBox = new JComboBox<>();
    for (int i = 0; i < itens.length; i++) {
      String item = itens[i];
      int dotIndex = item.indexOf(".");
      String produto = item.substring(dotIndex + 2); // Remove os três primeiros caracteres (número e ponto)
      produtoComboBox.addItem((i + 1) + ". " + produto);
    }
    panel.add(new JLabel("Selecione o produto:"));
    panel.add(produtoComboBox);

    // Adicionar campo de texto para inserir a quantidade
    JTextField quantidadeTextField = new JTextField();

    // Label para exibir a mensagem de erro
    JLabel mensagemErroLabel = new JLabel();
    mensagemErroLabel.setForeground(Color.RED);

    panel.add(new JLabel("Digite a quantidade:"));
    panel.add(quantidadeTextField);
    panel.add(mensagemErroLabel);

    // Criar o painel para os botões OK e Cancelar
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

    // Adicionar botões de OK e Cancelar
    JButton okButton = new JButton("OK");
    JButton cancelButton = new JButton("Cancelar");
    buttonPanel.add(okButton);
    buttonPanel.add(cancelButton);

    // Adicionar ação ao botão OK
    okButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // Obter o produto selecionado e a quantidade digitada
        int produtoIndex = produtoComboBox.getSelectedIndex();
        String quantidadeText = quantidadeTextField.getText();

        try {
          int quantidade = Integer.parseInt(quantidadeText);
          if (quantidade <= 0) {
            throw new NumberFormatException();
          }

          ItemEstoque itemSelecionado = estoque.getItem(produtoIndex + 1);
          if (!(itemSelecionado instanceof ProdutoVendavel)) {
            throw new IllegalArgumentException("Opção inválida. Por favor, selecione um item válido.");
          }

          ProdutoVendavel produtoSelecionado = (ProdutoVendavel) itemSelecionado;
          if (quantidade > produtoSelecionado.getQuantidade()) {
            throw new IllegalArgumentException("Quantidade insuficiente em estoque.");
          }

          ProdutoVendavel produtoCarrinho = findProdutoInCarrinho(produtoSelecionado.getNome());
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
          JOptionPane.showMessageDialog(dialog, produtoCarrinho.getNome() + " adicionado ao carrinho.");
          dialog.dispose(); // Fechar a janela quando a compra for feita com sucesso
        } catch (NumberFormatException ex) {
          mensagemErroLabel.setText("Entrada inválida. Apenas número inteiro e positivo.");
        } catch (IllegalArgumentException ex) {
          mensagemErroLabel.setText(ex.getMessage());
        }
      }
    });

    // Adicionar ação ao botão Cancelar
    cancelButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        quantidadeTextField.setText(""); // Limpar o campo de quantidade
        mensagemErroLabel.setText(""); // Limpar a mensagem de erro
        dialog.dispose(); // Fechar a janela quando o botão Cancelar for clicado
      }
    });

    // Adicionar o painel de botões ao painel principal
    panel.add(buttonPanel);

    // Configurar o layout da janela
    dialog.getContentPane().setLayout(new BorderLayout());
    dialog.getContentPane().add(panel, BorderLayout.CENTER);
    dialog.setSize(400, 200); // Definir o tamanho da janela (largura x altura)
    SwingUtilities.updateComponentTreeUI(dialog); // Atualizar a janela para não bugar
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
  }

  public void removerItemCarrinho() {
    if (carrinho.isEmpty()) {
      JOptionPane.showMessageDialog(this, "O carrinho está vazio.");
    } else {
      String[] options = new String[carrinho.size() + 1];
      for (int i = 0; i < carrinho.size(); i++) {
        ItemEstoque item = carrinho.get(i);
        options[i] = (i + 1) + ". " + item.getNome();
      }
      options[carrinho.size()] = "Cancelar";

      int opcao = JOptionPane.showOptionDialog(this,
          "Selecione o número do item para remover:",
          "Remover Item do Carrinho",
          JOptionPane.DEFAULT_OPTION,
          JOptionPane.PLAIN_MESSAGE,
          null,
          options,
          options[0]);

      if (opcao >= 0 && opcao < carrinho.size()) {
        ItemEstoque itemRemovido = carrinho.remove(opcao);
        JOptionPane.showMessageDialog(this, itemRemovido.getNome() + " removido do carrinho.");

        if (itemRemovido instanceof ProdutoVendavel) {
          ProdutoVendavel produtoRemovido = (ProdutoVendavel) itemRemovido;
          int quantidadeRemovida = produtoRemovido.getQuantidade();

          // Procurar o produto no estoque pelo nome
          ProdutoVendavel produtoEstoque = null;
          int itemCount = estoque.getItemCount();
          for (int i = 0; i < itemCount; i++) {
            ItemEstoque itemEstoque = estoque.getItem(i);
            if (itemEstoque instanceof ProdutoVendavel && itemEstoque.getNome().equals(produtoRemovido.getNome())) {
              produtoEstoque = (ProdutoVendavel) itemEstoque;
              break;
            }
          }

          if (produtoEstoque != null) {
            produtoEstoque.aumentarQuantidade(quantidadeRemovida); // Aumentar a quantidade no estoque
          }
        }
      } else if (opcao == carrinho.size()) {
        JOptionPane.showMessageDialog(this, "Nenhum item foi removido do carrinho.");
      }
    }
  }

  public void encerrarCompra() {
    // Criar a janela personalizada
    JDialog dialog = new JDialog(this, "Encerrar Compra", true);
    JPanel panel = new JPanel(new BorderLayout());

    // Criar a tabela para exibir os itens da compra
    String[] colunas = { "Nome do Produto", "Quantidade", "Preço" };
    String[][] dados = new String[carrinho.size() + 1][3];

    double totalCompra = 0.0;
    int totalQuantidade = 0;

    for (int i = 0; i < carrinho.size(); i++) {
      ProdutoVendavel produto = (ProdutoVendavel) carrinho.get(i);
      dados[i][0] = produto.getNome();
      dados[i][1] = Integer.toString(produto.getQuantidade());
      dados[i][2] = "R$" + String.format("%.2f", produto.getPreco());
      totalCompra += produto.getPreco() * produto.getQuantidade();
      totalQuantidade += produto.getQuantidade();
    }

    dados[carrinho.size()][0] = "Total";
    dados[carrinho.size()][1] = Integer.toString(totalQuantidade);
    dados[carrinho.size()][2] = "R$" + String.format("%.2f", totalCompra);

    JTable tabela = new JTable(dados, colunas);
    JScrollPane tabelaScrollPane = new JScrollPane(tabela);
    panel.add(tabelaScrollPane, BorderLayout.CENTER);

    // Criar o painel para os botões OK e Finalizar Compra
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

    // Adicionar botões de OK e Finalizar Compra
    JButton okButton = new JButton("OK");
    JButton finalizarButton = new JButton("Finalizar Compra");
    buttonPanel.add(okButton);
    buttonPanel.add(finalizarButton);

    // Adicionar ação ao botão OK
    okButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dialog.dispose(); // Fechar a janela quando o botão OK for clicado
      }
    });

    // Adicionar ação ao botão Finalizar Compra
    finalizarButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (carrinho.isEmpty()) {
          JOptionPane.showMessageDialog(dialog, "O carrinho está vazio. Adicione itens antes de finalizar a compra.");
        } else {
          // Realizar as ações necessárias para finalizar a compra
          ImageIcon icon = new ImageIcon("photos/thanks.png");
          Image image = icon.getImage();
          Image resizedImage = image.getScaledInstance(610, 409, Image.SCALE_SMOOTH);
          ImageIcon resizedIcon = new ImageIcon(resizedImage);

          JOptionPane.showMessageDialog(dialog, "Mercadinho vermelhinho agradece!", "Compra finalizada com sucesso!",
              JOptionPane.INFORMATION_MESSAGE, resizedIcon);
          carrinho.clear(); // Limpar o carrinho após a compra ser finalizada
          dialog.dispose(); // Fechar a janela quando a compra for finalizada
        }
      }
    });

    // Adicionar o painel de botões ao painel principal
    panel.add(buttonPanel, BorderLayout.SOUTH);

    // Configurar o layout da janela
    dialog.getContentPane().setLayout(new BorderLayout());
    dialog.getContentPane().add(panel, BorderLayout.CENTER);
    dialog.setSize(400, 300); // Definir o tamanho da janela (largura x altura)
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
  }

  public void exibirSobre() {
    ArrayList<String> informacoes = new ArrayList<>();
    informacoes.add("Programa de Gerenciamento de Estoque do mercadinho Vermelhinho");
    informacoes.add("Desenvolvido por: Allyson Gustavo");
    informacoes.add("Trabalho sem fins lucrativos.");

    StringBuilder sb = new StringBuilder();
    for (String informacao : informacoes) {
      sb.append(informacao).append("\n");
    }

    JOptionPane.showMessageDialog(this, sb.toString());
  }

  public ProdutoVendavel findProdutoInCarrinho(String nome) {
    for (ItemEstoque item : carrinho) {
      if (item instanceof ProdutoVendavel && item.getNome().equals(nome)) {
        return (ProdutoVendavel) item;
      }
    }
    return null;
  }
}