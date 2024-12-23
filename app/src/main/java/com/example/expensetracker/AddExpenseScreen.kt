package com.example.expensetracker

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.expensetracker.ui.theme.ExpenseTextView

@Composable
fun AddExpense(){
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
                })
        }
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun DataForm(modifier: Modifier){

    val name = remember  { mutableStateOf("") }
    val amount = remember  { mutableStateOf("") }

    Column (
        modifier= modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())

    ){
        ExpenseTextView(text = "Name", fontSize= 16.sp)
        Spacer(modifier= Modifier.size(8.dp))
        OutlinedTextField(value = name.value, onValueChange = {name.value=it}, modifier= Modifier.fillMaxWidth())
        Spacer(modifier=Modifier.size(12.dp))

        ExpenseTextView(text = "Amount", fontSize= 16.sp)
        Spacer(modifier= Modifier.size(8.dp))
        OutlinedTextField(value = amount.value, onValueChange = {amount.value=it}, modifier= Modifier.fillMaxWidth())
        Spacer(modifier=Modifier.size(12.dp))

        Button(onClick = {}, modifier= Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(3.dp))){
            ExpenseTextView(
                "Add Expense",
                fontSize=14.sp,
                color=Color.White
            )
        }



    }

}

@Composable




@Preview
@Composable
fun addexpensePreview(){
    AddExpense()
}