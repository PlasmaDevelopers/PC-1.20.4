/*     */ package net.minecraft.advancements;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.commands.CacheableFunction;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ public final class AdvancementRewards extends Record {
/*     */   private final int experience;
/*     */   private final List<ResourceLocation> loot;
/*     */   private final List<ResourceLocation> recipes;
/*     */   private final Optional<CacheableFunction> function;
/*     */   public static final Codec<AdvancementRewards> CODEC;
/*     */   
/*  23 */   public AdvancementRewards(int $$0, List<ResourceLocation> $$1, List<ResourceLocation> $$2, Optional<CacheableFunction> $$3) { this.experience = $$0; this.loot = $$1; this.recipes = $$2; this.function = $$3; } public final String toString() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/AdvancementRewards;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #23	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*  23 */     //   0	7	0	this	Lnet/minecraft/advancements/AdvancementRewards; } public int experience() { return this.experience; } public final int hashCode() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/AdvancementRewards;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #23	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/advancements/AdvancementRewards; } public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/AdvancementRewards;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #23	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/advancements/AdvancementRewards;
/*  23 */     //   0	8	1	$$0	Ljava/lang/Object; } public List<ResourceLocation> loot() { return this.loot; } public List<ResourceLocation> recipes() { return this.recipes; } public Optional<CacheableFunction> function() { return this.function; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  29 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField((Codec)Codec.INT, "experience", Integer.valueOf(0)).forGetter(AdvancementRewards::experience), (App)ExtraCodecs.strictOptionalField(ResourceLocation.CODEC.listOf(), "loot", List.of()).forGetter(AdvancementRewards::loot), (App)ExtraCodecs.strictOptionalField(ResourceLocation.CODEC.listOf(), "recipes", List.of()).forGetter(AdvancementRewards::recipes), (App)ExtraCodecs.strictOptionalField(CacheableFunction.CODEC, "function").forGetter(AdvancementRewards::function)).apply((Applicative)$$0, AdvancementRewards::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   public static final AdvancementRewards EMPTY = new AdvancementRewards(0, List.of(), List.of(), Optional.empty());
/*     */   
/*     */   public void grant(ServerPlayer $$0) {
/*  39 */     $$0.giveExperiencePoints(this.experience);
/*     */ 
/*     */ 
/*     */     
/*  43 */     LootParams $$1 = (new LootParams.Builder($$0.serverLevel())).withParameter(LootContextParams.THIS_ENTITY, $$0).withParameter(LootContextParams.ORIGIN, $$0.position()).create(LootContextParamSets.ADVANCEMENT_REWARD);
/*     */     
/*  45 */     boolean $$2 = false;
/*  46 */     for (ResourceLocation $$3 : this.loot) {
/*  47 */       for (ObjectListIterator<ItemStack> objectListIterator = $$0.server.getLootData().getLootTable($$3).getRandomItems($$1).iterator(); objectListIterator.hasNext(); ) { ItemStack $$4 = objectListIterator.next();
/*  48 */         if ($$0.addItem($$4)) {
/*  49 */           $$0.level().playSound(null, $$0.getX(), $$0.getY(), $$0.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, (($$0.getRandom().nextFloat() - $$0.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
/*  50 */           $$2 = true; continue;
/*     */         } 
/*  52 */         ItemEntity $$5 = $$0.drop($$4, false);
/*  53 */         if ($$5 != null) {
/*  54 */           $$5.setNoPickUpDelay();
/*  55 */           $$5.setTarget($$0.getUUID());
/*     */         }  }
/*     */     
/*     */     } 
/*     */     
/*  60 */     if ($$2) {
/*  61 */       $$0.containerMenu.broadcastChanges();
/*     */     }
/*  63 */     if (!this.recipes.isEmpty()) {
/*  64 */       $$0.awardRecipesByKey(this.recipes);
/*     */     }
/*  66 */     MinecraftServer $$6 = $$0.server;
/*  67 */     this.function.flatMap($$1 -> $$1.get($$0.getFunctions()))
/*  68 */       .ifPresent($$2 -> $$0.getFunctions().execute($$2, $$1.createCommandSourceStack().withSuppressedOutput().withPermission(2)));
/*     */   }
/*     */   
/*     */   public static class Builder {
/*     */     private int experience;
/*  73 */     private final ImmutableList.Builder<ResourceLocation> loot = ImmutableList.builder();
/*  74 */     private final ImmutableList.Builder<ResourceLocation> recipes = ImmutableList.builder();
/*  75 */     private Optional<ResourceLocation> function = Optional.empty();
/*     */     
/*     */     public static Builder experience(int $$0) {
/*  78 */       return (new Builder()).addExperience($$0);
/*     */     }
/*     */     
/*     */     public Builder addExperience(int $$0) {
/*  82 */       this.experience += $$0;
/*  83 */       return this;
/*     */     }
/*     */     
/*     */     public static Builder loot(ResourceLocation $$0) {
/*  87 */       return (new Builder()).addLootTable($$0);
/*     */     }
/*     */     
/*     */     public Builder addLootTable(ResourceLocation $$0) {
/*  91 */       this.loot.add($$0);
/*  92 */       return this;
/*     */     }
/*     */     
/*     */     public static Builder recipe(ResourceLocation $$0) {
/*  96 */       return (new Builder()).addRecipe($$0);
/*     */     }
/*     */     
/*     */     public Builder addRecipe(ResourceLocation $$0) {
/* 100 */       this.recipes.add($$0);
/* 101 */       return this;
/*     */     }
/*     */     
/*     */     public static Builder function(ResourceLocation $$0) {
/* 105 */       return (new Builder()).runs($$0);
/*     */     }
/*     */     
/*     */     public Builder runs(ResourceLocation $$0) {
/* 109 */       this.function = Optional.of($$0);
/* 110 */       return this;
/*     */     }
/*     */     
/*     */     public AdvancementRewards build() {
/* 114 */       return new AdvancementRewards(this.experience, (List<ResourceLocation>)this.loot.build(), (List<ResourceLocation>)this.recipes.build(), this.function.map(CacheableFunction::new));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\AdvancementRewards.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */