/*     */ package net.minecraft.data.structures;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.hash.HashCode;
/*     */ import com.google.common.hash.Hashing;
/*     */ import com.google.common.hash.HashingOutputStream;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.data.CachedOutput;
/*     */ import net.minecraft.data.DataProvider;
/*     */ import net.minecraft.data.PackOutput;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtIo;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class SnbtToNbt implements DataProvider {
/*  28 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final PackOutput output;
/*     */   
/*     */   private final Iterable<Path> inputFolders;
/*  33 */   private final List<Filter> filters = Lists.newArrayList();
/*     */   
/*     */   public SnbtToNbt(PackOutput $$0, Iterable<Path> $$1) {
/*  36 */     this.output = $$0;
/*  37 */     this.inputFolders = $$1;
/*     */   }
/*     */   
/*     */   public SnbtToNbt addFilter(Filter $$0) {
/*  41 */     this.filters.add($$0);
/*  42 */     return this;
/*     */   }
/*     */   
/*     */   private CompoundTag applyFilters(String $$0, CompoundTag $$1) {
/*  46 */     CompoundTag $$2 = $$1;
/*  47 */     for (Filter $$3 : this.filters) {
/*  48 */       $$2 = $$3.apply($$0, $$2);
/*     */     }
/*  50 */     return $$2;
/*     */   } @FunctionalInterface
/*     */   public static interface Filter {
/*  53 */     CompoundTag apply(String param1String, CompoundTag param1CompoundTag); } private static final class TaskResult extends Record { final String name; final byte[] payload; final HashCode hash; TaskResult(String $$0, byte[] $$1, HashCode $$2) { this.name = $$0; this.payload = $$1; this.hash = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/data/structures/SnbtToNbt$TaskResult;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #53	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  53 */       //   0	7	0	this	Lnet/minecraft/data/structures/SnbtToNbt$TaskResult; } public String name() { return this.name; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/data/structures/SnbtToNbt$TaskResult;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #53	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/data/structures/SnbtToNbt$TaskResult; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/data/structures/SnbtToNbt$TaskResult;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #53	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/data/structures/SnbtToNbt$TaskResult;
/*  53 */       //   0	8	1	$$0	Ljava/lang/Object; } public byte[] payload() { return this.payload; } public HashCode hash() { return this.hash; }
/*     */      }
/*     */   
/*     */   public CompletableFuture<?> run(CachedOutput $$0) {
/*  57 */     Path $$1 = this.output.getOutputFolder();
/*     */     
/*  59 */     List<CompletableFuture<?>> $$2 = Lists.newArrayList();
/*     */     
/*  61 */     for (Iterator<Path> iterator = this.inputFolders.iterator(); iterator.hasNext(); ) { Path $$3 = iterator.next();
/*  62 */       $$2.add(CompletableFuture.supplyAsync(() -> { try { Stream<Path> $$3 = Files.walk($$0, new java.nio.file.FileVisitOption[0]); try { CompletableFuture<Void> completableFuture = CompletableFuture.allOf((CompletableFuture<?>[])$$3.filter(()).map(()).toArray(())); if ($$3 != null)
/*  63 */                     $$3.close();  return completableFuture; } catch (Throwable throwable) { if ($$3 != null) try { $$3.close(); } catch (Throwable throwable1)
/*     */                     { throwable.addSuppressed(throwable1); }
/*     */                   
/*     */ 
/*     */                   
/*     */                   throw throwable; }
/*     */                  }
/*  70 */               catch (Exception $$4)
/*     */               { throw new RuntimeException("Failed to read structure input directory, aborting", $$4); }
/*     */             
/*  73 */             }Util.backgroundExecutor()).thenCompose($$0 -> $$0)); }
/*     */ 
/*     */     
/*  76 */     return Util.sequenceFailFast($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getName() {
/*  81 */     return "SNBT -> NBT";
/*     */   }
/*     */   
/*     */   private String getName(Path $$0, Path $$1) {
/*  85 */     String $$2 = $$0.relativize($$1).toString().replaceAll("\\\\", "/");
/*  86 */     return $$2.substring(0, $$2.length() - ".snbt".length());
/*     */   }
/*     */   private TaskResult readStructure(Path $$0, String $$1) {
/*     */     
/*  90 */     try { BufferedReader $$2 = Files.newBufferedReader($$0); 
/*  91 */       try { String $$3 = IOUtils.toString($$2);
/*  92 */         CompoundTag $$4 = applyFilters($$1, NbtUtils.snbtToStructure($$3));
/*  93 */         ByteArrayOutputStream $$5 = new ByteArrayOutputStream();
/*  94 */         HashingOutputStream $$6 = new HashingOutputStream(Hashing.sha1(), $$5);
/*  95 */         NbtIo.writeCompressed($$4, (OutputStream)$$6);
/*  96 */         byte[] $$7 = $$5.toByteArray();
/*  97 */         HashCode $$8 = $$6.hash();
/*  98 */         TaskResult taskResult = new TaskResult($$1, $$7, $$8);
/*  99 */         if ($$2 != null) $$2.close();  return taskResult; } catch (Throwable throwable) { if ($$2 != null) try { $$2.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable $$9)
/* 100 */     { throw new StructureConversionException($$0, $$9); }
/*     */   
/*     */   }
/*     */   
/*     */   private void storeStructureIfChanged(CachedOutput $$0, TaskResult $$1, Path $$2) {
/* 105 */     Path $$3 = $$2.resolve($$1.name + ".nbt");
/*     */     try {
/* 107 */       $$0.writeIfNeeded($$3, $$1.payload, $$1.hash);
/* 108 */     } catch (IOException $$4) {
/* 109 */       LOGGER.error("Couldn't write structure {} at {}", new Object[] { $$1.name, $$3, $$4 });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class StructureConversionException
/*     */     extends RuntimeException
/*     */   {
/*     */     public StructureConversionException(Path $$0, Throwable $$1) {
/* 120 */       super($$0.toAbsolutePath().toString(), $$1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\structures\SnbtToNbt.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */