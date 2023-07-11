import javax.swing.SwingUtilities;

public class Mercado {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        MercadoGUI mercadoGUI = new MercadoGUI();
        mercadoGUI.setVisible(true);
      }
    });
  }
}