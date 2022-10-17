import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WMItem implements INameHandler {
    
    private int itemID;
    private String name;
    private Rarity rarity;
    private Material base;
    private EquipmentSlot slot;

    int _score = 0;

    Map<WMPlayer.Statistics, Integer> Stats = new HashMap<>();

    abstract void onUse(PlayerInteractEvent e); //Called when right-clicked with the item in hand - designed for off-hands
    abstract void onHit(EntityDamageByEntityEvent e); //Called when the weapon/projectile hits the target
    abstract void onSwing(PlayerInteractEvent e); //Called when the weapon is swung or shot
    abstract void onStruck(EntityDamageByEntityEvent e); //Called when the owner of the item (must be equipped) is hit (Called before onHit() )

    public enum Rarity{
        Common,
        Uncommon,
        Rare,
        Epic,
        Legendary
    }
    public enum EquipmentSlot{
        Gear,
        Main,
        Ranged,
        Off,
        None,
        Gem
    }

    public WMItem(int ID, Material base, EquipmentSlot slot, String name, Rarity rarity, int score, Map<WMPlayer.Statistics, Integer> stats){
        itemID = ID;
        this.base = base;
        _score = score;
        this.name = name;
        this.rarity = rarity;
        this.slot = slot;
        Stats.putAll(stats);

        if(slot == EquipmentSlot.Main && !Stats.containsKey(WMPlayer.Statistics.AttackSpeed)){//it's a weapon and it didn't have it's weapon speed set
            Stats.put(WMPlayer.Statistics.AttackSpeed, 1); //One strike per second
        }

    }
    public WMItem(int ID, Material base, String name, Rarity rarity){
        itemID = ID;
        this.base = base;
        this.name = name;
        this.rarity = rarity;
        slot = EquipmentSlot.None;
    }
    public String GetName(){
        return switch (rarity) {
            case Uncommon -> ChatColor.LIGHT_PURPLE + name;
            case Rare -> ChatColor.GREEN + name;
            case Epic -> ChatColor.DARK_BLUE + name;
            case Legendary -> ChatColor.DARK_PURPLE + name;
            default -> ChatColor.WHITE + name;
        };
    }
    public String[] GetBaseDescription() {
        //Todo: Add the upper part of description here (Armor, damage, speed, level)
        List<String> ret = new ArrayList<>();

        if (_score != 0) {
            ret.add(ChatColor.YELLOW + "Gear Score: " + _score);
        }
        if (slot == EquipmentSlot.Main) {
            int val = GetStatValue(WMPlayer.Statistics.DamageDiceValue);
            ret.add(ChatColor.WHITE + "Damage: " + (1 + val) + " - " + (GetStatValue(WMPlayer.Statistics.DamageDiceAmount)) * val + ", " + GetStatValue(WMPlayer.Statistics.AttackSpeed) + " swings per second");
            ret.add(ChatColor.WHITE + "( Avg " + (GetStatValue(WMPlayer.Statistics.DamageDiceAmount) / 2 + val) * GetStatValue(WMPlayer.Statistics.AttackSpeed) + " damage per second)");
        } else if (slot == EquipmentSlot.Ranged) {
            int val = GetStatValue(WMPlayer.Statistics.DamageDiceValue);
            ret.add(ChatColor.WHITE + "Damage: " + (1 + val) + " - " + (GetStatValue(WMPlayer.Statistics.DamageDiceAmount)) * val);
            ret.add(ChatColor.WHITE + "( Avg " + (GetStatValue(WMPlayer.Statistics.DamageDiceAmount) / 2 + val) + " damage per shot)");
        }
        //TODO: Finish this for shields, off hands, gear and other items


        return (String[]) ret.toArray();
    }
    private String[] GetBaseStatsDescription(){
        //Todo: Add the middle part of description (Vitality, Power and such)
        return null;
    }
    private String[] GetSecondaryStatsDescription(){
        //Todo: Add the bottom part of the description (Secondary stats)
        return null;
    }

    public Rarity GetRarity(){
        return rarity;
    }
    public Material GetMaterial(){
        return base;
    }
    public Map<WMPlayer.Statistics, Integer>  GetAllStats(){
        return Stats;
    }
    public int GetStatValue(WMPlayer.Statistics type){
        return Stats.getOrDefault(type, 0);
    }
    public EquipmentSlot GetSlot(){
        return slot;
    }
    public void AssignStat(WMPlayer.Statistics type, int amount){
        Stats.put(type, amount);
    }

    //STATIC
    // TODO: Remake database into an ItemManager
    static List<WMItem> Database = new ArrayList<>();
    public static NamespacedKey ExtendedIDKey = new NamespacedKey(Warmachina.Main, "ExtendedID");

    public static WMItem Get(int id){
        return Database.get(id);
    }

}
