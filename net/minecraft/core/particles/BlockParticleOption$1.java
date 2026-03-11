/*    */ package net.minecraft.core.particles;
/*    */ 
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import net.minecraft.commands.arguments.blocks.BlockStateParser;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.IdMap;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements ParticleOptions.Deserializer<BlockParticleOption>
/*    */ {
/*    */   public BlockParticleOption fromCommand(ParticleType<BlockParticleOption> $$0, StringReader $$1) throws CommandSyntaxException {
/* 20 */     $$1.expect(' ');
/* 21 */     return new BlockParticleOption($$0, BlockStateParser.parseForBlock((HolderLookup)BuiltInRegistries.BLOCK.asLookup(), $$1, false).blockState());
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockParticleOption fromNetwork(ParticleType<BlockParticleOption> $$0, FriendlyByteBuf $$1) {
/* 26 */     return new BlockParticleOption($$0, (BlockState)$$1.readById((IdMap)Block.BLOCK_STATE_REGISTRY));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\particles\BlockParticleOption$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */