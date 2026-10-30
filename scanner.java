import java.util.*;

class scanner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name ");
        String name = sc.nextLine();
        System.out.println("Enter your Srn");
        String srn = sc.nextLine();
        System.out.println("Enter your address ");
        String address = sc.nextLine();
        System.out.println("Enter your age ");
        int age = sc.nextInt();
        System.out.println("-------------The data entered is ---------------");
        System.out.println("Name :" + name);
        System.out.println("Srn :" + srn);
        System.out.println("address :" + address);
        System.out.println("age :" + age);
        sc.close();
    }
}