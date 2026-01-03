package com.sjaindl.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

data class PortfolioSummary(
    val totalValue: String,
    val todaysChange: String,
    val isPositiveChange: Boolean,
)

data class Holding(
    val symbol: String,
    val name: String,
    val value: String,
    val change: String,
    val isPositiveChange: Boolean,
)

data class NewsArticle(
    val title: String,
    val source: String,
    val timeAgo: String,
)

val dummyPortfolioSummary = PortfolioSummary(
    totalValue = "$12,450.78",
    todaysChange = "+$230.56 (1.88%)",
    isPositiveChange = true
)

val dummyHoldings = listOf(
    Holding(
        symbol = "AAPL",
        name = "Apple Inc.",
        value = "$2,870.12",
        change = "+$45.67",
        isPositiveChange = true
    ),
    Holding(
        symbol = "GOOGL",
        name = "Alphabet Inc.",
        value = "$1,560.45",
        change = "-$12.34",
        isPositiveChange = false
    ),
    Holding(
        symbol = "TSLA",
        name = "Tesla, Inc.",
        value = "$890.23",
        change = "+$100.89",
        isPositiveChange = true
    ),
    Holding(
        symbol = "AMZN",
        name = "Amazon.com, Inc.",
        value = "$3,120.00",
        change = "-$5.67",
        isPositiveChange = false
    ),
)

val dummyNews = listOf(
    NewsArticle(
        title = "Market Hits All-Time High",
        source = "Global Finance News",
        timeAgo = "5m ago"
    ),
    NewsArticle(
        title = "Tech Stocks Rally on AI News",
        source = "Tech Chronicle",
        timeAgo = "1h ago"
    ),
    NewsArticle(
        title = "Interest Rate Hike Expected Next Month",
        source = "Economic Times",
        timeAgo = "3h ago"
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    MaterialTheme {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                PortfolioSummaryCard(summary = dummyPortfolioSummary)
            }

            item {
                SectionHeader("My Holdings")
            }

            items(dummyHoldings) { holding ->
                HoldingItem(holding = holding)
            }

            item {
                SectionHeader("Market News")
            }

            items(dummyNews) { article ->
                NewsItem(article = article)
            }
        }
    }
}

@Composable
fun PortfolioSummaryCard(summary: PortfolioSummary) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
        ) {
            Text(
                text = "Portfolio Value",
                style = MaterialTheme.typography.titleMedium,
            )

            Spacer(
                modifier = Modifier
                    .height(8.dp),
            )

            Text(
                text = summary.totalValue,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
            )

            Spacer(
                modifier = Modifier
                    .height(8.dp),
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                val color = if (summary.isPositiveChange) Color(0xFF00C853) else Color.Red

                Icon(
                    imageVector = if (summary.isPositiveChange) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                    contentDescription = null,
                    tint = color,
                )

                Spacer(
                    modifier = Modifier
                        .width(4.dp),
                )

                Text(
                    text = summary.todaysChange,
                    color = color,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp),
    )
}

@Composable
fun HoldingItem(holding: Holding) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
            ) {
                Text(
                    text = holding.symbol,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )

                Text(
                    text = holding.name,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = holding.value,
                    fontWeight = FontWeight.SemiBold,
                )

                val color = if (holding.isPositiveChange) Color(0xFF00C853) else Color.Red

                Text(
                    text = holding.change,
                    color = color,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Composable
fun NewsItem(article: NewsArticle) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = article.title,
                fontWeight = FontWeight.Bold,
            )

            Spacer(
                modifier = Modifier
                    .height(4.dp),
            )

            Row {
                Text(
                    text = article.source,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Spacer(
                    modifier = Modifier
                        .width(width = 8.dp)
                )

                Text(
                    text = article.timeAgo,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}
