package com.x3lnthpi.library

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.x3lnthpi.library.data.AppDatabase
import com.x3lnthpi.library.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


//class UserViewModel: ViewModel(){
//    val db = Room.databaseBuilder(
//        application,
//        AppDatabase::class.java, "User"
//    ).build()
//}


class UserViewModel(application: Application) : AndroidViewModel(application) {
    val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "User"
    ).build()


    private val userDao = db.userDao()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    init {
        viewModelScope.launch(Dispatchers.IO) {
            // Fetch data in a coroutine
            viewModelScope.launch(Dispatchers.IO) {
                _users.value = userDao.getAll()
            }

        }
            }

    // Function to get a single user by id
    suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }

    // Function to get the first user (for testing)
    suspend fun getFirstUser(): User? {
        return userDao.getFirstUser()
    }

    // Function to insert users
    fun insertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) { // Perform on a background thread
            userDao.insertAll(user)
        }
    }

    //Get all elements
    fun getAll(){
        viewModelScope.launch(Dispatchers.IO){
            userDao.getAll()
        }
    }

//    // Collect users as a Flow and expose as a StateFlow
//    val usersFlow: StateFlow<List<User>> = userDao.getAll()
//        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}


//class UserViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return UserViewModel(application) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}