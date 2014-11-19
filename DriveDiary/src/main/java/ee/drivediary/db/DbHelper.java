package ee.drivediary.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: AndreiN
 * Date: 19.11.2014
 */
 class DbHelper extends SQLiteOpenHelper {
  public static final String TAG = "DbCreator";

  private static final String DATABASE_NAME = "drivediary.db";
  private static final int DATABASE_VERSION = 1;

  public DbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  private static final String SQL_CREATE_TRACK_TABLE =
      "CREATE TABLE TRACK( " +
          " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
          " STARTED_AT DATE," +
          " STOPPED_AT DATE," +
          " DISTANCE NUMBER," +
          " CAR_NR VARCHAR2(10)" +
          "); ";

  @Override
  public void onCreate(SQLiteDatabase db) {
    Log.i(TAG, "Creating Database");
    db.execSQL(SQL_CREATE_TRACK_TABLE);
    Log.i(TAG, "Database created");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }
}
