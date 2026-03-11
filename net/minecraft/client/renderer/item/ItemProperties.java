/*     */ package net.minecraft.client.renderer.item;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.core.GlobalPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.data.models.ItemModelGenerators;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.HumanoidArm;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.BundleItem;
/*     */ import net.minecraft.world.item.CompassItem;
/*     */ import net.minecraft.world.item.CrossbowItem;
/*     */ import net.minecraft.world.item.ElytraItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.armortrim.ArmorTrim;
/*     */ import net.minecraft.world.item.armortrim.TrimMaterial;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.LightBlock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemProperties
/*     */ {
/*  37 */   private static final Map<ResourceLocation, ItemPropertyFunction> GENERIC_PROPERTIES = Maps.newHashMap();
/*     */   
/*     */   private static final String TAG_CUSTOM_MODEL_DATA = "CustomModelData";
/*     */   
/*  41 */   private static final ResourceLocation DAMAGED = new ResourceLocation("damaged"); private static final ClampedItemPropertyFunction PROPERTY_DAMAGED;
/*  42 */   private static final ResourceLocation DAMAGE = new ResourceLocation("damage"); private static final ClampedItemPropertyFunction PROPERTY_DAMAGE;
/*     */   static {
/*  44 */     PROPERTY_DAMAGED = (($$0, $$1, $$2, $$3) -> $$0.isDamaged() ? 1.0F : 0.0F);
/*  45 */     PROPERTY_DAMAGE = (($$0, $$1, $$2, $$3) -> Mth.clamp($$0.getDamageValue() / $$0.getMaxDamage(), 0.0F, 1.0F));
/*     */   }
/*     */   private static ClampedItemPropertyFunction registerGeneric(ResourceLocation $$0, ClampedItemPropertyFunction $$1) {
/*  48 */     GENERIC_PROPERTIES.put($$0, $$1);
/*  49 */     return $$1;
/*     */   }
/*     */   
/*     */   private static void registerCustomModelData(ItemPropertyFunction $$0) {
/*  53 */     GENERIC_PROPERTIES.put(new ResourceLocation("custom_model_data"), $$0);
/*     */   }
/*     */   
/*  56 */   private static final Map<Item, Map<ResourceLocation, ItemPropertyFunction>> PROPERTIES = Maps.newHashMap();
/*     */   
/*     */   private static void register(Item $$0, ResourceLocation $$1, ClampedItemPropertyFunction $$2) {
/*  59 */     ((Map<ResourceLocation, ClampedItemPropertyFunction>)PROPERTIES.computeIfAbsent($$0, $$0 -> Maps.newHashMap())).put($$1, $$2);
/*     */   }
/*     */   
/*     */   static {
/*  63 */     registerGeneric(new ResourceLocation("lefthanded"), ($$0, $$1, $$2, $$3) -> 
/*  64 */         ($$2 == null || $$2.getMainArm() == HumanoidArm.RIGHT) ? 0.0F : 1.0F);
/*     */ 
/*     */     
/*  67 */     registerGeneric(new ResourceLocation("cooldown"), ($$0, $$1, $$2, $$3) -> ($$2 instanceof Player) ? ((Player)$$2).getCooldowns().getCooldownPercent($$0.getItem(), 0.0F) : 0.0F);
/*     */ 
/*     */ 
/*     */     
/*  71 */     ClampedItemPropertyFunction $$0 = ($$0, $$1, $$2, $$3) -> !$$0.is(ItemTags.TRIMMABLE_ARMOR) ? Float.NEGATIVE_INFINITY : (($$1 == null) ? 0.0F : ((Float)ArmorTrim.getTrim($$1.registryAccess(), $$0, true).map(ArmorTrim::material).map(Holder::value).map(TrimMaterial::itemModelIndex).orElse(Float.valueOf(0.0F))).floatValue());
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
/*  82 */     registerGeneric(ItemModelGenerators.TRIM_TYPE_PREDICATE_ID, $$0);
/*     */     
/*  84 */     registerCustomModelData(($$0, $$1, $$2, $$3) -> $$0.hasTag() ? $$0.getTag().getInt("CustomModelData") : 0.0F);
/*     */ 
/*     */ 
/*     */     
/*  88 */     register(Items.BOW, new ResourceLocation("pull"), ($$0, $$1, $$2, $$3) -> ($$2 == null) ? 0.0F : (($$2.getUseItem() != $$0) ? 0.0F : (($$0.getUseDuration() - $$2.getUseItemRemainingTicks()) / 20.0F)));
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
/*  99 */     register(Items.BRUSH, new ResourceLocation("brushing"), ($$0, $$1, $$2, $$3) -> 
/* 100 */         ($$2 == null || $$2.getUseItem() != $$0) ? 0.0F : (($$2.getUseItemRemainingTicks() % 10) / 10.0F));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     register(Items.BOW, new ResourceLocation("pulling"), ($$0, $$1, $$2, $$3) -> 
/* 108 */         ($$2 != null && $$2.isUsingItem() && $$2.getUseItem() == $$0) ? 1.0F : 0.0F);
/*     */ 
/*     */     
/* 111 */     register(Items.BUNDLE, new ResourceLocation("filled"), ($$0, $$1, $$2, $$3) -> BundleItem.getFullnessDisplay($$0));
/*     */     
/* 113 */     register(Items.CLOCK, new ResourceLocation("time"), new ClampedItemPropertyFunction()
/*     */         {
/*     */           private double rotation;
/*     */           private double rota;
/*     */           private long lastUpdateTick;
/*     */           
/*     */           public float unclampedCall(ItemStack $$0, @Nullable ClientLevel $$1, @Nullable LivingEntity $$2, int $$3) {
/* 120 */             Entity $$4 = ($$2 != null) ? (Entity)$$2 : $$0.getEntityRepresentation();
/*     */             
/* 122 */             if ($$4 == null) {
/* 123 */               return 0.0F;
/*     */             }
/*     */             
/* 126 */             if ($$1 == null && $$4.level() instanceof ClientLevel) {
/* 127 */               $$1 = (ClientLevel)$$4.level();
/*     */             }
/*     */             
/* 130 */             if ($$1 == null) {
/* 131 */               return 0.0F;
/*     */             }
/*     */ 
/*     */             
/* 135 */             if ($$1.dimensionType().natural()) {
/* 136 */               double $$5 = $$1.getTimeOfDay(1.0F);
/*     */             } else {
/* 138 */               $$6 = Math.random();
/*     */             } 
/*     */             
/* 141 */             double $$6 = wobble((Level)$$1, $$6);
/*     */             
/* 143 */             return (float)$$6;
/*     */           }
/*     */           
/*     */           private double wobble(Level $$0, double $$1) {
/* 147 */             if ($$0.getGameTime() != this.lastUpdateTick) {
/* 148 */               this.lastUpdateTick = $$0.getGameTime();
/*     */               
/* 150 */               double $$2 = $$1 - this.rotation;
/* 151 */               $$2 = Mth.positiveModulo($$2 + 0.5D, 1.0D) - 0.5D;
/*     */               
/* 153 */               this.rota += $$2 * 0.1D;
/* 154 */               this.rota *= 0.9D;
/* 155 */               this.rotation = Mth.positiveModulo(this.rotation + this.rota, 1.0D);
/*     */             } 
/*     */             
/* 158 */             return this.rotation;
/*     */           }
/*     */         });
/*     */     
/* 162 */     register(Items.COMPASS, new ResourceLocation("angle"), new CompassItemPropertyFunction(($$0, $$1, $$2) -> CompassItem.isLodestoneCompass($$1) ? CompassItem.getLodestonePosition($$1.getOrCreateTag()) : CompassItem.getSpawnPosition((Level)$$0)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 170 */     register(Items.RECOVERY_COMPASS, new ResourceLocation("angle"), new CompassItemPropertyFunction(($$0, $$1, $$2) -> {
/*     */             if ($$2 instanceof Player) {
/*     */               Player $$3 = (Player)$$2;
/*     */               return $$3.getLastDeathLocation().orElse(null);
/*     */             } 
/*     */             return null;
/*     */           }));
/* 177 */     register(Items.CROSSBOW, new ResourceLocation("pull"), ($$0, $$1, $$2, $$3) -> ($$2 == null) ? 0.0F : (CrossbowItem.isCharged($$0) ? 0.0F : (($$0.getUseDuration() - $$2.getUseItemRemainingTicks()) / CrossbowItem.getChargeDuration($$0))));
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
/* 189 */     register(Items.CROSSBOW, new ResourceLocation("pulling"), ($$0, $$1, $$2, $$3) -> 
/* 190 */         ($$2 != null && $$2.isUsingItem() && $$2.getUseItem() == $$0 && !CrossbowItem.isCharged($$0)) ? 1.0F : 0.0F);
/*     */ 
/*     */     
/* 193 */     register(Items.CROSSBOW, new ResourceLocation("charged"), ($$0, $$1, $$2, $$3) -> CrossbowItem.isCharged($$0) ? 1.0F : 0.0F);
/*     */ 
/*     */ 
/*     */     
/* 197 */     register(Items.CROSSBOW, new ResourceLocation("firework"), ($$0, $$1, $$2, $$3) -> 
/* 198 */         (CrossbowItem.isCharged($$0) && CrossbowItem.containsChargedProjectile($$0, Items.FIREWORK_ROCKET)) ? 1.0F : 0.0F);
/*     */ 
/*     */     
/* 201 */     register(Items.ELYTRA, new ResourceLocation("broken"), ($$0, $$1, $$2, $$3) -> ElytraItem.isFlyEnabled($$0) ? 0.0F : 1.0F);
/*     */ 
/*     */ 
/*     */     
/* 205 */     register(Items.FISHING_ROD, new ResourceLocation("cast"), ($$0, $$1, $$2, $$3) -> {
/*     */           if ($$2 == null) {
/*     */             return 0.0F;
/*     */           }
/*     */           
/*     */           boolean $$4 = ($$2.getMainHandItem() == $$0);
/*     */           boolean $$5 = ($$2.getOffhandItem() == $$0);
/*     */           if ($$2.getMainHandItem().getItem() instanceof net.minecraft.world.item.FishingRodItem) {
/*     */             $$5 = false;
/*     */           }
/* 215 */           return (($$4 || $$5) && $$2 instanceof Player && ((Player)$$2).fishing != null) ? 1.0F : 0.0F;
/*     */         });
/*     */     
/* 218 */     register(Items.SHIELD, new ResourceLocation("blocking"), ($$0, $$1, $$2, $$3) -> 
/* 219 */         ($$2 != null && $$2.isUsingItem() && $$2.getUseItem() == $$0) ? 1.0F : 0.0F);
/*     */ 
/*     */     
/* 222 */     register(Items.TRIDENT, new ResourceLocation("throwing"), ($$0, $$1, $$2, $$3) -> 
/* 223 */         ($$2 != null && $$2.isUsingItem() && $$2.getUseItem() == $$0) ? 1.0F : 0.0F);
/*     */ 
/*     */     
/* 226 */     register(Items.LIGHT, new ResourceLocation("level"), ($$0, $$1, $$2, $$3) -> {
/*     */           CompoundTag $$4 = $$0.getTagElement("BlockStateTag");
/*     */           try {
/*     */             if ($$4 != null) {
/*     */               Tag $$5 = $$4.get(LightBlock.LEVEL.getName());
/*     */               if ($$5 != null) {
/*     */                 return Integer.parseInt($$5.getAsString()) / 16.0F;
/*     */               }
/*     */             } 
/* 235 */           } catch (NumberFormatException numberFormatException) {}
/*     */           
/*     */           return 1.0F;
/*     */         });
/*     */     
/* 240 */     register(Items.GOAT_HORN, new ResourceLocation("tooting"), ($$0, $$1, $$2, $$3) -> 
/* 241 */         ($$2 != null && $$2.isUsingItem() && $$2.getUseItem() == $$0) ? 1.0F : 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static ItemPropertyFunction getProperty(Item $$0, ResourceLocation $$1) {
/* 247 */     if ($$0.getMaxDamage() > 0) {
/* 248 */       if (DAMAGE.equals($$1)) {
/* 249 */         return PROPERTY_DAMAGE;
/*     */       }
/* 251 */       if (DAMAGED.equals($$1)) {
/* 252 */         return PROPERTY_DAMAGED;
/*     */       }
/*     */     } 
/*     */     
/* 256 */     ItemPropertyFunction $$2 = GENERIC_PROPERTIES.get($$1);
/* 257 */     if ($$2 != null) {
/* 258 */       return $$2;
/*     */     }
/*     */     
/* 261 */     Map<ResourceLocation, ItemPropertyFunction> $$3 = PROPERTIES.get($$0);
/* 262 */     if ($$3 == null) {
/* 263 */       return null;
/*     */     }
/*     */     
/* 266 */     return $$3.get($$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\item\ItemProperties.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */