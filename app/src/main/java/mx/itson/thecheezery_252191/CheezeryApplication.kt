package mx.itson.thecheezery_252191

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import mx.itson.thecheezery_252191.data.database.AppDatabase
import mx.itson.thecheezery_252191.data.repository.CheezeryRepository

class CheezeryApplication : Application() {

    val database by lazy { AppDatabase.getInstance(this) }

    val repository by lazy { CheezeryRepository(database) }
}

// para sacar el repositorio en las factories de los viewmodels
val CreationExtras.cheezeryApplication: CheezeryApplication
    get() = checkNotNull(this[APPLICATION_KEY]) as CheezeryApplication
