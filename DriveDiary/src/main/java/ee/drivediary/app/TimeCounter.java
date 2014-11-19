package ee.drivediary.app;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: AndreiN
 * Date: 17.11.2014
 */
class TimeCounter{

  private Timer timer;
  private RecordingActivity recordingActivity;

  public TimeCounter(RecordingActivity recordingActivity) {
    this.recordingActivity = recordingActivity;
  }


  private void notifyTimeElapsed( long millis ) {
    recordingActivity.updateTimeElapsed(millis / 1_000);
  }

  public void start(){
    timer = new Timer();
    timer.schedule(new TickerTask(), 1_000, 1_000);
  }

  public void stop() {
    timer.cancel();
  }

  private class TickerTask extends  TimerTask {
    private long startTime = System.currentTimeMillis();

    public void run() {
      Log.i("Timer", "Tick");
      notifyTimeElapsed(System.currentTimeMillis() - startTime);
    }
  };
}