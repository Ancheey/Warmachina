
public abstract class PlayerCard {

    public enum Statistics{
        Vitality, //Increases the value of players HP
        Power, //Increases players base damage
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
        BonusPhysicalDamage, //Bonus damage dealt by a weapon swing
        BonusMagicDamage, //Bonus damage dealt by magic effects and abilities
        BonusRangedDamage, //Bonus damage dealt by a ranged attack

    }
}
