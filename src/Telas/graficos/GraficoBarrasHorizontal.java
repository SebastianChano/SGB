/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package telas.graficos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GraficoBarrasHorizontal extends JPanel {
    private DadosGrafico dados;
    private Color cor;
    private int hoveredIndex = -1;
    
    public GraficoBarrasHorizontal(DadosGrafico dados, Color cor) {
        this.dados = dados;
        this.cor = cor;
        setPreferredSize(new Dimension(800, 500));
        setOpaque(false);
        
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int oldIndex = hoveredIndex;
                hoveredIndex = -1;
                
                for (int i = 0; i < dados.valores.length; i++) {
                    Rectangle r = getBarraBounds(i);
                    if (r != null && r.contains(e.getPoint())) {
                        hoveredIndex = i;
                        break;
                    }
                }
                
                if (oldIndex != hoveredIndex) {
                    repaint();
                }
            }
        });
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if (hoveredIndex != -1) {
                    hoveredIndex = -1;
                    repaint();
                }
            }
        });
    }
    
    private Rectangle getBarraBounds(int index) {
        int paddingLeft = 150;
        int paddingTop = 40;
        int paddingRight = 40;
        int paddingBottom = 40;
        
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        
        if (width <= 0 || height <= 0 || dados.valores.length == 0) return null;
        
        int barHeight = height / (dados.valores.length * 2);
        int gap = barHeight / 2;
        int y = paddingTop + index * (barHeight + gap);
        int barWidth = (int) (width * dados.valores[index] / dados.maxValor);
        int x = paddingLeft;
        
        return new Rectangle(x, y, barWidth, barHeight);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int paddingLeft = 150;
        int paddingTop = 40;
        int paddingRight = 40;
        int width = getWidth() - paddingLeft - paddingRight;
        
        if (dados.valores.length == 0) {
            g2d.setColor(Color.GRAY);
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
            String msg = "Sem dados disponíveis";
            FontMetrics fm = g2d.getFontMetrics();
            g2d.drawString(msg, getWidth()/2 - fm.stringWidth(msg)/2, getHeight()/2);
            g2d.dispose();
            return;
        }
        
        // Linhas de grade verticais
        g2d.setColor(new Color(240, 240, 240));
        for (int i = 0; i <= 4; i++) {
            int x = paddingLeft + (width * i / 4);
            g2d.drawLine(x, paddingTop, x, getHeight() - 40);
        }
        
        // Labels do eixo X (valores)
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        for (int i = 0; i <= 4; i++) {
            double value = dados.maxValor * i / 4;
            int x = paddingLeft + (width * i / 4);
            String label = String.format("%.0f", value);
            FontMetrics fm = g2d.getFontMetrics();
            g2d.drawString(label, x - fm.stringWidth(label)/2, getHeight() - 15);
        }
        
        // Desenhar barras horizontais
        for (int i = 0; i < dados.valores.length; i++) {
            Rectangle r = getBarraBounds(i);
            if (r != null) {
                if (i == hoveredIndex) {
                    g2d.setColor(cor.brighter());
                } else {
                    g2d.setColor(cor);
                }
                
                g2d.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10);
                
                // Tooltip ao passar o mouse
                if (i == hoveredIndex) {
                    String tooltip = dados.labels[i] + ": " + String.format("%.0f", dados.valores[i]);
                    g2d.setColor(new Color(50, 50, 50, 230));
                    FontMetrics fm = g2d.getFontMetrics();
                    int txtW = fm.stringWidth(tooltip);
                    g2d.fillRoundRect(r.x + r.width + 10, r.y + r.height/2 - 12, txtW + 16, 25, 5, 5);
                    g2d.setColor(Color.WHITE);
                    g2d.drawString(tooltip, r.x + r.width + 18, r.y + r.height/2 + 5);
                }
                
                // Label do produto (eixo Y)
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                String label = dados.labels[i];
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(label, paddingLeft - fm.stringWidth(label) - 10, r.y + r.height/2 + 5);
            }
        }
        
        g2d.dispose();
    }
}