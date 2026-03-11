/*    */ package net.minecraft.core.particles;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.commands.arguments.item.ItemInput;
/*    */ import net.minecraft.commands.arguments.item.ItemParser;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class ItemParticleOption implements ParticleOptions {
/*    */   public static Codec<ItemParticleOption> codec(ParticleType<ItemParticleOption> $$0) {
/* 14 */     return ItemStack.CODEC.xmap($$1 -> new ItemParticleOption($$0, $$1), $$0 -> $$0.itemStack);
/*    */   }
/*    */   
/* 17 */   public static final ParticleOptions.Deserializer<ItemParticleOption> DESERIALIZER = new ParticleOptions.Deserializer<ItemParticleOption>()
/*    */     {
/*    */       public ItemParticleOption fromCommand(ParticleType<ItemParticleOption> $$0, StringReader $$1) throws CommandSyntaxException {
/* 20 */         $$1.expect(' ');
/* 21 */         ItemParser.ItemResult $$2 = ItemParser.parseForItem((HolderLookup)BuiltInRegistries.ITEM.asLookup(), $$1);
/* 22 */         ItemStack $$3 = (new ItemInput($$2.item(), $$2.nbt())).createItemStack(1, false);
/* 23 */         return new ItemParticleOption($$0, $$3);
/*    */       }
/*    */ 
/*    */       
/*    */       public ItemParticleOption fromNetwork(ParticleType<ItemParticleOption> $$0, FriendlyByteBuf $$1) {
/* 28 */         return new ItemParticleOption($$0, $$1.readItem());
/*    */       }
/*    */     };
/*    */   
/*    */   private final ParticleType<ItemParticleOption> type;
/*    */   private final ItemStack itemStack;
/*    */   
/*    */   public ItemParticleOption(ParticleType<ItemParticleOption> $$0, ItemStack $$1) {
/* 36 */     this.type = $$0;
/* 37 */     this.itemStack = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToNetwork(FriendlyByteBuf $$0) {
/* 42 */     $$0.writeItem(this.itemStack);
/*    */   }
/*    */ 
/*    */   
/*    */   public String writeToString() {
/* 47 */     return "" + BuiltInRegistries.PARTICLE_TYPE.getKey(getType()) + " " + BuiltInRegistries.PARTICLE_TYPE.getKey(getType());
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleType<ItemParticleOption> getType() {
/* 52 */     return this.type;
/*    */   }
/*    */   
/*    */   public ItemStack getItem() {
/* 56 */     return this.itemStack;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\particles\ItemParticleOption.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */