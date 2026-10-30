class AccessData {
    public int pubVar = 10;
    protected int proVar = 20;
    int defVar = 30;         
    private int priVar = 40;
    public void showData() {
        System.out.println("Public: " + pubVar);
        System.out.println("Protected: " + proVar);
        System.out.println("Default: " + defVar);
        System.out.println("Private: " + priVar);
    }
}
public class Demo {
    public static void main(String[] args) {
        AccessData obj = new AccessData();
        System.out.println("Outside AccessData class:");
        System.out.println("Public: " + obj.pubVar);
        System.out.println("Protected: " + obj.proVar);
        System.out.println("Default: " + obj.defVar);
        System.out.println("\nInside AccessData using method:");
        obj.showData();
    }
}