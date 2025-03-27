package my.pet.project.task1.repository

import my.pet.project.task1.data.Contact
import my.pet.project.task1.data.ContactDao

class ContactsRepository(
    private val contactDao: ContactDao
) {
    suspend fun updateContact(contact: Contact) = contactDao.updateContact(contact)
    suspend fun insertContact(contact: Contact): Long = contactDao.insertContact(contact)
    suspend fun deleteContact(contact: Contact) = contactDao.deleteContact(contact)
    suspend fun getAllContacts() = contactDao.getAllContacts()
    suspend fun searchContacts(query: String) = contactDao.getContactsByNameOrPhone(query)
    suspend fun getContactById(id: Long) = contactDao.getContactById(id)
}