import java.io.*;
import java.util.Iterator;

/**
 * Anagram finder main file.
 * @author vangarde03
 * @version 1.0.2 December 16, 2023
 */

public class AnagramFinder {

    /**
     * Reads the user input and assigns the reference to one of 3 desired interfaces based on the input
     * @param theStructure the string containing the user's desired data structure
     * @param map the map reference that will eventually point to the data structure that contains the anagrams
     * @return returns the map reference with an assigned data structure
     */
    private static MyMap<String, MyList<String>> instantiateStructure(String theStructure, MyMap<String, MyList<String>> map){
        if(theStructure.compareTo("bst") == 0){
            map = new BSTMap<>();
        }else if(theStructure.compareTo("avl") == 0){
            map = new AVLTreeMap<>();
        } else{
            map = new MyHashMap<>();
        }
        return map;
    }

    /**
     * Takes any inputted word and sorts the letters in it lexicographically using insertion sort
     * @param word the word that needs be to sorted lexicographically
     * @return the lexicographically sorted version of the inputted word
     */
    private static String insertionSort(String word){
        char[] wordArray = word.toLowerCase().toCharArray(); //code taken from https://stackoverflow.com/questions/5235401/split-string-into-array-of-character-strings
        //the above line takes the inputted word and converts it all to lowercase and then into an array of chars
        int j;
        for( int p = 1; p < wordArray.length; p++ ) {
            char x = wordArray[p];
            for( j = p; j > 0 && x < wordArray[j-1]; j--){
                wordArray[ j ] = wordArray[j-1];
            }
            wordArray[ j ] = x;
        }
        return String.valueOf(wordArray); //code from https://stackoverflow.com/questions/7655127/how-to-convert-a-char-array-back-to-a-string
        //the above line converts the array of chars into a string
    }

    /**
     * Attempts to read the given file. It goes through each word in the file, makes it lowercase, and sorts each word using insertion sort, and then
     * it checks the object/data structure attached to the map reference to see if the sorted version of the word is in the data structure. If it is,
     * then it appends the original form of the word to the linked list attached to the key within the map. (The key is the sorted version of the word)
     * If it isn't present, then it makes a new linked list and assigns it to the key, inputting it into the map. Once finished, it returns the modified map,
     * with all the words from the file in the object it refers to.
     * @param mapThing the map reference that points to an assigned data structure
     * @param filename the File object that is named based on what the user inputted for the file
     * @param fileNameStringV the String version of the name that the user inputted for the file
     * @return map reference containing a modified version of the data structure (contains all the words from the file)
     */
    private static MyMap<String, MyList<String>> readFile(MyMap<String, MyList<String>> mapThing, File filename, String fileNameStringV){
        try{
            BufferedReader fileReader = new BufferedReader(new FileReader(filename)); //source for code: https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
            //the above line creates a BufferedReader object that is tasked with reading each word in the file
            String stringFromFile;
            String sortedString;

            try {
                while ((stringFromFile = fileReader.readLine()) != null) {  //reads each word in the file and converts it to a string
                    sortedString = insertionSort(stringFromFile.toLowerCase()); //sorts each word from the file after converting it to lowercase
                    MyList<String> checkKey = mapThing.get(sortedString); //checks if there is a linked list attached to the sorted version of the word
                    if(checkKey == null){
                        MyList<String> anagramList = new MyLinkedList<String>();
                        anagramList.add(stringFromFile); //if the checkKey is null (no linked list attached to value, then create a new one and attach it to the key, which is the sorted word)
                        mapThing.put(sortedString, anagramList);
                    } else if(mapThing.get(sortedString) != null){

                        checkKey.add(stringFromFile); //if the checkKey exists, then add the word from the file to the linked list
                    }
                }
            }catch(java.io.IOException a){
                System.out.println("Error: An I/O error occurred reading '" + fileNameStringV + "'.");
                System.exit(1);
            }


        } catch (FileNotFoundException e) {
            System.out.println("Error: Cannot open file '" + fileNameStringV + "' for input.");
            System.exit(1);
        }
        return mapThing;

    }

