interface Vehicle {
    default void start() {
        System.out.println("Vehicle starting...");
    }
}

interface Car extends Vehicle {
    default void start() {
        System.out.println("Car starting...");
    }
}