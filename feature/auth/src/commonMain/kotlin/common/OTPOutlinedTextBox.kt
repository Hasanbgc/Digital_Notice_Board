package common

import AccentGreen
import BorderGray
import ErrorRed
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OTPOutlinedTextBox(
    otpLength: Int,
    onOTPComplete: (String) -> Unit,
    isError: Boolean = false,
    autoReadOTP:String = "",
    modifier: Modifier = Modifier,
) {

    var otpValues by remember { mutableStateOf(List(otpLength) { "" }) }
    val focusRequesters = remember { List(otpLength) { FocusRequester() } }
    val clipboardManager = LocalFocusManager.current

    LaunchedEffect(otpValues) {
        val completeOTP = otpValues.joinToString("")
        onOTPComplete(completeOTP)
    }
    /* LaunchedEffect(Unit) {
         clipboardManager.toString()
     }*/

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        otpValues.forEachIndexed { index, value ->
            OutlinedTextField(
                value = value,
                modifier = Modifier
                    .size(56.dp)
                    .focusRequester(focusRequesters[index])
                    /*.onKeyEvent { keyEvent ->
                        if (keyEvent.type == KeyEventType.KeyDown &&
                            keyEvent.key == Key.Backspace &&
                            otpValues[index].isEmpty() &&
                            index > 0
                        ) {
                            val updated = otpValues.toMutableList()
                            updated[index - 1] = ""
                            val completeOTP = updated.joinToString("")
                            onOTPComplete(completeOTP)
                            focusRequesters[index - 1].requestFocus()
                            true
                        } else {
                            false
                        }

                        handleOtpKeyEvent(
                            keyEvent = keyEvent,
                            index = index,
                            otpValues = otpValues,
                            focusRequesters = focusRequesters,
                            onValueChange = { otpValues = it }
                        )
                    }*/,
                onValueChange = {
                    handleOtpInput(
                        newValue = it,
                        index = index,
                        otpValues = otpValues,
                        focusRequester = focusRequesters,
                        onValueChange = { otpValues = it }
                    )
                },

                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                ),
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = if (index == otpLength - 1) ImeAction.Done else ImeAction.Next
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (isError) ErrorRed else AccentGreen,
                    unfocusedBorderColor = if (isError) ErrorRed else BorderGray
                ),
            )
        }
    }
}

fun handleOtpInput(
    newValue: String,
    index: Int,
    otpValues: List<String>,
    focusRequester: List<FocusRequester>,
    onValueChange: (List<String>) -> Unit
) {
    //for paste from clipboard
    if (newValue.length > 1) {
        val updatedValues = otpValues.toMutableList()
        newValue.forEachIndexed { i, char ->
            if (index + i < otpValues.size) {
                updatedValues[index + i] = char.toString()
            }

        }
        onValueChange(updatedValues)
        if (index + newValue.length < otpValues.size) {
            focusRequester[index + newValue.length].requestFocus()
        } else {
            focusRequester[index].freeFocus()
        }
    } else {
        //user input
        val updatedValues = otpValues.toMutableList()
        updatedValues[index] = newValue
        onValueChange(updatedValues)
        if (newValue.isNotEmpty() && index < otpValues.size - 1) {
            focusRequester[index + 1].requestFocus()
        } else if (index > 0 && index <= otpValues.size - 1) {
            if (index == otpValues.size - 1)
                focusRequester[index - 0].requestFocus()
            else
                focusRequester[index - 1].requestFocus()
        } else {
            focusRequester[index].freeFocus()
        }
        println("hitting_backPresed_empty_o: ${otpValues[index]}")
        when {
            // ✅ Handle backspace on empty → move to previous box
            newValue.isEmpty() && otpValues[index].isEmpty() && index > 0 -> {
                println("hitting_backPresed_empty : ${otpValues[index]}")
                updatedValues[index] = ""
                onValueChange(updatedValues)
                focusRequester[index - 1].requestFocus()
            }

            // ✅ Handle backspace
            newValue.isEmpty() && otpValues[index].isNotEmpty() -> {
                println("hitting_backPresed : ${index}")
                updatedValues[index] = ""
                onValueChange(updatedValues)
                if (index != 0)
                    focusRequester[index - 1].requestFocus()
            }


            // ✅ Handle normal input (digit)
            newValue.length == 1 -> {
                updatedValues[index] = newValue
                onValueChange(updatedValues)

                // Move to next box automatically
                if (index < otpValues.lastIndex) {
                    focusRequester[index + 1].requestFocus()
                } else {
                    //LocalFocusManager.current.clearFocus() // last digit → close keyboard
                }
            }
        }
    }

}


/*fun handleOtpKeyEvent(
    keyEvent: KeyEvent,
    index: Int,
    otpValues: List<String>,
    focusRequesters: List<FocusRequester>,
    onValueChange: (List<String>) -> Unit
): Boolean {

    if (keyEvent.type == KeyEventType.KeyDown &&
        keyEvent.key == androidx.compose.ui.input.key.Key.Backspace
    ) {
        val updatedValues = otpValues.toMutableList()
        println("digit: ${otpValues[index]}")
        if (otpValues[index].isNotEmpty()) {
            updatedValues[index] = ""
            onValueChange(updatedValues)
            focusRequesters[index - 1].requestFocus()
        } else if (index > 0) {
            updatedValues[index - 1] = ""
            focusRequesters[index - 1].requestFocus()
            onValueChange(updatedValues)

        }
        return true
    }
    return false
}*/
