/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lovinghusbandpuzzle;

import cm3038.search.Node;
import cm3038.search.Path;
import cm3038.search.SearchProblem;
import java.util.HashMap;

/**
 *
 * @author Georgi
 */
public class LovingHusbandPuzzle {

    /**
     * @param args the command line arguments
     */
    public static HashMap<Character, Double> costs;
    public static HashMap<Character, Character> marriages;

    public static void main(String[] args) {

        char[] couplesN, couplesS, couplesNGoal, couplesSGoal,couplesSBasic,couplesNBasic,couplesNGoalB,couplesSGoalB;
        /*
         The arrays represent husbands and wives and '0' are empty spaces.
         In order the program to work both of them have to have the same size, because
         arrays are fixed size and we need to have some way of indicating if a space is
         free when applying different actions.
         N.B. earch person must be in one of the arrays for a state, have a cost in the 
         costs hashmap and have a connection in the marriages hashmap!!!
         */
        //put these in the problem variable for a solution for the basic problem
        //and remove the some of the costs and marriages
        couplesNBasic = new char[]{'0', '0', '0', '0', '0', '0'};//north side basic problem
        couplesSBasic = new char[]{'A', 'a', 'B', 'b', 'C', 'c'};//south side basic problem
        couplesNGoalB = new char[]{'A', 'a', 'B', 'b', 'C', 'c'};
        couplesSGoalB= new char[]{'0', '0', '0', '0', '0', '0'};
        
        //uncomment for extended problem
//        couplesN = new char[]{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0'};//north side
//        couplesS = new char[]{'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e'};//south side
//        couplesNGoal = new char[]{'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e'};
//        couplesSGoal = new char[]{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0'};

        costs = new HashMap();//a map to store the cost of each person
        costs.put('A', 10.0);
        costs.put('a', 10.0);
        costs.put('B', 5.0);
        costs.put('b', 5.0);
        costs.put('C', 1.0);
        costs.put('c', 1.0);
//        costs.put('D', 2.0); //uncomment for extended problem
//        costs.put('d', 2.0);
//        costs.put('E', 3.0);
//        costs.put('e', 3.0);

        marriages = new HashMap();//map to store the connections between the couples
        marriages.put('A', 'a');
        marriages.put('B', 'b');
        marriages.put('C', 'c');
//        marriages.put('D', 'd'); //uncomment for extended problem
//        marriages.put('E', 'e');
        
        int raftSize=2;//raft size can be customised
        LoHusbState initialStateB = new LoHusbState(couplesNBasic, couplesSBasic, RiverBank.SOUTH);
        LoHusbState goalStateB = new LoHusbState(couplesNGoalB, couplesSGoalB, RiverBank.NORTH);
        
//        LoHusbState initialState = new LoHusbState(couplesN, couplesS, RiverBank.SOUTH);//uncomment for extended
//        LoHusbState goalState = new LoHusbState(couplesNGoal, couplesSGoal, RiverBank.NORTH);
        
        LovingHusbandProblem problem = new LovingHusbandProblem(initialStateB, goalStateB, raftSize);
//        LovingHusbandProblem problem = new LovingHusbandProblem(initialState, goalState, raftSize);//extended
        System.out.println("Searching...");
        Path path = problem.search();			//perform search, get result
        System.out.println("Done!");			//print some message
        System.out.println("Raft size: "+raftSize);
        System.out.println("Initial state:\n" + initialStateB.toString() + "\n");//change to initialState for extended
        System.out.println("Goal state:\n" + goalStateB.toString());
        if (path == null) //if it is null, no solution
        {
            System.out.println("No solution");
        } else {
            path.print();
            System.out.println("Nodes visited: " + problem.nodeVisited);
            System.out.println("Cost: " + path.cost + "\n");
        }
    }

}
