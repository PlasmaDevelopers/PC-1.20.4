/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
/*     */ import net.minecraft.world.level.block.state.properties.IntegerProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class PitcherCropBlock extends DoublePlantBlock implements BonemealableBlock {
/*  29 */   public static final MapCodec<PitcherCropBlock> CODEC = simpleCodec(PitcherCropBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<PitcherCropBlock> codec() {
/*  33 */     return CODEC;
/*     */   }
/*     */   
/*  36 */   public static final IntegerProperty AGE = BlockStateProperties.AGE_4;
/*     */   
/*     */   public static final int MAX_AGE = 4;
/*     */   
/*     */   private static final int DOUBLE_PLANT_AGE_INTERSECTION = 3;
/*     */   
/*     */   private static final int BONEMEAL_INCREASE = 1;
/*  43 */   private static final VoxelShape FULL_UPPER_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 15.0D, 13.0D);
/*  44 */   private static final VoxelShape FULL_LOWER_SHAPE = Block.box(3.0D, -1.0D, 3.0D, 13.0D, 16.0D, 13.0D);
/*     */   
/*  46 */   private static final VoxelShape COLLISION_SHAPE_BULB = Block.box(5.0D, -1.0D, 5.0D, 11.0D, 3.0D, 11.0D);
/*  47 */   private static final VoxelShape COLLISION_SHAPE_CROP = Block.box(3.0D, -1.0D, 3.0D, 13.0D, 5.0D, 13.0D);
/*     */   
/*  49 */   private static final VoxelShape[] UPPER_SHAPE_BY_AGE = new VoxelShape[] {
/*  50 */       Block.box(3.0D, 0.0D, 3.0D, 13.0D, 11.0D, 13.0D), FULL_UPPER_SHAPE
/*     */     };
/*     */ 
/*     */   
/*  54 */   private static final VoxelShape[] LOWER_SHAPE_BY_AGE = new VoxelShape[] { COLLISION_SHAPE_BULB, 
/*     */       
/*  56 */       Block.box(3.0D, -1.0D, 3.0D, 13.0D, 14.0D, 13.0D), FULL_LOWER_SHAPE, FULL_LOWER_SHAPE, FULL_LOWER_SHAPE };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PitcherCropBlock(BlockBehaviour.Properties $$0) {
/*  63 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  69 */     return defaultBlockState();
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  74 */     return ($$0.getValue((Property)HALF) == DoubleBlockHalf.UPPER) ? UPPER_SHAPE_BY_AGE[Math.min(Math.abs(4 - ((Integer)$$0.getValue((Property)AGE)).intValue() + 1), UPPER_SHAPE_BY_AGE.length - 1)] : LOWER_SHAPE_BY_AGE[((Integer)$$0.getValue((Property)AGE)).intValue()];
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  79 */     if (((Integer)$$0.getValue((Property)AGE)).intValue() == 0)
/*  80 */       return COLLISION_SHAPE_BULB; 
/*  81 */     if ($$0.getValue((Property)HALF) == DoubleBlockHalf.LOWER) {
/*  82 */       return COLLISION_SHAPE_CROP;
/*     */     }
/*  84 */     return super.getCollisionShape($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  89 */     if (isDouble(((Integer)$$0.getValue((Property)AGE)).intValue())) {
/*  90 */       return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     }
/*  92 */     return $$0.canSurvive((LevelReader)$$3, $$4) ? $$0 : Blocks.AIR.defaultBlockState();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  98 */     if (isLower($$0) && !sufficientLight($$1, $$2)) {
/*  99 */       return false;
/*     */     }
/* 101 */     return super.canSurvive($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean mayPlaceOn(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 106 */     return $$0.is(Blocks.FARMLAND);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 111 */     $$0.add(new Property[] { (Property)AGE });
/* 112 */     super.createBlockStateDefinition($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/* 117 */     if ($$3 instanceof net.minecraft.world.entity.monster.Ravager && $$1.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
/* 118 */       $$1.destroyBlock($$2, true, $$3);
/*     */     }
/* 120 */     super.entityInside($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeReplaced(BlockState $$0, BlockPlaceContext $$1) {
/* 125 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, LivingEntity $$3, ItemStack $$4) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRandomlyTicking(BlockState $$0) {
/* 136 */     return ($$0.getValue((Property)HALF) == DoubleBlockHalf.LOWER && !isMaxAge($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 141 */     float $$4 = CropBlock.getGrowthSpeed(this, (BlockGetter)$$1, $$2);
/* 142 */     boolean $$5 = ($$3.nextInt((int)(25.0F / $$4) + 1) == 0);
/*     */     
/* 144 */     if ($$5) {
/* 145 */       grow($$1, $$0, $$2, 1);
/*     */     }
/*     */   }
/*     */   
/*     */   private void grow(ServerLevel $$0, BlockState $$1, BlockPos $$2, int $$3) {
/* 150 */     int $$4 = Math.min(((Integer)$$1.getValue((Property)AGE)).intValue() + $$3, 4);
/* 151 */     if (!canGrow((LevelReader)$$0, $$2, $$1, $$4)) {
/*     */       return;
/*     */     }
/*     */     
/* 155 */     BlockState $$5 = (BlockState)$$1.setValue((Property)AGE, Integer.valueOf($$4));
/* 156 */     $$0.setBlock($$2, $$5, 2);
/*     */     
/* 158 */     if (isDouble($$4)) {
/* 159 */       $$0.setBlock($$2.above(), (BlockState)$$5.setValue((Property)HALF, (Comparable)DoubleBlockHalf.UPPER), 3);
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean canGrowInto(LevelReader $$0, BlockPos $$1) {
/* 164 */     BlockState $$2 = $$0.getBlockState($$1);
/* 165 */     return ($$2.isAir() || $$2.is(Blocks.PITCHER_CROP));
/*     */   }
/*     */   
/*     */   private static boolean sufficientLight(LevelReader $$0, BlockPos $$1) {
/* 169 */     return CropBlock.hasSufficientLight($$0, $$1);
/*     */   }
/*     */   
/*     */   private static boolean isLower(BlockState $$0) {
/* 173 */     return ($$0.is(Blocks.PITCHER_CROP) && $$0.getValue((Property)HALF) == DoubleBlockHalf.LOWER);
/*     */   }
/*     */   
/*     */   private static boolean isDouble(int $$0) {
/* 177 */     return ($$0 >= 3);
/*     */   }
/*     */   
/*     */   private boolean canGrow(LevelReader $$0, BlockPos $$1, BlockState $$2, int $$3) {
/* 181 */     return (!isMaxAge($$2) && sufficientLight($$0, $$1) && (!isDouble($$3) || canGrowInto($$0, $$1.above())));
/*     */   }
/*     */   
/*     */   private boolean isMaxAge(BlockState $$0) {
/* 185 */     return (((Integer)$$0.getValue((Property)AGE)).intValue() >= 4);
/*     */   }
/*     */   private static final class PosAndState extends Record { final BlockPos pos; final BlockState state;
/* 188 */     PosAndState(BlockPos $$0, BlockState $$1) { this.pos = $$0; this.state = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/block/PitcherCropBlock$PosAndState;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #188	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 188 */       //   0	7	0	this	Lnet/minecraft/world/level/block/PitcherCropBlock$PosAndState; } public BlockPos pos() { return this.pos; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/block/PitcherCropBlock$PosAndState;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #188	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 188 */       //   0	7	0	this	Lnet/minecraft/world/level/block/PitcherCropBlock$PosAndState; } public BlockState state() { return this.state; } public final boolean equals(Object $$0) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/block/PitcherCropBlock$PosAndState;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #188	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/block/PitcherCropBlock$PosAndState;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */     } } @Nullable
/*     */   private PosAndState getLowerHalf(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 192 */     if (isLower($$2)) {
/* 193 */       return new PosAndState($$1, $$2);
/*     */     }
/* 195 */     BlockPos $$3 = $$1.below();
/* 196 */     BlockState $$4 = $$0.getBlockState($$3);
/* 197 */     if (isLower($$4)) {
/* 198 */       return new PosAndState($$3, $$4);
/*     */     }
/* 200 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 205 */     PosAndState $$3 = getLowerHalf($$0, $$1, $$2);
/* 206 */     if ($$3 == null) {
/* 207 */       return false;
/*     */     }
/* 209 */     return canGrow($$0, $$3.pos, $$3.state, ((Integer)$$3.state.getValue((Property)AGE)).intValue() + 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 214 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 219 */     PosAndState $$4 = getLowerHalf((LevelReader)$$0, $$2, $$3);
/* 220 */     if ($$4 == null) {
/*     */       return;
/*     */     }
/* 223 */     grow($$0, $$4.state, $$4.pos, 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\PitcherCropBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */