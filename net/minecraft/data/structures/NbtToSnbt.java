/*    */ package net.minecraft.data.structures;
/*    */ import com.google.common.hash.Hashing;
/*    */ import com.google.common.hash.HashingOutputStream;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.CompletionStage;
/*    */ import java.util.stream.Stream;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.data.CachedOutput;
/*    */ import net.minecraft.data.DataProvider;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.nbt.NbtAccounter;
/*    */ import net.minecraft.nbt.NbtIo;
/*    */ import net.minecraft.nbt.NbtUtils;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class NbtToSnbt implements DataProvider {
/* 29 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private final Iterable<Path> inputFolders;
/*    */   private final PackOutput output;
/*    */   
/*    */   public NbtToSnbt(PackOutput $$0, Collection<Path> $$1) {
/* 35 */     this.inputFolders = $$1;
/* 36 */     this.output = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public CompletableFuture<?> run(CachedOutput $$0) {
/* 41 */     Path $$1 = this.output.getOutputFolder();
/*    */     
/* 43 */     List<CompletableFuture<?>> $$2 = new ArrayList<>();
/*    */     
/* 45 */     for (Iterator<Path> iterator = this.inputFolders.iterator(); iterator.hasNext(); ) { Path $$3 = iterator.next();
/* 46 */       $$2.add(CompletableFuture.supplyAsync(() -> { try { Stream<Path> $$3 = Files.walk($$0, new java.nio.file.FileVisitOption[0]); try { CompletableFuture<Void> completableFuture = CompletableFuture.allOf((CompletableFuture<?>[])$$3.filter(()).map(()).toArray(())); if ($$3 != null)
/* 47 */                     $$3.close();  return completableFuture; } catch (Throwable throwable) { if ($$3 != null) try { $$3.close(); } catch (Throwable throwable1)
/*    */                     { throwable.addSuppressed(throwable1); }
/*    */                      
/*    */                   throw throwable; }
/*    */                  }
/* 52 */               catch (IOException $$4)
/*    */               { LOGGER.error("Failed to read structure input directory", $$4);
/*    */                 return CompletableFuture.completedFuture(null); }
/*    */             
/* 56 */             }Util.backgroundExecutor()).thenCompose($$0 -> $$0)); }
/*    */ 
/*    */     
/* 59 */     return CompletableFuture.allOf((CompletableFuture<?>[])$$2.toArray($$0 -> new CompletableFuture[$$0]));
/*    */   }
/*    */ 
/*    */   
/*    */   public final String getName() {
/* 64 */     return "NBT -> SNBT";
/*    */   }
/*    */   
/*    */   private static String getName(Path $$0, Path $$1) {
/* 68 */     String $$2 = $$0.relativize($$1).toString().replaceAll("\\\\", "/");
/* 69 */     return $$2.substring(0, $$2.length() - ".nbt".length());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static Path convertStructure(CachedOutput $$0, Path $$1, String $$2, Path $$3) {
/*    */     
/* 78 */     try { InputStream $$4 = Files.newInputStream($$1, new java.nio.file.OpenOption[0]); 
/* 79 */       try { Path $$5 = $$3.resolve($$2 + ".snbt");
/* 80 */         writeSnbt($$0, $$5, NbtUtils.structureToSnbt(NbtIo.readCompressed($$4, NbtAccounter.unlimitedHeap())));
/* 81 */         LOGGER.info("Converted {} from NBT to SNBT", $$2);
/* 82 */         Path path1 = $$5;
/* 83 */         if ($$4 != null) $$4.close();  return path1; } catch (Throwable throwable) { if ($$4 != null) try { $$4.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException $$6)
/* 84 */     { LOGGER.error("Couldn't convert {} from NBT to SNBT at {}", new Object[] { $$2, $$1, $$6 });
/* 85 */       return null; }
/*    */   
/*    */   }
/*    */   
/*    */   public static void writeSnbt(CachedOutput $$0, Path $$1, String $$2) throws IOException {
/* 90 */     ByteArrayOutputStream $$3 = new ByteArrayOutputStream();
/* 91 */     HashingOutputStream $$4 = new HashingOutputStream(Hashing.sha1(), $$3);
/* 92 */     $$4.write($$2.getBytes(StandardCharsets.UTF_8));
/* 93 */     $$4.write(10);
/* 94 */     $$0.writeIfNeeded($$1, $$3.toByteArray(), $$4.hash());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\structures\NbtToSnbt.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */