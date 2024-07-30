package avertae.sportradar.football.scoreboard.mapper;

public interface ModelMapper<S, T>
{
    T forward(S source);
    S backward(T target);
}
