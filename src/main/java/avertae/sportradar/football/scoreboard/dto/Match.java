package avertae.sportradar.football.scoreboard.dto;

import avertae.sportradar.football.scoreboard.model.MatchDO;

import java.time.LocalDateTime;

public record Match(String homeTeam, String awayTeam, int homeScore, int awayScore, LocalDateTime start)
{
    public static Match fromMatchDO(MatchDO match)
    {
        return new Match(match.getHomeTeam(), match.getAwayTeam(), match.getHomeTeamScore(),
                match.getAwayTeamScore(), match.getStartTimestamp());
    }
}
