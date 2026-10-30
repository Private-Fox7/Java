class Demovar
{
    int a;

    public Demovar() //No args constructor
    {
        a = 0;
    }

}
class Staticvar
{
    public static void main (String []args)
    {
        Demovar v1 = new Demovar();
        Demovar v2 = new Demovar();
        v1.a = 22;
        System.out.println(v1.a); 
        System.out.println(v2.a); 
    }
}