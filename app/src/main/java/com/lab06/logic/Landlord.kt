package com.lab06.logic

import java.io.Serializable

class Landlord : Serializable {

    var name: String
    var phone: String

    constructor(name: String, phone: String) {
        this.name = name
        this.phone = phone
    }

    constructor()

    init {
        name = ""
        phone = ""
    }

}