/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Type
/*    */ {
/* 11 */   static final Int2ObjectMap<Type> TYPES = (Int2ObjectMap<Type>)new Int2ObjectOpenHashMap();
/*    */   
/*    */   final int id;
/*    */   
/*    */   public Type(int $$0) {
/* 16 */     this.id = $$0;
/* 17 */     TYPES.put($$0, this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundGameEventPacket$Type.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */