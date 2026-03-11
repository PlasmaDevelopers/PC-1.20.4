/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.network.FriendlyByteBuf;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ enum ActionType
/*    */ {
/* 70 */   INTERACT(InteractionAction::new),
/* 71 */   ATTACK($$0 -> ServerboundInteractPacket.ATTACK_ACTION),
/* 72 */   INTERACT_AT(InteractionAtLocationAction::new);
/*    */   
/*    */   final Function<FriendlyByteBuf, ServerboundInteractPacket.Action> reader;
/*    */ 
/*    */   
/*    */   ActionType(Function<FriendlyByteBuf, ServerboundInteractPacket.Action> $$0) {
/* 78 */     this.reader = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundInteractPacket$ActionType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */