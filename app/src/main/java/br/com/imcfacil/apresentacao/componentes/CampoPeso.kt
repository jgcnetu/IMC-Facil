package br.com.imcfacil.apresentacao.componentes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.imcfacil.R

@Composable
fun CampoPeso(
    valor: String,
    aoAlterar: (String) -> Unit
) {

    // Este componente tem UMA responsabilidade:
    // desenhar o campo de peso.
    OutlinedTextField(
        value = valor,

        // UI continua desacoplada da lógica
        onValueChange = aoAlterar,

        label = { Text("Peso (kg)") },

        // Garantir consistência visual em light/dark theme
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.primary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary
        ),

                // Fonte do texto digitado
        textStyle = TextStyle(
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        ),

        // Placeholder (hint)
        placeholder = {
            Text("Ex: 70,5")
        },

        singleLine = true, // evita quebra de layout
        maxLines = 1,

        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.iv_weight),

                // Acessibilidade continua garantida
                contentDescription = "Peso em quilogramas",

                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
        },

        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),

        modifier = Modifier.fillMaxWidth()
    )
}