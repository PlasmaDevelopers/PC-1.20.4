/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ import net.minecraft.world.Nameable;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ 
/*    */ public class CopyNameFunction extends LootItemConditionalFunction {
/*    */   static {
/* 18 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and((App)NameSource.CODEC.fieldOf("source").forGetter(())).apply((Applicative)$$0, CopyNameFunction::new));
/*    */   }
/*    */   
/*    */   public static final Codec<CopyNameFunction> CODEC;
/*    */   private final NameSource source;
/*    */   
/*    */   private CopyNameFunction(List<LootItemCondition> $$0, NameSource $$1) {
/* 25 */     super($$0);
/* 26 */     this.source = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemFunctionType getType() {
/* 31 */     return LootItemFunctions.COPY_NAME;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 36 */     return (Set<LootContextParam<?>>)ImmutableSet.of(this.source.param);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/* 41 */     Object $$2 = $$1.getParamOrNull(this.source.param);
/* 42 */     if ($$2 instanceof Nameable) { Nameable $$3 = (Nameable)$$2;
/* 43 */       if ($$3.hasCustomName()) {
/* 44 */         $$0.setHoverName($$3.getDisplayName());
/*    */       } }
/*    */     
/* 47 */     return $$0;
/*    */   }
/*    */   
/*    */   public static LootItemConditionalFunction.Builder<?> copyName(NameSource $$0) {
/* 51 */     return simpleBuilder($$1 -> new CopyNameFunction($$1, $$0));
/*    */   }
/*    */   
/*    */   public enum NameSource implements StringRepresentable {
/* 55 */     THIS("this", LootContextParams.THIS_ENTITY),
/* 56 */     KILLER("killer", LootContextParams.KILLER_ENTITY),
/* 57 */     KILLER_PLAYER("killer_player", LootContextParams.LAST_DAMAGE_PLAYER),
/* 58 */     BLOCK_ENTITY("block_entity", LootContextParams.BLOCK_ENTITY);
/*    */     
/* 60 */     public static final Codec<NameSource> CODEC = (Codec<NameSource>)StringRepresentable.fromEnum(NameSource::values); private final String name; final LootContextParam<?> param;
/*    */     static {
/*    */     
/*    */     }
/*    */     
/*    */     NameSource(String $$0, LootContextParam<?> $$1) {
/* 66 */       this.name = $$0;
/* 67 */       this.param = $$1;
/*    */     }
/*    */ 
/*    */     
/*    */     public String getSerializedName() {
/* 72 */       return this.name;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\CopyNameFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */