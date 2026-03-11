/*    */ package net.minecraft.world.level.levelgen;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.stats.ServerStatsCounter;
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.DifficultyInstance;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.MobSpawnType;
/*    */ import net.minecraft.world.entity.SpawnGroupData;
/*    */ import net.minecraft.world.entity.monster.Phantom;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.CustomSpawner;
/*    */ import net.minecraft.world.level.GameRules;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.NaturalSpawner;
/*    */ import net.minecraft.world.level.ServerLevelAccessor;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ 
/*    */ public class PhantomSpawner
/*    */   implements CustomSpawner {
/*    */   public int tick(ServerLevel $$0, boolean $$1, boolean $$2) {
/* 28 */     if (!$$1) {
/* 29 */       return 0;
/*    */     }
/*    */     
/* 32 */     if (!$$0.getGameRules().getBoolean(GameRules.RULE_DOINSOMNIA)) {
/* 33 */       return 0;
/*    */     }
/*    */     
/* 36 */     RandomSource $$3 = $$0.random;
/*    */     
/* 38 */     this.nextTick--;
/* 39 */     if (this.nextTick > 0) {
/* 40 */       return 0;
/*    */     }
/* 42 */     this.nextTick += (60 + $$3.nextInt(60)) * 20;
/*    */     
/* 44 */     if ($$0.getSkyDarken() < 5 && $$0.dimensionType().hasSkyLight()) {
/* 45 */       return 0;
/*    */     }
/*    */     
/* 48 */     int $$4 = 0;
/* 49 */     for (ServerPlayer $$5 : $$0.players()) {
/* 50 */       if ($$5.isSpectator()) {
/*    */         continue;
/*    */       }
/* 53 */       BlockPos $$6 = $$5.blockPosition();
/* 54 */       if ($$0.dimensionType().hasSkyLight() && ($$6.getY() < $$0.getSeaLevel() || !$$0.canSeeSky($$6))) {
/*    */         continue;
/*    */       }
/* 57 */       DifficultyInstance $$7 = $$0.getCurrentDifficultyAt($$6);
/* 58 */       if (!$$7.isHarderThan($$3.nextFloat() * 3.0F)) {
/*    */         continue;
/*    */       }
/*    */       
/* 62 */       ServerStatsCounter $$8 = $$5.getStats();
/* 63 */       int $$9 = Mth.clamp($$8.getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST)), 1, 2147483647);
/* 64 */       int $$10 = 24000;
/* 65 */       if ($$3.nextInt($$9) < 72000) {
/*    */         continue;
/*    */       }
/*    */       
/* 69 */       BlockPos $$11 = $$6.above(20 + $$3.nextInt(15)).east(-10 + $$3.nextInt(21)).south(-10 + $$3.nextInt(21));
/* 70 */       BlockState $$12 = $$0.getBlockState($$11);
/* 71 */       FluidState $$13 = $$0.getFluidState($$11);
/* 72 */       if (!NaturalSpawner.isValidEmptySpawnBlock((BlockGetter)$$0, $$11, $$12, $$13, EntityType.PHANTOM)) {
/*    */         continue;
/*    */       }
/*    */       
/* 76 */       SpawnGroupData $$14 = null;
/* 77 */       int $$15 = 1 + $$3.nextInt($$7.getDifficulty().getId() + 1);
/* 78 */       for (int $$16 = 0; $$16 < $$15; $$16++) {
/* 79 */         Phantom $$17 = (Phantom)EntityType.PHANTOM.create((Level)$$0);
/* 80 */         if ($$17 != null) {
/* 81 */           $$17.moveTo($$11, 0.0F, 0.0F);
/* 82 */           $$14 = $$17.finalizeSpawn((ServerLevelAccessor)$$0, $$7, MobSpawnType.NATURAL, $$14, null);
/* 83 */           $$0.addFreshEntityWithPassengers((Entity)$$17);
/* 84 */           $$4++;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 89 */     return $$4;
/*    */   }
/*    */   
/*    */   private int nextTick;
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\PhantomSpawner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */