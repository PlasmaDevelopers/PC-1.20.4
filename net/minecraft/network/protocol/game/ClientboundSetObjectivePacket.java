/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.numbers.NumberFormat;
/*    */ import net.minecraft.network.chat.numbers.NumberFormatTypes;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.scores.Objective;
/*    */ import net.minecraft.world.scores.criteria.ObjectiveCriteria;
/*    */ 
/*    */ public class ClientboundSetObjectivePacket
/*    */   implements Packet<ClientGamePacketListener> {
/*    */   public static final int METHOD_ADD = 0;
/*    */   public static final int METHOD_REMOVE = 1;
/*    */   public static final int METHOD_CHANGE = 2;
/*    */   private final String objectiveName;
/*    */   private final Component displayName;
/*    */   private final ObjectiveCriteria.RenderType renderType;
/*    */   @Nullable
/*    */   private final NumberFormat numberFormat;
/*    */   private final int method;
/*    */   
/*    */   public ClientboundSetObjectivePacket(Objective $$0, int $$1) {
/* 27 */     this.objectiveName = $$0.getName();
/* 28 */     this.displayName = $$0.getDisplayName();
/* 29 */     this.renderType = $$0.getRenderType();
/* 30 */     this.numberFormat = $$0.numberFormat();
/* 31 */     this.method = $$1;
/*    */   }
/*    */   
/*    */   public ClientboundSetObjectivePacket(FriendlyByteBuf $$0) {
/* 35 */     this.objectiveName = $$0.readUtf();
/* 36 */     this.method = $$0.readByte();
/*    */     
/* 38 */     if (this.method == 0 || this.method == 2) {
/* 39 */       this.displayName = $$0.readComponentTrusted();
/* 40 */       this.renderType = (ObjectiveCriteria.RenderType)$$0.readEnum(ObjectiveCriteria.RenderType.class);
/* 41 */       this.numberFormat = (NumberFormat)$$0.readNullable(NumberFormatTypes::readFromStream);
/*    */     } else {
/* 43 */       this.displayName = CommonComponents.EMPTY;
/* 44 */       this.renderType = ObjectiveCriteria.RenderType.INTEGER;
/* 45 */       this.numberFormat = null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 51 */     $$0.writeUtf(this.objectiveName);
/* 52 */     $$0.writeByte(this.method);
/*    */     
/* 54 */     if (this.method == 0 || this.method == 2) {
/* 55 */       $$0.writeComponent(this.displayName);
/* 56 */       $$0.writeEnum((Enum)this.renderType);
/* 57 */       $$0.writeNullable(this.numberFormat, NumberFormatTypes::writeToStream);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 63 */     $$0.handleAddObjective(this);
/*    */   }
/*    */   
/*    */   public String getObjectiveName() {
/* 67 */     return this.objectiveName;
/*    */   }
/*    */   
/*    */   public Component getDisplayName() {
/* 71 */     return this.displayName;
/*    */   }
/*    */   
/*    */   public int getMethod() {
/* 75 */     return this.method;
/*    */   }
/*    */   
/*    */   public ObjectiveCriteria.RenderType getRenderType() {
/* 79 */     return this.renderType;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public NumberFormat getNumberFormat() {
/* 84 */     return this.numberFormat;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundSetObjectivePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */