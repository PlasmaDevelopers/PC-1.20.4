/*    */ package net.minecraft.world.entity;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MobCanWearArmorEntitySelector
/*    */   implements Predicate<Entity>
/*    */ {
/*    */   private final ItemStack itemStack;
/*    */   
/*    */   public MobCanWearArmorEntitySelector(ItemStack $$0) {
/* 28 */     this.itemStack = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(@Nullable Entity $$0) {
/* 33 */     if (!$$0.isAlive()) {
/* 34 */       return false;
/*    */     }
/* 36 */     if (!($$0 instanceof LivingEntity)) {
/* 37 */       return false;
/*    */     }
/* 39 */     LivingEntity $$1 = (LivingEntity)$$0;
/* 40 */     return $$1.canTakeItem(this.itemStack);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\EntitySelector$MobCanWearArmorEntitySelector.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */