package pl.numbers;


import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);


    private static String[] input() {
        System.out.println("\nEnter a request:\n");
        String userInput = SCANNER.nextLine().toUpperCase();
        return userInput.split(" ");
    }


    private static void printMenu() {
        System.out.println("Welcome to Amazing Numbers!\n" +
                "\n" +
                "Supported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "  * the first parameter represents a starting number;\n" +
                "  * the second parameter shows how many consecutive numbers are to be processed;\n" +
                "- two natural numbers and properties to search for;\n" +
                "- a property preceded by minus must not be present in numbers;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.");
    }


    private static void printAvailableProperties() {
        System.out.println("Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]");
    }

    private static String getManyNumbersProperties(BigInteger number) {
        String message = number + " is";

        message += isEven(number) ? " even," : "";
        message += isOdd(number) ? " odd," : "";
        message += isBuzz(number) ? " buzz," : "";
        message += isDuck(number) ? " duck," : "";
        message += isPalindromic(number.toString()) ? " palindromic," : "";
        message += isGapful(number) ? " gapeful," : "";
        message += isSpy(number) ? " spy," : "";
        message += isSquare(number) ? " square," : "";
        message += isSunny(number) ? " sunny," : "";
        message += isJumping(number.toString()) ? " jumping," : "";
        message += isHappy(number.toString()) ? " happy," : "";
        message += !isHappy(number.toString()) ? " sad," : "";

        return message.substring(0, message.length() - 1);
    }


    private static String getOneNumberProperties(BigInteger number) {
        return "\nProperties of " + NumberFormat.getNumberInstance(Locale.US).format(number) + "\n" +
                "        buzz: " + isBuzz(number) + "\n" +
                "        duck: " + isDuck(number) + "\n" +
                " palindromic: " + isPalindromic(number.toString()) + "\n" +
                "      gapful: " + isGapful(number) + "\n" +
                "         spy: " + isSpy(number) + "\n" +
                "      square: " + isSquare(number) + "\n" +
                "       sunny: " + isSunny(number) + "\n" +
                "        even: " + isEven(number) + "\n" +
                "         odd: " + isOdd(number) + "\n" +
                "     jumping: " + isJumping(number.toString()) + "\n" +
                "       happy: " + isHappy(number.toString()) + "\n" +
                "         sad: " + !isHappy(number.toString());
    }


    private static boolean hasNumberProperty(String property, BigInteger number) {
        switch (property) {
            case "EVEN":
                return isEven(number);
            case "ODD":
                return isOdd(number);
            case "BUZZ":
                return isBuzz(number);
            case "DUCK":
                return isDuck(number);
            case "PALINDROMIC":
                return isPalindromic(number.toString());
            case "GAPFUL":
                return isGapful(number);
            case "SPY":
                return isSpy(number);
            case "SQUARE":
                return isSquare(number);
            case "SUNNY":
                return isSunny(number);
            case "JUMPING":
                return isJumping(number.toString());
            case "HAPPY":
            case "-SAD":
                return isHappy(number.toString());
            case "SAD":
            case "-HAPPY":
                return !isHappy(number.toString());
            case "-EVEN":
                return !isEven(number);
            case "-ODD":
                return !isOdd(number);
            case "-BUZZ":
                return !isBuzz(number);
            case "-DUCK":
                return !isDuck(number);
            case "-PALINDROMIC":
                return !isPalindromic(number.toString());
            case "-GAPFUL":
                return !isGapful(number);
            case "-SPY":
                return !isSpy(number);
            case "-SQUARE":
                return !isSquare(number);
            case "-SUNNY":
                return !isSunny(number);
            case "-JUMPING":
                return !isJumping(number.toString());
            default:
                return false;
        }
    }

    private static boolean isMutuallyExclusive(String[] properties) {
        for (String property1 : properties) {
            for (String property2 : properties) {

                String errorMessage = "The request contains mutually exclusive properties: [" + property1 + ", " + property2 + "]\n" +
                        "There are no numbers with these properties.";

                if ((property1.equals("EVEN") && property2.equals("ODD")) || (property1.equals("ODD") && property2.equals("EVEN")) || (property1.equals("-EVEN") && property2.equals("-ODD")) || (property1.equals("-ODD") && property2.equals("-EVEN"))) {
                    System.out.println(errorMessage);
                    return true;
                }
                if ((property1.equals("DUCK") && property2.equals("SPY")) || (property1.equals("SPY") && property2.equals("DUCK")) || (property1.equals("-DUCK") && property2.equals("-SPY")) || (property1.equals("-SPY") && property2.equals("-DUCK"))) {
                    System.out.println(errorMessage);
                    return true;
                }
                if ((property1.equals("SUNNY") && property2.equals("SQUARE")) || (property1.equals("SQUARE") && property2.equals("SUNNY")) || (property1.equals("-SUNNY") && property2.equals("-SQUARE")) || (property1.equals("-SQUARE") && property2.equals("-SUNNY"))) {
                    System.out.println(errorMessage);
                    return true;
                }
                if ((property1.equals("HAPPY") && property2.equals("SAD")) || (property1.equals("SAD") && property2.equals("HAPPY")) || (property1.equals("-HAPPY") && property2.equals("-SAD")) || (property1.equals("-SAD") && property2.equals("-HAPPY"))) {
                    System.out.println(errorMessage);
                    return true;
                }
                if (property1.contains("-") && property1.substring(1).equals(property2)) {
                    System.out.println(errorMessage);
                    return true;
                }
                if (property1.equals(property2.substring(1)) && property2.contains("-")) {
                    System.out.println(errorMessage);
                    return true;
                }
            }
        }
        return false;
    }


    private static boolean isPropertyValid(String property) {
        for (Property p : Property.values()) {
            if (property.equals(p.toString()) || property.equals("-" + p)) {
                return true;
            }
        }
        return false;
    }


    private static boolean checkProperties(String[] properties) {
        ArrayList<String> invalidProperties = new ArrayList<>();
        for (String property : properties) {
            if (!isPropertyValid(property))
                invalidProperties.add(property);
        }
        if (invalidProperties.isEmpty()) {
            return true;
        } else {
            if (invalidProperties.size() == 1) {
                System.out.println("The property " + invalidProperties + " is wrong.");
            } else {
                System.out.println("The properties " + invalidProperties + " are wrong.");
            }
            printAvailableProperties();
            return false;
        }
    }


    public static void checkResult(String[] inputArray) {
        String message;
        BigInteger number;
        String[] propertiesArray;

        int counter;
        try {
            number = BigInteger.valueOf(Long.parseLong(inputArray[0]));
        } catch (NumberFormatException e) {
            number = new BigInteger("-1");
        }

        if (!isNatural(number)) {
            System.out.println("\nThe first parameter should be a natural number or zero.");
        } else if (inputArray.length == 1) {
            message = getOneNumberProperties(number);
            System.out.println(message);
        } else {
            try {
                counter = Integer.parseInt(inputArray[1]);
            } catch (NumberFormatException e) {
                counter = -1;
            }
            if (counter < 0) {
                System.out.println("\nThe second parameter should be a natural number.");
            } else if (inputArray.length == 2) {
                while (counter > 0) {
                    message = getManyNumbersProperties(number);
                    System.out.println(message);
                    number = number.add(BigInteger.ONE);
                    counter--;
                }
            } else {
                propertiesArray = Arrays.copyOfRange(inputArray, 2, inputArray.length);
                if (checkProperties(propertiesArray) && !isMutuallyExclusive(propertiesArray)) {
                    while (counter > 0) {
                        while (!hasNumberProperties(propertiesArray, number)) {
                            number = number.add(BigInteger.ONE);
                        }
                        message = getManyNumbersProperties(number);
                        System.out.println(message);
                        number = number.add(BigInteger.ONE);
                        counter--;
                    }
                }
            }
        }
    }


    private static boolean hasNumberProperties(String[] propertiesArray, BigInteger number) {
        for (String property : propertiesArray) {
            if (!hasNumberProperty(property, number)) {
                return false;
            }
        }
        return true;
    }


    private static void start() {
        printMenu();
        String[] inputArray = input();

        while (!inputArray[0].equals("0")) {
            checkResult(inputArray);
            inputArray = input();
        }
        System.out.println("\nGoodbye!");
    }


    private static boolean isNatural(BigInteger number) {
        return number.compareTo(BigInteger.ZERO) > 0;
    }

    private static boolean isEven(BigInteger number) {
        return number.remainder(BigInteger.TWO).equals(BigInteger.ZERO);
    }


    private static boolean isOdd(BigInteger number) {
        return number.remainder(BigInteger.TWO).equals(BigInteger.ONE);
    }


    private static boolean isBuzz(BigInteger number) {
        return number.remainder(BigInteger.valueOf(7)).equals(BigInteger.ZERO) || number.remainder(BigInteger.valueOf(10)).equals(BigInteger.valueOf(7)) || number.equals(BigInteger.valueOf(7));
    }


    private static boolean isDuck(BigInteger number) {
        while (!number.equals(BigInteger.ZERO)) {
            if (number.remainder(BigInteger.valueOf(10)).equals(BigInteger.ZERO)) {
                return true;
            } else {
                number = number.divide(BigInteger.valueOf(10));
            }
        }
        return false;
    }


    private static boolean isPalindromic(String number) {
        StringBuilder tempString = new StringBuilder(number);
        return number.equals(tempString.reverse().toString());
    }


    private static boolean isGapful(BigInteger number) {
        String tempNumber = String.valueOf(number);
        String divider = "" + tempNumber.charAt(0) + tempNumber.charAt(tempNumber.length() - 1);

        return tempNumber.length() >= 3 && number.remainder(BigInteger.valueOf(Long.parseLong(divider))).equals(BigInteger.ZERO);
    }


    private static boolean isSpy(BigInteger number) {
        int sum = 0;
        int product = 1;
        String num = number.toString();

        for (int i = 0; i < num.length(); i++) {
            sum += Character.getNumericValue(num.charAt(i));
            product *= Character.getNumericValue(num.charAt(i));
        }

        return sum == product;
    }


    private static boolean isSquare(BigInteger number) {
        return number.sqrtAndRemainder()[1].equals(BigInteger.ZERO);
    }


    private static boolean isSunny(BigInteger number) {
        return isSquare(number.add(BigInteger.ONE));
    }


    private static boolean isJumping(String number) {
        int predecessorDigit;
        int digit;
        int successorDigit;
        if (number.length() == 1) {
            return true;
        } else if (number.length() == 2) {
            for (int i = 1; i < number.length(); i++) {
                predecessorDigit = number.charAt(0);
                digit = number.charAt(i);
                if (digit != (predecessorDigit - 1) && digit != (predecessorDigit + 1)) {
                    return false;
                }
            }
        } else {
            for (int i = 1; i < number.length() - 1; i++) {
                predecessorDigit = number.charAt(i - 1);
                digit = number.charAt(i);
                successorDigit = number.charAt(i + 1);
                if ((digit != (predecessorDigit - 1) && digit != (predecessorDigit + 1)) || (digit != (successorDigit - 1) && digit != (successorDigit + 1))) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isHappy(String number) {
        String tempNumber = "0";
        for (int i = 0; i < number.length(); i++) {
            char ch = (char) Character.getNumericValue(number.charAt(i));
            tempNumber = String.valueOf(Integer.parseInt(tempNumber) + (ch * ch));
        }
        if (tempNumber.length() == 1) {
            return tempNumber.equals("1");
        }
        return isHappy(tempNumber);
    }


    public static void main(String[] args) {

       start();

    }
}
