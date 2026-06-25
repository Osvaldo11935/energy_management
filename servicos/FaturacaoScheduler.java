/******************************************
Projecto de Fundamentos de Programacao II
Tema: Sistema de gestão energetica
Nome: Osvaldo Quissola, N. 36452
File Name: FacturacaoScheduler.java
Data: 27.05.2026
*******************************************/

package servicos;

import javax.swing.*;
import java.util.*;
import java.util.Timer;

public class FaturacaoScheduler {
    
    private static FaturacaoScheduler instance;
  
    private Timer timer;
    private boolean agendado;
    private Date proximaExecucao;
    
    private FaturacaoScheduler() {
        this.agendado = false;
    }
    
    public static synchronized FaturacaoScheduler obterInstacia() {
        if (instance == null) {
            instance = new FaturacaoScheduler();
        }
        return instance;
    }
    public void iniciar(Date dataExecucao) {
        if (agendado) {
            parar();
        }

        if (dataExecucao.before(new Date())) {
            throw new IllegalArgumentException( "A data deve ser futura." );
        }

        this.proximaExecucao = dataExecucao;

        long delay = dataExecucao.getTime() - System.currentTimeMillis();

        timer = new Timer(true);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                executarFacturacaoMensal();
            }
        }, delay);

        agendado = true;

        System.out.println("Facturação agendada para: "+ new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dataExecucao));
    }
    public void iniciar() {
        if (agendado) {
            return;
        }
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        if (cal.getTime().before(new Date())) {
            cal.add(Calendar.MONTH, 1);
        }
        
        Date primeiraExecucao = cal.getTime();
        this.proximaExecucao = primeiraExecucao;

        long delay = primeiraExecucao.getTime() - System.currentTimeMillis();

        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                executarFacturacaoMensal();
                agendarProximaExecucao();
            }
        }, delay);
        
        agendado = true;
        
        System.out.println("Facturação agendada para: " + 
                          new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(primeiraExecucao));
    }
    
    private void agendarProximaExecucao() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        this.proximaExecucao = cal.getTime();
        
        if (timer != null) {
            timer.cancel();
            timer = new Timer(true);
        }
        
        long delay = cal.getTime().getTime() - System.currentTimeMillis();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                executarFacturacaoMensal();
                agendarProximaExecucao();
            }
        }, delay);
    }
    
    private void executarFacturacaoMensal() {
        try {
            System.out.println("Iniciando facturação automática mensal...");
            
            SwingUtilities.invokeLater(() -> {
                FaturacaoBackgroundServico servico = FaturacaoBackgroundServico.instancia();
                servico.executarFacturacao();
            });
            
        } catch (Exception e) {
            System.err.println("Erro na facturação automática: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void parar() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        agendado = false;
        System.out.println("Agendador de facturação parado");
    }
    
    public boolean isAgendado() {
        return agendado;
    }
    public Date getProximaExecucao() {
        return proximaExecucao;
    }
}