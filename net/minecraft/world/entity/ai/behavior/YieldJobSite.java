/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.GlobalPos;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.network.protocol.game.DebugPackets;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.village.poi.PoiType;
/*    */ import net.minecraft.world.entity.npc.Villager;
/*    */ import net.minecraft.world.entity.npc.VillagerProfession;
/*    */ import net.minecraft.world.level.pathfinder.Path;
/*    */ 
/*    */ public class YieldJobSite {
/*    */   public static BehaviorControl<Villager> create(float $$0) {
/* 24 */     return BehaviorBuilder.create($$1 -> $$1.group((App)$$1.present(MemoryModuleType.POTENTIAL_JOB_SITE), (App)$$1.absent(MemoryModuleType.JOB_SITE), (App)$$1.present(MemoryModuleType.NEAREST_LIVING_ENTITIES), (App)$$1.registered(MemoryModuleType.WALK_TARGET), (App)$$1.registered(MemoryModuleType.LOOK_TARGET)).apply((Applicative)$$1, ()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static boolean nearbyWantsJobsite(Holder<PoiType> $$0, Villager $$1, BlockPos $$2) {
/* 70 */     boolean $$3 = $$1.getBrain().getMemory(MemoryModuleType.POTENTIAL_JOB_SITE).isPresent();
/* 71 */     if ($$3) {
/* 72 */       return false;
/*    */     }
/*    */     
/* 75 */     Optional<GlobalPos> $$4 = $$1.getBrain().getMemory(MemoryModuleType.JOB_SITE);
/* 76 */     VillagerProfession $$5 = $$1.getVillagerData().getProfession();
/*    */ 
/*    */     
/* 79 */     if ($$5.heldJobSite().test($$0)) {
/* 80 */       if ($$4.isEmpty()) {
/* 81 */         return canReachPos((PathfinderMob)$$1, $$2, (PoiType)$$0.value());
/*    */       }
/* 83 */       return ((GlobalPos)$$4.get()).pos().equals($$2);
/*    */     } 
/* 85 */     return false;
/*    */   }
/*    */   
/*    */   private static boolean canReachPos(PathfinderMob $$0, BlockPos $$1, PoiType $$2) {
/* 89 */     Path $$3 = $$0.getNavigation().createPath($$1, $$2.validRange());
/* 90 */     return ($$3 != null && $$3.canReach());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\YieldJobSite.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */