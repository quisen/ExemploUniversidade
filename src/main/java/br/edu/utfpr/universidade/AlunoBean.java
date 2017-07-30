package br.edu.utfpr.universidade;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
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
        alunos = EManager.getInstance().getAlunoAccessor().getAlunos();
    }

    public void modificaAluno() {
        EManager.getInstance().getAlunoAccessor().modificaAluno(aluno);
        atualizaListaAlunos();
    }

    public void deletaAluno() {
        EManager.getInstance().getAlunoAccessor().deletaAluno(this.alunoSelecionado);
        atualizaListaAlunos();
    }

    public void enviaAluno(Aluno a) {
        this.alunoSelecionado = a;
        List<Matricula> m = EManager.getInstance().getAlunoAccessor().getMatriculas(a);
        if (m.size() > 0) {
            this.msgConfirmacao = "Tem certeza? A remoção do(a) aluno(a) acarretará na exclusão de todas as respectivas matrículas.";
            this.larguraPopupConfirma = 400;
        } else {
            this.msgConfirmacao = "Tem certeza?";
            this.larguraPopupConfirma = 200;
        }
    }

    public void novoCadastro() {
        EManager.getInstance().getAlunoAccessor().insereAluno(aluno);
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
