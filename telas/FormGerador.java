package telas;

import anotacoes.*;
import enums.DefaultEnum;
import utils.Session;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

public class FormGerador extends JPanel {

        private final Class<?> clazz;
        private final Object instanciaInicial;
        private final Map<String, List<ComboItem>> dadosCombos = new HashMap<>();
        private final Map<String, JComponent> campos = new HashMap<>();
        private final Map<String, CampoFormulario> metadados = new HashMap<>();
        private final Map<Integer, JPanel> linhas = new HashMap<>();
        private final Map<String, List<String>> dependencias = new HashMap<>();
        private final JPanel formulario = new JPanel();

        private final Map<String, Consumer<Object>> acoesBotoes = new HashMap<>();

        private String textoBotaoSalvar = "Salvar";

        public FormGerador(Class<?> clazz) {
                this(clazz, null, "Salvar");
        }

        public FormGerador(Class<?> clazz, Object instanciaInicial) {
                this(clazz, instanciaInicial, "Salvar");
        }

        public FormGerador(Class<?> clazz, Object instanciaInicial, String textoBotaoSalvar) {
                this.clazz = clazz;
                this.instanciaInicial = instanciaInicial;
                this.textoBotaoSalvar = textoBotaoSalvar;

                construirFormulario();
        }

        public void adicionarAcaoBotao(String nomeAcao, Consumer<Object> acao) {
                acoesBotoes.put(nomeAcao, acao);
        }

