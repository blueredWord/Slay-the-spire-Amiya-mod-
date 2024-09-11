package Amiyamod.power;

import Amiyamod.Amiyamod;
import Amiyamod.cards.Memory.Outcast2;
import Amiyamod.cards.YCard.FirstSayA;
import Amiyamod.patches.LeechDamage;
import Amiyamod.patches.OutcastDamage;
import Amiyamod.patches.YCardTagClassEnum;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.DamageModApplyingPower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OutcastPower extends AbstractPower implements DamageModApplyingPower {
    public static final String NAME = "OutcastPower";
    public static final String POWER_ID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public OutcastPower(int am) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = am;
        this.type = PowerType.BUFF;
        // 添加图标

        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        if (this.amount<6){
            this.description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1];
        }else {
            this.description = DESCRIPTIONS[2];
        }
    }

    @Override
    public boolean shouldPushMods(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return o instanceof AbstractCard && ((AbstractCard) o).type == AbstractCard.CardType.ATTACK
                && ((AbstractCard) o) instanceof Outcast2
                && this.amount >= 6
                && list.stream().noneMatch(mod -> mod instanceof OutcastDamage);
    }

    @Override
    public List<AbstractDamageModifier> modsToPush(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return Collections.singletonList(new OutcastDamage());
    }

}
