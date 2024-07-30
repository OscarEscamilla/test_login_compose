package com.racso.test_login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.racso.test_login.ui.screens.HomeScreen
import com.racso.test_login.ui.theme.TestloginTheme
import com.racso.test_login.ui.screens.LoginScreen
import com.racso.test_login.ui.screens.SignUpScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestloginTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    route = "auth",
                    startDestination = Screen.Login.route
                ) {
                    composable(Screen.Login.route) {
                        LoginScreen(
                            onNavigateToSignUp = {
                                navController.navigate(Screen.Signup.route)
                            },
                            onLoginSuccess = { email ->
                                navController.popBackStack()
                                navController.navigate("${Screen.Home.route}/$email")
                            }
                        )
                    }
                    composable(Screen.Signup.route) {
                        SignUpScreen(
                            onSignUpSuccess = {

                            },
                            onNavigateToLogin = {
                                navController.navigateUp()
                            }
                        )
                    }
                    composable("${Screen.Home.route}/{email}") { backStackEntry ->
                        backStackEntry.arguments?.getString("email")?.let {
                            HomeScreen(onLogout = {
                                navController.popBackStack()
                                navController.navigate(Screen.Login.route)
                            }, emailArg = it)
                        }
                    }
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Home : Screen("home")
}
