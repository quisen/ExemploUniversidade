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
    private List<AlunoMatriculado> matriculas;
    private List<matriculaMin> matriculasMin = new ArrayList<>();
    private AlunoMatriculado matriculaSelecionada = new AlunoMatriculado();
    private matriculaMin matriculaMinSelecionada = new matriculaMin();

    @PostConstruct
    public void init() {
        atualizaTodos();
    }

    public void novaMatricula() {
        AlunoMatriculado matricula = new AlunoMatriculado();
        matricula.setIdAluno(alunoSelecionado);
        matricula.setIdDisciplina(disciplinaSelecionada);
        EManager.getInstance().getTransaction().begin();
        EManager.getInstance().persist(matricula);
        EManager.getInstance().getTransaction().commit();
        this.alunoSelecionado = new Aluno();
        this.disciplinaSelecionada = new Disciplina();
        atualizaTodos();
    }

    public void agrupaDadosPorId() {
        this.matriculasMin = new ArrayList<>();

        for (int i = 0; i < matriculas.size(); i++) {
            matriculaMin mat = new matriculaMin();
            mat.setAluno((Aluno) EManager.getInstance().createNamedQuery("Aluno.findById").setParameter("id", matriculas.get(i).getIdAluno().getId()).getSingleResult());
            mat.setDisciplina((Disciplina) EManager.getInstance().createNamedQuery("Disciplina.findById").setParameter("id", matriculas.get(i).getIdDisciplina().getId()).getSingleResult());

            matriculasMin.add(mat);
        }
    }

    public void atualizaTodos() {
        atualizaListaAlunos();
        atualizaListaDisciplinas();
        atualizaListaMatriculas();
        agrupaDadosPorId();
    }

    public void enviaMatricula(matriculaMin a) {
        this.matriculaMinSelecionada = a;
    }

    public void deletaMatricula() {
        //TODO
    }

    public void modificaMatricula() {
        //TODO
    }

    public void atualizaListaAlunos() {
        alunos = EManager.getInstance().createNamedQuery("Aluno.findAll").getResultList();
    }

    public void atualizaListaDisciplinas() {
        disciplinas = EManager.getInstance().createNamedQuery("Disciplina.findAll").getResultList();
    }

    public void atualizaListaMatriculas() {
        matriculas = EManager.getInstance().createNamedQuery("AlunoMatriculado.findAll").getResultList();
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

    public List<AlunoMatriculado> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<AlunoMatriculado> matriculas) {
        this.matriculas = matriculas;
    }

    public AlunoMatriculado getMatriculaSelecionada() {
        return matriculaSelecionada;
    }

    public void setMatriculaSelecionada(AlunoMatriculado matriculaSelecionada) {
        this.matriculaSelecionada = matriculaSelecionada;
    }

    public List<matriculaMin> getMatriculasMin() {
        return matriculasMin;
    }

    public void setMatriculasMin(List<matriculaMin> matriculasMin) {
        this.matriculasMin = matriculasMin;
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

    public matriculaMin getMatriculaMinSelecionada() {
        return matriculaMinSelecionada;
    }

    public void setMatriculaMinSelecionada(matriculaMin matriculaMinSelecionada) {
        this.matriculaMinSelecionada = matriculaMinSelecionada;
    }
    
    

    public class matriculaMin {

        Aluno aluno;
        Disciplina disciplina;

        public matriculaMin() {
        }

        public Aluno getAluno() {
            return aluno;
        }

        public void setAluno(Aluno aluno) {
            this.aluno = aluno;
        }

        public Disciplina getDisciplina() {
            return disciplina;
        }

        public void setDisciplina(Disciplina disciplina) {
            this.disciplina = disciplina;
        }

    }

}
