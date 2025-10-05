/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package telas.graficos;

public class DadosGrafico {
    public String[] labels;
    public double[] valores;
    public double maxValor;
    
    public DadosGrafico(String[] labels, double[] valores, double maxValor) {
        this.labels = labels;
        this.valores = valores;
        this.maxValor = maxValor;
    }
}