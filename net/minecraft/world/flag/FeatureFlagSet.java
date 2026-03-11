/*    */ package net.minecraft.world.flag;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.HashCommon;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public final class FeatureFlagSet
/*    */ {
/* 10 */   private static final FeatureFlagSet EMPTY = new FeatureFlagSet(null, 0L);
/*    */   
/*    */   public static final int MAX_CONTAINER_SIZE = 64;
/*    */   
/*    */   @Nullable
/*    */   private final FeatureFlagUniverse universe;
/*    */   private final long mask;
/*    */   
/*    */   private FeatureFlagSet(@Nullable FeatureFlagUniverse $$0, long $$1) {
/* 19 */     this.universe = $$0;
/* 20 */     this.mask = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   static FeatureFlagSet create(FeatureFlagUniverse $$0, Collection<FeatureFlag> $$1) {
/* 25 */     if ($$1.isEmpty()) {
/* 26 */       return EMPTY;
/*    */     }
/* 28 */     long $$2 = computeMask($$0, 0L, $$1);
/* 29 */     return new FeatureFlagSet($$0, $$2);
/*    */   }
/*    */   
/*    */   public static FeatureFlagSet of() {
/* 33 */     return EMPTY;
/*    */   }
/*    */   
/*    */   public static FeatureFlagSet of(FeatureFlag $$0) {
/* 37 */     return new FeatureFlagSet($$0.universe, $$0.mask);
/*    */   }
/*    */   
/*    */   public static FeatureFlagSet of(FeatureFlag $$0, FeatureFlag... $$1) {
/* 41 */     long $$2 = ($$1.length == 0) ? $$0.mask : computeMask($$0.universe, $$0.mask, Arrays.asList($$1));
/* 42 */     return new FeatureFlagSet($$0.universe, $$2);
/*    */   }
/*    */   
/*    */   private static long computeMask(FeatureFlagUniverse $$0, long $$1, Iterable<FeatureFlag> $$2) {
/* 46 */     for (FeatureFlag $$3 : $$2) {
/* 47 */       if ($$0 != $$3.universe) {
/* 48 */         throw new IllegalStateException("Mismatched feature universe, expected '" + $$0 + "', but got '" + $$3.universe + "'");
/*    */       }
/* 50 */       $$1 |= $$3.mask;
/*    */     } 
/* 52 */     return $$1;
/*    */   }
/*    */   
/*    */   public boolean contains(FeatureFlag $$0) {
/* 56 */     if (this.universe != $$0.universe) {
/* 57 */       return false;
/*    */     }
/* 59 */     return ((this.mask & $$0.mask) != 0L);
/*    */   }
/*    */   
/*    */   public boolean isSubsetOf(FeatureFlagSet $$0) {
/* 63 */     if (this.universe == null) {
/* 64 */       return true;
/*    */     }
/* 66 */     if (this.universe != $$0.universe) {
/* 67 */       return false;
/*    */     }
/* 69 */     return ((this.mask & ($$0.mask ^ 0xFFFFFFFFFFFFFFFFL)) == 0L);
/*    */   }
/*    */   
/*    */   public FeatureFlagSet join(FeatureFlagSet $$0) {
/* 73 */     if (this.universe == null) {
/* 74 */       return $$0;
/*    */     }
/* 76 */     if ($$0.universe == null) {
/* 77 */       return this;
/*    */     }
/* 79 */     if (this.universe != $$0.universe) {
/* 80 */       throw new IllegalArgumentException("Mismatched set elements: '" + this.universe + "' != '" + $$0.universe + "'");
/*    */     }
/* 82 */     return new FeatureFlagSet(this.universe, this.mask | $$0.mask);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 87 */     if (this == $$0) {
/* 88 */       return true;
/*    */     }
/* 90 */     if ($$0 instanceof FeatureFlagSet) { FeatureFlagSet $$1 = (FeatureFlagSet)$$0; if (this.universe == $$1.universe && this.mask == $$1.mask); }  return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 95 */     return (int)HashCommon.mix(this.mask);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\flag\FeatureFlagSet.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */