package Amiyamod.patches;

import Amiyamod.Amiyamod;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class KingDamage extends AbstractDamageModifier {
    public static final String ID = Amiyamod.makeID("KingDamage");
    TooltipInfo leechTooltip = null;

    public KingDamage() {}

    //This hook grabs the lastDamageTaken once it is updated upon attacking the monster.
    //This lets us heal the attacker equal to the damage that was actually dealt to the target
    @Override
    public void onLastDamageTakenUpdate(DamageInfo info, int lastDamageTaken, int overkillAmount, AbstractCreature targetHit) {
        if (lastDamageTaken > 0) {

        }
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new KingDamage();
    }

    //Overriding this to true tells us that this damage mod is considered part of the card and not just something added on to the card later.
    //If you ever add a damage modifier during the initialization of a card, it should be inherent.
    public boolean isInherent() {
        return true;
    }
}
