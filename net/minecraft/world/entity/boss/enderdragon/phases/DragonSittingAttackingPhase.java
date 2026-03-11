/*    */ package net.minecraft.world.entity.boss.enderdragon.phases;
/*    */ 
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*    */ 
/*    */ public class DragonSittingAttackingPhase
/*    */   extends AbstractDragonSittingPhase
/*    */ {
/*    */   private static final int ROAR_DURATION = 40;
/*    */   private int attackingTicks;
/*    */   
/*    */   public DragonSittingAttackingPhase(EnderDragon $$0) {
/* 13 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void doClientTick() {
/* 18 */     this.dragon.level().playLocalSound(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ(), SoundEvents.ENDER_DRAGON_GROWL, this.dragon.getSoundSource(), 2.5F, 0.8F + this.dragon.getRandom().nextFloat() * 0.3F, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void doServerTick() {
/* 23 */     if (this.attackingTicks++ >= 40) {
/* 24 */       this.dragon.getPhaseManager().setPhase(EnderDragonPhase.SITTING_FLAMING);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void begin() {
/* 30 */     this.attackingTicks = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnderDragonPhase<DragonSittingAttackingPhase> getPhase() {
/* 35 */     return EnderDragonPhase.SITTING_ATTACKING;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\boss\enderdragon\phases\DragonSittingAttackingPhase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */