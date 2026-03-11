/*     */ package net.minecraft.world.level.biome;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.util.ExtraCodecs;
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
/*     */ public class ParameterList<T>
/*     */ {
/*     */   private final List<Pair<Climate.ParameterPoint, T>> values;
/*     */   private final Climate.RTree<T> index;
/*     */   
/*     */   public static <T> Codec<ParameterList<T>> codec(MapCodec<T> $$0) {
/* 282 */     return ExtraCodecs.nonEmptyList(RecordCodecBuilder.create($$1 -> $$1.group((App)Climate.ParameterPoint.CODEC.fieldOf("parameters").forGetter(Pair::getFirst), (App)$$0.forGetter(Pair::getSecond)).apply((Applicative)$$1, Pair::of))
/*     */ 
/*     */         
/* 285 */         .listOf()).xmap(ParameterList::new, ParameterList::values);
/*     */   }
/*     */   
/*     */   public ParameterList(List<Pair<Climate.ParameterPoint, T>> $$0) {
/* 289 */     this.values = $$0;
/* 290 */     this.index = Climate.RTree.create($$0);
/*     */   }
/*     */   
/*     */   public List<Pair<Climate.ParameterPoint, T>> values() {
/* 294 */     return this.values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T findValue(Climate.TargetPoint $$0) {
/* 301 */     return findValueIndex($$0);
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public T findValueBruteForce(Climate.TargetPoint $$0) {
/* 306 */     Iterator<Pair<Climate.ParameterPoint, T>> $$1 = values().iterator();
/*     */ 
/*     */     
/* 309 */     Pair<Climate.ParameterPoint, T> $$2 = $$1.next();
/* 310 */     long $$3 = ((Climate.ParameterPoint)$$2.getFirst()).fitness($$0);
/* 311 */     T $$4 = (T)$$2.getSecond();
/*     */     
/* 313 */     while ($$1.hasNext()) {
/* 314 */       Pair<Climate.ParameterPoint, T> $$5 = $$1.next();
/* 315 */       long $$6 = ((Climate.ParameterPoint)$$5.getFirst()).fitness($$0);
/* 316 */       if ($$6 < $$3) {
/* 317 */         $$3 = $$6;
/* 318 */         $$4 = (T)$$5.getSecond();
/*     */       } 
/*     */     } 
/* 321 */     return $$4;
/*     */   }
/*     */   
/*     */   public T findValueIndex(Climate.TargetPoint $$0) {
/* 325 */     return findValueIndex($$0, Climate.RTree.Node::distance);
/*     */   }
/*     */   
/*     */   protected T findValueIndex(Climate.TargetPoint $$0, Climate.DistanceMetric<T> $$1) {
/* 329 */     return this.index.search($$0, $$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\Climate$ParameterList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */