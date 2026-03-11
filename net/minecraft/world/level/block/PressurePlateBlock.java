/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.BlockSetType;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class PressurePlateBlock extends BasePressurePlateBlock {
/*    */   static {
/* 17 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)BlockSetType.CODEC.fieldOf("block_set_type").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, PressurePlateBlock::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final MapCodec<PressurePlateBlock> CODEC;
/*    */   
/*    */   public MapCodec<PressurePlateBlock> codec() {
/* 24 */     return CODEC;
/*    */   }
/*    */   
/* 27 */   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
/*    */   
/*    */   protected PressurePlateBlock(BlockSetType $$0, BlockBehaviour.Properties $$1) {
/* 30 */     super($$1, $$0);
/* 31 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)POWERED, Boolean.valueOf(false)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getSignalForState(BlockState $$0) {
/* 36 */     return ((Boolean)$$0.getValue((Property)POWERED)).booleanValue() ? 15 : 0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockState setSignalForState(BlockState $$0, int $$1) {
/* 41 */     return (BlockState)$$0.setValue((Property)POWERED, Boolean.valueOf(($$1 > 0)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getSignalStrength(Level $$0, BlockPos $$1) {
/* 46 */     switch (this.type.pressurePlateSensitivity()) { default: throw new IncompatibleClassChangeError();
/*    */       case EVERYTHING: 
/* 48 */       case MOBS: break; }  Class<LivingEntity> clazz = LivingEntity.class;
/*    */     
/* 50 */     return (getEntityCount($$0, TOUCH_AABB.move($$1), (Class)clazz) > 0) ? 15 : 0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 55 */     $$0.add(new Property[] { (Property)POWERED });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\PressurePlateBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */