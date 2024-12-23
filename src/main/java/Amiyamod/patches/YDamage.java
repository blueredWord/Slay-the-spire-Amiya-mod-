package Amiyamod.patches;

import Amiyamod.Amiyamod;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class YDamage extends AbstractDamageModifier {

    @Override
    public AbstractDamageModifier makeCopy() {
        return new YDamage();
    }
    @Override
    public void onLastDamageTakenUpdate(DamageInfo info, int lastDamageTaken, int overkillAmount, AbstractCreature targetHit) {
        if (lastDamageTaken > 0 && info.type != DamageInfo.DamageType.HP_LOSS) {
            Amiyamod.LinePower(lastDamageTaken);
        }
    }
    public boolean isInherent() {
        return true;
    }
}
