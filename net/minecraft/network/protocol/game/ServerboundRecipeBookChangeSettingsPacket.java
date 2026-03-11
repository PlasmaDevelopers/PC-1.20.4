/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.inventory.RecipeBookType;
/*    */ 
/*    */ public class ServerboundRecipeBookChangeSettingsPacket
/*    */   implements Packet<ServerGamePacketListener> {
/*    */   private final RecipeBookType bookType;
/*    */   
/*    */   public ServerboundRecipeBookChangeSettingsPacket(RecipeBookType $$0, boolean $$1, boolean $$2) {
/* 13 */     this.bookType = $$0;
/* 14 */     this.isOpen = $$1;
/* 15 */     this.isFiltering = $$2;
/*    */   }
/*    */   private final boolean isOpen; private final boolean isFiltering;
/*    */   public ServerboundRecipeBookChangeSettingsPacket(FriendlyByteBuf $$0) {
/* 19 */     this.bookType = (RecipeBookType)$$0.readEnum(RecipeBookType.class);
/* 20 */     this.isOpen = $$0.readBoolean();
/* 21 */     this.isFiltering = $$0.readBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 26 */     $$0.writeEnum((Enum)this.bookType);
/* 27 */     $$0.writeBoolean(this.isOpen);
/* 28 */     $$0.writeBoolean(this.isFiltering);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 33 */     $$0.handleRecipeBookChangeSettingsPacket(this);
/*    */   }
/*    */   
/*    */   public RecipeBookType getBookType() {
/* 37 */     return this.bookType;
/*    */   }
/*    */   
/*    */   public boolean isOpen() {
/* 41 */     return this.isOpen;
/*    */   }
/*    */   
/*    */   public boolean isFiltering() {
/* 45 */     return this.isFiltering;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundRecipeBookChangeSettingsPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */