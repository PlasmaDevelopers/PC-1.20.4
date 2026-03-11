/*    */ package net.minecraft.client.renderer.texture.atlas.sources;
/*    */ 
/*    */ import com.mojang.blaze3d.platform.NativeImage;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import java.util.concurrent.atomic.AtomicReference;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.resources.Resource;
/*    */ 
/*    */ public class LazyLoadedImage
/*    */ {
/*    */   private final ResourceLocation id;
/*    */   private final Resource resource;
/* 15 */   private final AtomicReference<NativeImage> image = new AtomicReference<>();
/*    */   private final AtomicInteger referenceCount;
/*    */   
/*    */   public LazyLoadedImage(ResourceLocation $$0, Resource $$1, int $$2) {
/* 19 */     this.id = $$0;
/* 20 */     this.resource = $$1;
/* 21 */     this.referenceCount = new AtomicInteger($$2);
/*    */   }
/*    */   
/*    */   public NativeImage get() throws IOException {
/* 25 */     NativeImage $$0 = this.image.get();
/* 26 */     if ($$0 == null) {
/* 27 */       synchronized (this) {
/* 28 */         $$0 = this.image.get();
/* 29 */         if ($$0 == null) {
/* 30 */           try { InputStream $$1 = this.resource.open(); 
/* 31 */             try { $$0 = NativeImage.read($$1);
/* 32 */               this.image.set($$0);
/* 33 */               if ($$1 != null) $$1.close();  } catch (Throwable throwable) { if ($$1 != null) try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException $$2)
/* 34 */           { throw new IOException("Failed to load image " + this.id, $$2); }
/*    */         
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/* 40 */     return $$0;
/*    */   }
/*    */   
/*    */   public void release() {
/* 44 */     int $$0 = this.referenceCount.decrementAndGet();
/* 45 */     if ($$0 <= 0) {
/* 46 */       NativeImage $$1 = this.image.getAndSet(null);
/* 47 */       if ($$1 != null)
/* 48 */         $$1.close(); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\atlas\sources\LazyLoadedImage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */