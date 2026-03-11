/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.function.BiFunction;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.projectile.Projectile;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class ChorusFlowerBlock extends Block {
/*     */   static {
/*  26 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)BuiltInRegistries.BLOCK.byNameCodec().fieldOf("plant").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, ChorusFlowerBlock::new));
/*     */   }
/*     */   
/*     */   public static final MapCodec<ChorusFlowerBlock> CODEC;
/*     */   public static final int DEAD_AGE = 5;
/*     */   
/*     */   public MapCodec<ChorusFlowerBlock> codec() {
/*  33 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*  37 */   public static final IntegerProperty AGE = BlockStateProperties.AGE_5;
/*  38 */   protected static final VoxelShape BLOCK_SUPPORT_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);
/*     */   private final Block plant;
/*     */   
/*     */   protected ChorusFlowerBlock(Block $$0, BlockBehaviour.Properties $$1) {
/*  42 */     super($$1);
/*  43 */     this.plant = $$0;
/*  44 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)AGE, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  49 */     if (!$$0.canSurvive((LevelReader)$$1, $$2)) {
/*  50 */       $$1.destroyBlock($$2, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRandomlyTicking(BlockState $$0) {
/*  56 */     return (((Integer)$$0.getValue((Property)AGE)).intValue() < 5);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getBlockSupportShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  61 */     return BLOCK_SUPPORT_SHAPE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  67 */     BlockPos $$4 = $$2.above();
/*  68 */     if (!$$1.isEmptyBlock($$4) || $$4.getY() >= $$1.getMaxBuildHeight()) {
/*     */       return;
/*     */     }
/*     */     
/*  72 */     int $$5 = ((Integer)$$0.getValue((Property)AGE)).intValue();
/*  73 */     if ($$5 >= 5) {
/*     */       return;
/*     */     }
/*     */     
/*  77 */     boolean $$6 = false;
/*  78 */     boolean $$7 = false;
/*     */     
/*  80 */     BlockState $$8 = $$1.getBlockState($$2.below());
/*  81 */     if ($$8.is(Blocks.END_STONE)) {
/*  82 */       $$6 = true;
/*  83 */     } else if ($$8.is(this.plant)) {
/*  84 */       int $$9 = 1;
/*  85 */       for (int $$10 = 0; $$10 < 4; $$10++) {
/*  86 */         BlockState $$11 = $$1.getBlockState($$2.below($$9 + 1));
/*  87 */         if ($$11.is(this.plant)) {
/*  88 */           $$9++;
/*     */         } else {
/*  90 */           if ($$11.is(Blocks.END_STONE)) {
/*  91 */             $$7 = true;
/*     */           }
/*     */           break;
/*     */         } 
/*     */       } 
/*  96 */       if ($$9 < 2 || $$9 <= $$3.nextInt($$7 ? 5 : 4)) {
/*  97 */         $$6 = true;
/*     */       }
/*  99 */     } else if ($$8.isAir()) {
/* 100 */       $$6 = true;
/*     */     } 
/*     */     
/* 103 */     if ($$6 && allNeighborsEmpty((LevelReader)$$1, $$4, (Direction)null) && $$1.isEmptyBlock($$2.above(2))) {
/* 104 */       $$1.setBlock($$2, ChorusPlantBlock.getStateWithConnections((BlockGetter)$$1, $$2, this.plant.defaultBlockState()), 2);
/* 105 */       placeGrownFlower((Level)$$1, $$4, $$5);
/* 106 */     } else if ($$5 < 4) {
/* 107 */       int $$12 = $$3.nextInt(4);
/* 108 */       if ($$7) {
/* 109 */         $$12++;
/*     */       }
/*     */       
/* 112 */       boolean $$13 = false;
/* 113 */       for (int $$14 = 0; $$14 < $$12; $$14++) {
/* 114 */         Direction $$15 = Direction.Plane.HORIZONTAL.getRandomDirection($$3);
/* 115 */         BlockPos $$16 = $$2.relative($$15);
/* 116 */         if ($$1.isEmptyBlock($$16) && $$1.isEmptyBlock($$16.below()) && allNeighborsEmpty((LevelReader)$$1, $$16, $$15.getOpposite())) {
/* 117 */           placeGrownFlower((Level)$$1, $$16, $$5 + 1);
/* 118 */           $$13 = true;
/*     */         } 
/*     */       } 
/*     */       
/* 122 */       if ($$13) {
/* 123 */         $$1.setBlock($$2, ChorusPlantBlock.getStateWithConnections((BlockGetter)$$1, $$2, this.plant.defaultBlockState()), 2);
/*     */       } else {
/* 125 */         placeDeadFlower((Level)$$1, $$2);
/*     */       } 
/*     */     } else {
/* 128 */       placeDeadFlower((Level)$$1, $$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void placeGrownFlower(Level $$0, BlockPos $$1, int $$2) {
/* 133 */     $$0.setBlock($$1, (BlockState)defaultBlockState().setValue((Property)AGE, Integer.valueOf($$2)), 2);
/* 134 */     $$0.levelEvent(1033, $$1, 0);
/*     */   }
/*     */   
/*     */   private void placeDeadFlower(Level $$0, BlockPos $$1) {
/* 138 */     $$0.setBlock($$1, (BlockState)defaultBlockState().setValue((Property)AGE, Integer.valueOf(5)), 2);
/* 139 */     $$0.levelEvent(1034, $$1, 0);
/*     */   }
/*     */   
/*     */   private static boolean allNeighborsEmpty(LevelReader $$0, BlockPos $$1, @Nullable Direction $$2) {
/* 143 */     for (Direction $$3 : Direction.Plane.HORIZONTAL) {
/* 144 */       if ($$3 != $$2 && !$$0.isEmptyBlock($$1.relative($$3))) {
/* 145 */         return false;
/*     */       }
/*     */     } 
/* 148 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 153 */     if ($$1 != Direction.UP && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/* 154 */       $$3.scheduleTick($$4, this, 1);
/*     */     }
/*     */     
/* 157 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 162 */     BlockState $$3 = $$1.getBlockState($$2.below());
/* 163 */     if ($$3.is(this.plant) || $$3.is(Blocks.END_STONE)) {
/* 164 */       return true;
/*     */     }
/* 166 */     if (!$$3.isAir()) {
/* 167 */       return false;
/*     */     }
/*     */     
/* 170 */     boolean $$4 = false;
/* 171 */     for (Direction $$5 : Direction.Plane.HORIZONTAL) {
/* 172 */       BlockState $$6 = $$1.getBlockState($$2.relative($$5));
/* 173 */       if ($$6.is(this.plant)) {
/* 174 */         if ($$4) {
/* 175 */           return false;
/*     */         }
/* 177 */         $$4 = true; continue;
/* 178 */       }  if (!$$6.isAir()) {
/* 179 */         return false;
/*     */       }
/*     */     } 
/* 182 */     return $$4;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 187 */     $$0.add(new Property[] { (Property)AGE });
/*     */   }
/*     */   
/*     */   public static void generatePlant(LevelAccessor $$0, BlockPos $$1, RandomSource $$2, int $$3) {
/* 191 */     $$0.setBlock($$1, ChorusPlantBlock.getStateWithConnections((BlockGetter)$$0, $$1, Blocks.CHORUS_PLANT.defaultBlockState()), 2);
/* 192 */     growTreeRecursive($$0, $$1, $$2, $$1, $$3, 0);
/*     */   }
/*     */   
/*     */   private static void growTreeRecursive(LevelAccessor $$0, BlockPos $$1, RandomSource $$2, BlockPos $$3, int $$4, int $$5) {
/* 196 */     Block $$6 = Blocks.CHORUS_PLANT;
/*     */     
/* 198 */     int $$7 = $$2.nextInt(4) + 1;
/* 199 */     if ($$5 == 0) {
/* 200 */       $$7++;
/*     */     }
/*     */     
/* 203 */     for (int $$8 = 0; $$8 < $$7; $$8++) {
/* 204 */       BlockPos $$9 = $$1.above($$8 + 1);
/* 205 */       if (!allNeighborsEmpty((LevelReader)$$0, $$9, (Direction)null)) {
/*     */         return;
/*     */       }
/*     */       
/* 209 */       $$0.setBlock($$9, ChorusPlantBlock.getStateWithConnections((BlockGetter)$$0, $$9, $$6.defaultBlockState()), 2);
/* 210 */       $$0.setBlock($$9.below(), ChorusPlantBlock.getStateWithConnections((BlockGetter)$$0, $$9.below(), $$6.defaultBlockState()), 2);
/*     */     } 
/*     */     
/* 213 */     boolean $$10 = false;
/* 214 */     if ($$5 < 4) {
/* 215 */       int $$11 = $$2.nextInt(4);
/* 216 */       if ($$5 == 0) {
/* 217 */         $$11++;
/*     */       }
/* 219 */       for (int $$12 = 0; $$12 < $$11; $$12++) {
/* 220 */         Direction $$13 = Direction.Plane.HORIZONTAL.getRandomDirection($$2);
/* 221 */         BlockPos $$14 = $$1.above($$7).relative($$13);
/* 222 */         if (Math.abs($$14.getX() - $$3.getX()) < $$4 && Math.abs($$14.getZ() - $$3.getZ()) < $$4)
/*     */         {
/*     */           
/* 225 */           if ($$0.isEmptyBlock($$14) && $$0.isEmptyBlock($$14.below()) && allNeighborsEmpty((LevelReader)$$0, $$14, $$13.getOpposite())) {
/* 226 */             $$10 = true;
/* 227 */             $$0.setBlock($$14, ChorusPlantBlock.getStateWithConnections((BlockGetter)$$0, $$14, $$6.defaultBlockState()), 2);
/* 228 */             $$0.setBlock($$14.relative($$13.getOpposite()), ChorusPlantBlock.getStateWithConnections((BlockGetter)$$0, $$14.relative($$13.getOpposite()), $$6.defaultBlockState()), 2);
/* 229 */             growTreeRecursive($$0, $$14, $$2, $$3, $$4, $$5 + 1);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 234 */     if (!$$10) {
/* 235 */       $$0.setBlock($$1.above($$7), (BlockState)Blocks.CHORUS_FLOWER.defaultBlockState().setValue((Property)AGE, Integer.valueOf(5)), 2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onProjectileHit(Level $$0, BlockState $$1, BlockHitResult $$2, Projectile $$3) {
/* 241 */     BlockPos $$4 = $$2.getBlockPos();
/* 242 */     if (!$$0.isClientSide && $$3.mayInteract($$0, $$4) && $$3.mayBreak($$0))
/* 243 */       $$0.destroyBlock($$4, true, (Entity)$$3); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\ChorusFlowerBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */