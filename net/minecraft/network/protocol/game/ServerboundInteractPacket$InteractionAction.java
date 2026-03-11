/*     */ package net.minecraft.network.protocol.game;
/*     */ 
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class InteractionAction
/*     */   implements ServerboundInteractPacket.Action
/*     */ {
/*     */   private final InteractionHand hand;
/*     */   
/*     */   InteractionAction(InteractionHand $$0) {
/* 102 */     this.hand = $$0;
/*     */   }
/*     */   
/*     */   private InteractionAction(FriendlyByteBuf $$0) {
/* 106 */     this.hand = (InteractionHand)$$0.readEnum(InteractionHand.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerboundInteractPacket.ActionType getType() {
/* 111 */     return ServerboundInteractPacket.ActionType.INTERACT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispatch(ServerboundInteractPacket.Handler $$0) {
/* 116 */     $$0.onInteraction(this.hand);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/* 121 */     $$0.writeEnum((Enum)this.hand);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundInteractPacket$InteractionAction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */