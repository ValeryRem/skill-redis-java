package com.skillbox.redisdemo;

import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.util.Date;

import static java.lang.System.out;

public class RedisStorage {
    // Объект для работы с Redis
    private RedissonClient redissonClient;

    // Объект для работы с ключами
//    private RKeys rKeys;

    // Объект для работы с Sorted Set'ом
    private RScoredSortedSet<String> onlineUsers;

    private final static String KEY = "ONLINE_USERS";

    public double getTs() {
        return new Date().getTime() / 1000;
    }

    public RScoredSortedSet<String> getOnlineUsers() {
        return onlineUsers;
    }
    // Пример вывода всех ключей
//    public void listKeys() {
//        Iterable<String> keys = rKeys.getKeys();
//        for(String key: keys) {
//            out.println("KEY: " + key + ", type:" + rKeys.getType(key));
//        }
//    }

    void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redissonClient = Redisson.create(config);
        } catch (RedisConnectionException Exc) {
            out.println("Не удалось подключиться к Redis");
            out.println(Exc.getMessage());
        }
//        rKeys = redissonClient.getKeys();
        onlineUsers = redissonClient.getScoredSortedSet(KEY);
//        rKeys.delete(KEY); // Why?
    }

    void shutdown() {
        redissonClient.shutdown();
    }

    // Фиксирует посещение пользователем страницы
    void logPageVisit(int user_id)
    {
        //ZADD ONLINE_USERS
        onlineUsers.add(getTs(), String.valueOf(user_id));
    }

    // Удаляет
//    void deletePopUpUser(int user_id)
//    {
//        //ZREVRANGEBYSCORE ONLINE_USERS 0 <time_5_seconds_ago>
//        onlineUsers.removeRangeByScore(user_id, true, user_id, true);
//        logPageVisit(user_id);
//    }
    int calculateUsersNumber()
    {
        //ZCOUNT ONLINE_USERS
        return onlineUsers.count(Double.NEGATIVE_INFINITY, true, Double.POSITIVE_INFINITY, true);
    }
//    void deleteOldEntries(int secondsShift)
//    {
//        onlineUsers.removeRangeByScore(getTs() - secondsShift, true, getTs(), true);
//    }
}
