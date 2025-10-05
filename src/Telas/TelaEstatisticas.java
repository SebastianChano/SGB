package telas;

import dao.EstatisticasDAO;
import telas.graficos.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TelaEstatisticas extends JFrame {
    
    private static final Color COR_FUNDO = new Color(245, 245, 250);
    private static final Color COR_SELECAO = new Color(192, 57, 43);
    
    private final Color[] CORES_GRAFICO = {
        new Color(138, 43, 226), // Roxo
        new Color(255, 140, 0),  // Laranja
        new Color(46, 204, 113), // Verde
        new Color(241, 196, 15), // Amarelo
        new Color(52, 152, 219)  // Azul
    };
    
    private JPanel painelConteudo;
    private CardLayout cardLayout;
    private List<JButton> botoesMenu;
    private EstatisticasDAO dao;
    
    // Dados dinâmicos carregados do banco de dados
    private DadosGrafico dadosVendas;
    private DadosGrafico dadosCadastros;
    private DadosGrafico dadosMaisSaida;
    private DadosGrafico dadosMenosSaida;
    
    public TelaEstatisticas() {
        setTitle("SGB - Estatísticas");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Inicializar DAO e carregar dados
        dao = new EstatisticasDAO();
        carregarDadosDoBanco();
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(COR_FUNDO);
        
        JPanel painelLateral = criarPainelLateral();
        mainPanel.add(painelLateral, BorderLayout.WEST);
        
        cardLayout = new CardLayout();
        painelConteudo = new JPanel(cardLayout);
        painelConteudo.setBackground(COR_FUNDO);
        
        // Criar painéis com os dados carregados
        painelConteudo.add(criarPainelGrafico(new GraficoLinha(dadosVendas, CORES_GRAFICO[0]), "Vendas Mensais", "Mês", "Valor (R$)"), "VendasMes");
        painelConteudo.add(criarPainelGrafico(new GraficoBarras(dadosCadastros, CORES_GRAFICO), "Cadastros por Categoria", "Categoria", "Quantidade"), "Cadastros");
        painelConteudo.add(criarPainelGrafico(new GraficoPizza(dadosMaisSaida, CORES_GRAFICO), "Produtos Mais Vendidos", "", ""), "MaisSaida");
        painelConteudo.add(criarPainelGrafico(new GraficoBarrasHorizontal(dadosMenosSaida, CORES_GRAFICO[2]), "Produtos Menos Vendidos", "Produto", "Quantidade"), "MenosSaida");
        
        mainPanel.add(painelConteudo, BorderLayout.CENTER);
        setContentPane(mainPanel);
        
        SwingUtilities.invokeLater(() -> atualizarSelecaoBotoes("VendasMes"));
    }
    
    /**
     * Carrega os dados da base de dados usando o DAO
     */
    private void carregarDadosDoBanco() {
        try {
            // Cargar vendas mensais
            Map<String, Double> vendasMap = dao.obterVendasMensais();
            String[] labelsVendas = vendasMap.keySet().toArray(new String[0]);
            double[] valoresVendas = vendasMap.values().stream().mapToDouble(Double::doubleValue).toArray();
            double maxVendas = valoresVendas.length > 0 ? 
                Math.max(20000, calcularMaximo(valoresVendas) * 1.2) : 20000;
            dadosVendas = new DadosGrafico(labelsVendas, valoresVendas, maxVendas);
            
            // Cargar cadastros por categoria
            Map<String, Double> cadastrosMap = dao.obterCadastrosPorCategoria();
            String[] labelsCadastros = cadastrosMap.keySet().toArray(new String[0]);
            double[] valoresCadastros = cadastrosMap.values().stream().mapToDouble(Double::doubleValue).toArray();
            double maxCadastros = valoresCadastros.length > 0 ? 
                Math.max(500, calcularMaximo(valoresCadastros) * 1.2) : 500;
            dadosCadastros = new DadosGrafico(labelsCadastros, valoresCadastros, maxCadastros);
            
            // Cargar produtos mais vendidos
            Map<String, Double> maisVendidosMap = dao.obterProdutosMaisVendidos();
            String[] labelsMaisVendidos = maisVendidosMap.keySet().toArray(new String[0]);
            double[] valoresMaisVendidos = maisVendidosMap.values().stream().mapToDouble(Double::doubleValue).toArray();
            dadosMaisSaida = new DadosGrafico(labelsMaisVendidos, valoresMaisVendidos, 100);
            
            // Cargar produtos menos vendidos
            Map<String, Double> menosVendidosMap = dao.obterProdutosMenosVendidos();
            String[] labelsMenosVendidos = menosVendidosMap.keySet().toArray(new String[0]);
            double[] valoresMenosVendidos = menosVendidosMap.values().stream().mapToDouble(Double::doubleValue).toArray();
            double maxMenosVendidos = valoresMenosVendidos.length > 0 ? 
                Math.max(100, calcularMaximo(valoresMenosVendidos) * 1.2) : 100;
            dadosMenosSaida = new DadosGrafico(labelsMenosVendidos, valoresMenosVendidos, maxMenosVendidos);
            
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
            e.printStackTrace();
            // Em caso de erro, usar dados padrão
            carregarDadosPadrao();
        }
    }
    
    /**
     * Carrega dados padrão em caso de erro na conexão
     */
    private void carregarDadosPadrao() {
        dadosVendas = new DadosGrafico(
            new String[]{"Janeiro", "Fevereiro", "Março", "Abril", "Maio"},
            new double[]{8000, 10000, 12500, 14000, 16000},
            20000
        );
        
        dadosCadastros = new DadosGrafico(
            new String[]{"Livros", "Utensílios", "Papelaria", "Outros"},
            new double[]{450, 320, 280, 150},
            500
        );
        
        dadosMaisSaida = new DadosGrafico(
            new String[]{"Dom Quixote", "O Pequeno Príncipe", "1984", "Caixa Cores", "Noites Brancas"},
            new double[]{35.0, 25.0, 20.0, 10.0, 10.0},
            100
        );
        
        dadosMenosSaida = new DadosGrafico(
            new String[]{"Quebrando Gelo", "48 Leis Poder", "Canetas Col.1", "Marca Páginas"},
            new double[]{85, 70, 55, 30},
            100
        );
    }
    
    /**
     * Calcula o valor máximo de um array
     */
    private double calcularMaximo(double[] valores) {
        double max = valores[0];
        for (double valor : valores) {
            if (valor > max) max = valor;
        }
        return max;
    }
    
    /**
     * Método público para recarregar os dados
     */
    public void recarregarDados() {
        carregarDadosDoBanco();
        // Recriar os painéis com os novos dados
        painelConteudo.removeAll();
        painelConteudo.add(criarPainelGrafico(new GraficoLinha(dadosVendas, CORES_GRAFICO[0]), "Vendas Mensais", "Mês", "Valor (R$)"), "VendasMes");
        painelConteudo.add(criarPainelGrafico(new GraficoBarras(dadosCadastros, CORES_GRAFICO), "Cadastros por Categoria", "Categoria", "Quantidade"), "Cadastros");
        painelConteudo.add(criarPainelGrafico(new GraficoPizza(dadosMaisSaida, CORES_GRAFICO), "Produtos Mais Vendidos", "", ""), "MaisSaida");
        painelConteudo.add(criarPainelGrafico(new GraficoBarrasHorizontal(dadosMenosSaida, CORES_GRAFICO[2]), "Produtos Menos Vendidos", "Produto", "Quantidade"), "MenosSaida");
        painelConteudo.revalidate();
        painelConteudo.repaint();
    }
    
    private JPanel criarPainelLateral() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Color.WHITE);
        painel.setPreferredSize(new Dimension(250, 0));
        painel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(230, 230, 230)));
        
        JPanel containerTitulo = new JPanel(new BorderLayout());
        containerTitulo.setBackground(Color.WHITE);
        containerTitulo.setBorder(BorderFactory.createEmptyBorder(30, 25, 20, 25));
        
        JLabel lblTitulo = new JLabel("Estatísticas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(50, 50, 50));
        containerTitulo.add(lblTitulo, BorderLayout.NORTH);
        
        JPanel containerBotoes = new JPanel();
        containerBotoes.setLayout(new BoxLayout(containerBotoes, BoxLayout.Y_AXIS));
        containerBotoes.setBackground(Color.WHITE);
        containerBotoes.setBorder(BorderFactory.createEmptyBorder(10, 25, 20, 25));
        
        botoesMenu = new ArrayList<>();
        String[] opcoes = {"Vendas Mes", "Cadastros", "Mais Saida", "Menos Saida"};
        
        for (String opcao : opcoes) {
            JButton btn = criarBotaoLateral(opcao);
            containerBotoes.add(btn);
            containerBotoes.add(Box.createRigidArea(new Dimension(0, 12)));
            botoesMenu.add(btn);
        }
        
        // Botão para recarregar dados
        JButton btnRecarregar = new JButton("🔄 Atualizar");
        btnRecarregar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnRecarregar.setMaximumSize(new Dimension(200, 48));
        btnRecarregar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRecarregar.setHorizontalAlignment(SwingConstants.CENTER);
        btnRecarregar.setFocusPainted(false);
        btnRecarregar.setBackground(new Color(46, 204, 113));
        btnRecarregar.setForeground(Color.WHITE);
        btnRecarregar.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btnRecarregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRecarregar.addActionListener(e -> {
            btnRecarregar.setEnabled(false);
            btnRecarregar.setText("Carregando...");
            new Thread(() -> {
                recarregarDados();
                SwingUtilities.invokeLater(() -> {
                    btnRecarregar.setText("🔄 Atualizar");
                    btnRecarregar.setEnabled(true);
                    JOptionPane.showMessageDialog(this, "Dados atualizados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                });
            }).start();
        });
        
        containerBotoes.add(Box.createRigidArea(new Dimension(0, 20)));
        containerBotoes.add(btnRecarregar);
        
        painel.add(containerTitulo, BorderLayout.NORTH);
        painel.add(containerBotoes, BorderLayout.CENTER);
        
        return painel;
    }
    
    private JButton criarBotaoLateral(String texto) {
        JButton botao = new JButton(texto);
        botao.setAlignmentX(Component.LEFT_ALIGNMENT);
        botao.setMaximumSize(new Dimension(200, 48));
        botao.setFont(new Font("Segoe UI", Font.BOLD, 15));
        botao.setHorizontalAlignment(SwingConstants.LEFT);
        botao.setFocusPainted(false);
        botao.setBackground(Color.WHITE);
        botao.setForeground(new Color(80, 80, 80));
        botao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        botao.addActionListener(e -> {
            String secao = texto.replace(" ", "");
            cardLayout.show(painelConteudo, secao);
            atualizarSelecaoBotoes(secao);
        });
        
        return botao;
    }
    
    private void atualizarSelecaoBotoes(String secaoAtiva) {
        for (JButton btn : botoesMenu) {
            String secao = btn.getText().replace(" ", "");
            if (secao.equals(secaoAtiva)) {
                btn.setBackground(COR_SELECAO);
                btn.setForeground(Color.WHITE);
            } else {
                btn.setBackground(Color.WHITE);
                btn.setForeground(new Color(80, 80, 80));
            }
        }
    }
    
    private JPanel criarPainelGrafico(JComponent grafico, String titulo, String eixoX, String eixoY) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(COR_FUNDO);
        wrapper.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(50, 50, 50));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JPanel painelGraficoCard = new JPanel(new BorderLayout());
        painelGraficoCard.setBackground(Color.WHITE);
        painelGraficoCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        painelGraficoCard.add(grafico, BorderLayout.CENTER);
        
        wrapper.add(lblTitulo, BorderLayout.NORTH);
        wrapper.add(painelGraficoCard, BorderLayout.CENTER);
        
        return wrapper;
    }
}