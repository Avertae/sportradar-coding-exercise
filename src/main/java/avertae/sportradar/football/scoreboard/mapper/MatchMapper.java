package avertae.sportradar.football.scoreboard.mapper;

import avertae.sportradar.football.scoreboard.dto.Match;
import avertae.sportradar.football.scoreboard.model.MatchEntity;

public class MatchMapper implements ModelMapper<MatchEntity, Match>
{
    @Override
    public Match forward(MatchEntity source)
    {
        return new Match(source.getHomeTeam(), source.getAwayTeam(), source.getHomeTeamScore(),
                source.getAwayTeamScore(), source.getStartTimestamp());
    }

    @Override
    public MatchEntity backward(Match target)
    {
        throw new RuntimeException("Not implemented");
    }
}
