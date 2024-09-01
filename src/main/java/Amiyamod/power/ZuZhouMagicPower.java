package Amiyamod.power;

import Amiyamod.Amiyamod;
import Amiyamod.cards.YCard.ZuZhouMagic;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ZuZhouMagicPower extends AbstractPower implements OnLoseTempHpPower {
    //=================================================================================================================
    //@ 【诅咒巫术】BUFF OWER失去生命时，记录的目标也将失去等量的生命。
    //=================================================================================================================
    public static final String NAME = "ZuZhouMagicPower";
    public static final String POWER_ID = Amiyamod.makeID(NAME);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public ArrayList<AbstractCreature> list = new ArrayList<>();
    public ZuZhouMagicPower(AbstractCreature owner,AbstractCreature target) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = -1;
        this.type = PowerType.BUFF;
        // 添加图标                         this.img = new Texture("img/Reimupowers/" + NAME + ".png");
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_48.png"),0,0,48,48);
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("img/powers/" + NAME + "_128.png"),0,0,128,128);
        // 首次添加能力更新描述
        this.list.add(target);
        this.updateDescription();
    }


    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] ;
        int i = this.list.size();
        for(AbstractCreature m : this.list){
            i--;
            this.description += DESCRIPTIONS[1];
            this.description += m.name;
            if (i!=0) {
                this.description += DESCRIPTIONS[2];
            }
        }
        this.description += DESCRIPTIONS[3];
    }

    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card instanceof ZuZhouMagic){
            this.flash();
            this.list.add(m);
            List<AbstractCreature> ll = this.list.stream().distinct().collect(Collectors.toList());
            this.list.clear();
            this.list.addAll(ll);
            this.updateDescription();
        }
    }

    // 效果 : 每次受到伤害获得层数丝线
    public int onLoseTempHp(DamageInfo info, int damageAmount){
        int tem=(Integer) TempHPField.tempHp.get(this.owner);
        if ( damageAmount > 0 && tem >= damageAmount){
            this.onLoseHp(damageAmount);
        }
        return damageAmount;
    }
    public int onLoseHp(int damageAmount) {
        if (damageAmount > 0) {
            this.flash();
            for(AbstractCreature m: this.list){
                this.addToBot(new LoseHPAction(m,this.owner,damageAmount, AbstractGameAction.AttackEffect.NONE));
            }
        }
        return damageAmount;
    }
}
