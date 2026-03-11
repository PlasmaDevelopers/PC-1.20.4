/*    */ package net.minecraft.world.entity.ai.sensing;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.GlobalPos;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.Brain;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.npc.Villager;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class SecondaryPoiSensor extends Sensor<Villager> {
/*    */   private static final int SCAN_RATE = 40;
/*    */   
/*    */   public SecondaryPoiSensor() {
/* 21 */     super(40);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doTick(ServerLevel $$0, Villager $$1) {
/* 26 */     ResourceKey<Level> $$2 = $$0.dimension();
/* 27 */     BlockPos $$3 = $$1.blockPosition();
/* 28 */     List<GlobalPos> $$4 = Lists.newArrayList();
/*    */     
/* 30 */     int $$5 = 4;
/* 31 */     for (int $$6 = -4; $$6 <= 4; $$6++) {
/* 32 */       for (int $$7 = -2; $$7 <= 2; $$7++) {
/* 33 */         for (int $$8 = -4; $$8 <= 4; $$8++) {
/* 34 */           BlockPos $$9 = $$3.offset($$6, $$7, $$8);
/* 35 */           if ($$1.getVillagerData().getProfession().secondaryPoi().contains($$0.getBlockState($$9).getBlock())) {
/* 36 */             $$4.add(GlobalPos.of($$2, $$9));
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 42 */     Brain<?> $$10 = $$1.getBrain();
/* 43 */     if (!$$4.isEmpty()) {
/* 44 */       $$10.setMemory(MemoryModuleType.SECONDARY_JOB_SITE, $$4);
/*    */     } else {
/* 46 */       $$10.eraseMemory(MemoryModuleType.SECONDARY_JOB_SITE);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<MemoryModuleType<?>> requires() {
/* 52 */     return (Set<MemoryModuleType<?>>)ImmutableSet.of(MemoryModuleType.SECONDARY_JOB_SITE);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\sensing\SecondaryPoiSensor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */