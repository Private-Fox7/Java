public class Staticvariable {
static int stude_Counter;
String stud_name;
int Stud_id;
public static void main(String[] args) {
    Staticvariable v= new Staticvariable();
    v.stud_name= "Sam";
    v.Stud_id = 1;
    ++v.stude_Counter;
    //Second obj
    Staticvariable v2 = new Staticvariable();
    v2.stud_name = "Joey";
    v2.Stud_id = 2;
    ++v2.stude_Counter;

    System.out.println("v.studname: "+v.stud_name);
    System.out.println("v.stud_id: "+v.Stud_id);
    System.out.println("v.stude_counter: "+v.stude_Counter);
    System.out.println("v1.studname: "+v2.stud_name);
    System.out.println("v2.stude_counter: "+v2.Stud_id);
    System.out.println("v2.stude_counter: "+v2.stude_Counter);
}    
}
