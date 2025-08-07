package ru.job4j;

public class Singleton {
    private static volatile Singleton instance;    //горантирует видимость изменений

    private Singleton() {}

    public static Singleton getInstance() {

        synchronized (Singleton.class) {      //исправленна гонка потоков,блокировка по классу
            if (instance == null) {
                instance = new Singleton();
            }
        }

        return instance;
    }
}
