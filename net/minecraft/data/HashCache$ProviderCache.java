/*    */ package net.minecraft.data;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.google.common.collect.UnmodifiableIterator;
/*    */ import com.google.common.hash.HashCode;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.IOException;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class ProviderCache
/*    */   extends Record
/*    */ {
/*    */   final String version;
/*    */   private final ImmutableMap<Path, HashCode> data;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/data/HashCache$ProviderCache;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #36	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/data/HashCache$ProviderCache;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/data/HashCache$ProviderCache;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #36	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/data/HashCache$ProviderCache;
/*    */   }
/*    */   
/*    */   ProviderCache(String $$0, ImmutableMap<Path, HashCode> $$1) {
/* 36 */     this.version = $$0; this.data = $$1; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/data/HashCache$ProviderCache;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #36	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/data/HashCache$ProviderCache;
/* 36 */     //   0	8	1	$$0	Ljava/lang/Object; } public String version() { return this.version; } public ImmutableMap<Path, HashCode> data() { return this.data; }
/*    */    @Nullable
/*    */   public HashCode get(Path $$0) {
/* 39 */     return (HashCode)this.data.get($$0);
/*    */   }
/*    */   
/*    */   public int count() {
/* 43 */     return this.data.size();
/*    */   }
/*    */   
/*    */   public static ProviderCache load(Path $$0, Path $$1) throws IOException {
/* 47 */     BufferedReader $$2 = Files.newBufferedReader($$1, StandardCharsets.UTF_8); 
/* 48 */     try { String $$3 = $$2.readLine();
/* 49 */       if (!$$3.startsWith("// ")) {
/* 50 */         throw new IllegalStateException("Missing cache file header");
/*    */       }
/* 52 */       String[] $$4 = $$3.substring("// ".length()).split("\t", 2);
/* 53 */       String $$5 = $$4[0];
/* 54 */       ImmutableMap.Builder<Path, HashCode> $$6 = ImmutableMap.builder();
/* 55 */       $$2.lines().forEach($$2 -> {
/*    */             int $$3 = $$2.indexOf(' ');
/*    */             $$0.put($$1.resolve($$2.substring($$3 + 1)), HashCode.fromString($$2.substring(0, $$3)));
/*    */           });
/* 59 */       ProviderCache providerCache = new ProviderCache($$5, $$6.build());
/* 60 */       if ($$2 != null) $$2.close();  return providerCache; } catch (Throwable throwable) { if ($$2 != null)
/*    */         try { $$2.close(); }
/*    */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*    */           throw throwable; }
/* 64 */      } public void save(Path $$0, Path $$1, String $$2) { try { BufferedWriter $$3 = Files.newBufferedWriter($$1, StandardCharsets.UTF_8, new java.nio.file.OpenOption[0]); 
/* 65 */       try { $$3.write("// ");
/* 66 */         $$3.write(this.version);
/* 67 */         $$3.write(9);
/* 68 */         $$3.write($$2);
/* 69 */         $$3.newLine();
/* 70 */         for (UnmodifiableIterator<Map.Entry<Path, HashCode>> unmodifiableIterator = this.data.entrySet().iterator(); unmodifiableIterator.hasNext(); ) { Map.Entry<Path, HashCode> $$4 = unmodifiableIterator.next();
/* 71 */           $$3.write(((HashCode)$$4.getValue()).toString());
/* 72 */           $$3.write(32);
/* 73 */           $$3.write($$0.relativize($$4.getKey()).toString());
/* 74 */           $$3.newLine(); }
/*    */         
/* 76 */         if ($$3 != null) $$3.close();  } catch (Throwable throwable) { if ($$3 != null) try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException $$5)
/* 77 */     { HashCache.LOGGER.warn("Unable write cachefile {}: {}", $$1, $$5); }
/*    */      }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\HashCache$ProviderCache.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */