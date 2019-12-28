package ru.kpfu.game.Help;

public class FindPath {
    public String findPath(int oldX, int oldY, int newX, int newY) {
        if (newX + 1 == oldX) return Args.IMAGE_PATH_PLAYER_LEFT;
        else if (newX - 1 == oldX) return Args.IMAGE_PATH_PLAYER_RIGHT;
        else if (newY + 1 == oldY) return Args.IMAGE_PATH_PLAYER_UP;
        else if (newY - 1 == oldY) return Args.IMAGE_PATH_PLAYER_DOWN;
        return Args.IMAGE_PATH_PLAYER_UP;
    }
}
