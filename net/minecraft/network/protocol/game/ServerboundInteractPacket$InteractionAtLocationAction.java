/*     */ package net.minecraft.network.protocol.game;
/*     */ 
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.phys.Vec3;
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
/*     */ class InteractionAtLocationAction
/*     */   implements ServerboundInteractPacket.Action
/*     */ {
/*     */   private final InteractionHand hand;
/*     */   private final Vec3 location;
/*     */   
/*     */   InteractionAtLocationAction(InteractionHand $$0, Vec3 $$1) {
/* 130 */     this.hand = $$0;
/* 131 */     this.location = $$1;
/*     */   }
/*     */   
/*     */   private InteractionAtLocationAction(FriendlyByteBuf $$0) {
/* 135 */     this.location = new Vec3($$0.readFloat(), $$0.readFloat(), $$0.readFloat());
/* 136 */     this.hand = (InteractionHand)$$0.readEnum(InteractionHand.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerboundInteractPacket.ActionType getType() {
/* 141 */     return ServerboundInteractPacket.ActionType.INTERACT_AT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispatch(ServerboundInteractPacket.Handler $$0) {
/* 146 */     $$0.onInteraction(this.hand, this.location);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/* 151 */     $$0.writeFloat((float)this.location.x);
/* 152 */     $$0.writeFloat((float)this.location.y);
/* 153 */     $$0.writeFloat((float)this.location.z);
/* 154 */     $$0.writeEnum((Enum)this.hand);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundInteractPacket$InteractionAtLocationAction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */