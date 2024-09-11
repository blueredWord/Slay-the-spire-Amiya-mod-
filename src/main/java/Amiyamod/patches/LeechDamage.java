package Amiyamod.patches;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class LeechDamage extends AbstractDamageModifier {
    public boolean ignoresBlock(AbstractCreature target) {
        return true;
    }
    @Override
    public AbstractDamageModifier makeCopy() {
        return new LeechDamage();
    }
}
