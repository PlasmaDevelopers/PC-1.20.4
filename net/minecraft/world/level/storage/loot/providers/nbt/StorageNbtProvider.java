/*    */ package net.minecraft.world.level.storage.loot.providers.nbt;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Set;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
/*    */ 
/*    */ public final class StorageNbtProvider extends Record implements NbtProvider {
/*    */   private final ResourceLocation id;
/*    */   public static final Codec<StorageNbtProvider> CODEC;
/*    */   
/* 14 */   public StorageNbtProvider(ResourceLocation $$0) { this.id = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/providers/nbt/StorageNbtProvider;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 14 */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/providers/nbt/StorageNbtProvider; } public ResourceLocation id() { return this.id; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/providers/nbt/StorageNbtProvider;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/providers/nbt/StorageNbtProvider; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/providers/nbt/StorageNbtProvider;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/providers/nbt/StorageNbtProvider;
/* 15 */     //   0	8	1	$$0	Ljava/lang/Object; } static { CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ResourceLocation.CODEC.fieldOf("source").forGetter(StorageNbtProvider::id)).apply((Applicative)$$0, StorageNbtProvider::new)); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LootNbtProviderType getType() {
/* 21 */     return NbtProviders.STORAGE;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Tag get(LootContext $$0) {
/* 27 */     return (Tag)$$0.getLevel().getServer().getCommandStorage().get(this.id);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 32 */     return (Set<LootContextParam<?>>)ImmutableSet.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\providers\nbt\StorageNbtProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */