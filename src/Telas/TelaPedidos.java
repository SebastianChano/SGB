/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package telas;

import model.Livro;
import model.Pedido;
import model.Usuario;
import dao.UsuarioDAO;
import dao.PedidoDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.text.JTextComponent;

public class TelaPedidos extends JFrame {

    // --- Definição de Cores Personalizadas ---
    private static final Color COR_FUNDO_GERAL = new Color(245, 240, 245); 
    private static final Color COR_HEADER = new Color(190, 210, 230); 
    private static final Color COR_INPUT_BORDA = new Color(220, 235, 250); 
    private static final Color COR_BOTAO_INGRESSAR = new Color(180, 45, 45); 
    private static final Color COR_BOTAO_PEDIDO = new Color(70, 130, 180); 
    private static final Color COR_TEXTO_TITULO = Color.BLACK;
    private static final int CORNER_RADIUS = 20; 
    
    // Elementos da Tela de Autenticação
    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JTextField txtData;
    private JButton btnIngresar;

    // Elementos da Tela de Pedido
    private JTextField txtCodigo;
    private JTextField txtCentro;
    private JTextField txtCategoria;
    private JTextField txtDescricao;
    private JTextField txtQuantExistente;
    private JTextField txtQuantMin;
    private JTextField txtQuantMax;
    private JTextField txtQuantPedido;
    private JButton btnFazerPedido;
    private JButton btnBuscarLivro;

    private JPanel painelPrincipal;
    private JPanel painelAutenticacao;
    private JPanel painelPedido;
    
    private Image imagemFundoPedido;
    
    // Información del usuario logueado
    private String usuarioLogado;
    private int usuarioId;
    private Usuario usuario;

    public TelaPedidos() {
        setTitle("SGB - Pedidos");
        setSize(850, 750); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        try {
            URL backgroundUrl = new URL("https://hips.hearstapps.com/hmg-prod/images/the-church-in-auvers-sur-oise-1647447409.jpg?crop=1xw:1xh;center,top&resize=980:*");
            imagemFundoPedido = ImageIO.read(backgroundUrl);
        } catch (IOException e) {
            System.err.println("Erro ao carregar a imagem de fundo: " + e.getMessage());
            imagemFundoPedido = null;
        }
        
        painelPrincipal = new JPanel(new CardLayout());
        
        criarPainelAutenticacao();
        painelPrincipal.add(painelAutenticacao, "Autenticacao");
        
        add(painelPrincipal, BorderLayout.CENTER);
        mostrarPainel("Autenticacao");
    }
    
