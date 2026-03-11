/*    */ package net.minecraft.tags;
/*    */ 
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ 
/*    */ public class GameEventTags {
/*  8 */   public static final TagKey<GameEvent> VIBRATIONS = create("vibrations");
/*  9 */   public static final TagKey<GameEvent> WARDEN_CAN_LISTEN = create("warden_can_listen");
/* 10 */   public static final TagKey<GameEvent> SHRIEKER_CAN_LISTEN = create("shrieker_can_listen");
/* 11 */   public static final TagKey<GameEvent> IGNORE_VIBRATIONS_SNEAKING = create("ignore_vibrations_sneaking");
/* 12 */   public static final TagKey<GameEvent> ALLAY_CAN_LISTEN = create("allay_can_listen");
/*    */   
/*    */   private static TagKey<GameEvent> create(String $$0) {
/* 15 */     return TagKey.create(Registries.GAME_EVENT, new ResourceLocation($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\tags\GameEventTags.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */