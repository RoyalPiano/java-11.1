public class Main {
    static int count = 0;
    private static StateObject stateObject;

    public static class StateObject {
        private int x;
        private int count;

        public synchronized int getCount() {
            return count;
        }

        public synchronized void run() {
            while (x < 999999) {
                x++;
                if ((x % 10) + (x / 10) % 10 + (x / 100) % 10 == (x / 1000)
                        % 10 + (x / 10000) % 10 + (x / 100000) % 10) {
                    System.out.println(x);
                    count++;
                }
            }
        }
    }

    static class LuckyThread extends Thread {
        public LuckyThread(StateObject object) {
            stateObject = object;
        }

        @Override
        public void run() {
            stateObject.run();
            count = stateObject.getCount();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        StateObject stateObject = new StateObject();
        Thread t1 = new LuckyThread(stateObject);
        Thread t2 = new LuckyThread(stateObject);
        Thread t3 = new LuckyThread(stateObject);
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        System.out.println("Total: " + count);
    }
}
