package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import Amiyamod.power.RedSkyPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import org.apache.logging.log4j.LogManager;

public class ShadowNoShadowAction extends AbstractGameAction {
    private AbstractCard card;
    private AbstractGameAction.AttackEffect effect;
    private boolean la = false;
    public ShadowNoShadowAction(AbstractCard card, AbstractGameAction.AttackEffect effect) {
        this.card = card;
        this.effect = effect;
    }
    public ShadowNoShadowAction(AbstractCard card,boolean last) {
        this.card = card;
        this.effect = AbstractGameAction.AttackEffect.NONE;
        this.la = last;
    }

    public ShadowNoShadowAction(AbstractCard card) {
        this(card, AbstractGameAction.AttackEffect.NONE);
    }

    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (mo.currentHealth > 0 && mo.currentHealth < this.target.currentHealth) {
                    LogManager.getLogger(Amiyamod.class.getSimpleName()).info(
                            "绝影：找到了血更少的{}", mo.name
                    );
                    this.target = mo;
                }
            }
            //if (this.card.damageTypeForTurn == DamageInfo.DamageType.NORMAL){this.card.damageTypeForTurn = DamageInfo.DamageType.THORNS;}
            this.card.calculateCardDamage((AbstractMonster)this.target);
            if (AbstractGameAction.AttackEffect.LIGHTNING == this.effect) {
                this.addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, this.card.damage, this.card.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
                this.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE", 0.1F));
                this.addToTop(new VFXAction(new LightningEffect(this.target.hb.cX, this.target.hb.cY)));
            } else {
                this.addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, this.card.damage, this.card.damageTypeForTurn), this.effect));
            }
        }
        this.isDone = true;
    }
}
