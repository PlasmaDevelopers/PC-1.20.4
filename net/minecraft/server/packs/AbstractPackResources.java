/*    */ package net.minecraft.server.packs;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*    */ import net.minecraft.server.packs.resources.IoSupplier;
/*    */ import net.minecraft.util.GsonHelper;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public abstract class AbstractPackResources
/*    */   implements PackResources {
/* 18 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   private final String name;
/*    */   private final boolean isBuiltin;
/*    */   
/*    */   protected AbstractPackResources(String $$0, boolean $$1) {
/* 23 */     this.name = $$0;
/* 24 */     this.isBuiltin = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T> T getMetadataSection(MetadataSectionSerializer<T> $$0) throws IOException {
/* 30 */     IoSupplier<InputStream> $$1 = getRootResource(new String[] { "pack.mcmeta" });
/* 31 */     if ($$1 == null) {
/* 32 */       return null;
/*    */     }
/* 34 */     InputStream $$2 = (InputStream)$$1.get(); 
/* 35 */     try { T t = (T)getMetadataFromStream((MetadataSectionSerializer)$$0, $$2);
/* 36 */       if ($$2 != null) $$2.close();  return t; }
/*    */     catch (Throwable throwable) { if ($$2 != null)
/*    */         try { $$2.close(); }
/*    */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*    */           throw throwable; }
/*    */      } @Nullable public static <T> T getMetadataFromStream(MetadataSectionSerializer<T> $$0, InputStream $$1) { JsonObject $$3; 
/* 42 */     try { BufferedReader $$2 = new BufferedReader(new InputStreamReader($$1, StandardCharsets.UTF_8)); 
/* 43 */       try { $$3 = GsonHelper.parse($$2);
/* 44 */         $$2.close(); } catch (Throwable throwable) { try { $$2.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Exception $$5)
/* 45 */     { LOGGER.error("Couldn't load {} metadata", $$0.getMetadataSectionName(), $$5);
/* 46 */       return null; }
/*    */ 
/*    */     
/* 49 */     if (!$$3.has($$0.getMetadataSectionName())) {
/* 50 */       return null;
/*    */     }
/*    */     try {
/* 53 */       return (T)$$0.fromJson(GsonHelper.getAsJsonObject($$3, $$0.getMetadataSectionName()));
/* 54 */     } catch (Exception $$7) {
/* 55 */       LOGGER.error("Couldn't load {} metadata", $$0.getMetadataSectionName(), $$7);
/* 56 */       return null;
/*    */     }  }
/*    */ 
/*    */ 
/*    */   
/*    */   public String packId() {
/* 62 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isBuiltin() {
/* 67 */     return this.isBuiltin;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\AbstractPackResources.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */