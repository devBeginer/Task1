package my.pet.project.task1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import my.pet.project.task1.R
import my.pet.project.task1.Utils.textInputAsFlow
import my.pet.project.task1.databinding.FragmentContactListBinding
import my.pet.project.task1.ui.adapter.ContactRecyclerViewAdapter
import my.pet.project.task1.ui.viewModel.ContactViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ContactListFragment: Fragment() {

    private val viewModel: ContactViewModel by sharedViewModel<ContactViewModel>()
    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ContactRecyclerViewAdapter
    private lateinit var suggestionAdapter: ContactRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ContactRecyclerViewAdapter ({

        },{
            viewModel.currentContactId = it
            parentFragmentManager.beginTransaction().replace(R.id.main_fragment_container, EditContactFragment()).addToBackStack("Add").commit()
        })
        binding.rvContacts.adapter = adapter


        binding.searchBar.setOnMenuItemClickListener { menuItem->
            when(menuItem.itemId){
                R.id.add_contact->{
                    parentFragmentManager.beginTransaction().replace(R.id.main_fragment_container, AddContactFragment()).addToBackStack("Edit").commit()
                }
            }
            true
        }
        binding.searchView.setupWithSearchBar(binding.searchBar)

        binding.searchView
            .editText
            .setOnEditorActionListener { v, actionId, event ->
                viewModel.search(binding.searchView.text.toString())
                false
            }
        binding.searchView
            .editText
            .textInputAsFlow()
            .debounce(300)
            .onEach { query->
                if (!query.isNullOrBlank()){
                    viewModel.search(query.toString())
                }
            }
            .launchIn(CoroutineScope(Dispatchers.Main))

        suggestionAdapter = ContactRecyclerViewAdapter ({

        },{
            viewModel.currentContactId = it
            parentFragmentManager.beginTransaction().replace(R.id.main_fragment_container, EditContactFragment()).addToBackStack("Edit").commit()
        })
        binding.rvSearchSuggestions.adapter = suggestionAdapter

        initObservers()
        viewModel.initContactList()

    }
    private fun initObservers() {
        viewModel.contactsLiveData.observe(viewLifecycleOwner, Observer { dataSet ->
            if (dataSet.isNotEmpty()) {
                adapter.setItemList(dataSet)
            }
        })


        viewModel.suggestionLiveData.observe(viewLifecycleOwner, Observer { dataSet ->
            suggestionAdapter.setItemList(dataSet)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}