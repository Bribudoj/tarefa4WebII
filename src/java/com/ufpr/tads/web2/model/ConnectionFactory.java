/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import exceptions.DAOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author eduardo
 */
public class ConnectionFactory implements AutoCloseable {
     private static final String DRIVER = "org.postgresql.Driver";
     private static final String URL    = "jdbc:postgresql://localhost:5432/db_tarefas";
     private static final String LOGIN  = "postgres";
     private static final String SENHA  = "postgres";
     private Connection con= null;
     
     public Connection getConnection() throws DAOException{  
         if(con== null) {
            try{
                Class.forName(DRIVER);
                con= DriverManager.getConnection(URL, LOGIN, SENHA);
            }catch(ClassNotFoundException e) {
                throw new DAOException("Driver do banco não encontrado: "+ DRIVER, e);
            }catch(SQLException e) {
                throw new DAOException("ErroconectandoaoBD: "+ URL+ "/"+ LOGIN+ "/"+ SENHA, e);
            }}
             
        return con;
    }
     @Override
     public void close() {
         if(con!=null) {
             try{ 
                 con.close();
                 con= null;
             }catch(Exception e) { 
                System.out.println("Erro fechando a conexão. IGNORADO");
                e.printStackTrace();
             } }
     }
    
}
