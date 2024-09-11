package Amiyamod.patches;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class YDamage extends AbstractDamageModifier {

    @Override
    public AbstractDamageModifier makeCopy() {
        return new YDamage();
    }
}
