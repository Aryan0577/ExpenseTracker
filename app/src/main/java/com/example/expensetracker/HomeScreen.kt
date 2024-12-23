package com.example.expensetracker

import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.expensetracker.data.model.ExpenseEntity
import com.example.expensetracker.ui.theme.ExpenseTextView
import com.example.expensetracker.ui.theme.Zinc
import com.example.expensetracker.viewmodel.HomeViewModel
import com.example.expensetracker.viewmodel.HomeViewModelFactory
import kotlin.math.exp


@Composable
fun HomeScreen(){
    
    val viewModel:HomeViewModel=HomeViewModelFactory(LocalContext.current).create(HomeViewModel::class.java)
    Surface(modifier= Modifier.fillMaxSize()){
       ConstraintLayout(modifier= Modifier.fillMaxSize()){
         val (namerow, list, card , topBar) = createRefs()
          Image(painterResource(id = R.drawable.ic_topbar), null, modifier=Modifier.constrainAs(topBar){
              top.linkTo(parent.top)
              start.linkTo(parent.start)
              end.linkTo(parent.end)
          })
           Box(modifier= Modifier
               .fillMaxWidth()
               .padding(top = 62.dp, start = 16.dp, end = 16.dp,)
               .constrainAs(namerow) {
                   top.linkTo(parent.top)
                   start.linkTo(parent.start)
                   end.linkTo(parent.end)
               }){
           Column {
               ExpenseTextView("Hello, World", fontSize = 16.sp,  color= Color.White)
               Spacer(modifier = Modifier.height(8.dp))
               ExpenseTextView("Name", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color=Color.White)
           }
               Image(painterResource(id = R.drawable.dots_menu),
                   null,
                   modifier = Modifier.align(Alignment.CenterEnd)
               )
               
           }
           val state= viewModel.expenses.collectAsState(initial = emptyList())
           val expenses=viewModel.getTotalExpense(state.value)
           val balance=viewModel.getBalance(state.value)
           val income=viewModel.getTotalIncome(state.value)

           CardItem(modifier = Modifier
               .constrainAs(card){
                   top.linkTo(namerow.bottom)
                   start.linkTo(parent.start)
                   end.linkTo(parent.end)
               },balance,income, expenses)

           TransactionList(
               modifier = Modifier
                   .fillMaxWidth()
                   .constrainAs(list) {
                       top.linkTo(card.bottom)
                       start.linkTo(parent.start)
                       end.linkTo(parent.end)
                       bottom.linkTo(parent.bottom)
                       height = Dimension.fillToConstraints
                   },state.value, viewModel)
       }

    }
}

@Composable
fun TransactionItem(
    title: String,
    amount: String,
    icon: Int,
    date: String,
    color: Color,
    modifier: Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column {
                ExpenseTextView(text = title, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.size(6.dp))
                ExpenseTextView(text = date, fontSize = 13.sp, color = Color.LightGray)
            }
        }
        ExpenseTextView(
            text = amount,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterEnd),
            color = color
        )
    }
}

@Composable
fun CardItem(modifier: Modifier, balance: String, income:String, expense:String){
    Column(modifier= modifier
        .padding(16.dp)
        .fillMaxWidth()
        .height(200.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(Zinc)
        .padding(16.dp)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .weight(1f)){
            Column(modifier=Modifier.align(Alignment.CenterStart)){
                ExpenseTextView("Total Balance", fontSize=16.sp,color=Color.White)
                ExpenseTextView("$ $balance", fontSize = 20.sp, color= Color.White, fontWeight = FontWeight.Bold)

            }
            Image(painterResource(id = R.drawable.dots_menu),
                null,
                modifier = Modifier.align(Alignment.CenterEnd)
            )

        }
        Spacer(modifier= Modifier.height(16.dp))
        Box(modifier=Modifier.fillMaxWidth(), ){
            CardRowItem(modifier = Modifier.align(Alignment.CenterStart), title = "Income", amount = "$ $income", R.drawable.ic_income )


            CardRowItem(modifier = Modifier.align(Alignment.CenterEnd), title = "Expense", amount = "$ $expense", R.drawable.ic_expense )
        }

    }
}




@Composable
fun TransactionList(
    modifier: Modifier,
    list:List<ExpenseEntity>,
    viewModel: HomeViewModel
) {

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
    ) {
        item{Box(modifier = Modifier.fillMaxWidth()){
            ExpenseTextView(text="Recent Transactions", fontSize= 20.sp)
            ExpenseTextView(text="See all", fontSize= 16.sp, modifier = Modifier.align(
                Alignment.CenterEnd))
        }}

        items(list){item->
            TransactionItem(title = item.title, amount = item.amount.toString(),
                icon = viewModel.getItemIcon(item),
                date = item.date.toString(),
                color = if(item.type=="Income") Color.Green else Color.Red,
                modifier = Modifier )
        }


        }
}
@Composable
fun CardRowItem(modifier: Modifier, title: String, amount: String, imaget: Int) {
    Column(modifier = modifier) {
        Row {

            Image(
                painter = painterResource(id = imaget),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.size(8.dp))
            ExpenseTextView(text = title, fontSize= 20.sp, color = Color.White)
        }
        Spacer(modifier = Modifier.size(4.dp))
        ExpenseTextView(text = amount, fontSize = 20.sp, color = Color.White)
    }
}


@Preview
@Composable
fun PreviewHomeScreen(){
    HomeScreen()
}