    // Constructor alternativo que recibe usuario logueado
    public TelaPedidos(String usuarioLogado) {
        this();
        this.usuarioLogado = usuarioLogado;
        
        // Si ya viene con usuario, obtener su ID y saltar autenticación
        if (usuarioLogado != null && !usuarioLogado.isEmpty()) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            this.usuario = usuarioDAO.obterUsuarioPorCredencial(usuarioLogado);
            if (this.usuario != null) {
                this.usuarioId = this.usuario.getId();
                criarPainelPedido();
                mostrarPainel("Pedido");
            }
        }
    }
    
    private class RoundedPanel extends JPanel {
        private Color backgroundColor;
        private int cornerRadius = 15;

        public RoundedPanel(LayoutManager layout, Color bgColor, int radius) {
            super(layout);
            this.backgroundColor = bgColor;
            this.cornerRadius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(cornerRadius, cornerRadius);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graphics.setColor(backgroundColor);
            graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
        }
    }
    
    private JPanel criarInputEstilizado(JTextComponent component, int width) {
        RoundedPanel wrapper = new RoundedPanel(new BorderLayout(), COR_INPUT_BORDA, 15);
        
        wrapper.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3)); 

        component.setBorder(BorderFactory.createEmptyBorder(5, 7, 5, 7)); 
        component.setBackground(Color.WHITE); 
        component.setPreferredSize(new Dimension(width, 35)); 
        
        wrapper.add(component, BorderLayout.CENTER);
        
        wrapper.setMinimumSize(wrapper.getPreferredSize());
        wrapper.setMaximumSize(new Dimension(width, wrapper.getPreferredSize().height));
        
        return wrapper;
    }
    
    private class ImagePanel extends JPanel {
        public ImagePanel() {
            setLayout(new BorderLayout());
            setOpaque(false); 
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagemFundoPedido != null) {
                g.drawImage(imagemFundoPedido, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
    
    private void mostrarPainel(String nomePainel) {
        CardLayout cl = (CardLayout) (painelPrincipal.getLayout());
        cl.show(painelPrincipal, nomePainel);
    }
    
    private void criarPainelAutenticacao() {
        painelAutenticacao = new JPanel(new GridBagLayout());
        painelAutenticacao.setBackground(COR_FUNDO_GERAL); 
        
        JPanel modal = new JPanel(new BorderLayout(0, 25));
        modal.setPreferredSize(new Dimension(500, 500));
        modal.setBackground(Color.WHITE);
        
        modal.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(40, 50, 40, 50)
        ));
        
        JLabel lblAcesso = new JLabel("<html><center>ACESSO SOMENTE AO<br>RESPONSÁVEL AUTORIZADO.</center></html>", SwingConstants.CENTER);
        lblAcesso.setFont(new Font("Arial", Font.BOLD, 24));
        lblAcesso.setForeground(COR_TEXTO_TITULO);
        modal.add(lblAcesso, BorderLayout.NORTH);
        
        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0); 
        gbc.gridwidth = 1;
        gbc.weightx = 1.0; 
        
        int inputWidth = 350; 

        //Usuário
        txtUsuario = new JTextField();
        gbc.gridy = 0; 
        painelForm.add(new JLabel("Usuário:"), gbc);
        gbc.gridy = 1; 
        painelForm.add(criarInputEstilizado(txtUsuario, inputWidth), gbc);
        
        //Senha
        txtSenha = new JPasswordField();
        gbc.gridy = 2; 
        painelForm.add(new JLabel("Senha:"), gbc);
        gbc.gridy = 3; 
        painelForm.add(criarInputEstilizado(txtSenha, inputWidth), gbc);
        
        //Data
        txtData = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        txtData.setEditable(false);
        gbc.gridy = 4; 
        painelForm.add(new JLabel("Data:"), gbc);
        gbc.gridy = 5; 
        painelForm.add(criarInputEstilizado(txtData, inputWidth), gbc);
        
        modal.add(painelForm, BorderLayout.CENTER);
        
        btnIngresar = new JButton("Ingresar");
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 18));
        btnIngresar.setBackground(COR_BOTAO_INGRESSAR);
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setPreferredSize(new Dimension(200, 50));
        
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotao.setOpaque(false);
        painelBotao.add(btnIngresar);
        
        modal.add(painelBotao, BorderLayout.SOUTH);

        btnIngresar.addActionListener(e -> validarAutenticacao());
        
        painelAutenticacao.add(modal); 
    }

    private void validarAutenticacao() {
        String usuario = txtUsuario.getText().trim();
        String senha = new String(txtSenha.getPassword());

        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, complete todos os campos de autenticação.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Usar UsuarioDAO para autenticación real
        UsuarioDAO dao = new UsuarioDAO();
        boolean autenticado = dao.autenticarUsuario(usuario, senha);

        if (autenticado) {
            this.usuarioLogado = usuario;
            this.usuario = dao.obterUsuarioPorCredencial(usuario);
            if (this.usuario != null) {
                this.usuarioId = this.usuario.getId();
            }
            criarPainelPedido(); 
            mostrarPainel("Pedido");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Usuário ou senha incorretos.\nAcesso negado.",
                    "Erro de Autenticação",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void criarPainelPedido() {
        painelPedido = new JPanel(new BorderLayout());
        painelPedido.setBackground(COR_FUNDO_GERAL);
        
        RoundedPanel headerPanel = new RoundedPanel(new BorderLayout(), COR_HEADER, CORNER_RADIUS);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        String nomeUsuario = (usuario != null) ? usuario.getNome() : "Usuário";
        JLabel lblTitulo = new JLabel("Fazer Pedido - " + nomeUsuario, SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(Color.DARK_GRAY);
        headerPanel.add(lblTitulo, BorderLayout.CENTER);
        
        JPanel headerWrapper = new JPanel(new BorderLayout());
        headerWrapper.setOpaque(false);
        headerWrapper.setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 30));
        headerWrapper.add(headerPanel, BorderLayout.CENTER);
        
        painelPedido.add(headerWrapper, BorderLayout.NORTH);
        
        JPanel contentPanel = new JPanel(new GridLayout(1, 2));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel painelFormPedido = criarFormularioPedido();
        contentPanel.add(painelFormPedido);

        RoundedPanel imageWrapper = new RoundedPanel(new BorderLayout(), Color.LIGHT_GRAY, CORNER_RADIUS);
        ImagePanel painelImagem = new ImagePanel();
        imageWrapper.add(painelImagem, BorderLayout.CENTER);

        contentPanel.add(imageWrapper);
        
        painelPedido.add(contentPanel, BorderLayout.CENTER);
        
        painelPrincipal.add(painelPedido, "Pedido");
    }
    
private JPanel criarFormularioPedido() {
    JPanel painel = new JPanel(new GridBagLayout());
    painel.setOpaque(false);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 5, 10, 5);
    gbc.anchor = GridBagConstraints.WEST;
    
    int longInputWidth = 300;
    int shortInputWidth = 120;

    // Configuração das Colunas
    gbc.gridx = 0;
    gbc.weightx = 0.0; 
    gbc.gridx = 1;
    gbc.weightx = 1.0; 
    gbc.fill = GridBagConstraints.HORIZONTAL; 
    
    int y = 0; 

    // Código con botón de búsqueda
    gbc.gridx = 0; gbc.gridy = y; painel.add(new JLabel("Código"), gbc);
    
    JPanel painelCodigo = new JPanel(new BorderLayout(5, 0));
    painelCodigo.setOpaque(false);
    txtCodigo = new JTextField();
    painelCodigo.add(criarInputEstilizado(txtCodigo, shortInputWidth - 60), BorderLayout.CENTER);
    
    btnBuscarLivro = new JButton("🔍");
    btnBuscarLivro.setFont(new Font("Arial", Font.PLAIN, 12));
    btnBuscarLivro.setPreferredSize(new Dimension(40, 35));
    btnBuscarLivro.addActionListener(e -> buscarLivroPorCodigo());
    painelCodigo.add(btnBuscarLivro, BorderLayout.EAST);
    
    gbc.gridx = 1; painel.add(painelCodigo, gbc);
    y++;

    // Centro
    gbc.gridx = 0; gbc.gridy = y; painel.add(new JLabel("Centro"), gbc);
    gbc.gridx = 1; txtCentro = new JTextField(); painel.add(criarInputEstilizado(txtCentro, shortInputWidth), gbc);
    y++;

    // Categoria
    gbc.gridx = 0; gbc.gridy = y; painel.add(new JLabel("Categoria"), gbc);
    gbc.gridx = 1; txtCategoria = new JTextField(); painel.add(criarInputEstilizado(txtCategoria, longInputWidth), gbc);
    y++;

    // Descrição
    gbc.gridx = 0; gbc.gridy = y; painel.add(new JLabel("Descrição"), gbc);
    gbc.gridx = 1; txtDescricao = new JTextField(); painel.add(criarInputEstilizado(txtDescricao, longInputWidth), gbc);
    y++;

    // Quant. Existente
    gbc.gridx = 0; gbc.gridy = y; painel.add(new JLabel("Quant. Existente"), gbc);
    gbc.gridx = 1; txtQuantExistente = new JTextField(); 
    
    // Agregar listener para cálculo automático
    txtQuantExistente.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            calcularQuantidadePedido();
        }
    });
    
    painel.add(criarInputEstilizado(txtQuantExistente, shortInputWidth), gbc);
    y++;

    // Quant. Min.
    gbc.gridx = 0; gbc.gridy = y; painel.add(new JLabel("Quant. Min."), gbc);
    gbc.gridx = 1; txtQuantMin = new JTextField(); 
    
    // Agregar listener para cálculo automático
    txtQuantMin.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            calcularQuantidadePedido();
        }
    });
    
    painel.add(criarInputEstilizado(txtQuantMin, shortInputWidth), gbc);
    y++;

    // Quant. Max.
    gbc.gridx = 0; gbc.gridy = y; painel.add(new JLabel("Quant. Max."), gbc);
    gbc.gridx = 1; txtQuantMax = new JTextField(); painel.add(criarInputEstilizado(txtQuantMax, shortInputWidth), gbc);
    y++;

    // Quant. Pedido - CORREGIDO: AHORA ES EDITABLE
    gbc.gridx = 0; gbc.gridy = y; painel.add(new JLabel("Quant. Pedido"), gbc);
    gbc.gridx = 1; txtQuantPedido = new JTextField(); 
    
    // Validar que solo se ingresen números
    txtQuantPedido.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                e.consume(); // No permitir caracteres no numéricos
                Toolkit.getDefaultToolkit().beep(); // Sonido de error opcional
            }
        }
    });
    
    painel.add(criarInputEstilizado(txtQuantPedido, shortInputWidth), gbc);
    y++;

    // Botão Fazer Pedido
    gbc.gridx = 1; gbc.gridy = y; 
    gbc.anchor = GridBagConstraints.WEST; 
    gbc.fill = GridBagConstraints.NONE; 
    gbc.insets = new Insets(25, 5, 10, 5); 

    btnFazerPedido = new JButton("Fazer Pedido");
    btnFazerPedido.setFont(new Font("Arial", Font.BOLD, 16));
    btnFazerPedido.setBackground(COR_BOTAO_PEDIDO);
    btnFazerPedido.setForeground(Color.WHITE);
    btnFazerPedido.setPreferredSize(new Dimension(180, 40));
    btnFazerPedido.addActionListener(e -> fazerPedido());
    
    painel.add(btnFazerPedido, gbc);
    
    return painel;
}

