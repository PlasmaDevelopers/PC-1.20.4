/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
/*    */ import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class SetItemDamageFunction extends LootItemConditionalFunction {
/* 19 */   private static final Logger LOGGER = LogUtils.getLogger(); public static final Codec<SetItemDamageFunction> CODEC;
/*    */   static {
/* 21 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and($$0.group((App)NumberProviders.CODEC.fieldOf("damage").forGetter(()), (App)Codec.BOOL.fieldOf("add").orElse(Boolean.valueOf(false)).forGetter(()))).apply((Applicative)$$0, SetItemDamageFunction::new));
/*    */   }
/*    */ 
/*    */   
/*    */   private final NumberProvider damage;
/*    */   
/*    */   private final boolean add;
/*    */   
/*    */   private SetItemDamageFunction(List<LootItemCondition> $$0, NumberProvider $$1, boolean $$2) {
/* 30 */     super($$0);
/* 31 */     this.damage = $$1;
/* 32 */     this.add = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemFunctionType getType() {
/* 37 */     return LootItemFunctions.SET_DAMAGE;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 42 */     return this.damage.getReferencedContextParams();
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/* 47 */     if ($$0.isDamageableItem()) {
/* 48 */       int $$2 = $$0.getMaxDamage();
/* 49 */       float $$3 = this.add ? (1.0F - $$0.getDamageValue() / $$2) : 0.0F;
/* 50 */       float $$4 = 1.0F - Mth.clamp(this.damage.getFloat($$1) + $$3, 0.0F, 1.0F);
/* 51 */       $$0.setDamageValue(Mth.floor($$4 * $$2));
/*    */     } else {
/* 53 */       LOGGER.warn("Couldn't set damage of loot item {}", $$0);
/*    */     } 
/* 55 */     return $$0;
/*    */   }
/*    */   
/*    */   public static LootItemConditionalFunction.Builder<?> setDamage(NumberProvider $$0) {
/* 59 */     return simpleBuilder($$1 -> new SetItemDamageFunction($$1, $$0, false));
/*    */   }
/*    */   
/*    */   public static LootItemConditionalFunction.Builder<?> setDamage(NumberProvider $$0, boolean $$1) {
/* 63 */     return simpleBuilder($$2 -> new SetItemDamageFunction($$2, $$0, $$1));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\SetItemDamageFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */