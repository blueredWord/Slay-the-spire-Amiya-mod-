package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import Amiyamod.patches.YCardTagClassEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class StoneSwordAction extends AbstractGameAction {
    public StoneSwordAction() {
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        int n = 0;
        for (int i = p.hand.size(); i>0 ;i--){
            AbstractCard c = p.hand.group.get(i-1);
            if (c.hasTag(YCardTagClassEnum.YZuZhou)){
                n++;
            }
        }
        Amiyamod.getRedSky(n,false);
        this.isDone = true;
    }
}
