package my.pet.project.task1.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact): Long

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("SELECT * FROM Contact ORDER BY name ASC")
    suspend fun getAllContacts(): List<Contact>

    @Query("SELECT * FROM Contact WHERE id == :id")
    suspend fun getContactById(id: Long): Contact?

    @Query("SELECT * FROM Contact WHERE name LIKE '%' || :query || '%' OR phone LIKE '%' || :query || '%' ORDER BY name ASC")
    suspend fun getContactsByNameOrPhone(query: String): List<Contact>
}