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
    private Aluno alunoSelecionado = new Aluno();
    private String msgConfirmacao = "Tem certeza?";
    private int larguraPopupConfirma = 200;

    @PostConstruct
    public void init() {
        atualizaListaAlunos();
    }

    public void atualizaListaAlunos() {
        alunos = EManager.getInstance().createNamedQuery("Aluno.findAll").getResultList();
    }

    public void modificaAluno() {
        EManager.getInstance().getTransaction().begin();
        EManager.getInstance().merge(this.alunoSelecionado);
        EManager.getInstance().getTransaction().commit();
        atualizaListaAlunos();
    }

    public void deletaAluno() {
        List<Matricula> m = EManager.getInstance().createNamedQuery("Matricula.findByAluno").setParameter("idAluno", this.alunoSelecionado.getId()).getResultList();
        EManager.getInstance().getTransaction().begin();
        for (int i = 0; i < m.size(); i++) {
            EManager.getInstance().remove(m.get(i));
        }
        EManager.getInstance().remove(this.alunoSelecionado);
        EManager.getInstance().getTransaction().commit();
        atualizaListaAlunos();
    }

    public void enviaAluno(Aluno a) {
        this.alunoSelecionado = a;
        List<Matricula> m = EManager.getInstance().createNamedQuery("Matricula.findByAluno").setParameter("idAluno", this.alunoSelecionado.getId()).getResultList();
        if (m.size() > 0) {
            this.msgConfirmacao = "Tem certeza? A remoção do(a) aluno(a) acarretará na exclusão de todas as respectivas matrículas.";
            this.larguraPopupConfirma = 400;
        } else {
            this.msgConfirmacao = "Tem certeza?";
            this.larguraPopupConfirma = 200;
        }
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

    public Aluno getAlunoSelecionado() {
        return alunoSelecionado;
    }

    public void setAlunoSelecionado(Aluno alunoSelecionado) {
        this.alunoSelecionado = alunoSelecionado;
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
