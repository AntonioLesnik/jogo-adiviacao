package com.example.jogoadivinhacao

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs
import kotlin.random.Random

class JogoActivity : AppCompatActivity() {

    private lateinit var txtBoasVindas: TextView
    private lateinit var txtDica: TextView
    private lateinit var txtTentativas: TextView
    private lateinit var edtPalpite: EditText
    private lateinit var btnChutar: Button
    private lateinit var btnJogarNovamente: Button

    private var nome: String = ""
    private var maximo: Int = 50
    private var numeroSecreto: Int = 0
    private var tentativas: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogo)

        txtBoasVindas = findViewById(R.id.txtBoasVindas)
        txtDica = findViewById(R.id.txtDica)
        txtTentativas = findViewById(R.id.txtTentativas)
        edtPalpite = findViewById(R.id.edtPalpite)
        btnChutar = findViewById(R.id.btnChutar)
        btnJogarNovamente = findViewById(R.id.btnJogarNovamente)

        nome = intent.getStringExtra("nome") ?: "Jogador"
        maximo = intent.getIntExtra("maximo", 50)

        iniciarJogo()

        btnChutar.setOnClickListener {
            verificarPalpite()
        }

        btnJogarNovamente.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }

    private fun iniciarJogo() {
        numeroSecreto = Random.nextInt(1, maximo + 1)
        tentativas = 0

        txtBoasVindas.text = "$nome, pensei em um número de 1 a $maximo!"
        txtDica.text = "Digite seu palpite e tente acertar."
        txtTentativas.text = "Tentativas: 0"

        edtPalpite.isEnabled = true
        btnChutar.isEnabled = true
        btnJogarNovamente.visibility = Button.GONE
        edtPalpite.text.clear()
        edtPalpite.requestFocus()
    }

    private fun verificarPalpite() {
        val texto = edtPalpite.text.toString().trim()

        if (texto.isEmpty()) {
            edtPalpite.error = "Digite um número"
            return
        }

        val palpite = texto.toIntOrNull()

        if (palpite == null) {
            edtPalpite.error = "Digite somente números"
            return
        }

        if (palpite !in 1..maximo) {
            edtPalpite.error = "Digite um número entre 1 e $maximo"
            return
        }

        tentativas++

        when {
            palpite == numeroSecreto -> {
                txtDica.text = "$nome acertou em $tentativas tentativas!"
                txtTentativas.text = "🎉 Parabéns! Número secreto: $numeroSecreto"
                finalizarJogo()
            }

            else -> {
                val diferenca = abs(numeroSecreto - palpite)
                val limiteQuente = maximo * 0.10

                val intensidade = if (diferenca <= limiteQuente) {
                    "Quente!"
                } else {
                    "Frio!"
                }

                val direcao = if (numeroSecreto > palpite) {
                    "O número é MAIOR."
                } else {
                    "O número é MENOR."
                }

                txtDica.text = "$intensidade $direcao"
                txtTentativas.text = "Tentativas: $tentativas"
                edtPalpite.text.clear()
                edtPalpite.requestFocus()
            }
        }
    }

    private fun finalizarJogo() {
        edtPalpite.isEnabled = false
        btnChutar.isEnabled = false
        btnJogarNovamente.visibility = Button.VISIBLE
    }
}
