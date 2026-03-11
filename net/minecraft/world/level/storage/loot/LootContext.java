/*     */ package net.minecraft.world.level.storage.loot;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*     */ 
/*     */ public class LootContext
/*     */ {
/*     */   private final LootParams params;
/*     */   private final RandomSource random;
/*     */   private final LootDataResolver lootDataResolver;
/*  26 */   private final Set<VisitedEntry<?>> visitedElements = Sets.newLinkedHashSet();
/*     */   
/*     */   LootContext(LootParams $$0, RandomSource $$1, LootDataResolver $$2) {
/*  29 */     this.params = $$0;
/*  30 */     this.random = $$1;
/*  31 */     this.lootDataResolver = $$2;
/*     */   }
/*     */   
/*     */   public boolean hasParam(LootContextParam<?> $$0) {
/*  35 */     return this.params.hasParam($$0);
/*     */   }
/*     */   
/*     */   public <T> T getParam(LootContextParam<T> $$0) {
/*  39 */     return this.params.getParameter($$0);
/*     */   }
/*     */   
/*     */   public void addDynamicDrops(ResourceLocation $$0, Consumer<ItemStack> $$1) {
/*  43 */     this.params.addDynamicDrops($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T> T getParamOrNull(LootContextParam<T> $$0) {
/*  49 */     return this.params.getParamOrNull($$0);
/*     */   }
/*     */   
/*     */   public boolean hasVisitedElement(VisitedEntry<?> $$0) {
/*  53 */     return this.visitedElements.contains($$0);
/*     */   }
/*     */   
/*     */   public boolean pushVisitedElement(VisitedEntry<?> $$0) {
/*  57 */     return this.visitedElements.add($$0);
/*     */   }
/*     */   
/*     */   public void popVisitedElement(VisitedEntry<?> $$0) {
/*  61 */     this.visitedElements.remove($$0);
/*     */   }
/*     */   
/*     */   public LootDataResolver getResolver() {
/*  65 */     return this.lootDataResolver;
/*     */   }
/*     */   
/*     */   public RandomSource getRandom() {
/*  69 */     return this.random;
/*     */   }
/*     */   
/*     */   public float getLuck() {
/*  73 */     return this.params.getLuck();
/*     */   }
/*     */   
/*     */   public ServerLevel getLevel() {
/*  77 */     return this.params.getLevel();
/*     */   }
/*     */   
/*     */   public static class Builder {
/*     */     private final LootParams params;
/*     */     @Nullable
/*     */     private RandomSource random;
/*     */     
/*     */     public Builder(LootParams $$0) {
/*  86 */       this.params = $$0;
/*     */     }
/*     */     
/*     */     public Builder withOptionalRandomSeed(long $$0) {
/*  90 */       if ($$0 != 0L) {
/*  91 */         this.random = RandomSource.create($$0);
/*     */       }
/*  93 */       return this;
/*     */     }
/*     */     
/*     */     public ServerLevel getLevel() {
/*  97 */       return this.params.getLevel();
/*     */     }
/*     */     
/*     */     public LootContext create(Optional<ResourceLocation> $$0) {
/* 101 */       ServerLevel $$1 = getLevel();
/* 102 */       MinecraftServer $$2 = $$1.getServer();
/*     */ 
/*     */       
/* 105 */       Objects.requireNonNull($$1); RandomSource $$3 = Optional.<RandomSource>ofNullable(this.random).or(() -> { Objects.requireNonNull($$1); return $$0.map($$1::getRandomSequence); }).orElseGet($$1::getRandom);
/* 106 */       return new LootContext(this.params, $$3, $$2.getLootData());
/*     */     }
/*     */   }
/*     */   
/*     */   public enum EntityTarget implements StringRepresentable {
/* 111 */     THIS("this", LootContextParams.THIS_ENTITY),
/* 112 */     KILLER("killer", LootContextParams.KILLER_ENTITY),
/* 113 */     DIRECT_KILLER("direct_killer", LootContextParams.DIRECT_KILLER_ENTITY),
/* 114 */     KILLER_PLAYER("killer_player", LootContextParams.LAST_DAMAGE_PLAYER);
/*     */ 
/*     */     
/* 117 */     public static final StringRepresentable.EnumCodec<EntityTarget> CODEC = StringRepresentable.fromEnum(EntityTarget::values); private final String name; private final LootContextParam<? extends Entity> param;
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */     
/*     */     EntityTarget(String $$0, LootContextParam<? extends Entity> $$1) {
/* 124 */       this.name = $$0;
/* 125 */       this.param = $$1;
/*     */     }
/*     */     
/*     */     public LootContextParam<? extends Entity> getParam() {
/* 129 */       return this.param;
/*     */     }
/*     */     
/*     */     public static EntityTarget getByName(String $$0) {
/* 133 */       EntityTarget $$1 = (EntityTarget)CODEC.byName($$0);
/* 134 */       if ($$1 != null) {
/* 135 */         return $$1;
/*     */       }
/* 137 */       throw new IllegalArgumentException("Invalid entity target " + $$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSerializedName() {
/* 142 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   public static VisitedEntry<LootTable> createVisitedEntry(LootTable $$0) {
/* 147 */     return new VisitedEntry<>(LootDataType.TABLE, $$0);
/*     */   }
/*     */   
/*     */   public static VisitedEntry<LootItemCondition> createVisitedEntry(LootItemCondition $$0) {
/* 151 */     return new VisitedEntry<>(LootDataType.PREDICATE, $$0);
/*     */   }
/*     */   
/*     */   public static VisitedEntry<LootItemFunction> createVisitedEntry(LootItemFunction $$0) {
/* 155 */     return new VisitedEntry<>(LootDataType.MODIFIER, $$0);
/*     */   }
/*     */   public static final class VisitedEntry<T> extends Record { private final LootDataType<T> type; private final T value;
/* 158 */     public VisitedEntry(LootDataType<T> $$0, T $$1) { this.type = $$0; this.value = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/LootContext$VisitedEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #158	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/LootContext$VisitedEntry;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 158 */       //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/LootContext$VisitedEntry<TT;>; } public LootDataType<T> type() { return this.type; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/LootContext$VisitedEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #158	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/LootContext$VisitedEntry;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/LootContext$VisitedEntry<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/LootContext$VisitedEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #158	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/LootContext$VisitedEntry;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 158 */       //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/LootContext$VisitedEntry<TT;>; } public T value() { return this.value; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\LootContext.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */