/*    */ package net.minecraft.world.level.gameevent;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.IdMap;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ public interface PositionSourceType<T extends PositionSource> {
/*  9 */   public static final PositionSourceType<BlockPositionSource> BLOCK = register("block", new BlockPositionSource.Type());
/* 10 */   public static final PositionSourceType<EntityPositionSource> ENTITY = register("entity", new EntityPositionSource.Type());
/*    */   
/*    */   T read(FriendlyByteBuf paramFriendlyByteBuf);
/*    */   
/*    */   void write(FriendlyByteBuf paramFriendlyByteBuf, T paramT);
/*    */   
/*    */   Codec<T> codec();
/*    */   
/*    */   static <S extends PositionSourceType<T>, T extends PositionSource> S register(String $$0, S $$1) {
/* 19 */     return (S)Registry.register(BuiltInRegistries.POSITION_SOURCE_TYPE, $$0, $$1);
/*    */   }
/*    */   
/*    */   static PositionSource fromNetwork(FriendlyByteBuf $$0) {
/* 23 */     PositionSourceType<?> $$1 = (PositionSourceType)$$0.readById((IdMap)BuiltInRegistries.POSITION_SOURCE_TYPE);
/* 24 */     if ($$1 == null) {
/* 25 */       throw new IllegalArgumentException("Unknown position source type");
/*    */     }
/* 27 */     return (PositionSource)$$1.read($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   static <T extends PositionSource> void toNetwork(T $$0, FriendlyByteBuf $$1) {
/* 32 */     $$1.writeId((IdMap)BuiltInRegistries.POSITION_SOURCE_TYPE, $$0.getType());
/* 33 */     $$0.getType().write($$1, $$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\gameevent\PositionSourceType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */