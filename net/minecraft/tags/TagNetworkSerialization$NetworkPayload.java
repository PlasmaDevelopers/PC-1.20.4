/*    */ package net.minecraft.tags;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.IntList;
/*    */ import java.util.Map;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.resources.ResourceLocation;
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
/*    */ public final class NetworkPayload
/*    */ {
/*    */   final Map<ResourceLocation, IntList> tags;
/*    */   
/*    */   NetworkPayload(Map<ResourceLocation, IntList> $$0) {
/* 63 */     this.tags = $$0;
/*    */   }
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 67 */     $$0.writeMap(this.tags, FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::writeIntIdList);
/*    */   }
/*    */   
/*    */   public static NetworkPayload read(FriendlyByteBuf $$0) {
/* 71 */     return new NetworkPayload($$0.readMap(FriendlyByteBuf::readResourceLocation, FriendlyByteBuf::readIntIdList));
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 75 */     return this.tags.isEmpty();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\tags\TagNetworkSerialization$NetworkPayload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */