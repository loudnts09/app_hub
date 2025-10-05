package com.example.hubapplication.hoopscore

import android.os.Bundle
import android.widget.Button
import com.example.hubapplication.R
import androidx.appcompat.app.AppCompatActivity

class HoopscoreActivity : AppCompatActivity() {

    private val STREAK_THRESHOLD = 3
    private lateinit var teamA: Team
    private lateinit var teamB: Team

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hoopscore)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Inicializa times
        teamA = Team(
            name = "Time A",
            scoreView = findViewById(R.id.placarTimeA),
            fireView = findViewById(R.id.fireA),
            nameView = findViewById(R.id.timeA)
        )

        teamB = Team(
            name = "Time B",
            scoreView = findViewById(R.id.placarTimeB),
            fireView = findViewById(R.id.fireB),
            nameView = findViewById(R.id.timeB)
        )

        // Botões Time A
        findViewById<Button>(R.id.tresPontosA).setOnClickListener { addPoints(teamA, teamB, 3) }
        findViewById<Button>(R.id.doisPontosA).setOnClickListener { addPoints(teamA, teamB, 2) }
        findViewById<Button>(R.id.tiroLivreA).setOnClickListener { addPoints(teamA, teamB, 1) }

        // Botões Time B
        findViewById<Button>(R.id.tresPontosB).setOnClickListener { addPoints(teamB, teamA, 3) }
        findViewById<Button>(R.id.doisPontosB).setOnClickListener { addPoints(teamB, teamA, 2) }
        findViewById<Button>(R.id.tiroLivreB).setOnClickListener { addPoints(teamB, teamA, 1) }

        // Reiniciar
        findViewById<Button>(R.id.reiniciarPartida).setOnClickListener { resetGame() }

        // Editar nomes
        teamA.nameView.setOnClickListener { editTeamName(teamA) }
        teamB.nameView.setOnClickListener { editTeamName(teamB) }
    }

    private fun addPoints(teamScored: Team, otherTeam: Team, points: Int) {
        teamScored.addPoints(points, STREAK_THRESHOLD)
        otherTeam.resetStreak()
    }

    private fun resetGame() {
        teamA.reset()
        teamB.reset()

        android.app.AlertDialog.Builder(this)
            .setTitle("Placar reiniciado")
            .setMessage("O placar foi zerado com sucesso!")
            .setIcon(R.mipmap.ic_launcher)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun editTeamName(team: Team) {
        val input = android.widget.EditText(this).apply {
            setText(team.nameView.text.toString())
        }

        android.app.AlertDialog.Builder(this)
            .setTitle("Alterar nome do time")
            .setView(input)
            .setPositiveButton("OK") { _, _ ->
                val novoNome = input.text.toString().trim()
                if (novoNome.isNotBlank()) {
                    team.name = novoNome
                    team.nameView.text = novoNome
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}