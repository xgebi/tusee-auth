package app.tusee.models

import java.time.Instant

case class Board(boardUuid: String, name: String, description: String, owner: String, created: Instant, columns: String)
