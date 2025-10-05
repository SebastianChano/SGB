/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package telas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.net.URL;

public class TelaMenu extends JFrame {
    private Image imgLivros, imgEstatisticas, imgCadastro, imgPedidos, imgSair;
    private String usuarioLogado; // Variable para almacenar el usuario logueado
    
    // Constructor que recibe el usuario logueado
    public TelaMenu(String usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        inicializarTela();
    }
    
    // Constructor por defecto (para compatibilidad)
    public TelaMenu() {
        this.usuarioLogado = ""; // Usuario vacío por defecto
        inicializarTela();
    }
    
    private void inicializarTela() {
        setTitle("SGB - Menu Principal");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 250));
        
        // Carregar imagens dos ícones locales
        carregarImagensLocais();
        
        JPanel painelSuperior = criarPainelSuperior();
        add(painelSuperior, BorderLayout.NORTH);
        
        JPanel painelCentral = criarPainelCentral();
        add(painelCentral, BorderLayout.CENTER);
        
        // Painel inferior - Botão Sair
        JPanel painelInferior = criarPainelInferior();
        add(painelInferior, BorderLayout.SOUTH);
    }
    
    private void carregarImagensLocais() {
        try {
            // Carregar imagens da pasta local
            String basePath = "src/Telas/imagens/";
            imgLivros = ImageIO.read(new File(basePath + "livros.png"));
            imgEstatisticas = ImageIO.read(new File(basePath + "estatisticas.png"));
            imgCadastro = ImageIO.read(new File(basePath + "cadastro.png"));
            imgPedidos = ImageIO.read(new File(basePath + "pedidos.png"));
            imgSair = ImageIO.read(new File(basePath + "sair.png"));
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagens locais: " + e.getMessage());
            criarIconesFallback();
        }
    }
    
    private void criarIconesFallback() {
        imgLivros = criarIconeSimples(Color.BLUE);
        imgEstatisticas = criarIconeSimples(Color.GREEN);
        imgCadastro = criarIconeSimples(Color.ORANGE);
        imgPedidos = criarIconeSimples(Color.RED);
        imgSair = criarIconeSimples(Color.GRAY);
    }
    
    private Image criarIconeSimples(Color cor) {
        int size = 64;
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(cor);
        g2d.fillOval(8, 8, size-16, size-16);
        g2d.dispose();
        return img;
    }
    
    private JPanel criarPainelSuperior() {
        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.setBackground(Color.WHITE);
        painelSuperior.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        JTextField barraBusca = criarBarraBusca();
        
        JButton btnUsuario = criarBotaoUsuario();
        
        painelSuperior.add(barraBusca, BorderLayout.CENTER);
        painelSuperior.add(btnUsuario, BorderLayout.EAST);
        
        return painelSuperior;
    }
    
    private JTextField criarBarraBusca() {
        JTextField barraBusca = new JTextField("Buscar...");
        barraBusca.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        barraBusca.setForeground(new Color(150, 150, 150));
        barraBusca.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        
        barraBusca.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                if (barraBusca.getText().equals("Buscar...")) {
                    barraBusca.setText("");
                    barraBusca.setForeground(new Color(60, 60, 60));
                }
            }
            
            @Override
            public void focusLost(FocusEvent evt) {
                if (barraBusca.getText().isEmpty()) {
                    barraBusca.setText("Buscar...");
                    barraBusca.setForeground(new Color(150, 150, 150));
                }
            }
        });
        
        barraBusca.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    realizarBusqueda(barraBusca.getText().trim());
                }
            }
        });
        
        return barraBusca;
    }
    
    private JButton criarBotaoUsuario() {
        JButton btnUsuario = new JButton("👤 " + (usuarioLogado.isEmpty() ? "Usuário" : usuarioLogado));
        btnUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnUsuario.setFocusPainted(false);
        btnUsuario.setBorderPainted(false);
        btnUsuario.setContentAreaFilled(false);
        btnUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUsuario.setPreferredSize(new Dimension(150, 40));
        
        btnUsuario.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Usuário logado: " + (usuarioLogado.isEmpty() ? "Não identificado" : usuarioLogado),
                "Informação do Usuário",
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        return btnUsuario;
    }
    
    private JPanel criarPainelCentral() {
        JPanel painelCentral = new JPanel(new GridBagLayout());
        painelCentral.setBackground(new Color(245, 245, 250));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        // Primeira linha
        gbc.gridx = 0; gbc.gridy = 0;
        painelCentral.add(criarCardMenu("LIVROS", imgLivros, e -> new TelaLivros().setVisible(true)), gbc);
        
        gbc.gridx = 1;
        painelCentral.add(criarCardMenu("ESTATÍSTICAS", imgEstatisticas, e -> new TelaEstatisticas().setVisible(true)), gbc);
        
        gbc.gridx = 2;
        painelCentral.add(criarCardMenu("CADASTRO", imgCadastro, e -> new TelaCadastro().setVisible(true)), gbc);
        
        // Segunda linha - PEDIDOS CON USUARIO LOGUEADO
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 1;
        painelCentral.add(criarCardMenu("PEDIDOS", imgPedidos, e -> {
            new TelaPedidos(usuarioLogado).setVisible(true);
            this.dispose(); // Cerrar el menú al abrir pedidos
        }), gbc);
        
        return painelCentral;
    }
    
    private JPanel criarCardMenu(String titulo, Image icone, ActionListener action) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setPreferredSize(new Dimension(200, 200));
        
        JPanel iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (icone != null) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    
                    int size = 80;
                    int x = (getWidth() - size) / 2;
                    int y = (getHeight() - size) / 2;
                    g2d.drawImage(icone, x, y, size, size, this);
                }
            }
        };
        iconPanel.setOpaque(false);
        iconPanel.setPreferredSize(new Dimension(150, 100));
        
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(60, 60, 60));
        
        card.add(iconPanel, BorderLayout.CENTER);
        card.add(lblTitulo, BorderLayout.SOUTH);
        
        card.addMouseListener(new MouseAdapter() {
            private Color originalColor = Color.WHITE;
            
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(248, 248, 252));
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(41, 128, 185), 2),
                    BorderFactory.createEmptyBorder(28, 18, 28, 18)
                ));
            }
            
            public void mouseExited(MouseEvent e) {
                card.setBackground(originalColor);
                card.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
            }
            
            public void mouseClicked(MouseEvent e) {
                if (action != null) {
                    action.actionPerformed(new ActionEvent(card, ActionEvent.ACTION_PERFORMED, null));
                }
            }
        });
        
        return card;
    }
    
    private JPanel criarPainelInferior() {
        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        painelInferior.setBackground(Color.WHITE);
        painelInferior.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)));
        
        JButton btnSair = criarBotaoSair();
        painelInferior.add(btnSair);
        btnSair.addActionListener(e -> mostrarDespedida());
        
        return painelInferior;
    }
    
    private JButton criarBotaoSair() {
        JButton btn = new JButton("SAIR");
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(new Color(100, 100, 100));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(new Color(220, 53, 69));
            }
            public void mouseExited(MouseEvent e) {
                btn.setForeground(new Color(100, 100, 100));
            }
        });
        
        return btn;
    }
    
    private void mostrarDespedida() {
        // Van Gogh sair
        JDialog dialogoDespedida = new JDialog(this, "Até a Próxima!", true);
        dialogoDespedida.setSize(600, 400);
        dialogoDespedida.setLocationRelativeTo(this);
        dialogoDespedida.setUndecorated(true);
        
        JPanel panel = new JPanel() {
            private Image bgImage;
            
            {
                try {
                    bgImage = ImageIO.read(new URL("https://i0.wp.com/www.pausa.com.ar/wp-content/uploads/2022/07/LA-NOCHE-ESTRELLADA-van-gogh.jpg?ssl=1"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                                        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    g2d.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                    
                    // Overlay semi-transparente
                    g2d.setColor(new Color(0, 0, 0, 120));
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        
        JLabel lblMensagem = new JLabel("ATÉ A PRÓXIMA! 😊", SwingConstants.CENTER);
        lblMensagem.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblMensagem.setForeground(Color.WHITE);
        
        panel.add(lblMensagem);
        dialogoDespedida.add(panel);
        
        // Timer
        Timer timer = new Timer(2500, e -> {
            dialogoDespedida.dispose();
            dispose();
            new TelaLogin().setVisible(true);
        });
        timer.setRepeats(false);
        timer.start();
        
        dialogoDespedida.setVisible(true);
    }
    
    private void realizarBusqueda(String termo) {
        if (termo.isEmpty() || termo.equals("Buscar...")) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, digite um termo para buscar.", 
                "Busca Vazia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String busca = termo.toLowerCase();
        
        if (busca.contains("livro") || busca.contains("livros") || 
            busca.contains("book") || busca.contains("books") ||
            busca.equals("l") || busca.equals("liv") || busca.equals("livr")) {
            new TelaLivros().setVisible(true);
            
        } else if (busca.contains("estatística") || busca.contains("estatisticas") || 
                  busca.contains("stats") || busca.contains("metric") ||
                  busca.equals("e") || busca.equals("est") || busca.equals("esta")) {
            new TelaEstatisticas().setVisible(true);
            
        } else if (busca.contains("cadastro") || busca.contains("cadastrar") || 
                  busca.contains("registro") || busca.contains("register") ||
                  busca.equals("c") || busca.equals("cad") || busca.equals("cada")) {
            new TelaCadastro().setVisible(true);
            
        } else if (busca.contains("pedido") || busca.contains("pedidos") || 
                  busca.contains("order") || busca.contains("orders") ||
                  busca.equals("p") || busca.equals("ped") || busca.equals("pedi")) {
            // PEDIDOS CON USUARIO LOGUEADO
            new TelaPedidos(usuarioLogado).setVisible(true);
            this.dispose();
            
        } else {
            JOptionPane.showMessageDialog(this, 
                "Nenhum resultado encontrado para: \"" + termo + "\"\n\n" +
                "Tente buscar por:\n" +
                "- Livros, Livro\n" +
                "- Estatísticas, Stats\n" +
                "- Cadastro, Registrar\n" +
                "- Pedidos, Orders", 
                "Busca sem Resultados", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Para probar, puedes usar: new TelaMenu("admin").setVisible(true);
            new TelaMenu().setVisible(true);
        });
    }
}