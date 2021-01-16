package com.divij.credittracker.model

import java.util.*

data class Transaction(val description : String, val amount : Int) {
    constructor() : this("", 0) {

    }
    val time : Date = Date();
}