// Método para calcular cantidad automáticamente
private void calcularQuantidadePedido() {
    try {
        String quantExistenteStr = txtQuantExistente.getText().trim();
        String quantMinStr = txtQuantMin.getText().trim();
        
        if (!quantExistenteStr.isEmpty() && !quantMinStr.isEmpty()) {
            int quantExistente = Integer.parseInt(quantExistenteStr);
            int quantMin = Integer.parseInt(quantMinStr);
            
            // Calcular cantidad sugerida
            int quantPedido = quantMin - quantExistente;
            if (quantPedido <= 0) {
                quantPedido = 1; // Mínimo 1 unidad
            }
            
            // Solo actualizar si el usuario no ha modificado manualmente
            // o si el campo está vacío
            if (txtQuantPedido.getText().trim().isEmpty()) {
                txtQuantPedido.setText(String.valueOf(quantPedido));
            }
        }
    } catch (NumberFormatException e) {
        // Ignorar errores de formato temporal
    }
}

    private void buscarLivroPorCodigo() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um código para buscar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        PedidoDAO pedidoDAO = new PedidoDAO();
        Livro livro = pedidoDAO.buscarLivroParaPedido(codigo);
        
        if (livro != null) {
            // Autocompletar campos con datos del libro
            txtCentro.setText(livro.getCentro());
            txtCategoria.setText(livro.getCategoria());
            txtDescricao.setText(livro.getDescricao());
            txtQuantExistente.setText(String.valueOf(livro.getQuantidade()));
            txtQuantMin.setText(String.valueOf(livro.getQuantidadeMin()));
            txtQuantMax.setText(String.valueOf(livro.getQuantidadeMax()));
            
            // Calcular cantidad a pedir automáticamente
            int quantPedido = livro.getQuantidadeMin() - livro.getQuantidade();
            if (quantPedido <= 0) {
                quantPedido = 1;
            }
            txtQuantPedido.setText(String.valueOf(quantPedido));
            
            JOptionPane.showMessageDialog(this, 
                "Livro encontrado: " + livro.getDescricao(), 
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, 
                "Código não encontrado no sistema.", 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fazerPedido() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o campo Código.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Verificar si tenemos un usuario válido
        if (usuarioId == -1 || usuario == null) {
            JOptionPane.showMessageDialog(this, 
                "Erro: Usuário não identificado. Faça login novamente.", 
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Buscar libro en la BD para verificar
        PedidoDAO pedidoDAO = new PedidoDAO();
        Livro livro = pedidoDAO.buscarLivroParaPedido(codigo);
        
        if (livro == null) {
            JOptionPane.showMessageDialog(this, 
                "Código não encontrado no sistema.", 
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int quantExistente = Integer.parseInt(txtQuantExistente.getText().trim());
            int quantMin = Integer.parseInt(txtQuantMin.getText().trim());
            int quantMax = Integer.parseInt(txtQuantMax.getText().trim());
            int quantPedido = Integer.parseInt(txtQuantPedido.getText().trim());
            
            if (quantPedido <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Quantidade do pedido deve ser maior que zero.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear pedido
            Pedido pedido = new Pedido();
            pedido.setLivroId(livro.getId());
            pedido.setUsuarioId(this.usuarioId);
            pedido.setCodigo(codigo);
            pedido.setCentro(txtCentro.getText().trim());
            pedido.setCategoria(txtCategoria.getText().trim());
            pedido.setDescricao(txtDescricao.getText().trim());
            pedido.setQuantidadeExistente(quantExistente);
            pedido.setQuantidadeMin(quantMin);
            pedido.setQuantidadeMax(quantMax);
            pedido.setQuantidadePedido(quantPedido);
            pedido.setStatus("PENDENTE");

            boolean sucesso = pedidoDAO.criarPedido(pedido);
            
            if (sucesso) {
                mostrarModalSucesso();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Erro ao registrar pedido no sistema.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "As quantidades devem ser valores numéricos válidos.",
                    "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    private void mostrarModalSucesso() {
        JLayeredPane layeredPane = getRootPane().getLayeredPane();
        
        JPanel overlay = new JPanel(new GridBagLayout());
        overlay.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());
        overlay.setBackground(new Color(0, 0, 0, 100));
        
        // Modal arredondado
        RoundedPanel painelSucesso = new RoundedPanel(new BorderLayout(0, 20), Color.WHITE, CORNER_RADIUS);
        painelSucesso.setPreferredSize(new Dimension(400, 300));
        painelSucesso.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        JLabel lblSucesso = new JLabel(
                "<html><center><h1>Pedido realizado com sucesso!</h1><br>" +
                "Em análise pelo Centro de Distribuição.</center></html>",
                SwingConstants.CENTER);
        lblSucesso.setFont(new Font("Arial", Font.PLAIN, 18));
        painelSucesso.add(lblSucesso, BorderLayout.CENTER);
        
        JButton btnFechar = new JButton("Fazer Novo Pedido");
        btnFechar.setFont(new Font("Arial", Font.BOLD, 16));
        btnFechar.setBackground(COR_BOTAO_PEDIDO);
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setPreferredSize(new Dimension(200, 45));
        
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotao.setOpaque(false);
        painelBotao.add(btnFechar);
        
        painelSucesso.add(painelBotao, BorderLayout.SOUTH);

        btnFechar.addActionListener(e -> {
            limparCamposPedido();
            layeredPane.remove(overlay);
            layeredPane.repaint();
        });
        
        overlay.add(painelSucesso);
        layeredPane.add(overlay, JLayeredPane.MODAL_LAYER);
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    private void limparCamposPedido() {
        txtCodigo.setText("");
        txtCentro.setText("");
        txtCategoria.setText("");
        txtDescricao.setText("");
        txtQuantExistente.setText("");
        txtQuantMin.setText("");
        txtQuantMax.setText("");
        txtQuantPedido.setText("");
        txtCodigo.requestFocus();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            // Para probar sin usuario logueado
            new TelaPedidos().setVisible(true);
            
            // Para probar con usuario logueado (descomenta la línea siguiente)
            // new TelaPedidos("admin").setVisible(true);
        });
    }
}