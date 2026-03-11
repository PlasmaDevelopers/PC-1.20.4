/*    */ package net.minecraft.world.entity.animal.axolotl;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ public class ValidatePlayDead {
/*    */   public static BehaviorControl<LivingEntity> create() {
/* 10 */     return (BehaviorControl<LivingEntity>)BehaviorBuilder.create($$0 -> $$0.group((App)$$0.present(MemoryModuleType.PLAY_DEAD_TICKS), (App)$$0.registered(MemoryModuleType.HURT_BY_ENTITY)).apply((Applicative)$$0, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\axolotl\ValidatePlayDead.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */