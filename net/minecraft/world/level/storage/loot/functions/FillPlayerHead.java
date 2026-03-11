/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ 
/*    */ public class FillPlayerHead extends LootItemConditionalFunction {
/*    */   static {
/* 21 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and((App)LootContext.EntityTarget.CODEC.fieldOf("entity").forGetter(())).apply((Applicative)$$0, FillPlayerHead::new));
/*    */   }
/*    */   
/*    */   public static final Codec<FillPlayerHead> CODEC;
/*    */   private final LootContext.EntityTarget entityTarget;
/*    */   
/*    */   public FillPlayerHead(List<LootItemCondition> $$0, LootContext.EntityTarget $$1) {
/* 28 */     super($$0);
/* 29 */     this.entityTarget = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemFunctionType getType() {
/* 34 */     return LootItemFunctions.FILL_PLAYER_HEAD;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 39 */     return (Set<LootContextParam<?>>)ImmutableSet.of(this.entityTarget.getParam());
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/* 44 */     if ($$0.is(Items.PLAYER_HEAD)) {
/* 45 */       Object object = $$1.getParamOrNull(this.entityTarget.getParam()); if (object instanceof Player) { Player $$2 = (Player)object;
/* 46 */         GameProfile $$3 = $$2.getGameProfile();
/* 47 */         $$0.getOrCreateTag().put("SkullOwner", (Tag)NbtUtils.writeGameProfile(new CompoundTag(), $$3)); }
/*    */     
/*    */     } 
/* 50 */     return $$0;
/*    */   }
/*    */   
/*    */   public static LootItemConditionalFunction.Builder<?> fillPlayerHead(LootContext.EntityTarget $$0) {
/* 54 */     return simpleBuilder($$1 -> new FillPlayerHead($$1, $$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\FillPlayerHead.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */