package com.example.jogoadivinhacao

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtNome = findViewById<EditText>(R.id.edtNome)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupMaximo)
        val btnJogar = findViewById<Button>(R.id.btnJogar)

        btnJogar.setOnClickListener {
            val nome = edtNome.text.toString().trim()

            if (nome.isEmpty()) {
                edtNome.error = "Digite seu nome"
                edtNome.requestFocus()
                return@setOnClickListener
            }

            val maximo = when (radioGroup.checkedRadioButtonId) {
                R.id.rb10 -> 10
                R.id.rb50 -> 50
                R.id.rb100 -> 100
                else -> {
                    Toast.makeText(
                        this,
                        "Escolha um limite para o jogo.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
            }

            val intent = Intent(this, JogoActivity::class.java)
            intent.putExtra("nome", nome)
            intent.putExtra("maximo", maximo)
            startActivity(intent)
        }
    }
}
