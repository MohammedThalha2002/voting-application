package com.example.votingapplication.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Rotation
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.models.Shape
import nl.dionsegijn.konfetti.core.models.Size
import java.util.concurrent.TimeUnit

@Composable
fun Confetti(){
    KonfettiView(
        modifier = Modifier.fillMaxSize().zIndex(1F),
        parties = festive(),
    )
}


fun festive(drawable: Shape.DrawableShape? = null): List<Party> {
    val party = Party(
        speed = 30f,
        maxSpeed = 50f,
        damping = 0.9f,
        angle = Angle.TOP,
        spread = 45,
        size = listOf(Size.SMALL, Size.LARGE, Size.LARGE),
        shapes = listOf(Shape.Square, Shape.Circle, drawable).filterNotNull(),
        timeToLive = 3000L,
        rotation = Rotation(),
        colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
        emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(30),
        position = Position.Relative(0.5, 1.0)
    )

    return listOf(
        party,
        party.copy(
            speed = 55f,
            maxSpeed = 65f,
            spread = 10,
            emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(10),
        ),
        party.copy(
            speed = 50f,
            maxSpeed = 60f,
            spread = 120,
            emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(40),
        ),
        party.copy(
            speed = 65f,
            maxSpeed = 80f,
            spread = 10,
            emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(10),
        )
    )
}
