package com.example.realmlivedatatest.repo

import com.example.realmlivedatatest.model.Counter
import com.example.realmlivedatatest.realm.executeTransactionWithCoroutine
import com.example.realmlivedatatest.realm.findFirstFlow
import com.example.realmlivedatatest.realm.findFirstLiveData
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CounterRepository {

    private val configuration =
        RealmConfiguration.Builder()
            .name("counter.realm")
            .deleteRealmIfMigrationNeeded()
            .build()

    private val realm = Realm.getInstance(configuration)

    fun getCounterAsync() =
        realm.where<Counter>()
            .findFirstLiveData()

    fun getCounterFlow() =
        realm.where<Counter>()
            .findFirstFlow()

    suspend fun incrementCount() = withContext(Dispatchers.Main) {
        realm.executeTransactionWithCoroutine { realm ->
            realm.where<Counter>()
                .findFirst()
                ?.increment()
                ?: run {
                    realm.insert(Counter())
                }
        }
    }
}