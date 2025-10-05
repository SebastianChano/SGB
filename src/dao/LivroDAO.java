/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Conexao.ConexaoDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Livro;

public class LivroDAO {

    // Buscar libros por diferentes criterios
    public List<Livro> buscarLivros(String codigo, String centro, String categoria, String descricao, String tipoMovimento) {
        List<Livro> livros = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM livros WHERE 1=1");
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            
            if (codigo != null && !codigo.isEmpty()) {
                sql.append(" AND codigo LIKE ?");
            }
            if (centro != null && !centro.isEmpty()) {
                sql.append(" AND centro = ?");
            }
            if (categoria != null && !categoria.isEmpty()) {
                sql.append(" AND categoria LIKE ?");
            }
            if (descricao != null && !descricao.isEmpty()) {
                sql.append(" AND descricao LIKE ?");
            }
            if (tipoMovimento != null && !tipoMovimento.isEmpty()) {
                sql.append(" AND tipo_movimento = ?");
            }
            
            stmt.close(); // Cerrar el statement anterior
            
            try (PreparedStatement newStmt = conn.prepareStatement(sql.toString())) {
                paramIndex = 1;
                
                if (codigo != null && !codigo.isEmpty()) {
                    newStmt.setString(paramIndex++, "%" + codigo + "%");
                }
                if (centro != null && !centro.isEmpty()) {
                    newStmt.setString(paramIndex++, centro);
                }
                if (categoria != null && !categoria.isEmpty()) {
                    newStmt.setString(paramIndex++, "%" + categoria + "%");
                }
                if (descricao != null && !descricao.isEmpty()) {
                    newStmt.setString(paramIndex++, "%" + descricao + "%");
                }
                if (tipoMovimento != null && !tipoMovimento.isEmpty()) {
                    newStmt.setString(paramIndex++, tipoMovimento);
                }
                
                ResultSet rs = newStmt.executeQuery();
                
                while (rs.next()) {
                    Livro livro = new Livro();
                    livro.setId(rs.getInt("id"));
                    livro.setCodigo(rs.getString("codigo"));
                    livro.setDescricao(rs.getString("descricao"));
                    livro.setCodigoInterno(rs.getString("codigo_interno"));
                    livro.setCategoria(rs.getString("categoria"));
                    livro.setCentro(rs.getString("centro"));
                    livro.setQuantidade(rs.getInt("quantidade"));
                    livro.setQuantidadeCd(rs.getInt("quantidade_cd"));
                    livro.setTipoMovimento(rs.getString("tipo_movimento"));
                    livro.setQuantidadeMin(rs.getInt("quantidade_min"));
                    livro.setQuantidadeMax(rs.getInt("quantidade_max"));
                    livro.setPreco(rs.getDouble("preco"));
                    
                    livros.add(livro);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar livros: " + e.getMessage());
        }
        
        return livros;
    }

    // Buscar libro por código
    public Livro buscarLivroPorCodigo(String codigo) {
        String sql = "SELECT * FROM livros WHERE codigo = ?";
        
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Livro livro = new Livro();
                livro.setId(rs.getInt("id"));
                livro.setCodigo(rs.getString("codigo"));
                livro.setDescricao(rs.getString("descricao"));
                livro.setCodigoInterno(rs.getString("codigo_interno"));
                livro.setCategoria(rs.getString("categoria"));
                livro.setCentro(rs.getString("centro"));
                livro.setQuantidade(rs.getInt("quantidade"));
                livro.setQuantidadeCd(rs.getInt("quantidade_cd"));
                livro.setTipoMovimento(rs.getString("tipo_movimento"));
                livro.setQuantidadeMin(rs.getInt("quantidade_min"));
                livro.setQuantidadeMax(rs.getInt("quantidade_max"));
                livro.setPreco(rs.getDouble("preco"));
                
                return livro;
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar livro por código: " + e.getMessage());
        }
        
        return null;
    }

    // Obtener todos los libros
    public List<Livro> obterTodosLivros() {
        return buscarLivros(null, null, null, null, null);
    }
}