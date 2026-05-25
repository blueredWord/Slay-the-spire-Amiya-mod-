package Amiyamod.action;

import Amiyamod.Amiyamod;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;

import java.util.Random;

public class SEAction extends AbstractGameAction {
    String N;
    boolean r;

    public SEAction(String n) {
        this.N = n;
        this.r = false;
    }
    public SEAction(String n,boolean r) {
        this.N = n;
        this.r = r;
    }

    public void update() {
        if (this.r){
           if (Amiyamod.sayed){
               Amiyamod.sayed = false;
               CardCrawlGame.sound.playA(this.N, MathUtils.random(0.0F, 0.0F));
           }
        } else {
            CardCrawlGame.sound.playA(this.N, MathUtils.random(0.0F, 0.0F));
        }
        this.isDone = true;
        //this.tickDuration();
    }
}
