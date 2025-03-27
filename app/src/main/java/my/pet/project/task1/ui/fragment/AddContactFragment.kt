package my.pet.project.task1.ui.fragment


import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import my.pet.project.task1.R
import my.pet.project.task1.Utils.checkValidEmail
import my.pet.project.task1.Utils.checkValidPhone
import my.pet.project.task1.data.Contact
import my.pet.project.task1.databinding.FragmentAddContactBinding
import my.pet.project.task1.ui.viewModel.ContactViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class AddContactFragment: Fragment() {

    private val viewModel: ContactViewModel by sharedViewModel<ContactViewModel>()
    private var _binding: FragmentAddContactBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddContactBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddContact.setOnClickListener {
            if (binding.etAddContactName.text.toString().isNotBlank()
                && binding.etAddContactPhone.text.toString().isNotBlank() && binding.etAddContactPhone.text.toString().checkValidPhone()
                && (binding.etAddContactEmail.text.toString().isBlank() || binding.etAddContactEmail.text.toString().checkValidEmail())
            ) {
                viewModel.saveContact(
                    Contact(
                        name = binding.etAddContactName.text.toString(),
                        phone = binding.etAddContactPhone.text.toString(),
                        email = binding.etAddContactEmail.text.toString()
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

        binding.toolbarAddContacts.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        binding.toolbarAddContacts.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }


        val mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
        mask.isHideHardcodedHead = false
        val formatWatcher: FormatWatcher = MaskFormatWatcher(mask)

        formatWatcher.installOn(binding.etAddContactPhone)
        binding.etAddContactPhone.inputType = InputType.TYPE_CLASS_PHONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}