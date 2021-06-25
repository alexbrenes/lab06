package com.lab06.persistance

import androidx.lifecycle.MutableLiveData
import com.lab06.logic.Apartment

class Apartments {

    var sequence: Int
    var items = MutableLiveData<List<Apartment>>()

    init{
        sequence = 0
        items.value = listOf(
            Apartment(++sequence),
            Apartment(++sequence),
            Apartment(++sequence),
            Apartment(++sequence),
            Apartment(++sequence),
            Apartment(++sequence),
            Apartment(++sequence)
        )
    }

    private object HOLDER {
        val INSTANCE = Apartments()
    }

    companion object {
        val instance: Apartments by lazy {
            HOLDER.INSTANCE
        }
    }

    fun add(apartment: Apartment) {
        apartment.id = ++sequence
        items.value = items.value!! + listOf(apartment)
    }

    fun delete(id: Int) {
        items.value = items.value!!.filter { it.id != id }
    }


}