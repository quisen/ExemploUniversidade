package br.edu.universidade;

import br.edu.universidade.EManager;
import br.edu.universidade.aluno.Aluno;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Rodrigo
 */
public class EManagerTests {

    @Test
    public void testSingletonEquality() {
        EManager singleton1 = EManager.getInstance();
        EManager singleton2 = EManager.getInstance();
        assertTrue(singleton1 == singleton2);
    }

    @Test
    public void testAddAluno() {
        Aluno aluno = new Aluno(1000, "Rodrigo", 123, "ABC", "555", "456", 8);
        EManager.getInstance().getAlunoAccessor().insereAluno(aluno);
        List<Aluno> alunos = EManager.getInstance().getAlunoAccessor().getAlunos();
        assertTrue(alunos.contains(aluno));
    }

}
