package Amiyamod.patches;

import Amiyamod.cards.Memory.Outcast2;
import Amiyamod.power.OutcastPower;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class OutcastDamage  extends AbstractDamageModifier {
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCreature target, AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasPower(OutcastPower.POWER_ID) && p.getPower(OutcastPower.POWER_ID).amount >= 6 && card instanceof Outcast2){
            return  damage * 2;
        }
        return damage;
    }
    @Override
    public AbstractDamageModifier makeCopy() {
        return new OutcastDamage();
    }
}
