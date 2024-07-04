import java.util.Scanner;

public class CommandLineCalculator 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Command-Line Calculator");
        System.out.println("Enter 'exit' to quit the calculator.");

        while (true) {
            System.out.print("Enter first number: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            double num1;
            try {
                num1 = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            System.out.print("Enter an operator (+, -, *, /, ^ for power, sqrt for square root): ");
            String operator = scanner.nextLine();

            double num2 = 0;
            if (!operator.equals("sqrt")) {
                System.out.print("Enter second number: ");
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) {
                    break;
                }
                try {
                    num2 = Double.parseDouble(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    continue;
                }
            }

            double result;
            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        System.out.println("Error: Division by zero is not allowed.");
                        continue;
                    }
                    break;
                case "^":
                    result = Math.pow(num1, num2);
                    break;
                case "sqrt":
                    if (num1 >= 0) {
                        result = Math.sqrt(num1);
                    } else {
                        System.out.println("Error: Square root of a negative number is not allowed.");
                        continue;
                    }
                    break;
                default:
                    System.out.println("Error: Invalid operator.");
                    continue;
            }

            System.out.println("Result: " + result);
        }

        System.out.println("Calculator exited.");
        scanner.close();
    }
}
