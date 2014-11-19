package ee.drivediary.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: AndreiN
 * Date: 17.11.2014
 */
public class TrackRecord implements Parcelable {
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

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong( startTime.getTime());
    dest.writeLong( endTime.getTime());
    dest.writeDouble(trackLength);
  }

  public static final Parcelable.Creator<TrackRecord> CREATOR
      = new Parcelable.Creator<TrackRecord>() {
    public TrackRecord createFromParcel(Parcel in) {
      return new TrackRecord(in);
    }

    public TrackRecord[] newArray(int size) {
      return new TrackRecord[size];
    }
  };

  private TrackRecord(Parcel parcel) {
    this(
        new Date(parcel.readLong()),
        new Date(parcel.readLong()),
        parcel.readDouble()
    );
  }
}
