/*    */ package net.minecraft.world.level.validation;
/*    */ 
/*    */ import java.nio.file.Path;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
/*    */ 
/*    */ public class ContentValidationException extends Exception {
/*    */   private final Path directory;
/*    */   private final List<ForbiddenSymlinkInfo> entries;
/*    */   
/*    */   public ContentValidationException(Path $$0, List<ForbiddenSymlinkInfo> $$1) {
/* 12 */     this.directory = $$0;
/* 13 */     this.entries = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 18 */     return getMessage(this.directory, this.entries);
/*    */   }
/*    */   
/*    */   public static String getMessage(Path $$0, List<ForbiddenSymlinkInfo> $$1) {
/* 22 */     return "Failed to validate '" + $$0 + "'. Found forbidden symlinks: " + (String)$$1.stream().map($$0 -> "" + $$0.link() + "->" + $$0.link()).collect(Collectors.joining(", "));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\validation\ContentValidationException.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */