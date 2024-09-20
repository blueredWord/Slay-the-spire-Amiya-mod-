package Amiyamod.patches;

import Amiyamod.power.LittleTePower;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
@SpirePatch(clz = com.megacrit.cardcrawl.actions.common.GainBlockAction.class, method = "update")
public class DefendPatch {
   @SpirePrefixPatch
    public static void Prefix(AbstractGameAction _int){
       //如果有特蕾西亚的庇护 ，不获得格挡 暂时不用
       /*
        if(_int.target.hasPower(LittleTePower.ID1) && _int.amount>0){
            AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(_int.target, _int.source, _int.amount));
            _int.amount -= _int.amount;
            _int.target.getPower(LittleTePower.ID1).flash();
        }

        */
    }
}

