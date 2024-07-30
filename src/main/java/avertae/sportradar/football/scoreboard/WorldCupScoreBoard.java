package avertae.sportradar.football.scoreboard;

import avertae.sportradar.football.scoreboard.dto.Match;
import avertae.sportradar.football.scoreboard.exception.InvalidTeamException;
import avertae.sportradar.football.scoreboard.exception.MatchDoesNotExistException;
import avertae.sportradar.football.scoreboard.exception.TeamAlreadyPlayingException;
import avertae.sportradar.football.scoreboard.dto.Summary;
import avertae.sportradar.football.scoreboard.model.MatchDO;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.stream.Stream;

public class WorldCupScoreBoard
{

    record MatchKey(String homeTeam, String awayTeam)
    {
    }

    private final Map<MatchKey, MatchDO> matches = new HashMap<>();

    public Optional<Match> createMatch(String homeTeam, String awayTeam)
            throws InvalidTeamException, TeamAlreadyPlayingException
    {
        validateTeams(homeTeam, awayTeam);
        checkTeamsExists(homeTeam, awayTeam);

        var matchKey = new MatchKey(homeTeam, awayTeam);
        var newMatch = new MatchDO(homeTeam, awayTeam);
        matches.put(matchKey, newMatch);
        return Optional.of(Match.fromMatchDO(newMatch));
    }

    public void updateMatch(String homeTeam, String awayTeam, int homeTeamScope, int awayTeamScore)
            throws MatchDoesNotExistException, InvalidTeamException {
        validateTeams(homeTeam, awayTeam);
        var matchKey = new MatchKey(homeTeam, awayTeam);
        checkMatchExists(matchKey);
        var match = matches.get(new MatchKey(homeTeam, awayTeam));
        match.setHomeTeamScore(homeTeamScope);
        match.setAwayTeamScore(awayTeamScore);
    }

    public void deleteMatch(String homeTeam, String awayTeam) throws InvalidTeamException, MatchDoesNotExistException
    {
        validateTeams(homeTeam, awayTeam);
        var matchKey = new MatchKey(homeTeam, awayTeam);
        checkMatchExists(matchKey);
        matches.remove(matchKey);
    }

    public Optional<Match> findMatch(String homeTeam, String awayTeam)
    {
        try
        {
            validateTeams(homeTeam, awayTeam);
            var matchKey = new MatchKey(homeTeam, awayTeam);
            checkMatchExists(matchKey);
            return Optional.of(Match.fromMatchDO(matches.get(matchKey)));
        }
        catch (InvalidTeamException | MatchDoesNotExistException _)
        {
        }
        return Optional.empty();
    }

    public Optional<Summary> getSummary()
    {
        var sortedMatches = matches.values().stream()
                .sorted((m1, m2) ->
                        m2.getStartTimestamp().compareTo(m1.getStartTimestamp()))   // secondary sort by timestamp
                .sorted((m1, m2) -> Integer.compare(
                        m2.getHomeTeamScore() + m2.getAwayTeamScore(),
                        m1.getHomeTeamScore() + m1.getAwayTeamScore()))             // primary sort by total score
                .map(Match::fromMatchDO)                                            // return immutable DTO objects
                .toList();
        return Optional.of(new Summary(sortedMatches));
    }

    private void validateTeams(String homeTeam, String awayTeam) throws InvalidTeamException
    {
        if (StringUtils.isBlank(homeTeam)
                || StringUtils.isBlank(awayTeam)
                || StringUtils.equals(homeTeam, awayTeam))
            throw new InvalidTeamException("Provided teams are invalid: '%s', '%s'".formatted(homeTeam, awayTeam));
    }

    private void checkMatchExists(MatchKey matchKey) throws MatchDoesNotExistException
    {
        if (!matches.containsKey(matchKey))
            throw new MatchDoesNotExistException("Teams '%s', '%s' are not currently playing together"
                    .formatted(matchKey.homeTeam(), matchKey.awayTeam()));
    }

    private void checkTeamsExists(String... teams) throws TeamAlreadyPlayingException
    {
        var teamSet = Set.of(teams);
        var exists = matches.keySet()
                .stream()
                .flatMap(key -> Stream.of(key.homeTeam, key.awayTeam))
                .anyMatch(teamSet::contains);
        if (exists)
            throw new TeamAlreadyPlayingException("At least one of provided teams is already playing");
    }
}
