package avertae.sportradar.football.scoreboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MatchEntity
{

    private String homeTeam;
    private String awayTeam;
    int homeTeamScore;
    int awayTeamScore;
    LocalDateTime startTimestamp;

    public MatchEntity(String homeTeam, String awayTeam)
    {
        this(homeTeam, awayTeam, 0, 0, LocalDateTime.now());
    }

}
