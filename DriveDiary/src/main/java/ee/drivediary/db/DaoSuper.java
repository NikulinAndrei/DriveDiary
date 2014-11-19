package ee.drivediary.db;

import android.content.Context;

/**
 * Created with IntelliJ IDEA.
 * User: AndreiN
 * Date: 19.11.2014
 */
class DaoSuper {
  protected DbHelper db;

  public DaoSuper(Context context) {
    this.db = new DbHelper( context );
  }

}
