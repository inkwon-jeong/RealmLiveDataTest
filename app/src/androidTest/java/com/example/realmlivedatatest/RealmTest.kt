package com.example.realmlivedatatest

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.realmlivedatatest.realm.executeTransactionWithCoroutine
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RealmTest {

    lateinit var context: Context
    lateinit var configuration: RealmConfiguration

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        configuration = RealmConfiguration.Builder().inMemory().build()

        Realm.init(context)
        Realm.setDefaultConfiguration(configuration)
    }

    @Test
    fun executeTransaction_dispatcher_io() = runBlocking {
        withContext(Dispatchers.IO) {
            val ioRealm = Realm.getDefaultInstance()
            repeat(100) {
                delay(100)
                ioRealm.executeTransaction {}
            }
        }
    }

    @Test
    fun executeTransaction_dispatcher_main() = runBlocking {
        withContext(Dispatchers.Main) {
            val mainRealm = Realm.getDefaultInstance()
            repeat(100) {
                delay(100)
                mainRealm.executeTransactionWithCoroutine {}
            }
        }
    }

    @Test
    fun handle_exception_executeTransactionWithCoroutine() = runBlocking {
        withContext(Dispatchers.Main) {
            val mainRealm = Realm.getDefaultInstance()
            try {
                mainRealm.executeTransactionWithCoroutine { throw Exception() }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}