package br.edu.utfpr.universidade;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EManager implements Serializable {

    private static final long serialVersionUID = 3675946111000695658L;

    /* Mutex for ensuring the "EManager" singleton is created only once. */
    private static final Object emLock = new Object();
    private static EntityManager em = null;

    /* Mutex for ensuring mutual exclusion of access to the database. */
    private static final Object operationLock = new Object();

    private EManager() {
    }

    public static EntityManager getInstance() {
        if (em == null) {
            synchronized (emLock) {
                if (em == null) {
                    em = Persistence.createEntityManagerFactory("UniversidadePU").createEntityManager();
                }
            }
        }
        return em;
    }

    public static List<Aluno> getAlunos() {
        synchronized (operationLock) {
            return getInstance().createNamedQuery("Aluno.findAll").getResultList();
        }
    }

    public static void modificaAluno(Aluno aluno) {
        synchronized (operationLock) {
            getInstance().getTransaction().begin();
            getInstance().merge(aluno);
            getInstance().getTransaction().commit();
        }
    }

    public static void deletaAluno(Aluno aluno) {
        synchronized (operationLock) {
            List<Matricula> m = getMatriculas(aluno);
            getInstance().getTransaction().begin();
            for (int i = 0; i < m.size(); i++) {
                EManager.getInstance().remove(m.get(i));
            }
            EManager.getInstance().remove(aluno);
            EManager.getInstance().getTransaction().commit();
        }
    }

    /**
     * Returns the matricula associated with selected aluno
     *
     * @param aluno
     *
     */
    public static List<Matricula> getMatriculas(Aluno aluno) {
        synchronized (operationLock) {
            return getInstance().createNamedQuery("Matricula.findByAluno").setParameter("idAluno", aluno.getId())
                    .getResultList();
        }
    }

    public static void insereAluno(Aluno aluno) {
        synchronized (operationLock) {
            aluno.setMatricula(1000000 + new Random().nextInt(9999999 - 1000000 + 1));
            EManager.getInstance().getTransaction().begin();
            EManager.getInstance().persist(aluno);
            EManager.getInstance().getTransaction().commit();
            aluno = new Aluno();
        }
    }

}