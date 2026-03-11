/*    */ package net.minecraft.core.particles;
/*    */ 
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import net.minecraft.commands.arguments.item.ItemInput;
/*    */ import net.minecraft.commands.arguments.item.ItemParser;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements ParticleOptions.Deserializer<ItemParticleOption>
/*    */ {
/*    */   public ItemParticleOption fromCommand(ParticleType<ItemParticleOption> $$0, StringReader $$1) throws CommandSyntaxException {
/* 20 */     $$1.expect(' ');
/* 21 */     ItemParser.ItemResult $$2 = ItemParser.parseForItem((HolderLookup)BuiltInRegistries.ITEM.asLookup(), $$1);
/* 22 */     ItemStack $$3 = (new ItemInput($$2.item(), $$2.nbt())).createItemStack(1, false);
/* 23 */     return new ItemParticleOption($$0, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemParticleOption fromNetwork(ParticleType<ItemParticleOption> $$0, FriendlyByteBuf $$1) {
/* 28 */     return new ItemParticleOption($$0, $$1.readItem());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\particles\ItemParticleOption$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */