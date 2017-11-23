package aid;

import com.mingli.unzip.UnZip;

import java.io.File;
import java.io.InputStream;

import element2.TexId;

public class SkinLoader {

	public File loadSkin(InputStream is, File targetDir, TexId ti) {
		// TODO Auto-generated method stub
		File[] fs = UnZip.unZip(is, targetDir);
		return loadSkin(ti, fs);
	}

	public File loadSkin(File file, File targetDir, TexId ti) {
		// TODO Auto-generated method stub
		File[] fs = UnZip.unZip(file, targetDir);
		return loadSkin(ti, fs);
	}

	public File loadSkin(TexId ti, File[] fs) {
		File mapFile=null;
		for (File f : fs) {
			String name = f.getName();
			mapFile = loadSingleFile(ti,  f, name);
		}
		return mapFile;
	}

	public File loadSingleFile(TexId ti, Object f, String name) {
		File mapFile=null;
		if (name.startsWith("body.")) {
            ti.reloadTextureId(TexId.BODY, f);
        } else if (name.startsWith("cap.")) {
            ti.reloadTextureId(TexId.CAP, f);
        } else if (name.startsWith("dialogtringle.")) {
            ti.reloadTextureId(TexId.DIALOGTRINGLE, f);
        } else if (name.startsWith("fenshenguo.")) {
            ti.reloadTextureId(TexId.FENSHEN, f);
        } else if (name.startsWith("putongdan.")) {
            ti.reloadTextureId(TexId.PUTONGQIANG, f);
        } else if (name.startsWith("espressionenemy.")) {
            ti.reloadTextureId(TexId.EXPRESSIONENEMY, f);
        } else if (name.startsWith("flag.")) {
            ti.reloadTextureId(TexId.FLAG, f);
        } else if (name.startsWith("toukuienemy.")) {
            ti.reloadTextureId(TexId.CAPENEMY, f);
        } else if (name.startsWith("clothenemy.")) {
            ti.reloadTextureId(TexId.CLOTHENEMY, f);
        } else if (name.startsWith("sticker.")) {
            ti.reloadTextureId(TexId.STICKER, f);
        } else if (name.startsWith("bamboopiple.")) {
            ti.reloadTextureId(TexId.BAMBOOPIPLE, f);
        } else if (name.startsWith("wipe.")) {
            ti.reloadTextureId(TexId.WIPE, f);
        } else if (name.startsWith("qigan.")) {
            ti.reloadTextureId(TexId.QIGAN, f);
        } else if (name.startsWith("boomgun.")) {
            ti.reloadTextureId(TexId.BOOMGUN, f);
        } else if (name.startsWith("shufudan.")) {
            ti.reloadTextureId(TexId.SHUFUDAN, f);
        } else if (name.startsWith("autobulletgun.")) {
            ti.reloadTextureId(TexId.ZIDONGDAN, f);
        } else if (name.startsWith("pifeng.")) {
            ti.reloadTextureId(TexId.PIFENG, f);
        } else if (name.startsWith("fruitfly.")) {
            ti.reloadTextureId(TexId.FRUITFLY, f);
        } else if (name.startsWith("stonegrass.")) {
            ti.reloadTextureId(TexId.STONEGRASS, f);
        } else if (name.startsWith("guiderect.")) {
            ti.reloadTextureId(TexId.GUIDERECT, f);
        } else if (name.startsWith("goalcircle.")) {
            ti.reloadTextureId(TexId.GOALCIRCLE, f);
        } else if (name.startsWith("woodroot.")) {
            ti.reloadTextureId(TexId.WOODROOT, f);
        } else if (name.startsWith("guangdan.")) {
            ti.reloadTextureId(TexId.GUANGDANQIANG, f);
        } else if (name.startsWith("jian.")) {
            ti.reloadTextureId(TexId.K, f);
        } else if (name.startsWith("guidecircle.")) {
            ti.reloadTextureId(TexId.GUIDECIRCLE, f);
        } else if (name.startsWith("egg.")) {
            ti.reloadTextureId(TexId.EGG, f);
        } else if (name.startsWith("lighttail.")) {
            ti.reloadTextureId(TexId.LIGHTTAIL, f);
        } else if (name.startsWith("cup.")) {
            ti.reloadTextureId(TexId.CUP, f);
        } else if (name.startsWith("huck.")) {
            ti.reloadTextureId(TexId.HOOKGUN, f);
        } else if (name.startsWith("jujidan.")) {
            ti.reloadTextureId(TexId.JUJI, f);
        } else if (name.startsWith("daodan.")) {
            ti.reloadTextureId(TexId.MISSILE, f);
        } else if (name.startsWith("s.")) {
            ti.reloadTextureId(TexId.SHOTGUN, f);
        } else if (name.startsWith("bullet.")) {
            ti.reloadTextureId(TexId.BULLET, f);
        } else if (name.startsWith("guidepost2.")) {
            ti.reloadTextureId(TexId.GUIDEPOST, f);
        } else if (name.startsWith("candlepor.")) {
            ti.reloadTextureId(TexId.CANDLEPOR, f);
        } else if (name.startsWith("firetail.")) {
            ti.reloadTextureId(TexId.FIRE, f);
        } else if (name.startsWith("paotai.")) {
            ti.reloadTextureId(TexId.PAOTA, f);
        } else if (name.startsWith("guntitle.")) {
            ti.reloadTextureId(TexId.GUN, f);
        } else if (name.startsWith("blood.")) {
            ti.reloadTextureId(TexId.BLOOD, f);
        } else if (name.startsWith("candle.")) {
            ti.reloadTextureId(TexId.CANDLETAIL, f);
        } else if (name.startsWith("lightspot.")) {
            ti.reloadTextureId(TexId.LIGHTSPOT, f);
        } else if (name.startsWith("lightning.")) {
            ti.reloadTextureId(TexId.LIGHTNING, f);
        } else if (name.startsWith("tail.")) {
            ti.reloadTextureId(TexId.RAINBOW, f);
        } else if (name.startsWith("black.")) {
            ti.reloadTextureId(TexId.BLACK, f);
        } else if (name.startsWith("boom2.")) {
            ti.reloadTextureId(TexId.BOOM, f);
        } else if (name.startsWith("boom.")) {
            ti.reloadTextureId(TexId.BOOMWHITE, f);
        } else if (name.startsWith("lightningset.")) {
            ti.reloadTextureId(TexId.THUNDER, f);
        } else if (name.startsWith("firework.")) {
            ti.reloadTextureId(TexId.FIREWORK, f);
        } else if (name.startsWith("snow.")) {
            ti.reloadTextureId(TexId.SNOW, f);
        } else if (name.startsWith("wind.")) {
            ti.reloadTextureId(TexId.WIND, f);
        } else if (name.startsWith("espression.")) {
            ti.reloadTextureId(TexId.EXPRESSION, f);
        } else if (name.startsWith("zan.")) {
            ti.reloadTextureId(TexId.ZAN, f);
        } else if (name.startsWith("light.")) {
            ti.reloadTextureId(TexId.HIKARI, f);
        } else if (name.startsWith("toukui.")) {
            ti.reloadTextureId(TexId.TOUKUI, f);
        } else if (name.startsWith("gao.")) {
            ti.reloadTextureId(TexId.GAO, f);
        } else if (name.startsWith("bamboo.")) {
            ti.reloadTextureId(TexId.BAMBOO, f);
        } else if (name.startsWith("bambooheart.")) {
            ti.reloadTextureId(TexId.BAMBOOHEART, f);
        } else if (name.startsWith("body.")) {
            ti.reloadTextureId(TexId.BODY, f);
        } else if (name.startsWith("hand.")) {
            ti.reloadTextureId(TexId.HAND, f);
        } else if (name.startsWith("foot.")) {
            ti.reloadTextureId(TexId.FOOT, f);
        } else if (name.startsWith("cloth.")) {
            ti.reloadTextureId(TexId.CLOTH, f);
        } else if (name.startsWith("cap.")) {
            ti.reloadTextureId(TexId.CAP, f);
        } else if (name.startsWith("back.")) {
            ti.reloadTextureId(TexId.BACK, f);
        } else if (name.startsWith("background.")) {
            ti.reloadTextureId(TexId.MORNING, f);
        } else if (name.startsWith("clock.")) {
            ti.reloadTextureId(TexId.CLOCK, f);
        } else if (name.startsWith("walker.")) {
            ti.reloadTextureId(TexId.WALKER, f);
        } else if (name.startsWith("greenwalker.")) {
            ti.reloadTextureId(TexId.GREENWALKER, f);
        } else if (name.startsWith("creeper.")) {
            ti.reloadTextureId(TexId.CREEPER, f);
        } else if (name.startsWith("bluecreeper.")) {
            ti.reloadTextureId(TexId.REDCREEPER, f);
        } else if (name.startsWith("hedgehog.")) {
            ti.reloadTextureId(TexId.HEDGEHOG, f);
        } else if (name.startsWith("flyer.")) {
            ti.reloadTextureId(TexId.FLYER, f);
        } else if (name.startsWith("enemy1.")) {
            ti.reloadTextureId(TexId.BALLER, f);
        } else if (name.startsWith("fball.")) {
            ti.reloadTextureId(TexId.WATERBALL, f);

        } else if (name.startsWith("coin.")) {
            ti.reloadTextureId(TexId.COIN, f);
        } else if (name.startsWith("coinicon.")) {
            ti.reloadTextureId(TexId.COINICON, f);
        } else if (name.startsWith("tomenu.")) {
            ti.reloadTextureId(TexId.TOMENU, f);
        } else if (name.startsWith("fireball.")) {
            ti.reloadTextureId(TexId.FIREBALL, f);
        } else if (name.startsWith("zhuan.")) {
            ti.reloadTextureId(TexId.ZHUAN, f);
        } else if (name.startsWith("bank.")) {
            ti.reloadTextureId(TexId.BANK, f);
        } else if (name.startsWith("blank.")) {
            ti.reloadTextureId(TexId.BLANK, f);

        } else if (name.startsWith("sword.")) {
            ti.reloadTextureId(TexId.SWORD, f);
        } else if (name.startsWith("pause.")) {
            ti.reloadTextureId(TexId.PAUSE, f);
        } else if (name.startsWith("feibiao.")) {
            ti.reloadTextureId(TexId.FEIBIAO, f);
        } else if (name.startsWith("goal.")) {
            ti.reloadTextureId(TexId.GOAL, f);

        } else if (name.startsWith("stone.")) {
            ti.reloadTextureId(TexId.STONE, f);
        } else if (name.startsWith("height.")) {
            ti.reloadTextureId(TexId.H, f);
        } else if (name.startsWith("wood.")) {
            ti.reloadTextureId(TexId.WOOD, f);
        } else if (name.startsWith("tree.")) {
            ti.reloadTextureId(TexId.TREE, f);
        } else if (name.startsWith("soil.")) {
            ti.reloadTextureId(TexId.SOIL, f);
        } else if (name.startsWith("soilgrass.")) {
            ti.reloadTextureId(TexId.SOILGRASS, f);
        } else if (name.startsWith("fog.")) {
            ti.reloadTextureId(TexId.FOG, f);
        } else if (name.startsWith("tomato.")) {
            ti.reloadTextureId(TexId.TOMATO, f);
        } else if (name.startsWith("jingubang.")) {
            ti.reloadTextureId(TexId.JINGUBANG, f);
        } else if (name.startsWith("red.")) {
            ti.reloadTextureId(TexId.RED, f);
        } else if (name.startsWith("blue.")) {
            ti.reloadTextureId(TexId.BLUE, f);
        } else if (name.startsWith("green.")) {
            ti.reloadTextureId(TexId.GREEN, f);
        } else if (name.startsWith("spine.")) {
            ti.reloadTextureId(TexId.PRICK, f);
        } else if (name.startsWith("spinex.")) {
            ti.reloadTextureId(TexId.PRICKX, f);
        } else if (name.startsWith("capjianci.")) {
            ti.reloadTextureId(TexId.CAPJIANCI, f);
        } else if (name.startsWith("bag.")) {
            ti.reloadTextureId(TexId.BAG, f);

        } else if (name.startsWith("zhijindong.")) {
            ti.reloadTextureId(TexId.DESERT, f);
        } else if (name.startsWith("huangshan2.")) {
            ti.reloadTextureId(TexId.EVENING, f);
        } else if (name.startsWith("sunset.")) {
            ti.reloadTextureId(TexId.SUNSET, f);
        } else if (name.startsWith("tianshan.")) {
            ti.reloadTextureId(TexId.TIANSHAN, f);
        } else if (name.equals("map.txt")) {
            return(File)f;
            //优先map.txt 如果有多个txt的话
        }else if (name.endsWith(".txt")&&mapFile!=null) {
            return(File)f;
        }
		return mapFile;
	}
}
