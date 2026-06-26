package visoes;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
public class TabelaGerador {
	public interface AcaoTabela {
		void executar(int linha, Object[] dadosLinha);
		String getNome();
	}
	public interface AcaoNovo {
		void executar();
	}
	public static JPanel criarTabelaComAcoes(String[] colunas, List < Object[] > dados, List < AcaoTabela > opcoes, String textoBotaoNovo, AcaoNovo acaoNovo) {
		JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
		painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JPanel topo = new JPanel(new BorderLayout(10, 0));
		JPanel painelPesquisa = new JPanel(new BorderLayout(5, 0));
		JLabel lblPesquisa = new JLabel("🔍");
		lblPesquisa.setFont(lblPesquisa.getFont().deriveFont(16f));
		JTextField txtPesquisa = new JTextField();
		txtPesquisa.setPreferredSize(new Dimension(250, 35));
		txtPesquisa.setToolTipText("Digite para pesquisar...");
		painelPesquisa.add(lblPesquisa, BorderLayout.WEST);
		painelPesquisa.add(txtPesquisa, BorderLayout.CENTER);
        topo.add(painelPesquisa, BorderLayout.CENTER);

        if(textoBotaoNovo != null)
        {
            JButton btnNovo = new JButton(textoBotaoNovo);
            btnNovo.setFont(btnNovo.getFont().deriveFont(Font.BOLD));
            btnNovo.setFocusPainted(false);
            btnNovo.setCursor(new Cursor(Cursor.HAND_CURSOR));
            if(acaoNovo != null) {
				btnNovo.addActionListener(e -> acaoNovo.executar());
			}
            topo.add(btnNovo, BorderLayout.EAST);
        }

		String[] colunasComAcoes = new String[colunas.length + 1];
		System.arraycopy(colunas, 0, colunasComAcoes, 0, colunas.length);
		colunasComAcoes[colunas.length] = "";
		DefaultTableModel model = new DefaultTableModel(colunasComAcoes, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == getColumnCount() - 1;
			}
		};
		for(Object[] linha: dados) {
			Object[] nova = new Object[colunasComAcoes.length];
			System.arraycopy(linha, 0, nova, 0, linha.length);
			nova[colunasComAcoes.length - 1] = "⋮";
			model.addRow(nova);
		}
		JTable tabela = new JTable(model);
		tabela.setRowHeight(40);
		tabela.setShowGrid(true);
		tabela.setGridColor(new Color(230, 230, 230));
		tabela.setIntercellSpacing(new Dimension(5, 5));
		tabela.getTableHeader().setFont(tabela.getFont().deriveFont(Font.BOLD));
		tabela.getTableHeader().setBackground(new Color(240, 240, 240));
		tabela.getTableHeader().setPreferredSize(new Dimension(0, 35));
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter < > (model);
		tabela.setRowSorter(sorter);
		txtPesquisa.getDocument().addDocumentListener(new SimpleDocumentListener(texto -> {
			if(texto.trim().isEmpty()) {
				sorter.setRowFilter(null);
			} else {
				sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
			}
		}));
		int colunaAcoes = colunasComAcoes.length - 1;
		TableColumn coluna = tabela.getColumnModel().getColumn(colunaAcoes);
		coluna.setPreferredWidth(60);
		coluna.setMaxWidth(80);
		coluna.setMinWidth(50);
		coluna.setCellRenderer(new MenuRenderer());
		coluna.setCellEditor(new MenuEditor(tabela, opcoes));
		painelPrincipal.add(topo, BorderLayout.NORTH);
		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		painelPrincipal.add(scrollPane, BorderLayout.CENTER);
		return painelPrincipal;
	}
	interface TextoListener {
		void alterar(String texto);
	}
	static class SimpleDocumentListener
	implements javax.swing.event.DocumentListener {
		private final TextoListener listener;
		public SimpleDocumentListener(TextoListener listener) {
			this.listener = listener;
		}
		public void changedUpdate(javax.swing.event.DocumentEvent e) {
			executar(e);
		}
		public void insertUpdate(javax.swing.event.DocumentEvent e) {
			executar(e);
		}
		public void removeUpdate(javax.swing.event.DocumentEvent e) {
			executar(e);
		}
		private void executar(javax.swing.event.DocumentEvent e) {
			try {
				listener.alterar(e.getDocument().getText(0, e.getDocument().getLength()));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	static class MenuRenderer
	extends JButton
	implements TableCellRenderer {
		public MenuRenderer() {
			setText("⋮");
			setFont(getFont().deriveFont(Font.BOLD, 20f));
			setForeground(new Color(100, 100, 100));
			setBackground(Color.WHITE);
			setBorderPainted(false);
			setFocusPainted(false);
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			if(isSelected) {
				setBackground(table.getSelectionBackground());
				setForeground(Color.WHITE);
			} else {
				setBackground(Color.WHITE);
				setForeground(new Color(100, 100, 100));
			}
			return this;
		}
	}
	static class MenuEditor
	extends AbstractCellEditor
	implements TableCellEditor {
		JButton botao = new JButton("⋮");
		JTable tabela;
		List < AcaoTabela > opcoes;
		int linhaAtual;
		public MenuEditor(JTable tabela, List < AcaoTabela > opcoes) {
			this.tabela = tabela;
			this.opcoes = opcoes;
			botao.setFont(botao.getFont().deriveFont(Font.BOLD, 20f));
			botao.setForeground(new Color(100, 100, 100));
			botao.setBackground(Color.WHITE);
			botao.setBorderPainted(false);
			botao.setFocusPainted(false);
			botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
			botao.addActionListener(e -> abrir());
		}
		private void abrir() {
			JPopupMenu menu = new JPopupMenu();
			menu.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
			for(AcaoTabela a: opcoes) {
				JMenuItem item = new JMenuItem(a.getNome());
				item.setFont(item.getFont().deriveFont(13f));
				item.setCursor(new Cursor(Cursor.HAND_CURSOR));
				item.addActionListener(ev -> {
					int linha = tabela.convertRowIndexToModel(linhaAtual);
					int total = tabela.getColumnCount() - 1;
					Object[] dados = new Object[total];
					for(int i = 0; i < total; i++) {
						dados[i] = tabela.getModel().getValueAt(linha, i);
					}
					a.executar(linha, dados);
					fireEditingStopped();
				});
				menu.add(item);
			}
			menu.show(botao, 0, botao.getHeight());
		}
		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			linhaAtual = row;
			SwingUtilities.invokeLater(this::abrir);
			return botao;
		}
		@Override
		public Object getCellEditorValue() {
			return "⋮";
		}
	}
}