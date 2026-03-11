/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.blaze3d.pipeline.RenderTarget;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.vertex.BufferBuilder;
/*     */ import com.mojang.blaze3d.vertex.BufferUploader;
/*     */ import com.mojang.blaze3d.vertex.DefaultVertexFormat;
/*     */ import com.mojang.blaze3d.vertex.Tesselator;
/*     */ import com.mojang.blaze3d.vertex.VertexFormat;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.IntSupplier;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import org.joml.Matrix4f;
/*     */ 
/*     */ public class PostPass
/*     */   implements AutoCloseable {
/*     */   private final EffectInstance effect;
/*     */   public final RenderTarget inTarget;
/*     */   public final RenderTarget outTarget;
/*  24 */   private final List<IntSupplier> auxAssets = Lists.newArrayList();
/*  25 */   private final List<String> auxNames = Lists.newArrayList();
/*  26 */   private final List<Integer> auxWidths = Lists.newArrayList();
/*  27 */   private final List<Integer> auxHeights = Lists.newArrayList();
/*     */   private Matrix4f shaderOrthoMatrix;
/*     */   
/*     */   public PostPass(ResourceManager $$0, String $$1, RenderTarget $$2, RenderTarget $$3) throws IOException {
/*  31 */     this.effect = new EffectInstance($$0, $$1);
/*  32 */     this.inTarget = $$2;
/*  33 */     this.outTarget = $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/*  38 */     this.effect.close();
/*     */   }
/*     */   
/*     */   public final String getName() {
/*  42 */     return this.effect.getName();
/*     */   }
/*     */   
/*     */   public void addAuxAsset(String $$0, IntSupplier $$1, int $$2, int $$3) {
/*  46 */     this.auxNames.add(this.auxNames.size(), $$0);
/*  47 */     this.auxAssets.add(this.auxAssets.size(), $$1);
/*  48 */     this.auxWidths.add(this.auxWidths.size(), Integer.valueOf($$2));
/*  49 */     this.auxHeights.add(this.auxHeights.size(), Integer.valueOf($$3));
/*     */   }
/*     */   
/*     */   public void setOrthoMatrix(Matrix4f $$0) {
/*  53 */     this.shaderOrthoMatrix = $$0;
/*     */   }
/*     */   
/*     */   public void process(float $$0) {
/*  57 */     this.inTarget.unbindWrite();
/*     */     
/*  59 */     float $$1 = this.outTarget.width;
/*  60 */     float $$2 = this.outTarget.height;
/*  61 */     RenderSystem.viewport(0, 0, (int)$$1, (int)$$2);
/*     */     
/*  63 */     Objects.requireNonNull(this.inTarget); this.effect.setSampler("DiffuseSampler", this.inTarget::getColorTextureId);
/*     */ 
/*     */     
/*  66 */     for (int $$3 = 0; $$3 < this.auxAssets.size(); $$3++) {
/*  67 */       this.effect.setSampler(this.auxNames.get($$3), this.auxAssets.get($$3));
/*  68 */       this.effect.safeGetUniform("AuxSize" + $$3).set(((Integer)this.auxWidths.get($$3)).intValue(), ((Integer)this.auxHeights.get($$3)).intValue());
/*     */     } 
/*     */     
/*  71 */     this.effect.safeGetUniform("ProjMat").set(this.shaderOrthoMatrix);
/*  72 */     this.effect.safeGetUniform("InSize").set(this.inTarget.width, this.inTarget.height);
/*  73 */     this.effect.safeGetUniform("OutSize").set($$1, $$2);
/*  74 */     this.effect.safeGetUniform("Time").set($$0);
/*     */     
/*  76 */     Minecraft $$4 = Minecraft.getInstance();
/*  77 */     this.effect.safeGetUniform("ScreenSize").set($$4.getWindow().getWidth(), $$4.getWindow().getHeight());
/*  78 */     this.effect.apply();
/*  79 */     this.outTarget.clear(Minecraft.ON_OSX);
/*  80 */     this.outTarget.bindWrite(false);
/*     */     
/*  82 */     RenderSystem.depthFunc(519);
/*     */ 
/*     */     
/*  85 */     BufferBuilder $$5 = Tesselator.getInstance().getBuilder();
/*  86 */     $$5.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
/*     */     
/*  88 */     $$5.vertex(0.0D, 0.0D, 500.0D).endVertex();
/*  89 */     $$5.vertex($$1, 0.0D, 500.0D).endVertex();
/*  90 */     $$5.vertex($$1, $$2, 500.0D).endVertex();
/*  91 */     $$5.vertex(0.0D, $$2, 500.0D).endVertex();
/*  92 */     BufferUploader.draw($$5.end());
/*     */     
/*  94 */     RenderSystem.depthFunc(515);
/*     */     
/*  96 */     this.effect.clear();
/*  97 */     this.outTarget.unbindWrite();
/*  98 */     this.inTarget.unbindRead();
/*  99 */     for (IntSupplier $$6 : this.auxAssets) {
/* 100 */       if ($$6 instanceof RenderTarget) {
/* 101 */         ((RenderTarget)$$6).unbindRead();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public EffectInstance getEffect() {
/* 107 */     return this.effect;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\PostPass.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */