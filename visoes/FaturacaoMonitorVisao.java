/******************************************
Projecto de Fundamentos de Programacao II
Tema: Sistema de gestão energetica
Nome: Osvaldo Quissola, N. 36452
File Name: FacturacaoMonitorVisao.java
Data: 27.05.2026
*******************************************/
package visoes;

import servicos.*;
import utils.Session;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
public class FaturacaoMonitorVisao extends JFrame {
	private JTextArea logTextArea;
	private JButton btnExecutar;
	private JButton btnParar;
	private JButton btnLimpar;
	private JButton btnFechar;
	private JLabel lblStatus;
	private JLabel lblProcessando;
	private JProgressBar progressBar;
	private JLabel lblProximaExecucao;
	private Timer timerAtualizacao;
	private JTextField txtDiaAtraso;
	private JTextField txtDiaToleranca;
	private JTextField txtDataExecucao;
	private JButton btnAgendar;
	private FaturacaoBackgroundServico service;
	private FaturacaoScheduler scheduler;
	public FaturacaoMonitorVisao() {
		service = FaturacaoBackgroundServico.instancia();
		scheduler = FaturacaoScheduler.obterInstacia();
		setTitle("Monitor de Facturação - ENDE");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		initComponents();
		atualizarStatus();
		iniciarTimerAtualizacao();
		scheduler.iniciar();
	}

	private void initComponents() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lblStatus = new JLabel("Estado: PRONTO");
		lblStatus.setFont(new Font("Arial", Font.BOLD, 14));
		lblStatus.setForeground(new Color(0, 128, 0));
		statusPanel.add(lblStatus);
		lblProcessando = new JLabel("Nenhum processo em execução");
		statusPanel.add(lblProcessando);
		lblProximaExecucao = new JLabel("Próxima execução: " + formatarData(scheduler.getProximaExecucao()));
		statusPanel.add(lblProximaExecucao);
		topPanel.add(statusPanel);

		JPanel agendaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
		agendaPanel.add(new JLabel("Dia de Vencimento:"));
		txtDiaToleranca = new JTextField(3);
		txtDiaToleranca.setPreferredSize(new Dimension(50, 30));
		txtDiaToleranca.setToolTipText("1");
		agendaPanel.add(txtDiaToleranca);
		
		agendaPanel.add(new JLabel("Dia de Atraso a Considerar:"));
		txtDiaAtraso = new JTextField(3);
		txtDiaAtraso.setPreferredSize(new Dimension(50, 30));
		txtDiaAtraso.setToolTipText("1");
		agendaPanel.add(txtDiaAtraso);

		agendaPanel.add(new JLabel("Data execução:"));
		txtDataExecucao = new JTextField(10);
		txtDataExecucao.setPreferredSize(new Dimension(180, 30));
		txtDataExecucao.setToolTipText("dd/MM/yyyy HH:mm:ss");
		agendaPanel.add(txtDataExecucao);

		btnAgendar = new JButton("Agendar");
		btnAgendar.addActionListener(e -> agendarFacturacao());
		agendaPanel.add(btnAgendar);

		topPanel.add(agendaPanel);

		JPanel progressPanel = new JPanel(new BorderLayout());
		progressBar = new JProgressBar(0, 100);
		progressBar.setStringPainted(true);
		progressBar.setPreferredSize(new Dimension(0, 30));
		progressPanel.add(progressBar, BorderLayout.CENTER);
		topPanel.add(progressPanel);
		add(topPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(BorderFactory.createTitledBorder("Log de Execução"));
		logTextArea = new JTextArea();
		logTextArea.setEditable(false);
		logTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		centerPanel.add(new JScrollPane(logTextArea), BorderLayout.CENTER);
		add(centerPanel, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnExecutar = new JButton("Executar Facturação");
		btnExecutar.addActionListener(e -> executarFacturacao());
		bottomPanel.add(btnExecutar);
		btnParar = new JButton("Parar");
		btnParar.setEnabled(false);
		btnParar.addActionListener(e -> pararFacturacao());
		bottomPanel.add(btnParar);
		btnLimpar = new JButton("Limpar Log");
		btnLimpar.addActionListener(e -> limparLog());
		bottomPanel.add(btnLimpar);
		btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(e -> fechar());
		bottomPanel.add(btnFechar);
		add(bottomPanel, BorderLayout.SOUTH);
	}
	private void executarFacturacao() {
		if(service.isExecutando()) {
			JOptionPane.showMessageDialog(this, "Já existe um processo em execução!", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}
		int confirm = JOptionPane.showConfirmDialog(this, "Deseja executar a facturação agora?\n" + "Este processo pode levar alguns minutos.", "Confirmar Execução", JOptionPane.YES_NO_OPTION);
		if(confirm == JOptionPane.YES_OPTION) {
			logTextArea.setText("");
			service.limparLogs();
			btnExecutar.setEnabled(false);
			btnParar.setEnabled(true);
			progressBar.setValue(0);
			lblStatus.setText("Estado: EXECUTANDO");
			lblStatus.setForeground(new Color(255, 165, 0));
			lblProcessando.setText("Processando...");
			SwingWorker<Void, String> worker = new SwingWorker <Void, String>() {
				@Override
				protected Void doInBackground() {
					service.execute();
					return null;
				}
				@Override
				protected void process(java.util.List < String > chunks) {
					for(String msg: chunks) {
						logTextArea.append(msg + "\n");
						logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
					}
				}
				@Override
				protected void done() {
					btnExecutar.setEnabled(true);
					btnParar.setEnabled(false);
					lblStatus.setText("Estado: PRONTO");
					lblStatus.setForeground(new Color(0, 128, 0));
					lblProcessando.setText("Processo concluído");
					progressBar.setValue(100);
					for(String log: service.getLogs()) {
						logTextArea.append(log + "\n");
					}
					logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
				}
			};
			new Thread(() -> {
				while(service.isExecutando()) {
					try {
						Thread.sleep(1000);
						int progress = service.getProgress();
						SwingUtilities.invokeLater(() -> { progressBar.setValue(progress);});
					} catch (InterruptedException e) {
						break;
					}
				}
			}).start();
			worker.execute();
		}
	}
	private void pararFacturacao() {
		if(!service.isExecutando()) {
			return;
		}
		int confirm = JOptionPane.showConfirmDialog(this, "Deseja parar o processo de facturação?", "Confirmar Paragem", JOptionPane.YES_NO_OPTION);
		if(confirm == JOptionPane.YES_OPTION) {
			service.cancel(true);
			btnExecutar.setEnabled(true);
			btnParar.setEnabled(false);
			lblStatus.setText("Status: CANCELADO");
			lblStatus.setForeground(new Color(255, 0, 0));
			lblProcessando.setText("Processo cancelado");
		}
	}
	private void limparLog() {
		logTextArea.setText("");
		service.limparLogs();
	}
	private void fechar() {
		timerAtualizacao.stop();
		dispose();
	}
	private void iniciarTimerAtualizacao() {
		timerAtualizacao = new Timer(5000, e -> atualizarStatus());
		timerAtualizacao.start();
	}
	private void atualizarStatus() {
		boolean executando = service.isExecutando();
		if(executando) {
			lblProcessando.setText("Processando... " + service.getProgress() + "%");
			progressBar.setValue(service.getProgress());
		} else {
			lblProximaExecucao.setText("Próxima execução: " + formatarData(scheduler.getProximaExecucao()));
		}
	}
	private String formatarData(Date data) {
		if(data == null) return "Não agendado";
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(data);
	}
	private void agendarFacturacao() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			sdf.setLenient(false);
			Date data = sdf.parse(txtDataExecucao.getText());

			Session.setDiaAtraso(Integer.parseInt(txtDiaAtraso.getText()));
			Session.setDiaToleranca(Integer.parseInt(txtDiaToleranca.getText()));

			scheduler.iniciar(data);
			lblProximaExecucao.setText("Próxima execução: " + formatarData(data));
			JOptionPane.showMessageDialog(this, "Facturação agendada com sucesso!");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Data inválida.\nFormato: dd/MM/yyyy HH:mm:ss", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			new FaturacaoMonitorVisao().setVisible(true);
		});
	}
}