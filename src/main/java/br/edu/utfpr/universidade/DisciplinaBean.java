package br.edu.utfpr.universidade;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class DisciplinaBean implements Serializable {

    private List<Disciplina> disciplinas;
    private Disciplina disciplina = new Disciplina();
    private Disciplina disciplinaSelecionada = new Disciplina();

    @PostConstruct
    public void init() {
        atualizaListaDisciplinas();
    }

    public void atualizaListaDisciplinas() {
        disciplinas = EManager.getInstance().createNamedQuery("Disciplina.findAll").getResultList();
    }

    public void modificaDisciplina() {
        EManager.getInstance().getTransaction().begin();
        EManager.getInstance().merge(this.disciplinaSelecionada);
        EManager.getInstance().getTransaction().commit();
        atualizaListaDisciplinas();
    }
    
    public void deletaDisciplina() {
        EManager.getInstance().getTransaction().begin();
        EManager.getInstance().remove(this.disciplinaSelecionada);
        EManager.getInstance().getTransaction().commit();
        atualizaListaDisciplinas();
    }

    public void enviaDisciplina(Disciplina a) {
        this.disciplinaSelecionada = a;
    }

    public void novoCadastro() {
        EManager.getInstance().getTransaction().begin();
        EManager.getInstance().persist(this.disciplina);
        EManager.getInstance().getTransaction().commit();
        this.disciplina = new Disciplina();
        atualizaListaDisciplinas();
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Disciplina getDisciplinaSelecionada() {
        return disciplinaSelecionada;
    }

    public void setDisciplinaSelecionada(Disciplina disciplinaSelecionada) {
        this.disciplinaSelecionada = disciplinaSelecionada;
    }

    
    
}
