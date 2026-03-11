/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class InfestedRotatedPillarBlock extends InfestedBlock {
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)BuiltInRegistries.BLOCK.byNameCodec().fieldOf("host").forGetter(InfestedBlock::getHostBlock), (App)propertiesCodec()).apply((Applicative)$$0, InfestedRotatedPillarBlock::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final MapCodec<InfestedRotatedPillarBlock> CODEC;
/*    */   
/*    */   public MapCodec<InfestedRotatedPillarBlock> codec() {
/* 19 */     return CODEC;
/*    */   }
/*    */   
/*    */   public InfestedRotatedPillarBlock(Block $$0, BlockBehaviour.Properties $$1) {
/* 23 */     super($$0, $$1);
/* 24 */     registerDefaultState((BlockState)defaultBlockState().setValue((Property)RotatedPillarBlock.AXIS, (Comparable)Direction.Axis.Y));
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 29 */     return RotatedPillarBlock.rotatePillar($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 34 */     $$0.add(new Property[] { (Property)RotatedPillarBlock.AXIS });
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 39 */     return (BlockState)defaultBlockState().setValue((Property)RotatedPillarBlock.AXIS, (Comparable)$$0.getClickedFace().getAxis());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\InfestedRotatedPillarBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */