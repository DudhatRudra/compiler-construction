import java.util.Scanner;
import java.util.*;

public class prac2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Map<Integer, Map<Character, Integer>> map = new HashMap<>();

        System.out.println("Number of input symbols");
        int n = sc.nextInt();

        char[] symbols = new char[n];
        System.out.println("Input symbols:");
        for (int i = 0; i < n; i++) {
            symbols[i] = sc.next().charAt(0);
        }

        System.out.print("Enter number of states: ");
        int state = sc.nextInt();

        System.out.print("Initial state: ");
        int initialState = sc.nextInt();

        System.out.print("Number of accepting states):");
        int numAccepting = sc.nextInt();

        System.out.println("Accepting states:");
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < numAccepting; i++) {
            set.add(sc.nextInt());
        }

        System.out.printf("Transition table: ");

        for(int i=1;i<=state;i++){
            Map<Character, Integer> innerMap = new HashMap<>();
            
            for(int j=0;j<n;j++){
                System.out.print(i + " to " + symbols[j] + " -> ");
                innerMap.put(symbols[j], sc.nextInt());
                System.out.println();
            }

            map.put(i, innerMap);
        }

        String str = sc.next();

        for(int i=0;i<str.length();i++){
            char c = str.charAt(i);
            initialState = map.get(initialState).get(c);
        }

        if(set.contains(initialState)){
            System.out.println("Valid String");
        }
        else{
            System.out.println("Invalid String");
        }

        sc.close();
    }
}