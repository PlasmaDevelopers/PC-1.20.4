/*    */ package net.minecraft.world.level.levelgen;
/*    */ 
/*    */ import com.google.common.annotations.VisibleForTesting;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface PositionalRandomFactory
/*    */ {
/*    */   default RandomSource at(BlockPos $$0) {
/* 20 */     return at($$0.getX(), $$0.getY(), $$0.getZ());
/*    */   }
/*    */   
/*    */   default RandomSource fromHashOf(ResourceLocation $$0) {
/* 24 */     return fromHashOf($$0.toString());
/*    */   }
/*    */   
/*    */   RandomSource fromHashOf(String paramString);
/*    */   
/*    */   RandomSource at(int paramInt1, int paramInt2, int paramInt3);
/*    */   
/*    */   @VisibleForTesting
/*    */   void parityConfigString(StringBuilder paramStringBuilder);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\PositionalRandomFactory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */