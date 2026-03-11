/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ public class ReactToBell {
/*    */   public static BehaviorControl<LivingEntity> create() {
/* 11 */     return BehaviorBuilder.create($$0 -> $$0.group((App)$$0.present(MemoryModuleType.HEARD_BELL_TIME)).apply((Applicative)$$0, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\ReactToBell.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */