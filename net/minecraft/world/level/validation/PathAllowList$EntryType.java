/*    */ package net.minecraft.world.level.validation;
/*    */ 
/*    */ import java.nio.file.FileSystem;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.PathMatcher;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface EntryType
/*    */ {
/* 21 */   public static final EntryType FILESYSTEM = FileSystem::getPathMatcher;
/*    */   public static final EntryType PREFIX = ($$0, $$1) -> ();
/*    */   
/*    */   PathMatcher compile(FileSystem paramFileSystem, String paramString);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\validation\PathAllowList$EntryType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */