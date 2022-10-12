import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WMItem implements INameHandler {
    
    int _itemID;
    int a;
    int b;
    String _name;
    Rarity _rarity;
    Material _base;
    EquipmentSlot _slot;

    int _score = 0;

    Map<PlayerCard.Statistics, Integer> _Stats = new HashMap<>();

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

    public WMItem(int ID, Material base, EquipmentSlot slot, String name, Rarity rarity, int score, Map<PlayerCard.Statistics, Integer> stats){
        _itemID = ID;
        _base = base;
        _score = score;
        _name = name;
        _rarity = rarity;
        _slot = slot;
        _Stats.putAll(stats);

        if(slot == EquipmentSlot.Main && !_Stats.containsKey(PlayerCard.Statistics.AttackSpeed)){//it's a weapon, and it didn't have its weapon speed set
            _Stats.put(PlayerCard.Statistics.AttackSpeed, 1); //One strike per second
        }

    }
    public WMItem(int ID, Material base, String name, Rarity rarity){
        _itemID = ID;
        _base = base;
        _name = name;
        _rarity = rarity;
        _slot = EquipmentSlot.None;
    }
    public String GetName(){
        return switch (_rarity) {
            case Uncommon -> ChatColor.LIGHT_PURPLE + _name;
            case Rare -> ChatColor.GREEN + _name;
            case Epic -> ChatColor.DARK_BLUE + _name;
            case Legendary -> ChatColor.DARK_PURPLE + _name;
            default -> ChatColor.WHITE + _name;
        };
    }
    public String[] GetBaseDescription() {
        //Todo: Add the upper part of description here (Armor, damage, speed, level)
        List<String> ret = new ArrayList<>();

        if (_score != 0) {
            ret.add(ChatColor.YELLOW + "Gear Score: " + _score);
        }
        if (_slot == EquipmentSlot.Main) {
            int val = GetStatValue(PlayerCard.Statistics.DamageDiceValue);
            ret.add(ChatColor.WHITE + "Damage: " + (1 + val) + " - " + (GetStatValue(PlayerCard.Statistics.DamageDiceAmount)) * val + ", " + GetStatValue(PlayerCard.Statistics.AttackSpeed) + " swings per second");
            ret.add(ChatColor.WHITE + "( Avg " + (GetStatValue(PlayerCard.Statistics.DamageDiceAmount) / 2 + val) * GetStatValue(PlayerCard.Statistics.AttackSpeed) + " damage per second)");
        } else if (_slot == EquipmentSlot.Ranged) {
            int val = GetStatValue(PlayerCard.Statistics.DamageDiceValue);
            ret.add(ChatColor.WHITE + "Damage: " + (1 + val) + " - " + (GetStatValue(PlayerCard.Statistics.DamageDiceAmount)) * val);
            ret.add(ChatColor.WHITE + "( Avg " + (GetStatValue(PlayerCard.Statistics.DamageDiceAmount) / 2 + val) + " damage per shot)");
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
        return _rarity;
    }
    public Material GetMaterial(){
        return _base;
    }
    public Map<PlayerCard.Statistics, Integer>  GetAllStats(){
        return _Stats;
    }
    public int GetStatValue(PlayerCard.Statistics type){
        return _Stats.getOrDefault(type, 0);
    }
    public void AssignStat(PlayerCard.Statistics type, int amount){
        _Stats.put(type, amount);
    }

    //STATIC

    static List<WMItem> Database = new ArrayList<>();
    public static NamespacedKey ExtendedIDKey = new NamespacedKey(Warmachina.Main, "ExtendedID");

    public static WMItem Get(int id){
        return Database.get(id);
    }

}
