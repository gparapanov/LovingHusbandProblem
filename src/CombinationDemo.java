package cm3038.jhp.test;

import java.util.*;
import org.apache.commons.math3.util.*;

public class CombinationDemo {

    public static void main(String[] arg) {
        String[] names = {"Adam", "Ben", "Claire", "Doris", "Edward", "Fiona"};	//An array of objects. In this case it is an array of String.

        Combinations comb = new Combinations(names.length, 3);		//Create a combination to select 3 objects out of the array.
        Iterator<int[]> iterator = comb.iterator();				//Get the iterator of the combination

        while (iterator.hasNext()) //while there is a combination left
        {
            int[] selected = iterator.next();				//get the next one

	//The indices of the selected object is in the "selected" array.
            //Now print them out.
            for (int nameIndex : selected) //Go through elements in the "selected" array. Each one is an index into the original array.
            {
                System.out.print(names[nameIndex] + " ");	//Print out a name, followed by a space.
            }
            System.out.println();						//Print a newline at the end of this selection.
        }
    } //end method
} //end class
