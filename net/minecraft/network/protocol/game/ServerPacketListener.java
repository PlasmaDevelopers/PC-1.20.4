/*   */ package net.minecraft.network.protocol.game;
/*   */ 
/*   */ import net.minecraft.network.ServerboundPacketListener;
/*   */ 
/*   */ public interface ServerPacketListener
/*   */   extends ServerboundPacketListener {
/*   */   default boolean shouldPropagateHandlingExceptions() {
/* 8 */     return false;
/*   */   }
/*   */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerPacketListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */