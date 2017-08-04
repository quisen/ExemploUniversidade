package br.edu.utfpr.universidade;

import br.edu.utfpr.universidade.matricula.MatriculaAccessor;
import br.edu.utfpr.universidade.disciplina.DisciplinaAccessor;
import br.edu.utfpr.universidade.aluno.AlunoAccessor;
import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EManager implements Serializable {

    private static final long serialVersionUID = 3675946111000695658L;

    private static final Object singletonLock = new Object();
    private static EManager instance = null;

    private final EntityManager em;

    /* Mutex for ensuring mutual exclusion of access to the database. */
    private final Object operationLock = new Object();
    private final AlunoAccessor alunoAccessor;
    private final DisciplinaAccessor disciplinaAccessor;
    private final MatriculaAccessor matriculaAccessor;

    public static EManager getInstance() {
        if (instance == null) {
            synchronized (singletonLock) {
                if (instance == null) {
                    instance = new EManager();
                }
            }
        }
        return instance;
    }

    private EManager() {
        this.em = Persistence.createEntityManagerFactory("UniversidadePU").createEntityManager();
        this.alunoAccessor = new AlunoAccessor(this.em, this.operationLock);
        this.disciplinaAccessor = new DisciplinaAccessor(this.em, this.operationLock);
        this.matriculaAccessor = new MatriculaAccessor(this.em, this.operationLock);
    }

    public AlunoAccessor getAlunoAccessor() {
        return this.alunoAccessor;
    }

    public DisciplinaAccessor getDisciplinaAccessor() {
        return disciplinaAccessor;
    }

    public MatriculaAccessor getMatriculaAccessor() {
        return matriculaAccessor;
    }
    

}
