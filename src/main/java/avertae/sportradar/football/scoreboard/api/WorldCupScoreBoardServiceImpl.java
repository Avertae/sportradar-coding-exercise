package avertae.sportradar.football.scoreboard.api;

import avertae.sportradar.football.scoreboard.data.InMemoryWorldCupScoreBoardRepository;
import avertae.sportradar.football.scoreboard.data.WorldCupScoreBoardRepository;
import avertae.sportradar.football.scoreboard.dto.Match;
import avertae.sportradar.football.scoreboard.dto.Summary;
import avertae.sportradar.football.scoreboard.exception.InvalidTeamException;
import avertae.sportradar.football.scoreboard.exception.MatchDoesNotExistException;
import avertae.sportradar.football.scoreboard.exception.TeamAlreadyPlayingException;
import avertae.sportradar.football.scoreboard.mapper.MatchMapper;
import avertae.sportradar.football.scoreboard.model.MatchEntity;
import avertae.sportradar.football.scoreboard.model.MatchKey;

import java.util.Optional;

public class WorldCupScoreBoardServiceImpl implements WorldCupScoreBoardService
{

    private final WorldCupScoreBoardRepository repo = new InMemoryWorldCupScoreBoardRepository();
    private final MatchMapper matchMapper = new MatchMapper();

    public Optional<Match> createMatch(String homeTeam, String awayTeam)
            throws InvalidTeamException, TeamAlreadyPlayingException
    {
        var matchKeyToCreate = new MatchKey(homeTeam, awayTeam);
        var createdMatch = repo.create(new MatchEntity(matchKeyToCreate));
        return Optional.of(matchMapper.forward(createdMatch));
    }

    public void updateMatch(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore)
            throws MatchDoesNotExistException, InvalidTeamException
    {
        var matchKeyToUpdate = new MatchKey(homeTeam, awayTeam);
        var match = repo.getByKey(matchKeyToUpdate)
                .orElseThrow(() -> new MatchDoesNotExistException(homeTeam, awayTeam));
        match.setHomeTeamScore(homeTeamScore);
        match.setAwayTeamScore(awayTeamScore);
        repo.update(match);
    }

    public void deleteMatch(String homeTeam, String awayTeam) throws InvalidTeamException, MatchDoesNotExistException
    {
        var matchKeyToDelete = new MatchKey(homeTeam, awayTeam);
        var match = repo.getByKey(matchKeyToDelete)
                .orElseThrow(() -> new MatchDoesNotExistException(homeTeam, awayTeam));
        repo.delete(match);
    }

    public Optional<Match> findMatch(String homeTeam, String awayTeam)
    {
        Optional<Match> result = Optional.empty();
        try
        {
            var matchKeyToFind = new MatchKey(homeTeam, awayTeam);
            var match = repo.getByKey(matchKeyToFind);
            if (match.isPresent())
                result = Optional.of(matchMapper.forward(match.get()));

        } catch (InvalidTeamException _)
        {
        }

        return result;
    }

    public Optional<Summary> getSummary()
    {
        var matches = repo.getSummaryMatches()
                .stream()
                .map(matchMapper::forward)
                .toList();
        return Optional.of(new Summary(matches));
    }

}
