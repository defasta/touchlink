package apps.eduraya.edurayaapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import apps.eduraya.edurayaapp.data.responses.UserInfo

//@TypeConverters(Converters::class)
@Database(entities = [UserInfo::class], version =1, exportSchema = false)
abstract class AppDatabase :RoomDatabase(){

    abstract fun getDao(): AppDao
    companion object{
        private var dbINSTANCE : AppDatabase? = null

        fun getAppDB(context: Context):AppDatabase{
            if(dbINSTANCE == null){
                dbINSTANCE = Room.databaseBuilder<AppDatabase>(
                    context.applicationContext, AppDatabase::class.java, "EdurayaAppDB"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return dbINSTANCE!!
        }
    }
}