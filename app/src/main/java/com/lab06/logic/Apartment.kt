package com.lab06.logic

import android.graphics.Bitmap
import android.widget.ImageView
import java.io.Serializable

class Apartment : Serializable {

    var id: Int
    var latitude: Double
    var longitude: Double
    var description: String
    var landlord: Landlord
    var price: Float
    var imageBM: Bitmap?
    var temperature: Double

    constructor(
        id: Int,
        latitude: Double,
        longitude: Double,
        description: String,
        landlord: Landlord,
        price: Float,
        imageBM: Bitmap?
    ) {
        this.id = id
        this.latitude = latitude
        this.longitude = longitude
        this.description = description
        this.landlord = landlord
        this.price = price
        this.imageBM = imageBM
    }

    constructor()

    constructor(id: Int) {
        this.id = id
    }

    constructor(
        latitude: Double,
        longitude: Double,
        description: String,
        landlord: Landlord,
        price: Float,
        imageBM: Bitmap?
    ) {
        this.latitude = latitude
        this.longitude = longitude
        this.description = description
        this.landlord = landlord
        this.price = price
        this.imageBM = imageBM
    }

    init {
        id = 0
        latitude = 10.105245
        longitude = -84.192202
        description = ""
        landlord = Landlord()
        price = .0f
        imageBM = null
        temperature = .0
    }


}