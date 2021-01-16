package com.divij.credittracker.model

import java.util.*
import kotlin.collections.ArrayList

data class Session(val startDate : Date, var total: Int = 0) {
    constructor() : this(Date()) {

    }
    val id : String = UUID.randomUUID().toString()
    var endDate = Date()

    fun endSession() {
        endDate = Date()
    }


}
