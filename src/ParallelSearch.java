import java.util.concurrent.CountDownLatch;

public class ParallelSearch {
    private static String[] arr = {"Sunny", "Smokey", "Rain", "Cloudy", "Sunny", "Overcast", "Snowy", "ThunderStrike"};
    private static int NUMBER_OF_THREADS = 2;
    private static String weather = "ThunderStrike";
    private static int position = -1;
    private static CountDownLatch countDownLatch = new CountDownLatch(NUMBER_OF_THREADS);

    public static void main(String[] args) {
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            Thread thread = new Thread(new Worker(weather, i * (arr.length - 1) / 2, (i + 1) * (arr.length) / 2));
            thread.start();
        }
        try {
            countDownLatch.await();
            if (position >= 0) {
                System.out.println("The position of " + weather + " is " + position);
            } else {
                System.out.println("Not Found ");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Worker implements Runnable {
        private final String weather;
        private final int left, right;

        public Worker(String weather, int left, int right) {
            this.weather = weather;
            this.left = left;
            this.right = right;
        }

        @Override
        public void run() {
            for (int i = left; i < right; i++) {
                if (arr[i] == weather) {
                    position = i;
                }
            }
            countDownLatch.countDown();
        }
    }
}
