package ee.drivediary.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import ee.drivediary.R;
import ee.drivediary.cli.AsyncClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class RecordingActivity extends ActionBarActivity {
    public static final String TAG = "RecordingActivity";

    volatile boolean started = false;

    EditText trackDistance;
    EditText trackTime;
    TimeCounter elapsedTimeCounter;
    AsyncClient serverCli = new AsyncClient();

    // Acquire a reference to the system Location Manager
    LocManager locationManager;

    private ArrayAdapter<TrackRecord> adapter;
    private List<TrackRecord> records = new ArrayList<>(10);
    private AtomicReference<TrackRecord> currentRecord = new AtomicReference<>();


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
            serverCli.tryToSendData( lastReading );
        }
        recButton.setText( getResources().getText(R.string.start_recording));

        started=false;
    }

    private void startRecording(Button recButton) {
        locationManager.start();
        elapsedTimeCounter.start();
        recButton.setText(getResources().getText(R.string.stop_recording));

        started=true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        trackDistance = (EditText) this.findViewById(R.id.trackDistance);
        trackTime = (EditText) this.findViewById(R.id.trackTime);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, records );
        ((ListView)this.findViewById(R.id.listRecords)).setAdapter(adapter);

        locationManager = new LocManager( this );
        elapsedTimeCounter = new TimeCounter(this);
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
