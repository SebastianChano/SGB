/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Conexao.ConexaoDB;
import java.sql.*;
import model.Livro;
import model.Pedido;

public class PedidoDAO {

    // Crear nuevo pedido
    public boolean criarPedido(Pedido pedido) {
        String sql = "INSERT INTO pedidos (livro_id, usuario_id, codigo, centro, categoria, descricao, " +
                    "quantidade_existente, quantidade_min, quantidade_max, quantidade_pedido, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, pedido.getLivroId());
            stmt.setInt(2, pedido.getUsuarioId());
            stmt.setString(3, pedido.getCodigo());
            stmt.setString(4, pedido.getCentro());
            stmt.setString(5, pedido.getCategoria());
            stmt.setString(6, pedido.getDescricao());
            stmt.setInt(7, pedido.getQuantidadeExistente());
            stmt.setInt(8, pedido.getQuantidadeMin());
            stmt.setInt(9, pedido.getQuantidadeMax());
            stmt.setInt(10, pedido.getQuantidadePedido());
            stmt.setString(11, pedido.getStatus());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao criar pedido: " + e.getMessage());
            return false;
        }
    }

    public Livro buscarLivroParaPedido(String codigo) {
        LivroDAO livroDAO = new LivroDAO();
        return livroDAO.buscarLivroPorCodigo(codigo);
    }
    
    public int obterIdUsuario(String cpfOuEmail) {
    String sql = "SELECT id FROM usuarios WHERE (cpf = ? OR email = ?) AND ativo = TRUE";
    
    try (Connection conn = ConexaoDB.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, cpfOuEmail);
        stmt.setString(2, cpfOuEmail);
        
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        }
        
    } catch (SQLException e) {
        System.err.println("Erro ao obter ID do usuário: " + e.getMessage());
    }
    
    return -1; 
}  
}