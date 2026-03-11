/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import java.util.function.BiPredicate;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ public class DismountOrSkipMounting {
/*    */   public static <E extends LivingEntity> BehaviorControl<E> create(int $$0, BiPredicate<E, Entity> $$1) {
/* 16 */     return BehaviorBuilder.create($$2 -> $$2.group((App)$$2.registered(MemoryModuleType.RIDE_TARGET)).apply((Applicative)$$2, ()));
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
/*    */   private static boolean isVehicleValid(LivingEntity $$0, Entity $$1, int $$2) {
/* 36 */     return ($$1.isAlive() && $$1
/* 37 */       .closerThan((Entity)$$0, $$2) && $$1
/* 38 */       .level() == $$0.level());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\DismountOrSkipMounting.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */