package apps.eduraya.e_parking.data.db

import androidx.room.*
import apps.eduraya.e_parking.data.responses.DataDeposit
import apps.eduraya.e_parking.data.responses.DataPayDeposit
import apps.eduraya.e_parking.data.responses.UserInfo
import apps.eduraya.e_parking.data.responses.user.UserData

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserInfo(userInfo: UserInfo)

    @Update
    fun updateUserInfo(userInfo: UserInfo)

    @Delete
    fun deleteUserInfo(userInfo: UserInfo)

    @Query("SELECT * FROM user_info")
    fun getUserInfo(): List<UserInfo>

}