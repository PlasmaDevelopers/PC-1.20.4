/*    */ package net.minecraft.data;
/*    */ 
/*    */ import com.google.common.hash.HashCode;
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import net.minecraft.FileUtil;
/*    */ 
/*    */ public interface CachedOutput {
/*    */   static {
/* 11 */     NO_CACHE = (($$0, $$1, $$2) -> {
/*    */         FileUtil.createDirectoriesSafe($$0.getParent());
/*    */         Files.write($$0, $$1, new java.nio.file.OpenOption[0]);
/*    */       });
/*    */   }
/*    */   
/*    */   public static final CachedOutput NO_CACHE;
/*    */   
/*    */   void writeIfNeeded(Path paramPath, byte[] paramArrayOfbyte, HashCode paramHashCode) throws IOException;
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\CachedOutput.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */