package id.rrdevfundamental.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import id.rrdevfundamental.data.db.dao.UserDao
import id.rrdevfundamental.data.db.entitiy.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
}