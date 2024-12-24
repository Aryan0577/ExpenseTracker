package com.example.expensetracker

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.data.model.ExpenseEntity
import com.example.expensetracker.ui.theme.ExpenseTextView
import com.example.expensetracker.viewmodel.AddExpenseViewModel
import com.example.expensetracker.viewmodel.AddExpenseViewModelFactory
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("SuspiciousIndentation")
@Composable
fun AddExpense(navController: NavController){
    val viewModel: AddExpenseViewModel = viewModel(factory = AddExpenseViewModelFactory(LocalContext.current))
    val coroutineScope= rememberCoroutineScope()
        Surface(modifier= Modifier.fillMaxSize()){
        ConstraintLayout(modifier= Modifier.fillMaxSize()) {
            val (nameRow, card, topBar) = createRefs()
            Image(painter = painterResource(id = R.drawable.ic_topbar),
                contentDescription = null,
                modifier = Modifier.constrainAs(topBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

            Box(modifier= Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, start = 16.dp, end = 16.dp)
                .constrainAs(nameRow) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }){
                ExpenseTextView(
                    text="Add Expense",
                    fontSize=20.sp,
                    fontWeight = FontWeight.Bold,
                    color= Color.White,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                )
                Icon(Icons.Filled.MoreVert, null, modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 18.dp))
                Icon(Icons.Filled.ArrowBack, null, modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(end = 18.dp))
            }
            DataForm(modifier = Modifier
                .padding(top = 60.dp)
                .constrainAs(card) {
                    top.linkTo(nameRow.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                onAddExpenseClick = {
                    coroutineScope.launch{
                        if(viewModel.addExpense(it)){
                            navController.popBackStack()
                        }
                    }}
                )
        }
    }
}
@Preview
@Composable
fun check(){
    AddExpense(navController = rememberNavController())
}

@SuppressLint("RememberReturnType")
@Composable
fun DataForm(modifier: Modifier,onAddExpenseClick:(model:ExpenseEntity)->Unit){

    val name = remember  { mutableStateOf("") }
    val amount = remember  { mutableStateOf("") }
    val date = remember  { mutableLongStateOf(System.currentTimeMillis()) }
    val dateDialogVis= remember{ mutableStateOf(false) }
    val category= remember {
        mutableStateOf("Other")
    }
    val type= remember {
        mutableStateOf("Expense")
    }
    Column (
        modifier= modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Black)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())

    ){
        ExpenseTextView(text = "Name", fontSize= 16.sp)
        Spacer(modifier= Modifier.size(8.dp))
        OutlinedTextField(value = name.value, onValueChange = {name.value=it}, modifier= Modifier.fillMaxWidth(), maxLines = 1, minLines = 1)
        Spacer(modifier=Modifier.size(12.dp))

        ExpenseTextView(text = "Amount", fontSize= 16.sp)
        Spacer(modifier= Modifier.size(8.dp))
        OutlinedTextField(value = amount.value, onValueChange = {amount.value=it},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier= Modifier.fillMaxWidth(), maxLines = 1, minLines = 1)
        Spacer(modifier=Modifier.size(12.dp))

        ExpenseTextView(text = "Date", fontSize=16.sp)
        Spacer(modifier= Modifier.size(8.dp))
        OutlinedTextField(value =  Util.formatDatetoHumanRead(date.longValue),
            onValueChange = {}, modifier= Modifier
                .fillMaxWidth()
                .clickable { dateDialogVis.value = true }, enabled = false)

        Spacer(modifier= Modifier.size(16.dp))

        ExpenseTextView(text = "Type", fontSize=16.sp)
        Spacer(modifier= Modifier.size(8.dp))
        ExpenseIncomeToggle { isIncome ->
            type.value= if(isIncome){"Income"} else "Expense"
        }
        Spacer(modifier= Modifier.size(16.dp))
        val incomeCategories = listOf("Salary", "Freelancing", "Investments", "Business Income", "Rental Income", "Interest Income", "Dividends", "Tax Refund", "Pension", "Scholarship")
        val expenseCategories = listOf("Canteen","Groceries", "Rent", "Utilities", "Transportation", "Health", "Entertainment", "Food", "Clothes", "Subscriptions", "Education")

        ExpenseTextView(text = "Category", fontSize=16.sp)
        Spacer(modifier= Modifier.size(8.dp))
        ExpenseDropDown(listOfItems = if(type.value=="Income"){incomeCategories} else expenseCategories,
            onItemSelected = {category.value=it}
        )




        val context= LocalContext.current
        Button(onClick = {
            if(amount.value==""){
                Toast.makeText(context,"Add amount!",Toast.LENGTH_SHORT).show()
            }
            else { if(name.value==""){name.value= category.value}
                val model=ExpenseEntity(
                null, name.value,category.value,type.value, Util.formatToDecimalValue(amount.value.toDouble()).toDouble()?:0.0, date.longValue)
            onAddExpenseClick(model)}
        },
            modifier= Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(3.dp))){
            ExpenseTextView(
                "Add Expense",
                fontSize=14.sp,
                color=Color.White
            )
        }
    }
    if(dateDialogVis.value){
    ExpenseDatePicker(onDateSelected = {date.longValue=it; dateDialogVis.value=false}, onDismiss = {dateDialogVis.value=false})

    }

}



@Composable
fun ExpenseIncomeToggle(
    onSelectionChanged: (Boolean) -> Unit = {}
) {
    var isIncome by remember { mutableStateOf(false) }
    val animatedColor by animateColorAsState(
        targetValue = if (isIncome) Color.Green else Color.Red,
        animationSpec = tween(300)
    )

    Box(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray.copy(alpha = 0.2f))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ToggleButton(
                text = "Income",
                isSelected = isIncome,
                selectedColor = Color.Green,
                onClick = {
                    isIncome = true
                    onSelectionChanged(true)
                }
            )

            ToggleButton(
                text = "Expense",
                isSelected = !isIncome,
                selectedColor = Color.Red,
                onClick = {
                    isIncome = false
                    onSelectionChanged(false)
                }
            )
        }
    }
}

@Composable
private fun ToggleButton(
    text: String,
    isSelected: Boolean,
    selectedColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(
                if (isSelected) selectedColor else Color.Transparent,
                RoundedCornerShape(6.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else selectedColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDatePicker(
    onDateSelected: (date: Long) -> Unit,
    onDismiss: () -> Unit
){
    val datePickerState= rememberDatePickerState()
    val selecteddate= datePickerState.selectedDateMillis ?:0L
    DatePickerDialog(onDismissRequest = { onDismiss()},
        confirmButton = {
            TextButton(onClick = { onDateSelected(selecteddate) }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick= {onDateSelected(selecteddate)}){
                Text("Cancel")
            }
        }
    ) {
           DatePicker(state=datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDropDown(listOfItems:List<String>,onItemSelected:(item:String)->Unit){
   val expanded =remember{
         mutableStateOf(false)
    }
     val selectedItem= remember{ mutableStateOf(listOfItems[0]) }
    ExposedDropdownMenuBox(expanded = expanded.value, onExpandedChange = {expanded.value=it}){
         TextField(value = selectedItem.value, onValueChange ={},
             modifier= Modifier
                 .fillMaxWidth()
                 .menuAnchor(), readOnly = true,
             trailingIcon = {
                 ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
             })
            ExposedDropdownMenu(expanded = expanded.value, onDismissRequest = {}){
                listOfItems.forEach{
                    DropdownMenuItem(text = { Text(it)}, onClick = { selectedItem.value= it;onItemSelected(selectedItem.value); expanded.value= false})
                }
            }
    }
}


object Util{
    fun formatDatetoHumanRead(date:Long):String{
           val dateFormatter=SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())

        return dateFormatter.format(date)
    }

    fun formatToDecimalValue(d: Double): String {
        return String.format("%.2f", d)
    }

}