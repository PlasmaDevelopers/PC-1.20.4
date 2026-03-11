/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.IntegerProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class CropBlock extends BushBlock implements BonemealableBlock {
/*  25 */   public static final MapCodec<CropBlock> CODEC = simpleCodec(CropBlock::new);
/*     */   public static final int MAX_AGE = 7;
/*     */   
/*     */   public MapCodec<? extends CropBlock> codec() {
/*  29 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*  33 */   public static final IntegerProperty AGE = BlockStateProperties.AGE_7;
/*     */   
/*  35 */   private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[] {
/*  36 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), 
/*  37 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), 
/*  38 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), 
/*  39 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), 
/*  40 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), 
/*  41 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), 
/*  42 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), 
/*  43 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
/*     */     };
/*     */   
/*     */   protected CropBlock(BlockBehaviour.Properties $$0) {
/*  47 */     super($$0);
/*  48 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)getAgeProperty(), Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  53 */     return SHAPE_BY_AGE[getAge($$0)];
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean mayPlaceOn(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  58 */     return $$0.is(Blocks.FARMLAND);
/*     */   }
/*     */   
/*     */   protected IntegerProperty getAgeProperty() {
/*  62 */     return AGE;
/*     */   }
/*     */   
/*     */   public int getMaxAge() {
/*  66 */     return 7;
/*     */   }
/*     */   
/*     */   public int getAge(BlockState $$0) {
/*  70 */     return ((Integer)$$0.getValue((Property)getAgeProperty())).intValue();
/*     */   }
/*     */   
/*     */   public BlockState getStateForAge(int $$0) {
/*  74 */     return (BlockState)defaultBlockState().setValue((Property)getAgeProperty(), Integer.valueOf($$0));
/*     */   }
/*     */   
/*     */   public final boolean isMaxAge(BlockState $$0) {
/*  78 */     return (getAge($$0) >= getMaxAge());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRandomlyTicking(BlockState $$0) {
/*  83 */     return !isMaxAge($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  88 */     if ($$1.getRawBrightness($$2, 0) >= 9) {
/*  89 */       int $$4 = getAge($$0);
/*  90 */       if ($$4 < getMaxAge()) {
/*  91 */         float $$5 = getGrowthSpeed(this, (BlockGetter)$$1, $$2);
/*     */         
/*  93 */         if ($$3.nextInt((int)(25.0F / $$5) + 1) == 0) {
/*  94 */           $$1.setBlock($$2, getStateForAge($$4 + 1), 2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void growCrops(Level $$0, BlockPos $$1, BlockState $$2) {
/* 101 */     int $$3 = getAge($$2) + getBonemealAgeIncrease($$0);
/* 102 */     int $$4 = getMaxAge();
/* 103 */     if ($$3 > $$4) {
/* 104 */       $$3 = $$4;
/*     */     }
/* 106 */     $$0.setBlock($$1, getStateForAge($$3), 2);
/*     */   }
/*     */   
/*     */   protected int getBonemealAgeIncrease(Level $$0) {
/* 110 */     return Mth.nextInt($$0.random, 2, 5);
/*     */   }
/*     */   
/*     */   protected static float getGrowthSpeed(Block $$0, BlockGetter $$1, BlockPos $$2) {
/* 114 */     float $$3 = 1.0F;
/*     */     
/* 116 */     BlockPos $$4 = $$2.below();
/* 117 */     for (int $$5 = -1; $$5 <= 1; $$5++) {
/* 118 */       for (int $$6 = -1; $$6 <= 1; $$6++) {
/* 119 */         float $$7 = 0.0F;
/*     */         
/* 121 */         BlockState $$8 = $$1.getBlockState($$4.offset($$5, 0, $$6));
/* 122 */         if ($$8.is(Blocks.FARMLAND)) {
/* 123 */           $$7 = 1.0F;
/* 124 */           if (((Integer)$$8.getValue((Property)FarmBlock.MOISTURE)).intValue() > 0) {
/* 125 */             $$7 = 3.0F;
/*     */           }
/*     */         } 
/*     */         
/* 129 */         if ($$5 != 0 || $$6 != 0) {
/* 130 */           $$7 /= 4.0F;
/*     */         }
/*     */         
/* 133 */         $$3 += $$7;
/*     */       } 
/*     */     } 
/*     */     
/* 137 */     BlockPos $$9 = $$2.north();
/* 138 */     BlockPos $$10 = $$2.south();
/* 139 */     BlockPos $$11 = $$2.west();
/* 140 */     BlockPos $$12 = $$2.east();
/*     */     
/* 142 */     boolean $$13 = ($$1.getBlockState($$11).is($$0) || $$1.getBlockState($$12).is($$0));
/* 143 */     boolean $$14 = ($$1.getBlockState($$9).is($$0) || $$1.getBlockState($$10).is($$0));
/*     */     
/* 145 */     if ($$13 && $$14) {
/* 146 */       $$3 /= 2.0F;
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 152 */       boolean $$15 = ($$1.getBlockState($$11.north()).is($$0) || $$1.getBlockState($$12.north()).is($$0) || $$1.getBlockState($$12.south()).is($$0) || $$1.getBlockState($$11.south()).is($$0));
/*     */       
/* 154 */       if ($$15) {
/* 155 */         $$3 /= 2.0F;
/*     */       }
/*     */     } 
/*     */     
/* 159 */     return $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 164 */     return (hasSufficientLight($$1, $$2) && super.canSurvive($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   protected static boolean hasSufficientLight(LevelReader $$0, BlockPos $$1) {
/* 168 */     return ($$0.getRawBrightness($$1, 0) >= 8);
/*     */   }
/*     */ 
/*     */   
/*     */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/* 173 */     if ($$3 instanceof net.minecraft.world.entity.monster.Ravager && $$1.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
/* 174 */       $$1.destroyBlock($$2, true, $$3);
/*     */     }
/* 176 */     super.entityInside($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemLike getBaseSeedId() {
/* 181 */     return (ItemLike)Items.WHEAT_SEEDS;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 186 */     return new ItemStack(getBaseSeedId());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 191 */     return !isMaxAge($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 196 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/* 201 */     growCrops((Level)$$0, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 206 */     $$0.add(new Property[] { (Property)AGE });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CropBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */