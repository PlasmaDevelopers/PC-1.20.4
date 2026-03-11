/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.GlobalPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import org.apache.commons.lang3.mutable.MutableInt;
/*    */ 
/*    */ public class SetHiddenState
/*    */ {
/*    */   private static final int HIDE_TIMEOUT = 300;
/*    */   
/*    */   public static BehaviorControl<LivingEntity> create(int $$0, int $$1) {
/* 21 */     int $$2 = $$0 * 20;
/*    */ 
/*    */     
/* 24 */     MutableInt $$3 = new MutableInt(0);
/*    */     
/* 26 */     return BehaviorBuilder.create($$3 -> $$3.group((App)$$3.present(MemoryModuleType.HIDING_PLACE), (App)$$3.present(MemoryModuleType.HEARD_BELL_TIME)).apply((Applicative)$$3, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\SetHiddenState.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */