/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
/*    */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*    */ 
/*    */ public class InteractWith {
/*    */   public static <T extends LivingEntity> BehaviorControl<LivingEntity> of(EntityType<? extends T> $$0, int $$1, MemoryModuleType<T> $$2, float $$3, int $$4) {
/* 18 */     return of($$0, $$1, $$0 -> true, $$0 -> true, $$2, $$3, $$4);
/*    */   }
/*    */   
/*    */   public static <E extends LivingEntity, T extends LivingEntity> BehaviorControl<E> of(EntityType<? extends T> $$0, int $$1, Predicate<E> $$2, Predicate<T> $$3, MemoryModuleType<T> $$4, float $$5, int $$6) {
/* 22 */     int $$7 = $$1 * $$1;
/* 23 */     Predicate<LivingEntity> $$8 = $$2 -> ($$0.equals($$2.getType()) && $$1.test($$2));
/*    */     
/* 25 */     return BehaviorBuilder.create($$6 -> $$6.group((App)$$6.registered($$0), (App)$$6.registered(MemoryModuleType.LOOK_TARGET), (App)$$6.absent(MemoryModuleType.WALK_TARGET), (App)$$6.present(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)).apply((Applicative)$$6, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\InteractWith.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */