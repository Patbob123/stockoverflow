package entities;

public class Simulation {
    private final String name;

    public Simulation(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name; // shows nicely in the dropdown
    }
}


