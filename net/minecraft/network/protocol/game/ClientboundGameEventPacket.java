/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ 
/*    */ public class ClientboundGameEventPacket implements Packet<ClientGamePacketListener> {
/*    */   public static class Type {
/* 11 */     static final Int2ObjectMap<Type> TYPES = (Int2ObjectMap<Type>)new Int2ObjectOpenHashMap();
/*    */     
/*    */     final int id;
/*    */     
/*    */     public Type(int $$0) {
/* 16 */       this.id = $$0;
/* 17 */       TYPES.put($$0, this);
/*    */     }
/*    */   }
/*    */   
/* 21 */   public static final Type NO_RESPAWN_BLOCK_AVAILABLE = new Type(0);
/* 22 */   public static final Type START_RAINING = new Type(1);
/* 23 */   public static final Type STOP_RAINING = new Type(2);
/* 24 */   public static final Type CHANGE_GAME_MODE = new Type(3);
/* 25 */   public static final Type WIN_GAME = new Type(4);
/* 26 */   public static final Type DEMO_EVENT = new Type(5);
/* 27 */   public static final Type ARROW_HIT_PLAYER = new Type(6);
/* 28 */   public static final Type RAIN_LEVEL_CHANGE = new Type(7);
/* 29 */   public static final Type THUNDER_LEVEL_CHANGE = new Type(8);
/* 30 */   public static final Type PUFFER_FISH_STING = new Type(9);
/* 31 */   public static final Type GUARDIAN_ELDER_EFFECT = new Type(10);
/* 32 */   public static final Type IMMEDIATE_RESPAWN = new Type(11);
/* 33 */   public static final Type LIMITED_CRAFTING = new Type(12);
/* 34 */   public static final Type LEVEL_CHUNKS_LOAD_START = new Type(13);
/*    */   
/*    */   public static final int DEMO_PARAM_INTRO = 0;
/*    */   
/*    */   public static final int DEMO_PARAM_HINT_1 = 101;
/*    */   public static final int DEMO_PARAM_HINT_2 = 102;
/*    */   public static final int DEMO_PARAM_HINT_3 = 103;
/*    */   public static final int DEMO_PARAM_HINT_4 = 104;
/*    */   private final Type event;
/*    */   private final float param;
/*    */   
/*    */   public ClientboundGameEventPacket(Type $$0, float $$1) {
/* 46 */     this.event = $$0;
/* 47 */     this.param = $$1;
/*    */   }
/*    */   
/*    */   public ClientboundGameEventPacket(FriendlyByteBuf $$0) {
/* 51 */     this.event = (Type)Type.TYPES.get($$0.readUnsignedByte());
/* 52 */     this.param = $$0.readFloat();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 57 */     $$0.writeByte(this.event.id);
/* 58 */     $$0.writeFloat(this.param);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ClientGamePacketListener $$0) {
/* 63 */     $$0.handleGameEvent(this);
/*    */   }
/*    */   
/*    */   public Type getEvent() {
/* 67 */     return this.event;
/*    */   }
/*    */   
/*    */   public float getParam() {
/* 71 */     return this.param;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundGameEventPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */