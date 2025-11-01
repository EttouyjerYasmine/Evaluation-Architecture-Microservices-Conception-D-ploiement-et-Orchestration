/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

/**
 *
 * @author hp
 */
import java.util.List;

public interface Idao<T> {
    void create(T o);
    T getById(int id);
    List<T> getAll();
    void update(T o);
    void delete(T o);
}
