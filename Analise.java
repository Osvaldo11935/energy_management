/******************************************
Projecto de Fundamentos de Programacao II
Tema: Sistema de gestão energetica
Nome: Osvaldo Quissola, N. 36452
File Name: Analise.java
Data: 27.05.2026
*******************************************/

public class Analise {}
    
    /*
     * 1. OBJECTIVO GERAL
     * Desenvolver um sistema de gestão energética capaz de administrar o cadastro 
     * de clientes, controlar pagamentos, gerenciar áreas de distribuição de energia 
     * e contadores, enviar notificações sobre possíveis cortes, interrupções e 
     * quedas no fornecimento de energia elétrica, além de permitir que os clientes 
     * acompanhem e gerenciem o seu consumo energético, efetuem pagamentos, 
     * visualizem as suas faturas em atraso para liquidação e interajam diretamente 
     * com o provedor de energia para solicitações, consultas e acompanhamento de serviços.
     
     * 3. GUI - GRAPHIC USER INTERFACE (Telas)
     * 
     * 3.1 - Telas de Autenticação
     *     - LoginVisao: Autenticação de utilizadores
     *     - RecuperarSenhaVisao: Recuperação de palavra-passe
     *     
     * 3.2 - Telas Principais (Dashboard)
     *     - MenuPrincipalVisao: Menu DINÂMICO gerado conforme perfil do usuário
     *     - DashboardClienteVisao: Visão personalizada para clientes
     *     - DashboardAdminVisao: Visão personalizada para administradores
     *     - DashboardTecnicoVisao: Visão personalizada para técnicos
     *     - DashboardGestorVisao: Visão personalizada para gestores
     *     
     * 3.3 - Telas de Gestão de Utilizadores e Perfis (RBAC)
     *     - UsuarioCadastroVisao: Cadastro de novos usuários
     *     - UsuarioConsultaVisao: Consulta de usuários
     *     - UsuarioEdicaoVisao: Edição de dados do usuário
     *     - PerfilCadastroVisao: Cadastro de perfis (ADMIN, CLIENTE, TECNICO, GESTOR)
     *     - PerfilConsultaVisao: Consulta de perfis
     *     - PerfilEdicaoVisao: Edição de perfis
     *     - MenuCadastroVisao: Cadastro de menus do sistema
     *     - MenuConsultaVisao: Consulta de menus
     *     - PerfilMenuAtribuicaoVisao: Atribuição de menus a perfis
     *     
     * 3.4 - Telas de Gestão de Clientes
     *     - ClienteCadastroVisao: Cadastro de novos clientes
     *     - ClienteConsultaVisao: Consulta de clientes cadastrados
     *     - ClienteEdicaoVisao: Edição de dados do cliente
     *     
     * 3.5 - Telas de Gestão de Áreas e Contadores
     *     - AreaDistribuicaoVisao: Gestão de áreas de distribuição
     *     - ContadorGestaoVisao: Cadastro e manutenção de contadores
     *     - ContadorLeituraVisao: Leitura de consumos
     *     
     * 3.6 - Telas de Facturação e Pagamentos (Cliente)
     *     - MinhasFacturasVisao: Lista todas as facturas do cliente
     *     - FacturasAtrasoVisao: Mostra facturas em traço (vencidas)
     *     - DetalheFacturaVisao: Visualização detalhada de uma factura
     *     - PagamentoOnlineVisao: Interface para o cliente efetuar pagamento
     *     - HistoricoPagamentosVisao: Histórico de pagamentos do cliente
     *     - ComprovativoPagamentoVisao: Exibição e impressão do comprovativo
     *     
     * 3.7 - Telas de Gestão de Pessoas
     *     - PessoaCadastroVisao: Cadastro de dados pessoais
     *     - PessoaConsultaVisao: Consulta de pessoas
     *     - PessoaEdicaoVisao: Edição de dados pessoais
     *     
     * 4. ESTRUTURA RBAC (Role-Based Access Control)
     * 
     * 4.1 - Tabela Pessoa (Dados Pessoais)
     *     - id (PK)
     *     - numeroBI
     *     - nomeCompleto
     *     - nomePai
     *     - nomeMae
     *     - dataNascimento
     *     - estadoCivil
     *     - genero
     *     - residencia
     *     - naturalidade
     *     - documentoEmitidoEm
     *     - documentoValidoAte
     *     - altura
	 *     - usuarioId (FK → Usuario.id)
     *     - activo
     * 
     * 4.2 - Tabela Perfil (Role)
     *     - id (PK)
     *     - nome (ADMIN, CLIENTE, TECNICO, GESTOR, CONTABILISTA)
     *     - descricao
     *     - nivelAcesso (1-10, onde 10 é super admin)
     *     - activo
     * 
     * 4.3 - Tabela Usuario 
     *     - id (PK)
     *     - perfilId (FK → Perfil.id)
     *     - nomeUsuario (login)
     *     - email
     *     - numeroTelefone
     *     - palavraPass (hash SHA-256)
     *     - dataUltimoAcesso
     *     - tentativasFalhas
     *     - bloqueado (boolean)
     *     - dataBloqueio
     *     - activo
     * 
     * 4.4 - Tabela Menu (Estrutura de Menus)
     *     - id (PK)
     *     - menuPaiId (FK → Menu.id, para submenus, pode ser NULL)
     *     - codigo (identificador único, ex: "CLIENTE.FACTURAS.ATRASO")
     *     - nome (ex: "Facturas em Atraso")
     *     - descricao (ex: "Visualiza facturas vencidas para pagamento")
     *     - icone (nome do ícone, ex: "warning.png")
     *     - caminhoClasse (Classe da view, ex: "views.cliente.FacturasAtrasoVisao")
     *     - ordem (para ordenação dos menus)
     *     - nivelMinimoAcesso (nível mínimo necessário)
     *     - activo
     * 
     * 4.5 - Tabela PerfilMenu (Relacionamento Perfil x Menu)
     *     - id (PK)
     *     - perfilId (FK → Perfil.id)
     *     - menuId (FK → Menu.id)
     *     - podeVisualizar (boolean)
     *     - podeCriar (boolean)
     *     - podeEditar (boolean)
     *     - podeEliminar (boolean)
     *     - dataAtribuicao
     * 
     * 5. ENTIDADES PRINCIPAIS DO NEGÓCIO
     * 
     * 5.1 - ClienteModelo
     *     int id;
     *     String usuarioId; (FK → Usuario.id)
     *     String tipoCliente (PESSOA_FISICA, PESSOA_JURIDICA);
     *     String nif;
     *     String areaDistribuicaoId;
     *     String contadorId;
     *     Date dataCadastro;
     *     double saldoDevedor;
     *     boolean activo;
     * 
     * 5.2 - AreaDistribuicaoModelo
     *     int id;
     *     String provincia;
     *     String municipio;
     *     String comuna;
     *     String bairro;
     *     String codigoPostal;
     *     String subestacaoId;
     *     boolean activo;
     * 
     * 5.3 - ContadorModelo
     *     int id;
     *     String numeroSerie;
     *     String tipoContador (ANALOGICO, DIGITAL, INTELIGENTE);
     *     String clienteId;
     *     String areaId;
     *     Date dataInstalacao;
     *     double limiteConsumo;
     *     boolean activo;
     * 
     * 5.4 - LeituraConsumoModelo
     *     int id;
     *     int contadorId;
     *     double leituraAnterior;
     *     double leituraActual;
     *     double consumoKwh;
     *     Date periodoInicio;
     *     Date periodoFim;
     *     Date dataLeitura;
     *     String responsavelLeitura;
     * 
     * 5.5 - TarifaModelo
     *     int id;
     *     String nomeTarifa;
     *     double precoKwh;
     *     double taxaFixa;
     *     double multaAtraso;
     *     Date dataVigor;
     *     boolean activo;
     * 
     * 5.6 - FacturaModelo
     *     int id;
     *     String clienteId;
     *     String contadorId;
     *     String leituraId;
     *     String tarifaId;
     *     double consumoKwh;
     *     double valorTotal;
     *     double valorMulta;
     *     double valorActualizado;
     *     Date dataEmissao;
     *     Date dataVencimento;
     *     Date dataPagamento;
     *     String status;
     *     int diasAtraso;
     * 
     * 5.7 - PagamentoModelo
     *     int id;
     *     int facturaId;
     *     String clienteId;
     *     double valorPago;
     *     double valorMultaPaga;
     *     Date dataPagamento;
     *     String metodoPagamento;
     *     String referenciaTransacao;
     *     String status;
     *     String comprovativoPath;
     * 
     * 5.8 - NotificacaoModelo
     *     int id;
     *     String clienteId;
     *     String areaId;
     *     String titulo;
     *     String mensagem;
     *     String tipo;
     *     Date dataEnvio;
     *     boolean lida;
     * 
     * 5.9 - SolicitacaoModelo
     *     int id;
     *     String clienteId;
     *     String tipoSolicitacao;
     *     String descricao;
     *     String prioridade;
     *     Date dataAbertura;
     *     String status;
     *     String tecnicoResponsavel;
     * 
     * 5.10 - CorteEnergiaModelo
     *     int id;
     *     String clienteId;
     *     String areaId;
     *     String motivo;
     *     Date dataInicio;
     *     Date dataFim;
     *     String status;
     * 
     * 6. PERSISTÊNCIA DE DADOS
     * 
     * 6.1 - Tabelas de Apoio / Auxiliares (.tab)
     *     Provincias.tab
     *     Municipios.tab
     *     Comunas.tab
     *     Bairros.tab
     *     TiposCliente.tab
     *     TiposContador.tab
     *     TiposTarifa.tab
     *     MetodosPagamento.tab
     *     TiposNotificacao.tab
     *     TiposSolicitacao.tab
     *     Prioridades.tab
     *     StatusFactura.tab
     * 
     * 6.2 - Ficheiros de Dados (.DAT)
     *     Pessoas.DAT
     *     Perfis.DAT
     *     Usuarios.DAT
     *     Menus.DAT
     *     PerfilMenus.DAT
     *     Clientes.DAT
     *     AreasDistribuicao.DAT
     *     Contadores.DAT
     *     LeituraConsumo.DAT
     *     Tarifas.DAT
     *     Facturas.DAT
     *     Pagamentos.DAT
     *     Notificacoes.DAT
     *     Solicitacoes.DAT
     *     CortesEnergia.DAT
     * 7. Implementacao
	 *	  Linguagem de Programacao: Java Swing 
	 *	  IDE: NotePad++
*/