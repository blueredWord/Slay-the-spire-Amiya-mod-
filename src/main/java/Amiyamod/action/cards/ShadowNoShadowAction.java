package Amiyamod.action.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class ShadowNoShadowAction extends AbstractGameAction {
    private AbstractCard c = null;
    private boolean isOut = false;

    public ShadowNoShadowAction(AbstractCard mo,boolean o) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.c = mo;
        this.source = AbstractDungeon.player;
        this.isOut = o;
    }

    public void update() {
        this.target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
        if(isOut){this.addToTop(new ApplyPowerAction(this.source,this.source,new IntangiblePlayerPower(this.source,1)));}
        for (int i = 0;i<10;i++) {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m.currentHealth > 0 && m.currentHealth < this.target.currentHealth) {
                    this.target = m;
                }
            }
            if (this.target.isDead){break;}
            DamageInfo info = new DamageInfo(this.source, this.c.damage, DamageInfo.DamageType.NORMAL);
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_HORIZONTAL));
            this.target.damage(info);
            this.duration += 0.1F;
            this.tickDuration();
            if (isOut && (this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead) {
                this.addToTop(new ApplyPowerAction(this.source, this.source, new StrengthPower(this.source, 1)));
            }
        }
        this.isDone = true;
    }
}
