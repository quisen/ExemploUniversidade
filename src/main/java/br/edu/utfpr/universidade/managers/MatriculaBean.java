package br.edu.utfpr.universidade.managers;

import br.edu.utfpr.universidade.pojos.Aluno;
import br.edu.utfpr.universidade.pojos.Disciplina;
import br.edu.utfpr.universidade.pojos.Matricula;

import java.io.Serializable;
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
        EManager.getInstance().getMatriculaAccessor().insereMatricula(matricula);
        this.alunoSelecionado = new Aluno();
        this.disciplinaSelecionada = new Disciplina();
        atualizaTodos();
    }

    public void atualizaTodos() {
        atualizaListaAlunos();
        atualizaListaDisciplinas();
        atualizaListaMatriculas();
    }

    public void enviaMatricula(Matricula m) {
        this.matriculaSelecionada = m;
    }

    public void deletaMatricula() {
        EManager.getInstance().getMatriculaAccessor().deletaMatricula(this.matriculaSelecionada.getId());
        atualizaTodos();
    }

    public void modificaMatricula() {
        Matricula matricula = new Matricula();
        matricula.setId(matriculaSelecionada.getId());
        matricula.setIdAluno(matriculaSelecionada.getIdAluno());
        matricula.setIdDisciplina(matriculaSelecionada.getIdDisciplina());
        EManager.getInstance().getMatriculaAccessor().modificaMatricula(matriculaSelecionada);
        atualizaTodos();
    }

    public void atualizaListaAlunos() {
        alunos = EManager.getInstance().getAlunoAccessor().getAlunos();
    }

    public void atualizaListaDisciplinas() {
        disciplinas = EManager.getInstance().getDisciplinaAccessor().getDisciplinas();
    }

    public void atualizaListaMatriculas() {
        matriculas = EManager.getInstance().getMatriculaAccessor().getMatriculas();
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
