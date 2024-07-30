package avertae.sportradar.football.scoreboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MatchDO
{

    private String homeTeam;
    private String awayTeam;
    int homeTeamScore;
    int awayTeamScore;
    LocalDateTime startTimestamp;

    public MatchDO(String homeTeam, String awayTeam)
    {
        this(homeTeam, awayTeam, 0, 0, LocalDateTime.now());
    }

}
