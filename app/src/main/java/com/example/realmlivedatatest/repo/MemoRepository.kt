package com.example.realmlivedatatest.repo

import com.example.realmlivedatatest.model.Memo
import com.example.realmlivedatatest.realm.executeTransactionWithCoroutine
import com.example.realmlivedatatest.realm.findAllFlow
import com.example.realmlivedatatest.realm.findAllLiveData
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MemoRepository {

    private val configuration =
        RealmConfiguration.Builder()
            .name("memo.realm")
            .deleteRealmIfMigrationNeeded()
            .build()

    private val realm = Realm.getInstance(configuration)

    fun getAllMemosAsync() =
        realm.where<Memo>()
            .sort("id", Sort.DESCENDING)
            .findAllLiveData()

    fun getAllMemosFlow() =
        realm.where<Memo>()
            .sort("id", Sort.DESCENDING)
            .findAllFlow()

    suspend fun addMemo(contents: String) = withContext(Dispatchers.Main) {
        realm.executeTransactionWithCoroutine { realm ->
            val memo = Memo(contents = contents)
            realm.insert(memo)
        }
    }
}