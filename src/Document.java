import java.util.ListIterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Document{
    public String name;
    public TwoWayCycledOrderedListWithSentinel<Link> link;
    public Document(String name, Scanner scan) {
        this.name=name.toLowerCase();
        link=new TwoWayCycledOrderedListWithSentinel<Link>();
        load(scan);
    }

    public void load(Scanner scan) {
        while (scan.hasNext()) {
            String line = scan.nextLine();
            if (line.equals("eod")) return;

            String[] oneLine = line.split(" ");

//            String regex = "[Ll][Ii][Nn][Kk]=(.+)";
//            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
//            Matcher matcher;
            String regex = "link=(.+)";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher;

            for (String string : oneLine) {
                matcher = pattern.matcher(string);

                if (matcher.matches())
                    if (isCorrectLink(matcher.group(1))) {
                        link.add(new Link(matcher.group(1).toLowerCase()));
                    } else if (checkWithWeight(matcher.group(1))) {
                        link.add(createLink(matcher.group(1).toLowerCase()));
                    }
            }
        }
    }

    public static boolean isCorrectLink(String id) {
        // (([0-9]*)\)
        String regex = "[a-zA-Z][a-zA-Z_0-9]*";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(id);

        return matcher.matches();
    }

    public static boolean checkWithWeight(String id) {
        // (([0-9]*)\)
        boolean result = false;

        String regex = "[a-zA-Z][a-zA-Z_0-9]*(.*)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(id);

        if (matcher.matches()) {
            String weightReg = "(\\({1}([1-9]{1}[0-9]*)\\){1})";
            Pattern weightPat = Pattern.compile(weightReg);
            Matcher weightMatch = weightPat.matcher(matcher.group(1));

            return weightMatch.matches();
        }

        return false;
    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    static Link createLink(String link) {
        String regex = "([a-zA-Z][a-zA-Z_0-9]*)\\((.*)\\)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(link);

        if (matcher.matches()) {

            return new Link(matcher.group(1), Integer.parseInt(matcher.group(2)));
        }
        return null;
    }

    public static boolean isCorrectId(String id) {
        String regex = "[a-zA-Z][a-zA-Z_0-9]*";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(id);

        return matcher.matches();
    }


    @Override
    public String toString() {

        return "Document: " + name + link.toString().toLowerCase();
    }

    public String toStringReverse() {

        return "Document: " + name + link.toStringReverse().toLowerCase();
    }

    public int[] getWeights() {
        int[]weights = new int[link.size];

        for (int i = 0; i < link.size; i++) {
            weights[i] = link.get(i).getWeight();
        }

        return weights;
    }

    public static void showArray(int[] arr) {//Sysout w sortach
        for (int i = 0; i < arr.length; i++ ){
            if (i != arr.length - 1){
                System.out.print(arr[i] + " ");
            }else {
                System.out.print(arr[i]);
            }
        }
    }

    void bubbleSort(int[] arr) {
        int elemCount = 0;
        int temp;

        for (int i = 0 ; i < arr.length; i++){
            showArray(arr);
            System.out.println();

            for (int j = arr.length - 1; j > elemCount;j--){
                if ( arr[j] < arr[j - 1]){
                    temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
            }

            elemCount++;
        }
    }

    public void insertSort(int[] arr) {
        int elemCount = 0;
        int currInt;

        for (int i = 0 ; i < arr.length; i++){
            currInt = arr[arr.length - elemCount - 1];

            for (int j = arr.length - elemCount; j < arr.length; j++){
                if (currInt < arr[j]){
                    break;

                }else {
                    arr[j - 1] = arr[j];
                    arr[j] = currInt;
                }
            }

            elemCount++;
            showArray(arr);
            System.out.println();
        }
    }

    public void selectSort(int[] arr) {
        int elemCount = 0;
        int currMax;
        int indexMax = -1;
        int temp;

        for (int i = 0 ; i < arr.length; i++){
            showArray(arr);
            System.out.println();

            currMax = Integer.MIN_VALUE;
            for (int j = 0; j < arr.length - elemCount; j++){
                if (arr[j] > currMax ){
                    currMax = arr[j];
                    indexMax = j;
                }
            }

            temp = arr[arr.length - elemCount - 1];
            arr[arr.length - elemCount - 1] = currMax;
            arr[indexMax] = temp;

            elemCount++;

//            if (i != arr.length - 1){
//                showArray(arr);
//                System.out.println();
//            }else {
//                showArray(arr);
//            }
        }
    }
}