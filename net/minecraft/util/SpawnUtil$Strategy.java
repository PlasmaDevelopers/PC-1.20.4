/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
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
/*    */ public interface Strategy
/*    */ {
/*    */   @Deprecated
/*    */   public static final Strategy LEGACY_IRON_GOLEM;
/*    */   public static final Strategy ON_TOP_OF_COLLIDER;
/*    */   
/*    */   static {
/* 53 */     LEGACY_IRON_GOLEM = (($$0, $$1, $$2, $$3, $$4) -> 
/* 54 */       ($$2.is(Blocks.COBWEB) || $$2.is(Blocks.CACTUS) || $$2.is(Blocks.GLASS_PANE) || $$2.getBlock() instanceof net.minecraft.world.level.block.StainedGlassPaneBlock || $$2.getBlock() instanceof net.minecraft.world.level.block.StainedGlassBlock || $$2.getBlock() instanceof net.minecraft.world.level.block.LeavesBlock || $$2.is(Blocks.CONDUIT) || $$2.is(Blocks.ICE) || $$2.is(Blocks.TNT) || $$2.is(Blocks.GLOWSTONE) || $$2.is(Blocks.BEACON) || $$2.is(Blocks.SEA_LANTERN) || $$2.is(Blocks.FROSTED_ICE) || $$2.is(Blocks.TINTED_GLASS) || $$2.is(Blocks.GLASS)) ? false : (
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
/* 72 */       (($$4.isAir() || $$4.liquid()) && ($$2.isSolid() || $$2.is(Blocks.POWDER_SNOW)))));
/*    */ 
/*    */     
/* 75 */     ON_TOP_OF_COLLIDER = (($$0, $$1, $$2, $$3, $$4) -> 
/* 76 */       ($$4.getCollisionShape((BlockGetter)$$0, $$3).isEmpty() && Block.isFaceFull($$2.getCollisionShape((BlockGetter)$$0, $$1), Direction.UP)));
/*    */   }
/*    */   
/*    */   boolean canSpawnOn(ServerLevel paramServerLevel, BlockPos paramBlockPos1, BlockState paramBlockState1, BlockPos paramBlockPos2, BlockState paramBlockState2);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\SpawnUtil$Strategy.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */