/*    */ package net.minecraft.world.entity.monster.warden;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ 
/*    */ public enum AngerLevel
/*    */ {
/* 10 */   CALM(0, SoundEvents.WARDEN_AMBIENT, SoundEvents.WARDEN_LISTENING),
/* 11 */   AGITATED(40, SoundEvents.WARDEN_AGITATED, SoundEvents.WARDEN_LISTENING_ANGRY),
/* 12 */   ANGRY(80, SoundEvents.WARDEN_ANGRY, SoundEvents.WARDEN_LISTENING_ANGRY);
/*    */   
/*    */   static {
/* 15 */     SORTED_LEVELS = (AngerLevel[])Util.make(values(), $$0 -> Arrays.sort($$0, ()));
/*    */   }
/*    */   
/*    */   private static final AngerLevel[] SORTED_LEVELS;
/*    */   private final int minimumAnger;
/*    */   private final SoundEvent ambientSound;
/*    */   private final SoundEvent listeningSound;
/*    */   
/*    */   AngerLevel(int $$0, SoundEvent $$1, SoundEvent $$2) {
/* 24 */     this.minimumAnger = $$0;
/* 25 */     this.ambientSound = $$1;
/* 26 */     this.listeningSound = $$2;
/*    */   }
/*    */   
/*    */   public int getMinimumAnger() {
/* 30 */     return this.minimumAnger;
/*    */   }
/*    */   
/*    */   public SoundEvent getAmbientSound() {
/* 34 */     return this.ambientSound;
/*    */   }
/*    */   
/*    */   public SoundEvent getListeningSound() {
/* 38 */     return this.listeningSound;
/*    */   }
/*    */   
/*    */   public static AngerLevel byAnger(int $$0) {
/* 42 */     for (AngerLevel $$1 : SORTED_LEVELS) {
/* 43 */       if ($$0 >= $$1.minimumAnger) {
/* 44 */         return $$1;
/*    */       }
/*    */     } 
/* 47 */     return CALM;
/*    */   }
/*    */   
/*    */   public boolean isAngry() {
/* 51 */     return (this == ANGRY);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\warden\AngerLevel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */