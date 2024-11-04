import java.util.InputMismatchException;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice = -1;
        try {
            choice = sc.nextInt(); 
            sc.nextLine();
            
        } catch (InputMismatchException e) {
            System.out.println("Enter valid ");
            sc.nextLine();

        }
        switch (choice) {

            case 1:
                
                break;
        
            default:
                break;
        }
    }
}
