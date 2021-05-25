package com.example.realmlivedatatest.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId

open class Memo(
  @PrimaryKey
  var id: ObjectId = ObjectId(),
  var contents: String = ""
) : RealmObject()