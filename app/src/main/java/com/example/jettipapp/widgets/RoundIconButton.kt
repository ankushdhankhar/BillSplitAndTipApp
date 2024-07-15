import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

val IconbuttonSizemodifier=Modifier.size(40.dp)
@Composable
fun Roundiconbutton(modifier: Modifier = Modifier,
                    imageVector: ImageVector,
                    onClick:()->Unit,
                    tint: Color = Color.Black.copy(alpha = 0.8f),
                    containerColor: Color = MaterialTheme.colorScheme.background,
                    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 4.dp) ){
    Card(modifier= modifier
        .padding(4.dp)
        .clickable { onClick.invoke() }
        .then(IconbuttonSizemodifier),
        shape = CircleShape ,
        colors = CardDefaults.cardColors(containerColor),
        elevation=elevation ,) {
        Icon(modifier= Modifier.fillMaxSize(),
            imageVector = imageVector,
            contentDescription ="Plus or Minus Icon",
            tint=tint)
    }

}

