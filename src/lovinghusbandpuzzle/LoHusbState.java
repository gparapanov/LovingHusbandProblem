/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lovinghusbandpuzzle;

import cm3038.search.ActionStatePair;
import cm3038.search.State;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math3.util.Combinations;

/**
 *
 * @author Georgi
 */
public class LoHusbState implements State {

    public char[] couplesN;//people on the north side
    public char[] couplesS;//people on the south side
    /**
     * The location of the raft as defined in the {@link RiverBank} enumerated
     * type.
     */
    public RiverBank raftLocation;

    /**
     * The state needs 3 parameters - arrays for people on the north and south,
     * and the location of the raft .
     *
     * @param couplesN people on the north side
     * @param couplesS people on the south side
     * @param raftLocation
     */
    public LoHusbState(char[] couplesN, char[] couplesS, RiverBank raftLocation) {
        this.couplesN = Arrays.copyOf(couplesN, couplesN.length);
        this.couplesS = Arrays.copyOf(couplesS, couplesS.length);
        this.raftLocation = raftLocation;
    }

    /**
     * This method returns a string representation of the state with the people
     * on both sides and the raft location.
     *
     * @return a string representation of the current state
     */
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < couplesN.length; i++) {
            //if the array value is '0' means that it is unoccupied
            //i.e. a person can be moved there if needed
            if (couplesN[i] != '0') {
                result += couplesN[i] + "";
            }
        }
        if (raftLocation == RiverBank.NORTH) {
            result += "  Raft";
        }
        result += "\n--------\n";
        for (int i = 0; i < couplesS.length; i++) {
            if (couplesS[i] != '0') {
                result += couplesS[i] + "";
            }
        }
        if (raftLocation == RiverBank.SOUTH) {
            result += " Raft";
        }
        return result;
    }

    /**
     * This method finds all the valid successors of the current state.
     *
     * @return a list containing the valid children of the current state
     */
    @Override
    public List<ActionStatePair> successor() {
        List<ActionStatePair> result = new ArrayList<>();
        if (this.isInvalid()) //if current state is invalid
        {
            return result;		//return an empty set
        }
        //an array of all of the people, that are on the side of the raft
        //therefore they will be moved and create children
        ArrayList<Character> toMove = new ArrayList<>();
        for (int j = 0; j < couplesN.length; j++) {
            if (raftLocation == RiverBank.NORTH) {//if the raft is on the north
                if (couplesN[j] != '0') {//people from the north are added to the arraylist
                    toMove.add(couplesN[j]);
                }
            } else {
                if (couplesS[j] != '0') {//if the raft is on the south
                    toMove.add(couplesS[j]);//people from the north are added to the arraylist
                }
            }
        }
        //start with moving a number of people as the raft size
        //until it gets to 1(moving 1 person)
        for (int i = LovingHusbandProblem.raftSize; i > 0; i--) {
            if (toMove.size() < i) {
                //if the people that need to be moved are less than the raft size
                //then i starts with the size of the arraylist
                i = toMove.size();
            }
            //make a new combination every time
            Combinations comb = new Combinations(toMove.size(), i);
            Iterator<int[]> iterator = comb.iterator();
            while (iterator.hasNext()) //while there is a combination left
            {
                int[] selected = iterator.next();//get the next one
                char[] temp = new char[i];//make a temporary array
                int count = 0;
                for (int index : selected) //Go through elements in the "selected" array. Each one is an index into the original array.
                {
                    temp[count] = toMove.get(index);//fill the temp array with combination people
                    count++;
                }
                LoHusbAction action = new LoHusbAction(temp, oppositeBank(this.raftLocation));
                if (action.peopleMoved.length > 0) {
                    LoHusbState nextState = this.applyAction(action);
                    if (!nextState.isInvalid()) {
                        result.add(new ActionStatePair(action, nextState));
                    }
                }

            }
        }
        return result;
    }

    /**
     *
     * @param current position of the raft
     * @return opposite side of the river raft
     */
    private RiverBank oppositeBank(RiverBank current) {
        if (current == RiverBank.NORTH) {
            return RiverBank.SOUTH;
        }
        return RiverBank.NORTH;
    }

    /**
     * This method basically replaces the '0' on one side with the values from
     * the action. Then it finds the people that it has already added on the
     * opposite side and replaces them with '0'.
     *
     * @param action
     * @return the new state after the applied action
     */
    public LoHusbState applyAction(LoHusbAction action) {
        int count = 0;//use count to iterate through the people from the action
        LoHusbState newState = new LoHusbState(couplesN, couplesS, raftLocation);
        if (action.toBank == RiverBank.NORTH) {//if we are moving to the north
            newState.raftLocation = RiverBank.NORTH;//new raft loc is north
            for (int i = 0; i < newState.couplesN.length; i++) {
                if (newState.couplesN[i] == '0' && (count < action.peopleMoved.length)) {
                    //going through the peopleMoved from action and putting them
                    //in their new place
                    newState.couplesN[i] = action.peopleMoved[count];
                    count++;
                }
            }
            count = 0;//now replace the added values in the north, with '0'
            //in the south(because they still exist there)
            for (int i = 0; i < newState.couplesS.length; i++) {
                if (count < action.peopleMoved.length) {
                    if (newState.couplesS[i] == action.peopleMoved[count]) {
                        newState.couplesS[i] = '0';
                        count++;
                    }
                }
            }
            count = 0;
        } else {
            //do exactly the same thing if we are moving to the south
            newState.raftLocation = RiverBank.SOUTH;
            for (int i = 0; i < newState.couplesS.length; i++) {
                if (newState.couplesS[i] == '0' && count < action.peopleMoved.length) {
                    newState.couplesS[i] = action.peopleMoved[count];
                    count++;
                }
            }
            count = 0;//now replace them with '0'
            for (int i = 0; i < newState.couplesN.length; i++) {
                if (count < action.peopleMoved.length) {
                    if ((newState.couplesN[i] == action.peopleMoved[count])) {
                        newState.couplesN[i] = '0';
                        count++;
                    }
                }
            }
            count = 0;
        }
        return newState;
        //On first look the method may seem long and complicated, but it is 
        //actually simple, it does almost the same things a few times.
        //Depending on the collection chosen to represent the problem it could
        //be shorter i.e. if using arraylist, there are easier method instead of loops
    }

    /**
     * This method checks if 2 states are the same. It sorts the arrays first,
     * because Arrays.sort does take in mind order of the elements inside.
     *
     * @param state other state
     * @return true/false
     */
    @Override
    public boolean equals(Object state) {
        if (!(state instanceof LoHusbState)) //if the given parameter is not a McState
        {
            return false;			//it must be false
        }
        LoHusbState other = (LoHusbState) state;
        Arrays.sort(this.couplesN);
        Arrays.sort(this.couplesS);
        Arrays.sort(other.couplesN);
        Arrays.sort(other.couplesS);
        return Arrays.equals(this.couplesN, other.couplesN)
                && Arrays.equals(this.couplesS, other.couplesS)
                && this.raftLocation == other.raftLocation;
    }

    /**
     * The method goes through the couples and finds on which side they are on.
     * If they are on the same side - all OK, if not then check if there is a
     * man on the same side as the wife, if there is then it is invalid. If it
     * passes all of the checks, then the state is okay.
     *
     * @return true/false
     */
    public boolean isInvalid() {
        for (char c : LovingHusbandPuzzle.marriages.keySet()) {//use marriage map to find all of the people
            char temp = LovingHusbandPuzzle.marriages.get(c);//get every woman - c is a man and temp woman
            boolean inNorthH = false, inNorthW = false;//the side of a husband and wife
            for (int i = 0; i < couplesN.length; i++) {
                if (couplesN[i] == c) {//find side of the husband
                    inNorthH = true;
                }
            }
            for (int i = 0; i < couplesN.length; i++) {
                if (couplesN[i] == temp) {//find side of the wife
                    inNorthW = true;
                }
            }
            if (inNorthW != inNorthH) {
                //if the sides are different then check the side of the wife
                //to see if there are any other men there, if there are then it's invalid
                if (inNorthW == true) {
                    for (char husb : LovingHusbandPuzzle.marriages.keySet()) {
                        for (int i = 0; i < couplesN.length; i++) {
                            if (couplesN[i] == husb) {//a husband is found
                                return true;
                            }
                        }
                    }
                } else {
                    for (char husb : LovingHusbandPuzzle.marriages.keySet()) {
                        for (int i = 0; i < couplesS.length; i++) {
                            if (couplesS[i] == husb) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Computes the hash code based on the ASCII value of the letters(people)
     * The people's values on the north are multiplied by 5 to have bigger
     * difference between the buckets.
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        for (int i = 0; i < this.couplesN.length; i++) {
            if (couplesN[i] != '0') {
                hash += ((int) couplesN[i]) * 5;
            }
        }
        for (int i = 0; i < this.couplesS.length; i++) {
            if (couplesS[i] != '0') {
                hash += ((int) couplesS[i]);
            }
        }
        if (this.raftLocation == RiverBank.NORTH) {
            hash += 1;
        }
        return hash;
    }

}
