/*    */ package net.minecraft.world.level.levelgen.feature.stateproviders;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.IntegerProperty;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class RandomizedIntStateProvider extends BlockStateProvider {
/*    */   static {
/* 17 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BlockStateProvider.CODEC.fieldOf("source").forGetter(()), (App)Codec.STRING.fieldOf("property").forGetter(()), (App)IntProvider.CODEC.fieldOf("values").forGetter(())).apply((Applicative)$$0, RandomizedIntStateProvider::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<RandomizedIntStateProvider> CODEC;
/*    */   
/*    */   private final BlockStateProvider source;
/*    */   private final String propertyName;
/*    */   @Nullable
/*    */   private IntegerProperty property;
/*    */   private final IntProvider values;
/*    */   
/*    */   public RandomizedIntStateProvider(BlockStateProvider $$0, IntegerProperty $$1, IntProvider $$2) {
/* 30 */     this.source = $$0;
/* 31 */     this.property = $$1;
/* 32 */     this.propertyName = $$1.getName();
/* 33 */     this.values = $$2;
/*    */     
/* 35 */     Collection<Integer> $$3 = $$1.getPossibleValues();
/* 36 */     for (int $$4 = $$2.getMinValue(); $$4 <= $$2.getMaxValue(); $$4++) {
/* 37 */       if (!$$3.contains(Integer.valueOf($$4))) {
/* 38 */         throw new IllegalArgumentException("Property value out of range: " + $$1.getName() + ": " + $$4);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public RandomizedIntStateProvider(BlockStateProvider $$0, String $$1, IntProvider $$2) {
/* 44 */     this.source = $$0;
/* 45 */     this.propertyName = $$1;
/* 46 */     this.values = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockStateProviderType<?> type() {
/* 51 */     return BlockStateProviderType.RANDOMIZED_INT_STATE_PROVIDER;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getState(RandomSource $$0, BlockPos $$1) {
/* 56 */     BlockState $$2 = this.source.getState($$0, $$1);
/* 57 */     if (this.property == null || !$$2.hasProperty((Property)this.property)) {
/* 58 */       this.property = findProperty($$2, this.propertyName);
/*    */     }
/* 60 */     return (BlockState)$$2.setValue((Property)this.property, Integer.valueOf(this.values.sample($$0)));
/*    */   }
/*    */   
/*    */   private static IntegerProperty findProperty(BlockState $$0, String $$1) {
/* 64 */     Collection<Property<?>> $$2 = $$0.getProperties();
/*    */ 
/*    */ 
/*    */     
/* 68 */     Optional<IntegerProperty> $$3 = $$2.stream().filter($$1 -> $$1.getName().equals($$0)).filter($$0 -> $$0 instanceof IntegerProperty).map($$0 -> (IntegerProperty)$$0).findAny();
/* 69 */     return $$3.<Throwable>orElseThrow(() -> new IllegalArgumentException("Illegal property: " + $$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\stateproviders\RandomizedIntStateProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */