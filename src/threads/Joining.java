package threads;

public class Joining {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Zacina main vlakno..");
        //v tom je schovano new Thread(new Runnable()) lambdou
        Thread first = new Thread(() ->{
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(3000+(int)(Math.random()*3000));
            } catch (InterruptedException e) {
                System.out.println("Thread died :(");
            }
            System.out.println(Thread.currentThread().getName() + " is done");
        });
        first.setName("Albert");


        Thread second = new Thread(() ->{
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(3000+(int)(Math.random()*3000));
            } catch (InterruptedException e) {
                System.out.println("Thread died :(");
            }
            System.out.println(Thread.currentThread().getName() + " is done");
        });
        second.setName("Patrick");

        System.out.println("Race start:");
        first.start();
        second.start();

        System.out.println("main is done");
        System.out.println(first.getName() + ": " + first.getState());
        first.interrupt();
        System.out.println(second.getName() + ": " + second.getState());
        System.out.println(first.getName() + ": " + first.getState());

        //pockame nez budou vsici hotovi
        first.join();
        System.out.println(first.getName() + ": " + first.getState());
        second.join();
        System.out.println(second.getName() + ": " + second.getState());


        System.out.println("Happy ending");
    }
}
