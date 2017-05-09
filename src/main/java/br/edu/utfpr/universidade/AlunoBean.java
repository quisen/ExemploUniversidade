package br.edu.utfpr.universidade;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class AlunoBean implements Serializable {

    private List<Aluno> alunos;
    private Aluno aluno = new Aluno();

    @PostConstruct
    public void init() {
        atualizaListaAlunos();
    }

    public void atualizaListaAlunos() {
        alunos = EManager.getInstance().createNamedQuery("Aluno.findAll").getResultList();
    }

    public void novoCadastro() {
        this.aluno.setMatricula(1000000 + new Random().nextInt(9999999 - 1000000 + 1));
        EManager.getInstance().getTransaction().begin();
        EManager.getInstance().persist(this.aluno);
        EManager.getInstance().getTransaction().commit();
        this.aluno = new Aluno();
        atualizaListaAlunos();
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

}
