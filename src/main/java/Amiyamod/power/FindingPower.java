package Amiyamod.power;

import Amiyamod.Amiyamod;
import Amiyamod.action.cards.FindingAction;
import Amiyamod.patches.YCardTagClassEnum;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

public class FindingPower extends AbstractPower {
    public static final String NAME = "FindingPower";
    // 能力的ID
    public static final String POWER_ID =  Amiyamod.makeID(NAME);
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FindingPower(AbstractCreature owner,int Amount) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.type = PowerType.BUFF;
        this.owner = owner;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = Amount;

        // 添加一大一小两张能力图
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/FindingPower_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/FindingPower_128.png"),0,0,128,128);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.type != AbstractCard.CardType.CURSE && card.type != AbstractCard.CardType.STATUS  && this.amount>0){
            this.flash();
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));

            card.exhaustOnUseOnce = true;
            //AbstractDungeon.player.hand.moveToExhaustPile(card);

            for (AbstractCard caa: AbstractDungeon.player.masterDeck.group) {
                if (caa.uuid == card.uuid) {
                    if(caa.canUpgrade()){
                        caa.upgrade();
                        AbstractDungeon.player.bottledCardUpgradeCheck(caa);
                    }
                    if (card.canUpgrade()){
                        card.upgrade();
                        if (card.hasTag(YCardTagClassEnum.MEMORY)){
                            Amiyamod.MakeMemoryCard(card);
                        }
                    }
                    AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));
                }
            }
        }
    }
}
