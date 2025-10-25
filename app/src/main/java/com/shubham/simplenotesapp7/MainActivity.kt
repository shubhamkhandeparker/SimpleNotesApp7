package com.shubham.simplenotesapp7


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shubham.simplenotesapp7.ui.theme.SimpleNotesApp7Theme
import java.util.UUID


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleNotesApp7Theme {
                AppNavigation()
            }
        }
    }
}


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val notesViewModel: NotesViewModel = viewModel()


    NavHost(
        navController = navController,
        startDestination = "notes_list_screen"
    ) {

        composable(route = "notes_list_screen") {
            NotesListScreen(
                navController = navController,
                viewModel = notesViewModel

            )
        }

        composable(route = "note_detail_screen/{noteId}") { backStackEntry ->

            val noteId = backStackEntry.arguments?.getString("noteId")
            val note = notesViewModel.getNoteById(UUID.fromString("noteId"))

            NoteDetailScreen(
                navController = navController,
                note = note
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListScreen(navController: NavHostController, viewModel: NotesViewModel)
{
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Simple Notes ") })
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(viewModel.notes) { note ->
                NoteItem(note = note, onClick = {
                    navController.navigate("note_detail_screen/${note.id}")
                })
                Divider()
            }
        }
    }
}

@Composable
fun NoteItem(note: Note, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(text = note.title, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = note.content, style = MaterialTheme.typography.bodySmall, maxLines = 2)
    }

}

@Composable
fun NoteDetailScreen(navController: NavController, note: Note?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (note != null) {
            Text(text = note.title, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = note.content, style = MaterialTheme.typography.bodyLarge)
        } else {
            Text(text = "Note not found")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {


            navController.popBackStack()
        }) {
            Text(text = "Back to List")
        }
    }
}