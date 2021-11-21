import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ItemTemplate {
    
    int _itemID;

    String _name;
    Rarity _rarity;
    Material _base;

    Map<PlayerCard.Statistics, Integer> _Stats = new HashMap<>();

    abstract void onUse(); //Called when right clicked with the item in hand - designed for off-hands
    abstract void onHit(); //Called when the weapon/projectile hits the target
    abstract void onSwing(); //Called when the weapon is swung or shot
    abstract void onStruck(); //Called when the owner of the item (must be equipped) is hit (Called before onHit() )

    public enum Rarity{
        Common,
        Uncommon,
        Rare,
        Epic,
        Legendary
    }

    public ItemTemplate(int ID, Material base, String name, Rarity rarity, Map<PlayerCard.Statistics, Integer> stats){
        _itemID = ID;
        _base = base;
        _name = name;
        _rarity = rarity;
        _Stats.putAll(stats);


    }
    public ItemTemplate(int ID, Material base, String name, Rarity rarity){
        _itemID = ID;
        _base = base;
        _name = name;
        _rarity = rarity;
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
    public String[] GetBaseDescription(){
        //Todo: Add the upper part of description here (Armor, damage, speed, name, level)
        return null;
    }
    public String[] GetBaseStatsDescription(){
        //Todo: Add the middle part of description (Vitality, Power and such)
        return null;
    }
    public String[] GetSecondaryStatsDescription(){
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

    static List<ItemTemplate> Database = new ArrayList<>();
    public static NamespacedKey ExtendedIDKey = new NamespacedKey(Warmachina.Main, "ExtendedID");

    public static ItemTemplate Get(int id){
        return Database.size() > id ? Database.get(id) : null;
    }

}
