/*    */ package net.minecraft.server.packs.resources;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.io.Reader;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.resources.FileToIdConverter;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.GsonHelper;
/*    */ import net.minecraft.util.profiling.ProfilerFiller;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public abstract class SimpleJsonResourceReloadListener
/*    */   extends SimplePreparableReloadListener<Map<ResourceLocation, JsonElement>>
/*    */ {
/* 19 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private final Gson gson;
/*    */   private final String directory;
/*    */   
/*    */   public SimpleJsonResourceReloadListener(Gson $$0, String $$1) {
/* 25 */     this.gson = $$0;
/* 26 */     this.directory = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Map<ResourceLocation, JsonElement> prepare(ResourceManager $$0, ProfilerFiller $$1) {
/* 31 */     Map<ResourceLocation, JsonElement> $$2 = new HashMap<>();
/* 32 */     scanDirectory($$0, this.directory, this.gson, $$2);
/* 33 */     return $$2;
/*    */   }
/*    */   
/*    */   public static void scanDirectory(ResourceManager $$0, String $$1, Gson $$2, Map<ResourceLocation, JsonElement> $$3) {
/* 37 */     FileToIdConverter $$4 = FileToIdConverter.json($$1);
/*    */     
/* 39 */     for (Map.Entry<ResourceLocation, Resource> $$5 : (Iterable<Map.Entry<ResourceLocation, Resource>>)$$4.listMatchingResources($$0).entrySet()) {
/* 40 */       ResourceLocation $$6 = $$5.getKey();
/* 41 */       ResourceLocation $$7 = $$4.fileToId($$6);
/*    */       
/* 43 */       try { Reader $$8 = ((Resource)$$5.getValue()).openAsReader(); 
/* 44 */         try { JsonElement $$9 = (JsonElement)GsonHelper.fromJson($$2, $$8, JsonElement.class);
/* 45 */           JsonElement $$10 = $$3.put($$7, $$9);
/* 46 */           if ($$10 != null) {
/* 47 */             throw new IllegalStateException("Duplicate data file ignored with ID " + $$7);
/*    */           }
/* 49 */           if ($$8 != null) $$8.close();  } catch (Throwable throwable) { if ($$8 != null) try { $$8.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (JsonParseException|IllegalArgumentException|java.io.IOException $$11)
/* 50 */       { LOGGER.error("Couldn't parse data file {} from {}", new Object[] { $$7, $$6, $$11 }); }
/*    */     
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\resources\SimpleJsonResourceReloadListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */