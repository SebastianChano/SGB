/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package telas.graficos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GraficoBarras extends JPanel {
    private DadosGrafico dados;
    private Color[] cores;
    private int hoveredIndex = -1;
    
    public GraficoBarras(DadosGrafico dados, Color[] cores) {
        this.dados = dados;
        this.cores = cores;
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
        int padding = 60;
        int width = getWidth() - padding * 2;
        int height = getHeight() - padding * 2;
        
        if (width <= 0 || height <= 0 || dados.valores.length == 0) return null;
        
        int barWidth = width / (dados.valores.length * 2);
        int gap = barWidth / 2;
        int x = padding + index * (barWidth + gap);
        int barHeight = (int) (height * dados.valores[index] / dados.maxValor);
        int y = padding + height - barHeight;
        
        return new Rectangle(x, y, barWidth, barHeight);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int padding = 60;
        int width = getWidth() - padding * 2;
        int height = getHeight() - padding * 2;
        
        if (dados.valores.length == 0) {
            g2d.setColor(Color.GRAY);
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
            String msg = "Sem dados disponíveis";
            FontMetrics fm = g2d.getFontMetrics();
            g2d.drawString(msg, getWidth()/2 - fm.stringWidth(msg)/2, getHeight()/2);
            g2d.dispose();
            return;
        }
        
        // Linhas de grade
        g2d.setColor(new Color(240, 240, 240));
        for (int i = 0; i <= 4; i++) {
            int y = padding + (height * i / 4);
            g2d.drawLine(padding, y, padding + width, y);
        }
        
        // Labels do eixo Y
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        for (int i = 0; i <= 4; i++) {
            double value = dados.maxValor * (4 - i) / 4;
            int y = padding + (height * i / 4);
            g2d.drawString(String.format("%.0f", value), 10, y + 5);
        }
        
        // Desenhar barras
        for (int i = 0; i < dados.valores.length; i++) {
            Rectangle r = getBarraBounds(i);
            if (r != null) {
                Color barColor = cores[i % cores.length];
                
                if (i == hoveredIndex) {
                    g2d.setColor(barColor.brighter());
                } else {
                    g2d.setColor(barColor);
                }
                
                g2d.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10);
                
                // Tooltip ao passar o mouse
                if (i == hoveredIndex) {
                    String tooltip = dados.labels[i] + ": " + String.format("%.0f", dados.valores[i]);
                    g2d.setColor(new Color(50, 50, 50, 230));
                    FontMetrics fm = g2d.getFontMetrics();
                    int txtW = fm.stringWidth(tooltip);
                    g2d.fillRoundRect(r.x + r.width/2 - txtW/2 - 8, r.y - 35, txtW + 16, 25, 5, 5);
                    g2d.setColor(Color.WHITE);
                    g2d.drawString(tooltip, r.x + r.width/2 - txtW/2, r.y - 17);
                }
                
                // Label do eixo X
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                String label = dados.labels[i];
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(label, r.x + r.width/2 - fm.stringWidth(label)/2, padding + height + 20);
            }
        }
        
        g2d.dispose();
    }
}