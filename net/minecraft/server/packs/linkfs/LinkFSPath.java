/*     */ package net.minecraft.server.packs.linkfs;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.nio.file.FileSystem;
/*     */ import java.nio.file.LinkOption;
/*     */ import java.nio.file.NoSuchFileException;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.ProviderMismatchException;
/*     */ import java.nio.file.ReadOnlyFileSystemException;
/*     */ import java.nio.file.WatchEvent;
/*     */ import java.nio.file.WatchKey;
/*     */ import java.nio.file.WatchService;
/*     */ import java.nio.file.attribute.BasicFileAttributeView;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.nio.file.attribute.FileTime;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ class LinkFSPath implements Path {
/*  27 */   private static final BasicFileAttributes DIRECTORY_ATTRIBUTES = new DummyFileAttributes()
/*     */     {
/*     */       public boolean isRegularFile() {
/*  30 */         return false;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean isDirectory() {
/*  35 */         return true;
/*     */       }
/*     */     };
/*     */   
/*  39 */   private static final BasicFileAttributes FILE_ATTRIBUTES = new DummyFileAttributes()
/*     */     {
/*     */       public boolean isRegularFile() {
/*  42 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean isDirectory() {
/*  47 */         return false;
/*     */       }
/*     */     };
/*     */   
/*  51 */   private static final Comparator<LinkFSPath> PATH_COMPARATOR = Comparator.comparing(LinkFSPath::pathToString);
/*     */   
/*     */   private final String name;
/*     */   
/*     */   private final LinkFileSystem fileSystem;
/*     */   @Nullable
/*     */   private final LinkFSPath parent;
/*     */   @Nullable
/*     */   private List<String> pathToRoot;
/*     */   @Nullable
/*     */   private String pathString;
/*     */   private final PathContents pathContents;
/*     */   
/*     */   public LinkFSPath(LinkFileSystem $$0, String $$1, @Nullable LinkFSPath $$2, PathContents $$3) {
/*  65 */     this.fileSystem = $$0;
/*  66 */     this.name = $$1;
/*  67 */     this.parent = $$2;
/*  68 */     this.pathContents = $$3;
/*     */   }
/*     */   
/*     */   private LinkFSPath createRelativePath(@Nullable LinkFSPath $$0, String $$1) {
/*  72 */     return new LinkFSPath(this.fileSystem, $$1, $$0, PathContents.RELATIVE);
/*     */   }
/*     */ 
/*     */   
/*     */   public LinkFileSystem getFileSystem() {
/*  77 */     return this.fileSystem;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAbsolute() {
/*  82 */     return (this.pathContents != PathContents.RELATIVE);
/*     */   }
/*     */ 
/*     */   
/*     */   public File toFile() {
/*  87 */     PathContents pathContents = this.pathContents; if (pathContents instanceof PathContents.FileContents) { PathContents.FileContents $$0 = (PathContents.FileContents)pathContents;
/*  88 */       return $$0.contents().toFile(); }
/*     */     
/*  90 */     throw new UnsupportedOperationException("Path " + pathToString() + " does not represent file");
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public LinkFSPath getRoot() {
/*  96 */     if (isAbsolute()) {
/*  97 */       return this.fileSystem.rootPath();
/*     */     }
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public LinkFSPath getFileName() {
/* 104 */     return createRelativePath(null, this.name);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public LinkFSPath getParent() {
/* 110 */     return this.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNameCount() {
/* 115 */     return pathToRoot().size();
/*     */   }
/*     */ 
/*     */   
/*     */   private List<String> pathToRoot() {
/* 120 */     if (this.name.isEmpty()) {
/* 121 */       return List.of();
/*     */     }
/*     */     
/* 124 */     if (this.pathToRoot == null) {
/* 125 */       ImmutableList.Builder<String> $$0 = ImmutableList.builder();
/* 126 */       if (this.parent != null) {
/* 127 */         $$0.addAll(this.parent.pathToRoot());
/*     */       }
/* 129 */       $$0.add(this.name);
/* 130 */       this.pathToRoot = (List<String>)$$0.build();
/*     */     } 
/* 132 */     return this.pathToRoot;
/*     */   }
/*     */ 
/*     */   
/*     */   public LinkFSPath getName(int $$0) {
/* 137 */     List<String> $$1 = pathToRoot();
/* 138 */     if ($$0 < 0 || $$0 >= $$1.size()) {
/* 139 */       throw new IllegalArgumentException("Invalid index: " + $$0);
/*     */     }
/* 141 */     return createRelativePath(null, $$1.get($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public LinkFSPath subpath(int $$0, int $$1) {
/* 146 */     List<String> $$2 = pathToRoot();
/*     */     
/* 148 */     if ($$0 < 0 || $$1 > $$2.size() || $$0 >= $$1) {
/* 149 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 152 */     LinkFSPath $$3 = null;
/* 153 */     for (int $$4 = $$0; $$4 < $$1; $$4++) {
/* 154 */       $$3 = createRelativePath($$3, $$2.get($$4));
/*     */     }
/* 156 */     return $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean startsWith(Path $$0) {
/* 161 */     if ($$0.isAbsolute() != isAbsolute()) {
/* 162 */       return false;
/*     */     }
/* 164 */     if ($$0 instanceof LinkFSPath) { LinkFSPath $$1 = (LinkFSPath)$$0;
/* 165 */       if ($$1.fileSystem != this.fileSystem) {
/* 166 */         return false;
/*     */       }
/* 168 */       List<String> $$2 = pathToRoot();
/* 169 */       List<String> $$3 = $$1.pathToRoot();
/*     */       
/* 171 */       int $$4 = $$3.size();
/* 172 */       if ($$4 > $$2.size()) {
/* 173 */         return false;
/*     */       }
/* 175 */       for (int $$5 = 0; $$5 < $$4; $$5++) {
/* 176 */         if (!((String)$$3.get($$5)).equals($$2.get($$5))) {
/* 177 */           return false;
/*     */         }
/*     */       } 
/* 180 */       return true; }
/*     */     
/* 182 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean endsWith(Path $$0) {
/* 187 */     if ($$0.isAbsolute() && !isAbsolute()) {
/* 188 */       return false;
/*     */     }
/* 190 */     if ($$0 instanceof LinkFSPath) { LinkFSPath $$1 = (LinkFSPath)$$0;
/* 191 */       if ($$1.fileSystem != this.fileSystem) {
/* 192 */         return false;
/*     */       }
/* 194 */       List<String> $$2 = pathToRoot();
/* 195 */       List<String> $$3 = $$1.pathToRoot();
/*     */       
/* 197 */       int $$4 = $$3.size();
/* 198 */       int $$5 = $$2.size() - $$4;
/* 199 */       if ($$5 < 0) {
/* 200 */         return false;
/*     */       }
/*     */       
/* 203 */       for (int $$6 = $$4 - 1; $$6 >= 0; $$6--) {
/* 204 */         if (!((String)$$3.get($$6)).equals($$2.get($$5 + $$6))) {
/* 205 */           return false;
/*     */         }
/*     */       } 
/* 208 */       return true; }
/*     */     
/* 210 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LinkFSPath normalize() {
/* 216 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public LinkFSPath resolve(Path $$0) {
/* 221 */     LinkFSPath $$1 = toLinkPath($$0);
/* 222 */     if ($$0.isAbsolute()) {
/* 223 */       return $$1;
/*     */     }
/* 225 */     return resolve($$1.pathToRoot());
/*     */   }
/*     */   
/*     */   private LinkFSPath resolve(List<String> $$0) {
/* 229 */     LinkFSPath $$1 = this;
/* 230 */     for (String $$2 : $$0) {
/* 231 */       $$1 = $$1.resolveName($$2);
/*     */     }
/*     */     
/* 234 */     return $$1;
/*     */   }
/*     */   
/*     */   LinkFSPath resolveName(String $$0) {
/* 238 */     if (isRelativeOrMissing(this.pathContents))
/* 239 */       return new LinkFSPath(this.fileSystem, $$0, this, this.pathContents); 
/* 240 */     PathContents pathContents = this.pathContents; if (pathContents instanceof PathContents.DirectoryContents) { PathContents.DirectoryContents $$1 = (PathContents.DirectoryContents)pathContents;
/* 241 */       LinkFSPath $$2 = $$1.children().get($$0);
/* 242 */       return ($$2 != null) ? $$2 : new LinkFSPath(this.fileSystem, $$0, this, PathContents.MISSING); }
/* 243 */      if (this.pathContents instanceof PathContents.FileContents) {
/* 244 */       return new LinkFSPath(this.fileSystem, $$0, this, PathContents.MISSING);
/*     */     }
/*     */     
/* 247 */     throw new AssertionError("All content types should be already handled");
/*     */   }
/*     */   
/*     */   private static boolean isRelativeOrMissing(PathContents $$0) {
/* 251 */     return ($$0 == PathContents.MISSING || $$0 == PathContents.RELATIVE);
/*     */   }
/*     */ 
/*     */   
/*     */   public LinkFSPath relativize(Path $$0) {
/* 256 */     LinkFSPath $$1 = toLinkPath($$0);
/* 257 */     if (isAbsolute() != $$1.isAbsolute()) {
/* 258 */       throw new IllegalArgumentException("absolute mismatch");
/*     */     }
/* 260 */     List<String> $$2 = pathToRoot();
/* 261 */     List<String> $$3 = $$1.pathToRoot();
/*     */ 
/*     */     
/* 264 */     if ($$2.size() >= $$3.size()) {
/* 265 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 268 */     for (int $$4 = 0; $$4 < $$2.size(); $$4++) {
/* 269 */       if (!((String)$$2.get($$4)).equals($$3.get($$4))) {
/* 270 */         throw new IllegalArgumentException();
/*     */       }
/*     */     } 
/*     */     
/* 274 */     return $$1.subpath($$2.size(), $$3.size());
/*     */   }
/*     */ 
/*     */   
/*     */   public URI toUri() {
/*     */     try {
/* 280 */       return new URI("x-mc-link", this.fileSystem.store().name(), pathToString(), null);
/* 281 */     } catch (URISyntaxException $$0) {
/* 282 */       throw new AssertionError("Failed to create URI", $$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public LinkFSPath toAbsolutePath() {
/* 288 */     if (isAbsolute()) {
/* 289 */       return this;
/*     */     }
/*     */     
/* 292 */     return this.fileSystem.rootPath().resolve(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public LinkFSPath toRealPath(LinkOption... $$0) {
/* 297 */     return toAbsolutePath();
/*     */   }
/*     */ 
/*     */   
/*     */   public WatchKey register(WatchService $$0, WatchEvent.Kind<?>[] $$1, WatchEvent.Modifier... $$2) {
/* 302 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(Path $$0) {
/* 307 */     LinkFSPath $$1 = toLinkPath($$0);
/* 308 */     return PATH_COMPARATOR.compare(this, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 313 */     if ($$0 == this) {
/* 314 */       return true;
/*     */     }
/* 316 */     if ($$0 instanceof LinkFSPath) { LinkFSPath $$1 = (LinkFSPath)$$0;
/* 317 */       if (this.fileSystem != $$1.fileSystem) {
/* 318 */         return false;
/*     */       }
/* 320 */       boolean $$2 = hasRealContents();
/* 321 */       if ($$2 != $$1.hasRealContents()) {
/* 322 */         return false;
/*     */       }
/* 324 */       if ($$2)
/*     */       {
/* 326 */         return (this.pathContents == $$1.pathContents);
/*     */       }
/* 328 */       return (Objects.equals(this.parent, $$1.parent) && Objects.equals(this.name, $$1.name)); }
/*     */     
/* 330 */     return false;
/*     */   }
/*     */   
/*     */   private boolean hasRealContents() {
/* 334 */     return !isRelativeOrMissing(this.pathContents);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 339 */     return hasRealContents() ? this.pathContents.hashCode() : this.name.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 344 */     return pathToString();
/*     */   }
/*     */   
/*     */   private String pathToString() {
/* 348 */     if (this.pathString == null) {
/* 349 */       StringBuilder $$0 = new StringBuilder();
/* 350 */       if (isAbsolute()) {
/* 351 */         $$0.append("/");
/*     */       }
/* 353 */       Joiner.on("/").appendTo($$0, pathToRoot());
/* 354 */       this.pathString = $$0.toString();
/*     */     } 
/* 356 */     return this.pathString;
/*     */   }
/*     */   
/*     */   private LinkFSPath toLinkPath(@Nullable Path $$0) {
/* 360 */     if ($$0 == null) {
/* 361 */       throw new NullPointerException();
/*     */     }
/* 363 */     if ($$0 instanceof LinkFSPath) { LinkFSPath $$1 = (LinkFSPath)$$0; if ($$1.fileSystem == this.fileSystem)
/* 364 */         return $$1;  }
/*     */     
/* 366 */     throw new ProviderMismatchException();
/*     */   }
/*     */   
/*     */   public boolean exists() {
/* 370 */     return hasRealContents();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Path getTargetPath() {
/* 375 */     PathContents pathContents = this.pathContents; PathContents.FileContents $$0 = (PathContents.FileContents)pathContents; return (pathContents instanceof PathContents.FileContents) ? $$0.contents() : null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public PathContents.DirectoryContents getDirectoryContents() {
/* 380 */     PathContents pathContents = this.pathContents; PathContents.DirectoryContents $$0 = (PathContents.DirectoryContents)pathContents; return (pathContents instanceof PathContents.DirectoryContents) ? $$0 : null;
/*     */   }
/*     */   
/*     */   public BasicFileAttributeView getBasicAttributeView() {
/* 384 */     return new BasicFileAttributeView()
/*     */       {
/*     */         public String name() {
/* 387 */           return "basic";
/*     */         }
/*     */ 
/*     */         
/*     */         public BasicFileAttributes readAttributes() throws IOException {
/* 392 */           return LinkFSPath.this.getBasicAttributes();
/*     */         }
/*     */ 
/*     */         
/*     */         public void setTimes(FileTime $$0, FileTime $$1, FileTime $$2) {
/* 397 */           throw new ReadOnlyFileSystemException();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public BasicFileAttributes getBasicAttributes() throws IOException {
/* 403 */     if (this.pathContents instanceof PathContents.DirectoryContents) {
/* 404 */       return DIRECTORY_ATTRIBUTES;
/*     */     }
/* 406 */     if (this.pathContents instanceof PathContents.FileContents) {
/* 407 */       return FILE_ATTRIBUTES;
/*     */     }
/* 409 */     throw new NoSuchFileException(pathToString());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\linkfs\LinkFSPath.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */