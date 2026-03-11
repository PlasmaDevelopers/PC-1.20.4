/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import net.minecraft.core.GlobalPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
/*    */ 
/*    */ public class SocializeAtBell {
/*    */   public static OneShot<LivingEntity> create() {
/* 15 */     return BehaviorBuilder.create($$0 -> $$0.group((App)$$0.registered(MemoryModuleType.WALK_TARGET), (App)$$0.registered(MemoryModuleType.LOOK_TARGET), (App)$$0.present(MemoryModuleType.MEETING_POINT), (App)$$0.present(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES), (App)$$0.absent(MemoryModuleType.INTERACTION_TARGET)).apply((Applicative)$$0, ()));
/*    */   }
/*    */   
/*    */   private static final float SPEED_MODIFIER = 0.3F;
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\SocializeAtBell.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */