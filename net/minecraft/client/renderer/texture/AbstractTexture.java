/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import com.mojang.blaze3d.platform.GlStateManager;
/*    */ import com.mojang.blaze3d.platform.TextureUtil;
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import java.io.IOException;
/*    */ import java.util.concurrent.Executor;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ 
/*    */ public abstract class AbstractTexture
/*    */   implements AutoCloseable
/*    */ {
/*    */   public static final int NOT_ASSIGNED = -1;
/* 15 */   protected int id = -1;
/*    */   protected boolean blur;
/*    */   
/*    */   public void setFilter(boolean $$0, boolean $$1) {
/*    */     int $$4, $$5;
/* 20 */     RenderSystem.assertOnRenderThreadOrInit();
/* 21 */     this.blur = $$0;
/* 22 */     this.mipmap = $$1;
/*    */ 
/*    */ 
/*    */     
/* 26 */     if ($$0) {
/* 27 */       int $$2 = $$1 ? 9987 : 9729;
/* 28 */       int $$3 = 9729;
/*    */     } else {
/* 30 */       $$4 = $$1 ? 9986 : 9728;
/* 31 */       $$5 = 9728;
/*    */     } 
/* 33 */     bind();
/* 34 */     GlStateManager._texParameter(3553, 10241, $$4);
/* 35 */     GlStateManager._texParameter(3553, 10240, $$5);
/*    */   }
/*    */   protected boolean mipmap;
/*    */   public int getId() {
/* 39 */     RenderSystem.assertOnRenderThreadOrInit();
/* 40 */     if (this.id == -1) {
/* 41 */       this.id = TextureUtil.generateTextureId();
/*    */     }
/*    */     
/* 44 */     return this.id;
/*    */   }
/*    */   
/*    */   public void releaseId() {
/* 48 */     if (!RenderSystem.isOnRenderThread()) {
/* 49 */       RenderSystem.recordRenderCall(() -> {
/*    */             if (this.id != -1) {
/*    */               TextureUtil.releaseTextureId(this.id);
/*    */               
/*    */               this.id = -1;
/*    */             } 
/*    */           });
/* 56 */     } else if (this.id != -1) {
/* 57 */       TextureUtil.releaseTextureId(this.id);
/* 58 */       this.id = -1;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void bind() {
/* 66 */     if (!RenderSystem.isOnRenderThreadOrInit()) {
/* 67 */       RenderSystem.recordRenderCall(() -> GlStateManager._bindTexture(getId()));
/*    */     }
/*    */     else {
/*    */       
/* 71 */       GlStateManager._bindTexture(getId());
/*    */     } 
/*    */   }
/*    */   
/*    */   public void reset(TextureManager $$0, ResourceManager $$1, ResourceLocation $$2, Executor $$3) {
/* 76 */     $$0.register($$2, this);
/*    */   }
/*    */   
/*    */   public void close() {}
/*    */   
/*    */   public abstract void load(ResourceManager paramResourceManager) throws IOException;
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\AbstractTexture.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */