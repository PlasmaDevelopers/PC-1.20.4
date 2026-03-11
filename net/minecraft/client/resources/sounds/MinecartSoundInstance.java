/*    */ package net.minecraft.client.resources.sounds;
/*    */ 
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.vehicle.AbstractMinecart;
/*    */ 
/*    */ public class MinecartSoundInstance
/*    */   extends AbstractTickableSoundInstance {
/*    */   private static final float VOLUME_MIN = 0.0F;
/*    */   private static final float VOLUME_MAX = 0.7F;
/*    */   private static final float PITCH_MIN = 0.0F;
/*    */   private static final float PITCH_MAX = 1.0F;
/*    */   private static final float PITCH_DELTA = 0.0025F;
/*    */   private final AbstractMinecart minecart;
/* 16 */   private float pitch = 0.0F;
/*    */   
/*    */   public MinecartSoundInstance(AbstractMinecart $$0) {
/* 19 */     super(SoundEvents.MINECART_RIDING, SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
/* 20 */     this.minecart = $$0;
/* 21 */     this.looping = true;
/* 22 */     this.delay = 0;
/* 23 */     this.volume = 0.0F;
/* 24 */     this.x = (float)$$0.getX();
/* 25 */     this.y = (float)$$0.getY();
/* 26 */     this.z = (float)$$0.getZ();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canPlaySound() {
/* 31 */     return !this.minecart.isSilent();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canStartSilent() {
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 41 */     if (this.minecart.isRemoved()) {
/* 42 */       stop();
/*    */       
/*    */       return;
/*    */     } 
/* 46 */     this.x = (float)this.minecart.getX();
/* 47 */     this.y = (float)this.minecart.getY();
/* 48 */     this.z = (float)this.minecart.getZ();
/*    */     
/* 50 */     float $$0 = (float)this.minecart.getDeltaMovement().horizontalDistance();
/* 51 */     if ($$0 >= 0.01F && this.minecart.level().tickRateManager().runsNormally()) {
/* 52 */       this.pitch = Mth.clamp(this.pitch + 0.0025F, 0.0F, 1.0F);
/*    */       
/* 54 */       this.volume = Mth.lerp(Mth.clamp($$0, 0.0F, 0.5F), 0.0F, 0.7F);
/*    */     } else {
/* 56 */       this.pitch = 0.0F;
/* 57 */       this.volume = 0.0F;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\sounds\MinecartSoundInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */