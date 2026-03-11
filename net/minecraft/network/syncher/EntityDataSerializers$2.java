/*    */ package net.minecraft.network.syncher;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements EntityDataSerializer.ForValueType<Optional<BlockState>>
/*    */ {
/*    */   public void write(FriendlyByteBuf $$0, Optional<BlockState> $$1) {
/* 71 */     if ($$1.isPresent()) {
/* 72 */       $$0.writeVarInt(Block.getId($$1.get()));
/*    */     } else {
/* 74 */       $$0.writeVarInt(0);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<BlockState> read(FriendlyByteBuf $$0) {
/* 80 */     int $$1 = $$0.readVarInt();
/* 81 */     if ($$1 == 0) {
/* 82 */       return Optional.empty();
/*    */     }
/* 84 */     return Optional.of(Block.stateById($$1));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\syncher\EntityDataSerializers$2.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */