package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import Amiyamod.cards.RedSky.RedSky;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

public class ShadowSkillAction extends AbstractGameAction {
    public int[] multiDamage;
    private boolean freeToPlayOnce = false;
    private boolean up;
    private DamageInfo.DamageType damageType;
    private AbstractPlayer p;
    private int energyOnUse;
    private int m;
    private final int max=4;
    public ShadowSkillAction(AbstractCard c) {
        this.multiDamage = c.multiDamage;
        this.damageType = c.damageTypeForTurn;
        this.p = AbstractDungeon.player;
        this.freeToPlayOnce = c.freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = c.energyOnUse;
        this.up = c.upgraded;
        this.m = c.magicNumber;
    }

    public void update() {
        int effect = Math.min(EnergyPanel.totalCount,this.max) ;
        if (effect > 0 && !this.freeToPlayOnce) {
            this.p.energy.use(EnergyPanel.totalCount);
        }
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        //X药剂+2次
        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        //如果升级多触发一次
        if (this.up){
            effect += this.m;
        }

        if (effect > 0) {
            for(int i = 0; i < effect; ++i) {
                if (i == 0) {

                }
                Amiyamod.getRedSky(i,false);
            }
        }

        this.isDone = true;
    }
}
