public class Timer {
    private long start, spent = 0;
    public Timer() {
    play();
    }

    //Checks how much time has currently passed since we started
    public double check() {
    return (System.nanoTime() - start + spent) / 1e9;
    }
    //Updates the spent variable with the time passed since start until now
    public void pause() {
    spent += System.nanoTime() - start;
    }
    //Assign the current local time to the variable of start
    public void play() {
    start = System.nanoTime();
    }
    }
