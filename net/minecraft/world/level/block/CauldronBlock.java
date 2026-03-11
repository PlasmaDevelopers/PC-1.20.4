/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.cauldron.CauldronInteraction;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import net.minecraft.world.level.material.Fluids;
/*    */ 
/*    */ public class CauldronBlock extends AbstractCauldronBlock {
/* 14 */   public static final MapCodec<CauldronBlock> CODEC = simpleCodec(CauldronBlock::new); private static final float RAIN_FILL_CHANCE = 0.05F;
/*    */   private static final float POWDER_SNOW_FILL_CHANCE = 0.1F;
/*    */   
/*    */   public MapCodec<CauldronBlock> codec() {
/* 18 */     return CODEC;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CauldronBlock(BlockBehaviour.Properties $$0) {
/* 25 */     super($$0, CauldronInteraction.EMPTY);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFull(BlockState $$0) {
/* 30 */     return false;
/*    */   }
/*    */   
/*    */   protected static boolean shouldHandlePrecipitation(Level $$0, Biome.Precipitation $$1) {
/* 34 */     if ($$1 == Biome.Precipitation.RAIN)
/* 35 */       return ($$0.getRandom().nextFloat() < 0.05F); 
/* 36 */     if ($$1 == Biome.Precipitation.SNOW) {
/* 37 */       return ($$0.getRandom().nextFloat() < 0.1F);
/*    */     }
/* 39 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void handlePrecipitation(BlockState $$0, Level $$1, BlockPos $$2, Biome.Precipitation $$3) {
/* 44 */     if (!shouldHandlePrecipitation($$1, $$3)) {
/*    */       return;
/*    */     }
/*    */     
/* 48 */     if ($$3 == Biome.Precipitation.RAIN) {
/* 49 */       $$1.setBlockAndUpdate($$2, Blocks.WATER_CAULDRON.defaultBlockState());
/* 50 */       $$1.gameEvent(null, GameEvent.BLOCK_CHANGE, $$2);
/* 51 */     } else if ($$3 == Biome.Precipitation.SNOW) {
/* 52 */       $$1.setBlockAndUpdate($$2, Blocks.POWDER_SNOW_CAULDRON.defaultBlockState());
/* 53 */       $$1.gameEvent(null, GameEvent.BLOCK_CHANGE, $$2);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canReceiveStalactiteDrip(Fluid $$0) {
/* 59 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void receiveStalactiteDrip(BlockState $$0, Level $$1, BlockPos $$2, Fluid $$3) {
/* 64 */     if ($$3 == Fluids.WATER) {
/* 65 */       BlockState $$4 = Blocks.WATER_CAULDRON.defaultBlockState();
/* 66 */       $$1.setBlockAndUpdate($$2, $$4);
/* 67 */       $$1.gameEvent(GameEvent.BLOCK_CHANGE, $$2, GameEvent.Context.of($$4));
/* 68 */       $$1.levelEvent(1047, $$2, 0);
/* 69 */     } else if ($$3 == Fluids.LAVA) {
/* 70 */       BlockState $$5 = Blocks.LAVA_CAULDRON.defaultBlockState();
/* 71 */       $$1.setBlockAndUpdate($$2, $$5);
/* 72 */       $$1.gameEvent(GameEvent.BLOCK_CHANGE, $$2, GameEvent.Context.of($$5));
/* 73 */       $$1.levelEvent(1046, $$2, 0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CauldronBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */