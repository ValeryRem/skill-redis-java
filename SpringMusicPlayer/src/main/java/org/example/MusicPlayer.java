package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class MusicPlayer {

    @Value("${musicPlayer.name}")
    private String name;

//    @Value("${musicPlayer.volume}") // при этой аннотации уровень звука берется из musicPlayer.properties
    private int volume;

    private final List<Music> musicList;

    public MusicPlayer(List<Music> musicList) {
        this.musicList = musicList;
    }

    public String getName() {
        return name;
    }
    private final Random random = new Random();

    public int getVolume() {
        int mVolume = 100;
         return random.nextInt(mVolume);
    }

    public String playMusic() {
        volume = getVolume();
        return getName() + "\nPlaying: " + musicList.get(random.nextInt(musicList.size())).getSong()
                + "\nwith volume " + volume;
    }
}