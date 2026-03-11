/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import com.mojang.blaze3d.platform.NativeImage;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ 
/*    */ 
/*    */ public class LegacyStuffWrapper
/*    */ {
/*    */   @Deprecated
/*    */   public static int[] getPixels(ResourceManager $$0, ResourceLocation $$1) throws IOException {
/* 14 */     InputStream $$2 = $$0.open($$1); try {
/* 15 */       NativeImage $$3 = NativeImage.read($$2);
/*    */       try {
/* 17 */         int[] arrayOfInt = $$3.makePixelArray();
/* 18 */         if ($$3 != null) $$3.close();  if ($$2 != null) $$2.close(); 
/*    */         return arrayOfInt;
/*    */       } catch (Throwable throwable) {
/*    */         if ($$3 != null)
/*    */           try {
/*    */             $$3.close();
/*    */           } catch (Throwable throwable1) {
/*    */             throwable.addSuppressed(throwable1);
/*    */           }  
/*    */         throw throwable;
/*    */       } 
/*    */     } catch (Throwable throwable) {
/*    */       if ($$2 != null)
/*    */         try {
/*    */           $$2.close();
/*    */         } catch (Throwable throwable1) {
/*    */           throwable.addSuppressed(throwable1);
/*    */         }  
/*    */       throw throwable;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\LegacyStuffWrapper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */