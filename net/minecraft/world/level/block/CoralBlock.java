/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.BiFunction;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ 
/*    */ public class CoralBlock extends Block {
/* 20 */   public static final MapCodec<Block> DEAD_CORAL_FIELD = BuiltInRegistries.BLOCK.byNameCodec().fieldOf("dead"); public static final MapCodec<CoralBlock> CODEC; private final Block deadBlock;
/*    */   static {
/* 22 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)DEAD_CORAL_FIELD.forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, CoralBlock::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CoralBlock(Block $$0, BlockBehaviour.Properties $$1) {
/* 30 */     super($$1);
/* 31 */     this.deadBlock = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public MapCodec<CoralBlock> codec() {
/* 36 */     return CODEC;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 41 */     if (!scanForWater((BlockGetter)$$1, $$2)) {
/* 42 */       $$1.setBlock($$2, this.deadBlock.defaultBlockState(), 2);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 48 */     if (!scanForWater((BlockGetter)$$3, $$4)) {
/* 49 */       $$3.scheduleTick($$4, this, 60 + $$3.getRandom().nextInt(40));
/*    */     }
/* 51 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */   
/*    */   protected boolean scanForWater(BlockGetter $$0, BlockPos $$1) {
/* 55 */     for (Direction $$2 : Direction.values()) {
/* 56 */       FluidState $$3 = $$0.getFluidState($$1.relative($$2));
/* 57 */       if ($$3.is(FluidTags.WATER)) {
/* 58 */         return true;
/*    */       }
/*    */     } 
/* 61 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 67 */     if (!scanForWater((BlockGetter)$$0.getLevel(), $$0.getClickedPos())) {
/* 68 */       $$0.getLevel().scheduleTick($$0.getClickedPos(), this, 60 + $$0.getLevel().getRandom().nextInt(40));
/*    */     }
/* 70 */     return defaultBlockState();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CoralBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */