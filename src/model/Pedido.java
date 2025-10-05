/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Pedido {
    private int id;
    private int livroId;
    private int usuarioId;
    private String codigo;
    private String centro;
    private String categoria;
    private String descricao;
    private int quantidadeExistente;
    private int quantidadeMin;
    private int quantidadeMax;
    private int quantidadePedido;
    private String status;
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getLivroId() { return livroId; }
    public void setLivroId(int livroId) { this.livroId = livroId; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getCentro() { return centro; }
    public void setCentro(String centro) { this.centro = centro; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public int getQuantidadeExistente() { return quantidadeExistente; }
    public void setQuantidadeExistente(int quantidadeExistente) { this.quantidadeExistente = quantidadeExistente; }

    public int getQuantidadeMin() { return quantidadeMin; }
    public void setQuantidadeMin(int quantidadeMin) { this.quantidadeMin = quantidadeMin; }

    public int getQuantidadeMax() { return quantidadeMax; }
    public void setQuantidadeMax(int quantidadeMax) { this.quantidadeMax = quantidadeMax; }

    public int getQuantidadePedido() { return quantidadePedido; }
    public void setQuantidadePedido(int quantidadePedido) { this.quantidadePedido = quantidadePedido; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
