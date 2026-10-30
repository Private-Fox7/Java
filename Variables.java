class Variables
{
    int instanceVariable=10;
    static int staticVariable=20;
    public static void main(String args[]){
    int LocalVariable=30;
    Variables v=new Variables();
    System.out.println("Static variable:" + staticVariable);
    System.out.println("instance variable:" + v.instanceVariable);
    System.out.println("local variable:" + LocalVariable);


    }
}