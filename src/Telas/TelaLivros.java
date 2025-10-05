/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package telas;

import model.Livro;
import dao.LivroDAO;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class TelaLivros extends JFrame {
    
    // Elementos do Formulário
    private JTextField txtCodigo;
    private JTextField txtCentro;
    private JRadioButton rbSalida;
    private JRadioButton rbEntrada;
    private ButtonGroup bgTipoMovimento;
    private JTextField txtCategoria;
    private JTextField txtDescricao;
    private JButton btnProcurar;
    private JButton btnLimpar;
    
    // Elementos da Tabela
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaLivros() {
        setTitle("SGB - Gestão de Livros");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 250)); 

        // Painel principal que contém todo o conteúdo (Formulário + Tabela)
        JPanel painelPrincipal = new JPanel(new BorderLayout(25, 0));
        painelPrincipal.setBackground(new Color(245, 245, 250));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        JPanel painelEsquerdo = criarFormulario();
        painelEsquerdo.setPreferredSize(new Dimension(420, 650));

        JPanel painelDireito = criarPainelResultados();
        
        painelPrincipal.add(painelEsquerdo, BorderLayout.WEST);
        painelPrincipal.add(painelDireito, BorderLayout.CENTER);
        
        add(painelPrincipal, BorderLayout.CENTER);
    }
    
    private JPanel criarFormulario() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(Color.WHITE);
        
        // Adiciona cantos arredondados discretos ao painel do formulário
        painel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(30, 25, 30, 25)
        ));

        JLabel lblTitulo = new JLabel("Procurar código");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(50, 50, 50));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.add(lblTitulo);
        
        painel.add(Box.createVerticalStrut(8));

        JSeparator separador = new JSeparator();
        separador.setMaximumSize(new Dimension(370, 1));
        separador.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.add(separador);
        
        painel.add(Box.createVerticalStrut(25));

        // Campo Código
        painel.add(criarCampoTexto("Código", txtCodigo = new JTextField()));
        painel.add(Box.createVerticalStrut(18));
        
        // Campo Centro
        painel.add(criarCampoTexto("Centro", txtCentro = new JTextField()));
        painel.add(Box.createVerticalStrut(18));

        // Tipo de movimento - Radio buttons
        JLabel lblTipo = new JLabel("Tipo de movimento");
        lblTipo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTipo.setForeground(new Color(80, 80, 80));
        lblTipo.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.add(lblTipo);
        painel.add(Box.createVerticalStrut(10));
        
        JPanel painelRadio = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        painelRadio.setBackground(Color.WHITE);
        painelRadio.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelRadio.setMaximumSize(new Dimension(370, 40));
        
        rbSalida = new JRadioButton("Saida");
        rbEntrada = new JRadioButton("Entrada");
        bgTipoMovimento = new ButtonGroup();
        bgTipoMovimento.add(rbSalida);
        bgTipoMovimento.add(rbEntrada);
        
        estilizarRadioButton(rbSalida);
        estilizarRadioButton(rbEntrada);
        
        painelRadio.add(rbSalida);
        painelRadio.add(rbEntrada);
        painel.add(painelRadio);
        
        painel.add(Box.createVerticalStrut(18));

        // Campo Categoria
        painel.add(criarCampoTexto("Categoria", txtCategoria = new JTextField()));
        painel.add(Box.createVerticalStrut(18));
        
        // Campo Descrição
        painel.add(criarCampoTexto("Descrição", txtDescricao = new JTextField()));
        painel.add(Box.createVerticalStrut(30));

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        painelBotoes.setBackground(Color.WHITE);
        painelBotoes.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelBotoes.setMaximumSize(new Dimension(370, 50));
        
        btnProcurar = criarBotao("Procurar", new Color(41, 128, 185), Color.WHITE);
        btnLimpar = criarBotao("Limpar", new Color(240, 240, 240), new Color(80, 80, 80));
        
        btnProcurar.addActionListener(e -> procurarLivros());
        btnLimpar.addActionListener(e -> limparCampos());
        
        painelBotoes.add(btnProcurar);
        painelBotoes.add(btnLimpar);
        painel.add(painelBotoes);
        
        painel.add(Box.createVerticalGlue());
        
        return painel;
    }

    private JPanel criarCampoTexto(String label, JTextField campo) {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(Color.WHITE);
        painel.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.setMaximumSize(new Dimension(370, 65));
        
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setForeground(new Color(80, 80, 80));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setMaximumSize(new Dimension(370, 42));
        
        campo.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(41, 128, 185), 2),
                    BorderFactory.createEmptyBorder(9, 13, 9, 13)
                ));
            }
            public void focusLost(FocusEvent e) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                    BorderFactory.createEmptyBorder(10, 14, 10, 14)
                ));
            }
        });
        
        painel.add(lbl);
        painel.add(Box.createVerticalStrut(6));
        painel.add(campo);
        return painel;
    }

    private void estilizarRadioButton(JRadioButton rb) {
        rb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rb.setBackground(Color.WHITE);
        rb.setFocusPainted(false);
        rb.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private JButton criarBotao(String texto, Color corFundo, Color corTexto) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color cor = corFundo;
                if (getModel().isPressed()) {
                    cor = corFundo.darker();
                } else if (getModel().isRollover()) {
                    // Hover effect
                    if (corFundo.equals(new Color(41, 128, 185))) {
                        cor = new Color(52, 152, 219);
                    } else {
                        cor = new Color(230, 230, 230);
                    }
                }
                
                g2d.setColor(cor);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);

                g2d.setColor(corTexto);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2d.drawString(getText(), x, y);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(170, 42));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JPanel criarPainelResultados() {
        JPanel painel = new JPanel(new BorderLayout(0, 0));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));

        JPanel painelCabecalho = new JPanel(new BorderLayout());
        painelCabecalho.setBackground(Color.WHITE);
        painelCabecalho.setBorder(BorderFactory.createEmptyBorder(20, 20, 15, 20));
        
        JLabel lblTitulo = new JLabel("Resultados da Busca");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(50, 50, 50));
        painelCabecalho.add(lblTitulo, BorderLayout.WEST);
        painel.add(painelCabecalho, BorderLayout.NORTH);

        // Criar modelo e tabela
        String[] colunas = { "ID", "DESCRIÇÃO", "CÓDIGO INTERNO", "QUANT. Nº", "QUAN. CD", "TRÁFICO", "QUANT. MIN", "QUANT. MAX" };
        
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        tabela = new JTable(modeloTabela);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabela.setRowHeight(35);
        tabela.setShowGrid(true);
        tabela.setGridColor(new Color(235, 235, 235));
        tabela.setIntercellSpacing(new Dimension(1, 1));
        
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabela.getTableHeader().setBackground(new Color(250, 250, 252));
        tabela.getTableHeader().setForeground(new Color(70, 70, 70));
        tabela.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)));
        
        tabela.setSelectionBackground(new Color(230, 240, 255));
        tabela.setSelectionForeground(new Color(40, 40, 40));

        tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(240);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(130);
        
        DefaultTableCellRenderer centralizador = new DefaultTableCellRenderer();
        centralizador.setHorizontalAlignment(JLabel.CENTER);
        
        for (int i = 0; i < 8; i++) {
            if (i != 1) {
                tabela.getColumnModel().getColumn(i).setCellRenderer(centralizador);
            }
        }

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        painel.add(scrollPane, BorderLayout.CENTER);
        return painel;
    }

