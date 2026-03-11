/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ public class VillagerCalmDown {
/*    */   public static BehaviorControl<LivingEntity> create() {
/* 15 */     return BehaviorBuilder.create($$0 -> $$0.group((App)$$0.registered(MemoryModuleType.HURT_BY), (App)$$0.registered(MemoryModuleType.HURT_BY_ENTITY), (App)$$0.registered(MemoryModuleType.NEAREST_HOSTILE)).apply((Applicative)$$0, ()));
/*    */   }
/*    */   
/*    */   private static final int SAFE_DISTANCE_FROM_DANGER = 36;
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\VillagerCalmDown.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */