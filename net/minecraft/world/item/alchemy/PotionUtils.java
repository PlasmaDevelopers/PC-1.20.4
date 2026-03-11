/*     */ package net.minecraft.world.item.alchemy;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.effect.AttributeModifierTemplate;
/*     */ import net.minecraft.world.effect.MobEffect;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffectUtil;
/*     */ import net.minecraft.world.entity.ai.attributes.Attribute;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ 
/*     */ public class PotionUtils
/*     */ {
/*     */   public static final String TAG_CUSTOM_POTION_EFFECTS = "custom_potion_effects";
/*     */   public static final String TAG_CUSTOM_POTION_COLOR = "CustomPotionColor";
/*     */   public static final String TAG_POTION = "Potion";
/*     */   private static final int EMPTY_COLOR = 16253176;
/*  33 */   private static final Component NO_EFFECT = (Component)Component.translatable("effect.none").withStyle(ChatFormatting.GRAY);
/*     */   
/*     */   public static List<MobEffectInstance> getMobEffects(ItemStack $$0) {
/*  36 */     return getAllEffects($$0.getTag());
/*     */   }
/*     */   
/*     */   public static List<MobEffectInstance> getAllEffects(Potion $$0, Collection<MobEffectInstance> $$1) {
/*  40 */     List<MobEffectInstance> $$2 = Lists.newArrayList();
/*     */     
/*  42 */     $$2.addAll($$0.getEffects());
/*  43 */     $$2.addAll($$1);
/*     */     
/*  45 */     return $$2;
/*     */   }
/*     */   
/*     */   public static List<MobEffectInstance> getAllEffects(@Nullable CompoundTag $$0) {
/*  49 */     List<MobEffectInstance> $$1 = Lists.newArrayList();
/*     */     
/*  51 */     $$1.addAll(getPotion($$0).getEffects());
/*  52 */     getCustomEffects($$0, $$1);
/*     */     
/*  54 */     return $$1;
/*     */   }
/*     */   
/*     */   public static List<MobEffectInstance> getCustomEffects(ItemStack $$0) {
/*  58 */     return getCustomEffects($$0.getTag());
/*     */   }
/*     */   
/*     */   public static List<MobEffectInstance> getCustomEffects(@Nullable CompoundTag $$0) {
/*  62 */     List<MobEffectInstance> $$1 = Lists.newArrayList();
/*  63 */     getCustomEffects($$0, $$1);
/*  64 */     return $$1;
/*     */   }
/*     */   
/*     */   public static void getCustomEffects(@Nullable CompoundTag $$0, List<MobEffectInstance> $$1) {
/*  68 */     if ($$0 != null && $$0.contains("custom_potion_effects", 9)) {
/*  69 */       ListTag $$2 = $$0.getList("custom_potion_effects", 10);
/*     */       
/*  71 */       for (int $$3 = 0; $$3 < $$2.size(); $$3++) {
/*  72 */         CompoundTag $$4 = $$2.getCompound($$3);
/*  73 */         MobEffectInstance $$5 = MobEffectInstance.load($$4);
/*  74 */         if ($$5 != null) {
/*  75 */           $$1.add($$5);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getColor(ItemStack $$0) {
/*  82 */     CompoundTag $$1 = $$0.getTag();
/*  83 */     if ($$1 != null && 
/*  84 */       $$1.contains("CustomPotionColor", 99)) {
/*  85 */       return $$1.getInt("CustomPotionColor");
/*     */     }
/*     */     
/*  88 */     return (getPotion($$0) == Potions.EMPTY) ? 16253176 : getColor(getMobEffects($$0));
/*     */   }
/*     */   
/*     */   public static int getColor(Potion $$0) {
/*  92 */     return ($$0 == Potions.EMPTY) ? 16253176 : getColor($$0.getEffects());
/*     */   }
/*     */   
/*     */   public static int getColor(Collection<MobEffectInstance> $$0) {
/*  96 */     int $$1 = 3694022;
/*  97 */     if ($$0.isEmpty()) {
/*  98 */       return 3694022;
/*     */     }
/*     */     
/* 101 */     float $$2 = 0.0F;
/* 102 */     float $$3 = 0.0F;
/* 103 */     float $$4 = 0.0F;
/* 104 */     int $$5 = 0;
/*     */     
/* 106 */     for (MobEffectInstance $$6 : $$0) {
/* 107 */       if (!$$6.isVisible()) {
/*     */         continue;
/*     */       }
/*     */       
/* 111 */       int $$7 = $$6.getEffect().getColor();
/* 112 */       int $$8 = $$6.getAmplifier() + 1;
/* 113 */       $$2 += ($$8 * ($$7 >> 16 & 0xFF)) / 255.0F;
/* 114 */       $$3 += ($$8 * ($$7 >> 8 & 0xFF)) / 255.0F;
/* 115 */       $$4 += ($$8 * ($$7 >> 0 & 0xFF)) / 255.0F;
/* 116 */       $$5 += $$8;
/*     */     } 
/*     */     
/* 119 */     if ($$5 == 0) {
/* 120 */       return 0;
/*     */     }
/*     */     
/* 123 */     $$2 = $$2 / $$5 * 255.0F;
/* 124 */     $$3 = $$3 / $$5 * 255.0F;
/* 125 */     $$4 = $$4 / $$5 * 255.0F;
/*     */     
/* 127 */     return (int)$$2 << 16 | (int)$$3 << 8 | (int)$$4;
/*     */   }
/*     */   
/*     */   public static Potion getPotion(ItemStack $$0) {
/* 131 */     return getPotion($$0.getTag());
/*     */   }
/*     */   
/*     */   public static Potion getPotion(@Nullable CompoundTag $$0) {
/* 135 */     if ($$0 == null) {
/* 136 */       return Potions.EMPTY;
/*     */     }
/*     */     
/* 139 */     return Potion.byName($$0.getString("Potion"));
/*     */   }
/*     */   
/*     */   public static ItemStack setPotion(ItemStack $$0, Potion $$1) {
/* 143 */     ResourceLocation $$2 = BuiltInRegistries.POTION.getKey($$1);
/*     */     
/* 145 */     if ($$1 == Potions.EMPTY) {
/* 146 */       $$0.removeTagKey("Potion");
/*     */     } else {
/* 148 */       $$0.getOrCreateTag().putString("Potion", $$2.toString());
/*     */     } 
/*     */     
/* 151 */     return $$0;
/*     */   }
/*     */   
/*     */   public static ItemStack setCustomEffects(ItemStack $$0, Collection<MobEffectInstance> $$1) {
/* 155 */     if ($$1.isEmpty()) {
/* 156 */       return $$0;
/*     */     }
/*     */     
/* 159 */     CompoundTag $$2 = $$0.getOrCreateTag();
/* 160 */     ListTag $$3 = $$2.getList("custom_potion_effects", 9);
/*     */     
/* 162 */     for (MobEffectInstance $$4 : $$1) {
/* 163 */       $$3.add($$4.save(new CompoundTag()));
/*     */     }
/* 165 */     $$2.put("custom_potion_effects", (Tag)$$3);
/*     */     
/* 167 */     return $$0;
/*     */   }
/*     */   
/*     */   public static void addPotionTooltip(ItemStack $$0, List<Component> $$1, float $$2, float $$3) {
/* 171 */     addPotionTooltip(getMobEffects($$0), $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public static void addPotionTooltip(List<MobEffectInstance> $$0, List<Component> $$1, float $$2, float $$3) {
/* 175 */     List<Pair<Attribute, AttributeModifier>> $$4 = Lists.newArrayList();
/*     */     
/* 177 */     if ($$0.isEmpty()) {
/* 178 */       $$1.add(NO_EFFECT);
/*     */     } else {
/* 180 */       for (MobEffectInstance $$5 : $$0) {
/* 181 */         MutableComponent $$6 = Component.translatable($$5.getDescriptionId());
/* 182 */         MobEffect $$7 = $$5.getEffect();
/*     */         
/* 184 */         Map<Attribute, AttributeModifierTemplate> $$8 = $$7.getAttributeModifiers();
/* 185 */         if (!$$8.isEmpty()) {
/* 186 */           for (Map.Entry<Attribute, AttributeModifierTemplate> $$9 : $$8.entrySet()) {
/* 187 */             $$4.add(new Pair($$9.getKey(), ((AttributeModifierTemplate)$$9.getValue()).create($$5.getAmplifier())));
/*     */           }
/*     */         }
/*     */         
/* 191 */         if ($$5.getAmplifier() > 0) {
/* 192 */           $$6 = Component.translatable("potion.withAmplifier", new Object[] { $$6, Component.translatable("potion.potency." + $$5.getAmplifier()) });
/*     */         }
/*     */         
/* 195 */         if (!$$5.endsWithin(20)) {
/* 196 */           $$6 = Component.translatable("potion.withDuration", new Object[] { $$6, MobEffectUtil.formatDuration($$5, $$2, $$3) });
/*     */         }
/*     */         
/* 199 */         $$1.add($$6.withStyle($$7.getCategory().getTooltipFormatting()));
/*     */       } 
/*     */     } 
/*     */     
/* 203 */     if (!$$4.isEmpty()) {
/* 204 */       $$1.add(CommonComponents.EMPTY);
/* 205 */       $$1.add(Component.translatable("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));
/*     */       
/* 207 */       for (Pair<Attribute, AttributeModifier> $$10 : $$4) {
/* 208 */         double $$14; AttributeModifier $$11 = (AttributeModifier)$$10.getSecond();
/* 209 */         double $$12 = $$11.getAmount();
/*     */ 
/*     */         
/* 212 */         if ($$11.getOperation() == AttributeModifier.Operation.MULTIPLY_BASE || $$11.getOperation() == AttributeModifier.Operation.MULTIPLY_TOTAL) {
/* 213 */           double $$13 = $$11.getAmount() * 100.0D;
/*     */         } else {
/* 215 */           $$14 = $$11.getAmount();
/*     */         } 
/*     */         
/* 218 */         if ($$12 > 0.0D) {
/* 219 */           $$1.add(
/* 220 */               Component.translatable("attribute.modifier.plus." + $$11.getOperation().toValue(), new Object[] { ItemStack.ATTRIBUTE_MODIFIER_FORMAT
/* 221 */                   .format($$14), 
/* 222 */                   Component.translatable(((Attribute)$$10.getFirst()).getDescriptionId())
/* 223 */                 }).withStyle(ChatFormatting.BLUE)); continue;
/*     */         } 
/* 225 */         if ($$12 < 0.0D) {
/* 226 */           $$14 *= -1.0D;
/* 227 */           $$1.add(
/* 228 */               Component.translatable("attribute.modifier.take." + $$11.getOperation().toValue(), new Object[] { ItemStack.ATTRIBUTE_MODIFIER_FORMAT
/* 229 */                   .format($$14), 
/* 230 */                   Component.translatable(((Attribute)$$10.getFirst()).getDescriptionId())
/* 231 */                 }).withStyle(ChatFormatting.RED));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\alchemy\PotionUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */