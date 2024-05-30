package com.unsoldriceball.thaumcraftdamagebooster;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.unsoldriceball.thaumcraftdamagebooster.TDBMain.ID_MOD;


@Config(modid = ID_MOD)
public class TDBConfig
{
    @Config.RequiresMcRestart
    @Config.RangeDouble(min = 0.0d, max = 1.0d)
    public static float damageMultiplier = 1.25f;
    @Config.RequiresMcRestart
    public static boolean damageBoost_AffectOnlyTo_AttackByPlayer = true;



    @Mod.EventBusSubscriber(modid = ID_MOD)
    private static class EventHandler
    {
        //Configが変更されたときに呼び出される。変更を適用する関数。
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(ID_MOD))
            {
                ConfigManager.sync(ID_MOD, Config.Type.INSTANCE);
            }
        }
    }
}