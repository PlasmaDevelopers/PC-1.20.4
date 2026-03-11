/*    */ package net.minecraft.world.level.gameevent;
/*    */ 
/*    */ import com.mojang.datafixers.util.Either;
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
/*    */ public class Type
/*    */   implements PositionSourceType<EntityPositionSource>
/*    */ {
/*    */   public EntityPositionSource read(FriendlyByteBuf $$0) {
/* 85 */     return new EntityPositionSource(Either.right(Either.right(Integer.valueOf($$0.readVarInt()))), $$0.readFloat());
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0, EntityPositionSource $$1) {
/* 90 */     $$0.writeVarInt($$1.getId());
/* 91 */     $$0.writeFloat($$1.yOffset);
/*    */   }
/*    */ 
/*    */   
/*    */   public Codec<EntityPositionSource> codec() {
/* 96 */     return EntityPositionSource.CODEC;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\gameevent\EntityPositionSource$Type.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */