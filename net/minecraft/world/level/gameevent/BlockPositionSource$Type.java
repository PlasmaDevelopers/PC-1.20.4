/*    */ package net.minecraft.world.level.gameevent;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Type
/*    */   implements PositionSourceType<BlockPositionSource>
/*    */ {
/*    */   public BlockPositionSource read(FriendlyByteBuf $$0) {
/* 36 */     return new BlockPositionSource($$0.readBlockPos());
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0, BlockPositionSource $$1) {
/* 41 */     $$0.writeBlockPos($$1.pos);
/*    */   }
/*    */ 
/*    */   
/*    */   public Codec<BlockPositionSource> codec() {
/* 46 */     return BlockPositionSource.CODEC;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\gameevent\BlockPositionSource$Type.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */