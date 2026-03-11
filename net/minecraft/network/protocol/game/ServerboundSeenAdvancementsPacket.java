/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.advancements.AdvancementHolder;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class ServerboundSeenAdvancementsPacket implements Packet<ServerGamePacketListener> {
/*    */   private final Action action;
/*    */   @Nullable
/*    */   private final ResourceLocation tab;
/*    */   
/*    */   public ServerboundSeenAdvancementsPacket(Action $$0, @Nullable ResourceLocation $$1) {
/* 16 */     this.action = $$0;
/* 17 */     this.tab = $$1;
/*    */   }
/*    */   
/*    */   public static ServerboundSeenAdvancementsPacket openedTab(AdvancementHolder $$0) {
/* 21 */     return new ServerboundSeenAdvancementsPacket(Action.OPENED_TAB, $$0.id());
/*    */   }
/*    */   
/*    */   public static ServerboundSeenAdvancementsPacket closedScreen() {
/* 25 */     return new ServerboundSeenAdvancementsPacket(Action.CLOSED_SCREEN, null);
/*    */   }
/*    */   
/*    */   public ServerboundSeenAdvancementsPacket(FriendlyByteBuf $$0) {
/* 29 */     this.action = (Action)$$0.readEnum(Action.class);
/* 30 */     if (this.action == Action.OPENED_TAB) {
/* 31 */       this.tab = $$0.readResourceLocation();
/*    */     } else {
/* 33 */       this.tab = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 39 */     $$0.writeEnum(this.action);
/* 40 */     if (this.action == Action.OPENED_TAB) {
/* 41 */       $$0.writeResourceLocation(this.tab);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 47 */     $$0.handleSeenAdvancements(this);
/*    */   }
/*    */   
/*    */   public Action getAction() {
/* 51 */     return this.action;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public ResourceLocation getTab() {
/* 56 */     return this.tab;
/*    */   }
/*    */   
/*    */   public enum Action {
/* 60 */     OPENED_TAB,
/* 61 */     CLOSED_SCREEN;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundSeenAdvancementsPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */