/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package telas;

import dao.UsuarioDAO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;

public class TelaLogin extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JLabel lblEsqueceuSenha;
    private Image backgroundImage;
    
    public TelaLogin() {
        setTitle("SGB - Sistema de Gestão");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        
        // Carregar imagem de fundo
        try {
            URL imageUrl = new URL("https://i.pinimg.com/736x/10/32/b2/1032b2fd3b2da79d6ec028f1959c0be0.jpg");
            backgroundImage = ImageIO.read(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(245, 245, 245));
        
        // Painel esquerdo - Formulário de login
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(0, 0, 450, 650);
        leftPanel.setBackground(Color.WHITE);
        
        // Logo
        JLabel lblTitulo = new JLabel("SGB", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 42));
        lblTitulo.setForeground(new Color(40, 40, 40));
        lblTitulo.setBounds(0, 80, 450, 50);
        leftPanel.add(lblTitulo);
        
        JLabel lblSubtitulo = new JLabel("Sistema de Gestão de Biblioteca", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSubtitulo.setForeground(new Color(100, 100, 100));
        lblSubtitulo.setBounds(0, 135, 450, 25);
        leftPanel.add(lblSubtitulo);
        
        // Label Usuário
        JLabel lblUsuario = new JLabel("Usuário");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUsuario.setForeground(new Color(60, 60, 60));
        lblUsuario.setBounds(70, 210, 310, 25);
        leftPanel.add(lblUsuario);

        txtUsuario = new JTextField("Usuario");
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsuario.setBounds(70, 240, 310, 45);
        txtUsuario.setForeground(new Color(150, 150, 150));
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
        txtUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtUsuario.getText().equals("Usuario")) {
                    txtUsuario.setText("");
                    txtUsuario.setForeground(new Color(60, 60, 60));
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtUsuario.getText().isEmpty()) {
                    txtUsuario.setText("Usuario");
                    txtUsuario.setForeground(new Color(150, 150, 150));
                }
            }
        });
        leftPanel.add(txtUsuario);
        
        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSenha.setForeground(new Color(60, 60, 60));
        lblSenha.setBounds(70, 310, 310, 25);
        leftPanel.add(lblSenha);
        
        // Campo Senha
        txtSenha = new JPasswordField();
        txtSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSenha.setBounds(70, 340, 310, 45);
        txtSenha.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        txtSenha.setEchoChar('•');
        leftPanel.add(txtSenha);
        
        lblEsqueceuSenha = new JLabel("Esqueceu a sua senha?");
        lblEsqueceuSenha.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblEsqueceuSenha.setForeground(new Color(41, 128, 185));
        lblEsqueceuSenha.setBounds(70, 395, 310, 20);
        lblEsqueceuSenha.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblEsqueceuSenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(TelaLogin.this, 
                    "Entre em contato com o administrador do sistema.",
                    "Recuperação de Senha",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblEsqueceuSenha.setText("<html><u>Esqueceu a sua senha?</u></html>");
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblEsqueceuSenha.setText("Esqueceu a sua senha?");
            }
        });
        leftPanel.add(lblEsqueceuSenha);
        
        // Botão Entrar
        btnEntrar = new JButton("Ingressar") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(new Color(20, 80, 100));
                } else if (getModel().isRollover()) {
                    g2d.setColor(new Color(35, 110, 130));
                } else {
                    g2d.setColor(new Color(41, 128, 185));
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2d.drawString(getText(), x, y);
            }
        };
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setBounds(70, 440, 310, 50);
        btnEntrar.setBorderPainted(false);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setContentAreaFilled(false);
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEntrar.addActionListener(e -> validarLogin());
        leftPanel.add(btnEntrar);
        
        JLabel lblCopyright = new JLabel("© 2023 ALL RIGHTS RESERVED", SwingConstants.CENTER);
        lblCopyright.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblCopyright.setForeground(new Color(150, 150, 150));
        lblCopyright.setBounds(0, 570, 450, 30);
        leftPanel.add(lblCopyright);
        
        JPanel rightPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                                        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                                        RenderingHints.VALUE_RENDER_QUALITY);
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                        RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    int imgWidth = backgroundImage.getWidth(this);
                    int imgHeight = backgroundImage.getHeight(this);
                    int panelWidth = getWidth();
                    int panelHeight = getHeight();
                    
                    double imgAspect = (double) imgWidth / imgHeight;
                    double panelAspect = (double) panelWidth / panelHeight;
                    
                    int drawWidth, drawHeight, x, y;
                    
                    if (imgAspect > panelAspect) {
                        drawHeight = panelHeight;
                        drawWidth = (int) (drawHeight * imgAspect);
                        x = (panelWidth - drawWidth) / 2;
                        y = 0;
                    } else {
                        drawWidth = panelWidth;
                        drawHeight = (int) (drawWidth / imgAspect);
                        x = 0;
                        y = (panelHeight - drawHeight) / 2;
                    }
                    
                    g2d.drawImage(backgroundImage, x, y, drawWidth, drawHeight, this);
                }
            }
        };
        rightPanel.setBounds(450, 0, 550, 650);
        rightPanel.setBackground(new Color(30, 30, 30));
        
        // Adicionar painéis
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        add(mainPanel);
        
        // Enter para fazer login
        txtSenha.addActionListener(e -> validarLogin());
    }
    
private void validarLogin() {
    String usuario = txtUsuario.getText().trim();
    String senha = new String(txtSenha.getPassword());

    if (usuario.equals("Usuario")) usuario = "";

    if (usuario.isEmpty() || senha.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Por favor, preencha todos os campos.",
                "Atenção",
                JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (senha.length() < 8) {
        JOptionPane.showMessageDialog(this,
                "A senha deve ter no mínimo 8 caracteres.",
                "Atenção",
                JOptionPane.WARNING_MESSAGE);
        return;
    }

    UsuarioDAO dao = new UsuarioDAO();
    boolean autenticado = dao.autenticarUsuario(usuario, senha);

    if (autenticado) {
        JOptionPane.showMessageDialog(this,
                "Login efetuado com sucesso!",
                "Bem-vindo",
                JOptionPane.INFORMATION_MESSAGE);

        dispose();
        new TelaSplash().setVisible(true); // Abre a próxima tela
    } else {
        JOptionPane.showMessageDialog(this,
                "Usuário ou senha incorretos, ou conta não autorizada.",
                "Erro de Login",
                JOptionPane.ERROR_MESSAGE);
    }
}
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}