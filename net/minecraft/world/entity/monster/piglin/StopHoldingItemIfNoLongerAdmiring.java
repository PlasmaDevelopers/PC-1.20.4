/*    */ package net.minecraft.world.entity.monster.piglin;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import net.minecraft.world.entity.ai.behavior.BehaviorControl;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ 
/*    */ public class StopHoldingItemIfNoLongerAdmiring {
/*    */   public static BehaviorControl<Piglin> create() {
/* 10 */     return (BehaviorControl<Piglin>)BehaviorBuilder.create($$0 -> $$0.group((App)$$0.absent(MemoryModuleType.ADMIRING_ITEM)).apply((Applicative)$$0, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\piglin\StopHoldingItemIfNoLongerAdmiring.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */