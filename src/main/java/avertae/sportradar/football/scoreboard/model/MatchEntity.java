package avertae.sportradar.football.scoreboard.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MatchEntity
{

    private MatchKey key;
    private int homeTeamScore;
    private int awayTeamScore;
    private LocalDateTime startTimestamp;

    public MatchEntity(MatchKey key)
    {
        this(key, 0, 0, LocalDateTime.now());
    }

}
