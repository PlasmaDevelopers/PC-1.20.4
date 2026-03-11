/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.BufferBuilder;
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
/*    */ import java.util.SortedMap;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.resources.model.ModelBakery;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderBuffers
/*    */ {
/*    */   private final SectionBufferBuilderPack fixedBufferPack;
/*    */   private final SectionBufferBuilderPool sectionBufferPool;
/*    */   private final MultiBufferSource.BufferSource bufferSource;
/*    */   private final MultiBufferSource.BufferSource crumblingBufferSource;
/*    */   private final OutlineBufferSource outlineBufferSource;
/*    */   
/*    */   public RenderBuffers(int $$0) {
/* 20 */     this.fixedBufferPack = new SectionBufferBuilderPack();
/* 21 */     this.sectionBufferPool = SectionBufferBuilderPool.allocate($$0);
/*    */     
/* 23 */     SortedMap<RenderType, BufferBuilder> $$1 = (SortedMap<RenderType, BufferBuilder>)Util.make(new Object2ObjectLinkedOpenHashMap(), $$0 -> {
/*    */           $$0.put(Sheets.solidBlockSheet(), this.fixedBufferPack.builder(RenderType.solid()));
/*    */           
/*    */           $$0.put(Sheets.cutoutBlockSheet(), this.fixedBufferPack.builder(RenderType.cutout()));
/*    */           
/*    */           $$0.put(Sheets.bannerSheet(), this.fixedBufferPack.builder(RenderType.cutoutMipped()));
/*    */           
/*    */           $$0.put(Sheets.translucentCullBlockSheet(), this.fixedBufferPack.builder(RenderType.translucent()));
/*    */           
/*    */           put($$0, Sheets.shieldSheet());
/*    */           put($$0, Sheets.bedSheet());
/*    */           put($$0, Sheets.shulkerBoxSheet());
/*    */           put($$0, Sheets.signSheet());
/*    */           put($$0, Sheets.hangingSignSheet());
/*    */           $$0.put(Sheets.chestSheet(), new BufferBuilder(786432));
/*    */           put($$0, RenderType.armorGlint());
/*    */           put($$0, RenderType.armorEntityGlint());
/*    */           put($$0, RenderType.glint());
/*    */           put($$0, RenderType.glintDirect());
/*    */           put($$0, RenderType.glintTranslucent());
/*    */           put($$0, RenderType.entityGlint());
/*    */           put($$0, RenderType.entityGlintDirect());
/*    */           put($$0, RenderType.waterMask());
/*    */           ModelBakery.DESTROY_TYPES.forEach(());
/*    */         });
/* 48 */     this.crumblingBufferSource = MultiBufferSource.immediate(new BufferBuilder(1536));
/* 49 */     this.bufferSource = MultiBufferSource.immediateWithBuffers($$1, new BufferBuilder(786432));
/* 50 */     this.outlineBufferSource = new OutlineBufferSource(this.bufferSource);
/*    */   }
/*    */   
/*    */   private static void put(Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> $$0, RenderType $$1) {
/* 54 */     $$0.put($$1, new BufferBuilder($$1.bufferSize()));
/*    */   }
/*    */   
/*    */   public SectionBufferBuilderPack fixedBufferPack() {
/* 58 */     return this.fixedBufferPack;
/*    */   }
/*    */   
/*    */   public SectionBufferBuilderPool sectionBufferPool() {
/* 62 */     return this.sectionBufferPool;
/*    */   }
/*    */   
/*    */   public MultiBufferSource.BufferSource bufferSource() {
/* 66 */     return this.bufferSource;
/*    */   }
/*    */   
/*    */   public MultiBufferSource.BufferSource crumblingBufferSource() {
/* 70 */     return this.crumblingBufferSource;
/*    */   }
/*    */   
/*    */   public OutlineBufferSource outlineBufferSource() {
/* 74 */     return this.outlineBufferSource;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\RenderBuffers.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */