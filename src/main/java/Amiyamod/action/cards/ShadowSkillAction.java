package Amiyamod.action.cards;

import Amiyamod.Amiyamod;
import Amiyamod.cards.RedSky.RedSky;
import Amiyamod.cards.RedSky.ShadowCloudBreakA;
import Amiyamod.cards.RedSky.ShadowCloudBreakB;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.UpgradeRandomCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
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
    public ShadowSkillAction(AbstractCard c) {
        this.multiDamage = c.multiDamage;
        this.damageType = c.damageTypeForTurn;
        this.p = AbstractDungeon.player;
        this.freeToPlayOnce = c.freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = c.energyOnUse;
        this.up = false;//c.upgraded;
        this.m = c.magicNumber;


    }

    public void update() {
        //设置花费不能超过magicnumber
        int effect = EnergyPanel.totalCount ;

        //花费能量
        if (effect > 0 && !this.freeToPlayOnce) {
            this.p.energy.use(effect);
        }
        if (this.up){
            effect++;
        }
        //X药剂+2次
        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        for(;effect >0;effect--){
            CardGroup G = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
            G.addToBottom(new ShadowCloudBreakB());
            G.addToBottom(new ShadowCloudBreakA());
            this.addToTop(new ChooseOneAction(G.group));
        }
        //Amiyamod.getRedSky(effect);

        this.isDone = true;
    }
}
