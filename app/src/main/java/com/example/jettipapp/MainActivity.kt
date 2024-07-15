package com.example.jettipapp

import Roundiconbutton
import android.health.connect.datatypes.units.Percentage
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettipapp.components.InputField
import com.example.jettipapp.ui.theme.JetTipAppTheme
import com.example.jettipapp.util.calculateTotalPerPerson
import com.example.jettipapp.util.calculateTotalTip
import javax.xml.transform.TransformerException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
//                TopHeader()
                MainContent()
            }
        }
    }
}

@Composable
fun MyApp(content:@Composable () -> Unit){
    JetTipAppTheme {
        Surface(color=MaterialTheme.colorScheme.background) {
            content()
        }
    }
}

//@Preview
@Composable
fun TopHeader(totalperperson:Double   ){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
//        .clip(shape = RoundedCornerShape(corner = CornerSize(15.dp)))
        .padding(10.dp), color = Color(0xFFE9D7F7),
        shape = RoundedCornerShape(corner = CornerSize(15.dp))
    ) {
           Column(modifier = Modifier.padding(12.dp)
               ,horizontalAlignment = Alignment.CenterHorizontally
               ,verticalArrangement = Arrangement.Center){
               val total ="%.2f".format(totalperperson)
              Text(
                  text = "Total Per Person",
                  style = TextStyle(
                      color = Color.Black,
                      fontWeight = FontWeight.Bold,
                      fontFamily = FontFamily.Monospace,
                      fontSize = 20.sp
                  )
              )
               Spacer(modifier = Modifier.height(5.dp))
               Text(text = "$$total" ,
                    style = TextStyle(
                        fontWeight = FontWeight.ExtraBold ,
                        fontSize = 30.sp
                    )
               )

           }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun MainContent(){

      Column {
          BillForm(){
                  amt ->
              Log.d("amt", "MainContent:$amt ")
          }
      }



}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(modifier: Modifier=Modifier,
             onValChange:(String)-> Unit={} ){
    val totalBillState = remember {
        mutableStateOf("" )
    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val keyboardController=LocalSoftwareKeyboardController.current

    val sliderPositionState = remember {
        mutableStateOf(0f)
    }
    val tipPercentage=(sliderPositionState.value*100).toInt()

    val splitByState = remember {
        mutableStateOf(1)
    }
    val tipAmountState= remember {
        mutableStateOf(0.0)
    }
    val totalPerPersonState= remember {
        mutableStateOf(0.0)
    }
 TopHeader(totalperperson = totalPerPersonState.value)

        Surface(modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(),
            color=Color.White ,
            shape = RoundedCornerShape(corner = CornerSize(8.dp)) ,
            border = BorderStroke(1.dp , color = Color.LightGray)
        ) {
            Column() {
                InputField(valueState = totalBillState,
                    labelId ="Enter Bill" ,
                    enabled =true,
                    isSingleLine =true,
                    onAction = KeyboardActions{
                        if (!validState) return@KeyboardActions
                        onValChange(totalBillState.value.trim())
                        keyboardController?.hide()
                    }
                )
            if (validState){
                Row(modifier=Modifier.padding(horizontal = 15.dp , vertical = 15.dp),
                    horizontalArrangement = Arrangement.Start){

                    Text(text = "Split" , modifier=Modifier.align(alignment=Alignment.CenterVertically))
                    Spacer(modifier = Modifier.width(140.dp))
                    Row(modifier = Modifier.padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End) {
                        Roundiconbutton(imageVector = Icons.Default.Remove ,
                            onClick = {
                                splitByState.value=if (splitByState.value>1) splitByState.value-1
                                else 1
                                totalPerPersonState.value= calculateTotalPerPerson(totalBill = totalBillState.value.toDouble() ,splitByState=splitByState.value ,
                                    tipPercentage=tipPercentage)
                            })

                        Text(text = "${splitByState.value}" ,modifier= Modifier
                            .align(Alignment.CenterVertically)
                            .padding(horizontal = 9.dp))


                        Roundiconbutton(imageVector = Icons.Default.Add ,
                            onClick = {
                                splitByState.value=splitByState.value+1
                                totalPerPersonState.value= calculateTotalPerPerson(totalBill = totalBillState.value.toDouble() ,splitByState=splitByState.value ,
                                    tipPercentage=tipPercentage)
                            })

                    }

                }

                Row(modifier=Modifier.padding(horizontal = 15.dp , vertical = 15.dp)) {
                    Text(text = "Tip" ,modifier=Modifier.align(Alignment.CenterVertically))
                    Spacer(modifier = Modifier.width(200.dp))
                    Text(text = "$ ${tipAmountState.value}" ,modifier=Modifier.align(Alignment.CenterVertically))
//                    Log.d("tipfinal", "BillForm: ${tipAmountState.value}")
                }
                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "$tipPercentage%")
                    Spacer(modifier = Modifier.height(14.dp))
//                slider
                    Slider(value = sliderPositionState.value,
                        onValueChange = {newval->
                            sliderPositionState.value=newval
                            tipAmountState.value=
                                calculateTotalTip(totalBill = totalBillState.value.toDouble() ,tipPercentage=tipPercentage)
                            totalPerPersonState.value= calculateTotalPerPerson(totalBill = totalBillState.value.toDouble() ,splitByState=splitByState.value ,
                                tipPercentage=tipPercentage)
                        },modifier=Modifier.padding(horizontal = 16.dp),
                        )
                }

            }
            else Box {}


            }

        }
    }







@Preview(showBackground = true)
@Composable
fun JetTip() {
    JetTipAppTheme {
           MyApp() {}
    }
}