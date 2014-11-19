package ee.drivediary.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import ee.drivediary.R;
import ee.drivediary.db.TrackDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Arrays.asList;


public class RecordingActivity extends ActionBarActivity {
    public static final String TAG = "RecordingActivity";

    volatile boolean started = false;

    EditText trackDistance;
    EditText trackTime;
    TimeCounter elapsedTimeCounter;

    // Acquire a reference to the system Location Manager
    LocManager locationManager;

    private ArrayAdapter<TrackRecord> adapter;
    private List<TrackRecord> records = new ArrayList<>(10);
    private AtomicReference<TrackRecord> currentRecord = new AtomicReference<>();

    // Local Databas Storage Facilities
    private TrackDAO trackDAO;


    public void toggleRecording( View view ){
        Button recButton =  ((Button)view) ;
        if(started)
            stopRecording( recButton );
        else
            startRecording(recButton);
    }

    private void stopRecording(Button recButton) {
        locationManager.stop();
        elapsedTimeCounter.stop();
        TrackRecord lastReading = currentRecord.getAndSet(null);
        if(lastReading != null) {
            records.add(lastReading);
            adapter.notifyDataSetChanged();
            Log.i(TAG, "New record added :" + lastReading);
            trackDAO.insert( lastReading );
        }
        recButton.setText( getResources().getText(R.string.start_recording));

        started=false;
    }

    private void startRecording(Button recButton) {
        locationManager.start();
        elapsedTimeCounter.start();

        recButton.setText(getResources().getText(R.string.stop_recording));
        trackTime.setText("0");
        trackDistance.setText("0");

        started=true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        trackDistance = (EditText) this.findViewById(R.id.trackDistance);
        trackTime = (EditText) this.findViewById(R.id.trackTime);

        trackDAO = new TrackDAO(this);

        restoreStateBetweenRotations(savedInstanceState);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, records );
        ((ListView)this.findViewById(R.id.listRecords)).setAdapter(adapter);

        locationManager = new LocManager( this );
        elapsedTimeCounter = new TimeCounter(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        persistStateBetweenRotations(outState);
    }

    private static final String KEY_SAVED_LIST = "SAVED_LIST";
    private void persistStateBetweenRotations(Bundle outState){
        outState.putParcelableArray( KEY_SAVED_LIST, records.toArray( new TrackRecord[0]));
    }
    private void restoreStateBetweenRotations(Bundle state){
        if(state!=null){
            TrackRecord[] arr =(TrackRecord[]) state.getParcelableArray(KEY_SAVED_LIST);
            if(arr != null)
                records = asList( arr );

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recording, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateRecord(final TrackRecord record) {
        this.runOnUiThread(
            new Runnable() {
                @Override public void run() {
                    currentRecord.set( record );
                    trackDistance.setText( String.valueOf(record.getTrackLength().longValue()));
                }
            }
        );
    }

    public void updateTimeElapsed(final long elapsedSec) {
        this.runOnUiThread(
            new Runnable() {
                @Override public void run() {
                    trackTime.setText( String.valueOf(elapsedSec) );
                }
            }
        );
    }
}
