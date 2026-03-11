/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.MobSpawnType;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class SpawnUtil {
/*    */   public static <T extends Mob> Optional<T> trySpawnMob(EntityType<T> $$0, MobSpawnType $$1, ServerLevel $$2, BlockPos $$3, int $$4, int $$5, int $$6, Strategy $$7) {
/* 21 */     BlockPos.MutableBlockPos $$8 = $$3.mutable();
/* 22 */     for (int $$9 = 0; $$9 < $$4; $$9++) {
/* 23 */       int $$10 = Mth.randomBetweenInclusive($$2.random, -$$5, $$5);
/* 24 */       int $$11 = Mth.randomBetweenInclusive($$2.random, -$$5, $$5);
/*    */       
/* 26 */       $$8.setWithOffset((Vec3i)$$3, $$10, $$6, $$11);
/* 27 */       if ($$2.getWorldBorder().isWithinBounds((BlockPos)$$8) && moveToPossibleSpawnPosition($$2, $$6, $$8, $$7)) {
/*    */ 
/*    */ 
/*    */         
/* 31 */         Mob mob = (Mob)$$0.create($$2, null, null, (BlockPos)$$8, $$1, false, false);
/* 32 */         if (mob != null) {
/* 33 */           if (mob.checkSpawnRules((LevelAccessor)$$2, $$1) && mob.checkSpawnObstruction((LevelReader)$$2)) {
/* 34 */             $$2.addFreshEntityWithPassengers((Entity)mob);
/* 35 */             return Optional.of((T)mob);
/*    */           } 
/* 37 */           mob.discard();
/*    */         } 
/*    */       } 
/*    */     } 
/* 41 */     return Optional.empty();
/*    */   }
/*    */   
/*    */   public static interface Strategy
/*    */   {
/*    */     @Deprecated
/*    */     public static final Strategy LEGACY_IRON_GOLEM;
/*    */     public static final Strategy ON_TOP_OF_COLLIDER;
/*    */     
/*    */     boolean canSpawnOn(ServerLevel param1ServerLevel, BlockPos param1BlockPos1, BlockState param1BlockState1, BlockPos param1BlockPos2, BlockState param1BlockState2);
/*    */     
/*    */     static {
/* 53 */       LEGACY_IRON_GOLEM = (($$0, $$1, $$2, $$3, $$4) -> 
/* 54 */         ($$2.is(Blocks.COBWEB) || $$2.is(Blocks.CACTUS) || $$2.is(Blocks.GLASS_PANE) || $$2.getBlock() instanceof net.minecraft.world.level.block.StainedGlassPaneBlock || $$2.getBlock() instanceof net.minecraft.world.level.block.StainedGlassBlock || $$2.getBlock() instanceof net.minecraft.world.level.block.LeavesBlock || $$2.is(Blocks.CONDUIT) || $$2.is(Blocks.ICE) || $$2.is(Blocks.TNT) || $$2.is(Blocks.GLOWSTONE) || $$2.is(Blocks.BEACON) || $$2.is(Blocks.SEA_LANTERN) || $$2.is(Blocks.FROSTED_ICE) || $$2.is(Blocks.TINTED_GLASS) || $$2.is(Blocks.GLASS)) ? false : (
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
/* 72 */         (($$4.isAir() || $$4.liquid()) && ($$2.isSolid() || $$2.is(Blocks.POWDER_SNOW)))));
/*    */ 
/*    */       
/* 75 */       ON_TOP_OF_COLLIDER = (($$0, $$1, $$2, $$3, $$4) -> 
/* 76 */         ($$4.getCollisionShape((BlockGetter)$$0, $$3).isEmpty() && Block.isFaceFull($$2.getCollisionShape((BlockGetter)$$0, $$1), Direction.UP)));
/*    */     } }
/*    */   
/*    */   private static boolean moveToPossibleSpawnPosition(ServerLevel $$0, int $$1, BlockPos.MutableBlockPos $$2, Strategy $$3) {
/* 80 */     BlockPos.MutableBlockPos $$4 = (new BlockPos.MutableBlockPos()).set((Vec3i)$$2);
/* 81 */     BlockState $$5 = $$0.getBlockState((BlockPos)$$4);
/*    */     
/* 83 */     for (int $$6 = $$1; $$6 >= -$$1; $$6--) {
/* 84 */       $$2.move(Direction.DOWN);
/* 85 */       $$4.setWithOffset((Vec3i)$$2, Direction.UP);
/*    */       
/* 87 */       BlockState $$7 = $$0.getBlockState((BlockPos)$$2);
/* 88 */       if ($$3.canSpawnOn($$0, (BlockPos)$$2, $$7, (BlockPos)$$4, $$5)) {
/* 89 */         $$2.move(Direction.UP);
/* 90 */         return true;
/*    */       } 
/* 92 */       $$5 = $$7;
/*    */     } 
/* 94 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\SpawnUtil.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */