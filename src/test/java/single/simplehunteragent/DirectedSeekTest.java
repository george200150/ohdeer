package single.simplehunteragent;

import org.junit.Test;

import static org.junit.Assert.*;

public class DirectedSeekTest {

    @Test
    public void testmanhattenWithDiagonal() {
        DirectedSeek action = new DirectedSeek();
        // (1,1) -> (8,8) diagonala
        int noTurns = action.manhattenWithDiagonal(1, 1, 8, 8);
        assertEquals("from (1,1) to (8,8) it takes 7 turns!", 7, noTurns);

        // (1,1) -> (1,8) rand
        noTurns = action.manhattenWithDiagonal(1, 1, 1, 8);
        assertEquals("from (1,1) to (1,8) it takes 7 turns!", 7, noTurns);

        // (1,1) -> (8,1) coloana
        noTurns = action.manhattenWithDiagonal(1, 1, 8, 1);
        assertEquals("from (1,1) to (8,1) it takes 7 turns!", 7, noTurns);

        // (1,1) -> (1,1) nimic
        noTurns = action.manhattenWithDiagonal(1, 1, 1, 1);
        assertEquals("from (1,1) to (1,1) it takes 0 turns!", 0, noTurns);

        // freestyle 1 turn
        noTurns = action.manhattenWithDiagonal(2, 2, 1, 1);
        assertEquals("it takes 1 turn!", 1, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 1, 2);
        assertEquals("it takes 1 turn!", 1, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 3, 1);
        assertEquals("it takes 1 turn!", 1, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 3, 2);
        assertEquals("it takes 1 turn!", 1, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 3, 3);
        assertEquals("it takes 1 turn!", 1, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 2, 3);
        assertEquals("it takes 1 turn!", 1, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 2, 1);
        assertEquals("it takes 1 turn!", 1, noTurns);

        // freestyle 2 turns
        noTurns = action.manhattenWithDiagonal(2, 2, 0, 0);
        assertEquals("it takes 2 turns!", 2, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 0, 1);
        assertEquals("it takes 2 turns!", 2, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 0, 2);
        assertEquals("it takes 2 turns!", 2, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 0, 3);
        assertEquals("it takes 2 turns!", 2, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 0, 4);
        assertEquals("it takes 2 turns!", 2, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 1, 0);
        assertEquals("it takes 2 turns!", 2, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 1, 4);
        assertEquals("it takes 2 turns!", 2, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 2, 0);
        assertEquals("it takes 2 turns!", 2, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 2, 4);
        assertEquals("it takes 2 turns!", 2, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 3, 0);
        assertEquals("it takes 2 turns!", 2, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 3, 4);
        assertEquals("it takes 2 turns!", 2, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 4, 1);
        assertEquals("it takes 2 turns!", 2, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 4, 2);
        assertEquals("it takes 2 turns!", 2, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 4, 3);
        assertEquals("it takes 2 turns!", 2, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 4, 4);
        assertEquals("it takes 2 turns!", 2, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 4, 4);
        assertEquals("it takes 2 turns!", 2, noTurns);

        // freestyle 3 turns
        noTurns = action.manhattenWithDiagonal(2, 2, 0, 5);
        assertEquals("it takes 3 turns!", 3, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 1, 5);
        assertEquals("it takes 3 turns!", 3, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 2, 5);
        assertEquals("it takes 3 turns!", 3, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 3, 5);
        assertEquals("it takes 3 turns!", 3, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 4, 5);
        assertEquals("it takes 3 turns!", 3, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 5, 5);
        assertEquals("it takes 3 turns!", 3, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 5, 4);
        assertEquals("it takes 3 turns!", 3, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 5, 3);
        assertEquals("it takes 3 turns!", 3, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 5, 2);
        assertEquals("it takes 3 turns!", 3, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 5, 1);
        assertEquals("it takes 3 turns!", 3, noTurns);

        noTurns = action.manhattenWithDiagonal(2, 2, 5, 0);
        assertEquals("it takes 3 turns!", 3, noTurns);
    }
}