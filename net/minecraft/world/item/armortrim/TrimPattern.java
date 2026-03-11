/*    */ package net.minecraft.world.item.armortrim;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public final class TrimPattern extends Record {
/*    */   private final ResourceLocation assetId;
/*    */   private final Holder<Item> templateItem;
/*    */   private final Component description;
/*    */   private final boolean decal;
/*    */   public static final Codec<TrimPattern> DIRECT_CODEC;
/*    */   
/* 14 */   public TrimPattern(ResourceLocation $$0, Holder<Item> $$1, Component $$2, boolean $$3) { this.assetId = $$0; this.templateItem = $$1; this.description = $$2; this.decal = $$3; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/item/armortrim/TrimPattern;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 14 */     //   0	7	0	this	Lnet/minecraft/world/item/armortrim/TrimPattern; } public ResourceLocation assetId() { return this.assetId; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/item/armortrim/TrimPattern;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/item/armortrim/TrimPattern; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/item/armortrim/TrimPattern;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/item/armortrim/TrimPattern;
/* 14 */     //   0	8	1	$$0	Ljava/lang/Object; } public Holder<Item> templateItem() { return this.templateItem; } public Component description() { return this.description; } public boolean decal() { return this.decal; } static {
/* 15 */     DIRECT_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ResourceLocation.CODEC.fieldOf("asset_id").forGetter(TrimPattern::assetId), (App)RegistryFixedCodec.create(Registries.ITEM).fieldOf("template_item").forGetter(TrimPattern::templateItem), (App)ComponentSerialization.CODEC.fieldOf("description").forGetter(TrimPattern::description), (App)Codec.BOOL.fieldOf("decal").orElse(Boolean.valueOf(false)).forGetter(TrimPattern::decal)).apply((Applicative)$$0, TrimPattern::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 22 */   public static final Codec<Holder<TrimPattern>> CODEC = (Codec<Holder<TrimPattern>>)RegistryFileCodec.create(Registries.TRIM_PATTERN, DIRECT_CODEC);
/*    */   
/*    */   public Component copyWithStyle(Holder<TrimMaterial> $$0) {
/* 25 */     return (Component)this.description.copy().withStyle(((TrimMaterial)$$0.value()).description().getStyle());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\armortrim\TrimPattern.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */