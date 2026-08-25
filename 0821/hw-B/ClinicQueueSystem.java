import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class Patient {
    private final String id;   
    private final String name; 

    public Patient(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + name;
    }
}

public class ClinicQueueSystem {
  
}
