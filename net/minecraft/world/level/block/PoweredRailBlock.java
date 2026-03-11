/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.RailShape;
/*     */ 
/*     */ public class PoweredRailBlock extends BaseRailBlock {
/*  15 */   public static final MapCodec<PoweredRailBlock> CODEC = simpleCodec(PoweredRailBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<PoweredRailBlock> codec() {
/*  19 */     return CODEC;
/*     */   }
/*     */   
/*  22 */   public static final EnumProperty<RailShape> SHAPE = BlockStateProperties.RAIL_SHAPE_STRAIGHT;
/*  23 */   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
/*     */   
/*     */   protected PoweredRailBlock(BlockBehaviour.Properties $$0) {
/*  26 */     super(true, $$0);
/*  27 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)SHAPE, (Comparable)RailShape.NORTH_SOUTH)).setValue((Property)POWERED, Boolean.valueOf(false))).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*     */   }
/*     */   
/*     */   protected boolean findPoweredRailSignal(Level $$0, BlockPos $$1, BlockState $$2, boolean $$3, int $$4) {
/*  31 */     if ($$4 >= 8) {
/*  32 */       return false;
/*     */     }
/*     */     
/*  35 */     int $$5 = $$1.getX();
/*  36 */     int $$6 = $$1.getY();
/*  37 */     int $$7 = $$1.getZ();
/*     */     
/*  39 */     boolean $$8 = true;
/*  40 */     RailShape $$9 = (RailShape)$$2.getValue((Property)SHAPE);
/*  41 */     switch ($$9) {
/*     */       case LEFT_RIGHT:
/*  43 */         if ($$3) {
/*  44 */           $$7++; break;
/*     */         } 
/*  46 */         $$7--;
/*     */         break;
/*     */       
/*     */       case FRONT_BACK:
/*  50 */         if ($$3) {
/*  51 */           $$5--; break;
/*     */         } 
/*  53 */         $$5++;
/*     */         break;
/*     */       
/*     */       case null:
/*  57 */         if ($$3) {
/*  58 */           $$5--;
/*     */         } else {
/*  60 */           $$5++;
/*  61 */           $$6++;
/*  62 */           $$8 = false;
/*     */         } 
/*  64 */         $$9 = RailShape.EAST_WEST;
/*     */         break;
/*     */       case null:
/*  67 */         if ($$3) {
/*  68 */           $$5--;
/*  69 */           $$6++;
/*  70 */           $$8 = false;
/*     */         } else {
/*  72 */           $$5++;
/*     */         } 
/*  74 */         $$9 = RailShape.EAST_WEST;
/*     */         break;
/*     */       case null:
/*  77 */         if ($$3) {
/*  78 */           $$7++;
/*     */         } else {
/*  80 */           $$7--;
/*  81 */           $$6++;
/*  82 */           $$8 = false;
/*     */         } 
/*  84 */         $$9 = RailShape.NORTH_SOUTH;
/*     */         break;
/*     */       case null:
/*  87 */         if ($$3) {
/*  88 */           $$7++;
/*  89 */           $$6++;
/*  90 */           $$8 = false;
/*     */         } else {
/*  92 */           $$7--;
/*     */         } 
/*  94 */         $$9 = RailShape.NORTH_SOUTH;
/*     */         break;
/*     */     } 
/*     */     
/*  98 */     if (isSameRailWithPower($$0, new BlockPos($$5, $$6, $$7), $$3, $$4, $$9)) {
/*  99 */       return true;
/*     */     }
/* 101 */     if ($$8 && isSameRailWithPower($$0, new BlockPos($$5, $$6 - 1, $$7), $$3, $$4, $$9)) {
/* 102 */       return true;
/*     */     }
/* 104 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean isSameRailWithPower(Level $$0, BlockPos $$1, boolean $$2, int $$3, RailShape $$4) {
/* 108 */     BlockState $$5 = $$0.getBlockState($$1);
/*     */     
/* 110 */     if (!$$5.is(this)) {
/* 111 */       return false;
/*     */     }
/*     */     
/* 114 */     RailShape $$6 = (RailShape)$$5.getValue((Property)SHAPE);
/* 115 */     if ($$4 == RailShape.EAST_WEST && ($$6 == RailShape.NORTH_SOUTH || $$6 == RailShape.ASCENDING_NORTH || $$6 == RailShape.ASCENDING_SOUTH)) {
/* 116 */       return false;
/*     */     }
/* 118 */     if ($$4 == RailShape.NORTH_SOUTH && ($$6 == RailShape.EAST_WEST || $$6 == RailShape.ASCENDING_EAST || $$6 == RailShape.ASCENDING_WEST)) {
/* 119 */       return false;
/*     */     }
/*     */     
/* 122 */     if (((Boolean)$$5.getValue((Property)POWERED)).booleanValue()) {
/* 123 */       if ($$0.hasNeighborSignal($$1)) {
/* 124 */         return true;
/*     */       }
/* 126 */       return findPoweredRailSignal($$0, $$1, $$5, $$2, $$3 + 1);
/*     */     } 
/*     */     
/* 129 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateState(BlockState $$0, Level $$1, BlockPos $$2, Block $$3) {
/* 134 */     boolean $$4 = ((Boolean)$$0.getValue((Property)POWERED)).booleanValue();
/* 135 */     boolean $$5 = ($$1.hasNeighborSignal($$2) || findPoweredRailSignal($$1, $$2, $$0, true, 0) || findPoweredRailSignal($$1, $$2, $$0, false, 0));
/*     */     
/* 137 */     if ($$5 != $$4) {
/* 138 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)POWERED, Boolean.valueOf($$5)), 3);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 143 */       $$1.updateNeighborsAt($$2.below(), this);
/* 144 */       if (((RailShape)$$0.getValue((Property)SHAPE)).isAscending()) {
/* 145 */         $$1.updateNeighborsAt($$2.above(), this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Property<RailShape> getShapeProperty() {
/* 152 */     return (Property<RailShape>)SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 157 */     switch ($$1) {
/*     */       case LEFT_RIGHT:
/* 159 */         switch ((RailShape)$$0.getValue((Property)SHAPE)) {
/*     */           case null:
/* 161 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_WEST);
/*     */           case null:
/* 163 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_EAST);
/*     */           case null:
/* 165 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_SOUTH);
/*     */           case null:
/* 167 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_NORTH);
/*     */           case null:
/* 169 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_WEST);
/*     */           case null:
/* 171 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_EAST);
/*     */           case null:
/* 173 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_EAST);
/*     */           case null:
/* 175 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_WEST);
/*     */         } 
/*     */       case FRONT_BACK:
/* 178 */         switch ((RailShape)$$0.getValue((Property)SHAPE)) {
/*     */           case LEFT_RIGHT:
/* 180 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.EAST_WEST);
/*     */           case FRONT_BACK:
/* 182 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_SOUTH);
/*     */           case null:
/* 184 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_NORTH);
/*     */           case null:
/* 186 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_SOUTH);
/*     */           case null:
/* 188 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_WEST);
/*     */           case null:
/* 190 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_EAST);
/*     */           case null:
/* 192 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_EAST);
/*     */           case null:
/* 194 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_EAST);
/*     */           case null:
/* 196 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_WEST);
/*     */           case null:
/* 198 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_WEST);
/*     */         } 
/*     */       case null:
/* 201 */         switch ((RailShape)$$0.getValue((Property)SHAPE)) {
/*     */           case LEFT_RIGHT:
/* 203 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.EAST_WEST);
/*     */           case FRONT_BACK:
/* 205 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_SOUTH);
/*     */           case null:
/* 207 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_SOUTH);
/*     */           case null:
/* 209 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_NORTH);
/*     */           case null:
/* 211 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_EAST);
/*     */           case null:
/* 213 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_WEST);
/*     */           case null:
/* 215 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_WEST);
/*     */           case null:
/* 217 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_WEST);
/*     */           case null:
/* 219 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_EAST);
/*     */           case null:
/* 221 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_EAST);
/*     */         }  break;
/*     */     } 
/* 224 */     return $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 230 */     RailShape $$2 = (RailShape)$$0.getValue((Property)SHAPE);
/* 231 */     switch ($$1) {
/*     */       case LEFT_RIGHT:
/* 233 */         switch ($$2) {
/*     */           case null:
/* 235 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_SOUTH);
/*     */           case null:
/* 237 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_NORTH);
/*     */           case null:
/* 239 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_EAST);
/*     */           case null:
/* 241 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_WEST);
/*     */           case null:
/* 243 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_WEST);
/*     */           case null:
/* 245 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_EAST);
/*     */         } 
/*     */         
/*     */         break;
/*     */       
/*     */       case FRONT_BACK:
/* 251 */         switch ($$2) {
/*     */           case null:
/* 253 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_WEST);
/*     */           case null:
/* 255 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_EAST);
/*     */           case null:
/* 257 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_WEST);
/*     */           case null:
/* 259 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_EAST);
/*     */           case null:
/* 261 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_EAST);
/*     */           case null:
/* 263 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_WEST);
/*     */         } 
/*     */ 
/*     */         
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 271 */     return super.mirror($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 276 */     $$0.add(new Property[] { (Property)SHAPE, (Property)POWERED, (Property)WATERLOGGED });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\PoweredRailBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */