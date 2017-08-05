package br.edu.utfpr.universidade.disciplina;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class DisciplinaTests {

    @Test
    public void testGetId() {
        Disciplina disciplina = new Disciplina(1);
        assertEquals(1, (int) disciplina.getId());
    }

}
