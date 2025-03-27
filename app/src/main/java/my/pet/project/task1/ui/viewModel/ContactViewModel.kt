package my.pet.project.task1.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.pet.project.task1.data.Contact
import my.pet.project.task1.repository.ContactsRepository

class ContactViewModel(
    private val contactRepository: ContactsRepository,
): ViewModel() {

    private val contactsMutableLiveData = MutableLiveData<List<Contact>>(emptyList())
    val contactsLiveData: LiveData<List<Contact>>
        get() = contactsMutableLiveData

    private val suggestionMutableLiveData = MutableLiveData<List<Contact>>(emptyList())
    val suggestionLiveData: LiveData<List<Contact>>
        get() = suggestionMutableLiveData

    private val currentContactMutableLiveData = MutableLiveData<Contact?>(null)
    val currentContactLiveData: LiveData<Contact?>
        get() = currentContactMutableLiveData

    var currentContactId: Long = -1L

    fun saveContact(contact: Contact, onSave:()->Unit){
        viewModelScope.launch(Dispatchers.IO) {
            contactRepository.insertContact(contact)
            onSave()
        }
    }


    fun editContact(contact: Contact, onSave:()->Unit){
        viewModelScope.launch(Dispatchers.IO) {
            contactRepository.updateContact(contact)
            onSave()
        }
    }

    fun initContact(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            if(id!=-1L){
                val contact = contactRepository.getContactById(id)
                contact?.let { currentContactMutableLiveData.postValue(it) }
            }
        }
    }

    fun initContactList(){
        viewModelScope.launch(Dispatchers.IO) {

            val contacts = contactRepository.getAllContacts()
            contactsMutableLiveData.postValue(contacts)
        }
    }

    fun search(query: String){
        viewModelScope.launch(Dispatchers.IO) {
            val contacts = contactRepository.searchContacts(query)

            suggestionMutableLiveData.postValue(contacts)

        }
    }
}