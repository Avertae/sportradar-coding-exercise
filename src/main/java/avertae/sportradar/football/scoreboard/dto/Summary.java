package avertae.sportradar.football.scoreboard.dto;

import java.time.LocalDateTime;
import java.util.List;

public record Summary(List<Match> matches, LocalDateTime creationTime)
{
    public Summary(List<Match> matches)
    {
        this(matches, LocalDateTime.now());
    }
}
