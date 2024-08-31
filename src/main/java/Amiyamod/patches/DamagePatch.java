package Amiyamod.patches;

import Amiyamod.Amiyamod;
import Amiyamod.power.MercyPower;
import com.evacipated.cardcrawl.mod.stslib.patches.tempHp.PlayerDamage;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;


@SpirePatch(
        clz = AbstractMonster.class,
        method = "damage"
)
public class DamagePatch {
    @SpirePostfixPatch
    public static void Postfix(AbstractCreature __instance, DamageInfo info){
        //如果有慈悲愿景 获得伤害量一半的丝线
        if (AbstractDungeon.player.hasPower(MercyPower.POWERID)){
            Amiyamod.LinePower((int)info.output/2);
        }
    }
}
