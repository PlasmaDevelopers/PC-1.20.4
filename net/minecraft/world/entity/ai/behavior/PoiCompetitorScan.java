/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.GlobalPos;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.MemoryAccessor;
/*    */ import net.minecraft.world.entity.ai.behavior.declarative.Trigger;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.village.poi.PoiType;
/*    */ import net.minecraft.world.entity.npc.Villager;
/*    */ import net.minecraft.world.entity.npc.VillagerProfession;
/*    */ 
/*    */ public class PoiCompetitorScan
/*    */ {
/*    */   public static BehaviorControl<Villager> create() {
/* 22 */     return BehaviorBuilder.create($$0 -> $$0.group((App)$$0.present(MemoryModuleType.JOB_SITE), (App)$$0.present(MemoryModuleType.NEAREST_LIVING_ENTITIES)).apply((Applicative)$$0, ()));
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
/*    */   private static Villager selectWinner(Villager $$0, Villager $$1) {
/*    */     Villager $$4, $$5;
/* 42 */     if ($$0.getVillagerXp() > $$1.getVillagerXp()) {
/* 43 */       Villager $$2 = $$0;
/* 44 */       Villager $$3 = $$1;
/*    */     } else {
/* 46 */       $$4 = $$1;
/* 47 */       $$5 = $$0;
/*    */     } 
/*    */     
/* 50 */     $$5.getBrain().eraseMemory(MemoryModuleType.JOB_SITE);
/* 51 */     return $$4;
/*    */   }
/*    */   
/*    */   private static boolean competesForSameJobsite(GlobalPos $$0, Holder<PoiType> $$1, Villager $$2) {
/* 55 */     Optional<GlobalPos> $$3 = $$2.getBrain().getMemory(MemoryModuleType.JOB_SITE);
/* 56 */     return ($$3.isPresent() && $$0
/* 57 */       .equals($$3.get()) && 
/* 58 */       hasMatchingProfession($$1, $$2.getVillagerData().getProfession()));
/*    */   }
/*    */   
/*    */   private static boolean hasMatchingProfession(Holder<PoiType> $$0, VillagerProfession $$1) {
/* 62 */     return $$1.heldJobSite().test($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\PoiCompetitorScan.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */