package ee.drivediary.cli;


import android.util.Log;
import ee.drivediary.common.TrackRecord;
import org.springframework.http.*;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created with IntelliJ IDEA.
 * User: AndreiN
 * Date: 19.11.2014
 */
 class ServerCli {
  public static final String TAG = "ServerCli";

  public boolean sendData( ee.drivediary.app.TrackRecord data ) {
    Log.i(TAG, "sending data to server: "+ data);
    TrackRecord payload = new TrackRecord();
    payload.setCarNr("APN 346");
    payload.setEndedAt(data.getEndTime());
    payload.setStartedAt(data.getStartTime());
    payload.setLengthInMeters(data.getTrackLength().longValue());

    // Create a new RestTemplate instance
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setContentType(new MediaType("application","json"));
    HttpEntity<TrackRecord> requestEntity = new HttpEntity<>(payload, requestHeaders);

    //restTemplate.getMessageConverters().add( new GsonHttpMessageConverter() );

  // Make the HTTP POST request, marshaling the request to JSON, and the response to a String
    ResponseEntity<String> response = restTemplate.exchange(
          "http://10.0.3.2:8080/track/saveNewRecord",
          HttpMethod.POST,
          requestEntity,
          String.class);

    return true;
  }
}
