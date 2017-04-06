import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
 /**
 *
 * @author Sachanidis Athanasios
 * I crested my programm based on the implementation of mergesort in this site:
 * http://howtodoinjava.com/algorithm/merge-sort-java-example/
 */
public class MergerSort 
{
    public static void main(String[] args) throws IOException , FileNotFoundException
    {    
        
        String filename;
        Scanner in = new Scanner(System.in);
        System.out.println("choose the txt file you want(it must be placed in the same folder as the jar , and cd to the folder before execute jar)");
        filename = in.nextLine();
        File file = new File(filename);
        Scanner input = new Scanner(file);
        /**
         * So far we take the name of the input file from the user and  read it
         * from the programm
         */
        int sum=0;
        while(input.hasNext()){
            input.nextInt();
            sum++;
        }
        input.close();
        /**
         * In the first while loop i just count the number of integers that
         * the  given file contains in order to know the length of the array
         * that i will sort later
        */
        int[] integers = new int [sum];
        Scanner input1 = new Scanner(file);
        int t=0;
        while(input1.hasNext()){
            integers[t]=(input1.nextInt());
            t++;
        }
        input1.close();
        Integer[] teliko = Arrays.stream( integers ).boxed().toArray( Integer[]::new );
        /**
         * In the second while loop i inset the given integers into an array
         * Afterwards(in line 46) i covert the int[] array into an Integer[] array
         * This is necessary because i need the array in a state of Object to 
         * use it later
        */
        //Just a wrapper for the integer that will count the cost later
        IntWrapper countCompare = new IntWrapper();
        /**
         * Call merge sort , the basic function 
         * Input is an Object(array) and the wrapper
         */
        mergeSort(teliko,countCompare);
        System.out.println("Total cost: " + countCompare.val);
    }

    @SuppressWarnings("rawtypes") 
    public static Comparable[] mergeSort(Comparable[] list,IntWrapper swaps) 
    {
        //If list is empty; no need to do anything
        if (list.length <= 1) {
            return list;
        }
         
        //Split the array in half in two parts
        Comparable[] first = new Comparable[list.length / 2];
        Comparable[] second = new Comparable[list.length - first.length];
        /**
         * arraycopy is a function that copy an array to an other
         * base of the index you want to start and whre you want to end
         * Only for objects not int[] arrays
         */
        System.arraycopy(list, 0, first, 0, first.length);
        System.arraycopy(list, first.length, second, 0, second.length);
         
        //Sort each half recursively 
        mergeSort(first,swaps);
        mergeSort(second,swaps);
         
        //Merge both halves together, overwriting to original array
        merge(first, second, list,swaps);
        return list;
    }
     
    @SuppressWarnings({ "rawtypes", "unchecked" }) 
    private static void merge(Comparable[] first, Comparable[] second, Comparable[] result,IntWrapper swaps) 
    {
        //Index Position in first array - starting with first element
        int iFirst = 0;
         
        //Index Position in second array - starting with first element
        int iSecond = 0;
         
        //Index Position in merged array - starting with first position
        int iMerged = 0;
         
        //Compare elements at iFirst and iSecond, 
        //and move smaller element at iMerged
        while (iFirst < first.length && iSecond < second.length) 
        {
            /**
             * The method compares the Number object that invoked the method to 
             * the argument. It is possible to compare Byte, Long, Integer, etc.
             * However, two different types cannot be compared, both the 
             * argument and the Number object invoking the method should be 
             * of the same type.
             */ 
            if (first[iFirst].compareTo(second[iSecond]) < 0) 
            {
                //if  first<second the new array(merged) take first integer
                result[iMerged] = first[iFirst];
                //move to the next integer
                iFirst++;
            } 
            else if (first[iFirst].compareTo(second[iSecond]) > 0)
            {
                /**
                 * if first>secondthe new array(merged) take second integer
                 */
                /*convert the Integer to int x,y because we need to express
                the +1 difference between the two numbers*/
                int x = ((Number) first[iFirst]).intValue();
                int y = ((Number) second[iSecond]).intValue();
                if(x==(y+1)){swaps.val=swaps.val+2;}
                else{swaps.val=swaps.val+3;}
                /**
                 * I compare x and y in order to add the cost in every case
                 * if first integer is bigger only by 1 then cost=cost+2
                 * else cost=cost+3
                 */
                result[iMerged] = second[iSecond];
                iSecond++;
            }
            else {
                /**
                 * when first=second we add both numbers in the merged array
                 * and increment the indexes of the two arrays by one +1
                 * no inversion here
                 */
                result[iMerged] = second[iSecond];
                result[iMerged+1] = first[iSecond];
                iFirst++;
                iSecond++;
                /*
                because we insert two numbers we move index of merged array
                by two +2
                One here and one in the general outside of the if-else statement
                */
                iMerged++;
            }
            iMerged++;
        }
        //copy remaining elements from both halves - each half will have already sorted elements
        System.arraycopy(first, iFirst, result, iMerged, first.length - iFirst);
        System.arraycopy(second, iSecond, result, iMerged, second.length - iSecond);
    }
}