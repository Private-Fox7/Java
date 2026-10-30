public class NoArgsConstruct {
    int id;
    String name;

    NoArgsConstruct() {
        id = 1012;
        name = "Mary Jane";
    }

    void display()
    {
        System.out.println("id:" + id);
        System.out.println("name:" + name);
    }
    public static void main(String[] args) {
        NoArgsConstruct s1 = new NoArgsConstruct();
        NoArgsConstruct s2 = new NoArgsConstruct();
        s1.display();
        s2.display();
    }
    

}
