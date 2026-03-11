/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class ConcretePowderBlock extends FallingBlock {
/*    */   static {
/* 17 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)BuiltInRegistries.BLOCK.byNameCodec().fieldOf("concrete").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, ConcretePowderBlock::new));
/*    */   }
/*    */   
/*    */   public static final MapCodec<ConcretePowderBlock> CODEC;
/*    */   private final Block concrete;
/*    */   
/*    */   public MapCodec<ConcretePowderBlock> codec() {
/* 24 */     return CODEC;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ConcretePowderBlock(Block $$0, BlockBehaviour.Properties $$1) {
/* 30 */     super($$1);
/* 31 */     this.concrete = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onLand(Level $$0, BlockPos $$1, BlockState $$2, BlockState $$3, FallingBlockEntity $$4) {
/* 36 */     if (shouldSolidify((BlockGetter)$$0, $$1, $$3)) {
/* 37 */       $$0.setBlock($$1, this.concrete.defaultBlockState(), 3);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 43 */     Level level = $$0.getLevel();
/* 44 */     BlockPos $$2 = $$0.getClickedPos();
/* 45 */     BlockState $$3 = level.getBlockState($$2);
/*    */     
/* 47 */     if (shouldSolidify((BlockGetter)level, $$2, $$3)) {
/* 48 */       return this.concrete.defaultBlockState();
/*    */     }
/* 50 */     return super.getStateForPlacement($$0);
/*    */   }
/*    */   
/*    */   private static boolean shouldSolidify(BlockGetter $$0, BlockPos $$1, BlockState $$2) {
/* 54 */     return (canSolidify($$2) || touchesLiquid($$0, $$1));
/*    */   }
/*    */   
/*    */   private static boolean touchesLiquid(BlockGetter $$0, BlockPos $$1) {
/* 58 */     boolean $$2 = false;
/* 59 */     BlockPos.MutableBlockPos $$3 = $$1.mutable();
/* 60 */     for (Direction $$4 : Direction.values()) {
/* 61 */       BlockState $$5 = $$0.getBlockState((BlockPos)$$3);
/* 62 */       if ($$4 != Direction.DOWN || canSolidify($$5)) {
/*    */ 
/*    */         
/* 65 */         $$3.setWithOffset((Vec3i)$$1, $$4);
/* 66 */         $$5 = $$0.getBlockState((BlockPos)$$3);
/* 67 */         if (canSolidify($$5) && !$$5.isFaceSturdy($$0, $$1, $$4.getOpposite())) {
/* 68 */           $$2 = true; break;
/*    */         } 
/*    */       } 
/*    */     } 
/* 72 */     return $$2;
/*    */   }
/*    */   
/*    */   private static boolean canSolidify(BlockState $$0) {
/* 76 */     return $$0.getFluidState().is(FluidTags.WATER);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 81 */     if (touchesLiquid((BlockGetter)$$3, $$4)) {
/* 82 */       return this.concrete.defaultBlockState();
/*    */     }
/*    */     
/* 85 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDustColor(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 90 */     return ($$0.getMapColor($$1, $$2)).col;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\ConcretePowderBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */