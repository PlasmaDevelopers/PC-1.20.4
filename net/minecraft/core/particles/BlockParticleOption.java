/*    */ package net.minecraft.core.particles;
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
/*    */ public class BlockParticleOption implements ParticleOptions {
/*    */   public static Codec<BlockParticleOption> codec(ParticleType<BlockParticleOption> $$0) {
/* 14 */     return BlockState.CODEC.xmap($$1 -> new BlockParticleOption($$0, $$1), $$0 -> $$0.state);
/*    */   }
/*    */   
/* 17 */   public static final ParticleOptions.Deserializer<BlockParticleOption> DESERIALIZER = new ParticleOptions.Deserializer<BlockParticleOption>()
/*    */     {
/*    */       public BlockParticleOption fromCommand(ParticleType<BlockParticleOption> $$0, StringReader $$1) throws CommandSyntaxException {
/* 20 */         $$1.expect(' ');
/* 21 */         return new BlockParticleOption($$0, BlockStateParser.parseForBlock((HolderLookup)BuiltInRegistries.BLOCK.asLookup(), $$1, false).blockState());
/*    */       }
/*    */ 
/*    */       
/*    */       public BlockParticleOption fromNetwork(ParticleType<BlockParticleOption> $$0, FriendlyByteBuf $$1) {
/* 26 */         return new BlockParticleOption($$0, (BlockState)$$1.readById((IdMap)Block.BLOCK_STATE_REGISTRY));
/*    */       }
/*    */     };
/*    */   
/*    */   private final ParticleType<BlockParticleOption> type;
/*    */   private final BlockState state;
/*    */   
/*    */   public BlockParticleOption(ParticleType<BlockParticleOption> $$0, BlockState $$1) {
/* 34 */     this.type = $$0;
/* 35 */     this.state = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToNetwork(FriendlyByteBuf $$0) {
/* 40 */     $$0.writeId((IdMap)Block.BLOCK_STATE_REGISTRY, this.state);
/*    */   }
/*    */ 
/*    */   
/*    */   public String writeToString() {
/* 45 */     return "" + BuiltInRegistries.PARTICLE_TYPE.getKey(getType()) + " " + BuiltInRegistries.PARTICLE_TYPE.getKey(getType());
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleType<BlockParticleOption> getType() {
/* 50 */     return this.type;
/*    */   }
/*    */   
/*    */   public BlockState getState() {
/* 54 */     return this.state;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\particles\BlockParticleOption.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */