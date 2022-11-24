package bullscows;

import java.util.*;

class countBullsAndCows {
    private int secLen;
    private String guess;
    private String secret;
    int bulls = 0;
    int cows = 0;
    private ArrayList<String> guessArr;
    private ArrayList<String> secretArr;

    countBullsAndCows(int secLen, String sec, String gue) {
        this.secLen = secLen;
        this.secret = sec;
        this.guess = gue;
        guessArr = numberProcessor(guess);
        secretArr = numberProcessor(secret);
    }
    public ArrayList<String> numberProcessor(String num){
        ArrayList<String> numArr = new ArrayList<String>(Arrays.asList(num.split("")));
//        System.out.println("split array "+numArr);
        return numArr;
    }

    public void grader(){
        for(int i = 0; i<secLen; i++){
            String elem = guessArr.get(i);
            if(elem.equals(secretArr.get(i))) {
                bulls++;
            } else if(secretArr.contains(elem)) {
                cows++;
            }
        }
    }

    public void printGrade(){
        String bullStr = "bulls";
        String cowStr = "cows";
        if(bulls == 0 || bulls == 1){
            bullStr = "bull";
        }
        if(cows == 0 || cows == 1){
            cowStr = "cow";
        }
        if (bulls == 0 && cows == 0){
            System.out.println("Grade: None");
        } else if (bulls == 0) {
            System.out.println("Grade: " + cows + " " + cowStr);
        } else if (cows == 0) {
            System.out.println("Grade: " + bulls + " " + bullStr );
        }
        else {
            System.out.println("Grade: " + bulls + " " + bullStr + " and " + cows + " " + cowStr);
        }
    }
}

class randomNumberGenerator {
    private int n;
    private int symbols;
    String secretCode ;
    randomNumberGenerator(int len, int symbols){
            this.n = len;
            this.symbols = symbols;
            this.secretCode = randomNumber(n, symbols);
            System.out.print("The secret is prepared: ");
            for (int i = 0; i < n; i++) {
                System.out.print("*");
            }
            if(symbols<10){
                System.out.println(" (0-9).");
            }
            else if (symbols == 11){
                System.out.println(" (0-9, a)" );
            }
            else {
                System.out.println(" (0-9, a-" + (char)(symbols+86) + ").");
            }
    }
    public String randomNumber (int n, int symbols){

        StringBuilder possibleSymbols = new StringBuilder("0123456789abcdefghijklmnopqrstuvwxyz");

        StringBuilder num = new StringBuilder("");
        while(num.length() != n){
            Random random = new Random();
            char c = possibleSymbols.charAt((random.nextInt(symbols)));
            if(num.indexOf(String.valueOf(c)) == -1){
                num = num.append(c);
            }
        }
        return num.toString();
    }
}
public class Main {

    public static void main(String[] args) {
        int turn = 1;
        int breakFlag = 0;
        int n = 0;
        int symbols = 0;

        Scanner scan = new Scanner(System.in);
        try{
            System.out.println("Input the length of the secret code:");
            String s = scan.nextLine();
            n = Integer.parseInt(s);
        } catch (NumberFormatException e){
            System.out.println("Error: enter a valid number");
            return;
        }
        if(n<= 0 || n>36){
            System.out.println("Error: range for n is 1-36");
            return;
        }

        try {
            System.out.println("Input the number of possible symbols in the code:");
            String s = scan.nextLine();
            symbols = Integer.parseInt(s);
        } catch (NumberFormatException e){
            System.out.println("Error: enter a valid number");
            return;
        }
        if(symbols<=0 || symbols>36) {
            System.out.println("Error: number of symbols possible is 1-36");
            return;
        }

        if(symbols<n) {
            System.out.println("Error: not enough symbols");
            return;
        }

            System.out.println("Okay, let's start a game!");
            randomNumberGenerator randomNum = new randomNumberGenerator(n,symbols);

            do{
                System.out.println("Turn " + turn + " :");
                Scanner scanner = new Scanner(System.in);
                String guess = scanner.nextLine();
                if(guess.length() != n){
                    System.out.println("Error: Your guess should be " + n + " characters");
                    return;
                }
                countBullsAndCows obj = new countBullsAndCows(n,randomNum.secretCode,guess) ;
                obj.grader();
                obj.printGrade();
                breakFlag = obj.bulls;
                if(breakFlag == n){
                    System.out.println("Congratulations! You guessed the secret code.");
                }
                turn++;
            } while(breakFlag != n);

    }
}
