/*     */ package net.minecraft.advancements;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ 
/*     */ public final class AdvancementRequirements extends Record {
/*     */   private final List<List<String>> requirements;
/*     */   
/*  14 */   public AdvancementRequirements(List<List<String>> $$0) { this.requirements = $$0; } public final int hashCode() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/AdvancementRequirements;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #14	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*  14 */     //   0	7	0	this	Lnet/minecraft/advancements/AdvancementRequirements; } public List<List<String>> requirements() { return this.requirements; }
/*     */   public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/AdvancementRequirements;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #14	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/advancements/AdvancementRequirements;
/*  15 */     //   0	8	1	$$0	Ljava/lang/Object; } public static final Codec<AdvancementRequirements> CODEC = Codec.STRING.listOf().listOf().xmap(AdvancementRequirements::new, AdvancementRequirements::requirements);
/*     */   
/*  17 */   public static final AdvancementRequirements EMPTY = new AdvancementRequirements(List.of());
/*     */   
/*     */   public AdvancementRequirements(FriendlyByteBuf $$0) {
/*  20 */     this($$0.readList($$0 -> $$0.readList(FriendlyByteBuf::readUtf)));
/*     */   }
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/*  24 */     $$0.writeCollection(this.requirements, ($$0, $$1) -> $$0.writeCollection($$1, FriendlyByteBuf::writeUtf));
/*     */   }
/*     */   
/*     */   public static AdvancementRequirements allOf(Collection<String> $$0) {
/*  28 */     return new AdvancementRequirements($$0.stream().map(List::of).toList());
/*     */   }
/*     */   
/*     */   public static AdvancementRequirements anyOf(Collection<String> $$0) {
/*  32 */     return new AdvancementRequirements(List.of(List.copyOf($$0)));
/*     */   }
/*     */   
/*     */   public int size() {
/*  36 */     return this.requirements.size();
/*     */   }
/*     */   
/*     */   public boolean test(Predicate<String> $$0) {
/*  40 */     if (this.requirements.isEmpty()) {
/*  41 */       return false;
/*     */     }
/*  43 */     for (List<String> $$1 : this.requirements) {
/*  44 */       if (!anyMatch($$1, $$0)) {
/*  45 */         return false;
/*     */       }
/*     */     } 
/*  48 */     return true;
/*     */   }
/*     */   
/*     */   public int count(Predicate<String> $$0) {
/*  52 */     int $$1 = 0;
/*  53 */     for (List<String> $$2 : this.requirements) {
/*  54 */       if (anyMatch($$2, $$0)) {
/*  55 */         $$1++;
/*     */       }
/*     */     } 
/*  58 */     return $$1;
/*     */   }
/*     */   
/*     */   private static boolean anyMatch(List<String> $$0, Predicate<String> $$1) {
/*  62 */     for (String $$2 : $$0) {
/*  63 */       if ($$1.test($$2)) {
/*  64 */         return true;
/*     */       }
/*     */     } 
/*  67 */     return false;
/*     */   }
/*     */   
/*     */   public DataResult<AdvancementRequirements> validate(Set<String> $$0) {
/*  71 */     ObjectOpenHashSet<String> objectOpenHashSet = new ObjectOpenHashSet();
/*  72 */     for (List<String> $$2 : this.requirements) {
/*  73 */       if ($$2.isEmpty() && $$0.isEmpty()) {
/*  74 */         return DataResult.error(() -> "Requirement entry cannot be empty");
/*     */       }
/*  76 */       objectOpenHashSet.addAll($$2);
/*     */     } 
/*  78 */     if (!$$0.equals(objectOpenHashSet)) {
/*  79 */       Sets.SetView setView1 = Sets.difference($$0, (Set)objectOpenHashSet);
/*  80 */       Sets.SetView setView2 = Sets.difference((Set)objectOpenHashSet, $$0);
/*  81 */       return DataResult.error(() -> "Advancement completion requirements did not exactly match specified criteria. Missing: " + $$0 + ". Unknown: " + $$1);
/*     */     } 
/*  83 */     return DataResult.success(this);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  87 */     return this.requirements.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  92 */     return this.requirements.toString();
/*     */   }
/*     */   
/*     */   public Set<String> names() {
/*  96 */     ObjectOpenHashSet<String> objectOpenHashSet = new ObjectOpenHashSet();
/*  97 */     for (List<String> $$1 : this.requirements) {
/*  98 */       objectOpenHashSet.addAll($$1);
/*     */     }
/* 100 */     return (Set<String>)objectOpenHashSet;
/*     */   }
/*     */   
/*     */   public static interface Strategy {
/* 104 */     public static final Strategy AND = AdvancementRequirements::allOf;
/* 105 */     public static final Strategy OR = AdvancementRequirements::anyOf;
/*     */     
/*     */     AdvancementRequirements create(Collection<String> param1Collection);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\AdvancementRequirements.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */