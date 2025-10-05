/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package telas.graficos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GraficoPizza extends JPanel {
    private final DadosGrafico dados;
    private final Color[] cores;
    private int hoveredIndex = -1;
    
    public GraficoPizza(DadosGrafico dados, Color[] cores) {
        this.dados = dados;
        this.cores = cores;
        setPreferredSize(new Dimension(800, 500));
        setOpaque(false);
        
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int oldIndex = hoveredIndex;
                hoveredIndex = getSliceAt(e.getPoint());
                
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
    
    private int getSliceAt(Point p) {
        int diameter = Math.min(getWidth(), getHeight()) - 100;
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        
        double dx = p.x - centerX;
        double dy = p.y - centerY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        if (distance > diameter / 2) return -1;
        
        double angle = Math.toDegrees(Math.atan2(dy, dx));
        if (angle < 0) angle += 360;
        angle = (90 - angle + 360) % 360;
        
        double total = 0;
        for (double v : dados.valores) total += v;
        
        if (total == 0) return -1;
        
        double currentAngle = 0;
        for (int i = 0; i < dados.valores.length; i++) {
            double sliceAngle = (dados.valores[i] / total) * 360;
            if (angle >= currentAngle && angle < currentAngle + sliceAngle) {
                return i;
            }
            currentAngle += sliceAngle;
        }
        
        return -1;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int diameter = Math.min(getWidth(), getHeight()) - 100;
        int x = getWidth() / 2 - diameter / 2;
        int y = getHeight() / 2 - diameter / 2;
        
        if (dados.valores.length == 0) {
            g2d.setColor(Color.GRAY);
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
            String msg = "Sem dados disponíveis";
            FontMetrics fm = g2d.getFontMetrics();
            g2d.drawString(msg, getWidth()/2 - fm.stringWidth(msg)/2, getHeight()/2);
            g2d.dispose();
            return;
        }
        
        double total = 0;
        for (double v : dados.valores) total += v;
        
        if (total == 0) {
            g2d.setColor(Color.GRAY);
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
            String msg = "Sem dados para exibir";
            FontMetrics fm = g2d.getFontMetrics();
            g2d.drawString(msg, getWidth()/2 - fm.stringWidth(msg)/2, getHeight()/2);
            g2d.dispose();
            return;
        }
        
        // Desenhar fatias
        double currentAngle = 90;
        for (int i = 0; i < dados.valores.length; i++) {
            double sliceAngle = (dados.valores[i] / total) * 360;
            
            Color sliceColor = cores[i % cores.length];
            if (i == hoveredIndex) {
                sliceColor = sliceColor.brighter();
            }
            
            g2d.setColor(sliceColor);
            g2d.fillArc(x, y, diameter, diameter, (int) currentAngle, (int) -sliceAngle);
            
            // Borda branca entre fatias
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawArc(x, y, diameter, diameter, (int) currentAngle, (int) -sliceAngle);
            
            // Percentual ao redor da pizza
            double midAngle = currentAngle - sliceAngle / 2;
            double rad = Math.toRadians(midAngle);
            int labelX = (int) (getWidth() / 2 + (diameter / 2 + 40) * Math.cos(rad));
            int labelY = (int) (getHeight() / 2 - (diameter / 2 + 40) * Math.sin(rad));
            
            String percentage = String.format("%.1f%%", (dados.valores[i] / total) * 100);
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 13));
            FontMetrics fm = g2d.getFontMetrics();
            g2d.drawString(percentage, labelX - fm.stringWidth(percentage) / 2, labelY);
            
            // Tooltip ao passar o mouse
            if (i == hoveredIndex) {
                String tooltip = dados.labels[i] + " - " + percentage;
                g2d.setColor(new Color(50, 50, 50, 230));
                int txtW = fm.stringWidth(tooltip);
                g2d.fillRoundRect(getWidth()/2 - txtW/2 - 10, 20, txtW + 20, 30, 5, 5);
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
                g2d.drawString(tooltip, getWidth()/2 - txtW/2, 40);
            }
            
            currentAngle -= sliceAngle;
        }
        
        // Legenda
        int legendY = getHeight() - 80;
        int legendX = 50;
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        for (int i = 0; i < dados.labels.length; i++) {
            g2d.setColor(cores[i % cores.length]);
            g2d.fillRect(legendX, legendY + i * 20, 15, 15);
            g2d.setColor(Color.BLACK);
            g2d.drawString(dados.labels[i], legendX + 20, legendY + i * 20 + 12);
        }
        
        g2d.dispose();
    }
}