package my.pet.project.task1.Utils

import android.text.TextWatcher
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import my.pet.project.task1.Utils.Utils.EMAIL_PATTERN
import my.pet.project.task1.Utils.Utils.PHONE_PATTERN


fun String.checkValidEmail(): Boolean{
    return matches(Regex(EMAIL_PATTERN))
}

fun String.checkValidPhone(): Boolean{
    return matches(Regex(PHONE_PATTERN))
}


fun EditText.textInputAsFlow() = callbackFlow {
    val watcher: TextWatcher = doOnTextChanged { textInput: CharSequence?, _, _, _ ->
        trySend(textInput)
    }

    awaitClose { removeTextChangedListener(watcher) }
}