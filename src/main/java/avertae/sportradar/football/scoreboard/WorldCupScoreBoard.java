package avertae.sportradar.football.scoreboard;

import avertae.sportradar.football.scoreboard.exception.InvalidTeamException;
import avertae.sportradar.football.scoreboard.exception.MatchDoesNotExistException;
import avertae.sportradar.football.scoreboard.model.Match;
import avertae.sportradar.football.scoreboard.model.Summary;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Hello world!
 *
 */
public class WorldCupScoreBoard
{

    private List<Match> matches;


    public Optional<Match> createMatch(String homeTeam, String awayTeam) throws InvalidTeamException
    {
        return Optional.empty();
    }

    public void updateMatch(String homeTeam, String awayTeam, int homeTeamScope, int awayTeamScore)
            throws MatchDoesNotExistException
    {

    }

    public void deleteMatch(String homeTeam, String awayTeam) throws MatchDoesNotExistException
    {

    }

    public Optional<Match> findMatch(String homeTeam, String awayTeam)
    {
        return Optional.empty();
    }

    public Optional<Summary> getSummary()
    {
        return Optional.empty();
    }

}
