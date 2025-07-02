package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import Amiyamod.power.CardBackPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.UpgradeRandomCardAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class PunchAction extends AbstractGameAction {
    public int[] multiDamage;
    private AbstractCard c;
    private AbstractPlayer p;

    public PunchAction(AbstractCard c,AbstractMonster m) {
        this.multiDamage = c.multiDamage;
        this.damageType = c.damageTypeForTurn;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.c = c;
        this.target = m;
    }

    public void update() {
        if (this.target != null){
            this.addToTop(new VFXAction(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.BLUNT_HEAVY)));

            this.target.damage(new DamageInfo(this.p, this.c.damage, this.c.damageTypeForTurn));
            if (((AbstractMonster)this.target).isDying || this.target.currentHealth <= 0) {
                //如果击杀

                //p.hand.addToTop(c);
                this.addToTop(new GainEnergyAction(1));
                //c.upgrade();
                //c.retain = true;
                this.addToBot(new ExhaustToHandAction(c));
                c.applyPowers();
                c.superFlash();
                /*
                AbstractCard tmp = c.makeSameInstanceOf();
                AbstractDungeon.player.limbo.addToBottom(tmp);
                tmp.current_x = c.current_x;
                tmp.current_y = c.current_y;
                tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = (float) Settings.HEIGHT / 2.0F;
                tmp.freeToPlayOnce = true;
                tmp.purgeOnUse = true;

                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp,null, c.energyOnUse, true, true), true);
            */
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

        }
        this.isDone = true;
    }
}
