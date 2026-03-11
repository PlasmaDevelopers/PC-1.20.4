/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import java.util.function.Function;
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
/*    */ enum OperationType
/*    */ {
/* 86 */   ADD(AddOperation::new),
/* 87 */   REMOVE($$0 -> ClientboundBossEventPacket.REMOVE_OPERATION),
/* 88 */   UPDATE_PROGRESS(UpdateProgressOperation::new),
/* 89 */   UPDATE_NAME(UpdateNameOperation::new),
/* 90 */   UPDATE_STYLE(UpdateStyleOperation::new),
/* 91 */   UPDATE_PROPERTIES(UpdatePropertiesOperation::new);
/*    */   
/*    */   final Function<FriendlyByteBuf, ClientboundBossEventPacket.Operation> reader;
/*    */ 
/*    */   
/*    */   OperationType(Function<FriendlyByteBuf, ClientboundBossEventPacket.Operation> $$0) {
/* 97 */     this.reader = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundBossEventPacket$OperationType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */