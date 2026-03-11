/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.RailShape;
/*     */ 
/*     */ public class RailBlock extends BaseRailBlock {
/*  14 */   public static final MapCodec<RailBlock> CODEC = simpleCodec(RailBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<RailBlock> codec() {
/*  18 */     return CODEC;
/*     */   }
/*     */   
/*  21 */   public static final EnumProperty<RailShape> SHAPE = BlockStateProperties.RAIL_SHAPE;
/*     */   
/*     */   protected RailBlock(BlockBehaviour.Properties $$0) {
/*  24 */     super(false, $$0);
/*  25 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)SHAPE, (Comparable)RailShape.NORTH_SOUTH)).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateState(BlockState $$0, Level $$1, BlockPos $$2, Block $$3) {
/*  30 */     if ($$3.defaultBlockState().isSignalSource() && (
/*  31 */       new RailState($$1, $$2, $$0)).countPotentialConnections() == 3) {
/*  32 */       updateDir($$1, $$2, $$0, false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Property<RailShape> getShapeProperty() {
/*  39 */     return (Property<RailShape>)SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/*  44 */     RailShape $$2 = (RailShape)$$0.getValue((Property)SHAPE);
/*  45 */     switch ($$1) { case LEFT_RIGHT:
/*  46 */         switch ($$2) { default: throw new IncompatibleClassChangeError();
/*     */           case LEFT_RIGHT: 
/*     */           case FRONT_BACK: 
/*     */           case null: 
/*     */           case null: 
/*     */           case null: 
/*     */           case null: 
/*     */           case null: 
/*     */           case null: 
/*     */           case null: 
/*     */           case null: break; } 
/*     */       case FRONT_BACK:
/*  58 */         switch ($$2) { default: throw new IncompatibleClassChangeError();
/*     */           case LEFT_RIGHT: 
/*     */           case FRONT_BACK: 
/*     */           case null: 
/*     */           case null: 
/*     */           case null: 
/*     */           case null: 
/*     */           case null: 
/*     */           case null: 
/*     */           case null: 
/*     */           case null: break; } 
/*     */       case null:
/*  70 */         switch ($$2) { default: throw new IncompatibleClassChangeError();
/*     */           case LEFT_RIGHT: 
/*     */           case FRONT_BACK: 
/*     */           case null: 
/*     */           case null: 
/*     */           case null: 
/*     */           case null: 
/*     */           case null: 
/*     */           case null: 
/*     */           case null: 
/*  80 */           case null: break; }  }  return (BlockState)SHAPE.setValue((Property)RailShape.SOUTH_EAST, 
/*     */         
/*  82 */         (Comparable)$$2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/*  88 */     RailShape $$2 = (RailShape)$$0.getValue((Property)SHAPE);
/*  89 */     switch ($$1) {
/*     */       case LEFT_RIGHT:
/*  91 */         switch ($$2) {
/*     */           case null:
/*  93 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_SOUTH);
/*     */           case null:
/*  95 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_NORTH);
/*     */           case null:
/*  97 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_EAST);
/*     */           case null:
/*  99 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_WEST);
/*     */           case null:
/* 101 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_WEST);
/*     */           case null:
/* 103 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_EAST);
/*     */         } 
/*     */         
/*     */         break;
/*     */       
/*     */       case FRONT_BACK:
/* 109 */         switch ($$2) {
/*     */           case null:
/* 111 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_WEST);
/*     */           case null:
/* 113 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.ASCENDING_EAST);
/*     */           case null:
/* 115 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_WEST);
/*     */           case null:
/* 117 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.SOUTH_EAST);
/*     */           case null:
/* 119 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_EAST);
/*     */           case null:
/* 121 */             return (BlockState)$$0.setValue((Property)SHAPE, (Comparable)RailShape.NORTH_WEST);
/*     */         } 
/*     */ 
/*     */         
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 129 */     return super.mirror($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 134 */     $$0.add(new Property[] { (Property)SHAPE, (Property)WATERLOGGED });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\RailBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */