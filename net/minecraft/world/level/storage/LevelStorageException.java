/*    */ package net.minecraft.world.level.storage;
/*    */ 
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class LevelStorageException extends RuntimeException {
/*    */   private final Component messageComponent;
/*    */   
/*    */   public LevelStorageException(Component $$0) {
/*  9 */     super($$0.getString());
/* 10 */     this.messageComponent = $$0;
/*    */   }
/*    */   
/*    */   public Component getMessageComponent() {
/* 14 */     return this.messageComponent;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\LevelStorageException.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */