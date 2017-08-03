package br.edu.utfpr.universidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class MatriculaBean implements Serializable {

    private List<Aluno> alunos;
    private Aluno alunoSelecionado = new Aluno();
    private Disciplina disciplinaSelecionada = new Disciplina();
    private List<Disciplina> disciplinas;
    private List<Matricula> matriculas;
    private Matricula matriculaSelecionada = new Matricula();

    @PostConstruct
    public void init() {
        atualizaTodos();
    }

    public void novaMatricula() {
        Matricula matricula = new Matricula();
        matricula.setIdAluno(alunoSelecionado);
        matricula.setIdDisciplina(disciplinaSelecionada);
        EManager.getInstance().getTransaction().begin();
        EManager.getInstance().persist(matricula);
        EManager.getInstance().getTransaction().commit();
        this.alunoSelecionado = new Aluno();
        this.disciplinaSelecionada = new Disciplina();
        atualizaTodos();
    }

    public void atualizaTodos() {
        atualizaListaAlunos();
        atualizaListaDisciplinas();
        atualizaListaMatriculas();
    }

    public void enviaMatricula(Matricula a) {
        this.matriculaSelecionada = a;
    }

    public void deletaMatricula() {
        Matricula matricula = new Matricula();
        matricula.setId(matriculaSelecionada.getId());
        EManager.getInstance().getTransaction().begin();
        EManager.getInstance().remove(
                EManager.getInstance().find(
                        Matricula.class, matricula.getId()
                )
        );
        EManager.getInstance().getTransaction().commit();
        atualizaTodos();
    }

    public void modificaMatricula() {
        Matricula matricula = new Matricula();
        matricula.setId(matriculaSelecionada.getId());
        matricula.setIdAluno(matriculaSelecionada.getIdAluno());
        matricula.setIdDisciplina(matriculaSelecionada.getIdDisciplina());
        EManager.getInstance().getTransaction().begin();
        EManager.getInstance().merge(matricula);
        EManager.getInstance().getTransaction().commit();
        atualizaTodos();
    }

    public void atualizaListaAlunos() {
        alunos = EManager.getInstance().createNamedQuery("Aluno.findAll").getResultList();
    }

    public void atualizaListaDisciplinas() {
        disciplinas = EManager.getInstance().createNamedQuery("Disciplina.findAll").getResultList();
    }

    public void atualizaListaMatriculas() {
        matriculas = EManager.getInstance().createNamedQuery("Matricula.findAll").getResultList();
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public Matricula getMatriculaSelecionada() {
        return matriculaSelecionada;
    }

    public void setMatriculaSelecionada(Matricula matriculaSelecionada) {
        this.matriculaSelecionada = matriculaSelecionada;
    }

    public Aluno getAlunoSelecionado() {
        return alunoSelecionado;
    }

    public void setAlunoSelecionado(Aluno alunoSelecionado) {
        this.alunoSelecionado = alunoSelecionado;
    }

    public Disciplina getDisciplinaSelecionada() {
        return disciplinaSelecionada;
    }

    public void setDisciplinaSelecionada(Disciplina disciplinaSelecionada) {
        this.disciplinaSelecionada = disciplinaSelecionada;
    }

}
