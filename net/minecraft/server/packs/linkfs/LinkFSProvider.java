/*     */ package net.minecraft.server.packs.linkfs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.nio.channels.SeekableByteChannel;
/*     */ import java.nio.file.AccessDeniedException;
/*     */ import java.nio.file.AccessMode;
/*     */ import java.nio.file.CopyOption;
/*     */ import java.nio.file.DirectoryIteratorException;
/*     */ import java.nio.file.DirectoryStream;
/*     */ import java.nio.file.FileStore;
/*     */ import java.nio.file.FileSystem;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.LinkOption;
/*     */ import java.nio.file.NoSuchFileException;
/*     */ import java.nio.file.NotDirectoryException;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.ProviderMismatchException;
/*     */ import java.nio.file.ReadOnlyFileSystemException;
/*     */ import java.nio.file.StandardOpenOption;
/*     */ import java.nio.file.attribute.BasicFileAttributeView;
/*     */ import java.nio.file.attribute.BasicFileAttributes;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.nio.file.spi.FileSystemProvider;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ class LinkFSProvider
/*     */   extends FileSystemProvider
/*     */ {
/*     */   public static final String SCHEME = "x-mc-link";
/*     */   
/*     */   public String getScheme() {
/*  37 */     return "x-mc-link";
/*     */   }
/*     */ 
/*     */   
/*     */   public FileSystem newFileSystem(URI $$0, Map<String, ?> $$1) {
/*  42 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public FileSystem getFileSystem(URI $$0) {
/*  47 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Path getPath(URI $$0) {
/*  52 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public SeekableByteChannel newByteChannel(Path $$0, Set<? extends OpenOption> $$1, FileAttribute<?>... $$2) throws IOException {
/*  57 */     if ($$1.contains(StandardOpenOption.CREATE_NEW) || $$1
/*  58 */       .contains(StandardOpenOption.CREATE) || $$1
/*  59 */       .contains(StandardOpenOption.APPEND) || $$1
/*  60 */       .contains(StandardOpenOption.WRITE))
/*     */     {
/*  62 */       throw new UnsupportedOperationException();
/*     */     }
/*  64 */     Path $$3 = toLinkPath($$0).toAbsolutePath().getTargetPath();
/*  65 */     if ($$3 == null) {
/*  66 */       throw new NoSuchFileException($$0.toString());
/*     */     }
/*  68 */     return Files.newByteChannel($$3, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public DirectoryStream<Path> newDirectoryStream(Path $$0, final DirectoryStream.Filter<? super Path> filter) throws IOException {
/*  73 */     final PathContents.DirectoryContents directoryContents = toLinkPath($$0).toAbsolutePath().getDirectoryContents();
/*  74 */     if ($$2 == null) {
/*  75 */       throw new NotDirectoryException($$0.toString());
/*     */     }
/*     */     
/*  78 */     return new DirectoryStream<Path>()
/*     */       {
/*     */         public Iterator<Path> iterator() {
/*  81 */           return directoryContents.children().values()
/*  82 */             .stream()
/*  83 */             .filter($$1 -> {
/*     */                 try {
/*     */                   return $$0.accept($$1);
/*  86 */                 } catch (IOException $$2) {
/*     */                   
/*     */                   throw new DirectoryIteratorException($$2);
/*     */                 } 
/*  90 */               }).map($$0 -> $$0)
/*  91 */             .iterator();
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void close() {}
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public void createDirectory(Path $$0, FileAttribute<?>... $$1) {
/* 102 */     throw new ReadOnlyFileSystemException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void delete(Path $$0) {
/* 107 */     throw new ReadOnlyFileSystemException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void copy(Path $$0, Path $$1, CopyOption... $$2) {
/* 112 */     throw new ReadOnlyFileSystemException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(Path $$0, Path $$1, CopyOption... $$2) {
/* 117 */     throw new ReadOnlyFileSystemException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSameFile(Path $$0, Path $$1) {
/* 122 */     return ($$0 instanceof LinkFSPath && $$1 instanceof LinkFSPath && $$0.equals($$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHidden(Path $$0) {
/* 127 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public FileStore getFileStore(Path $$0) {
/* 132 */     return toLinkPath($$0).getFileSystem().store();
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkAccess(Path $$0, AccessMode... $$1) throws IOException {
/* 137 */     if ($$1.length == 0 && 
/* 138 */       !toLinkPath($$0).exists()) {
/* 139 */       throw new NoSuchFileException($$0.toString());
/*     */     }
/*     */ 
/*     */     
/* 143 */     for (AccessMode $$2 : $$1) {
/* 144 */       switch ($$2) {
/*     */         case READ:
/* 146 */           if (!toLinkPath($$0).exists())
/* 147 */             throw new NoSuchFileException($$0.toString());  break;
/*     */         case EXECUTE:
/*     */         case WRITE:
/* 150 */           throw new AccessDeniedException($$2.toString());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <V extends java.nio.file.attribute.FileAttributeView> V getFileAttributeView(Path $$0, Class<V> $$1, LinkOption... $$2) {
/* 159 */     LinkFSPath $$3 = toLinkPath($$0);
/* 160 */     if ($$1 == BasicFileAttributeView.class) {
/* 161 */       return (V)$$3.getBasicAttributeView();
/*     */     }
/* 163 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <A extends BasicFileAttributes> A readAttributes(Path $$0, Class<A> $$1, LinkOption... $$2) throws IOException {
/* 169 */     LinkFSPath $$3 = toLinkPath($$0).toAbsolutePath();
/* 170 */     if ($$1 == BasicFileAttributes.class) {
/* 171 */       return (A)$$3.getBasicAttributes();
/*     */     }
/* 173 */     throw new UnsupportedOperationException("Attributes of type " + $$1.getName() + " not supported");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> readAttributes(Path $$0, String $$1, LinkOption... $$2) {
/* 179 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAttribute(Path $$0, String $$1, Object $$2, LinkOption... $$3) {
/* 184 */     throw new ReadOnlyFileSystemException();
/*     */   }
/*     */   
/*     */   private static LinkFSPath toLinkPath(@Nullable Path $$0) {
/* 188 */     if ($$0 == null) {
/* 189 */       throw new NullPointerException();
/*     */     }
/* 191 */     if ($$0 instanceof LinkFSPath) { LinkFSPath $$1 = (LinkFSPath)$$0;
/* 192 */       return $$1; }
/*     */     
/* 194 */     throw new ProviderMismatchException();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\linkfs\LinkFSProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */