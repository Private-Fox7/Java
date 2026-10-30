public class ProductMain {
    public static void main(String[] args) {
        String name = "Rice";
        int qtty = 20, prices = 25;
        Product p1 = new Product(name, prices, qtty);
        Product p2 = new Product();
        p2.display();        
        p1.display();
    }
    
}
