package com.shubham.simplenotesapp7

import java.util.UUID

data class Note (
    val id:UUID = UUID.randomUUID(),
    val title :String,
    val content : String
)