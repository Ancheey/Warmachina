import org.bukkit.plugin.java.JavaPlugin;

public class Warmachina extends JavaPlugin {
    public static Warmachina Main;

    @Override
    public void onLoad() {
        super.onLoad();

        Main = this;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        getServer().getPluginManager().registerEvents(new CombatEvents(), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
