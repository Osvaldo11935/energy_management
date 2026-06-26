package visoes;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import modeloFiles.ClienteFile;
import modeloFiles.NotificacaoFile;
import modelos.ClienteModelo;
import modelos.NotificacaoModelo;
import modelos.UsuarioModelo;
import utils.Session;

public class NotificacoesVisao extends JFrame {

    private final DefaultListModel<NotificacaoModelo> model = new DefaultListModel<>();

    private final JList<NotificacaoModelo> lista = new JList<>(model);

    private final JLabel lblTitulo = new JLabel("Selecione uma notificação");

    private final JLabel lblTipo = new JLabel();

    private final JLabel lblData = new JLabel();

    private final JTextArea txtMensagem = new JTextArea();

    public NotificacoesVisao() {

        setTitle("Notificações");
        setSize(900, 600);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnAtualizar =
            new JButton("Atualizar");

        btnAtualizar.addActionListener(
            e -> carregarNotificacoes()
        );

        topo.add(btnAtualizar);

        add(topo, BorderLayout.NORTH);

        lista.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        lista.setCellRenderer(
            new NotificacaoRenderer()
        );

        lista.addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                NotificacaoModelo n =
                    lista.getSelectedValue();

                if (n != null) {
                    mostrarNotificacao(n);
                }
            }
        });

        JScrollPane scrollLista =
            new JScrollPane(lista);

        JPanel detalhe = new JPanel(new BorderLayout(10, 10));

        JPanel cabecalho = new JPanel();

        cabecalho.setLayout(new BoxLayout(cabecalho, BoxLayout.Y_AXIS));

        lblTitulo.setFont(new Font("Arial",Font.BOLD,18));

        cabecalho.add(lblTitulo);
        cabecalho.add(lblTipo);
        cabecalho.add(lblData);

        txtMensagem.setEditable(false);
        txtMensagem.setLineWrap(true);
        txtMensagem.setWrapStyleWord(true);

        detalhe.add(cabecalho, BorderLayout.NORTH);
        detalhe.add(
            new JScrollPane(txtMensagem),
            BorderLayout.CENTER
        );

        JSplitPane split =
            new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                scrollLista,
                detalhe
            );

        split.setDividerLocation(300);

        add(split, BorderLayout.CENTER);

        carregarNotificacoes();
    }

    private void carregarNotificacoes() {

        model.clear();
        UsuarioModelo usuario = Session.getUsuario();
        List<ClienteModelo> clientes = ClienteFile.instaciar().buscarClientePorUsuarioId(usuario.getId());

        for(ClienteModelo cliente : clientes)
        {
            List<NotificacaoModelo> notificacoes = NotificacaoFile.instaciar().buscarPorClienteId(cliente.getId());

            for (NotificacaoModelo n : notificacoes) {
                model.addElement(n);
            }
        }
    }

    private void mostrarNotificacao(NotificacaoModelo n) {

        lblTitulo.setText(n.getTitulo().toString());

        lblTipo.setText("Tipo: " + n.getTipo());

        lblData.setText("Data: " + n.getDataEnvio());

        txtMensagem.setText(n.getMensagem().toString());
        
        if(!n.isActivo() && n.getAreaId() == 0){
          n.setLida(true);
          n.atualizarDados();
        }
    }

    private static class NotificacaoRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            JLabel lbl =
                (JLabel) super
                .getListCellRendererComponent(
                    list,
                    value,
                    index,
                    isSelected,
                    cellHasFocus
                );

            NotificacaoModelo n = (NotificacaoModelo) value;

            lbl.setText(
                "<html><b>" +
                n.getTitulo() +
                "</b><br>" +
                n.getTipo() +
                "</html>"
            );

            lbl.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
            return lbl;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NotificacoesVisao().setVisible(true));
    }
}