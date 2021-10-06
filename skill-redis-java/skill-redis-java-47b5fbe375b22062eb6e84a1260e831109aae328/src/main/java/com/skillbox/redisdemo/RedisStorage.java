package com.skillbox.redisdemo;

import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.util.Date;
import java.util.SortedSet;

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
        return Double.longBitsToDouble(new Date().getTime());
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
            onlineUsers = redissonClient.getScoredSortedSet(KEY);
            onlineUsers.removeAll(onlineUsers.readAll());
//            onlineUsers.removeRangeByScore(onlineUsers.firstScore(), true, onlineUsers.lastScore(), true); // Очищаем множество от остатков
            onlineUsers.add(getTs(), "User_1");
            onlineUsers.add(getTs(), "User_2");
            onlineUsers.add(getTs(), "User_3");
            onlineUsers.add(getTs(), "User_4");
            onlineUsers.add(getTs(), "User_5");
            onlineUsers.add(getTs(), "User_6");
            onlineUsers.add(getTs(), "User_7");
            onlineUsers.add(getTs(), "User_8");
            onlineUsers.add(getTs(), "User_9");
            onlineUsers.add(getTs(), "User_10");
            onlineUsers.add(getTs(), "User_11");
            onlineUsers.add(getTs(), "User_12");
            onlineUsers.add(getTs(), "User_13");
            onlineUsers.add(getTs(), "User_14");
            onlineUsers.add(getTs(), "User_15");
            onlineUsers.add(getTs(), "User_16");
            onlineUsers.add(getTs(), "User_17");
            onlineUsers.add(getTs(), "User_18");
            onlineUsers.add(getTs(), "User_19");
            onlineUsers.add(getTs(), "User_20");
        } catch (RedisConnectionException Exc) {
            out.println("Не удалось подключиться к Redis");
            out.println(Exc.getMessage());
        }
//        rKeys = redissonClient.getKeys();

//        rKeys.delete(KEY); // Why?
    }

    public RScoredSortedSet<String> getOnlineUsers() {
        return onlineUsers;
    }
    void shutdown() {
        redissonClient.shutdown();
    }

    // Фиксирует посещение пользователем страницы
    void logPageVisit(int user_id)
    {
        out.println("User_" + user_id + " popped up");
        onlineUsers.removeRangeByScore(user_id, true, user_id, true);
//        onlineUsers.remove("User_" + user_id);
        onlineUsers.add(getTs(), "User_" + user_id);
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
