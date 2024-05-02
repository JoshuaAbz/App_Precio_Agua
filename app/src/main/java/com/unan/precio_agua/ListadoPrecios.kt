package com.unan.precio_agua

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unan.precio_agua.databinding.ActivityListadoPreciosBinding

class ListadoPrecios : AppCompatActivity() {
    private lateinit var binding: ActivityListadoPreciosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListadoPreciosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Se obtienen los años y meses únicos de la lista "Consumo" y se convierten a arrays
        val years = Global.Consumo.map { it.año }.distinct().toTypedArray()
        val months = Global.Consumo.map { it.mes }.distinct().toTypedArray()

        // Se crean adaptadores para los spinners de años y meses con los arrays obtenidos
        val yearAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        val monthAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, months)

        // Se asignan los adaptadores a los spinners de años y meses
        binding.spinnerYear.adapter = yearAdapter
        binding.spinnerMonth.adapter = monthAdapter

        // Regresar a valor por defecto de spinnerMonth cuando se selecciona un elemento en el spinnerYear
        binding.spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Establecer el spinnerMonth en su valor por defecto (posición 0)
                binding.spinnerMonth.setSelection(0)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No es necesario hacer nada aquí
            }
        }

        // Cuando se hace clic en el botón "btnMostrar", se obtienen el año y el mes seleccionados en los spinners
        binding.btnMostrar.setOnClickListener {
            val year = binding.spinnerYear.selectedItem.toString()
            val month = binding.spinnerMonth.selectedItem.toString()

            // Se busca en la lista "Consumo" el primer elemento que coincida con el año y el mes seleccionados
            val bloques = Global.Consumo.firstOrNull { it.año == year && it.mes == month }

            // Si se encontró un elemento, se muestran los datos en los campos de texto
            if (bloques != null) {
                binding.txtAnio2.text = bloques.año
                binding.txtMes2.text = bloques.mes
                binding.txtResidencial.text = bloques.Residencial.toString()
                binding.txtComercial.text = bloques.Comercial.toString()
                binding.txtIndustrial.text = bloques.Industrial.toString()
                binding.txtGobierno.text = bloques.Gobierno.toString()
                binding.txtPomedioNacional.text = bloques.promedioNacional.toString()
            } else {
                //Posible solución o alternativa al otro codigo
                Toast.makeText(
                    this,
                    "No se encontraron datos para el año y el mes seleccionados.",
                    Toast.LENGTH_SHORT
                ).show()

                // Muestra un mensaje si no se encontraron datos para el año y el mes seleccionados
                /*val mensaje = "No se encontraron datos para el año y el mes seleccionados."
                binding.txtAnio2.text = mensaje
                binding.txtMes2.text = mensaje
                binding.txtResidencial.text = mensaje
                binding.txtComercial.text = mensaje
                binding.txtIndustrial.text = mensaje
                binding.txtGobierno.text = mensaje
                binding.txtPomedioNacional.text = mensaje*/
            }
        }
    }
}
