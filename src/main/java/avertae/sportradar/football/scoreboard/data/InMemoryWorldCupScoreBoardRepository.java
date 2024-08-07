package avertae.sportradar.football.scoreboard.data;

import avertae.sportradar.football.scoreboard.exception.MatchDoesNotExistException;
import avertae.sportradar.football.scoreboard.exception.TeamAlreadyPlayingException;
import avertae.sportradar.football.scoreboard.model.MatchEntity;
import avertae.sportradar.football.scoreboard.model.MatchKey;

import java.util.*;
import java.util.stream.Stream;

public class InMemoryWorldCupScoreBoardRepository implements WorldCupScoreBoardRepository
{

    private final Map<MatchKey, MatchEntity> matches = new HashMap<>();


    @Override
    public MatchEntity create(MatchEntity match) throws TeamAlreadyPlayingException
    {
        if (checkTeamsExists(match.getKey().homeTeam(), match.getKey().awayTeam()))
        {
            throw new TeamAlreadyPlayingException(match.getKey().homeTeam(), match.getKey().awayTeam());
        }
        matches.put(match.getKey(), match);
        return match;
    }

    @Override
    public Optional<MatchEntity> getByKey(MatchKey matchKey)
    {
        return Optional.ofNullable(matches.get(matchKey));
    }

    @Override
    public MatchEntity update(MatchEntity match) throws MatchDoesNotExistException
    {
        if (!matches.containsKey(match.getKey()))
        {
            throw new MatchDoesNotExistException(match.getKey().homeTeam(), match.getKey().awayTeam());
        }
        matches.put(match.getKey(), match);
        return match;
    }

    @Override
    public void delete(MatchEntity match) throws MatchDoesNotExistException
    {
        if (!matches.containsKey(match.getKey()))
        {
            throw new MatchDoesNotExistException(match.getKey().homeTeam(), match.getKey().awayTeam());
        }
        matches.remove(match.getKey());
    }

    @Override
    public List<MatchEntity> getSummaryMatches()
    {
        return matches.values().stream()
                .sorted((m1, m2) ->
                        m2.getStartTimestamp().compareTo(m1.getStartTimestamp()))   // secondary sort by timestamp
                .sorted((m1, m2) -> Integer.compare(
                        m2.getHomeTeamScore() + m2.getAwayTeamScore(),
                        m1.getHomeTeamScore() + m1.getAwayTeamScore()))             // primary sort by total score
                .toList();
    }

    private boolean checkTeamsExists(String... teams)
    {
        var teamSet = Set.of(teams);
        return matches.keySet()
                .stream()
                .flatMap(k -> Stream.of(k.homeTeam(), k.awayTeam()))
                .anyMatch(teamSet::contains);
    }

}
