import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public abstract class WMPlayer {

    Map<Statistics,Integer> Stats;
    Map<Statistics,Float> DynamicStatModifiers;

    //ADD STATIC STAT MODIFIERS / BUFFS
    Map<WMItem.EquipmentSlot, WMItem> Equipment;

    public void Equip(WMItem item){
        Equipment.put(item._slot,item);

        //Adding stats
        for ( Map.Entry<Statistics, Integer> entry :
        item.GetAllStats().entrySet()) {
            Stats.put(entry.getKey(),
                    (
                            Stats.containsKey(entry.getKey()) ?
                                    Stats.get(entry.getKey()) + entry.getValue() :
                                    entry.getValue()
                    ));
        }

    }
    public void Unequip(WMItem.EquipmentSlot slot){
        WMItem item = Equipment.get(slot);
        Equipment.put(slot, null);

        for ( Map.Entry<Statistics, Integer> entry :
                item.GetAllStats().entrySet()) {
            Stats.put(entry.getKey(), Stats.get(entry.getKey()) - entry.getValue() );
        }
    }

    public WMPlayer(){
        Stats = new HashMap<>();
        Equipment = new HashMap<>();
    }

    public enum Statistics{
        Vitality, //Increases the value of players HP
        Power, //Increases players base damage - moves lower damage boundary by 0.95 damage per point and upper by 1.05 per point
        Spirit, //Increases the rate at which Health and Energy regenerates - only out of combat
        Armor, //Reduces damage received by a percentage
        BonusArmor, //Reduces damage received by a percentage - shown as a sub stat
        ArmorToughness, //Reduces damage by a flat amount
        BonusArmorToughness, //Reduces damage by a flat amount - shown as a sub stat
        CriticalChance, //Increases the chance to critically strike
        CriticalPower, //Increases the damage percentage of a critical strike
        DodgeChance, //Increases the chance to dodge (only above 75% energy)
        BlockValue, //Increases the raw damage blocked by a shield
        BlockEnergyCost, //Reduces the cost of a block (base: 4 - can't go below 1)
        TrueStrikeChance, //Chance to perform a strike that avoids [Armor Toughness] and shield block
        Thorns, //Damage dealt to the attacker when struck while blocking
        BonusPhysicalDamage, //Flat Bonus damage dealt by a weapon swing
        BonusMagicDamage, //Flat Bonus damage dealt by magic effects and abilities
        BonusRangedDamage, //Flat Bonus damage dealt by a ranged attack
        DamageDiceAmount, //Damage roll 1to DamageDiceAmount; modified at a rate of 1.05 per point
        DamageDiceValue, //This value is added onto the damage roll; modified by power at a rate of 0.95 per point
        AttackSpeed //Tis can be only set once, cannot be modified by stats and is required for weapons
    }

    //Used for random stat generations based on item lvl score
    public static final Map<Statistics, Float> StatWeights = new HashMap<>();
    static{
        StatWeights.put(Statistics.Vitality, 1f);
        StatWeights.put(Statistics.Power, 1f);
        StatWeights.put(Statistics.Spirit, 1f);
        StatWeights.put(Statistics.Armor, 0.1862f);
        StatWeights.put(Statistics.BonusArmor, 0.374f);
        StatWeights.put(Statistics.ArmorToughness, 5.93f);
        StatWeights.put(Statistics.BonusArmorToughness, 7.48f);
        StatWeights.put(Statistics.CriticalChance, 0.096f);
        StatWeights.put(Statistics.CriticalPower, 0.118f);
        StatWeights.put(Statistics.DodgeChance, 0.28f);
        StatWeights.put(Statistics.BlockValue, 2.33f);
        StatWeights.put(Statistics.BlockEnergyCost, 18f);
        StatWeights.put(Statistics.TrueStrikeChance, 0.405f);
        StatWeights.put(Statistics.Thorns, 4.9f);
        StatWeights.put(Statistics.BonusPhysicalDamage, 0.86f);
        StatWeights.put(Statistics.BonusMagicDamage, 0.86f);
        StatWeights.put(Statistics.BonusRangedDamage, 0.86f);
        StatWeights.put(Statistics.DamageDiceAmount, 2.12f);
        StatWeights.put(Statistics.DamageDiceValue, 0.96f);
    }

}
