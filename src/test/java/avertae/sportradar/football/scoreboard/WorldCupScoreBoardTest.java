package avertae.sportradar.football.scoreboard;

import avertae.sportradar.football.scoreboard.exception.InvalidTeamException;
import avertae.sportradar.football.scoreboard.exception.MatchDoesNotExistException;
import avertae.sportradar.football.scoreboard.exception.TeamAlreadyPlayingException;
import avertae.sportradar.football.scoreboard.model.Match;
import avertae.sportradar.football.scoreboard.model.Summary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class WorldCupScoreBoardTest
{

    private WorldCupScoreBoard scoreBoard;

    public static final String HOME_TEAM = "home";
    public static final String AWAY_TEAM = "away";
    public static final String HOME_TEAM_2 = "home2";
    public static final String AWAY_TEAM_2 = "away2";
    public static final String HOME_TEAM_3 = "home3";
    public static final String AWAY_TEAM_3 = "away3";
    public static final int INITIAL_SCORE = 0;
    public static final int UPDATED_SCORE_1 = 1;
    public static final int UPDATED_SCORE_2 = 2;


    @BeforeEach
    public void init()
    {
        scoreBoard = new WorldCupScoreBoard();
    }

    @Test
    public void createMatch_whenCalled_shouldReturnMatch()
    {
        Optional<Match> match = assertDoesNotThrow(() -> scoreBoard.createMatch(HOME_TEAM, AWAY_TEAM));
        assertTrue(match.isPresent());
        assertEquals(HOME_TEAM, match.get().getHomeTeam());
        assertEquals(AWAY_TEAM, match.get().getAwayTeam());
        assertEquals(INITIAL_SCORE, match.get().getHomeTeamScore());
        assertEquals(INITIAL_SCORE, match.get().getAwayTeamScore());
    }

    @Test
    public void createMatch_whenCalledWithInvalidParameters_shouldThrowException()
    {
        assertThrows(InvalidTeamException.class, () -> scoreBoard.createMatch(null, AWAY_TEAM));
        assertThrows(InvalidTeamException.class, () -> scoreBoard.createMatch(HOME_TEAM, ""));
    }

    @Test
    public void createMatch_whenCalledWithExistingTeam_shouldThrowException()
    {
        assertDoesNotThrow(() -> scoreBoard.createMatch(HOME_TEAM, AWAY_TEAM));
        assertThrows(TeamAlreadyPlayingException.class, () -> scoreBoard.createMatch(HOME_TEAM, AWAY_TEAM));
    }

    @Test
    public void findMatch_whenCalledOnEmptyScoreBoard_shouldReturnEmptyResult()
    {
        Optional<Match> match = scoreBoard.findMatch(HOME_TEAM, AWAY_TEAM);
        assertTrue(match.isEmpty());
    }

    @Test
    public void findMatch_whenCalledOnScoreBoard_shouldReturnMatchedResult()
    {
        assertDoesNotThrow(() -> scoreBoard.createMatch(HOME_TEAM, AWAY_TEAM));
        Optional<Match> match = scoreBoard.findMatch(HOME_TEAM, AWAY_TEAM);
        assertTrue(match.isPresent());
        assertEquals(HOME_TEAM, match.get().getHomeTeam());
        assertEquals(AWAY_TEAM, match.get().getAwayTeam());
    }

    @Test
    public void findMatch_whenCalledWithNonExistingTeams_shouldReturnEmptyResult()
    {
        assertDoesNotThrow(() -> scoreBoard.createMatch(AWAY_TEAM, HOME_TEAM));
        Optional<Match> match = scoreBoard.findMatch(HOME_TEAM, AWAY_TEAM);
        assertTrue(match.isEmpty());
    }

    @Test
    public void updateMatch_whenCalledOnEmptyScoreBoard_shouldThrowException()
    {
        assertThrows(MatchDoesNotExistException.class, () ->
                scoreBoard.updateMatch(AWAY_TEAM, HOME_TEAM, UPDATED_SCORE_1, UPDATED_SCORE_2));
    }

    @Test
    public void updateMatch_whenCalledOnExistingMatch_shouldCorrectlyUpdateScore()
    {
        assertDoesNotThrow(() -> scoreBoard.createMatch(HOME_TEAM, AWAY_TEAM));
        assertDoesNotThrow(() -> scoreBoard.updateMatch(HOME_TEAM, AWAY_TEAM, UPDATED_SCORE_1, UPDATED_SCORE_2));
        Optional<Match> match = assertDoesNotThrow(() -> scoreBoard.findMatch(HOME_TEAM, AWAY_TEAM));
        assertTrue(match.isPresent());
        assertEquals(UPDATED_SCORE_1, match.get().getHomeTeamScore());
        assertEquals(UPDATED_SCORE_2, match.get().getAwayTeamScore());
    }

    @Test
    public void deleteMatch_whenCalledOnEmptyScoreBoard_shouldThrowException()
    {
        assertThrows(MatchDoesNotExistException.class, () -> scoreBoard.deleteMatch(HOME_TEAM, AWAY_TEAM));
    }

    @Test
    public void deleteMatch_whenCalledOnExistingMatch_shouldCorrectlyDelete()
    {
        assertDoesNotThrow(() -> scoreBoard.createMatch(HOME_TEAM, AWAY_TEAM));
        assertDoesNotThrow(() -> scoreBoard.deleteMatch(HOME_TEAM, AWAY_TEAM));
        Optional<Match> match = scoreBoard.findMatch(HOME_TEAM, AWAY_TEAM);
        assertTrue(match.isEmpty());
    }

    @Test
    public void getSummary_whenCalledOnEmptyScoreBoard_shouldReturnZeroMatches()
    {
        Optional<Summary> summary = scoreBoard.getSummary();
        assertTrue(summary.isPresent());
        assertNotNull(summary.get().getMatches());
        assertEquals(0, summary.get().getMatches().size());
    }

    @Test
    public void getSummary_whenCalledOnFilledScoreBoard_shouldReturnCorrectMatches()
    {
        assertDoesNotThrow(() -> scoreBoard.createMatch(HOME_TEAM, AWAY_TEAM));
        assertDoesNotThrow(() -> scoreBoard.createMatch(HOME_TEAM_2, AWAY_TEAM_2));
        assertDoesNotThrow(() -> scoreBoard.createMatch(HOME_TEAM_3, AWAY_TEAM_3));
        Optional<Summary> summary = scoreBoard.getSummary();
        assertTrue(summary.isPresent());
        assertNotNull(summary.get().getMatches());
        assertEquals(3, summary.get().getMatches().size());
    }

    @Test
    public void getSummary_whenCalledOnFilledScoreBoard_shouldReturnCorrectlySortedMatches()
    {
        assertDoesNotThrow(() -> scoreBoard.createMatch(HOME_TEAM, AWAY_TEAM));
        assertDoesNotThrow(() -> scoreBoard.createMatch(HOME_TEAM_2, AWAY_TEAM_2));
        assertDoesNotThrow(() -> scoreBoard.createMatch(HOME_TEAM_3, AWAY_TEAM_3));
        assertDoesNotThrow(() -> scoreBoard.updateMatch(HOME_TEAM_2, AWAY_TEAM_2, 2, 1));
        assertDoesNotThrow(() -> scoreBoard.updateMatch(HOME_TEAM, AWAY_TEAM, 5, 5));
        assertDoesNotThrow(() -> scoreBoard.updateMatch(HOME_TEAM_3, AWAY_TEAM_3, 10, 0));
        Optional<Summary> summary = scoreBoard.getSummary();
        assertTrue(summary.isPresent());
        assertNotNull(summary.get().getMatches());
        assertEquals(3, summary.get().getMatches().size());
        assertEquals(HOME_TEAM_3, summary.get().getMatches().get(0).getHomeTeam());
        assertEquals(HOME_TEAM, summary.get().getMatches().get(1).getHomeTeam());
        assertEquals(HOME_TEAM_2, summary.get().getMatches().get(2).getHomeTeam());
    }

}
