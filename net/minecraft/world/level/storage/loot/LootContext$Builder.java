/*     */ package net.minecraft.world.level.storage.loot;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Builder
/*     */ {
/*     */   private final LootParams params;
/*     */   @Nullable
/*     */   private RandomSource random;
/*     */   
/*     */   public Builder(LootParams $$0) {
/*  86 */     this.params = $$0;
/*     */   }
/*     */   
/*     */   public Builder withOptionalRandomSeed(long $$0) {
/*  90 */     if ($$0 != 0L) {
/*  91 */       this.random = RandomSource.create($$0);
/*     */     }
/*  93 */     return this;
/*     */   }
/*     */   
/*     */   public ServerLevel getLevel() {
/*  97 */     return this.params.getLevel();
/*     */   }
/*     */   
/*     */   public LootContext create(Optional<ResourceLocation> $$0) {
/* 101 */     ServerLevel $$1 = getLevel();
/* 102 */     MinecraftServer $$2 = $$1.getServer();
/*     */ 
/*     */     
/* 105 */     Objects.requireNonNull($$1); RandomSource $$3 = Optional.<RandomSource>ofNullable(this.random).or(() -> { Objects.requireNonNull($$1); return $$0.map($$1::getRandomSequence); }).orElseGet($$1::getRandom);
/* 106 */     return new LootContext(this.params, $$3, $$2.getLootData());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\LootContext$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */