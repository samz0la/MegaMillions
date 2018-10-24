package samuelandazola.com.megamillions.view;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import samuelandazola.com.megamillions.R;
import samuelandazola.com.megamillions.controller.MainActivity;
import samuelandazola.com.megamillions.model.entity.PickNumber;
import samuelandazola.com.megamillions.model.pojo.PickAndNumbers;

public class PickAdapter extends RecyclerView.Adapter<PickAdapter.Holder> {

  private Context context;
  private List<PickAndNumbers> picks;

  public PickAdapter(Context context, List<PickAndNumbers> picks) {
    this.context = context;
    this.picks = picks;
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(context).inflate(R.layout.pick_item, viewGroup, false);
    return new Holder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    holder.bind();
    int background = (position % 2 == 0)
        ? ContextCompat.getColor(context, R.color.pickBackground)
        : ContextCompat.getColor(context, R.color.pickBackgroundAlternate);
    holder.itemView.setBackgroundColor(background);
  }

  @Override
  public int getItemCount() {
    return picks.size();
  }

  public class Holder extends RecyclerView.ViewHolder
      implements View.OnCreateContextMenuListener {

    private static final int PICK_LENGTH = 6;
    private static final String ID_RES_TYPE = "id";
    private static final String NUM_ID_FORMAT = "num_%d";

    private PickAndNumbers pick;
    private TextView[] numbers;

    public Holder(@NonNull View view) {
      super(view);
      Resources res = context.getResources();
      String pkg = context.getPackageName();
      view.setOnCreateContextMenuListener(this);
      numbers = new TextView[PICK_LENGTH];
      for (int i = 0; i < PICK_LENGTH; i++) {
        int id = res.getIdentifier(String.format(NUM_ID_FORMAT, i), ID_RES_TYPE, pkg);
        numbers[i] = view.findViewById(id);
      }
    }

    private void bind() {
      pick = picks.get(getAdapterPosition());
      List<PickNumber> numbers = pick.getNumbers();
      int index = 0;
      for (PickNumber pickNumber : numbers) {
        this.numbers[index++]
            .setText(context.getString(R.string.pick_number_format, pickNumber.getValue()));
      }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
      menu.add(R.string.delete_pick).setOnMenuItemClickListener((item) -> {
        ((MainActivity) context).deletePick(getAdapterPosition(), pick.getPick());
        return true;
      });
    }

  }

}