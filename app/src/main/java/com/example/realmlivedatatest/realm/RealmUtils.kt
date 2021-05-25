package com.example.realmlivedatatest.realm

import androidx.lifecycle.LiveData
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmQuery
import io.realm.RealmResults
import io.realm.kotlin.toFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun Realm.executeTransactionWithCoroutine(block: (Realm) -> Unit) {
    suspendCancellableCoroutine<Unit> { continuation ->
        val transaction = Realm.Transaction(block)
        val onSuccess = Realm.Transaction.OnSuccess { continuation.resume(Unit) }
        val onError = Realm.Transaction.OnError { error -> continuation.resumeWithException(error) }
        executeTransactionAsync(transaction, onSuccess, onError)
    }
}

fun <T : RealmModel> RealmResults<T>.asLiveRealmObject() = LiveRealmObject(this)
fun <T : RealmModel> RealmResults<T>.asLiveRealmResults() = LiveRealmResults(this)

fun <T : RealmModel> RealmQuery<T>.findFirstLiveData(): LiveData<T?> {
    return findAllAsync().asLiveRealmObject()
}

fun <T : RealmModel> RealmQuery<T>.findAllLiveData(): LiveData<List<T>> {
    return findAllAsync().asLiveRealmResults()
}

fun <T : RealmModel> RealmQuery<T>.findFirstFlow(): Flow<T?> {
    return findAllAsync().toFlow().map { it.firstOrNull() }
}

fun <T : RealmModel> RealmQuery<T>.findAllFlow(): Flow<List<T>> {
    return findAllAsync().toFlow()
}