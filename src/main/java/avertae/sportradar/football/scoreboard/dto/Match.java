package avertae.sportradar.football.scoreboard.dto;

import java.time.LocalDateTime;

public record Match(String homeTeam, String awayTeam, int homeScore, int awayScore, LocalDateTime start)
{
}
