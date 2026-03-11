/*    */ package net.minecraft.client.resources.sounds;
/*    */ 
/*    */ import net.minecraft.client.player.LocalPlayer;
/*    */ import net.minecraft.client.sounds.SoundManager;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ 
/*    */ public class UnderwaterAmbientSoundHandler
/*    */   implements AmbientSoundHandler {
/*    */   public static final float CHANCE_PER_TICK = 0.01F;
/*    */   public static final float RARE_CHANCE_PER_TICK = 0.001F;
/*    */   public static final float ULTRA_RARE_CHANCE_PER_TICK = 1.0E-4F;
/*    */   private static final int MINIMUM_TICK_DELAY = 0;
/*    */   private final LocalPlayer player;
/*    */   private final SoundManager soundManager;
/* 15 */   private int tickDelay = 0;
/*    */   
/*    */   public UnderwaterAmbientSoundHandler(LocalPlayer $$0, SoundManager $$1) {
/* 18 */     this.player = $$0;
/* 19 */     this.soundManager = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 24 */     this.tickDelay--;
/*    */     
/* 26 */     if (this.tickDelay <= 0 && this.player.isUnderWater()) {
/* 27 */       float $$0 = (this.player.level()).random.nextFloat();
/* 28 */       if ($$0 < 1.0E-4F) {
/* 29 */         this.tickDelay = 0;
/* 30 */         this.soundManager.play(new UnderwaterAmbientSoundInstances.SubSound(this.player, SoundEvents.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE));
/* 31 */       } else if ($$0 < 0.001F) {
/* 32 */         this.tickDelay = 0;
/* 33 */         this.soundManager.play(new UnderwaterAmbientSoundInstances.SubSound(this.player, SoundEvents.AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE));
/* 34 */       } else if ($$0 < 0.01F) {
/* 35 */         this.tickDelay = 0;
/* 36 */         this.soundManager.play(new UnderwaterAmbientSoundInstances.SubSound(this.player, SoundEvents.AMBIENT_UNDERWATER_LOOP_ADDITIONS));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\sounds\UnderwaterAmbientSoundHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */