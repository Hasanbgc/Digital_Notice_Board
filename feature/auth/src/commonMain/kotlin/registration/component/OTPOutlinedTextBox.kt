package registration.component

import AccentGreen
import BorderGray
import ErrorRed
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OTPOutlinedTextBox(
    otpLength: Int,
    onOTPComplete: (String) -> Unit,
    isError: Boolean = false,
    modifier: Modifier = Modifier,
) {

    var otpValues by remember { mutableStateOf(List(otpLength) { "" }) }
    val focusRequesters = remember { List(otpLength) { FocusRequester() } }
    val clipboardManager = LocalFocusManager.current

    LaunchedEffect(otpValues) {
        val completeOTP = otpValues.joinToString("")
        if (completeOTP.length == otpLength) {
            onOTPComplete(completeOTP)
        }
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
                onValueChange = {
                    handleOtpInput(
                        newValue = it,
                        index = index,
                        otpValues = otpValues,
                        focusRequester = focusRequesters,
                        onValueChange = { otpValues = it }
                    )
                },
                modifier = Modifier
                    .size(56.dp)
                    .focusRequester(focusRequesters[index])
                    .onKeyEvent { keyEvent ->
                        handleOtpKeyEvent(
                            keyEvent = keyEvent,
                            index = index,
                            otpValues = otpValues,
                            focusRequesters = focusRequesters,
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

    if(newValue.length >1){
        val updatedValues = otpValues.toMutableList()
        newValue.forEachIndexed { i, char ->
            if(index + i < otpValues.size) {
                updatedValues[index +i] = char.toString()
            }

        }
        onValueChange(updatedValues)
        if(index+newValue.length < otpValues.size){
            focusRequester[index+newValue.length].requestFocus()
        }else{
            focusRequester[index].freeFocus()
        }
    }else{
        val updatedValues = otpValues.toMutableList()
        updatedValues[index] = newValue
        onValueChange(updatedValues)
        if (newValue.isNotEmpty() && index < otpValues.size - 1) {
            focusRequester[index + 1].requestFocus()
        }/*else if(index > 0 && index <= otpValues.size-1) {
            if(index == otpValues.size-1)
               focusRequester[index - 0].requestFocus()
            else
                focusRequester[index - 1].requestFocus()
        }else{
            focusRequester[index].freeFocus()
        }*/
    }

}

fun handleOtpKeyEvent(
    keyEvent: KeyEvent,
    index: Int,
    otpValues: List<String>,
    focusRequesters: List<FocusRequester>,
    onValueChange: (List<String>) -> Unit
): Boolean {

    if(keyEvent.type == KeyEventType.KeyDown &&
        keyEvent.key == androidx.compose.ui.input.key.Key.Backspace)
    {
        val updatedValues = otpValues.toMutableList()
        if(otpValues[index].isNotEmpty()){
            updatedValues[index] = ""
            onValueChange(updatedValues)
            focusRequesters[index-1].requestFocus()
        }else if(index > 0){
            updatedValues[index-1] = ""
            focusRequesters[index-1].requestFocus()
            onValueChange(updatedValues)

        }
        return true
    }
    return false
}