package Amiyamod.power;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

/*
implements OnLoseTempHpPower

    public int onLoseTempHp(DamageInfo info, int damageAmount){
        int tem=(Integer) TempHPField.tempHp.get(this.owner);
        if ( damageAmount > 0 && tem >= damageAmount){
            this.flash();

        }
        return damageAmount;
    }
    public int onLoseHp(int damageAmount) {
        if (damageAmount > 0) {
            this.flash();

        }
        return damageAmount;
    }
 */


public abstract class ABeautifulLifePower extends AbstractPower {
    public static ArrayList<AbstractCard> CardGroup= new ArrayList<AbstractCard>();
    public ABeautifulLifePower(AbstractCreature owner, ArrayList<AbstractCard> G) {
        super();
    }
}
