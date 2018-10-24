package samuelandazola.com.megamillions;

import android.app.Application;
import com.facebook.stetho.Stetho;
import samuelandazola.com.megamillions.model.db.PickDatabase;

public class PickApplication extends Application {

  private PickDatabase database;

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    database = PickDatabase.getInstance(this);
  }

  @Override
  public void onTerminate() {
    PickDatabase.forgetInstance();
    super.onTerminate();
  }

}
