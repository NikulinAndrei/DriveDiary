package ee.drivediary.common;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: AndreiN
 * Date: 19.11.2014
 *
 */
public class TrackRecord {
  private String carNr;
  private Date startedAt;
  private Date endedAt;
  private Long lengthInMeters;

  public String getCarNr() {
    return carNr;
  }

  public void setCarNr(String carNr) {
    this.carNr = carNr;
  }

  public Date getStartedAt() {
    return startedAt;
  }

  public void setStartedAt(Date startedAt) {
    this.startedAt = startedAt;
  }

  public Date getEndedAt() {
    return endedAt;
  }

  public void setEndedAt(Date endedAt) {
    this.endedAt = endedAt;
  }

  public Long getLengthInMeters() {
    return lengthInMeters;
  }

  public void setLengthInMeters(Long lengthInMeters) {
    this.lengthInMeters = lengthInMeters;
  }
}
