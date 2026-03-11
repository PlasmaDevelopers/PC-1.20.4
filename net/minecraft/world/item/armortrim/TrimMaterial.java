/*    */ package net.minecraft.world.item.armortrim;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Map;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.world.item.ArmorMaterials;
/*    */ 
/*    */ public final class TrimMaterial extends Record {
/*    */   private final String assetName;
/*    */   private final Holder<Item> ingredient;
/*    */   private final float itemModelIndex;
/*    */   private final Map<ArmorMaterials, String> overrideArmorMaterials;
/*    */   private final Component description;
/*    */   public static final Codec<TrimMaterial> DIRECT_CODEC;
/*    */   
/* 18 */   public TrimMaterial(String $$0, Holder<Item> $$1, float $$2, Map<ArmorMaterials, String> $$3, Component $$4) { this.assetName = $$0; this.ingredient = $$1; this.itemModelIndex = $$2; this.overrideArmorMaterials = $$3; this.description = $$4; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/item/armortrim/TrimMaterial;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #18	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 18 */     //   0	7	0	this	Lnet/minecraft/world/item/armortrim/TrimMaterial; } public String assetName() { return this.assetName; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/item/armortrim/TrimMaterial;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #18	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/item/armortrim/TrimMaterial; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/item/armortrim/TrimMaterial;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #18	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/item/armortrim/TrimMaterial;
/* 18 */     //   0	8	1	$$0	Ljava/lang/Object; } public Holder<Item> ingredient() { return this.ingredient; } public float itemModelIndex() { return this.itemModelIndex; } public Map<ArmorMaterials, String> overrideArmorMaterials() { return this.overrideArmorMaterials; } public Component description() { return this.description; } static {
/* 19 */     DIRECT_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.RESOURCE_PATH_CODEC.fieldOf("asset_name").forGetter(TrimMaterial::assetName), (App)RegistryFixedCodec.create(Registries.ITEM).fieldOf("ingredient").forGetter(TrimMaterial::ingredient), (App)Codec.FLOAT.fieldOf("item_model_index").forGetter(TrimMaterial::itemModelIndex), (App)Codec.unboundedMap(ArmorMaterials.CODEC, (Codec)Codec.STRING).optionalFieldOf("override_armor_materials", Map.of()).forGetter(TrimMaterial::overrideArmorMaterials), (App)ComponentSerialization.CODEC.fieldOf("description").forGetter(TrimMaterial::description)).apply((Applicative)$$0, TrimMaterial::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 27 */   public static final Codec<Holder<TrimMaterial>> CODEC = (Codec<Holder<TrimMaterial>>)RegistryFileCodec.create(Registries.TRIM_MATERIAL, DIRECT_CODEC);
/*    */   
/*    */   public static TrimMaterial create(String $$0, Item $$1, float $$2, Component $$3, Map<ArmorMaterials, String> $$4) {
/* 30 */     return new TrimMaterial($$0, BuiltInRegistries.ITEM.wrapAsHolder($$1), $$2, $$4, $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\armortrim\TrimMaterial.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */