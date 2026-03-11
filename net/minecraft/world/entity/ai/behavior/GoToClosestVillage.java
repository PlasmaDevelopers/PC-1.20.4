/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.village.poi.PoiManager;
/*    */ import net.minecraft.world.entity.npc.Villager;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class GoToClosestVillage {
/*    */   public static BehaviorControl<Villager> create(float $$0, int $$1) {
/* 15 */     return BehaviorBuilder.create($$2 -> $$2.group((App)$$2.absent(MemoryModuleType.WALK_TARGET)).apply((Applicative)$$2, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\GoToClosestVillage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */