/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.scores.DisplaySlot;
/*    */ import net.minecraft.world.scores.Objective;
/*    */ 
/*    */ public class ClientboundSetDisplayObjectivePacket implements Packet<ClientGamePacketListener> {
/*    */   private final DisplaySlot slot;
/*    */   private final String objectiveName;
/*    */   
/*    */   public ClientboundSetDisplayObjectivePacket(DisplaySlot $$0, @Nullable Objective $$1) {
/* 16 */     this.slot = $$0;
/*    */     
/* 18 */     if ($$1 == null) {
/* 19 */       this.objectiveName = "";
/*    */     } else {
/* 21 */       this.objectiveName = $$1.getName();
/*    */     } 
/*    */   }
/*    */   
/*    */   public ClientboundSetDisplayObjectivePacket(FriendlyByteBuf $$0) {
/* 26 */     this.slot = (DisplaySlot)$$0.readById(DisplaySlot.BY_ID);
/* 27 */     this.objectiveName = $$0.readUtf();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 32 */     $$0.writeById(DisplaySlot::id, this.slot);
/* 33 */     $$0.writeUtf(this.objectiveName);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 38 */     $$0.handleSetDisplayObjective(this);
/*    */   }
/*    */   
/*    */   public DisplaySlot getSlot() {
/* 42 */     return this.slot;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getObjectiveName() {
/* 47 */     return Objects.equals(this.objectiveName, "") ? null : this.objectiveName;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetDisplayObjectivePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */