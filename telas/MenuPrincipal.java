package telas;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import java.util.*;

import models.MenuModelo;
import models.PerfilMenuModelo;
import modeloFiles.PerfilMenuFile;

public class MenuPrincipal extends JFrame {

    private JMenuBar menuBar;

    private int nivelUsuario;

    public MenuPrincipal(int nivelUsuario) {

        super("Sistema");

        this.nivelUsuario = nivelUsuario;

        menuBar = new JMenuBar();

        carregarMenus();

        setJMenuBar(menuBar);

        setSize(900, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);
    }

    private void carregarMenus() {
        List<PerfilMenuModelo> perfilMenus = new PerfilMenuFile(
                        new PerfilMenuModelo()).buscarMenuPorPerfilId(1);

        perfilMenus = perfilMenus.stream()
                        .filter(pm -> pm.getMenu().getNivelMinimoAcesso()<= nivelUsuario)
                        .sorted(Comparator.comparingInt(pm ->pm.getMenu().getOrdem()))
                        .toList();

        Map<Integer, JMenu> mapa = new HashMap<>();

        for (PerfilMenuModelo pm : perfilMenus) {

            MenuModelo menu =
                    pm.getMenu();

            if (menu == null)
                continue;

            if (menu.getMenuPaiId() == 0) {

                JMenu jm = new JMenu(menu.getNome());

                mapa.put(menu.getId(),jm);

                menuBar.add(jm);

                boolean temFilhos = perfilMenus.stream()
                                .anyMatch(p ->p.getMenu() != null && p.getMenu().getMenuPaiId() == menu.getId());

                boolean podeAbrir = menu.getCaminhoClasse() != null && !menu.getCaminhoClasse() .isBlank();

                if (!temFilhos && podeAbrir) {
                    jm.addMenuListener(new MenuListener() {

                                @Override
                                public void menuSelected(MenuEvent e) {
                                    abrirClasse(menu.getCaminhoClasse());
                                }

                                @Override
                                public void menuDeselected(MenuEvent e) {
                                }

                                @Override
                                public void menuCanceled(MenuEvent e) {
                                }
                            });
                }
            }
        }

        for (PerfilMenuModelo pm : perfilMenus) {
            MenuModelo menu = pm.getMenu();

            if (menu == null) continue;

            if (menu.getMenuPaiId() != 0) {

                JMenu pai = mapa.get(menu.getMenuPaiId());

                if (pai == null) continue;

                JMenuItem item = new JMenuItem(menu.getNome());

                item.addActionListener( e ->abrirClasse(menu.getCaminhoClasse()));
                pai.add(item);
            }
        }

        menuBar.revalidate();

        menuBar.repaint();
    }

    private void abrirClasse(String caminho) {
        try {

            Class<?> classe = Class.forName(caminho);
            Object obj = classe.getDeclaredConstructor().newInstance();
            if (obj instanceof JFrame frame) {

                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
            else if (obj instanceof JDialog dialog) {

                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
            else if (obj instanceof JPanel panel) {
                JFrame frame = new JFrame();

                frame.setContentPane(panel);

                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                frame.pack();

                frame.setLocationRelativeTo(null);

                frame.setVisible(true);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao abrir:\n" + caminho);
        }
    }

    public static void main(String[] args) {
        new MenuPrincipal(2);
    }
}