package ee.drivediary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import ee.drivediary.app.TrackRecord;

/**
 * Created with IntelliJ IDEA.
 * User: AndreiN
 * Date: 19.11.2014
 */
public class TrackDAO extends DaoSuper {
  public static final String TAG = "TrackDAO";
  private static final String TABLE = "TRACK";
  private static final String STARTED_AT = "STARTED_AT";
  private static final String STOPPED_AT = "STOPPED_AT";
  private static final String DISTANCE = "DISTANCE";
  private static final String CAR_NR = "CAR_NR";

  public TrackDAO(Context context) {
    super(context);
  }

  public long insert( TrackRecord record ){
    Log.d(TAG, "Inserting new " + record);
    SQLiteDatabase db = null;
    try{
      ContentValues values = new ContentValues();
      values.put(CAR_NR, "AFN 357");
      values.put(STARTED_AT, record.getStartTime().getTime() );
      values.put(STOPPED_AT, record.getEndTime().getTime() );
      values.put(DISTANCE, record.getTrackLength() );

      db = this.db.getWritableDatabase();
      long newId = db.insert( TABLE, null, values );
      Log.d(TAG, "Inserted with ID=" + newId);

      return newId;
    }finally {
      if(db != null)
        db.close();
    }
  }
}
