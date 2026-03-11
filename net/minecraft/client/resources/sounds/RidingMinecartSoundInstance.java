/*    */ package net.minecraft.client.resources.sounds;
/*    */ 
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.entity.vehicle.AbstractMinecart;
/*    */ 
/*    */ public class RidingMinecartSoundInstance
/*    */   extends AbstractTickableSoundInstance {
/*    */   private static final float VOLUME_MIN = 0.0F;
/*    */   private static final float VOLUME_MAX = 0.75F;
/*    */   private final Player player;
/*    */   private final AbstractMinecart minecart;
/*    */   private final boolean underwaterSound;
/*    */   
/*    */   public RidingMinecartSoundInstance(Player $$0, AbstractMinecart $$1, boolean $$2) {
/* 18 */     super($$2 ? SoundEvents.MINECART_INSIDE_UNDERWATER : SoundEvents.MINECART_INSIDE, SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
/* 19 */     this.player = $$0;
/* 20 */     this.minecart = $$1;
/* 21 */     this.underwaterSound = $$2;
/*    */     
/* 23 */     this.attenuation = SoundInstance.Attenuation.NONE;
/* 24 */     this.looping = true;
/* 25 */     this.delay = 0;
/* 26 */     this.volume = 0.0F;
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
/* 41 */     if (this.minecart.isRemoved() || !this.player.isPassenger() || this.player.getVehicle() != this.minecart) {
/* 42 */       stop();
/*    */       
/*    */       return;
/*    */     } 
/* 46 */     if (this.underwaterSound != this.player.isUnderWater()) {
/* 47 */       this.volume = 0.0F;
/*    */       
/*    */       return;
/*    */     } 
/* 51 */     float $$0 = (float)this.minecart.getDeltaMovement().horizontalDistance();
/*    */     
/* 53 */     if ($$0 >= 0.01F) {
/* 54 */       this.volume = Mth.clampedLerp(0.0F, 0.75F, $$0);
/*    */     } else {
/* 56 */       this.volume = 0.0F;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\sounds\RidingMinecartSoundInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */