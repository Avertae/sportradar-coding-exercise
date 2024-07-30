package avertae.sportradar.football.scoreboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class Summary {
    List<Match> matches;
    LocalDateTime generationTimestamp;
}
