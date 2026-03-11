/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class UseItemGoal<T extends Mob>
/*    */   extends Goal {
/*    */   private final T mob;
/*    */   private final ItemStack item;
/*    */   private final Predicate<? super T> canUseSelector;
/*    */   @Nullable
/*    */   private final SoundEvent finishUsingSound;
/*    */   
/*    */   public UseItemGoal(T $$0, ItemStack $$1, @Nullable SoundEvent $$2, Predicate<? super T> $$3) {
/* 20 */     this.mob = $$0;
/* 21 */     this.item = $$1;
/* 22 */     this.finishUsingSound = $$2;
/* 23 */     this.canUseSelector = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 28 */     return this.canUseSelector.test(this.mob);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canContinueToUse() {
/* 33 */     return this.mob.isUsingItem();
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 38 */     this.mob.setItemSlot(EquipmentSlot.MAINHAND, this.item.copy());
/* 39 */     this.mob.startUsingItem(InteractionHand.MAIN_HAND);
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 44 */     this.mob.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
/*    */     
/* 46 */     if (this.finishUsingSound != null)
/* 47 */       this.mob.playSound(this.finishUsingSound, 1.0F, this.mob.getRandom().nextFloat() * 0.2F + 0.9F); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\UseItemGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */