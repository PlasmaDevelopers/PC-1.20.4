/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.crafting.RecipeHolder;
/*    */ 
/*    */ public class ServerboundPlaceRecipePacket
/*    */   implements Packet<ServerGamePacketListener> {
/*    */   private final int containerId;
/*    */   
/*    */   public ServerboundPlaceRecipePacket(int $$0, RecipeHolder<?> $$1, boolean $$2) {
/* 14 */     this.containerId = $$0;
/* 15 */     this.recipe = $$1.id();
/* 16 */     this.shiftDown = $$2;
/*    */   }
/*    */   private final ResourceLocation recipe; private final boolean shiftDown;
/*    */   public ServerboundPlaceRecipePacket(FriendlyByteBuf $$0) {
/* 20 */     this.containerId = $$0.readByte();
/* 21 */     this.recipe = $$0.readResourceLocation();
/* 22 */     this.shiftDown = $$0.readBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 27 */     $$0.writeByte(this.containerId);
/* 28 */     $$0.writeResourceLocation(this.recipe);
/* 29 */     $$0.writeBoolean(this.shiftDown);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 34 */     $$0.handlePlaceRecipe(this);
/*    */   }
/*    */   
/*    */   public int getContainerId() {
/* 38 */     return this.containerId;
/*    */   }
/*    */   
/*    */   public ResourceLocation getRecipe() {
/* 42 */     return this.recipe;
/*    */   }
/*    */   
/*    */   public boolean isShiftDown() {
/* 46 */     return this.shiftDown;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundPlaceRecipePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */