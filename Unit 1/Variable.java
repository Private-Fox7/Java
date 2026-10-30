class Variable
{
    int instancevariable = 10;
    static int classvariable = 20;
    public static void main(String[] args) {
        int localvariale=0;
        Variable v = new Variable();
        Variable v2 = new Variable();
        System.out.println("Static or class variable " + classvariable);
        System.out.println("local variable " + localvariale);
        System.out.println("instance variable of obj v" + v.instancevariable);
        System.out.println("instance variable of obj v2 " + ++v2.instancevariable);
        System.out.println(v.instancevariable

        );

    }

}