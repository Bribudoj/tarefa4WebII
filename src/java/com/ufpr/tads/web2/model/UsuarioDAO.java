/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import exceptions.DAOException;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;


/**
 *
 * @author eduardo
 */
public class UsuarioDAO implements DAO<Usuario> {
    
    private static final String QUERY_INSERIR = "INSERT INTO tb_usuario(login_usuario, senha_usuario, nome_usuario) VALUES (?, ?, ?)";
    private static final String QUERY_BUSCAR_TODOS = "SELECT login_usuario, senha_usuario, nome_usuario FROM tb_usuario";
    private static final String QUERY_BUSCAR = "SELECT login_usuario, senha_usuario, nome_usuario FROM tb_usuario WHERE login_usuario = ?";
    
    private Connection con= null;
    
    public UsuarioDAO(Connection con) throws DAOException{
        if(con== null) {
            throw new DAOException("ConexãonulaaocriarPessoaDAO.");
        }
        this.con= con;
    }
    
    @Override
    public Usuario buscar(String login) throws DAOException {
        Usuario usuario = new Usuario();
        try(PreparedStatement st = con.prepareStatement(QUERY_BUSCAR)){
            st.setString(1, login);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()) {
                
                usuario.setLogin(rs.getString("login_usuario"));
                usuario.setSenha(rs.getString("senha_usuario"));
                usuario.setNome(rs.getString("nome_usuario"));
                
            }
        }
        catch(SQLException e){
            throw new DAOException("Erro buscando usuario: " + QUERY_BUSCAR, e);
                }
        return usuario;
    }

    @Override
    public List<Usuario> buscarTodos() throws DAOException {
        List<Usuario> usuarios = new ArrayList<>();
        try(PreparedStatement st = con.prepareStatement(QUERY_BUSCAR_TODOS);
            ResultSet rs = st.executeQuery()){
            while(rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setLogin(rs.getString("login_usuario"));
                usuario.setSenha(rs.getString("senha_usuario"));
                usuario.setNome(rs.getString("nome_usuario"));
                usuarios.add(usuario);
            }
        }
        catch(SQLException e){
            throw new DAOException("Erro buscando todos os usuários: " + QUERY_BUSCAR_TODOS, e);
                }
        return usuarios;
    }

    @Override
    public void inserir(Usuario t) throws DAOException {
        try(PreparedStatement st = con.prepareStatement(QUERY_INSERIR)) {
            st.setString(1, t.getLogin());
            st.setString(2, t.getSenha());
            st.setString(3, t.getNome());
            st.executeUpdate();
        }
        catch(SQLException e) {
            throw new DAOException("Erro inserindo usuario: "+ QUERY_INSERIR + "/ "+ t.toString(), e);
        }
    }

    @Override
    public void atualizar(Usuario t) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void remover(Usuario t) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
