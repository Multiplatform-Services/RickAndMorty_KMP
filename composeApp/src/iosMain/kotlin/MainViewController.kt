import androidx.compose.ui.window.ComposeUIViewController
import com.rickandmorty.kmp.di.initKoin
import com.rickandmorty.kmp.presentation.App

fun createComposeUIViewController() = ComposeUIViewController { App() }

fun initializeKoin() {
    initKoin()
}
