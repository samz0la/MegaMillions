package samuelandazola.com.megamillions.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(
    primaryKeys = {"pick_id", "pool", "value"},
    foreignKeys = {
        @ForeignKey(
            entity = Pick.class,
            parentColumns = "pick_id",
            childColumns = "pick_id",
            onDelete = ForeignKey.CASCADE
        )
    }
)
public class PickNumber {

  @ColumnInfo (name = "pick_id", index = true)
  private long pickId;

  private int value;

  private int pool;

  public long getPickId() {
    return pickId;
  }

  public void setPickId(long pickId) {
    this.pickId = pickId;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public int getPool() {
    return pool;
  }

  public void setPool(int pool) {
    this.pool = pool;
  }
}
