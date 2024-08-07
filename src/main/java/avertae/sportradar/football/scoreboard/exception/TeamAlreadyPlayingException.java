package avertae.sportradar.football.scoreboard.exception;

public class TeamAlreadyPlayingException extends BaseScoreBoardException
{
    public TeamAlreadyPlayingException(String homeTeam, String awayTeam)
    {
        super("At least one of provided teams is already playing: '%s', '%s'"
                .formatted(homeTeam, awayTeam));
    }
}
