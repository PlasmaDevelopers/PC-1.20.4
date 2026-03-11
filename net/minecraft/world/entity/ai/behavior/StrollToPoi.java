/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import net.minecraft.core.GlobalPos;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import org.apache.commons.lang3.mutable.MutableLong;
/*    */ 
/*    */ public class StrollToPoi {
/*    */   public static BehaviorControl<PathfinderMob> create(MemoryModuleType<GlobalPos> $$0, float $$1, int $$2, int $$3) {
/* 15 */     MutableLong $$4 = new MutableLong(0L);
/*    */     
/* 17 */     return BehaviorBuilder.create($$5 -> $$5.group((App)$$5.registered(MemoryModuleType.WALK_TARGET), (App)$$5.present($$0)).apply((Applicative)$$5, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\StrollToPoi.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */