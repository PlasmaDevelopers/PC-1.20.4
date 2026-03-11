/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.world.item.DyeColor;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class StainedGlassPaneBlock extends IronBarsBlock implements BeaconBeamBlock {
/*    */   static {
/*  8 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)DyeColor.CODEC.fieldOf("color").forGetter(StainedGlassPaneBlock::getColor), (App)propertiesCodec()).apply((Applicative)$$0, StainedGlassPaneBlock::new));
/*    */   }
/*    */   
/*    */   public static final MapCodec<StainedGlassPaneBlock> CODEC;
/*    */   private final DyeColor color;
/*    */   
/*    */   public MapCodec<StainedGlassPaneBlock> codec() {
/* 15 */     return CODEC;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public StainedGlassPaneBlock(DyeColor $$0, BlockBehaviour.Properties $$1) {
/* 21 */     super($$1);
/* 22 */     this.color = $$0;
/* 23 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)NORTH, Boolean.valueOf(false))).setValue((Property)EAST, Boolean.valueOf(false))).setValue((Property)SOUTH, Boolean.valueOf(false))).setValue((Property)WEST, Boolean.valueOf(false))).setValue((Property)WATERLOGGED, Boolean.valueOf(false)));
/*    */   }
/*    */ 
/*    */   
/*    */   public DyeColor getColor() {
/* 28 */     return this.color;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\StainedGlassPaneBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */