package playSound;

public class MusicPlayer implements Runnable {
    private Thread thread;
    private volatile boolean isRunning = false;
    private MyPlayer mp;

    public MusicPlayer(MyPlayer player) {
        mp = player;
        thread = new Thread(this);
    }

    public void start() {
        isRunning = true;
        thread.start();
    }

    public void stopMusic() {
        mp.stop();  // MyPlayer의 stop 메소드 호출
        thread.interrupt();
    }

    @Override
    public void run() {
        while (isRunning && !Thread.currentThread().isInterrupted()) {
            mp.Play();
        }
    }
}
