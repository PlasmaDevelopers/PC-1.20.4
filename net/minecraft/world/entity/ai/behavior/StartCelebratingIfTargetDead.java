/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import java.util.function.BiPredicate;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.level.GameRules;
/*    */ 
/*    */ 
/*    */ public class StartCelebratingIfTargetDead
/*    */ {
/*    */   public static BehaviorControl<LivingEntity> create(int $$0, BiPredicate<LivingEntity, LivingEntity> $$1) {
/* 19 */     return BehaviorBuilder.create($$2 -> $$2.group((App)$$2.present(MemoryModuleType.ATTACK_TARGET), (App)$$2.registered(MemoryModuleType.ANGRY_AT), (App)$$2.absent(MemoryModuleType.CELEBRATE_LOCATION), (App)$$2.registered(MemoryModuleType.DANCING)).apply((Applicative)$$2, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\StartCelebratingIfTargetDead.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */