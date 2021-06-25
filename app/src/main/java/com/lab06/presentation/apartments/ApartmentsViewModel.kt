package com.lab06.presentation.apartments

import androidx.lifecycle.ViewModel
import com.lab06.persistance.Apartments

class ApartmentsViewModel : ViewModel() {

    val apartments: Apartments

    init {
        apartments = Apartments.instance
    }



}