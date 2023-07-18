import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TelaVerificarEstoque extends JInternalFrame implements ActionListener {
  private JTextArea textArea;

  public TelaVerificarEstoque(Estoque estoque) {
    setTitle("Verificar Estoque");
    setClosable(true);
    setResizable(true);
    setMaximizable(true);
    setIconifiable(true);
    setSize(400, 300);

    textArea = new JTextArea();
    textArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(textArea);
    add(scrollPane, BorderLayout.CENTER);

    exibirEstoque(estoque);

    setLocation(200, 200); // Definir a posição relativa no JDesktopPane
    setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    // Não é necessário implementar esse método neste caso
  }

  public void exibirEstoque(Estoque estoque) {
    textArea.setText(estoque.getItensString());

    // Atualizar o título da janela
    setTitle("Mercado - Verificar Estoque");
  }
}
