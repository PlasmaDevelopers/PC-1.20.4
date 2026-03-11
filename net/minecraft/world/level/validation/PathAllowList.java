/*     */ package net.minecraft.world.level.validation;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.BufferedReader;
/*     */ import java.nio.file.FileSystem;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.PathMatcher;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.stream.Stream;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class PathAllowList implements PathMatcher {
/*  16 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   private static final String COMMENT_PREFIX = "#";
/*     */   private final List<ConfigEntry> entries;
/*     */   
/*     */   @FunctionalInterface
/*  21 */   public static interface EntryType { public static final EntryType FILESYSTEM = FileSystem::getPathMatcher; public static final EntryType PREFIX = ($$0, $$1) -> ();
/*     */     
/*     */     PathMatcher compile(FileSystem param1FileSystem, String param1String); }
/*     */   
/*     */   public static final class ConfigEntry extends Record { private final PathAllowList.EntryType type;
/*     */     private final String pattern;
/*     */     
/*  28 */     public ConfigEntry(PathAllowList.EntryType $$0, String $$1) { this.type = $$0; this.pattern = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/validation/PathAllowList$ConfigEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #28	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  28 */       //   0	7	0	this	Lnet/minecraft/world/level/validation/PathAllowList$ConfigEntry; } public PathAllowList.EntryType type() { return this.type; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/validation/PathAllowList$ConfigEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #28	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/validation/PathAllowList$ConfigEntry; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/validation/PathAllowList$ConfigEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #28	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/validation/PathAllowList$ConfigEntry;
/*  28 */       //   0	8	1	$$0	Ljava/lang/Object; } public String pattern() { return this.pattern; }
/*     */      public PathMatcher compile(FileSystem $$0) {
/*  30 */       return type().compile($$0, this.pattern);
/*     */     }
/*     */     
/*     */     static Optional<ConfigEntry> parse(String $$0) {
/*  34 */       if ($$0.isBlank() || $$0.startsWith("#")) {
/*  35 */         return Optional.empty();
/*     */       }
/*  37 */       if (!$$0.startsWith("[")) {
/*  38 */         return Optional.of(new ConfigEntry(PathAllowList.EntryType.PREFIX, $$0));
/*     */       }
/*     */       
/*  41 */       int $$1 = $$0.indexOf(']', 1);
/*  42 */       if ($$1 == -1) {
/*  43 */         throw new IllegalArgumentException("Unterminated type in line '" + $$0 + "'");
/*     */       }
/*     */       
/*  46 */       String $$2 = $$0.substring(1, $$1);
/*  47 */       String $$3 = $$0.substring($$1 + 1);
/*  48 */       switch ($$2) { case "glob": case "regex":
/*     */         
/*     */         case "prefix":
/*  51 */          }  throw new IllegalArgumentException("Unsupported definition type in line '" + $$0 + "'");
/*     */     }
/*     */ 
/*     */     
/*     */     static ConfigEntry glob(String $$0) {
/*  56 */       return new ConfigEntry(PathAllowList.EntryType.FILESYSTEM, "glob:" + $$0);
/*     */     }
/*     */     
/*     */     static ConfigEntry regex(String $$0) {
/*  60 */       return new ConfigEntry(PathAllowList.EntryType.FILESYSTEM, "regex:" + $$0);
/*     */     }
/*     */     
/*     */     static ConfigEntry prefix(String $$0) {
/*  64 */       return new ConfigEntry(PathAllowList.EntryType.PREFIX, $$0);
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*  69 */   private final Map<String, PathMatcher> compiledPaths = new ConcurrentHashMap<>();
/*     */   
/*     */   public PathAllowList(List<ConfigEntry> $$0) {
/*  72 */     this.entries = $$0;
/*     */   }
/*     */   
/*     */   public PathMatcher getForFileSystem(FileSystem $$0) {
/*  76 */     return this.compiledPaths.computeIfAbsent($$0.provider().getScheme(), $$1 -> {
/*     */           List<PathMatcher> $$2;
/*     */ 
/*     */           
/*     */           try {
/*     */             $$2 = this.entries.stream().map(()).toList();
/*  82 */           } catch (Exception $$3) {
/*     */             LOGGER.error("Failed to compile file pattern list", $$3);
/*     */             return ();
/*     */           } 
/*     */           switch ($$2.size()) {
/*     */             case 0:
/*     */             
/*     */             case 1:
/*     */             
/*     */           } 
/*     */           return ();
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(Path $$0) {
/* 104 */     return getForFileSystem($$0.getFileSystem()).matches($$0);
/*     */   }
/*     */   
/*     */   public static PathAllowList readPlain(BufferedReader $$0) {
/* 108 */     return new PathAllowList($$0.lines().<ConfigEntry>flatMap($$0 -> ConfigEntry.parse($$0).stream()).toList());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\validation\PathAllowList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */