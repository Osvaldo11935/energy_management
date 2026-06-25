package telas;
import models.FaturaModelo;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.DecimalFormat;
public class TelaVisualizarFatura extends JFrame {
	private final FaturaModelo fatura;
	private final DecimalFormat dinheiro = new DecimalFormat("#,##0.00");
	public TelaVisualizarFatura(FaturaModelo fatura) {
		this.fatura = fatura;
		setTitle("Visualizar Fatura");
		setSize(950, 760);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		construirTela();
	}
	private void construirTela() {
		JPanel root = new JPanel(new BorderLayout());
		root.setBackground(new Color(240, 243, 248));
		root.add(criarCabecalho(), BorderLayout.NORTH);
		root.add(criarCorpo(), BorderLayout.CENTER);
		root.add(criarRodape(), BorderLayout.SOUTH);
		setContentPane(root);
	}
	private JPanel criarCabecalho() {
		JPanel p = new JPanel(new BorderLayout());
		p.setBackground(new Color(18, 63, 120));
		p.setBorder(new EmptyBorder(25, 30, 25, 30));
		JLabel empresa = new JLabel("ENERGIA ANGOLA");
		empresa.setForeground(Color.WHITE);
		empresa.setFont(new Font("Segoe UI", Font.BOLD, 30));
		JLabel numero = new JLabel("FATURA Nº " + fatura.getNumeroFatura());
		numero.setForeground(Color.WHITE);
		numero.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		p.add(empresa, BorderLayout.WEST);
		p.add(numero, BorderLayout.EAST);
		return p;
	}
	private JPanel criarCorpo() {
		JPanel body = new JPanel();
		body.setBackground(new Color(240, 243, 248));
		body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
		body.setBorder(new EmptyBorder(20, 20, 20, 20));
                body.add(criarSecao("INFORMAÇÕES",
                                """
                                        Cliente: %d
                                        Contador: %d
                                        Tarifa: %d

                                        Emissão:
                                        %s

                                        Vencimento:
                                        %s
                                        """
                                .formatted(
                                fatura.getClienteId(),
                                fatura.getContadorId(),
                                fatura.getTarifaId(),
                                fatura.getDataEmissao(),
                                fatura.getDataVencimento()
                                )
                        )
                );
                        body.add(Box.createVerticalStrut(15));
		body.add(criarResumo());
		body.add(Box.createVerticalStrut(15));
        body.add(
                criarSecao(
                        "CONSUMO",
                        """
                        Leitura:
                        %d

                        Consumo:
                        %.2f KWh
                        """
                        .formatted(
                        fatura.getLeituraId(),
                        fatura.getConsumoKwh()
                                                )
                                        )
                                );
		return body;
	}
	private JPanel criarResumo() {
		JPanel p = new JPanel(new GridLayout(1, 3, 15, 15));
		p.setOpaque(false);
		p.add(cardValor("VALOR", fatura.getValorTotal()));
		p.add(cardValor("MULTA", fatura.getValorMulta()));
		p.add(cardValor("TOTAL", fatura.getValorActualizado()));
		return p;
	}
	private JPanel cardValor(String titulo, double valor) {
		JPanel c = new JPanel(new BorderLayout());
		c.setBackground(Color.WHITE);
		c.setBorder(new CompoundBorder(new LineBorder(new Color(220, 220, 220)), new EmptyBorder(20, 20, 20, 20)));
		JLabel l1 = new JLabel(titulo);
		JLabel l2 = new JLabel(dinheiro.format(valor) + " kz");
		l1.setFont(new Font("Segoe UI", Font.BOLD, 15));
		l2.setFont(new Font("Segoe UI", Font.BOLD, 28));
		l2.setForeground(new Color(25, 110, 200));
		c.add(l1, BorderLayout.NORTH);
		c.add(l2, BorderLayout.CENTER);
		return c;
	}
	private JPanel criarSecao(String titulo, String conteudo) {
		JPanel p = new JPanel(new BorderLayout());
		p.setBackground(Color.WHITE);
		p.setBorder(new CompoundBorder(new LineBorder(new Color(220, 220, 220)), new EmptyBorder(20, 20, 20, 20)));
		JLabel t = new JLabel(titulo);
		t.setFont(new Font("Segoe UI", Font.BOLD, 18));
		JTextArea area = new JTextArea(conteudo);
		area.setEditable(false);
		area.setBackground(Color.WHITE);
		area.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		p.add(t, BorderLayout.NORTH);
		p.add(area, BorderLayout.CENTER);
		return p;
	}
	private JPanel criarRodape() {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
		JButton pagar = new JButton("PAGAR");
		JButton pdf = new JButton("BAIXAR PDF");
		pagar.setPreferredSize(new Dimension(170, 45));
		pdf.setPreferredSize(new Dimension(170, 45));
		pagar.setBackground(new Color(39, 174, 96));
		pagar.setForeground(Color.WHITE);
		pdf.setBackground(new Color(41, 128, 185));
		pdf.setForeground(Color.WHITE);
		pagar.addActionListener(e -> pagar());
		pdf.addActionListener(e -> baixar());
		p.add(pagar);
		p.add(pdf);
		return p;
	}
	private void pagar() {
		JOptionPane.showMessageDialog(this, "Pagamento realizado!");
	}
	private void baixar() {
		JOptionPane.showMessageDialog(this, "PDF gerado.");
	}
	public static void main(String args[]) {
		FaturaModelo f = new FaturaModelo(1, 15, 2, 12, 1, 350, 25000, 500, 25500, "2026-06-23", "2026-07-01", "", "PENDENTE", 0, "FT-0001");
		new TelaVisualizarFatura(f).setVisible(true);
	}
}
