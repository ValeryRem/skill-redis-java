package com.company;
import java.util.List;

public class Connection {
    List<String> transferHub;
    Station prime;

    public Connection() {
    }

    public Connection (Station prime, List<String> transferHub) {
        this.prime = prime;
        this.transferHub = transferHub;
    }
}

