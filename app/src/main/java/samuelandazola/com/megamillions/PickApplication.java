package samuelandazola.com.megamillions;

import android.app.Application;
import com.facebook.stetho.Stetho;
import samuelandazola.com.megamillions.model.db.PickDatabase;

public class PickApplication extends Application {

  // TODO Remove
  private PickDatabase database;

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    // TODO Remove database stuff from here; not needed anymore.
    database = PickDatabase.getInstance(this);
  }

  @Override
  public void onTerminate() {
    // TODO Remove database stuff (and this method) from here; not needed anymore.
    PickDatabase.forgetInstance();
    super.onTerminate();
  }

}
