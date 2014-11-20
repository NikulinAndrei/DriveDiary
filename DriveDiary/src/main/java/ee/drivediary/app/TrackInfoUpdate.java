package ee.drivediary.app;

/**
 * Created with IntelliJ IDEA.
 * User: AndreiN
 * Date: 20.11.2014
 */
class TrackInfoUpdate {
  public TrackInfoUpdate(double distance) {
    this.distance = distance;
  }

  private final double distance ;

  public double getDistance() {
    return distance;
  }

  @Override
  public String toString() {
    return "TrackInfoUpdate{" +
        "distance=" + distance +
        '}';
  }
}
