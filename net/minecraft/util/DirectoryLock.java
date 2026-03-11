/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.base.Charsets;
/*    */ import java.io.IOException;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.channels.FileChannel;
/*    */ import java.nio.channels.FileLock;
/*    */ import java.nio.file.AccessDeniedException;
/*    */ import java.nio.file.NoSuchFileException;
/*    */ import java.nio.file.OpenOption;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.StandardOpenOption;
/*    */ import net.minecraft.FileUtil;
/*    */ 
/*    */ public class DirectoryLock
/*    */   implements AutoCloseable
/*    */ {
/*    */   public static final String LOCK_FILE = "session.lock";
/*    */   private final FileChannel lockFile;
/*    */   private final FileLock lock;
/*    */   private static final ByteBuffer DUMMY;
/*    */   
/*    */   static {
/* 24 */     byte[] $$0 = "☃".getBytes(Charsets.UTF_8);
/* 25 */     DUMMY = ByteBuffer.allocateDirect($$0.length);
/* 26 */     DUMMY.put($$0);
/* 27 */     DUMMY.flip();
/*    */   }
/*    */   
/*    */   public static DirectoryLock create(Path $$0) throws IOException {
/* 31 */     Path $$1 = $$0.resolve("session.lock");
/*    */     
/* 33 */     FileUtil.createDirectoriesSafe($$0);
/* 34 */     FileChannel $$2 = FileChannel.open($$1, new OpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.WRITE });
/*    */     
/*    */     try {
/* 37 */       $$2.write(DUMMY.duplicate());
/* 38 */       $$2.force(true);
/* 39 */       FileLock $$3 = $$2.tryLock();
/* 40 */       if ($$3 == null) {
/* 41 */         throw LockException.alreadyLocked($$1);
/*    */       }
/* 43 */       return new DirectoryLock($$2, $$3);
/* 44 */     } catch (IOException $$4) {
/*    */       try {
/* 46 */         $$2.close();
/* 47 */       } catch (IOException $$5) {
/* 48 */         $$4.addSuppressed($$5);
/*    */       } 
/* 50 */       throw $$4;
/*    */     } 
/*    */   }
/*    */   
/*    */   private DirectoryLock(FileChannel $$0, FileLock $$1) {
/* 55 */     this.lockFile = $$0;
/* 56 */     this.lock = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/*    */     try {
/* 62 */       if (this.lock.isValid()) {
/* 63 */         this.lock.release();
/*    */       }
/*    */     } finally {
/* 66 */       if (this.lockFile.isOpen()) {
/* 67 */         this.lockFile.close();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isValid() {
/* 73 */     return this.lock.isValid();
/*    */   }
/*    */   
/*    */   public static boolean isLocked(Path $$0) throws IOException {
/* 77 */     Path $$1 = $$0.resolve("session.lock");
/*    */     
/* 79 */     try { FileChannel $$2 = FileChannel.open($$1, new OpenOption[] { StandardOpenOption.WRITE }); 
/* 80 */       try { FileLock $$3 = $$2.tryLock(); 
/* 81 */         try { boolean bool = ($$3 == null) ? true : false;
/* 82 */           if ($$3 != null) $$3.close();  if ($$2 != null) $$2.close();  return bool; } catch (Throwable throwable) { if ($$3 != null) try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable throwable) { if ($$2 != null) try { $$2.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (AccessDeniedException $$4)
/* 83 */     { return true; }
/* 84 */     catch (NoSuchFileException $$5)
/* 85 */     { return false; }
/*    */   
/*    */   }
/*    */   
/*    */   public static class LockException extends IOException {
/*    */     private LockException(Path $$0, String $$1) {
/* 91 */       super("" + $$0.toAbsolutePath() + ": " + $$0.toAbsolutePath());
/*    */     }
/*    */     
/*    */     public static LockException alreadyLocked(Path $$0) {
/* 95 */       return new LockException($$0, "already locked (possibly by other Minecraft instance?)");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\DirectoryLock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */