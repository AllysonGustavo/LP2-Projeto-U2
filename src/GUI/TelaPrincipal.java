import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TelaPrincipal extends JFrame implements ActionListener {
  private JDesktopPane desktopPane;
  private Estoque estoque;
  private ArrayList<ItemEstoque> carrinho;

  public TelaPrincipal() {
    estoque = new Estoque();
    carrinho = new ArrayList<>();

    setTitle("Mercado");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setExtendedState(JFrame.MAXIMIZED_BOTH);

    // Criar o desktop pane
    desktopPane = new JDesktopPane();
    add(desktopPane, BorderLayout.CENTER);

    // Criar o menu
    JMenuBar menuBar = new JMenuBar();

    JMenu estoqueMenu = new JMenu("Estoque");
    JMenuItem verificarEstoqueMenuItem = new JMenuItem("Verificar Estoque");
    verificarEstoqueMenuItem.addActionListener(this);
    estoqueMenu.add(verificarEstoqueMenuItem);
    menuBar.add(estoqueMenu);

    JMenu comprarMenu = new JMenu("Comprar");
    JMenuItem comprarItensMenuItem = new JMenuItem("Comprar Itens");
    comprarItensMenuItem.addActionListener(this);
    comprarMenu.add(comprarItensMenuItem);
    menuBar.add(comprarMenu);

    JMenu removerMenu = new JMenu("Remover");
    JMenuItem removerItensMenuItem = new JMenuItem("Remover Itens");
    removerItensMenuItem.addActionListener(this);
    removerMenu.add(removerItensMenuItem);
    menuBar.add(removerMenu);

    JMenu encerrarMenu = new JMenu("Encerrar");
    JMenuItem encerrarCompraMenuItem = new JMenuItem("Encerrar Compra");
    encerrarCompraMenuItem.addActionListener(this);
    encerrarMenu.add(encerrarCompraMenuItem);
    menuBar.add(encerrarMenu);

    JMenu sobreMenu = new JMenu("Sobre");
    JMenuItem sobreMenuItem = new JMenuItem("Sobre");
    sobreMenuItem.addActionListener(this);
    sobreMenu.add(sobreMenuItem);
    menuBar.add(sobreMenu);

    setJMenuBar(menuBar);

    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Verificar Estoque")) {
      TelaVerificarEstoque telaVerificarEstoque = new TelaVerificarEstoque(estoque);
      desktopPane.add(telaVerificarEstoque);
      ajustarPosicaoJanelaInterna(telaVerificarEstoque);
      telaVerificarEstoque.setVisible(true);
    } else if (e.getActionCommand().equals("Comprar Itens")) {
      TelaComprarItens telaComprarItens = new TelaComprarItens(estoque, carrinho);
      desktopPane.add(telaComprarItens);
      ajustarPosicaoJanelaInterna(telaComprarItens);
      telaComprarItens.setVisible(true);
    } else if (e.getActionCommand().equals("Remover Itens")) {
      TelaRemoverItens telaRemoverItens = new TelaRemoverItens(carrinho);
      desktopPane.add(telaRemoverItens);
      ajustarPosicaoJanelaInterna(telaRemoverItens);
      telaRemoverItens.setVisible(true);
    } else if (e.getActionCommand().equals("Encerrar Compra")) {
      TelaEncerrarCompra telaEncerrarCompra = new TelaEncerrarCompra(carrinho);
      desktopPane.add(telaEncerrarCompra);
      ajustarPosicaoJanelaInterna(telaEncerrarCompra);
      telaEncerrarCompra.setVisible(true);
    } else if (e.getActionCommand().equals("Sobre")) {
      TelaSobre telaSobre = new TelaSobre();
      desktopPane.add(telaSobre);
      ajustarPosicaoJanelaInterna(telaSobre);
      telaSobre.setVisible(true);
    }
  }

  private void ajustarPosicaoJanelaInterna(JInternalFrame janelaInterna) {
    int x = desktopPane.getAllFrames().length * 20;
    int y = desktopPane.getAllFrames().length * 20;
    janelaInterna.setLocation(x, y);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(TelaPrincipal::new);
  }
}