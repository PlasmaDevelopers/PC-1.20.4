/*    */ package net.minecraft.network.protocol.game;
/*    */ import java.util.List;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.stats.RecipeBookSettings;
/*    */ 
/*    */ public class ClientboundRecipePacket implements Packet<ClientGamePacketListener> {
/*    */   private final State state;
/*    */   private final List<ResourceLocation> recipes;
/*    */   private final List<ResourceLocation> toHighlight;
/*    */   private final RecipeBookSettings bookSettings;
/*    */   
/*    */   public enum State {
/* 14 */     INIT, ADD, REMOVE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ClientboundRecipePacket(State $$0, Collection<ResourceLocation> $$1, Collection<ResourceLocation> $$2, RecipeBookSettings $$3) {
/* 24 */     this.state = $$0;
/* 25 */     this.recipes = (List<ResourceLocation>)ImmutableList.copyOf($$1);
/* 26 */     this.toHighlight = (List<ResourceLocation>)ImmutableList.copyOf($$2);
/* 27 */     this.bookSettings = $$3;
/*    */   }
/*    */   
/*    */   public ClientboundRecipePacket(FriendlyByteBuf $$0) {
/* 31 */     this.state = (State)$$0.readEnum(State.class);
/*    */     
/* 33 */     this.bookSettings = RecipeBookSettings.read($$0);
/*    */     
/* 35 */     this.recipes = $$0.readList(FriendlyByteBuf::readResourceLocation);
/*    */     
/* 37 */     if (this.state == State.INIT) {
/* 38 */       this.toHighlight = $$0.readList(FriendlyByteBuf::readResourceLocation);
/*    */     } else {
/* 40 */       this.toHighlight = (List<ResourceLocation>)ImmutableList.of();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 46 */     $$0.writeEnum(this.state);
/*    */     
/* 48 */     this.bookSettings.write($$0);
/*    */     
/* 50 */     $$0.writeCollection(this.recipes, FriendlyByteBuf::writeResourceLocation);
/*    */     
/* 52 */     if (this.state == State.INIT) {
/* 53 */       $$0.writeCollection(this.toHighlight, FriendlyByteBuf::writeResourceLocation);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 59 */     $$0.handleAddOrRemoveRecipes(this);
/*    */   }
/*    */   
/*    */   public List<ResourceLocation> getRecipes() {
/* 63 */     return this.recipes;
/*    */   }
/*    */   
/*    */   public List<ResourceLocation> getHighlights() {
/* 67 */     return this.toHighlight;
/*    */   }
/*    */   
/*    */   public RecipeBookSettings getBookSettings() {
/* 71 */     return this.bookSettings;
/*    */   }
/*    */   
/*    */   public State getState() {
/* 75 */     return this.state;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundRecipePacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */