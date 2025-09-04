package login.component

import LightGray
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import digita_notice_board.feature.auth.generated.resources.Res
import digita_notice_board.feature.auth.generated.resources.flag_bd
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun PhoneNumberTextBoxWithCountryCode(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    countryCode: String,
    onCountryCodeChange: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = "",
    label: String = "Phone Number",
    placeholder: String = "18xxxxxxxxx",
    modifier: Modifier = Modifier
) {

            /*Row(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxHeight()
                    .background(color = gray_light, shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                    .padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.flag_bd),
                    contentDescription = "Country Flag",
                    modifier = Modifier
                        .size(20.dp)
                        .clip(RoundedCornerShape(2.dp))
                )
                Spacer(Modifier.width(6.dp))
                Text(text = "+880")
            }*/
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { newValue ->
                    val filteredValue = newValue.filter { it.isDigit() }
                    if (filteredValue.length <= 12) {
                        onPhoneNumberChange(filteredValue)
                    }
                },
                placeholder = {
                    Text(text = placeholder, color = Color.Gray)
                },
                leadingIcon = {
                    Row(
                        modifier = Modifier
                            .fillMaxHeight()
                            .background(color = LightGray,/* shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)*/)
                            .padding(start = 16.dp, end = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.flag_bd),
                            contentDescription = "Country Flag",
                            modifier = Modifier
                                .size(20.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(text = "+880")
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color(0xFF6200EE).copy(alpha = 0.6f),
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                ),
                modifier = modifier,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done),
                isError = isError,
                shape = RoundedCornerShape(16.dp)
            )

}
