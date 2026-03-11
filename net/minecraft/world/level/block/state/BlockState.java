/*    */ package net.minecraft.world.level.block.state;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ 
/*    */ public class BlockState
/*    */   extends BlockBehaviour.BlockStateBase
/*    */ {
/* 14 */   public static final Codec<BlockState> CODEC = codec(BuiltInRegistries.BLOCK.byNameCodec(), Block::defaultBlockState).stable();
/*    */   
/*    */   public BlockState(Block $$0, ImmutableMap<Property<?>, Comparable<?>> $$1, MapCodec<BlockState> $$2) {
/* 17 */     super($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockState asState() {
/* 22 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\BlockState.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */