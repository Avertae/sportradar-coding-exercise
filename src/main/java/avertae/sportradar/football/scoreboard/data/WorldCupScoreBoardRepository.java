package avertae.sportradar.football.scoreboard.data;

import avertae.sportradar.football.scoreboard.exception.MatchDoesNotExistException;
import avertae.sportradar.football.scoreboard.exception.TeamAlreadyPlayingException;
import avertae.sportradar.football.scoreboard.model.MatchEntity;
import avertae.sportradar.football.scoreboard.model.MatchKey;

import java.util.List;
import java.util.Optional;

public interface WorldCupScoreBoardRepository
{
    MatchEntity create(MatchEntity match) throws TeamAlreadyPlayingException;
    Optional<MatchEntity> getByKey(MatchKey matchKey);
    MatchEntity update(MatchEntity match) throws MatchDoesNotExistException;
    void delete(MatchEntity match) throws MatchDoesNotExistException;
    List<MatchEntity> getSummaryMatches();
}