    /**
     * This method attempts to find the original word (in sorted form) in the map. If it can't find it, it exits the method. If it is able
     * to find it, will take each word in the linked list attached to the found key (sorted form of the original word) and put it into an
     * array of the same size as the linked list. It then sorts the words in the array lexicographically using insertion sort and then
     * prints out the sorted form of the array. It also makes sure not to print out the original, unsorted version of the word in its output.
     * @param originalWordSorted the sorted version of the original word that the user inputted
     * @param mapThing the map reference pointing to a data structure (hash, avl, or bst) based on what the user inputted
     * @param originalWord the unsorted, original version of the word that the user inputted
     */
    private static void displayOutPut(String originalWordSorted, MyMap<String, MyList<String>> mapThing, String originalWord){
        String[] anagramArr = null;
        MyList<String> checkKey = mapThing.get(originalWordSorted);
        if(checkKey == null){
            System.out.println("No anagrams found.");
            return;
        }

        else{
            anagramArr = new String[checkKey.size()];

            for(int i = 0; i< anagramArr.length; i++){
                anagramArr[i] = checkKey.get(i); //converts the linked list associated with the sorted version of the original word into any array of strings
            }
            anagramArr = insertionSort(anagramArr);
            int counter = 0;
            for(String element: anagramArr){
                if(element.compareTo(originalWord)!=0) { //makes sure not to output the original word
                    counter+=1; //count number of times the word itself is not present
                    System.out.println(element);
                }
            }
            if(counter==0){
                //this is here because sometimes, words that are searched for are already present in the file.
                //This can end up resulting in an anagram array being of length 1, but containing only the same word (but its origin is from the file
                //rather than the user input)
                System.out.println("No anagrams found.");
                return;
            }
        }
    }

    /**
     * Takes an array of words (anagrams) and sorts them lexicographically using insertion sort, returning
     * the sorted version of the array.
     * @param arrOfAnagrams the array of words
     * @return sorted array of words
     */
    private static String[] insertionSort(String[] arrOfAnagrams){
        int j;
        for( int p = 1; p < arrOfAnagrams.length; p++ ) {
            String x = arrOfAnagrams[p];
            for( j = p; j > 0 && x.compareTo(arrOfAnagrams[j-1]) < 0; j--){
                arrOfAnagrams[ j ] = arrOfAnagrams[j-1];
            }
            arrOfAnagrams[j] = x;
        }
        return arrOfAnagrams;
    }


    public static void main(String[] args){
        if(args.length != 3){  //checks if args is the correct length
            System.out.println("Usage: java AnagramFinder <word> <dictionary file> <bst|avl|hash>");
            System.exit(1);
        }
        else{
            File textFile = new File(args[1]);//attempting to open the file given by the user
            if(!textFile.exists()){
                System.out.println("Error: Cannot open file '" + args[1] + "' for input.");
                System.exit(1);
            }
            if(args[2].compareTo("avl") !=0 && args[2].compareTo("bst") !=0 && args[2].compareTo("hash") !=0 ){ //checks if the user inputted a valid data structure
                System.out.println("Error: Invalid data structure '" + args[2] + "' received.");
                System.exit(1);
            }


            MyMap<String, MyList<String>> mapThing = null;  //creating the map reference
            mapThing = instantiateStructure(args[2], mapThing); //assigning the reference to a certain data structure

            String sortedWord = insertionSort(args[0]);


            mapThing.put(sortedWord, null); //inputs sorted version of original word it into the map

            mapThing = readFile(mapThing, textFile, args[0]); //reads the dictionary and puts the words from it into the map

            displayOutPut(sortedWord, mapThing, args[0]); //displays the output
            System.exit(0);
        }
    }


}
