/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

import exceptions.DAOException;
import java.util.List;

/**
 *
 * @author eduardo
 */

public interface DAO<T> {
    T buscar(String login) throws DAOException;
    List<T> buscarTodos() throws DAOException;
    void inserir(T t) throws DAOException; 
    void atualizar(T t) throws DAOException;
    void remover(T t) throws DAOException;
}
