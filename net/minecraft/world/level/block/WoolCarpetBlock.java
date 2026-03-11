/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.world.item.DyeColor;
/*    */ 
/*    */ public class WoolCarpetBlock extends CarpetBlock {
/*    */   static {
/*  8 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)DyeColor.CODEC.fieldOf("color").forGetter(WoolCarpetBlock::getColor), (App)propertiesCodec()).apply((Applicative)$$0, WoolCarpetBlock::new));
/*    */   }
/*    */   
/*    */   public static final MapCodec<WoolCarpetBlock> CODEC;
/*    */   private final DyeColor color;
/*    */   
/*    */   public MapCodec<WoolCarpetBlock> codec() {
/* 15 */     return CODEC;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected WoolCarpetBlock(DyeColor $$0, BlockBehaviour.Properties $$1) {
/* 21 */     super($$1);
/* 22 */     this.color = $$0;
/*    */   }
/*    */   
/*    */   public DyeColor getColor() {
/* 26 */     return this.color;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WoolCarpetBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */