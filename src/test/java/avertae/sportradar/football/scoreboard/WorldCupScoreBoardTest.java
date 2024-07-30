package avertae.sportradar.football.scoreboard;

import avertae.sportradar.football.scoreboard.dto.Match;
import avertae.sportradar.football.scoreboard.dto.Summary;
import avertae.sportradar.football.scoreboard.exception.InvalidTeamException;
import avertae.sportradar.football.scoreboard.exception.MatchDoesNotExistException;
import avertae.sportradar.football.scoreboard.exception.TeamAlreadyPlayingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
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
        assertEquals(HOME_TEAM, match.get().homeTeam());
        assertEquals(AWAY_TEAM, match.get().awayTeam());
        assertEquals(INITIAL_SCORE, match.get().homeScore());
        assertEquals(INITIAL_SCORE, match.get().awayScore());
    }

    @Test
    public void createMatch_whenCalledWithInvalidParameters_shouldThrowException()
    {
        assertThrows(InvalidTeamException.class, () -> scoreBoard.createMatch(null, AWAY_TEAM));
        assertThrows(InvalidTeamException.class, () -> scoreBoard.createMatch(HOME_TEAM, ""));
        assertThrows(InvalidTeamException.class, () -> scoreBoard.createMatch(HOME_TEAM, HOME_TEAM));
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
        assertEquals(HOME_TEAM, match.get().homeTeam());
        assertEquals(AWAY_TEAM, match.get().awayTeam());
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
        assertEquals(UPDATED_SCORE_1, match.get().homeScore());
        assertEquals(UPDATED_SCORE_2, match.get().awayScore());
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
        assertNotNull(summary.get().matches());
        assertEquals(0, summary.get().matches().size());
    }

    @Test
    public void getSummary_whenCalledOnFilledScoreBoard_shouldReturnCorrectMatches()
    {
        assertDoesNotThrow(() -> scoreBoard.createMatch(HOME_TEAM, AWAY_TEAM));
        assertDoesNotThrow(() -> scoreBoard.createMatch(HOME_TEAM_2, AWAY_TEAM_2));
        assertDoesNotThrow(() -> scoreBoard.createMatch(HOME_TEAM_3, AWAY_TEAM_3));
        Optional<Summary> summary = scoreBoard.getSummary();
        assertTrue(summary.isPresent());
        assertNotNull(summary.get().matches());
        assertEquals(3, summary.get().matches().size());
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
        assertNotNull(summary.get().matches());
        assertEquals(3, summary.get().matches().size());
        assertEquals(HOME_TEAM_3, summary.get().matches().get(0).homeTeam());
        assertEquals(HOME_TEAM, summary.get().matches().get(1).homeTeam());
        assertEquals(HOME_TEAM_2, summary.get().matches().get(2).homeTeam());
    }

    /*
     * Example test from requirements
     */
    @Test
    public void getSummary_whenCalledOnScoreBoardFromExample_shouldMatchToExampleResult()
    {
        assertDoesNotThrow(() -> scoreBoard.createMatch("Mexico", "Canada"));
        assertDoesNotThrow(() -> scoreBoard.createMatch("Spain", "Brasil"));
        assertDoesNotThrow(() -> scoreBoard.createMatch("Germany", "France"));
        assertDoesNotThrow(() -> scoreBoard.createMatch("Uruguay", "Italy"));
        assertDoesNotThrow(() -> scoreBoard.createMatch("Argentina", "Australia"));
        assertDoesNotThrow(() -> scoreBoard.updateMatch("Mexico", "Canada", 0, 5));
        assertDoesNotThrow(() -> scoreBoard.updateMatch("Spain", "Brasil", 10, 2));
        assertDoesNotThrow(() -> scoreBoard.updateMatch("Germany", "France", 2, 2));
        assertDoesNotThrow(() -> scoreBoard.updateMatch("Uruguay", "Italy", 6, 6));
        assertDoesNotThrow(() -> scoreBoard.updateMatch("Argentina", "Australia", 3 ,1));
        Optional<Summary> summary = scoreBoard.getSummary();
        assertTrue(summary.isPresent());
        List<Match> matches = summary.get().matches();
        assertEquals(5, matches.size());
        assertEquals("Uruguay", matches.get(0).homeTeam());
        assertEquals("Spain", matches.get(1).homeTeam());
        assertEquals("Mexico", matches.get(2).homeTeam());
        assertEquals("Argentina", matches.get(3).homeTeam());
        assertEquals("Germany", matches.get(4).homeTeam());
    }

}
