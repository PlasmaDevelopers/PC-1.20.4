/*    */ package com.mojang.blaze3d.platform;
/*    */ 
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.List;
/*    */ import net.minecraft.server.packs.PackResources;
/*    */ import net.minecraft.server.packs.resources.IoSupplier;
/*    */ import org.apache.commons.lang3.ArrayUtils;
/*    */ 
/*    */ public enum IconSet
/*    */ {
/* 13 */   RELEASE(new String[] { "icons" }),
/* 14 */   SNAPSHOT(new String[] { "icons", "snapshot" });
/*    */   
/*    */   private final String[] path;
/*    */ 
/*    */   
/*    */   IconSet(String... $$0) {
/* 20 */     this.path = $$0;
/*    */   }
/*    */   
/*    */   public List<IoSupplier<InputStream>> getStandardIcons(PackResources $$0) throws IOException {
/* 24 */     return List.of(
/* 25 */         getFile($$0, "icon_16x16.png"), 
/* 26 */         getFile($$0, "icon_32x32.png"), 
/* 27 */         getFile($$0, "icon_48x48.png"), 
/* 28 */         getFile($$0, "icon_128x128.png"), 
/* 29 */         getFile($$0, "icon_256x256.png"));
/*    */   }
/*    */ 
/*    */   
/*    */   public IoSupplier<InputStream> getMacIcon(PackResources $$0) throws IOException {
/* 34 */     return getFile($$0, "minecraft.icns");
/*    */   }
/*    */   
/*    */   private IoSupplier<InputStream> getFile(PackResources $$0, String $$1) throws IOException {
/* 38 */     String[] $$2 = (String[])ArrayUtils.add((Object[])this.path, $$1);
/* 39 */     IoSupplier<InputStream> $$3 = $$0.getRootResource($$2);
/* 40 */     if ($$3 == null) {
/* 41 */       throw new FileNotFoundException(String.join("/", $$2));
/*    */     }
/* 43 */     return $$3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\platform\IconSet.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */