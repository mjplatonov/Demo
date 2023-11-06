import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    static String arabicDigitsRegex = "[0-9]{1,2}";
    static String romanDigitsRegex = "[IVX]{1,4}";
    static String mathOperationRegex = "[/*+-]";

    static Map<String, String> romanToArabicMap = new HashMap<>() {{
        put("I", "1");
        put("II", "2");
        put("III", "3");
        put("IV", "4");
        put("V", "5");
        put("VI", "6");
        put("VII", "7");
        put("VIII", "8");
        put("IX", "9");
        put("X", "10");
    }};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            while (true) {
                String command = scanner.nextLine();

                if (command.equals("quit")) {
                    System.out.println("Program completed");
                    break;
                }

                System.out.println(calc(command));

            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }


    }

    public static String calc(String input) throws Exception{

        String[] splittedInput  = input.split(" ");
        if (splittedInput.length != 3) {
            throw new Exception("Wrong command format");
        }

        String firstDigit = splittedInput[0];
        String mathOperation = splittedInput[1];
        String secondDigit = splittedInput[2];

        if (!mathOperation.matches(mathOperationRegex)) {
            throw new Exception("Wrong math operation");
        }

        if (isAcceptedArabicNumber(firstDigit) &&
                isAcceptedArabicNumber(secondDigit)) {
            return getResultOfMathOperation(firstDigit, secondDigit, mathOperation);
        }

        if (isAcceptedRomanDigits(firstDigit) && isAcceptedRomanDigits(secondDigit)) {
            String result = getResultOfMathOperation(getArabicDigit(firstDigit), getArabicDigit(secondDigit), mathOperation);
            return getRomanDigit(result);
        }

        throw new Exception("Something went wrong");
    }

    static boolean isAcceptedRomanDigits(String value) throws Exception{
        if (romanToArabicMap.containsKey(value) && value.matches(romanDigitsRegex)) {
            return true;
        }
        throw new Exception("Number is out of range");
    }

    static String getArabicDigit(String romanDigit){
        return romanToArabicMap.get(romanDigit);
    }

    static boolean isAcceptedArabicNumber(String number) throws Exception{
        if (number.matches(arabicDigitsRegex)) {
            int numberValue = Integer.parseInt(number);
            return numberValue >= 1 && numberValue <= 10;
        }
        throw new Exception("Numbers out of range");
    }

    static String getResultOfMathOperation(String value1, String value2, String mathOperation) throws Exception{
        int firstDigit = Integer.parseInt(value1);
        int secondDigit = Integer.parseInt(value2);
        Integer result = switch (mathOperation) {
            case "-" -> firstDigit - secondDigit;
            case "+" -> firstDigit + secondDigit;
            case "/" -> firstDigit / secondDigit;
            case "*" -> firstDigit * secondDigit;
            default -> throw new Exception("Wrong math operation");
        };
        return String.valueOf(result);
        }

        static String getRomanDigit(String value) throws Exception{
        int number = Integer.parseInt(value);
        if (number <= 0 || number > 100) {
            throw new Exception("Result out of range");
        }

        int[] arabicValues = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanNumerals = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder result = new StringBuilder();
        int i = 0;

        while (number > 0) {
            if (number >= arabicValues[i]) {
                result.append(romanNumerals[i]);
                number -= arabicValues[i];
            } else {
                i++;
            }
        }
            return result.toString();
        }
    }
