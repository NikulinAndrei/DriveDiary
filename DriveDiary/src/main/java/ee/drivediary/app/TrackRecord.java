package ee.drivediary.app;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: AndreiN
 * Date: 17.11.2014
 */
public class TrackRecord {
  private final Date startTime;
  private final Date endTime;
  private final Double trackLength;

  public TrackRecord(Date startTime, Date endTime, Double trackLength) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.trackLength = trackLength;
  }

  public Date getStartTime() {
    return startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public Double getTrackLength() {
    return trackLength;
  }

  public long getTrackLengthInKm() {
    return (long)( trackLength / 1000 );
  }

  public int getTrackTime() {
    return (int) ((endTime.getTime() - startTime.getTime())/1000);
  }

  @Override
  public String toString() {
    return new SimpleDateFormat("dd.MM.yyyy HH:mm").format(startTime)
        +" / "  + getTrackTime() + " s / "
        + getTrackLengthInKm() +" km"  ;
  }
}
