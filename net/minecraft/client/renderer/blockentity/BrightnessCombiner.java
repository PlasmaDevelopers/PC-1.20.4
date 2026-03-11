/*    */ package net.minecraft.client.renderer.blockentity;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2IntFunction;
/*    */ import net.minecraft.client.renderer.LevelRenderer;
/*    */ import net.minecraft.client.renderer.LightTexture;
/*    */ import net.minecraft.world.level.BlockAndTintGetter;
/*    */ import net.minecraft.world.level.block.DoubleBlockCombiner;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ 
/*    */ public class BrightnessCombiner<S extends BlockEntity> implements DoubleBlockCombiner.Combiner<S, Int2IntFunction> {
/*    */   public Int2IntFunction acceptDouble(S $$0, S $$1) {
/* 12 */     return $$2 -> {
/*    */         int $$3 = LevelRenderer.getLightColor((BlockAndTintGetter)$$0.getLevel(), $$0.getBlockPos());
/*    */         int $$4 = LevelRenderer.getLightColor((BlockAndTintGetter)$$1.getLevel(), $$1.getBlockPos());
/*    */         int $$5 = LightTexture.block($$3);
/*    */         int $$6 = LightTexture.block($$4);
/*    */         int $$7 = LightTexture.sky($$3);
/*    */         int $$8 = LightTexture.sky($$4);
/*    */         return LightTexture.pack(Math.max($$5, $$6), Math.max($$7, $$8));
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Int2IntFunction acceptSingle(S $$0) {
/* 28 */     return $$0 -> $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Int2IntFunction acceptNone() {
/* 33 */     return $$0 -> $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\BrightnessCombiner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */