package com.lab06.presentation.publish

import androidx.lifecycle.ViewModel
import com.lab06.logic.Apartment
import com.lab06.persistance.Apartments

class PublishViewModel : ViewModel() {
    val apartments: Apartments

    init {
        apartments = Apartments.instance
    }

    fun add(apartment: Apartment){
        apartments.add(apartment)
    }

}