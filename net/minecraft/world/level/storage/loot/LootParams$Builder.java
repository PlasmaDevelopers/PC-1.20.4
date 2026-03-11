/*     */ package net.minecraft.world.level.storage.loot;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
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
/*     */   private final ServerLevel level;
/*  77 */   private final Map<LootContextParam<?>, Object> params = Maps.newIdentityHashMap();
/*  78 */   private final Map<ResourceLocation, LootParams.DynamicDrop> dynamicDrops = Maps.newHashMap();
/*     */   private float luck;
/*     */   
/*     */   public Builder(ServerLevel $$0) {
/*  82 */     this.level = $$0;
/*     */   }
/*     */   
/*     */   public ServerLevel getLevel() {
/*  86 */     return this.level;
/*     */   }
/*     */   
/*     */   public <T> Builder withParameter(LootContextParam<T> $$0, T $$1) {
/*  90 */     this.params.put($$0, $$1);
/*  91 */     return this;
/*     */   }
/*     */   
/*     */   public <T> Builder withOptionalParameter(LootContextParam<T> $$0, @Nullable T $$1) {
/*  95 */     if ($$1 == null) {
/*  96 */       this.params.remove($$0);
/*     */     } else {
/*  98 */       this.params.put($$0, $$1);
/*     */     } 
/* 100 */     return this;
/*     */   }
/*     */   
/*     */   public <T> T getParameter(LootContextParam<T> $$0) {
/* 104 */     T $$1 = (T)this.params.get($$0);
/* 105 */     if ($$1 == null) {
/* 106 */       throw new NoSuchElementException($$0.getName().toString());
/*     */     }
/*     */     
/* 109 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T> T getOptionalParameter(LootContextParam<T> $$0) {
/* 115 */     return (T)this.params.get($$0);
/*     */   }
/*     */   
/*     */   public Builder withDynamicDrop(ResourceLocation $$0, LootParams.DynamicDrop $$1) {
/* 119 */     LootParams.DynamicDrop $$2 = this.dynamicDrops.put($$0, $$1);
/*     */     
/* 121 */     if ($$2 != null) {
/* 122 */       throw new IllegalStateException("Duplicated dynamic drop '" + this.dynamicDrops + "'");
/*     */     }
/*     */     
/* 125 */     return this;
/*     */   }
/*     */   
/*     */   public Builder withLuck(float $$0) {
/* 129 */     this.luck = $$0;
/* 130 */     return this;
/*     */   }
/*     */   
/*     */   public LootParams create(LootContextParamSet $$0) {
/* 134 */     Sets.SetView setView1 = Sets.difference(this.params.keySet(), $$0.getAllowed());
/* 135 */     if (!setView1.isEmpty()) {
/* 136 */       throw new IllegalArgumentException("Parameters not allowed in this parameter set: " + setView1);
/*     */     }
/*     */     
/* 139 */     Sets.SetView setView2 = Sets.difference($$0.getRequired(), this.params.keySet());
/* 140 */     if (!setView2.isEmpty()) {
/* 141 */       throw new IllegalArgumentException("Missing required parameters: " + setView2);
/*     */     }
/*     */     
/* 144 */     return new LootParams(this.level, this.params, this.dynamicDrops, this.luck);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\LootParams$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */