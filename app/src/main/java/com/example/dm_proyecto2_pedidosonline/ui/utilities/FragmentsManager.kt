package com.example.dm_proyecto2_pedidosonline.ui.utilities

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.dm_proyecto2_pedidosonline.ui.fragment.FirstFragment

class FragmentsManager {

    //Para reemplazar todos los fragments
    fun replaceFragment(
        manager: FragmentManager,
        //referencia del id como archivo de recursos
        container: Int,
        //Fragment que se va a enviar
        fragment: Fragment
    ) {
        with(manager.beginTransaction()) {
            replace(container, fragment)
            addToBackStack(null)
            commit()
        }
    }

    fun addFragment(
        manager: FragmentManager,
        //referencia del id como archivo de recursos
        container: Int,
        //Fragment que se va a enviar
        fragment: Fragment
    ) {
        with(manager.beginTransaction()) {
            add(container, fragment)
            commit()
        }
    }
}