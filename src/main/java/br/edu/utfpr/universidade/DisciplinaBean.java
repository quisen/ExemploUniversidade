package br.edu.utfpr.universidade;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
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
        disciplinas = EManager.getInstance().createNamedQuery("Disciplina.findAll").getResultList();
    }

    public void modificaDisciplina() {
        EManager.getInstance().getTransaction().begin();
        EManager.getInstance().merge(this.disciplinaSelecionada);
        EManager.getInstance().getTransaction().commit();
        atualizaListaDisciplinas();
    }

    public void deletaDisciplina() {
        List<Matricula> m = EManager.getInstance().createNamedQuery("Matricula.findByDisciplina").setParameter("idDisciplina", this.disciplinaSelecionada.getId()).getResultList();
        EManager.getInstance().getTransaction().begin();
        for (int i = 0; i < m.size(); i++) {
            EManager.getInstance().remove(m.get(i));
        }
        EManager.getInstance().remove(this.disciplinaSelecionada);
        EManager.getInstance().getTransaction().commit();
        atualizaListaDisciplinas();
    }

    public void enviaDisciplina(Disciplina a) {
        this.disciplinaSelecionada = a;
        List<Matricula> m = EManager.getInstance().createNamedQuery("Matricula.findByDisciplina").setParameter("idDisciplina", this.disciplinaSelecionada.getId()).getResultList();
        if (m.size() > 0) {
            this.msgConfirmacao = "Tem certeza? A remoção da disciplina acarretará na exclusão de todas as respectivas matrículas.";
            this.larguraPopupConfirma = 400;
        } else {
            this.msgConfirmacao = "Tem certeza?";
            this.larguraPopupConfirma = 200;
        }
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
