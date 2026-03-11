/*    */ package net.minecraft.world.level.storage.loot.parameters;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Builder
/*    */ {
/* 51 */   private final Set<LootContextParam<?>> required = Sets.newIdentityHashSet();
/* 52 */   private final Set<LootContextParam<?>> optional = Sets.newIdentityHashSet();
/*    */   
/*    */   public Builder required(LootContextParam<?> $$0) {
/* 55 */     if (this.optional.contains($$0)) {
/* 56 */       throw new IllegalArgumentException("Parameter " + $$0.getName() + " is already optional");
/*    */     }
/* 58 */     this.required.add($$0);
/* 59 */     return this;
/*    */   }
/*    */   
/*    */   public Builder optional(LootContextParam<?> $$0) {
/* 63 */     if (this.required.contains($$0)) {
/* 64 */       throw new IllegalArgumentException("Parameter " + $$0.getName() + " is already required");
/*    */     }
/* 66 */     this.optional.add($$0);
/* 67 */     return this;
/*    */   }
/*    */   
/*    */   public LootContextParamSet build() {
/* 71 */     return new LootContextParamSet(this.required, this.optional);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\parameters\LootContextParamSet$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */