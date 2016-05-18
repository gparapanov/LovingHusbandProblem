/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lovinghusbandpuzzle;

import cm3038.search.Action;
import cm3038.search.ActionStatePair;
import cm3038.search.Node;
import cm3038.search.Path;
import cm3038.search.State;
import cm3038.search.informed.BestFirstSearchProblem;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Georgi
 */
public class LovingHusbandProblem extends BestFirstSearchProblem {

    public static int raftSize;
    /**
     * 
     * @param start initial state
     * @param goal goal state
     * @param raftSize 
     */
    public LovingHusbandProblem(State start, State goal, int raftSize) {
        super(start, goal);
        if (raftSize < 2) {
            System.out.println("Invalid raft size!");
            System.exit(0);
        }
        this.raftSize = raftSize;
    }

    @Override
    public double evaluation(Node node) {
        return node.getCost() + heuristic(node.state);//A*
    }
    /**
     * The method goals through all people and finds their position on the current
     * and goal state. If they don't match then the people need to be moved, therefore
     * their cost is added to result.
     * @param state
     * @return 
     */
    public double heuristic(State state) {
        LoHusbState thisState = (LoHusbState) state;
        LoHusbState goalState = (LoHusbState) this.goalState;
        double result = 0;
        for (char c : LovingHusbandPuzzle.costs.keySet()) {//use this map, because it contains all people
            boolean inNorth = false, inNorthG = false;
            for (int i = 0; i < thisState.couplesN.length; i++) {
                if (thisState.couplesN[i] == c) {//find side in this state
                    inNorth = true;
                }
            }
            for (int i = 0; i < goalState.couplesN.length; i++) {
                if (goalState.couplesN[i] == c) {//find side in the goal state
                    inNorthG = true;
                }
            }
            //if sides are different, then it has to be moved
            if (inNorth != inNorthG) {
                result += LovingHusbandPuzzle.costs.get(c);
            }
        }

        return result;
    }

    @Override
    public boolean isGoal(State state) {
        LoHusbState st = (LoHusbState) state;
        return st.equals(this.goalState);
    }

}
