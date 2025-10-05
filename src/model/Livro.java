/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Livro {
    private int id;
    private String codigo;
    private String descricao;
    private String codigoInterno;
    private String categoria;
    private String centro;
    private int quantidade;
    private int quantidadeCd;
    private String tipoMovimento;
    private int quantidadeMin;
    private int quantidadeMax;
    private double preco;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCodigoInterno() { return codigoInterno; }
    public void setCodigoInterno(String codigoInterno) { this.codigoInterno = codigoInterno; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getCentro() { return centro; }
    public void setCentro(String centro) { this.centro = centro; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public int getQuantidadeCd() { return quantidadeCd; }
    public void setQuantidadeCd(int quantidadeCd) { this.quantidadeCd = quantidadeCd; }

    public String getTipoMovimento() { return tipoMovimento; }
    public void setTipoMovimento(String tipoMovimento) { this.tipoMovimento = tipoMovimento; }

    public int getQuantidadeMin() { return quantidadeMin; }
    public void setQuantidadeMin(int quantidadeMin) { this.quantidadeMin = quantidadeMin; }

    public int getQuantidadeMax() { return quantidadeMax; }
    public void setQuantidadeMax(int quantidadeMax) { this.quantidadeMax = quantidadeMax; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
}