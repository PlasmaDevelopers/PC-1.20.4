/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.util.ColorRGBA;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ 
/*    */ public class ColoredFallingBlock extends FallingBlock {
/*    */   static {
/* 11 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)ColorRGBA.CODEC.fieldOf("falling_dust_color").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, ColoredFallingBlock::new));
/*    */   }
/*    */   
/*    */   public static final MapCodec<ColoredFallingBlock> CODEC;
/*    */   private final ColorRGBA dustColor;
/*    */   
/*    */   public MapCodec<ColoredFallingBlock> codec() {
/* 18 */     return CODEC;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ColoredFallingBlock(ColorRGBA $$0, BlockBehaviour.Properties $$1) {
/* 24 */     super($$1);
/* 25 */     this.dustColor = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDustColor(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 30 */     return this.dustColor.rgba();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\ColoredFallingBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */