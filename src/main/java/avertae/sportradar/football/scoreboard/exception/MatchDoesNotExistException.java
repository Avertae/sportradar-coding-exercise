package avertae.sportradar.football.scoreboard.exception;

public class MatchDoesNotExistException extends BaseScoreBoardException
{
    public MatchDoesNotExistException(String homeTeam, String awayTeam)
    {
        super("Teams '%s', '%s' are not currently playing together"
                .formatted(homeTeam, awayTeam));
    }
}
