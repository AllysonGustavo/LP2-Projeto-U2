import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TelaEncerrarCompra extends JInternalFrame implements ActionListener {
  private JTable tabela;
  private JButton finalizarButton;

  private ArrayList<ItemEstoque> carrinho;

  public TelaEncerrarCompra(ArrayList<ItemEstoque> carrinho) {
    setTitle("Encerrar Compra");
    setClosable(true);
    setResizable(true);
    setMaximizable(true);
    setIconifiable(true);
    setSize(400, 300);

    this.carrinho = carrinho;

    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());

    String[] colunas = { "Nome do Produto", "Quantidade", "Preço" };
    String[][] dados = { { "Produto 1", "1", "R$ 10.00" }, { "Produto 2", "2", "R$ 20.00" } };

    tabela = new JTable(dados, colunas);
    JScrollPane scrollPane = new JScrollPane(tabela);
    panel.add(scrollPane, BorderLayout.CENTER);

    finalizarButton = new JButton("Finalizar Compra");
    finalizarButton.addActionListener(this);
    panel.add(finalizarButton, BorderLayout.SOUTH);

    add(panel, BorderLayout.CENTER);

    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == finalizarButton) {
      finalizarCompra();
    }
  }

  public void finalizarCompra() {
    // Lógica para finalizar a compra
  }
}
