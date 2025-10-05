/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package telas.graficos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GraficoLinha extends JPanel {
    private DadosGrafico dados;
    private Color cor;
    private int hoveredIndex = -1;
    
    public GraficoLinha(DadosGrafico dados, Color cor) {
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
                    Point p = getPonto(i);
                    if (p != null && Math.abs(e.getX() - p.x) < 15 && Math.abs(e.getY() - p.y) < 15) {
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
    
    private Point getPonto(int index) {
        int padding = 60;
        int width = getWidth() - padding * 2;
        int height = getHeight() - padding * 2;
        
        if (width <= 0 || height <= 0 || dados.valores.length == 0) return null;
        
        int stepX = dados.valores.length > 1 ? width / (dados.valores.length - 1) : width / 2;
        int x = padding + index * stepX;
        int y = padding + height - (int) (height * dados.valores[index] / dados.maxValor);
        
        return new Point(x, y);
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
        
        // Área preenchida sob a linha
        Polygon area = new Polygon();
        area.addPoint(padding, padding + height);
        
        for (int i = 0; i < dados.valores.length; i++) {
            Point p = getPonto(i);
            if (p != null) area.addPoint(p.x, p.y);
        }
        
        area.addPoint(padding + width, padding + height);
        
        g2d.setColor(new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), 50));
        g2d.fill(area);
        
        // Linha principal
        g2d.setColor(cor);
        g2d.setStroke(new BasicStroke(3));
        for (int i = 0; i < dados.valores.length - 1; i++) {
            Point p1 = getPonto(i);
            Point p2 = getPonto(i + 1);
            if (p1 != null && p2 != null) {
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
        
        // Pontos e labels
        for (int i = 0; i < dados.valores.length; i++) {
            Point p = getPonto(i);
            if (p != null) {
                if (i == hoveredIndex) {
                    g2d.setColor(cor.darker());
                    g2d.fillOval(p.x - 8, p.y - 8, 16, 16);
                    
                    // Tooltip
                    String tooltip = dados.labels[i] + ": R$ " + String.format("%.0f", dados.valores[i]);
                    g2d.setColor(new Color(50, 50, 50, 230));
                    FontMetrics fm = g2d.getFontMetrics();
                    int txtW = fm.stringWidth(tooltip);
                    g2d.fillRoundRect(p.x - txtW/2 - 8, p.y - 35, txtW + 16, 25, 5, 5);
                    g2d.setColor(Color.WHITE);
                    g2d.drawString(tooltip, p.x - txtW/2, p.y - 17);
                } else {
                    g2d.setColor(Color.WHITE);
                    g2d.fillOval(p.x - 5, p.y - 5, 10, 10);
                    g2d.setColor(cor);
                    g2d.fillOval(p.x - 4, p.y - 4, 8, 8);
                }
                
                // Label do eixo X
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                String label = dados.labels[i];
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(label, p.x - fm.stringWidth(label)/2, padding + height + 20);
            }
        }
        
        g2d.dispose();
    }
}