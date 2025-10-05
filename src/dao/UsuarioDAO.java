/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Conexao.ConexaoDB;
import java.sql.*;
import model.Usuario;

public class UsuarioDAO {

    public boolean autenticarUsuario(String cpfOuEmail, String senha) {
        String sql = "SELECT * FROM usuarios WHERE (cpf = ? OR email = ?) AND senha = ? AND ativo = TRUE AND autorizado = TRUE";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpfOuEmail);
            stmt.setString(2, cpfOuEmail);
            stmt.setString(3, senha);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Se encontrou usuário válido → login OK

        } catch (SQLException e) {
            System.err.println("Erro ao autenticar usuário: " + e.getMessage());
            return false;
        }
    }
    
    public boolean registrarUsuario(String nome, String cpf, String email, String senha, String tipo) {
    String sql = "INSERT INTO usuarios (nome, cpf, email, senha, tipo, autorizado) VALUES (?, ?, ?, ?, ?, ?)";
    
    boolean autorizado = tipo.equals("CLIENTE");
    
    try (Connection conn = ConexaoDB.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, nome);
        stmt.setString(2, cpf);
        stmt.setString(3, email);
        stmt.setString(4, senha);
        stmt.setString(5, tipo);
        stmt.setBoolean(6, autorizado);
        
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
        
    } catch (SQLException e) {
        System.err.println("Erro ao registrar usuário: " + e.getMessage());
        return false;
    }
}

public boolean verificarCPFExistente(String cpf) {
    String sql = "SELECT id FROM usuarios WHERE cpf = ?";
    
    try (Connection conn = ConexaoDB.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, cpf);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
        
    } catch (SQLException e) {
        System.err.println("Erro ao verificar CPF: " + e.getMessage());
        return false;
    }
}

public boolean verificarEmailExistente(String email) {
    String sql = "SELECT id FROM usuarios WHERE email = ?";
    
    try (Connection conn = ConexaoDB.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
        
    } catch (SQLException e) {
        System.err.println("Erro ao verificar email: " + e.getMessage());
        return false;
    }
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
    
    return -1; // Retorna -1 si no encuentra
}


public Usuario obterUsuarioPorCredencial(String cpfOuEmail) {
    String sql = "SELECT * FROM usuarios WHERE (cpf = ? OR email = ?) AND ativo = TRUE";
    
    try (Connection conn = ConexaoDB.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, cpfOuEmail);
        stmt.setString(2, cpfOuEmail);
        
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setId(rs.getInt("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setCpf(rs.getString("cpf"));
            usuario.setEmail(rs.getString("email"));
            usuario.setTipo(rs.getString("tipo"));
            usuario.setAutorizado(rs.getBoolean("autorizado"));
            return usuario;
        }
        
    } catch (SQLException e) {
        System.err.println("Erro ao obter usuário: " + e.getMessage());
    }
    
    return null;
}

}
