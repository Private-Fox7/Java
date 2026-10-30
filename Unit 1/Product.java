public class Product
{
    String pname;
    int price, qty;

    Product()
    {
        price = 200;
        pname = "Empty";
        qty = 25;

    }

    public Product(String n,int p,int qt) {
        pname = n;
        price = p;
        qty = qt;
    }
    
    void display()
    {
        System.out.println("Total cost of the Product "+pname+" :"+price*qty );
    }

}