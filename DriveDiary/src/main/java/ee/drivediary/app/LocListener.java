package ee.drivediary.app;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: AndreiN
 * Date: 17.11.2014
 */
public class LocListener implements LocationListener {
  public static final String TAG="LocListener";

  private RecordingActivity recordingActivity;
  private final Date startDate = new Date();
  private Location previousLocation = null;
  private Double calculatedLength = 0D;

  public LocListener(RecordingActivity recordingActivity) {
    this.recordingActivity = recordingActivity;
  }

  @Override
  public void onLocationChanged(Location location) {
    Log.d(TAG, location.toString());
    if(previousLocation != null) {
      calculatedLength += (double) location.distanceTo(previousLocation);
      recordingActivity.updateRecord(
          new TrackRecord(startDate,
              new Date(location.getTime()),
              calculatedLength));
    }
    previousLocation = location;
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
    Log.i(TAG, "Status is changed " + provider);
  }

  @Override
  public void onProviderEnabled(String provider) {
    Log.i(TAG, "Provider is enabled " + provider);
  }

  @Override
  public void onProviderDisabled(String provider) {
    Log.i(TAG, "Provider is disabled " + provider);
  }
}
