import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    static final String ARABIC_NUMBERS_REGEX = "[0-9]{1,2}";
    static final String ROMAN_NUMBERS_REGEX = "[IVX]{1,4}";
    static final String MATH_OPERATION_REGEX = "[/*+-]";

    static final Map<String, String> ROMAN_TO_ARABIC_MAP = new HashMap<>() {{
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
        System.out.println("Enter two numbers from 1 to 10 and math operation to get result (e.g. 1 + 2, VI / III)");

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

        if (!mathOperation.matches(MATH_OPERATION_REGEX)) {
            throw new Exception("Wrong math operation");
        }

        if (isAcceptedArabicNumber(firstDigit) &&
                isAcceptedArabicNumber(secondDigit)) {
            return getResultOfMathOperation(firstDigit, secondDigit, mathOperation);
        }

        if (isAcceptedRomanNumber(firstDigit) && isAcceptedRomanNumber(secondDigit)) {
            String result = getResultOfMathOperation(getArabicNumber(firstDigit), getArabicNumber(secondDigit), mathOperation);
            return getRomanDigit(result);
        }

        throw new Exception("Wrong input numbers");
    }

    static boolean isAcceptedRomanNumber(String value) {
        return ROMAN_TO_ARABIC_MAP.containsKey(value) && value.matches(ROMAN_NUMBERS_REGEX);
    }

    static String getArabicNumber(String romanDigit){
        return ROMAN_TO_ARABIC_MAP.get(romanDigit);
    }

    static boolean isAcceptedArabicNumber(String number) {
        if (number.matches(ARABIC_NUMBERS_REGEX)) {
            int numberValue = Integer.parseInt(number);
            return numberValue >= 1 && numberValue <= 10;
        }
        return false;
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
            throw new Exception("Result is out of range");
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
