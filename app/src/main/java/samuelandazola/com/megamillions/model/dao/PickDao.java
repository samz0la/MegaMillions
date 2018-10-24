package samuelandazola.com.megamillions.model.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import java.util.List;
import samuelandazola.com.megamillions.model.entity.Pick;
import samuelandazola.com.megamillions.model.pojo.PickAndNumbers;

@Dao
public interface PickDao {

  @Insert(onConflict = OnConflictStrategy.FAIL)
  long insert(Pick pick);

  @Query("SELECT * FROM Pick ORDER BY timestamp")
  List<Pick> select();

  @Transaction
  @Query("SELECT *FROM Pick ORDER BY timestamp")
  List<PickAndNumbers> selectWithNumbers();

  @Delete
  int delete(Pick pick);

  @Query("DELETE FROM Pick")
  int nuke();

}
