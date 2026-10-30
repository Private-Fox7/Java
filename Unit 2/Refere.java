abstract class Teacher
    {
        protected String name;
        protected String id;
        protected String mail;
        String Studentname;
        String Sid;
        Teacher ()
        {
            name = "James Matheev";
            id = "2145james";
            mail = "jamesMatheev2145@gmail.com";
        }
        abstract public void show();
        abstract public void referer(String s,int id);
    }

class Student extends Teacher
{
        @Override 
        public void show()
        {
            System.out.println("The details of the Teacher is: " + name);
            System.out.println("The details of the Teacher id: " + id);
            System.out.println("The details of the Teacher mail: " + mail);
        }
        @Override 
        public void referer(String s,int id)
        {
            System.out.println("Student : "+s);
            System.out.println("Student id: "+id);
        }
    }

class Refere
    {
        public static void main(String []args)
        {
            Teacher tt =new Student();
            tt.show();
            tt.referer("Mary",2426);
        }
    }
