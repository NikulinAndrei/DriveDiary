package ee.drivediary.cli;

import android.nfc.Tag;
import android.util.Log;
import ee.drivediary.app.TrackRecord;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created with IntelliJ IDEA.
 * User: AndreiN
 * Date: 19.11.2014
 */
public class AsyncClient {
  public static final String TAG ="AsyncClient";
  ExecutorService executorService = Executors.newSingleThreadExecutor();
  ServerCli serverCli = new ServerCli();

  public void tryToSendData(final TrackRecord trackRecord){
    executeInBackground(
        new Runnable() {
          @Override public void run() {
            try {
              serverCli.sendData(trackRecord);
            }
            catch (Throwable e){
              Log.e(TAG,"Error syncing data to server", e) ;
            }
          }
        }
    );
  }

  private void executeInBackground( Runnable runnable){
    executorService.submit( runnable );
  }

}
