/*    */ package net.minecraft.world.level.levelgen.carver;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Set;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.chunk.CarvingMask;
/*    */ import net.minecraft.world.level.chunk.ChunkAccess;
/*    */ import net.minecraft.world.level.levelgen.Aquifer;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import net.minecraft.world.level.material.Fluids;
/*    */ import org.apache.commons.lang3.mutable.MutableBoolean;
/*    */ 
/*    */ public class NetherWorldCarver extends CaveWorldCarver {
/*    */   public NetherWorldCarver(Codec<CaveCarverConfiguration> $$0) {
/* 20 */     super($$0);
/* 21 */     this.liquids = (Set<Fluid>)ImmutableSet.of(Fluids.LAVA, Fluids.WATER);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getCaveBound() {
/* 29 */     return 10;
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getThickness(RandomSource $$0) {
/* 34 */     return ($$0.nextFloat() * 2.0F + $$0.nextFloat()) * 2.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   protected double getYScale() {
/* 39 */     return 5.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean carveBlock(CarvingContext $$0, CaveCarverConfiguration $$1, ChunkAccess $$2, Function<BlockPos, Holder<Biome>> $$3, CarvingMask $$4, BlockPos.MutableBlockPos $$5, BlockPos.MutableBlockPos $$6, Aquifer $$7, MutableBoolean $$8) {
/* 44 */     if (canReplaceBlock($$1, $$2.getBlockState((BlockPos)$$5))) {
/*    */       BlockState $$10;
/* 46 */       if ($$5.getY() <= $$0.getMinGenY() + 31) {
/* 47 */         BlockState $$9 = LAVA.createLegacyBlock();
/*    */       } else {
/* 49 */         $$10 = CAVE_AIR;
/*    */       } 
/* 51 */       $$2.setBlockState((BlockPos)$$5, $$10, false);
/* 52 */       return true;
/*    */     } 
/* 54 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\carver\NetherWorldCarver.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */