package avertae.sportradar.football.scoreboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Match {

    private String homeTeam;
    private String awayTeam;

    @Setter
    int homeTeamScore;
    @Setter
    int awayTeamScore;

    LocalDateTime startTimestamp;

}
