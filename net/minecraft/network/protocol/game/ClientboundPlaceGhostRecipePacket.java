/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.crafting.RecipeHolder;
/*    */ 
/*    */ public class ClientboundPlaceGhostRecipePacket implements Packet<ClientGamePacketListener> {
/*    */   private final int containerId;
/*    */   
/*    */   public ClientboundPlaceGhostRecipePacket(int $$0, RecipeHolder<?> $$1) {
/* 13 */     this.containerId = $$0;
/* 14 */     this.recipe = $$1.id();
/*    */   }
/*    */   private final ResourceLocation recipe;
/*    */   public ClientboundPlaceGhostRecipePacket(FriendlyByteBuf $$0) {
/* 18 */     this.containerId = $$0.readByte();
/* 19 */     this.recipe = $$0.readResourceLocation();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 24 */     $$0.writeByte(this.containerId);
/* 25 */     $$0.writeResourceLocation(this.recipe);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 30 */     $$0.handlePlaceRecipe(this);
/*    */   }
/*    */   
/*    */   public ResourceLocation getRecipe() {
/* 34 */     return this.recipe;
/*    */   }
/*    */   
/*    */   public int getContainerId() {
/* 38 */     return this.containerId;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundPlaceGhostRecipePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */