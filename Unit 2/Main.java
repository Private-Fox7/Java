import java.util.*;
class Main{
    public static void main(String[] args) {
        System.out.println("Enter the data:");
        Scanner sc = new Scanner(System.in);
        try {
            int a = Integer.parseInt(sc.next());
            System.out.println("The entered number is :" + a);
        } catch (Exception e) {
            System.out.println("Error occured"+e);
        }
         
    }
}