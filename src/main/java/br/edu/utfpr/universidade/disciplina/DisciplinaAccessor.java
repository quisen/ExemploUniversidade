package br.edu.utfpr.universidade.disciplina;

import br.edu.utfpr.universidade.disciplina.Disciplina;
import br.edu.utfpr.universidade.matricula.Matricula;
import java.util.List;
import javax.persistence.EntityManager;

public class DisciplinaAccessor {

    private final EntityManager manager;
    private final Object operationLock;

    public DisciplinaAccessor(EntityManager manager, Object operationLock) {
        this.manager = manager;
        this.operationLock = operationLock;
    }

    public List<Disciplina> getDisciplinas() {
        synchronized (this.operationLock) {
            return this.manager.createNamedQuery("Disciplina.findAll").getResultList();
        }
    }

    public void modificaDisciplina(Disciplina disciplina) {
        synchronized (this.operationLock) {
            this.manager.getTransaction().begin();
            this.manager.merge(disciplina);
            this.manager.getTransaction().commit();
        }
    }

    public void deletaDisciplina(Disciplina disciplina) {
        synchronized (this.operationLock) {
            List<Matricula> m = this.getMatriculas(disciplina);
            this.manager.getTransaction().begin();
            for (int i = 0; i < m.size(); i++) {
                this.manager.remove(m.get(i));
            }
            this.manager.remove(disciplina);
            this.manager.getTransaction().commit();
        }
    }

    /**
     * Retorna a matricula associada com a disciplina selecionada
     *
     * @param disciplina
     *
     */
    public List<Matricula> getMatriculas(Disciplina disciplina) {
        synchronized (this.operationLock) {
            return this.manager.createNamedQuery("Matricula.findByDisciplina").setParameter("idDisciplina", disciplina.getId())
                    .getResultList();
        }
    }
    
    public void insereDisciplina(Disciplina disciplina){
        synchronized(this.operationLock){
            this.manager.getTransaction().begin();
            this.manager.persist(disciplina);
            this.manager.getTransaction().commit();
        }
    }

}
