class Bird {
    void fly() {
        System.out.println("I am a Bird");
    }
}

class Parrot extends Bird {
    void whatColourAmI() {
        System.out.println("I am green!");
    }
}

class SingingParrot extends Parrot {
    void whatCanISing() {
        System.out.println("I can sing Opera!");
    }
}

class Main {
    public static void main(String args[]) {

        SingingParrot obj = new SingingParrot();

        obj.whatCanISing();
        obj.whatColourAmI();
        obj.fly();
    }
}