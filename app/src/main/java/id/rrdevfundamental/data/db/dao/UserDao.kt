package id.rrdevfundamental.data.db.dao

import androidx.room.*
import id.rrdevfundamental.data.db.entitiy.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity)

    @Query("DELETE FROM UserEntity WHERE id = :id")
    fun deleteUser(id: Int)

    @Query("SELECT * FROM UserEntity ORDER BY name ASC")
    fun getUsers(): List<UserEntity>

    @Query("SELECT * FROM UserEntity WHERE login = :username")
    fun getUserById(username: String): UserEntity
}