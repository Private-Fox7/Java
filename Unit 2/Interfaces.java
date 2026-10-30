class A implements Runnable
    {
        public void run()
        {
            for (int i=0;i<20;i++)
                System.out.print("a");
        }
        
    }

class B implements Runnable
    {
        public void run()
        {
            for (int i=0;i<20;i++)
                System.out.print("b");
        }
        
    }

class Interfaces
    {
        public static void main(String [] args)

        {
            Runnable obj1 = new A();
            Runnable obj2 = new B();
            Thread a = new Thread(obj1);
            Thread b = new Thread(obj2);
            a.start();            
            b.start();            
            
        }
    }