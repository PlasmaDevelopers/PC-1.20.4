/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.BlockSetType;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class WeightedPressurePlateBlock extends BasePressurePlateBlock {
/*    */   static {
/* 19 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.intRange(1, 1024).fieldOf("max_weight").forGetter(()), (App)BlockSetType.CODEC.fieldOf("block_set_type").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, WeightedPressurePlateBlock::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final MapCodec<WeightedPressurePlateBlock> CODEC;
/*    */ 
/*    */   
/*    */   public MapCodec<WeightedPressurePlateBlock> codec() {
/* 27 */     return CODEC;
/*    */   }
/*    */   
/* 30 */   public static final IntegerProperty POWER = BlockStateProperties.POWER;
/*    */   
/*    */   private final int maxWeight;
/*    */   
/*    */   protected WeightedPressurePlateBlock(int $$0, BlockSetType $$1, BlockBehaviour.Properties $$2) {
/* 35 */     super($$2, $$1);
/* 36 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)POWER, Integer.valueOf(0)));
/* 37 */     this.maxWeight = $$0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getSignalStrength(Level $$0, BlockPos $$1) {
/* 43 */     int $$2 = Math.min(getEntityCount($$0, TOUCH_AABB.move($$1), Entity.class), this.maxWeight);
/* 44 */     if ($$2 > 0) {
/* 45 */       float $$3 = Math.min(this.maxWeight, $$2) / this.maxWeight;
/* 46 */       return Mth.ceil($$3 * 15.0F);
/*    */     } 
/*    */     
/* 49 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getSignalForState(BlockState $$0) {
/* 54 */     return ((Integer)$$0.getValue((Property)POWER)).intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockState setSignalForState(BlockState $$0, int $$1) {
/* 59 */     return (BlockState)$$0.setValue((Property)POWER, Integer.valueOf($$1));
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getPressedTime() {
/* 64 */     return 10;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 69 */     $$0.add(new Property[] { (Property)POWER });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WeightedPressurePlateBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */