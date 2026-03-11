/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.ProjectileWeaponItem;
/*    */ 
/*    */ public class MeleeAttack {
/*    */   public static OneShot<Mob> create(int $$0) {
/* 17 */     return BehaviorBuilder.create($$1 -> $$1.group((App)$$1.registered(MemoryModuleType.LOOK_TARGET), (App)$$1.present(MemoryModuleType.ATTACK_TARGET), (App)$$1.absent(MemoryModuleType.ATTACK_COOLING_DOWN), (App)$$1.present(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)).apply((Applicative)$$1, ()));
/*    */   }
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
/*    */ 
/*    */   
/*    */   private static boolean isHoldingUsableProjectileWeapon(Mob $$0) {
/* 38 */     return $$0.isHolding($$1 -> {
/*    */           Item $$2 = $$1.getItem();
/* 40 */           return ($$2 instanceof ProjectileWeaponItem && $$0.canFireProjectileWeapon((ProjectileWeaponItem)$$2));
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\MeleeAttack.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */