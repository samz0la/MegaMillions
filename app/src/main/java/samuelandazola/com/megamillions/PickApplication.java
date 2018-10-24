package samuelandazola.com.megamillions;

import android.app.Application;
import com.facebook.stetho.Stetho;
import samuelandazola.com.megamillions.model.db.PickDatabase;

public class PickApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    // TODO Remove database stuff from here; not needed anymore.
  }

}
