/*     */ package net.minecraft.server.packs.linkfs;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.nio.file.FileStore;
/*     */ import java.nio.file.FileSystem;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.PathMatcher;
/*     */ import java.nio.file.WatchService;
/*     */ import java.nio.file.attribute.UserPrincipalLookupService;
/*     */ import java.nio.file.spi.FileSystemProvider;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LinkFileSystem
/*     */   extends FileSystem
/*     */ {
/*  39 */   private static final Set<String> VIEWS = Set.of("basic");
/*     */   public static final String PATH_SEPARATOR = "/";
/*  41 */   private static final Splitter PATH_SPLITTER = Splitter.on('/');
/*     */   
/*     */   private final FileStore store;
/*  44 */   private final FileSystemProvider provider = new LinkFSProvider();
/*     */   private final LinkFSPath root;
/*     */   
/*     */   LinkFileSystem(String $$0, DirectoryEntry $$1) {
/*  48 */     this.store = new LinkFSFileStore($$0);
/*  49 */     this.root = buildPath($$1, this, "", null);
/*     */   }
/*     */   
/*     */   private static LinkFSPath buildPath(DirectoryEntry $$0, LinkFileSystem $$1, String $$2, @Nullable LinkFSPath $$3) {
/*  53 */     Object2ObjectOpenHashMap<String, LinkFSPath> $$4 = new Object2ObjectOpenHashMap();
/*  54 */     LinkFSPath $$5 = new LinkFSPath($$1, $$2, $$3, new PathContents.DirectoryContents((Map<String, LinkFSPath>)$$4));
/*  55 */     $$0.files.forEach(($$3, $$4) -> $$0.put($$3, new LinkFSPath($$1, $$3, $$2, new PathContents.FileContents($$4))));
/*     */ 
/*     */     
/*  58 */     $$0.children.forEach(($$3, $$4) -> $$0.put($$3, buildPath($$4, $$1, $$3, $$2)));
/*     */ 
/*     */     
/*  61 */     $$4.trim();
/*  62 */     return $$5;
/*     */   }
/*     */ 
/*     */   
/*     */   public FileSystemProvider provider() {
/*  67 */     return this.provider;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/*  76 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isReadOnly() {
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSeparator() {
/*  86 */     return "/";
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<Path> getRootDirectories() {
/*  91 */     return List.of(this.root);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<FileStore> getFileStores() {
/*  96 */     return List.of(this.store);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> supportedFileAttributeViews() {
/* 101 */     return VIEWS;
/*     */   }
/*     */ 
/*     */   
/*     */   public Path getPath(String $$0, String... $$1) {
/* 106 */     Stream<String> $$2 = Stream.of($$0);
/* 107 */     if ($$1.length > 0) {
/* 108 */       $$2 = Stream.concat($$2, Stream.of($$1));
/*     */     }
/* 110 */     String $$3 = $$2.collect(Collectors.joining("/"));
/* 111 */     if ($$3.equals("/")) {
/* 112 */       return this.root;
/*     */     }
/*     */     
/* 115 */     if ($$3.startsWith("/")) {
/* 116 */       LinkFSPath $$4 = this.root;
/* 117 */       for (String $$5 : PATH_SPLITTER.split($$3.substring(1))) {
/* 118 */         if ($$5.isEmpty()) {
/* 119 */           throw new IllegalArgumentException("Empty paths not allowed");
/*     */         }
/* 121 */         $$4 = $$4.resolveName($$5);
/*     */       } 
/* 123 */       return $$4;
/*     */     } 
/* 125 */     LinkFSPath $$6 = null;
/* 126 */     for (String $$7 : PATH_SPLITTER.split($$3)) {
/* 127 */       if ($$7.isEmpty()) {
/* 128 */         throw new IllegalArgumentException("Empty paths not allowed");
/*     */       }
/* 130 */       $$6 = new LinkFSPath(this, $$7, $$6, PathContents.RELATIVE);
/*     */     } 
/* 132 */     if ($$6 == null) {
/* 133 */       throw new IllegalArgumentException("Empty paths not allowed");
/*     */     }
/* 135 */     return $$6;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PathMatcher getPathMatcher(String $$0) {
/* 141 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public UserPrincipalLookupService getUserPrincipalLookupService() {
/* 146 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public WatchService newWatchService() {
/* 151 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public FileStore store() {
/* 155 */     return this.store;
/*     */   }
/*     */   
/*     */   public LinkFSPath rootPath() {
/* 159 */     return this.root;
/*     */   }
/*     */   private static final class DirectoryEntry extends Record { final Map<String, DirectoryEntry> children; final Map<String, Path> files;
/* 162 */     public Map<String, Path> files() { return this.files; } public Map<String, DirectoryEntry> children() { return this.children; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/packs/linkfs/LinkFileSystem$DirectoryEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #162	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/packs/linkfs/LinkFileSystem$DirectoryEntry;
/* 162 */       //   0	8	1	$$0	Ljava/lang/Object; } private DirectoryEntry(Map<String, DirectoryEntry> $$0, Map<String, Path> $$1) { this.children = $$0; this.files = $$1; }
/*     */     public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/packs/linkfs/LinkFileSystem$DirectoryEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #162	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/packs/linkfs/LinkFileSystem$DirectoryEntry; } public DirectoryEntry() {
/* 164 */       this(new HashMap<>(), new HashMap<>());
/*     */     } public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/packs/linkfs/LinkFileSystem$DirectoryEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #162	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/packs/linkfs/LinkFileSystem$DirectoryEntry;
/*     */     } } public static class Builder { private final LinkFileSystem.DirectoryEntry root;
/*     */     public Builder() {
/* 169 */       this.root = new LinkFileSystem.DirectoryEntry();
/*     */     }
/*     */     public Builder put(List<String> $$0, String $$1, Path $$2) {
/* 172 */       LinkFileSystem.DirectoryEntry $$3 = this.root;
/* 173 */       for (String $$4 : $$0) {
/* 174 */         $$3 = $$3.children.computeIfAbsent($$4, $$0 -> new LinkFileSystem.DirectoryEntry());
/*     */       }
/* 176 */       $$3.files.put($$1, $$2);
/* 177 */       return this;
/*     */     }
/*     */     
/*     */     public Builder put(List<String> $$0, Path $$1) {
/* 181 */       if ($$0.isEmpty()) {
/* 182 */         throw new IllegalArgumentException("Path can't be empty");
/*     */       }
/* 184 */       int $$2 = $$0.size() - 1;
/* 185 */       return put($$0.subList(0, $$2), $$0.get($$2), $$1);
/*     */     }
/*     */     
/*     */     public FileSystem build(String $$0) {
/* 189 */       return new LinkFileSystem($$0, this.root);
/*     */     } }
/*     */ 
/*     */   
/*     */   public static Builder builder() {
/* 194 */     return new Builder();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\linkfs\LinkFileSystem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */