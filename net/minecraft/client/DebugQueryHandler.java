/*    */ package net.minecraft.client;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.multiplayer.ClientPacketListener;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.game.ServerboundBlockEntityTagQuery;
/*    */ import net.minecraft.network.protocol.game.ServerboundEntityTagQuery;
/*    */ 
/*    */ public class DebugQueryHandler
/*    */ {
/*    */   private final ClientPacketListener connection;
/* 15 */   private int transactionId = -1;
/*    */   
/*    */   @Nullable
/*    */   private Consumer<CompoundTag> callback;
/*    */   
/*    */   public DebugQueryHandler(ClientPacketListener $$0) {
/* 21 */     this.connection = $$0;
/*    */   }
/*    */   
/*    */   public boolean handleResponse(int $$0, @Nullable CompoundTag $$1) {
/* 25 */     if (this.transactionId == $$0 && this.callback != null) {
/* 26 */       this.callback.accept($$1);
/* 27 */       this.callback = null;
/* 28 */       return true;
/*    */     } 
/*    */     
/* 31 */     return false;
/*    */   }
/*    */   
/*    */   private int startTransaction(Consumer<CompoundTag> $$0) {
/* 35 */     this.callback = $$0;
/* 36 */     return ++this.transactionId;
/*    */   }
/*    */   
/*    */   public void queryEntityTag(int $$0, Consumer<CompoundTag> $$1) {
/* 40 */     int $$2 = startTransaction($$1);
/* 41 */     this.connection.send((Packet)new ServerboundEntityTagQuery($$2, $$0));
/*    */   }
/*    */   
/*    */   public void queryBlockEntityTag(BlockPos $$0, Consumer<CompoundTag> $$1) {
/* 45 */     int $$2 = startTransaction($$1);
/* 46 */     this.connection.send((Packet)new ServerboundBlockEntityTagQuery($$2, $$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\DebugQueryHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */