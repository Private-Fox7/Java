import java.util.Scanner;

class EmailValidation {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your email address: ");
        String email = sc.nextLine();
        email = email.trim();
        email = email.replace(" ", "");
        if (!email.contains("@")) {
            System.out.println("Invalid Email: @ is missing");
            return;
        }
        String domain = email.substring(email.indexOf("@") + 1);
        domain = domain.toLowerCase();
        System.out.println("Cleaned Email: " + email);
        System.out.println("Domain: " + domain);
        if (domain.endsWith(".com") ||
            domain.endsWith(".org") ||
            domain.endsWith(".in")) {

            System.out.println("Valid Email Address");
        } 
        else {
            System.out.println("Invalid Email: Domain must end with .com, .org or .in");
        }

        sc.close();
    }
}