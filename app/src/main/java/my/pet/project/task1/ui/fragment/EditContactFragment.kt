package my.pet.project.task1.ui.fragment

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import my.pet.project.task1.R
import my.pet.project.task1.Utils.checkValidEmail
import my.pet.project.task1.Utils.checkValidPhone
import my.pet.project.task1.data.Contact
import my.pet.project.task1.databinding.FragmentEditContactBinding
import my.pet.project.task1.ui.viewModel.ContactViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class EditContactFragment : Fragment() {

    private val viewModel: ContactViewModel by sharedViewModel<ContactViewModel>()
    private var _binding: FragmentEditContactBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditContactBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        viewModel.initContact(viewModel.currentContactId)
        binding.btnEditContact.setOnClickListener {
            if (binding.etEditContactName.text.toString().isNotBlank()
                && binding.etEditContactPhone.text.toString().isNotBlank() && binding.etEditContactPhone.text.toString().checkValidPhone()
                && (binding.etEditContactEmail.text.toString().isBlank() || binding.etEditContactEmail.text.toString().checkValidEmail())
                && viewModel.currentContactId != -1L
            ) {
                viewModel.editContact(
                    Contact(
                        viewModel.currentContactId,
                        binding.etEditContactName.text.toString(),
                        binding.etEditContactPhone.text.toString(),
                        binding.etEditContactEmail.text.toString()
                    )
                ) {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, ContactListFragment()).commit()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Не заполнены обязательные поля",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.toolbarEditContacts.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        binding.toolbarEditContacts.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }


        val mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
        mask.isHideHardcodedHead = false
        val formatWatcher: FormatWatcher = MaskFormatWatcher(mask)

        formatWatcher.installOn(binding.etEditContactPhone)
        binding.etEditContactPhone.inputType = InputType.TYPE_CLASS_PHONE
    }

    private fun initObservers() {
        viewModel.currentContactLiveData.observe(viewLifecycleOwner, Observer { contact ->
            if (contact != null) {
                binding.etEditContactName.setText(contact.name)
                binding.etEditContactPhone.setText(contact.phone)
                binding.etEditContactEmail.setText(contact.email)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}