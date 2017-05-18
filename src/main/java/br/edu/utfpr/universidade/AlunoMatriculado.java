
package br.edu.utfpr.universidade;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cnmoro
 */
@Entity
@Table(name = "aluno_matriculado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AlunoMatriculado.findAll", query = "SELECT a FROM AlunoMatriculado a")
    , @NamedQuery(name = "AlunoMatriculado.findById", query = "SELECT a FROM AlunoMatriculado a WHERE a.id = :id")})
public class AlunoMatriculado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_aluno", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Aluno idAluno;
    @JoinColumn(name = "id_disciplina", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Disciplina idDisciplina;

    public AlunoMatriculado() {
    }

    public AlunoMatriculado(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Aluno getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Aluno idAluno) {
        this.idAluno = idAluno;
    }

    public Disciplina getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Disciplina idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlunoMatriculado)) {
            return false;
        }
        AlunoMatriculado other = (AlunoMatriculado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.edu.utfpr.universidade.AlunoMatriculado[ id=" + id + " ]";
    }
    
}
