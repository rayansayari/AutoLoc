// IGenericService.java
package tn.esprit.autoloc.service;

public interface IGenericService<T, K>
        extends IRead<T, K>, IWrite<T, K>, ICheck<T> {
}