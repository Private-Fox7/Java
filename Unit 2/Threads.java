class A extends Thread
{
public void run()// running state
{
for(int i=0;i<20;i++)
System.out.println("A");
try{
Thread.sleep(2);
}
catch(InterruptedException e)
{
}}}
class B extends Thread
{
public void run()
{
for(int i=0;i<20;i++)
System.out.println("B");
try{
Thread.sleep(2);
}
catch(InterruptedException e)
{
}}}
public class Threads
{
public static void main(String args[])
{
A obja=new A();
B objb=new B();
//System.out.println(obja.getPriority());
obja.start();
objb.start();// runnable state
}
}