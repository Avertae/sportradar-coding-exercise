package avertae.sportradar.football.scoreboard.exception;

public class InvalidTeamException extends RuntimeException
{
    public InvalidTeamException(String homeTeam, String awayTeam)
    {
        super("Provided teams are invalid: '%s', '%s".formatted(homeTeam, awayTeam));
    }
}
