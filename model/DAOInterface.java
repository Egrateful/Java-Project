/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.resort.model;

import java.util.List;
import uuu.resort.domain.ResortException;

/**
 *
 * @author Administrator
 */
public interface DAOInterface <K, T> {
    void insert(T data) throws ResortException;
    void update(T data) throws ResortException;
    void delete(T data) throws ResortException;
    T get(K key)throws ResortException;
    List<T> getAll()throws ResortException;
}
