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

public class TelaCadastro extends JFrame {
    private final JPanel painelSelecao;
    private final JPanel painelCliente;
    private final JPanel painelFuncionario;
    private Image backgroundImage;
    
    public TelaCadastro() {
        setTitle("SGB - Cadastro");
        setSize(650, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new CardLayout());
        
        // Carregar imagem de fundo
        carregarImagemFundo();
        
        // Criar os painéis
        painelSelecao = criarPainelSelecao();
        painelCliente = criarPainelCliente();
        painelFuncionario = criarPainelFuncionario();
        
        add(painelSelecao, "Selecao");
        add(painelCliente, "Cliente");
        add(painelFuncionario, "Funcionario");
        
        mostrarPainel("Selecao");
    }
    
    private void carregarImagemFundo() {
        try {
            URL imageUrl = new URL("https://i.pinimg.com/736x/10/32/b2/1032b2fd3b2da79d6ec028f1959c0be0.jpg");
            backgroundImage = ImageIO.read(imageUrl);
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem de fundo: " + e.getMessage());
        }
    }
    
    private JPanel criarPainelSelecao() {
        // Painel com imagem de fundo
        JPanel painel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                                        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    
                    // Overlay escuro para melhor contraste
                    g2d.setColor(new Color(0, 0, 0, 100));
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        
        painel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Card central branco
        JPanel cardCentral = new JPanel();
        cardCentral.setLayout(new BoxLayout(cardCentral, BoxLayout.Y_AXIS));
        cardCentral.setBackground(new Color(255, 255, 255, 240));
        cardCentral.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(40, 50, 40, 50)
        ));
        cardCentral.setPreferredSize(new Dimension(400, 350));
        
        // Título
        JLabel lblTitulo = new JLabel("Cadastrar", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(50, 50, 50));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardCentral.add(lblTitulo);
        cardCentral.add(Box.createVerticalStrut(40));
        
        // Botão Funcionário
        JButton btnFuncionario = criarBotaoSelecao("Funcionário", new Color(41, 128, 185));
        btnFuncionario.addActionListener(e -> mostrarPainel("Funcionario"));
        btnFuncionario.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardCentral.add(btnFuncionario);
        cardCentral.add(Box.createVerticalStrut(20));
        
        // Botão Cliente
        JButton btnCliente = criarBotaoSelecao("Cliente", new Color(46, 204, 113));
        btnCliente.addActionListener(e -> mostrarPainel("Cliente"));
        btnCliente.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardCentral.add(btnCliente);
        cardCentral.add(Box.createVerticalStrut(30));
        
        // Botão Cadastrar (vermelho)
        JButton btnCadastrar = criarBotaoSelecao("Cadastrar", new Color(192, 57, 43));
        btnCadastrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCadastrar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Selecione o tipo de cadastro acima.",
                "Informação",
                JOptionPane.INFORMATION_MESSAGE);
        });
        cardCentral.add(btnCadastrar);
        
        // Copyright
        JLabel lblCopyright = new JLabel("© 2023 ALL RIGHTS RESERVED", SwingConstants.CENTER);
        lblCopyright.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblCopyright.setForeground(new Color(120, 120, 120));
        lblCopyright.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardCentral.add(Box.createVerticalStrut(25));
        cardCentral.add(lblCopyright);
        
        painel.add(cardCentral, gbc);
        return painel;
    }
    
    private JButton criarBotaoSelecao(String texto, Color cor) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color corAtual = cor;
                if (getModel().isPressed()) {
                    corAtual = cor.darker();
                } else if (getModel().isRollover()) {
                    corAtual = cor.brighter();
                }
                
                g2d.setColor(corAtual);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2d.drawString(getText(), x, y);
            }
        };
        
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(280, 50));
        btn.setMaximumSize(new Dimension(280, 50));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return btn;
    }
    
    private JPanel criarPainelCliente() {
    JPanel painel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    };

    painel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    JPanel cardForm = new JPanel();
    cardForm.setLayout(new BoxLayout(cardForm, BoxLayout.Y_AXIS));
    cardForm.setBackground(new Color(255, 255, 255, 240));
    cardForm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(30, 50, 30, 50)
    ));
    cardForm.setPreferredSize(new Dimension(450, 580));

    JLabel lblTitulo = new JLabel("Cliente", SwingConstants.CENTER);
    lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
    lblTitulo.setForeground(new Color(50, 50, 50));
    lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
    cardForm.add(lblTitulo);
    cardForm.add(Box.createVerticalStrut(25));

    JTextField txtNome = new JTextField();
    JPasswordField txtSenha = new JPasswordField();
    JPasswordField txtConfirmarSenha = new JPasswordField();
    JTextField txtCPF = new JTextField();
    JTextField txtEmail = new JTextField();

    JLabel lblErroNome = criarLabelErro();
    JLabel lblErroSenha = criarLabelErro();
    JLabel lblErroConfirmarSenha = criarLabelErro();
    JLabel lblErroCPF = criarLabelErro();
    JLabel lblErroEmail = criarLabelErro();

    cardForm.add(criarCampoFormulario("Nome", txtNome, lblErroNome));
    cardForm.add(Box.createVerticalStrut(12));
    cardForm.add(criarCampoFormulario("Criar Senha", txtSenha, lblErroSenha));
    cardForm.add(Box.createVerticalStrut(12));
    cardForm.add(criarCampoFormulario("Confirmar Senha", txtConfirmarSenha, lblErroConfirmarSenha));
    cardForm.add(Box.createVerticalStrut(12));
    cardForm.add(criarCampoFormulario("CPF", txtCPF, lblErroCPF));
    cardForm.add(Box.createVerticalStrut(12));
    cardForm.add(criarCampoFormulario("E-mail", txtEmail, lblErroEmail));

    cardForm.add(Box.createVerticalGlue()); // empuja botones hacia el final

    JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
    painelBotoes.setOpaque(false);
    painelBotoes.setAlignmentX(Component.CENTER_ALIGNMENT);
    painelBotoes.setMaximumSize(new Dimension(400, 60));

    JButton btnVoltar = criarBotaoFormulario("Voltar", new Color(150, 150, 150));
    btnVoltar.addActionListener(e -> {
        mostrarPainel("Selecao");
        limparCamposCliente(txtNome, txtSenha, txtConfirmarSenha, txtCPF, txtEmail,
                lblErroNome, lblErroSenha, lblErroConfirmarSenha, lblErroCPF, lblErroEmail);
    });

    JButton btnCadastrar = criarBotaoFormulario("Cadastrar", new Color(192, 57, 43));
    btnCadastrar.addActionListener(e -> {
        if (validarCliente(txtNome, txtSenha, txtConfirmarSenha, txtCPF, txtEmail,
                lblErroNome, lblErroSenha, lblErroConfirmarSenha, lblErroCPF, lblErroEmail)) {
            mostrarMensagemSucesso("Cliente");
            limparCamposCliente(txtNome, txtSenha, txtConfirmarSenha, txtCPF, txtEmail,
                    lblErroNome, lblErroSenha, lblErroConfirmarSenha, lblErroCPF, lblErroEmail);
        }
    });

    painelBotoes.add(btnVoltar);
    painelBotoes.add(btnCadastrar);
    cardForm.add(Box.createVerticalStrut(25));
    cardForm.add(painelBotoes);
    cardForm.add(Box.createVerticalGlue());

    painel.add(cardForm, gbc);
    return painel;
}
    
    private JPanel criarPainelFuncionario() {
    JPanel painel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    };

    painel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    JPanel cardForm = new JPanel();
    cardForm.setLayout(new BoxLayout(cardForm, BoxLayout.Y_AXIS));
    cardForm.setBackground(new Color(255, 255, 255, 240));
    cardForm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(30, 50, 30, 50)
    ));
    cardForm.setPreferredSize(new Dimension(450, 520));

    JLabel lblTitulo = new JLabel("Funcionário", SwingConstants.CENTER);
    lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
    lblTitulo.setForeground(new Color(50, 50, 50));
    lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
    cardForm.add(lblTitulo);
    cardForm.add(Box.createVerticalStrut(25));

    JTextField txtNome = new JTextField();
    JPasswordField txtSenha = new JPasswordField();
    JPasswordField txtConfirmarSenha = new JPasswordField();
    JTextField txtCPF = new JTextField();

    JLabel lblErroNome = criarLabelErro();
    JLabel lblErroSenha = criarLabelErro();
    JLabel lblErroConfirmarSenha = criarLabelErro();
    JLabel lblErroCPF = criarLabelErro();

    cardForm.add(criarCampoFormulario("Nome", txtNome, lblErroNome));
    cardForm.add(Box.createVerticalStrut(12));
    cardForm.add(criarCampoFormulario("Criar Senha", txtSenha, lblErroSenha));
    cardForm.add(Box.createVerticalStrut(12));
    cardForm.add(criarCampoFormulario("Confirmar Senha", txtConfirmarSenha, lblErroConfirmarSenha));
    cardForm.add(Box.createVerticalStrut(12));
    cardForm.add(criarCampoFormulario("CPF", txtCPF, lblErroCPF));

    cardForm.add(Box.createVerticalGlue());

    JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
    painelBotoes.setOpaque(false);
    painelBotoes.setAlignmentX(Component.CENTER_ALIGNMENT);
    painelBotoes.setMaximumSize(new Dimension(400, 60));

    JButton btnVoltar = criarBotaoFormulario("Voltar", new Color(150, 150, 150));
    btnVoltar.addActionListener(e -> {
        mostrarPainel("Selecao");
        limparCamposFuncionario(txtNome, txtSenha, txtConfirmarSenha, txtCPF,
                lblErroNome, lblErroSenha, lblErroConfirmarSenha, lblErroCPF);
    });

    JButton btnCadastrar = criarBotaoFormulario("Cadastrar", new Color(192, 57, 43));
    btnCadastrar.addActionListener(e -> {
        if (validarFuncionario(txtNome, txtSenha, txtConfirmarSenha, txtCPF,
                lblErroNome, lblErroSenha, lblErroConfirmarSenha, lblErroCPF)) {
            mostrarMensagemSucesso("Funcionario");
            limparCamposFuncionario(txtNome, txtSenha, txtConfirmarSenha, txtCPF,
                    lblErroNome, lblErroSenha, lblErroConfirmarSenha, lblErroCPF);
        }
    });

    painelBotoes.add(btnVoltar);
    painelBotoes.add(btnCadastrar);
    cardForm.add(Box.createVerticalStrut(25));
    cardForm.add(painelBotoes);
    cardForm.add(Box.createVerticalGlue());

    painel.add(cardForm, gbc);
    return painel;
}
    
    private JPanel criarCampoFormulario(String label, JTextField campo, JLabel lblErro) {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setOpaque(false);
        painel.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.setMaximumSize(new Dimension(370, 70));
        
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(new Color(80, 80, 80));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setMaximumSize(new Dimension(370, 38));
        
        lblErro.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        painel.add(lbl);
        painel.add(Box.createVerticalStrut(4));
        painel.add(campo);
        painel.add(lblErro);
        
        return painel;
    }
    
    private JLabel criarLabelErro() {
        JLabel lbl = new JLabel(" ");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setForeground(new Color(231, 76, 60));
        return lbl;
    }
    
    private JButton criarBotaoFormulario(String texto, Color cor) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color corAtual = cor;
                if (getModel().isPressed()) {
                    corAtual = cor.darker();
                } else if (getModel().isRollover()) {
                    corAtual = cor.brighter();
                }
                
                g2d.setColor(corAtual);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2d.drawString(getText(), x, y);
            }
        };
        
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(150, 42));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return btn;
    }
    
private boolean validarCliente(JTextField txtNome, JPasswordField txtSenha, JPasswordField txtConfirmarSenha,
                             JTextField txtCPF, JTextField txtEmail, JLabel lblErroNome, JLabel lblErroSenha,
                             JLabel lblErroConfirmarSenha, JLabel lblErroCPF, JLabel lblErroEmail) {
    boolean valido = true;
    String nome = txtNome.getText().trim();
    String senha = new String(txtSenha.getPassword());
    String confirmarSenha = new String(txtConfirmarSenha.getPassword());
    String cpf = txtCPF.getText().trim();
    String email = txtEmail.getText().trim();
    
    // Limpar mensajes de erro existentes
    lblErroNome.setText(" ");
    lblErroSenha.setText(" ");
    lblErroConfirmarSenha.setText(" ");
    lblErroCPF.setText(" ");
    lblErroEmail.setText(" ");
    
    // Validaciones básicas (las que ya tienes)
    if (nome.length() < 4) {
        lblErroNome.setText("Mínimo 4 caracteres!");
        valido = false;
    }
    
    if (senha.length() < 8) {
        lblErroSenha.setText("Mínimo 8 caracteres!");
        valido = false;
    }
    
    if (!senha.equals(confirmarSenha)) {
        lblErroConfirmarSenha.setText("Senha não coincide");
        valido = false;
    }
    
    if (!validarCPF(cpf)) {
        lblErroCPF.setText("CPF inválido");
        valido = false;
    }
    
    if (!validarEmail(email)) {
        lblErroEmail.setText("E-mail inválido");
        valido = false;
    }
    
    // NUEVO: Validaciones con BD
    if (valido) {
        UsuarioDAO dao = new UsuarioDAO();
        
        // Verificar si CPF ya existe
        if (dao.verificarCPFExistente(cpf)) {
            lblErroCPF.setText("CPF já cadastrado!");
            valido = false;
        }
        
        // Verificar si email ya existe
        if (dao.verificarEmailExistente(email)) {
            lblErroEmail.setText("E-mail já cadastrado!");
            valido = false;
        }
        
        // Si todo está bien, registrar en BD
        if (valido) {
            boolean sucesso = dao.registrarUsuario(nome, cpf, email, senha, "CLIENTE");
            
            if (sucesso) {
                mostrarMensagemSucesso("Cliente");
                limparCamposCliente(txtNome, txtSenha, txtConfirmarSenha, txtCPF, txtEmail,
                                  lblErroNome, lblErroSenha, lblErroConfirmarSenha, lblErroCPF, lblErroEmail);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao cadastrar usuário. Tente novamente.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    return valido;
}

private boolean validarFuncionario(JTextField txtNome, JPasswordField txtSenha, JPasswordField txtConfirmarSenha,
                                 JTextField txtCPF, JLabel lblErroNome, JLabel lblErroSenha,
                                 JLabel lblErroConfirmarSenha, JLabel lblErroCPF) {
    boolean valido = true;
    String nome = txtNome.getText().trim();
    String senha = new String(txtSenha.getPassword());
    String confirmarSenha = new String(txtConfirmarSenha.getPassword());
    String cpf = txtCPF.getText().trim();
    
    // Limpar mensajes de erro
    lblErroNome.setText(" ");
    lblErroSenha.setText(" ");
    lblErroConfirmarSenha.setText(" ");
    lblErroCPF.setText(" ");
    
    if (nome.length() < 4) {
        lblErroNome.setText("Mínimo 4 caracteres!");
        valido = false;
    }
    
    if (senha.length() < 8) {
        lblErroSenha.setText("Mínimo 8 caracteres!");
        valido = false;
    }
    
    if (!senha.equals(confirmarSenha)) {
        lblErroConfirmarSenha.setText("Senha não coincide");
        valido = false;
    }
    
    if (!validarCPF(cpf)) {
        lblErroCPF.setText("CPF inválido");
        valido = false;
    }
    
    //BD
    if (valido) {
        UsuarioDAO dao = new UsuarioDAO();
        
        // Verificar CPF
        if (dao.verificarCPFExistente(cpf)) {
            lblErroCPF.setText("CPF já cadastrado!");
            valido = false;
        }
        
        //Registrar en BD
        if (valido) {
            boolean sucesso = dao.registrarUsuario(nome, cpf, "", senha, "FUNCIONARIO");
            
            if (sucesso) {
                mostrarMensagemSucesso("Funcionario");
                limparCamposFuncionario(txtNome, txtSenha, txtConfirmarSenha, txtCPF,
                                      lblErroNome, lblErroSenha, lblErroConfirmarSenha, lblErroCPF);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao cadastrar funcionário. Tente novamente.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    return valido;
}
    
    private boolean validarCPF(String cpf) {
    cpf = cpf.replaceAll("[^0-9]", "");
    if (cpf.length() != 11) {
        return false;
    }
    if (cpf.matches("(\\d)\\1{10}")) {
        return false;
    }
    return cpf.matches("\\d{11}") || cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
}

private boolean validarEmail(String email) {
    return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
}
    
    private void mostrarMensagemSucesso(String tipo) {
        String mensagem = tipo.equals("Cliente") ? 
            "Cadastrado com Sucesso!" : 
            "Cadastro com Sucesso. Esperando Autorização.";
        JOptionPane.showMessageDialog(this, 
            mensagem, 
            "Sucesso", 
            JOptionPane.INFORMATION_MESSAGE);
        
        mostrarPainel("Selecao");
    }
    
    private void limparCamposCliente(JTextField txtNome, JPasswordField txtSenha, JPasswordField txtConfirmarSenha,
                                   JTextField txtCPF, JTextField txtEmail, JLabel lblErroNome, JLabel lblErroSenha,
                                   JLabel lblErroConfirmarSenha, JLabel lblErroCPF, JLabel lblErroEmail) {
        txtNome.setText("");
        txtSenha.setText("");
        txtConfirmarSenha.setText("");
        txtCPF.setText("");
        txtEmail.setText("");
        lblErroNome.setText(" ");
        lblErroSenha.setText(" ");
        lblErroConfirmarSenha.setText(" ");
        lblErroCPF.setText(" ");
        lblErroEmail.setText(" ");
    }
    
    private void limparCamposFuncionario(JTextField txtNome, JPasswordField txtSenha, JPasswordField txtConfirmarSenha,
                                       JTextField txtCPF, JLabel lblErroNome, JLabel lblErroSenha,
                                       JLabel lblErroConfirmarSenha, JLabel lblErroCPF) {
        txtNome.setText("");
        txtSenha.setText("");
        txtConfirmarSenha.setText("");
        txtCPF.setText("");
        lblErroNome.setText(" ");
        lblErroSenha.setText(" ");
        lblErroConfirmarSenha.setText(" ");
        lblErroCPF.setText(" ");
    }
    
    private void mostrarPainel(String nomePainel) {
        CardLayout layout = (CardLayout) getContentPane().getLayout();
        layout.show(getContentPane(), nomePainel);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new TelaCadastro().setVisible(true);
        });
    }
}