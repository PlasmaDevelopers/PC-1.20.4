/*   */ package net.minecraft.network.chat;
/*   */ 
/*   */ import javax.annotation.Nullable;
/*   */ import net.minecraft.server.level.ServerPlayer;
/*   */ 
/*   */ @FunctionalInterface
/*   */ public interface ChatDecorator {
/*   */   static {
/* 9 */     PLAIN = (($$0, $$1) -> $$1);
/*   */   }
/*   */   
/*   */   public static final ChatDecorator PLAIN;
/*   */   
/*   */   Component decorate(@Nullable ServerPlayer paramServerPlayer, Component paramComponent);
/*   */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\ChatDecorator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */