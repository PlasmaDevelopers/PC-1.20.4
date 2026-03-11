/*    */ package net.minecraft.data;
/*    */ import com.google.common.hash.Hashing;
/*    */ import com.google.common.hash.HashingOutputStream;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import com.mojang.serialization.Codec;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.io.OutputStreamWriter;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.nio.file.Path;
/*    */ import java.util.Comparator;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.ToIntFunction;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.util.GsonHelper;
/*    */ 
/*    */ public interface DataProvider {
/*    */   public static final ToIntFunction<String> FIXED_ORDER_FIELDS;
/*    */   
/*    */   static {
/* 25 */     FIXED_ORDER_FIELDS = (ToIntFunction<String>)Util.make(new Object2IntOpenHashMap(), $$0 -> {
/*    */           $$0.put("type", 0);
/*    */           $$0.put("parent", 1);
/*    */           $$0.defaultReturnValue(2);
/*    */         });
/* 30 */     KEY_COMPARATOR = Comparator.<String>comparingInt(FIXED_ORDER_FIELDS).thenComparing($$0 -> $$0);
/*    */   }
/* 32 */   public static final Comparator<String> KEY_COMPARATOR; public static final Logger LOGGER = LogUtils.getLogger();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static <T> CompletableFuture<?> saveStable(CachedOutput $$0, Codec<T> $$1, T $$2, Path $$3) {
/* 39 */     JsonElement $$4 = (JsonElement)Util.getOrThrow($$1.encodeStart((DynamicOps)JsonOps.INSTANCE, $$2), IllegalStateException::new);
/* 40 */     return saveStable($$0, $$4, $$3);
/*    */   }
/*    */   
/*    */   static CompletableFuture<?> saveStable(CachedOutput $$0, JsonElement $$1, Path $$2) {
/* 44 */     return CompletableFuture.runAsync(() -> {
/*    */           try {
/*    */             ByteArrayOutputStream $$3 = new ByteArrayOutputStream(); HashingOutputStream $$4 = new HashingOutputStream(Hashing.sha1(), $$3); JsonWriter $$5 = new JsonWriter(new OutputStreamWriter((OutputStream)$$4, StandardCharsets.UTF_8)); 
/*    */             try { $$5.setSerializeNulls(false); $$5.setIndent("  "); GsonHelper.writeValue($$5, $$0, KEY_COMPARATOR); $$5.close(); }
/* 48 */             catch (Throwable throwable) { try { $$5.close(); } catch (Throwable throwable1)
/*    */               { throwable.addSuppressed(throwable1); }
/*    */               
/*    */               throw throwable; }
/*    */             
/*    */             $$1.writeIfNeeded($$2, $$3.toByteArray(), $$4.hash());
/* 54 */           } catch (IOException $$6) {
/*    */             LOGGER.error("Failed to save file to {}", $$2, $$6);
/*    */           } 
/* 57 */         }Util.backgroundExecutor());
/*    */   }
/*    */   
/*    */   CompletableFuture<?> run(CachedOutput paramCachedOutput);
/*    */   
/*    */   String getName();
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface Factory<T extends DataProvider> {
/*    */     T create(PackOutput param1PackOutput);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\DataProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */