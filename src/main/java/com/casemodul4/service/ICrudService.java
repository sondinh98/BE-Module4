package com.casemodul4.service;

import java.util.List;
import java.util.Optional;

public interface ICrudService<E>{
    List<E> getAll();
    E save(E e);

    Optional<E> findById(int id);


    void  delete(int id);

}
