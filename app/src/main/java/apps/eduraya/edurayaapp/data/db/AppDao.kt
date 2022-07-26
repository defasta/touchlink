package apps.eduraya.edurayaapp.data.db

import androidx.room.*
import apps.eduraya.edurayaapp.data.responses.UserInfo

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