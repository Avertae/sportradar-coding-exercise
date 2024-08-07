package avertae.sportradar.football.scoreboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Summary
{
    private List<Match> matches;
    LocalDateTime creationTime;

    public Summary(List<Match> matches)
    {
        this(matches, LocalDateTime.now());
    }
}
