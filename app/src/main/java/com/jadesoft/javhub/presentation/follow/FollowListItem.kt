package com.jadesoft.javhub.presentation.follow

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jadesoft.javhub.data.model.Actress
import com.jadesoft.javhub.navigation.ActressRoute
import com.jadesoft.javhub.ui.follow.FollowEvent
import com.jadesoft.javhub.widget.MyAsyncImage

@Composable
fun FollowListItem(
    actress: Actress,
    unreadCount: Int = 0,
    onToggleShowDialog: (FollowEvent.ToggleShowDialog) -> Unit,
    navController: NavController
) {
    ListItem(
        leadingContent = {
            Box(
                modifier = Modifier.clickable(onClick = {
                    navController.navigate(
                        route = ActressRoute(
                            code = actress.code,
                            name = actress.name,
                            avatarUrl = actress.avatar,
                            censored = actress.censored
                        )
                    )
                })
            ) {
                Card(
                    modifier = Modifier.size(60.dp),
                    shape = CircleShape,
                ) {
                    MyAsyncImage(
                        imageUrl = actress.avatar,
                        modifier = Modifier.fillMaxSize().clickable { },
                        contentScale = ContentScale.Crop
                    )
                }

                if (unreadCount > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 4.dp, y = (-4).dp)
                            .size(20.dp)
                            .background(Color.Red, CircleShape)
                    ) {
                        Text(
                            text = if (unreadCount > 99) "99+" else "$unreadCount",
                            color = Color.White,
                            fontSize = 10.sp,
                            maxLines = 1,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        },
        headlineContent = {
            Text(
                text = actress.name,
                style = MaterialTheme.typography.titleMedium
            )
        },
        trailingContent = {
            OutlinedButton(
                onClick = { onToggleShowDialog(FollowEvent.ToggleShowDialog(actress.code)) }
            ) {
                Text("关注中")
            }
        },
        modifier = Modifier.clickable {
            navController.navigate(
                route = ActressRoute(
                    code = actress.code,
                    name = actress.name,
                    avatarUrl = actress.avatar,
                    censored = actress.censored
                )
            )
        }
    )
}