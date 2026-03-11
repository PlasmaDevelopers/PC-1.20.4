/*     */ package net.minecraft.world.entity.animal;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.item.ItemStack;
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
/*     */ class PlayWithItemsGoal
/*     */   extends Goal
/*     */ {
/*     */   private int cooldown;
/*     */   
/*     */   public boolean canUse() {
/* 394 */     if (this.cooldown > Dolphin.this.tickCount) {
/* 395 */       return false;
/*     */     }
/* 397 */     List<ItemEntity> $$0 = Dolphin.this.level().getEntitiesOfClass(ItemEntity.class, Dolphin.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), Dolphin.ALLOWED_ITEMS);
/* 398 */     return (!$$0.isEmpty() || !Dolphin.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 403 */     List<ItemEntity> $$0 = Dolphin.this.level().getEntitiesOfClass(ItemEntity.class, Dolphin.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), Dolphin.ALLOWED_ITEMS);
/* 404 */     if (!$$0.isEmpty()) {
/* 405 */       Dolphin.this.getNavigation().moveTo((Entity)$$0.get(0), 1.2000000476837158D);
/* 406 */       Dolphin.this.playSound(SoundEvents.DOLPHIN_PLAY, 1.0F, 1.0F);
/*     */     } 
/* 408 */     this.cooldown = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/* 413 */     ItemStack $$0 = Dolphin.this.getItemBySlot(EquipmentSlot.MAINHAND);
/* 414 */     if (!$$0.isEmpty()) {
/* 415 */       drop($$0);
/* 416 */       Dolphin.this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
/* 417 */       this.cooldown = Dolphin.this.tickCount + Dolphin.access$000(Dolphin.this).nextInt(100);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 423 */     List<ItemEntity> $$0 = Dolphin.this.level().getEntitiesOfClass(ItemEntity.class, Dolphin.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), Dolphin.ALLOWED_ITEMS);
/*     */     
/* 425 */     ItemStack $$1 = Dolphin.this.getItemBySlot(EquipmentSlot.MAINHAND);
/* 426 */     if (!$$1.isEmpty()) {
/* 427 */       drop($$1);
/* 428 */       Dolphin.this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
/* 429 */     } else if (!$$0.isEmpty()) {
/* 430 */       Dolphin.this.getNavigation().moveTo((Entity)$$0.get(0), 1.2000000476837158D);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drop(ItemStack $$0) {
/* 435 */     if ($$0.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 439 */     double $$1 = Dolphin.this.getEyeY() - 0.30000001192092896D;
/* 440 */     ItemEntity $$2 = new ItemEntity(Dolphin.this.level(), Dolphin.this.getX(), $$1, Dolphin.this.getZ(), $$0);
/* 441 */     $$2.setPickUpDelay(40);
/*     */     
/* 443 */     $$2.setThrower((Entity)Dolphin.this);
/*     */     
/* 445 */     float $$3 = 0.3F;
/* 446 */     float $$4 = Dolphin.access$100(Dolphin.this).nextFloat() * 6.2831855F;
/* 447 */     float $$5 = 0.02F * Dolphin.access$200(Dolphin.this).nextFloat();
/* 448 */     $$2.setDeltaMovement((0.3F * 
/* 449 */         -Mth.sin(Dolphin.this.getYRot() * 0.017453292F) * Mth.cos(Dolphin.this.getXRot() * 0.017453292F) + Mth.cos($$4) * $$5), (0.3F * 
/* 450 */         Mth.sin(Dolphin.this.getXRot() * 0.017453292F) * 1.5F), (0.3F * 
/* 451 */         Mth.cos(Dolphin.this.getYRot() * 0.017453292F) * Mth.cos(Dolphin.this.getXRot() * 0.017453292F) + Mth.sin($$4) * $$5));
/*     */ 
/*     */     
/* 454 */     Dolphin.this.level().addFreshEntity((Entity)$$2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Dolphin$PlayWithItemsGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */