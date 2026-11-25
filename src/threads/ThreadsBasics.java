package threads;

import java.io.*;
import java.util.Comparator;

public class ThreadsBasics {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Neco se stane za 3 vteriny...");
        Thread.sleep(3000);
        System.out.println("neco");

        System.out.println(Thread.activeCount());
        System.out.println(Thread.currentThread());
        FirstThread fred = new FirstThread();
        fred.setName("Prvni_vlakno");
        fred.start();
        System.out.println("Nyni by se neco melo dit jinde...");
        System.out.println(Thread.activeCount());

        Runnable alfred = () -> {
          //nyni jsme v run()
            System.out.println(Thread.currentThread().getName());
            try {
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(Thread.currentThread().getName() + ".txt")));
                for (int i = 0; i < 50000000; i++) {
                    pw.println(Thread.currentThread().getName() + ": " + i);
                }
                pw.close();
            } catch (IOException e) {
                System.out.println("Thread died :(");
            }
            System.out.println(Thread.currentThread().getName() + " is finished");
        };
        Thread ovladacAlfreda = new Thread(alfred);
        ovladacAlfreda.setName("Alfred");
        ovladacAlfreda.start();
        System.out.println("Hlavni vlakno done, Happy ending :)");
    }
}
class FirstThread extends Thread{
    @Override
    public void run() {
        System.out.println(getName());
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(getName() + ".txt")));
            for (int i = 0; i < 50000000; i++) {
                pw.println(getName() + ": " + i);
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Thread died :(");
        }
        System.out.println(getName() + " is finished");
    }
}
