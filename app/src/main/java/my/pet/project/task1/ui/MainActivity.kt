package my.pet.project.task1.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import my.pet.project.task1.R
import my.pet.project.task1.ui.fragment.AddContactFragment
import my.pet.project.task1.ui.fragment.ContactListFragment
import my.pet.project.task1.ui.viewModel.ContactViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, ContactListFragment()).commit()
    }
}