        private void construirFormulario() {

                setLayout(new BorderLayout());

                formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));
                formulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                for (Field field : clazz.getDeclaredFields()) {

                        CampoFormulario meta = field.getAnnotation(CampoFormulario.class);

                        if (meta == null)
                                continue;

                        metadados.put(field.getName(), meta);

                        JPanel campoPanel = criarCampoFormulario(field, meta);
                        if (!meta.dependeDe().isEmpty()) {
                                dependencias
                                        .computeIfAbsent(meta.dependeDe(), k -> new ArrayList<>())
                                        .add(field.getName());
                        }
                        int linha = meta.linha();
                        if (linha == -1) {
                                linha = linhas.size();
                        }

                        JPanel linhaPanel = linhas.computeIfAbsent(linha, k -> {
                                JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
                                p.setAlignmentX(Component.LEFT_ALIGNMENT);

                                formulario.add(p);
                                formulario.add(Box.createVerticalStrut(10));

                                return p;
                        });

                        linhaPanel.add(campoPanel);
                }

                JScrollPane scroll = new JScrollPane(formulario);
                scroll.setBorder(null);

                JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER));

                JButton salvar = new JButton(textoBotaoSalvar);
                salvar.addActionListener(e -> executarAcao(textoBotaoSalvar));

                JButton limpar = new JButton("Limpar");
                limpar.addActionListener(e -> limparCampos());

                botoes.add(salvar);
                botoes.add(limpar);

                add(scroll, BorderLayout.CENTER);
                add(botoes, BorderLayout.SOUTH);
        }

        private JPanel criarCampoFormulario(Field field, CampoFormulario meta) {

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setAlignmentX(Component.LEFT_ALIGNMENT);

                JLabel label = new JLabel(
                                meta.descricao() + (meta.obrigatorio() ? " *" : ""));

                label.setFont(new Font("Segoe UI", Font.BOLD, 13));
                label.setAlignmentX(Component.LEFT_ALIGNMENT);

                JComponent campo = criarCampo(field, meta);
                campo.setAlignmentX(Component.LEFT_ALIGNMENT);
                preencherValorInicial(field, campo);

                campos.put(field.getName(), campo);

                panel.add(label);
                panel.add(Box.createVerticalStrut(5));
                panel.add(campo);

                return panel;
        }

        private void preencherValorInicial(Field field, Component campo) {

                if (instanciaInicial == null)
                        return;

                try {

                        field.setAccessible(true);

                        Object valor = field.get(instanciaInicial);

                        if (valor == null)return;

                        if (campo instanceof JTextField tf) {
                                tf.setText(valor.toString());

                        } else if (campo instanceof JPasswordField pf) {
                                pf.setText(valor.toString());

                        } else if (campo instanceof JComboBox<?> combo) {

                                for (int i = 0; i < combo.getItemCount(); i++) {

                                        Object item = combo.getItemAt(i);

                                        if (item instanceof ComboItem ci) {

                                                if (Objects.equals(ci.getId(),valor) || Objects.equals(ci.getDescricao(), valor.toString())) {
                                                        combo.setSelectedIndex(i);
                                                        break;
                                                }
                                        }
                                }
                        }

                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        @SuppressWarnings("rawtypes")
        private JComponent criarCampo(Field field, CampoFormulario meta) {

                if (meta.tipo() == TipoCampo.COMBO) {

                        JComboBox<ComboItem> combo = new JComboBox<>();

                        if (!meta.dependeDe().isEmpty()) {
                            combo.setEnabled(false);
                        }

                        if (meta.pesquisavel()) {
                        habilitarPesquisa(
                                field.getName(),
                                combo);
                        }
                        try {
                                List<ComboItem> itens = new ArrayList<>();
                                combo.setPreferredSize(new Dimension(meta.largura(), meta.altura()));
                                combo.setMinimumSize(new Dimension(meta.largura(), meta.altura()));
                                combo.setMaximumSize(new Dimension(meta.largura(), meta.altura()));
                                if (meta.provider() != ComboDadosProvedor.class) {

                                        ComboDadosProvedor provider = meta.provider().getDeclaredConstructor()
                                                        .newInstance();
                                        itens = provider.carregar();
                                        for (ComboItem item : itens) {
                                                combo.addItem(item);
                                        }
                                } 
                                else if (meta.enumType() != DefaultEnum.class) {
                                        Class<? extends Enum> enumClass = (Class<? extends Enum>) meta.enumType();

                                        for (Enum<?> e : enumClass.getEnumConstants()) {
                                                ComboItem item = new ComboItem(e.name(), formatEnum(e.name()));
                                                itens.add(item);
                                                combo.addItem(item);
                                        }

                                        carregarCombo(field.getName(),combo, itens);
                                }
                                else {

                                        for (String opcao : meta.opcoes()) {
                                                ComboItem item = new  ComboItem(null, opcao);
                                                itens.add(item);
                                                combo.addItem(item);
                                        }
                                }
                               
                                combo.addActionListener(e -> {
                                        ComboItem selecionado = (ComboItem) combo.getSelectedItem();

                                        if (selecionado == null)
                                                return;
                                        
                                        Session.setDadoCombo(field.getName(), selecionado.getId() != null ? selecionado.getId() : selecionado.getDescricao().trim());
                                        atualizarDependentes(field.getName());
                                });
                               carregarCombo(field.getName(),combo, itens);
                        } catch (Exception e) {
                                e.printStackTrace();
                        }

                        combo.setSelectedIndex(-1);
                        return combo;
                }

                if (meta.tipo() == TipoCampo.SENHA) {

                        JPasswordField campo = new JPasswordField();

                        campo.setPreferredSize(new Dimension(meta.largura(), meta.altura()));
                        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, meta.altura()));

                        return campo;
                }
                if(meta.tipo() == TipoCampo.MULTTEXTO)
                {
                        JTextArea campo = new JTextArea();
                        campo.setPreferredSize(new Dimension(meta.largura(), meta.altura()));
                        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, meta.altura()));
                        return campo;
                }
                JTextField campo = new JTextField();

                campo.setPreferredSize(new Dimension(meta.largura(), meta.altura()));
                campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, meta.altura()));

                return campo;
        }

        private Object obterValor(JComponent component) {

                if (component instanceof JPasswordField pf) {
                        return new String(pf.getPassword());
                }

                if (component instanceof JTextField tf) {
                        return tf.getText().trim();
                }
                if (component instanceof JTextArea ta) {
                        return ta.getText().trim();
                }
                if (component instanceof JComboBox cb) {
                        Object selecionado = cb.getSelectedItem();
                        if (selecionado == null)
                                return null;
                        if (selecionado instanceof ComboItem item) {
                                return item.getId() != null ? item.getId() : item.getDescricao().trim();
                        }
                        
                        return selecionado;
                }
                return null;
        }

        public Map<String, Object> getDadosFormulario() {

                Map<String, Object> dados = new LinkedHashMap<>();

                for (var campo : campos.entrySet()) {
                        dados.put(campo.getKey(), obterValor(campo.getValue()));
                }

                return dados;
        }

        public boolean validarFormulario() {

                StringBuilder erros = new StringBuilder();

                for (var campo : campos.entrySet()) {

                        CampoFormulario meta = metadados.get(campo.getKey());
                        Object valor = obterValor(campo.getValue());

                        if (meta.obrigatorio()) {

                                boolean vazio = valor == null || valor.toString().trim().isEmpty();

                                if (vazio) {
                                        erros.append("- ")
                                             .append(meta.descricao())
                                             .append("\n");
                                }
                        }
                }

                if (!erros.isEmpty()) {

                        JOptionPane.showMessageDialog(
                                        this,
                                        "Campos obrigatórios:\n\n" + erros,
                                        "Validação",
                                        JOptionPane.WARNING_MESSAGE);

                        return false;
                }

                return true;
        }

        private void executarAcao(String nomeAcao) {

                Consumer<Object> acao = acoesBotoes.get(nomeAcao);

                if (acao == null) {

                        JOptionPane.showMessageDialog(
                                        this,
                                        "Nenhuma ação definida para: " + nomeAcao);

                        return;
                }

                if (!validarFormulario())
                        return;

                try {

                        Object instancia = clazz.getDeclaredConstructor().newInstance();

                        Map<String, Object> dados = getDadosFormulario();

                        for (var entrada : dados.entrySet()) {

                                String campo = entrada.getKey();
                                Object valor = entrada.getValue();

                                if (valor == null)
                                        continue;

                                String setter = "set"
                                                + Character.toUpperCase(campo.charAt(0))
                                                + campo.substring(1);

                                try {

                                        Field field = clazz.getDeclaredField(campo);

                                        Class<?> tipo = field.getType();

                                        var metodo = clazz.getMethod(
                                                        setter,
                                                        tipo);
                                        Object valorConvertido = valor;

                                        if (tipo == int.class || tipo == Integer.class) {
                                                valorConvertido = Integer.parseInt(valor.toString());
                                        } else if (tipo == long.class || tipo == Long.class) {
                                                valorConvertido = Long.parseLong(valor.toString());
                                        } else if (tipo == double.class || tipo == Double.class) {
                                                valorConvertido = Double.parseDouble(valor.toString());
                                        } else if (tipo == float.class || tipo == Float.class) {
                                                valorConvertido = Float.parseFloat(valor.toString());
                                        } else if (tipo == boolean.class || tipo == Boolean.class) {
                                                valorConvertido = Boolean.parseBoolean(valor.toString());
                                        }
                                        metodo.invoke(instancia, valorConvertido);

                                } catch (NoSuchMethodException e) {
                                        try {
                                                clazz.getMethod(setter, String.class).invoke(instancia,valor.toString());

                                        } catch (Exception ignored) {
                                        }
                                }
                        }

                        acao.accept(instancia);

                        limparCampos();

                } catch (Exception e) {

                        e.printStackTrace();

                        JOptionPane.showMessageDialog(
                                        this,
                                        "Erro ao executar ação: " + e.getMessage());
                }
        }

        public void limparCampos() {

                for (JComponent component : campos.values()) {

                        if (component instanceof JTextField tf) {
                                tf.setText("");
                        }

                        if (component instanceof JPasswordField pf) {
                                pf.setText("");
                        }
                }
        }
        private String formatEnum(String valor) {
                String texto = valor.toLowerCase().replace("_", " ");
                return Character.toUpperCase(texto.charAt(0))
                        + texto.substring(1);
        }
        private void atualizarDependentes(String campoPai) {

                List<String> filhos = dependencias.get(campoPai);

                if (filhos == null)
                        return;

                for (String campoFilho : filhos) {

                        JComboBox<ComboItem> comboFilho = (JComboBox<ComboItem>)campos.get(campoFilho);

                        comboFilho.removeAllItems();

                        try {

                        Field fieldFilho = clazz.getDeclaredField(campoFilho);

                        CampoFormulario meta = fieldFilho.getAnnotation(CampoFormulario.class);

                        ComboDadosProvedor provider =
                                meta.provider()
                                        .getDeclaredConstructor()
                                        .newInstance();

                        for (ComboItem item :
                                provider.carregar()) {

                                comboFilho.addItem(item);
                        }

                        comboFilho.setEnabled(true);

                        } catch (Exception ex) {

                        ex.printStackTrace();
                        }
                }
        }

        private void habilitarPesquisa(String nomeCampo, JComboBox<ComboItem> combo) {

                combo.setEditable(true);

                JTextField editor =
                        (JTextField) combo
                                .getEditor()
                                .getEditorComponent();

                editor.getDocument()
                        .addDocumentListener(new DocumentListener() {

                        private void pesquisar() {

                        SwingUtilities.invokeLater(() -> {

                                String texto =
                                        editor.getText()
                                        .toLowerCase()
                                        .trim();

                                List<ComboItem> itens =
                                        dadosCombos
                                                .getOrDefault(
                                                        nomeCampo,
                                                        Collections.emptyList());

                                combo.removeAllItems();

                                for (ComboItem item : itens) {

                                if (texto.isEmpty()
                                        || item.getDescricao()
                                                .toLowerCase()
                                                .contains(texto)) {

                                        combo.addItem(item);
                                }
                                }

                                editor.setText(texto);

                                if (combo.getItemCount() > 0) {
                                combo.showPopup();
                                }
                        });
                        }

                        @Override
                        public void insertUpdate(DocumentEvent e) {
                        pesquisar();
                        }

                        @Override
                        public void removeUpdate(DocumentEvent e) {
                        pesquisar();
                        }

                        @Override
                        public void changedUpdate(DocumentEvent e) {
                        pesquisar();
                        }
                });
        }
        private void carregarCombo(String nomeCampo, JComboBox<ComboItem> combo, List<ComboItem> itens) {

                dadosCombos.put(nomeCampo, new ArrayList<>(itens));

                combo.removeAllItems();

                for (ComboItem item : itens) {
                        combo.addItem(item);
                }

                combo.setSelectedIndex(-1);
        }
}
