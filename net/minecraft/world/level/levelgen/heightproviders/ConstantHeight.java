/*    */ package net.minecraft.world.level.levelgen.heightproviders;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.levelgen.VerticalAnchor;
/*    */ import net.minecraft.world.level.levelgen.WorldGenerationContext;
/*    */ 
/*    */ public class ConstantHeight extends HeightProvider {
/* 10 */   public static final ConstantHeight ZERO = new ConstantHeight(VerticalAnchor.absolute(0));
/*    */   
/* 12 */   public static final Codec<ConstantHeight> CODEC = ExtraCodecs.withAlternative(VerticalAnchor.CODEC, VerticalAnchor.CODEC
/*    */       
/* 14 */       .fieldOf("value").codec())
/* 15 */     .xmap(ConstantHeight::new, ConstantHeight::getValue);
/*    */ 
/*    */   
/*    */   private final VerticalAnchor value;
/*    */ 
/*    */ 
/*    */   
/*    */   public static ConstantHeight of(VerticalAnchor $$0) {
/* 23 */     return new ConstantHeight($$0);
/*    */   }
/*    */   
/*    */   private ConstantHeight(VerticalAnchor $$0) {
/* 27 */     this.value = $$0;
/*    */   }
/*    */   
/*    */   public VerticalAnchor getValue() {
/* 31 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int sample(RandomSource $$0, WorldGenerationContext $$1) {
/* 36 */     return this.value.resolveY($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public HeightProviderType<?> getType() {
/* 41 */     return HeightProviderType.CONSTANT;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 46 */     return this.value.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\heightproviders\ConstantHeight.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */