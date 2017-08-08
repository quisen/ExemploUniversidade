package br.edu.universidade.disciplina;

import br.edu.universidade.EManager;
import br.edu.universidade.matricula.Matricula;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class DisciplinaBean implements Serializable {

    private List<Disciplina> disciplinas;
    private Disciplina disciplina = new Disciplina();
    private Disciplina disciplinaSelecionada = new Disciplina();
    private String msgConfirmacao = "Tem certeza?";
    private int larguraPopupConfirma = 200;

    @PostConstruct
    public void init() {
        atualizaListaDisciplinas();
    }

    public void atualizaListaDisciplinas() {
        disciplinas = EManager.getInstance().getDisciplinaAccessor().getDisciplinas();
    }

    public void novoCadastro() {
        EManager.getInstance().getDisciplinaAccessor().insereDisciplina(disciplina);
        this.disciplina = new Disciplina();
        atualizaListaDisciplinas();
    }

    public void modificaDisciplina() {
        EManager.getInstance().getDisciplinaAccessor().modificaDisciplina(disciplinaSelecionada);
        atualizaListaDisciplinas();
    }

    public void deletaDisciplina() {
        EManager.getInstance().getDisciplinaAccessor().deletaDisciplina(disciplinaSelecionada);
        atualizaListaDisciplinas();
    }

    public void enviaDisciplina(Disciplina d) {
        this.disciplinaSelecionada = d;
        List<Matricula> m = EManager.getInstance().getDisciplinaAccessor().getMatriculas(d);
        if (m.size() > 0) {
            this.msgConfirmacao = "Tem certeza? A remoção da disciplina acarretará na exclusão de todas as respectivas matrículas.";
            this.larguraPopupConfirma = 400;
        } else {
            this.msgConfirmacao = "Tem certeza?";
            this.larguraPopupConfirma = 200;
        }
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

    public String getMsgConfirmacao() {
        return msgConfirmacao;
    }

    public void setMsgConfirmacao(String msgConfirmacao) {
        this.msgConfirmacao = msgConfirmacao;
    }

    public int getLarguraPopupConfirma() {
        return larguraPopupConfirma;
    }

    public void setLarguraPopupConfirma(int larguraPopupConfirma) {
        this.larguraPopupConfirma = larguraPopupConfirma;
    }

}
