// IRead.java
package tn.esprit.autoloc.service;

import java.util.List;

public interface IRead<T, K> {
    T findById(K id);
    List<T> findAll();
}