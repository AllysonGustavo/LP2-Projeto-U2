import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaSobre extends JInternalFrame implements ActionListener {
  public TelaSobre() {
    setTitle("Sobre o Programa");
    setClosable(true);
    setResizable(true);
    setMaximizable(true);
    setIconifiable(true);
    setSize(400, 200);

    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());

    JTextArea textArea = new JTextArea();
    textArea.setEditable(false);
    textArea.setText("Programa de Gerenciamento de Estoque do mercadinho Vermelhinho\n" +
        "Desenvolvido por: Allyson Gustavo\n" +
        "Trabalho sem fins lucrativos.");

    panel.add(textArea, BorderLayout.CENTER);

    add(panel, BorderLayout.CENTER);

    setLocation(150, 150); // Definir a posição relativa no JDesktopPane
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    exibirSobre();
  }

  public void exibirSobre() {
    // Lógica para exibir informações sobre o programa
  }
}
