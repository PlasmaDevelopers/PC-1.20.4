/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import com.google.common.base.Splitter;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.packs.linkfs.LinkFileSystem;
/*    */ import net.minecraft.util.GsonHelper;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class IndexedAssetSource
/*    */ {
/* 21 */   private static final Logger LOGGER = LogUtils.getLogger();
/* 22 */   public static final Splitter PATH_SPLITTER = Splitter.on('/');
/*    */   
/*    */   public static Path createIndexFs(Path $$0, String $$1) {
/* 25 */     Path $$2 = $$0.resolve("objects");
/*    */     
/* 27 */     LinkFileSystem.Builder $$3 = LinkFileSystem.builder();
/*    */     
/* 29 */     Path $$4 = $$0.resolve("indexes/" + $$1 + ".json"); 
/* 30 */     try { BufferedReader $$5 = Files.newBufferedReader($$4, StandardCharsets.UTF_8); 
/* 31 */       try { JsonObject $$6 = GsonHelper.parse($$5);
/* 32 */         JsonObject $$7 = GsonHelper.getAsJsonObject($$6, "objects", null);
/* 33 */         if ($$7 != null) {
/* 34 */           for (Map.Entry<String, JsonElement> $$8 : (Iterable<Map.Entry<String, JsonElement>>)$$7.entrySet()) {
/* 35 */             JsonObject $$9 = (JsonObject)$$8.getValue();
/*    */             
/* 37 */             String $$10 = $$8.getKey();
/* 38 */             List<String> $$11 = PATH_SPLITTER.splitToList($$10);
/* 39 */             String $$12 = GsonHelper.getAsString($$9, "hash");
/* 40 */             Path $$13 = $$2.resolve($$12.substring(0, 2) + "/" + $$12.substring(0, 2));
/*    */             
/* 42 */             $$3.put($$11, $$13);
/*    */           } 
/*    */         }
/* 45 */         if ($$5 != null) $$5.close();  } catch (Throwable throwable) { if ($$5 != null) try { $$5.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (JsonParseException $$14)
/* 46 */     { LOGGER.error("Unable to parse resource index file: {}", $$4); }
/* 47 */     catch (IOException $$15)
/* 48 */     { LOGGER.error("Can't open the resource index file: {}", $$4); }
/*    */ 
/*    */     
/* 51 */     return $$3.build("index-" + $$1).getPath("/", new String[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\IndexedAssetSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */