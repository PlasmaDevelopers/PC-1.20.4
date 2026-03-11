/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.MobCategory;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
/*    */ 
/*    */ public class SetEntityLookTarget {
/*    */   public static BehaviorControl<LivingEntity> create(MobCategory $$0, float $$1) {
/* 18 */     return create($$1 -> $$0.equals($$1.getType().getCategory()), $$1);
/*    */   }
/*    */   
/*    */   public static OneShot<LivingEntity> create(EntityType<?> $$0, float $$1) {
/* 22 */     return create($$1 -> $$0.equals($$1.getType()), $$1);
/*    */   }
/*    */   
/*    */   public static OneShot<LivingEntity> create(float $$0) {
/* 26 */     return create($$0 -> true, $$0);
/*    */   }
/*    */   
/*    */   public static OneShot<LivingEntity> create(Predicate<LivingEntity> $$0, float $$1) {
/* 30 */     float $$2 = $$1 * $$1;
/*    */     
/* 32 */     return BehaviorBuilder.create($$2 -> $$2.group((App)$$2.absent(MemoryModuleType.LOOK_TARGET), (App)$$2.present(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)).apply((Applicative)$$2, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\SetEntityLookTarget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */