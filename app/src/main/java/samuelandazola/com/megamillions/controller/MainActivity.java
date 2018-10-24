package samuelandazola.com.megamillions.controller;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import edu.cnm.deepdive.Generator;
import edu.cnm.deepdive.MMGenerator;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import samuelandazola.com.megamillions.R;
import samuelandazola.com.megamillions.model.db.PickDatabase;
import samuelandazola.com.megamillions.model.entity.Pick;
import samuelandazola.com.megamillions.model.entity.PickNumber;
import samuelandazola.com.megamillions.model.pojo.PickAndNumbers;
import samuelandazola.com.megamillions.view.PickAdapter;

public class MainActivity extends AppCompatActivity {

  private Generator generator;
  private RecyclerView pickListView;
  private PickAdapter adapter;
  private List<int[]> picks;
  private Random rng;
  private PickDatabase database;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    pickListView = findViewById(R.id.pick_list_view);
    picks = new ArrayList<>();
    adapter = new PickAdapter(this, picks);
    pickListView.setAdapter(adapter);
    rng = new SecureRandom();
    generator = new MMGenerator(rng);
    FloatingActionButton fab = findViewById(R.id.add_pick);
    fab.setOnClickListener((view) -> {
      int[] pick = generator.generate();
      new AddTask().execute(pick);
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
   boolean handled = true;
   switch (item.getItemId()) {
     case R.id.action_clear:
       picks.clear();
       adapter.notifyDataSetChanged();
       break;
     default:
       handled = super.onOptionsItemSelected(item);

   }
    return handled;
  }

  @Override
  protected void onStart() {
    super.onStart();
    database = PickDatabase.getInstance(this);
    new QueryTask().execute();
  }

  @Override
  protected void onStop() {
    database = null;
    PickDatabase.forgetInstance();
    super.onStop();
  }

  private class QueryTask extends AsyncTask<Void, Void, List<PickAndNumbers>> {

    @Override
    protected void onPostExecute(List<PickAndNumbers> pickAndNumbers) {
      // FIXME Assume less with data model.
      picks.clear();
      for(PickAndNumbers pick : pickAndNumbers) {
        int[] numbers = new int[pick.getNumbers().size()];
        int index = 0;
        for(PickNumber number : pick.getNumbers()) {
          numbers[index++] = number.getValue();
        }
        picks.add(numbers);
      }
      adapter.notifyDataSetChanged();
    }

    @Override
    protected List<PickAndNumbers> doInBackground(Void... voids) {
      return database.getPickDao().selectWithNumbers();
    }
  }

  private class AddTask extends AsyncTask<int[], Void, int[]> {

    @Override
    protected void onPostExecute(int[] numbers) {
      picks.add(numbers);
      adapter.notifyItemInserted(picks.size() - 1);
    }

    @Override
    protected int[] doInBackground(int[]... ints) {
      int[] numbers = ints[0];
      Pick pick = new Pick();
      long pickId = database.getPickDao().insert(pick);
      List<PickNumber> pickNumbers = new LinkedList<>();
      for(int i = 0; i < numbers.length; i++) {
        PickNumber pickNumber = new PickNumber();
        pickNumber.setPickId(pickId);
        pickNumber.setValue(numbers[i]);
        pickNumber.setPool((i == numbers.length - 1) ? 1 : 0);
        pickNumbers.add(pickNumber);
      }
      database.getPickNumberDao().insert(pickNumbers);
      return numbers;
    }
  }
}
