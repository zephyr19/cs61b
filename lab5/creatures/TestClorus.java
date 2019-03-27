package creatures;

import huglife.*;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.*;

public class TestClorus {
    @Test
    public void testBasics() {
        Clorus clorus = new Clorus(2);
        assertEquals(2, clorus.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), clorus.color());
        clorus.move();
        assertEquals(1.97, clorus.energy(), 0.01);
        clorus.move();
        assertEquals(1.94, clorus.energy(), 0.01);
        clorus.stay();
        assertEquals(1.93, clorus.energy(), 0.01);
        clorus.stay();
        assertEquals(1.92, clorus.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Clorus exceptedC = new Clorus(0.6);
        Clorus clorus = new Clorus(1.2);
        Clorus actualP = clorus.replicate();
        assertEquals(exceptedC.energy(), actualP.energy(), 0.01);
        assertEquals(actualP.energy(), clorus.energy(), 0.01);
        assertNotEquals(clorus, actualP);
    }

    @Test
    public void testChose() {

        // No empty adjacent spaces; stay.
        Clorus p = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);


        // Energy >= 1; replicate towards an empty space.
        p = new Clorus(1.2);
        HashMap<Direction, Occupant> allEmpty = new HashMap<Direction, Occupant>();
        allEmpty.put(Direction.TOP, new Empty());
        allEmpty.put(Direction.BOTTOM, new Empty());
        allEmpty.put(Direction.LEFT, new Empty());
        allEmpty.put(Direction.RIGHT, new Empty());

        actual = p.chooseAction(allEmpty);
        Action unexpected = new Action(Action.ActionType.STAY);

        assertNotEquals(unexpected, actual);


        // attack.
        p = new Clorus(1.2);

        HashMap<Direction, Occupant> topPlip = new HashMap<Direction, Occupant>();
        topPlip.put(Direction.TOP, new Impassible());
        topPlip.put(Direction.BOTTOM, new Empty());
        topPlip.put(Direction.LEFT, new Empty());
        topPlip.put(Direction.RIGHT, new Empty());

        actual = p.chooseAction(topPlip);
        expected = new Action(Action.ActionType.ATTACK, Direction.TOP);

        assertEquals(expected, actual);
    }
}
