package samuelandazola.com.megamillions.model.pojo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import java.util.List;
import samuelandazola.com.megamillions.model.entity.Pick;
import samuelandazola.com.megamillions.model.entity.PickNumber;

public class PickAndNumbers {

  @Embedded
  private Pick pick;

  public Pick getPick() {
    return pick;
  }

  public void setPick(Pick pick) {
    this.pick = pick;
  }

  public List<PickNumber> getNumbers() {
    return numbers;
  }

  public void setNumbers(List<PickNumber> numbers) {
    this.numbers = numbers;
  }

  @Relation(parentColumn = "pick_id", entityColumn = "pick_id")
  private List<PickNumber> numbers;
}
