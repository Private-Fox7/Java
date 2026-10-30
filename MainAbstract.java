abstract class College
{
    String collegeName = "PES University";
    abstract void department();
}

class Computer extends College {
    @Override
    void department() {
        System.out.println("Welcome to the Computer Science department");
    }
}

class Ece extends College {
    @Override
    void department()
    {
        System.out.println("Welcome to the Electronics department");
    }
}
class MainAbstract{
    public static void main(String []args)
    {
        College c = new Computer();
        System.out.println(c.collegeName);
        c.department();
        College e = new Ece();
        e.department();
    }
}