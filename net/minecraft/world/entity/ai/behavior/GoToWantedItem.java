/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*    */ import net.minecraft.world.entity.item.ItemEntity;
/*    */ 
/*    */ public class GoToWantedItem {
/*    */   public static BehaviorControl<LivingEntity> create(float $$0, boolean $$1, int $$2) {
/* 13 */     return create($$0 -> true, $$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   public static <E extends LivingEntity> BehaviorControl<E> create(Predicate<E> $$0, float $$1, boolean $$2, int $$3) {
/* 17 */     return BehaviorBuilder.create($$4 -> {
/*    */           BehaviorBuilder<E, ? extends MemoryAccessor<? extends K1, WalkTarget>> $$5 = $$0 ? $$4.registered(MemoryModuleType.WALK_TARGET) : $$4.absent(MemoryModuleType.WALK_TARGET);
/*    */           return $$4.group((App)$$4.registered(MemoryModuleType.LOOK_TARGET), (App)$$5, (App)$$4.present(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM), (App)$$4.registered(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS)).apply((Applicative)$$4, ());
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\GoToWantedItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */