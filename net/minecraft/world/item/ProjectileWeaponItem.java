/*    */ package net.minecraft.world.item;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ 
/*    */ public abstract class ProjectileWeaponItem extends Item {
/*    */   public static final Predicate<ItemStack> ARROW_ONLY;
/*    */   
/*    */   static {
/* 10 */     ARROW_ONLY = ($$0 -> $$0.is(ItemTags.ARROWS));
/* 11 */     ARROW_OR_FIREWORK = ARROW_ONLY.or($$0 -> $$0.is(Items.FIREWORK_ROCKET));
/*    */   } public static final Predicate<ItemStack> ARROW_OR_FIREWORK;
/*    */   public ProjectileWeaponItem(Item.Properties $$0) {
/* 14 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Predicate<ItemStack> getSupportedHeldProjectiles() {
/* 19 */     return getAllSupportedProjectiles();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ItemStack getHeldProjectile(LivingEntity $$0, Predicate<ItemStack> $$1) {
/* 26 */     if ($$1.test($$0.getItemInHand(InteractionHand.OFF_HAND))) {
/* 27 */       return $$0.getItemInHand(InteractionHand.OFF_HAND);
/*    */     }
/* 29 */     if ($$1.test($$0.getItemInHand(InteractionHand.MAIN_HAND))) {
/* 30 */       return $$0.getItemInHand(InteractionHand.MAIN_HAND);
/*    */     }
/* 32 */     return ItemStack.EMPTY;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEnchantmentValue() {
/* 37 */     return 1;
/*    */   }
/*    */   
/*    */   public abstract Predicate<ItemStack> getAllSupportedProjectiles();
/*    */   
/*    */   public abstract int getDefaultProjectileRange();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ProjectileWeaponItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */