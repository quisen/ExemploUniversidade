package br.edu.utfpr.universidade.managers;

import java.util.List;

import javax.persistence.EntityManager;

import br.edu.utfpr.universidade.pojos.Matricula;

public class MatriculaAccessor {

    private final EntityManager manager;
    private final Object operationLock;

    public MatriculaAccessor(EntityManager manager, Object operationLock) {
        this.manager = manager;
        this.operationLock = operationLock;
    }

    public List<Matricula> getMatriculas() {
        synchronized (this.operationLock) {
            return this.manager.createNamedQuery("Matricula.findAll").getResultList();
        }
    }

    public void modificaMatricula(Matricula matricula) {
        synchronized (this.operationLock) {
            this.manager.getTransaction().begin();
            this.manager.merge(matricula);
            this.manager.getTransaction().commit();
        }
    }

    public void deletaMatricula(int matriculaId) {
        synchronized (this.operationLock) {
            Matricula matricula = this.manager.find(Matricula.class, matriculaId);
            this.manager.getTransaction().begin();
            this.manager.remove(matricula);
            this.manager.getTransaction().commit();
        }
    }

    public void insereMatricula(Matricula matricula) {
        synchronized (this.operationLock) {
            this.manager.getTransaction().begin();
            this.manager.persist(matricula);
            this.manager.getTransaction().commit();
        }
    }
}
