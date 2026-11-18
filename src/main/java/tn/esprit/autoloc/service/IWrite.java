// IWrite.java
package tn.esprit.autoloc.service;

public interface IWrite<T, K> {
    T create(T entity);
    T update(K id, T entity);
    void deleteById(K id);
}