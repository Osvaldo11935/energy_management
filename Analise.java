/******************************************
Projecto de Fundamentos de Programacao II
Tema: Sistema de gestão energetica
Nome: Osvaldo Quissola, N. 36452
File Name: Analise.java
Data: 27.05.2026
*******************************************/

public class Analise {}
/*

1. OBJECTIVO GERAL

  Desenvolver um sistema de gestão energética capaz de administrar o cadastro de clientes, controlar pagamentos, 
  gerenciar áreas de distribuição de energia e contadores, enviar notificações sobre possíveis cortes, interrupções e 
  quedas no fornecimento de energia elétrica, além de permitir que os clientes acompanhem e gerenciem o seu consumo energético, efetuem pagamentos, 
  visualizem as suas faturas em atraso para liquidação e interajam diretamente com o provedor de energia para solicitações, consultas e acompanhamento de serviços.
     
2. GUI - GRAPHIC USER INTERFACE (Telas)

  ApresentacaoVisao
  ConfiguracaoVisao
  DetalheContratoVisao
  DetalhePessoaVisao
  DetalheUsuarioVisao
  FaturacaoMonitorVisao

  FormAreaDistribuicaoVisao (Formulário de cadastro e atualização das áreas de distribuição de energia.)
  FormAtalhoVisao (Formulário de cadastro e atualização dos atalhos.)
  FormClienteVisao (Formulário de cadastro e atualização dos contratos dos clientes.)
  FormContadorVisao (Formulário de cadastro e atualização dos contadores.)
  FormContratoVisao (Formulário que exibe as etapas do cadastro dos contratos, integrando o FormClienteVisao e o FormContadorVisao)
  FormFaixaConsumoVisao (Formulário de cadastro e atualização das faixas de consumo.)
  FormLoginVisao (Formulário para efetuar o login.)
  FormMenuVisao (Formulário de cadastro e atualização dos menus.)
  FormNotificacaoVisao (Formulário de cadastro e atualização das notificações.)
  FormPerfilVisao (Formulário de cadastro e atualização dos perfis.)
  FormPerfilMenuVisao (Formulário de cadastro e atualização dos menus vinculados a cada perfil.)
  FormPessoaVisao (Formulário de cadastro e atualização dos dados pessoais dos utilizadores.)
  FormSolicitacaoVisao (Formulário de cadastro e atualização das solicitações efetuadas pelos utilizadores (clientes).)
  FormSubestacaoVisao (Formulário de cadastro e atualização das subestações.)
  FormTarifaVisao (Formulário de cadastro e atualização das tarifas.)
  FormUsuarioVisao (Formulário de cadastro e atualização dos utilizadores.)
  FormWizardVisao (Formulário que exibe as etapas do cadastro dos utilizadores, integrando o FormUsuarioVisao e o FormPessoaVisao.)
  
  MenuPrincipalVisao
  
  NotificacoesVisao(Tela que vai exibir as notificações recebidas pelos usuarios(CLIENTE))
  SolicitacaoDetalheVisao(Tela que vai exibir os detalhes das solicitações feitas pelos usuarios(CLIENTE))

  TabelaAreaDistribuicaoVisao (Tabela responsável por exibir os dados das áreas de distribuição. Por intermédio da tabela é possível pesquisar, atualizar e remover um determinado registo.)
  TabelaAtalhoVisao (Tabela responsável por exibir os dados dos atalhos. Por intermédio da tabela é possível pesquisar, atualizar e remover um determinado registo.)
  TabelaCorteEnergiaVisao (Tabela responsável por exibir os dados dos cortes de energia. Por intermédio da tabela é possível pesquisar, atualizar e remover um determinado registo.)
  TabelaFaixaConsumoVisao (Tabela responsável por exibir os dados das faixas de consumo. Por intermédio da tabela é possível pesquisar, atualizar e remover um determinado registo.)
  TabelaFaturaVisao (Tabela responsável por exibir os dados das faturas. Por intermédio da tabela é possível pesquisar, atualizar e remover um determinado registo.)
  TabelaMenuVisao (Tabela responsável por exibir os dados dos menus. Por intermédio da tabela é possível pesquisar, atualizar e remover um determinado registo.)
  TabelaNotificacaoVisao (Tabela responsável por exibir os dados das notificações. Por intermédio da tabela é possível pesquisar, atualizar e remover um determinado registo.)
  TabelaPerfilVisao (Tabela responsável por exibir os dados dos perfis. Por intermédio da tabela é possível pesquisar, atualizar e remover um determinado registo.)
  TabelaPerfilMenuVisao (Tabela responsável por exibir os dados das associações entre perfis e menus. Por intermédio da tabela é possível pesquisar, atualizar e remover um determinado registo.)
  TabelaSolicitacaoVisao (Tabela responsável por exibir os dados das solicitações. Por intermédio da tabela é possível pesquisar, atualizar e remover um determinado registo.)
  TabelaSubestacaoVisao (Tabela responsável por exibir os dados das subestações. Por intermédio da tabela é possível pesquisar, atualizar e remover um determinado registo.)
  TabelaTarifaVisao (Tabela responsável por exibir os dados das tarifas. Por intermédio da tabela é possível pesquisar, atualizar e remover um determinado registo.)
  TabelaUsuariosVisao (Tabela responsável por exibir os dados dos utilizadores. Por intermédio da tabela é possível pesquisar, atualizar e remover um determinado registo.)

  VisualizarFaturaVisao

3. ENTIDADES PRINCIPAIS

   AreaDistribuicaoModelo

    int id;
    String provincia;
    String municipio;
    String comuna;
    String bairro;
    String codigoPostal;
    String subestacaoId;
    boolean activo;

   UsuarioModelo

    int id;
    int perfilId;
    String nomeUsuario;
    String email;
    String numeroTelefone;
    String palavraPass;
    boolean activo

   PessoaModelo

    int id;
    String numeroBI;
    String nomeCompleto;
    String nomePai;
    String nomeMae;
    String dataNascimento;
    String estadoCivil;
    String genero;
    String residencia;
    String naturalidade;
    String documentoEmitidoEm;
    String documentoValidoAte;
    String altura;
	int usuarioId;
    boolean activo;

   ClienteModelo

    int id;
    int usuarioId;
    String tipoContrato;
    String tipoCliente;
    String areaDistribuicaoId;
    Date dataCadastro;
    double saldoDevedor;
    double creditoDisponivel
    double limiteConsumoMensal
    boolean activo;

   AtalhoModelo

    int id;
    int manuId;
    String nome;
    String descricao;
    String teclado;
    int usuarioId;
    boolean activo;

   ContadorModelo

    int id;
    String numeroSerie;
    String tipoContador;
    int clienteId;
    Date dataInstalacao;
    double limiteConsumo;
    boolean activo;

   CorteEnergiaModelo

    int id;
    int clienteId;
    int areaId;
    String motivo;
    Date dataInicio;
    Date dataFim;
    String status;
    boolean activo;

   FaixaConsumoModelo

    int id;
    double limiteMaximo;
    double limiteMinimo;
    double preco;
    String nome;
    String descricao;
    int ordem;
    DataModelo dataCriacao;
    DataModelo dataActualizacao;
    double desconto;
    boolean social;
    String observacoes;
    int tarifaId;
    boolean activo;

   FaturaModelo

    int id;
    int clienteId;
    int contadorId;
    int leituraId;
    int tarifaId;
    double consumoKwh;
    double valorTotal;
    double valorMulta;
    double valorActualizado;
    Date dataEmissao;
    Date dataVencimento;
    Date dataPagamento;
    String status;
    String numeroFatura;
    int diasAtraso;
    boolean activo;

   LeituraConsumoModelo

    int id;
    int contadorId;
    double leituraAnterior;
    double leituraActual;
    double consumoKwh;
    Date periodoInicio;
    Date periodoFim;
    Date dataLeitura;
    int responsavelLeituraId;
    boolean activo;

   MenuModelo

    int id;
    int menuPaiId;
    String codigo;
    String nome;
    String descricao;
    String icone;
    String caminhoClasse;
    int ordem;
    int nivelMinimoAcesso;
    boolean activo;

   NotificacaoModelo

    int id;
    int clienteId;
    int areaId;
    StringBufferModelo titulo;
    StringBufferModelo mensagem;
    StringBufferModelo tipo;
    Date dataEnvio;
    boolean lida;
    boolean activo;

   PagamentoModelo

    int id;
    int facturaId;
    int clienteId;
    double valorPago;
    double valorMultaPaga;
    Date dataPagamento;
    String metodoPagamento;
    String referenciaTransacao;
    String status;
    String comprovativoPath;
    boolean activo;

   PerfilMenuModelo

    int id;
    int perfilId;
    int menuId;
    boolean podeVisualizar;
    boolean podeCriar;
    boolean podeEditar;
    boolean podeEliminar;
    Date dataAtribuicao;
    boolean activo;

   PerfilModelo

    int id;
    String nome;
    String descricao;
    boolean activo;

   SolicitacaoModelo

    int id;
    int usuarioId;
    int contratoId;
    String tipoSolicitacao;
    String descricao;
    String prioridade;
    Date dataAbertura;
    String status;
    int tecnicoResponsavelId;
    int solicitacaoPaiId;
    boolean activo;

   SubestacaoModelo

    int id;
    String codigo;
    String nome;
    String localizacao;
    String provincia;
    String municipio;
    double capacidade;
    double tensaoNominal;
    Date dataInstalacao;
    Date ultimaManutencao;
    int usuarioId;
    double latitude;
    double longitude;
    String observacoes;
    boolean activo;

   TarifaModelo

    int id;
    StringBufferModelo nomeTarifa;
    double precoKwh;
    double taxaFixa;
    double multaAtraso;
    Date dataVigor;
    boolean activo;

4. PERSISTÊNCIA DE DADOS
4.1 - Tabelas de Apoio /auxiliares

    Provincias.tab
    Municipios.tab
    Comunas.tab

4.2 - Ficheiros de Dados
    AreasDistribuicao.DAT
    Atalho.DAT
    Clientes.DAT
    Contadores.DAT
    CortesEnergia.DAT
    FaixaConsumo.DAT
    Fatura.DAT
    LeituraConsumo.DAT
    Menus.DAT
    Notificacao.DAT
    Pagamento.DAT
    PerfilMenu.DAT
    Perfil.DAT
    Pessoa.DAT
    Solicitacao.DAT
    Subestacao.DAT
    Tarifa.DAT
    Usuarios.DAT

5. Implementacao
	Linguagem de Programacao: Java Swing 
	IDE: NotePad++

*/