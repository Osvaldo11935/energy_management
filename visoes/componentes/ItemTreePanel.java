package visoes.componentes;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import anotacoes.ComboItem;
import modelos.CheckBoxNode;

public class ItemTreePanel extends JPanel {

  private final JTree arvore;
  private final DefaultMutableTreeNode raiz;

  public ItemTreePanel(List<ComboItem> menus) {

    setLayout(new BorderLayout());

    raiz = new DefaultMutableTreeNode("Menus");

    arvore = new JTree(raiz);
    arvore.setRootVisible(false);
    arvore.setShowsRootHandles(true);
    arvore.setCellRenderer(new CheckBoxTreeRenderer());

    montarArvore(menus);
    configurarEventos();

    add(new JScrollPane(arvore), BorderLayout.CENTER);
  }
  private void montarArvore(List<ComboItem> menus) {

    Map<Integer,DefaultMutableTreeNode> mapa = new HashMap<>();

    raiz.removeAllChildren();

    for (ComboItem menu: menus) {

      Integer id = toInt(menu.getId());

      DefaultMutableTreeNode node = new DefaultMutableTreeNode(
      new CheckBoxNode(
      id, menu.getDescricao()));

      mapa.put(id, node);
    }

    for (ComboItem menu: menus) {

      Integer id = toInt(menu.getId());
      Integer paiId = toInt(menu.getPaiId());

      DefaultMutableTreeNode node = mapa.get(id);

      if (paiId == null || paiId == 0 || !mapa.containsKey(paiId)) {
        raiz.add(node);
      } else {
        mapa.get(paiId).add(node);
      }
    }

    ((DefaultTreeModel) arvore.getModel()).reload();
  }
  private void configurarEventos() {

    arvore.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(MouseEvent e) {

        TreePath path = arvore.getPathForLocation(
        e.getX(), e.getY());

        if (path == null) return;

        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
        path.getLastPathComponent();

        Object obj = node.getUserObject();

        if (! (obj instanceof CheckBoxNode item)) return;

        boolean novoEstado = !item.isSelecionado();

        item.setSelecionado(novoEstado);

        if (novoEstado) {
          marcarPais(node);
        } else {
          atualizarPais(node);
        }

        ((DefaultTreeModel) arvore.getModel()).nodeChanged(node);
      }
    });
  }
  private void marcarPais(
  DefaultMutableTreeNode node) {

    DefaultMutableTreeNode pai = (DefaultMutableTreeNode)
    node.getParent();

    while (pai != null) {

      Object obj = pai.getUserObject();

      if (obj instanceof CheckBoxNode item) {
        item.setSelecionado(true);
      }

      pai = (DefaultMutableTreeNode)
      pai.getParent();
    }
  }

  private void atualizarPais(
  DefaultMutableTreeNode node) {

    DefaultMutableTreeNode pai = (DefaultMutableTreeNode)
    node.getParent();

    while (pai != null) {

      Object obj = pai.getUserObject();

      if (obj instanceof CheckBoxNode item) {

        item.setSelecionado(
        possuiFilhoMarcado(
        pai));
      }

      pai = (DefaultMutableTreeNode)
      pai.getParent();
    }
  }

  private boolean possuiFilhoMarcado(DefaultMutableTreeNode node) {

    Enumeration<?> filhos = node.children();

    while (filhos.hasMoreElements()) {

      DefaultMutableTreeNode filho = (DefaultMutableTreeNode)
      filhos.nextElement();

      Object obj = filho.getUserObject();

      if (obj instanceof CheckBoxNode item && item.isSelecionado()) {
        return true;
      }

      if (possuiFilhoMarcado(filho)) {
        return true;
      }
    }

    return false;
  }

  public List<Integer> getIdsSelecionados() {

    List<Integer> ids = new ArrayList<>();

    Enumeration<?> nodes = raiz.depthFirstEnumeration();

    while (nodes.hasMoreElements()) {

      DefaultMutableTreeNode node = (DefaultMutableTreeNode)
      nodes.nextElement();

      Object obj = node.getUserObject();

      if (obj instanceof CheckBoxNode item && item.isSelecionado()) {

        ids.add(item.getId());
      }
    }

    return ids;
  }
  private Integer toInt(Object value) {
    if (value == null) return null;

    if (value instanceof Integer i) return i;
    if (value instanceof Long l) return l.intValue();

    try {
      return Integer.parseInt(value.toString().trim());
    } catch(Exception e) {
      return null;
    }
  }
}