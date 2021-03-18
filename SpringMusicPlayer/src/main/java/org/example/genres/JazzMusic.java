package org.example.genres;

import org.example.Music;

import java.util.ArrayList;
import java.util.List;

public class JazzMusic implements Music {
    @Override
    public String getSong() {
        return "Take five Jazz";
    }
}
