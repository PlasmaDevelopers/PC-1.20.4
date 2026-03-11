/*     */ package net.minecraft.world.level.storage.loot.providers.nbt;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.critereon.NbtPredicate;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContextNbtProvider
/*     */   implements NbtProvider
/*     */ {
/*     */   private static final String BLOCK_ENTITY_ID = "block_entity";
/*     */   
/*  29 */   private static final Getter BLOCK_ENTITY_PROVIDER = new Getter()
/*     */     {
/*     */       public Tag get(LootContext $$0) {
/*  32 */         BlockEntity $$1 = (BlockEntity)$$0.getParamOrNull(LootContextParams.BLOCK_ENTITY);
/*  33 */         return ($$1 != null) ? (Tag)$$1.saveWithFullMetadata() : null;
/*     */       }
/*     */ 
/*     */       
/*     */       public String getId() {
/*  38 */         return "block_entity";
/*     */       }
/*     */ 
/*     */       
/*     */       public Set<LootContextParam<?>> getReferencedContextParams() {
/*  43 */         return (Set<LootContextParam<?>>)ImmutableSet.of(LootContextParams.BLOCK_ENTITY);
/*     */       }
/*     */     };
/*     */   
/*     */   private static Getter forEntity(final LootContext.EntityTarget target) {
/*  48 */     return new Getter()
/*     */       {
/*     */         @Nullable
/*     */         public Tag get(LootContext $$0) {
/*  52 */           Entity $$1 = (Entity)$$0.getParamOrNull(target.getParam());
/*  53 */           return ($$1 != null) ? (Tag)NbtPredicate.getEntityTagToCompare($$1) : null;
/*     */         }
/*     */ 
/*     */         
/*     */         public String getId() {
/*  58 */           return target.name();
/*     */         }
/*     */ 
/*     */         
/*     */         public Set<LootContextParam<?>> getReferencedContextParams() {
/*  63 */           return (Set<LootContextParam<?>>)ImmutableSet.of(target.getParam());
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*  68 */   public static final ContextNbtProvider BLOCK_ENTITY = new ContextNbtProvider(BLOCK_ENTITY_PROVIDER); private static interface Getter {
/*     */     @Nullable
/*  70 */     Tag get(LootContext param1LootContext); String getId(); Set<LootContextParam<?>> getReferencedContextParams(); } private static final Codec<Getter> GETTER_CODEC; public static final Codec<ContextNbtProvider> CODEC; public static final Codec<ContextNbtProvider> INLINE_CODEC; private final Getter getter; static { GETTER_CODEC = Codec.STRING.xmap($$0 -> { if ($$0.equals("block_entity")) return BLOCK_ENTITY_PROVIDER;  LootContext.EntityTarget $$1 = LootContext.EntityTarget.getByName($$0); return forEntity($$1); }Getter::getId);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)GETTER_CODEC.fieldOf("target").forGetter(())).apply((Applicative)$$0, ContextNbtProvider::new));
/*     */ 
/*     */ 
/*     */     
/*  82 */     INLINE_CODEC = GETTER_CODEC.xmap(ContextNbtProvider::new, $$0 -> $$0.getter); }
/*     */ 
/*     */ 
/*     */   
/*     */   private ContextNbtProvider(Getter $$0) {
/*  87 */     this.getter = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public LootNbtProviderType getType() {
/*  92 */     return NbtProviders.CONTEXT;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Tag get(LootContext $$0) {
/*  98 */     return this.getter.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 103 */     return this.getter.getReferencedContextParams();
/*     */   }
/*     */   
/*     */   public static NbtProvider forContextEntity(LootContext.EntityTarget $$0) {
/* 107 */     return new ContextNbtProvider(forEntity($$0));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\providers\nbt\ContextNbtProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */