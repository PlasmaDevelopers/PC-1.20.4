/*    */ package net.minecraft.tags;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*    */ import it.unimi.dsi.fastutil.ints.IntList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.stream.Collectors;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderSet;
/*    */ import net.minecraft.core.LayeredRegistryAccess;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.core.RegistrySynchronization;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.RegistryLayer;
/*    */ 
/*    */ public class TagNetworkSerialization {
/*    */   public static Map<ResourceKey<? extends Registry<?>>, NetworkPayload> serializeTagsToNetwork(LayeredRegistryAccess<RegistryLayer> $$0) {
/* 24 */     return (Map<ResourceKey<? extends Registry<?>>, NetworkPayload>)RegistrySynchronization.networkSafeRegistries($$0)
/* 25 */       .map($$0 -> Pair.of($$0.key(), serializeToNetwork($$0.value())))
/* 26 */       .filter($$0 -> !((NetworkPayload)$$0.getSecond()).isEmpty())
/* 27 */       .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
/*    */   }
/*    */   
/*    */   private static <T> NetworkPayload serializeToNetwork(Registry<T> $$0) {
/* 31 */     Map<ResourceLocation, IntList> $$1 = new HashMap<>();
/* 32 */     $$0.getTags().forEach($$2 -> {
/*    */           HolderSet<T> $$3 = (HolderSet<T>)$$2.getSecond();
/*    */           IntArrayList intArrayList = new IntArrayList($$3.size());
/*    */           for (Holder<T> $$5 : $$3) {
/*    */             if ($$5.kind() != Holder.Kind.REFERENCE) {
/*    */               throw new IllegalStateException("Can't serialize unregistered value " + $$5);
/*    */             }
/*    */             intArrayList.add($$0.getId($$5.value()));
/*    */           } 
/*    */           $$1.put(((TagKey)$$2.getFirst()).location(), intArrayList);
/*    */         });
/* 43 */     return new NetworkPayload($$1);
/*    */   }
/*    */   
/*    */   public static <T> void deserializeTagsFromNetwork(ResourceKey<? extends Registry<T>> $$0, Registry<T> $$1, NetworkPayload $$2, TagOutput<T> $$3) {
/* 47 */     $$2.tags.forEach(($$3, $$4) -> {
/*    */           TagKey<T> $$5 = TagKey.create($$0, $$3);
/*    */           Objects.requireNonNull($$1);
/*    */           List<Holder<T>> $$6 = (List<Holder<T>>)$$4.intStream().mapToObj($$1::getHolder).flatMap(Optional::stream).collect(Collectors.toUnmodifiableList());
/*    */           $$2.accept($$5, $$6);
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static final class NetworkPayload
/*    */   {
/*    */     final Map<ResourceLocation, IntList> tags;
/*    */ 
/*    */     
/*    */     NetworkPayload(Map<ResourceLocation, IntList> $$0) {
/* 63 */       this.tags = $$0;
/*    */     }
/*    */     
/*    */     public void write(FriendlyByteBuf $$0) {
/* 67 */       $$0.writeMap(this.tags, FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::writeIntIdList);
/*    */     }
/*    */     
/*    */     public static NetworkPayload read(FriendlyByteBuf $$0) {
/* 71 */       return new NetworkPayload($$0.readMap(FriendlyByteBuf::readResourceLocation, FriendlyByteBuf::readIntIdList));
/*    */     }
/*    */     
/*    */     public boolean isEmpty() {
/* 75 */       return this.tags.isEmpty();
/*    */     }
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface TagOutput<T> {
/*    */     void accept(TagKey<T> param1TagKey, List<Holder<T>> param1List);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\tags\TagNetworkSerialization.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */