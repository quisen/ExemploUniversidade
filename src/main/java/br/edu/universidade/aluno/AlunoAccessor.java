package br.edu.universidade.aluno;

import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import br.edu.universidade.aluno.Aluno;
import br.edu.universidade.matricula.Matricula;

public class AlunoAccessor {

    private final EntityManager manager;
    private final Object operationLock;

    public AlunoAccessor(EntityManager manager, Object operationLock) {
        this.manager = manager;
        this.operationLock = operationLock;
    }

    public List<Aluno> getAlunos() {
        synchronized (this.operationLock) {
            return this.manager.createNamedQuery("Aluno.findAll").getResultList();
        }
    }

    /**
     * Retorna a matricula associada com o aluno selecionado
     *
     * @param aluno
     *
     */
    public List<Matricula> getMatriculas(Aluno aluno) {
        synchronized (this.operationLock) {
            return this.manager.createNamedQuery("Matricula.findByAluno").setParameter("idAluno", aluno.getId())
                    .getResultList();
        }
    }

    public void insereAluno(Aluno aluno) {
        synchronized (this.operationLock) {
            aluno.setMatricula(1000000 + new Random().nextInt(9999999 - 1000000 + 1));
            this.manager.getTransaction().begin();
            this.manager.persist(aluno);
            this.manager.getTransaction().commit();
        }
    }

    public void modificaAluno(Aluno aluno) {
        synchronized (this.operationLock) {
            this.manager.getTransaction().begin();
            this.manager.merge(aluno);
            this.manager.getTransaction().commit();
        }
    }

    public void deletaAluno(Aluno aluno) {
        synchronized (this.operationLock) {
            List<Matricula> m = this.getMatriculas(aluno);
            this.manager.getTransaction().begin();
            for (int i = 0; i < m.size(); i++) {
                this.manager.remove(m.get(i));
            }
            this.manager.remove(aluno);
            this.manager.getTransaction().commit();
        }
    }

}
