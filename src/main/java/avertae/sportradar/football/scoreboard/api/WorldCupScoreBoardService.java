package avertae.sportradar.football.scoreboard.api;

import avertae.sportradar.football.scoreboard.dto.Match;
import avertae.sportradar.football.scoreboard.dto.Summary;
import avertae.sportradar.football.scoreboard.exception.InvalidTeamException;
import avertae.sportradar.football.scoreboard.exception.MatchDoesNotExistException;
import avertae.sportradar.football.scoreboard.exception.TeamAlreadyPlayingException;

import java.util.Optional;

public interface WorldCupScoreBoardService
{

    Optional<Match> createMatch(String homeTeam, String awayTeam)
            throws InvalidTeamException, TeamAlreadyPlayingException;

    void updateMatch(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore)
            throws MatchDoesNotExistException, InvalidTeamException;

    void deleteMatch(String homeTeam, String awayTeam) throws InvalidTeamException, MatchDoesNotExistException;

    Optional<Match> findMatch(String homeTeam, String awayTeam);

    Optional<Integer> findTeamScore(String team);

    Optional<Summary> getSummary();



}
