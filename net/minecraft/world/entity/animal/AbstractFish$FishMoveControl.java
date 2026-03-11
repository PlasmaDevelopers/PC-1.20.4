/*     */ package net.minecraft.world.entity.animal;
/*     */ 
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.control.MoveControl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class FishMoveControl
/*     */   extends MoveControl
/*     */ {
/*     */   private final AbstractFish fish;
/*     */   
/*     */   FishMoveControl(AbstractFish $$0) {
/* 186 */     super((Mob)$$0);
/* 187 */     this.fish = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 192 */     if (this.fish.isEyeInFluid(FluidTags.WATER))
/*     */     {
/* 194 */       this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0D, 0.005D, 0.0D));
/*     */     }
/*     */     
/* 197 */     if (this.operation != MoveControl.Operation.MOVE_TO || this.fish.getNavigation().isDone()) {
/* 198 */       this.fish.setSpeed(0.0F);
/*     */       
/*     */       return;
/*     */     } 
/* 202 */     float $$0 = (float)(this.speedModifier * this.fish.getAttributeValue(Attributes.MOVEMENT_SPEED));
/* 203 */     this.fish.setSpeed(Mth.lerp(0.125F, this.fish.getSpeed(), $$0));
/*     */     
/* 205 */     double $$1 = this.wantedX - this.fish.getX();
/* 206 */     double $$2 = this.wantedY - this.fish.getY();
/* 207 */     double $$3 = this.wantedZ - this.fish.getZ();
/*     */     
/* 209 */     if ($$2 != 0.0D) {
/* 210 */       double $$4 = Math.sqrt($$1 * $$1 + $$2 * $$2 + $$3 * $$3);
/*     */       
/* 212 */       this.fish.setDeltaMovement(this.fish.getDeltaMovement().add(0.0D, this.fish.getSpeed() * $$2 / $$4 * 0.1D, 0.0D));
/*     */     } 
/*     */     
/* 215 */     if ($$1 != 0.0D || $$3 != 0.0D) {
/* 216 */       float $$5 = (float)(Mth.atan2($$3, $$1) * 57.2957763671875D) - 90.0F;
/*     */       
/* 218 */       this.fish.setYRot(rotlerp(this.fish.getYRot(), $$5, 90.0F));
/* 219 */       this.fish.yBodyRot = this.fish.getYRot();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\AbstractFish$FishMoveControl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */