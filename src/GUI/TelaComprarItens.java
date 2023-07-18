import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TelaComprarItens extends JInternalFrame implements ActionListener {
  private JComboBox<String> produtoComboBox;
  private JTextField quantidadeTextField;
  private JButton okButton;
  private JButton cancelButton;

  private Estoque estoque;
  private ArrayList<ItemEstoque> carrinho;

  public TelaComprarItens(Estoque estoque, ArrayList<ItemEstoque> carrinho) {
    this.estoque = estoque;
    this.carrinho = carrinho;

    setTitle("Comprar Itens");
    setClosable(true);
    setResizable(true);
    setMaximizable(true);
    setIconifiable(true);
    setSize(400, 200);

    JPanel panel = new JPanel(new GridLayout(0, 1));

    produtoComboBox = new JComboBox<>();
    panel.add(new JLabel("Selecione o produto:"));
    panel.add(produtoComboBox);

    quantidadeTextField = new JTextField();
    panel.add(new JLabel("Digite a quantidade:"));
    panel.add(quantidadeTextField);

    okButton = new JButton("OK");
    okButton.addActionListener(this);
    panel.add(okButton);

    cancelButton = new JButton("Cancelar");
    cancelButton.addActionListener(this);
    panel.add(cancelButton);

    add(panel, BorderLayout.CENTER);

    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == okButton) {
      comprarItem();
    } else if (e.getSource() == cancelButton) {
      cancelarCompra();
    }
  }

  public void comprarItem() {
    // Lógica para comprar o item selecionado
  }

  public void cancelarCompra() {
    // Lógica para cancelar a compra
  }
}
