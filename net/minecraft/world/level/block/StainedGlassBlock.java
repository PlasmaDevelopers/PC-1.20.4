/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.world.item.DyeColor;
/*    */ 
/*    */ public class StainedGlassBlock extends TransparentBlock implements BeaconBeamBlock {
/*    */   static {
/*  8 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)DyeColor.CODEC.fieldOf("color").forGetter(StainedGlassBlock::getColor), (App)propertiesCodec()).apply((Applicative)$$0, StainedGlassBlock::new));
/*    */   }
/*    */   
/*    */   public static final MapCodec<StainedGlassBlock> CODEC;
/*    */   private final DyeColor color;
/*    */   
/*    */   public MapCodec<StainedGlassBlock> codec() {
/* 15 */     return CODEC;
/*    */   }
/*    */ 
/*    */   
/*    */   public StainedGlassBlock(DyeColor $$0, BlockBehaviour.Properties $$1) {
/* 20 */     super($$1);
/* 21 */     this.color = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public DyeColor getColor() {
/* 26 */     return this.color;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\StainedGlassBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */