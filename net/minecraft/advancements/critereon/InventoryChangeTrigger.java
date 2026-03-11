/*     */ package net.minecraft.advancements.critereon;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.advancements.Criterion;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ 
/*     */ public class InventoryChangeTrigger extends SimpleCriterionTrigger<InventoryChangeTrigger.TriggerInstance> {
/*     */   public Codec<TriggerInstance> codec() {
/*  22 */     return TriggerInstance.CODEC;
/*     */   }
/*     */   
/*     */   public void trigger(ServerPlayer $$0, Inventory $$1, ItemStack $$2) {
/*  26 */     int $$3 = 0;
/*  27 */     int $$4 = 0;
/*  28 */     int $$5 = 0;
/*     */     
/*  30 */     for (int $$6 = 0; $$6 < $$1.getContainerSize(); $$6++) {
/*  31 */       ItemStack $$7 = $$1.getItem($$6);
/*  32 */       if ($$7.isEmpty()) {
/*  33 */         $$4++;
/*     */       } else {
/*  35 */         $$5++;
/*  36 */         if ($$7.getCount() >= $$7.getMaxStackSize()) {
/*  37 */           $$3++;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  42 */     trigger($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   private void trigger(ServerPlayer $$0, Inventory $$1, ItemStack $$2, int $$3, int $$4, int $$5) {
/*  46 */     trigger($$0, $$5 -> $$5.matches($$0, $$1, $$2, $$3, $$4));
/*     */   }
/*     */   public static final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance { private final Optional<ContextAwarePredicate> player; private final Slots slots; private final List<ItemPredicate> items; public static final Codec<TriggerInstance> CODEC;
/*  49 */     public TriggerInstance(Optional<ContextAwarePredicate> $$0, Slots $$1, List<ItemPredicate> $$2) { this.player = $$0; this.slots = $$1; this.items = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #49	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  49 */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance; } public Optional<ContextAwarePredicate> player() { return this.player; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #49	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #49	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance;
/*  49 */       //   0	8	1	$$0	Ljava/lang/Object; } public Slots slots() { return this.slots; } public List<ItemPredicate> items() { return this.items; }
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*  54 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(Slots.CODEC, "slots", Slots.ANY).forGetter(TriggerInstance::slots), (App)ExtraCodecs.strictOptionalField(ItemPredicate.CODEC.listOf(), "items", List.of()).forGetter(TriggerInstance::items)).apply((Applicative)$$0, TriggerInstance::new));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Criterion<TriggerInstance> hasItems(ItemPredicate.Builder... $$0) {
/*  61 */       return hasItems((ItemPredicate[])Stream.<ItemPredicate.Builder>of($$0).map(ItemPredicate.Builder::build).toArray($$0 -> new ItemPredicate[$$0]));
/*     */     }
/*     */     
/*     */     public static Criterion<TriggerInstance> hasItems(ItemPredicate... $$0) {
/*  65 */       return CriteriaTriggers.INVENTORY_CHANGED.createCriterion(new TriggerInstance(Optional.empty(), Slots.ANY, List.of($$0)));
/*     */     }
/*     */     
/*     */     public static Criterion<TriggerInstance> hasItems(ItemLike... $$0) {
/*  69 */       ItemPredicate[] $$1 = new ItemPredicate[$$0.length];
/*  70 */       for (int $$2 = 0; $$2 < $$0.length; $$2++) {
/*  71 */         $$1[$$2] = new ItemPredicate(Optional.empty(), (Optional)Optional.of(HolderSet.direct(new Holder[] { (Holder)$$0[$$2].asItem().builtInRegistryHolder() }, )), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, List.of(), List.of(), Optional.empty(), Optional.empty());
/*     */       } 
/*  73 */       return hasItems($$1);
/*     */     }
/*     */     
/*     */     public boolean matches(Inventory $$0, ItemStack $$1, int $$2, int $$3, int $$4) {
/*  77 */       if (!this.slots.matches($$2, $$3, $$4)) {
/*  78 */         return false;
/*     */       }
/*     */       
/*  81 */       if (this.items.isEmpty()) {
/*  82 */         return true;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  87 */       if (this.items.size() == 1) {
/*  88 */         return (!$$1.isEmpty() && ((ItemPredicate)this.items.get(0)).matches($$1));
/*     */       }
/*     */       
/*  91 */       ObjectArrayList objectArrayList = new ObjectArrayList(this.items);
/*  92 */       int $$6 = $$0.getContainerSize();
/*  93 */       for (int $$7 = 0; $$7 < $$6; $$7++) {
/*  94 */         if (objectArrayList.isEmpty()) {
/*  95 */           return true;
/*     */         }
/*     */         
/*  98 */         ItemStack $$8 = $$0.getItem($$7);
/*  99 */         if (!$$8.isEmpty()) {
/* 100 */           objectArrayList.removeIf($$1 -> $$1.matches($$0));
/*     */         }
/*     */       } 
/* 103 */       return objectArrayList.isEmpty();
/*     */     }
/*     */     public static final class Slots extends Record { private final MinMaxBounds.Ints occupied; private final MinMaxBounds.Ints full; private final MinMaxBounds.Ints empty; public static final Codec<Slots> CODEC;
/* 106 */       public Slots(MinMaxBounds.Ints $$0, MinMaxBounds.Ints $$1, MinMaxBounds.Ints $$2) { this.occupied = $$0; this.full = $$1; this.empty = $$2; } public final String toString() { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance$Slots;)Ljava/lang/String;
/*     */         //   6: areturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #106	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	7	0	this	Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance$Slots; } public final int hashCode() { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance$Slots;)I
/*     */         //   6: ireturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #106	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	7	0	this	Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance$Slots; } public final boolean equals(Object $$0) { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: aload_1
/*     */         //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance$Slots;Ljava/lang/Object;)Z
/*     */         //   7: ireturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #106	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	8	0	this	Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance$Slots;
/* 106 */         //   0	8	1	$$0	Ljava/lang/Object; } public MinMaxBounds.Ints occupied() { return this.occupied; } public MinMaxBounds.Ints full() { return this.full; } public MinMaxBounds.Ints empty() { return this.empty; }
/*     */ 
/*     */ 
/*     */       
/*     */       static {
/* 111 */         CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "occupied", MinMaxBounds.Ints.ANY).forGetter(Slots::occupied), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "full", MinMaxBounds.Ints.ANY).forGetter(Slots::full), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "empty", MinMaxBounds.Ints.ANY).forGetter(Slots::empty)).apply((Applicative)$$0, Slots::new));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 117 */       public static final Slots ANY = new Slots(MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY);
/*     */       
/*     */       public boolean matches(int $$0, int $$1, int $$2) {
/* 120 */         if (!this.full.matches($$0)) {
/* 121 */           return false;
/*     */         }
/* 123 */         if (!this.empty.matches($$1)) {
/* 124 */           return false;
/*     */         }
/* 126 */         if (!this.occupied.matches($$2)) {
/* 127 */           return false;
/*     */         }
/* 129 */         return true; } } } public static final class Slots extends Record { private final MinMaxBounds.Ints occupied; public boolean matches(int $$0, int $$1, int $$2) { if (!this.full.matches($$0)) return false;  if (!this.empty.matches($$1)) return false;  if (!this.occupied.matches($$2)) return false;  return true; }
/*     */ 
/*     */     
/*     */     private final MinMaxBounds.Ints full;
/*     */     private final MinMaxBounds.Ints empty;
/*     */     public static final Codec<Slots> CODEC;
/*     */     
/*     */     public Slots(MinMaxBounds.Ints $$0, MinMaxBounds.Ints $$1, MinMaxBounds.Ints $$2) {
/*     */       this.occupied = $$0;
/*     */       this.full = $$1;
/*     */       this.empty = $$2;
/*     */     }
/*     */     
/*     */     public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance$Slots;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #106	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance$Slots;
/*     */     }
/*     */     
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance$Slots;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #106	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance$Slots;
/*     */     }
/*     */     
/*     */     public final boolean equals(Object $$0) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance$Slots;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #106	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$TriggerInstance$Slots;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */     }
/*     */     
/*     */     public MinMaxBounds.Ints occupied() {
/*     */       return this.occupied;
/*     */     }
/*     */     
/*     */     public MinMaxBounds.Ints full() {
/*     */       return this.full;
/*     */     }
/*     */     
/*     */     public MinMaxBounds.Ints empty() {
/*     */       return this.empty;
/*     */     }
/*     */     
/*     */     static {
/*     */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "occupied", MinMaxBounds.Ints.ANY).forGetter(Slots::occupied), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "full", MinMaxBounds.Ints.ANY).forGetter(Slots::full), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "empty", MinMaxBounds.Ints.ANY).forGetter(Slots::empty)).apply((Applicative)$$0, Slots::new));
/*     */     }
/*     */     
/*     */     public static final Slots ANY = new Slots(MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY); }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\InventoryChangeTrigger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */