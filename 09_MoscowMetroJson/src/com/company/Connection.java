package com.company;
import java.util.List;

public class Connection {
    List<Station> transferHub;
    Station prime;

    public Connection() {
    }

    public Connection (Station prime, List<Station> transferHub) {
        this.prime = prime;
        this.transferHub = transferHub;
    }
}

