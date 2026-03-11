/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class MagmaBlock extends Block {
/* 16 */   public static final MapCodec<MagmaBlock> CODEC = simpleCodec(MagmaBlock::new);
/*    */   private static final int BUBBLE_COLUMN_CHECK_DELAY = 20;
/*    */   
/*    */   public MapCodec<MagmaBlock> codec() {
/* 20 */     return CODEC;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public MagmaBlock(BlockBehaviour.Properties $$0) {
/* 26 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void stepOn(Level $$0, BlockPos $$1, BlockState $$2, Entity $$3) {
/* 31 */     if (!$$3.isSteppingCarefully() && $$3 instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)$$3)) {
/* 32 */       $$3.hurt($$0.damageSources().hotFloor(), 1.0F);
/*    */     }
/*    */     
/* 35 */     super.stepOn($$0, $$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 40 */     BubbleColumnBlock.updateColumn((LevelAccessor)$$1, $$2.above(), $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 45 */     if ($$1 == Direction.UP && $$2.is(Blocks.WATER)) {
/* 46 */       $$3.scheduleTick($$4, this, 20);
/*    */     }
/*    */     
/* 49 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 54 */     $$1.scheduleTick($$2, this, 20);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\MagmaBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */