package com.unsoldriceball.thaumcraftdamagebooster;


import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;




@Mod(modid = TDBMain.ID_MOD, acceptableRemoteVersions = "*")
public class TDBMain
{
    public static final String ID_MOD = "thaumcraftdamagebooster";


    private static final String TARGET = "class thaumcraft.";




    //ModがInitializeを呼び出す前に発生するイベント。
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        //これでこのクラス内でForgeのイベントが動作するようになるらしい。
        MinecraftForge.EVENT_BUS.register(this);
    }




    //Entityが攻撃を受けたときのイベント。
    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event)
    {
        //現在の処理がサーバー側かつダメージを受けたエンティティが存在するか
        if (event.getEntity().world.isRemote) return;
        if (event.getEntityLiving() != null)
        {

            final Entity _ATTACKER = event.getSource().getImmediateSource();        // ダメージを与えたエンティティを取得

            if (_ATTACKER != null)
            {
                //Configでプレイヤーの攻撃にのみダメージブーストが発生するように設定されている場合。
                if (TDBConfig.damageBoost_AffectOnlyTo_AttackByPlayer)
                {
                    final Entity _TRUE_ATTACKER = event.getSource().getTrueSource();
                    if (_TRUE_ATTACKER != null)
                    {
                        //「EntityPlayerであって、FakePlayerでない。」でない場合。
                        if (!(_TRUE_ATTACKER instanceof EntityPlayer && !(_TRUE_ATTACKER instanceof FakePlayer)))
                        {
                            return;
                        }
                    }
                }

                final String _ID_ATTACKER = _ATTACKER.getClass().toString();

                //ダメージ抑制の対象かどうか
                if (_ID_ATTACKER.contains(TARGET))
                {
                    float _boosted_damage = event.getAmount() * TDBConfig.damageMultiplier;       // ダメージを(damageMultiplier)%に減少させる
                    event.setAmount(_boosted_damage);
                }
            }
        }
    }
}