/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.imageio.ImageIO;

public class TelaSplash extends JFrame {
    private Image backgroundImage;
    private int progress = 0;
    private Timer progressTimer;
    
    public TelaSplash() {
        setTitle("SGB - Carregando");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        
        try {
            URL imageUrl = new URL("https://wallpapercave.com/wp/ln5mjPz.jpg");
            backgroundImage = ImageIO.read(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JPanel mainPanel = new JPanel() {
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
                    
                    g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    
                    g2d.setColor(new Color(255, 255, 255, 180));
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(240, 240, 240));
        
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        
        JLabel lblLogo = new JLabel("SGB", SwingConstants.CENTER);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblLogo.setForeground(new Color(40, 40, 40));
        lblLogo.setBounds(0, 100, 800, 60);
        mainPanel.add(lblLogo);
        
        JLabel lblMensagem = new JLabel("BEM-VINDO! NOVAMENTE 😊", SwingConstants.CENTER);
        lblMensagem.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblMensagem.setForeground(new Color(60, 60, 60));
        lblMensagem.setBounds(0, 180, 800, 40);
        mainPanel.add(lblMensagem);

        JLabel lblSubtitulo = new JLabel("Sistema de Gestão de Biblioteca", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSubtitulo.setForeground(new Color(100, 100, 100));
        lblSubtitulo.setBounds(0, 230, 800, 30);
        mainPanel.add(lblSubtitulo);
        
        JProgressBar progressBar = new JProgressBar() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(new Color(220, 220, 220));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                int progressWidth = (int) (getWidth() * (getValue() / 100.0));
                g2d.setColor(new Color(41, 128, 185));
                g2d.fillRoundRect(0, 0, progressWidth, getHeight(), 10, 10);
            }
        };
        progressBar.setBounds(250, 320, 300, 12);
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        progressBar.setBorderPainted(false);
        progressBar.setStringPainted(false);
        mainPanel.add(progressBar);
        
        JLabel lblCarregando = new JLabel("Carregando...", SwingConstants.CENTER);
        lblCarregando.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblCarregando.setForeground(new Color(120, 120, 120));
        lblCarregando.setBounds(0, 345, 800, 20);
        mainPanel.add(lblCarregando);
        
        JLabel lblCopyright = new JLabel("© 2023 ALL RIGHTS RESERVED", SwingConstants.CENTER);
        lblCopyright.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblCopyright.setForeground(new Color(150, 150, 150));
        lblCopyright.setBounds(0, 460, 800, 20);
        mainPanel.add(lblCopyright);
        
        add(mainPanel);

        progressTimer = new Timer(30, e -> {
            progress += 2;
            progressBar.setValue(progress);
            
            if (progress >= 100) {
                ((Timer)e.getSource()).stop();
            }
        });
        progressTimer.start();
        
        Timer closeTimer = new Timer(2500, (ActionEvent e) -> {
            ((Timer)e.getSource()).stop();
            dispose();
            new TelaMenu().setVisible(true);
        });
        closeTimer.setRepeats(false);
        closeTimer.start();
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new TelaSplash().setVisible(true);
        });
    }
}