package improvedspleef;

import org.bukkit.entity.Player;

public class Team {
    private int points;
    private Player player;

    public Team(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPoints() {
        return points;
    }

    public void addPoint(int add) {
        this.points += add;
    }
}
