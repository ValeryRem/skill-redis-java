package com.skillbox.redisdemo;

import org.redisson.api.RScoredSortedSet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static java.lang.System.out;
import static java.lang.System.setOut;

public class RedisTest {

    // Запуск докер-контейнера:
    // docker run --rm --name skill-redis -p 127.0.0.1:6379:6379/tcp -d redis

    // Для теста будем считать неактивными пользователей, которые не заходили 2 секунды
//    private static final int DELETE_SECONDS_AGO = 2;

    // Допустим пользователи делают 500 запросов к сайту в секунду
//    private static final int RPS = 500;

    // И всего на сайт заходило 1000 различных пользователей
    private static  int usersNumber;
    private static final int REQUESTS = 50;

    // Также мы добавим задержку вывода в лог пользователя, следующего пo списку
    private static final int SLEEP = 100; // 1 секунда
//    private static final SimpleDateFormat DF = new SimpleDateFormat("HH:mm:ss");
//    private static void log(int UsersOnline) {
//        String log = String.format("[%s] Пользователей онлайн: %d", DF.format(new Date()), UsersOnline);
//        out.println(log);
//    }
    private static final RedisStorage redisStorage = new RedisStorage();

    private static void tryPopUpMember() {
        int popUp = 1 + new Random().nextInt(9);
        if (popUp == 1) {
            int user_id = 1 + new Random().nextInt(redisStorage.calculateUsersNumber() - 1);
            redisStorage.logPageVisit(user_id);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        redisStorage.init();
        for (int i = 0; i < REQUESTS; i++) {
            for (String member : redisStorage.getOnlineUsers()) {
                tryPopUpMember();
                out.println(member); //redisStorage.getOnlineUsers().first());
                redisStorage.getOnlineUsers().remove(member);// (redisStorage.getOnlineUsers().first());
                redisStorage.getOnlineUsers().add(redisStorage.getTs(), member);
                Thread.sleep(SLEEP);
            }
            out.println(redisStorage.calculateUsersNumber());
        }
//        int usersOnline = redisStorage.calculateUsersNumber();
//        // Эмулируем 10 секунд работы сайта
////        for(int seconds=0; seconds <= 10; seconds++) {
//// Выполним 500 запросов
//        for (int i = 0; i < REQUESTS; i++) {
//            for (int requestId = 0; requestId <= usersOnline - 1; requestId++) {
//                redisStorage.logPageVisit(requestId);
//                int popUp = new Random().nextInt(10);
//                if (popUp == 1) {
//                    int user_id = new Random().nextInt(usersNumber);
//                    redisStorage.logPageVisit(user_id);
//                    // для элемента с user_id = requestId (порядковым номером) вводим новый балл по текущему времени, сек
//                    redisStorage.getOnlineUsers().add(redisStorage.getTs(), redisStorage.getOnlineUsers().entryRange(requestId,
//                            requestId);
//                    // для элемента с данным user_id (порядковым номером) вводим новый балл по текущему времени, сек
//                    redisStorage.getOnlineUsers().add(redisStorage.getTs(), redisStorage.getOnlineUsers().entryRange(user_id,
//                            user_id);
//                }
//
//            }
////            redisStorage.deleteOldEntries(DELETE_SECONDS_AGO);
//            Thread.sleep(SLEEP);
//            log(usersOnline);
//            out.println(redisStorage.getOnlineUsers().first());
//        }
        redisStorage.shutdown();
    }
}
