/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import java.util.Optional;
/*     */ 
/*     */ public final class EntityEquipmentPredicate extends Record {
/*     */   private final Optional<ItemPredicate> head;
/*     */   private final Optional<ItemPredicate> chest;
/*     */   private final Optional<ItemPredicate> legs;
/*     */   private final Optional<ItemPredicate> feet;
/*     */   private final Optional<ItemPredicate> mainhand;
/*     */   private final Optional<ItemPredicate> offhand;
/*     */   public static final Codec<EntityEquipmentPredicate> CODEC;
/*     */   
/*  15 */   public EntityEquipmentPredicate(Optional<ItemPredicate> $$0, Optional<ItemPredicate> $$1, Optional<ItemPredicate> $$2, Optional<ItemPredicate> $$3, Optional<ItemPredicate> $$4, Optional<ItemPredicate> $$5) { this.head = $$0; this.chest = $$1; this.legs = $$2; this.feet = $$3; this.mainhand = $$4; this.offhand = $$5; } public final String toString() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/EntityEquipmentPredicate;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #15	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*  15 */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/EntityEquipmentPredicate; } public Optional<ItemPredicate> head() { return this.head; } public final int hashCode() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/EntityEquipmentPredicate;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #15	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/EntityEquipmentPredicate; } public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/EntityEquipmentPredicate;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #15	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/EntityEquipmentPredicate;
/*  15 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<ItemPredicate> chest() { return this.chest; } public Optional<ItemPredicate> legs() { return this.legs; } public Optional<ItemPredicate> feet() { return this.feet; } public Optional<ItemPredicate> mainhand() { return this.mainhand; } public Optional<ItemPredicate> offhand() { return this.offhand; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  23 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(ItemPredicate.CODEC, "head").forGetter(EntityEquipmentPredicate::head), (App)ExtraCodecs.strictOptionalField(ItemPredicate.CODEC, "chest").forGetter(EntityEquipmentPredicate::chest), (App)ExtraCodecs.strictOptionalField(ItemPredicate.CODEC, "legs").forGetter(EntityEquipmentPredicate::legs), (App)ExtraCodecs.strictOptionalField(ItemPredicate.CODEC, "feet").forGetter(EntityEquipmentPredicate::feet), (App)ExtraCodecs.strictOptionalField(ItemPredicate.CODEC, "mainhand").forGetter(EntityEquipmentPredicate::mainhand), (App)ExtraCodecs.strictOptionalField(ItemPredicate.CODEC, "offhand").forGetter(EntityEquipmentPredicate::offhand)).apply((Applicative)$$0, EntityEquipmentPredicate::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  32 */   public static final EntityEquipmentPredicate CAPTAIN = Builder.equipment()
/*  33 */     .head(ItemPredicate.Builder.item().of(new ItemLike[] { (ItemLike)Items.WHITE_BANNER }).hasNbt(Raid.getLeaderBannerInstance().getTag()))
/*  34 */     .build();
/*     */   public boolean matches(@Nullable Entity $$0) {
/*     */     LivingEntity $$1;
/*  37 */     if ($$0 instanceof LivingEntity) { $$1 = (LivingEntity)$$0; }
/*  38 */     else { return false; }
/*     */ 
/*     */     
/*  41 */     if (this.head.isPresent() && !((ItemPredicate)this.head.get()).matches($$1.getItemBySlot(EquipmentSlot.HEAD))) {
/*  42 */       return false;
/*     */     }
/*  44 */     if (this.chest.isPresent() && !((ItemPredicate)this.chest.get()).matches($$1.getItemBySlot(EquipmentSlot.CHEST))) {
/*  45 */       return false;
/*     */     }
/*  47 */     if (this.legs.isPresent() && !((ItemPredicate)this.legs.get()).matches($$1.getItemBySlot(EquipmentSlot.LEGS))) {
/*  48 */       return false;
/*     */     }
/*  50 */     if (this.feet.isPresent() && !((ItemPredicate)this.feet.get()).matches($$1.getItemBySlot(EquipmentSlot.FEET))) {
/*  51 */       return false;
/*     */     }
/*  53 */     if (this.mainhand.isPresent() && !((ItemPredicate)this.mainhand.get()).matches($$1.getItemBySlot(EquipmentSlot.MAINHAND))) {
/*  54 */       return false;
/*     */     }
/*  56 */     if (this.offhand.isPresent() && !((ItemPredicate)this.offhand.get()).matches($$1.getItemBySlot(EquipmentSlot.OFFHAND))) {
/*  57 */       return false;
/*     */     }
/*     */     
/*  60 */     return true;
/*     */   }
/*     */   
/*     */   public static class Builder {
/*  64 */     private Optional<ItemPredicate> head = Optional.empty();
/*  65 */     private Optional<ItemPredicate> chest = Optional.empty();
/*  66 */     private Optional<ItemPredicate> legs = Optional.empty();
/*  67 */     private Optional<ItemPredicate> feet = Optional.empty();
/*  68 */     private Optional<ItemPredicate> mainhand = Optional.empty();
/*  69 */     private Optional<ItemPredicate> offhand = Optional.empty();
/*     */     
/*     */     public static Builder equipment() {
/*  72 */       return new Builder();
/*     */     }
/*     */     
/*     */     public Builder head(ItemPredicate.Builder $$0) {
/*  76 */       this.head = Optional.of($$0.build());
/*  77 */       return this;
/*     */     }
/*     */     
/*     */     public Builder chest(ItemPredicate.Builder $$0) {
/*  81 */       this.chest = Optional.of($$0.build());
/*  82 */       return this;
/*     */     }
/*     */     
/*     */     public Builder legs(ItemPredicate.Builder $$0) {
/*  86 */       this.legs = Optional.of($$0.build());
/*  87 */       return this;
/*     */     }
/*     */     
/*     */     public Builder feet(ItemPredicate.Builder $$0) {
/*  91 */       this.feet = Optional.of($$0.build());
/*  92 */       return this;
/*     */     }
/*     */     
/*     */     public Builder mainhand(ItemPredicate.Builder $$0) {
/*  96 */       this.mainhand = Optional.of($$0.build());
/*  97 */       return this;
/*     */     }
/*     */     
/*     */     public Builder offhand(ItemPredicate.Builder $$0) {
/* 101 */       this.offhand = Optional.of($$0.build());
/* 102 */       return this;
/*     */     }
/*     */     
/*     */     public EntityEquipmentPredicate build() {
/* 106 */       return new EntityEquipmentPredicate(this.head, this.chest, this.legs, this.feet, this.mainhand, this.offhand);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\EntityEquipmentPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */