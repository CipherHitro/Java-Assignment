
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

public class DateInputExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a date (dd-MM-yyyy): ");
        String inputDate = scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        LocalDate date = null;
        try {
            date = LocalDate.parse(inputDate, formatter);
        } 
        catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
        }
        
        LocalDate dateNow = LocalDate.now();

        System.out.println(dateNow);
        if (date.isBefore(dateNow)) {
            System.out.println("Invalid date. The date cannot be in the past. Please enter today or a future date.");
        } 
        else {
            System.out.println("Today's date: " + dateNow.format(formatter));
            System.out.println("The date entered is: " + date.format(formatter));
            
            // Display the date in a different format
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
            System.out.println("Formatted date: " + date.format(outputFormatter));
        }
    }
}
