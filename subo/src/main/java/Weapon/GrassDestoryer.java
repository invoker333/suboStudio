package Weapon;

import com.mingli.toms.Music;
import com.mingli.toms.MusicId;
import com.mingli.toms.World;

import Element.AnimationMove;
import Enviroments.Grass;
import Enviroments.GrassSet;

/**
 * Created by Administrator on 2017/9/12.
 */

public class GrassDestoryer {

    private final AnimationMove ani;
    private GrassSet gra;
    public GrassDestoryer(GrassSet gra, AnimationMove ani){
        this.gra=gra;
        this.ani=ani;
    }

    boolean destory(float x, float y, int grassId){
        int x1 = (int) (x / gra.getGrid());
        int y1= (int) (y / gra.getGrid());
        if(x1>=0&&x1<gra.getMapWidth()
                &&y1>=0&&y1<=gra.getMapHeight()){
           return destory(grassId,x1,y1);
        }
        return false;
    }

    boolean destory(int grassId, int x1, int my1) {
        Grass g=gra.getgList().get(grassId);
        if(!g.canBeBreak){
            return false;
        }
        gra.particleCheck(grassId, 5, ani);
        gra.toNull(grassId, x1, my1);
        World.music.playSound(MusicId.wood2,0);
        return true;
    }
}
