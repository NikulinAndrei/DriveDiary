package ee.drivediary.app;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;

/**
 * Created with IntelliJ IDEA.
 * User: AndreiN
 * Date: 18.11.2014
 */
public class LocManager {

  private LocationManager locationManager;
  private LocationListener locationListener ;
  private RecordingActivity activity;

  public LocManager(RecordingActivity activity){
    this.activity = activity;
    locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
  }

  public void start(){
    locationListener = new LocListener( activity);
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3, 5, locationListener);
  }

  public void stop(){
    locationManager.removeUpdates( locationListener);
    locationListener = null;
  }
}
