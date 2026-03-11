/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import java.util.function.UnaryOperator;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.ComponentSerialization;
/*    */ import net.minecraft.network.chat.ComponentUtils;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class SetNameFunction extends LootItemConditionalFunction {
/* 27 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   static {
/* 29 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and($$0.group((App)ExtraCodecs.strictOptionalField(ComponentSerialization.CODEC, "name").forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)LootContext.EntityTarget.CODEC, "entity").forGetter(()))).apply((Applicative)$$0, SetNameFunction::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<SetNameFunction> CODEC;
/*    */   private final Optional<Component> name;
/*    */   private final Optional<LootContext.EntityTarget> resolutionContext;
/*    */   
/*    */   private SetNameFunction(List<LootItemCondition> $$0, Optional<Component> $$1, Optional<LootContext.EntityTarget> $$2) {
/* 38 */     super($$0);
/* 39 */     this.name = $$1;
/* 40 */     this.resolutionContext = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemFunctionType getType() {
/* 45 */     return LootItemFunctions.SET_NAME;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 50 */     return this.resolutionContext.<Set<LootContextParam<?>>>map($$0 -> Set.of($$0.getParam())).orElse(Set.of());
/*    */   }
/*    */   
/*    */   public static UnaryOperator<Component> createResolver(LootContext $$0, @Nullable LootContext.EntityTarget $$1) {
/* 54 */     if ($$1 != null) {
/* 55 */       Entity $$2 = (Entity)$$0.getParamOrNull($$1.getParam());
/* 56 */       if ($$2 != null) {
/*    */ 
/*    */         
/* 59 */         CommandSourceStack $$3 = $$2.createCommandSourceStack().withPermission(2);
/* 60 */         return $$2 -> {
/*    */             try {
/*    */               return (Component)ComponentUtils.updateForEntity($$0, $$2, $$1, 0);
/* 63 */             } catch (CommandSyntaxException $$3) {
/*    */               LOGGER.warn("Failed to resolve text component", (Throwable)$$3);
/*    */               return $$2;
/*    */             } 
/*    */           };
/*    */       } 
/*    */     } 
/* 70 */     return $$0 -> $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/* 75 */     this.name.ifPresent($$2 -> $$0.setHoverName(createResolver($$1, this.resolutionContext.orElse(null)).apply($$2)));
/* 76 */     return $$0;
/*    */   }
/*    */   
/*    */   public static LootItemConditionalFunction.Builder<?> setName(Component $$0) {
/* 80 */     return simpleBuilder($$1 -> new SetNameFunction($$1, Optional.of($$0), Optional.empty()));
/*    */   }
/*    */   
/*    */   public static LootItemConditionalFunction.Builder<?> setName(Component $$0, LootContext.EntityTarget $$1) {
/* 84 */     return simpleBuilder($$2 -> new SetNameFunction($$2, Optional.of($$0), Optional.of($$1)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\SetNameFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */