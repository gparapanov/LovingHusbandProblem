/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lovinghusbandpuzzle;

import cm3038.search.Action;
import java.util.Arrays;

/**
 *
 * @author Georgi
 */
public class LoHusbAction extends Action {

    public RiverBank toBank;
    public char[] peopleMoved;

    //public double cost;

    /**
     *  This constructor takes 2 parameters and creates and action class, adding
     * all of the costs of the people, taken from the main method.
     * @param peopleMoved an array of people that will be moved
     * @param to the side towards the raft is going
     */
    public LoHusbAction(char[] peopleMoved, RiverBank to) {
        this.peopleMoved = Arrays.copyOf(peopleMoved, peopleMoved.length);
        this.cost = 0;
        this.toBank = to;
        for (int i = 0; i < peopleMoved.length; i++) {
            this.cost += LovingHusbandPuzzle.costs.get(peopleMoved[i]);
        }

    }
/**
 * This method returns the current action represented by the direction, people
 * that are moved and the whole cost of the action, based on the people's costs.
 * @return the current action in a string format
 */
    @Override
    public String toString() {
        String result;

        if (this.toBank == RiverBank.NORTH) {
            result = "South->North ";
        } else {
            result = "North->South ";
        }
        for (int i = 0; i < peopleMoved.length; i++) {
            result += peopleMoved[i];
        }

        result += " (Cost: " + cost + ")";

        return result;
    }

}
