/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.GlobalPos;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*    */ import net.minecraft.world.entity.npc.Villager;
/*    */ import org.apache.commons.lang3.mutable.MutableLong;
/*    */ 
/*    */ public class StrollToPoiList {
/*    */   public static BehaviorControl<Villager> create(MemoryModuleType<List<GlobalPos>> $$0, float $$1, int $$2, int $$3, MemoryModuleType<GlobalPos> $$4) {
/* 17 */     MutableLong $$5 = new MutableLong(0L);
/*    */     
/* 19 */     return BehaviorBuilder.create($$6 -> $$6.group((App)$$6.registered(MemoryModuleType.WALK_TARGET), (App)$$6.present($$0), (App)$$6.present($$1)).apply((Applicative)$$6, ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\StrollToPoiList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */