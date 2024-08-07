package avertae.sportradar.football.scoreboard.model;

import avertae.sportradar.football.scoreboard.exception.InvalidTeamException;
import org.apache.commons.lang.StringUtils;

public record MatchKey(String homeTeam, String awayTeam)
{

    public MatchKey
    {
        if (StringUtils.isBlank(homeTeam)
                || StringUtils.isBlank(awayTeam)
                || StringUtils.equals(homeTeam, awayTeam))
        {
            throw new InvalidTeamException(homeTeam, awayTeam);
        }
    }

}