private void procurarLivros() {
    String codigo = txtCodigo.getText().trim();
    String centro = txtCentro.getText().trim();
    String categoria = txtCategoria.getText().trim();
    String descricao = txtDescricao.getText().trim();
    
    String tipoMovimento = null;
    if (rbEntrada.isSelected()) {
        tipoMovimento = "ENTRADA";
    } else if (rbSalida.isSelected()) {
        tipoMovimento = "SAIDA";
    }
    
    LivroDAO livroDAO = new LivroDAO();
        var livros = livroDAO.buscarLivros(codigo, centro, categoria, descricao, tipoMovimento);
    
    modeloTabela.setRowCount(0); // Limpiar tabla
    
    for (Livro livro : livros) {
        Object[] row = {
            livro.getId(),
            livro.getDescricao(),
            livro.getCodigoInterno(),
            livro.getQuantidade(),
            livro.getQuantidadeCd(),
            livro.getTipoMovimento(),
            livro.getQuantidadeMin(),
            livro.getQuantidadeMax()
        };
        modeloTabela.addRow(row);
    }
    
    if (livros.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "Nenhum livro encontrado com os critérios informados.", 
            "Busca Concluída", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, 
            "Busca realizada com sucesso!\nEncontrados " + livros.size() + " registros.", 
            "Busca Concluída", JOptionPane.INFORMATION_MESSAGE);
    }
}

    private void limparCampos() {
        txtCodigo.setText("");
        txtCentro.setText("");
        bgTipoMovimento.clearSelection();
        txtCategoria.setText("");
        txtDescricao.setText("");
        modeloTabela.setRowCount(0);
        txtCodigo.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new TelaLivros().setVisible(true);
        });
    }
}