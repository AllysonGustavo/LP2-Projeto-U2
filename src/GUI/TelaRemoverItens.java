import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TelaRemoverItens extends JInternalFrame implements ActionListener {
  private JComboBox<String> itemComboBox;
  private JButton removerButton;

  private ArrayList<ItemEstoque> carrinho;

  public TelaRemoverItens(ArrayList<ItemEstoque> carrinho) {
    setTitle("Remover Itens");
    setClosable(true);
    setResizable(true);
    setMaximizable(true);
    setIconifiable(true);
    setSize(400, 200);

    this.carrinho = carrinho;

    JPanel panel = new JPanel(new GridLayout(0, 1));

    itemComboBox = new JComboBox<>();
    panel.add(new JLabel("Selecione o item:"));
    panel.add(itemComboBox);

    removerButton = new JButton("Remover");
    removerButton.addActionListener(this);
    panel.add(removerButton);

    add(panel, BorderLayout.CENTER);

    setLocation(250, 250); // Definir a posição relativa no JDesktopPane
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == removerButton) {
      removerItem();
    }
  }

  public void removerItem() {
    // Lógica para remover o item selecionado
  }
}
