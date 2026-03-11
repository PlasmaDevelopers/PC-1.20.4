/*    */ package net.minecraft.world.level.levelgen.flat;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.dimension.DimensionType;
/*    */ 
/*    */ public class FlatLayerInfo {
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.intRange(0, DimensionType.Y_SIZE).fieldOf("height").forGetter(FlatLayerInfo::getHeight), (App)BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").orElse(Blocks.AIR).forGetter(())).apply((Applicative)$$0, FlatLayerInfo::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<FlatLayerInfo> CODEC;
/*    */   private final Block block;
/*    */   private final int height;
/*    */   
/*    */   public FlatLayerInfo(int $$0, Block $$1) {
/* 21 */     this.height = $$0;
/* 22 */     this.block = $$1;
/*    */   }
/*    */   
/*    */   public int getHeight() {
/* 26 */     return this.height;
/*    */   }
/*    */   
/*    */   public BlockState getBlockState() {
/* 30 */     return this.block.defaultBlockState();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 34 */     return ((this.height != 1) ? ("" + this.height + "*") : "") + ((this.height != 1) ? ("" + this.height + "*") : "");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\flat\FlatLayerInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */