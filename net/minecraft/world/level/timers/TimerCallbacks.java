/*    */ package net.minecraft.world.level.timers;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ 
/*    */ public class TimerCallbacks<C>
/*    */ {
/* 15 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/* 17 */   public static final TimerCallbacks<MinecraftServer> SERVER_CALLBACKS = (new TimerCallbacks())
/* 18 */     .register(new FunctionCallback.Serializer())
/* 19 */     .register(new FunctionTagCallback.Serializer());
/*    */   
/* 21 */   private final Map<ResourceLocation, TimerCallback.Serializer<C, ?>> idToSerializer = Maps.newHashMap();
/*    */   
/* 23 */   private final Map<Class<?>, TimerCallback.Serializer<C, ?>> classToSerializer = Maps.newHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TimerCallbacks<C> register(TimerCallback.Serializer<C, ?> $$0) {
/* 30 */     this.idToSerializer.put($$0.getId(), $$0);
/* 31 */     this.classToSerializer.put($$0.getCls(), $$0);
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   private <T extends TimerCallback<C>> TimerCallback.Serializer<C, T> getSerializer(Class<?> $$0) {
/* 37 */     return (TimerCallback.Serializer<C, T>)this.classToSerializer.get($$0);
/*    */   }
/*    */   
/*    */   public <T extends TimerCallback<C>> CompoundTag serialize(T $$0) {
/* 41 */     TimerCallback.Serializer<C, T> $$1 = getSerializer($$0.getClass());
/* 42 */     CompoundTag $$2 = new CompoundTag();
/* 43 */     $$1.serialize($$2, $$0);
/* 44 */     $$2.putString("Type", $$1.getId().toString());
/* 45 */     return $$2;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public TimerCallback<C> deserialize(CompoundTag $$0) {
/* 50 */     ResourceLocation $$1 = ResourceLocation.tryParse($$0.getString("Type"));
/* 51 */     TimerCallback.Serializer<C, ?> $$2 = this.idToSerializer.get($$1);
/* 52 */     if ($$2 == null) {
/* 53 */       LOGGER.error("Failed to deserialize timer callback: {}", $$0);
/* 54 */       return null;
/*    */     } 
/*    */     try {
/* 57 */       return (TimerCallback<C>)$$2.deserialize($$0);
/* 58 */     } catch (Exception $$3) {
/* 59 */       LOGGER.error("Failed to deserialize timer callback: {}", $$0, $$3);
/* 60 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\timers\TimerCallbacks.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */