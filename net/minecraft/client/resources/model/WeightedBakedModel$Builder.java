/*    */ package net.minecraft.client.resources.model;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.random.WeightedEntry;
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
/* 72 */   private final List<WeightedEntry.Wrapper<BakedModel>> list = Lists.newArrayList();
/*    */   
/*    */   public Builder add(@Nullable BakedModel $$0, int $$1) {
/* 75 */     if ($$0 != null) {
/* 76 */       this.list.add(WeightedEntry.wrap($$0, $$1));
/*    */     }
/* 78 */     return this;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public BakedModel build() {
/* 83 */     if (this.list.isEmpty()) {
/* 84 */       return null;
/*    */     }
/* 86 */     if (this.list.size() == 1) {
/* 87 */       return (BakedModel)((WeightedEntry.Wrapper)this.list.get(0)).getData();
/*    */     }
/* 89 */     return new WeightedBakedModel(this.list);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\model\WeightedBakedModel$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */