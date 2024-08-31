package Amiyamod.power;

import Amiyamod.Amiyamod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import java.util.ArrayList;
import java.util.UUID;

//璀璨生命的效果
//消耗 抽2（3）张牌，在本场战斗将它们的耗能降为0，但在打出时会受到等同于耗能的真实伤害。
public class BeautifulLifePower extends ABeautifulLifePower {
    public static final String NAME = "BeautifulLifePower";
    public static final String POWER_ID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public BeautifulLifePower(AbstractCreature owner,ArrayList<AbstractCard> G) {
        super(owner,G);
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = -1;
        this.type = PowerType.DEBUFF;

        CardGroup.addAll(G);

        // 添加图标                         this.img = new Texture("img/Reimupowers/" + NAME + ".png");
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
        StringBuilder d = new StringBuilder(DESCRIPTIONS[0]);
        for(AbstractCard c : CardGroup){
            d.append(c.name).append(DESCRIPTIONS[1]);
        }
        d.append(DESCRIPTIONS[2]);
        this.description = d.toString();
    }

    // 效果 : 打出记录的卡后扣血
    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        for(AbstractCard uid : CardGroup){
            if (uid.uuid == card.uuid){
                this.flash();
                AbstractDungeon.actionManager.addToTop(
                        new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, card.makeCopy().cost)
                );
                break;
            }
        }
    }

}
