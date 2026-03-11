/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.crafting.Recipe;
/*    */ import net.minecraft.world.item.crafting.RecipeHolder;
/*    */ import net.minecraft.world.item.crafting.RecipeSerializer;
/*    */ 
/*    */ public class ClientboundUpdateRecipesPacket implements Packet<ClientGamePacketListener> {
/*    */   private final List<RecipeHolder<?>> recipes;
/*    */   
/*    */   public ClientboundUpdateRecipesPacket(Collection<RecipeHolder<?>> $$0) {
/* 19 */     this.recipes = Lists.newArrayList($$0);
/*    */   }
/*    */   
/*    */   public ClientboundUpdateRecipesPacket(FriendlyByteBuf $$0) {
/* 23 */     this.recipes = $$0.readList(ClientboundUpdateRecipesPacket::fromNetwork);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 28 */     $$0.writeCollection(this.recipes, ClientboundUpdateRecipesPacket::toNetwork);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 33 */     $$0.handleUpdateRecipes(this);
/*    */   }
/*    */   
/*    */   public List<RecipeHolder<?>> getRecipes() {
/* 37 */     return this.recipes;
/*    */   }
/*    */   
/*    */   private static RecipeHolder<?> fromNetwork(FriendlyByteBuf $$0) {
/* 41 */     ResourceLocation $$1 = $$0.readResourceLocation();
/* 42 */     ResourceLocation $$2 = $$0.readResourceLocation();
/*    */ 
/*    */ 
/*    */     
/* 46 */     Recipe<?> $$3 = ((RecipeSerializer)BuiltInRegistries.RECIPE_SERIALIZER.getOptional($$1).orElseThrow(() -> new IllegalArgumentException("Unknown recipe serializer " + $$0))).fromNetwork($$0);
/* 47 */     return new RecipeHolder($$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public static <T extends Recipe<?>> void toNetwork(FriendlyByteBuf $$0, RecipeHolder<?> $$1) {
/* 52 */     $$0.writeResourceLocation(BuiltInRegistries.RECIPE_SERIALIZER.getKey($$1.value().getSerializer()));
/* 53 */     $$0.writeResourceLocation($$1.id());
/* 54 */     $$1.value().getSerializer().toNetwork($$0, $$1.value());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundUpdateRecipesPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */