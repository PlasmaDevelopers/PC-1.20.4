/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.crafting.RecipeHolder;
/*    */ 
/*    */ public class ServerboundRecipeBookSeenRecipePacket
/*    */   implements Packet<ServerGamePacketListener> {
/*    */   public ServerboundRecipeBookSeenRecipePacket(RecipeHolder<?> $$0) {
/* 12 */     this.recipe = $$0.id();
/*    */   }
/*    */   private final ResourceLocation recipe;
/*    */   public ServerboundRecipeBookSeenRecipePacket(FriendlyByteBuf $$0) {
/* 16 */     this.recipe = $$0.readResourceLocation();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 21 */     $$0.writeResourceLocation(this.recipe);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ServerGamePacketListener $$0) {
/* 26 */     $$0.handleRecipeBookSeenRecipePacket(this);
/*    */   }
/*    */   
/*    */   public ResourceLocation getRecipe() {
/* 30 */     return this.recipe;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ServerboundRecipeBookSeenRecipePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */