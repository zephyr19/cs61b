package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Clorus extends Creature {
    private int r;
    private int g;
    private int b;

    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    @Override
    public void move() {
        energy -= 0.03;
    }

    @Override
    public void attack(Creature plip) {
        energy += plip.energy();
    }

    @Override
    public Clorus replicate() {
        energy /= 2;
        return new Clorus(energy);
    }

    @Override
    public void stay() {
        energy -= 0.01;
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyPosition = new ArrayDeque<>();
        Deque<Direction> plopPosition = new ArrayDeque<>();
        for (Direction key : neighbors.keySet()) {
            Occupant occupant = neighbors.get(key);
            if (occupant.name().equals("empty")) {
                emptyPosition.addFirst(key);
            } else if (!occupant.color().equals(color(34, 0, 231)) &&
                    !occupant.color().equals(color(0, 0, 0))) {
                plopPosition.addFirst(key);
            }
        }
        if (emptyPosition.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }
        if (plopPosition.size() != 0) {
            return new Action(Action.ActionType.ATTACK, plopPosition.getFirst());
        }
        if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, emptyPosition.getFirst());
        }
        return new Action(Action.ActionType.MOVE, emptyPosition.getFirst());
    }

    @Override
    public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r, g, b);
    }

}
