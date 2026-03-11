/*     */ package net.minecraft.world.damagesource;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.network.chat.ClickEvent;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.chat.HoverEvent;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ public class CombatTracker
/*     */ {
/*     */   public static final int RESET_DAMAGE_STATUS_TIME = 100;
/*     */   public static final int RESET_COMBAT_STATUS_TIME = 300;
/*  22 */   private static final Style INTENTIONAL_GAME_DESIGN_STYLE = Style.EMPTY
/*  23 */     .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://bugs.mojang.com/browse/MCPE-28723"))
/*  24 */     .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("MCPE-28723")));
/*     */   
/*  26 */   private final List<CombatEntry> entries = Lists.newArrayList();
/*     */   private final LivingEntity mob;
/*     */   private int lastDamageTime;
/*     */   private int combatStartTime;
/*     */   private int combatEndTime;
/*     */   private boolean inCombat;
/*     */   private boolean takingDamage;
/*     */   
/*     */   public CombatTracker(LivingEntity $$0) {
/*  35 */     this.mob = $$0;
/*     */   }
/*     */   
/*     */   public void recordDamage(DamageSource $$0, float $$1) {
/*  39 */     recheckStatus();
/*     */     
/*  41 */     FallLocation $$2 = FallLocation.getCurrentFallLocation(this.mob);
/*  42 */     CombatEntry $$3 = new CombatEntry($$0, $$1, $$2, this.mob.fallDistance);
/*     */     
/*  44 */     this.entries.add($$3);
/*  45 */     this.lastDamageTime = this.mob.tickCount;
/*  46 */     this.takingDamage = true;
/*     */     
/*  48 */     if (!this.inCombat && this.mob.isAlive() && shouldEnterCombat($$0)) {
/*  49 */       this.inCombat = true;
/*  50 */       this.combatStartTime = this.mob.tickCount;
/*  51 */       this.combatEndTime = this.combatStartTime;
/*  52 */       this.mob.onEnterCombat();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean shouldEnterCombat(DamageSource $$0) {
/*  57 */     return $$0.getEntity() instanceof LivingEntity;
/*     */   }
/*     */   
/*     */   private Component getMessageForAssistedFall(Entity $$0, Component $$1, String $$2, String $$3) {
/*  61 */     LivingEntity $$4 = (LivingEntity)$$0; ItemStack $$5 = ($$0 instanceof LivingEntity) ? $$4.getMainHandItem() : ItemStack.EMPTY;
/*     */     
/*  63 */     if (!$$5.isEmpty() && $$5.hasCustomHoverName()) {
/*  64 */       return (Component)Component.translatable($$2, new Object[] { this.mob.getDisplayName(), $$1, $$5.getDisplayName() });
/*     */     }
/*     */     
/*  67 */     return (Component)Component.translatable($$3, new Object[] { this.mob.getDisplayName(), $$1 });
/*     */   }
/*     */   
/*     */   private Component getFallMessage(CombatEntry $$0, @Nullable Entity $$1) {
/*  71 */     DamageSource $$2 = $$0.source();
/*     */     
/*  73 */     if ($$2.is(DamageTypeTags.IS_FALL) || $$2.is(DamageTypeTags.ALWAYS_MOST_SIGNIFICANT_FALL)) {
/*  74 */       FallLocation $$3 = Objects.<FallLocation>requireNonNullElse($$0.fallLocation(), FallLocation.GENERIC);
/*  75 */       return (Component)Component.translatable($$3.languageKey(), new Object[] { this.mob.getDisplayName() });
/*     */     } 
/*     */     
/*  78 */     Component $$4 = getDisplayName($$1);
/*  79 */     Entity $$5 = $$2.getEntity();
/*  80 */     Component $$6 = getDisplayName($$5);
/*     */ 
/*     */     
/*  83 */     if ($$6 != null && !$$6.equals($$4)) {
/*  84 */       return getMessageForAssistedFall($$5, $$6, "death.fell.assist.item", "death.fell.assist");
/*     */     }
/*     */     
/*  87 */     if ($$4 != null) {
/*  88 */       return getMessageForAssistedFall($$1, $$4, "death.fell.finish.item", "death.fell.finish");
/*     */     }
/*     */     
/*  91 */     return (Component)Component.translatable("death.fell.killer", new Object[] { this.mob.getDisplayName() });
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static Component getDisplayName(@Nullable Entity $$0) {
/*  96 */     return ($$0 == null) ? null : $$0.getDisplayName();
/*     */   }
/*     */   
/*     */   public Component getDeathMessage() {
/* 100 */     if (this.entries.isEmpty()) {
/* 101 */       return (Component)Component.translatable("death.attack.generic", new Object[] { this.mob.getDisplayName() });
/*     */     }
/*     */     
/* 104 */     CombatEntry $$0 = this.entries.get(this.entries.size() - 1);
/* 105 */     DamageSource $$1 = $$0.source();
/*     */     
/* 107 */     CombatEntry $$2 = getMostSignificantFall();
/*     */     
/* 109 */     DeathMessageType $$3 = $$1.type().deathMessageType();
/* 110 */     if ($$3 == DeathMessageType.FALL_VARIANTS && $$2 != null) {
/* 111 */       return getFallMessage($$2, $$1.getEntity());
/*     */     }
/*     */     
/* 114 */     if ($$3 == DeathMessageType.INTENTIONAL_GAME_DESIGN) {
/* 115 */       String $$4 = "death.attack." + $$1.getMsgId();
/* 116 */       MutableComponent mutableComponent = ComponentUtils.wrapInSquareBrackets((Component)Component.translatable($$4 + ".link")).withStyle(INTENTIONAL_GAME_DESIGN_STYLE);
/* 117 */       return (Component)Component.translatable($$4 + ".message", new Object[] { this.mob.getDisplayName(), mutableComponent });
/*     */     } 
/*     */     
/* 120 */     return $$1.getLocalizedDeathMessage(this.mob);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private CombatEntry getMostSignificantFall() {
/* 125 */     CombatEntry $$0 = null;
/* 126 */     CombatEntry $$1 = null;
/* 127 */     float $$2 = 0.0F;
/* 128 */     float $$3 = 0.0F;
/*     */     
/* 130 */     for (int $$4 = 0; $$4 < this.entries.size(); $$4++) {
/* 131 */       CombatEntry $$5 = this.entries.get($$4);
/* 132 */       CombatEntry $$6 = ($$4 > 0) ? this.entries.get($$4 - 1) : null;
/*     */       
/* 134 */       DamageSource $$7 = $$5.source();
/* 135 */       boolean $$8 = $$7.is(DamageTypeTags.ALWAYS_MOST_SIGNIFICANT_FALL);
/* 136 */       float $$9 = $$8 ? Float.MAX_VALUE : $$5.fallDistance();
/* 137 */       if (($$7.is(DamageTypeTags.IS_FALL) || $$8) && $$9 > 0.0F && ($$0 == null || $$9 > $$3)) {
/* 138 */         if ($$4 > 0) {
/* 139 */           $$0 = $$6;
/*     */         } else {
/* 141 */           $$0 = $$5;
/*     */         } 
/* 143 */         $$3 = $$9;
/*     */       } 
/*     */       
/* 146 */       if ($$5.fallLocation() != null && ($$1 == null || $$5.damage() > $$2)) {
/* 147 */         $$1 = $$5;
/* 148 */         $$2 = $$5.damage();
/*     */       } 
/*     */     } 
/*     */     
/* 152 */     if ($$3 > 5.0F && $$0 != null)
/* 153 */       return $$0; 
/* 154 */     if ($$2 > 5.0F && $$1 != null) {
/* 155 */       return $$1;
/*     */     }
/* 157 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCombatDuration() {
/* 162 */     if (this.inCombat) {
/* 163 */       return this.mob.tickCount - this.combatStartTime;
/*     */     }
/* 165 */     return this.combatEndTime - this.combatStartTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public void recheckStatus() {
/* 170 */     int $$0 = this.inCombat ? 300 : 100;
/*     */     
/* 172 */     if (this.takingDamage && (!this.mob.isAlive() || this.mob.tickCount - this.lastDamageTime > $$0)) {
/* 173 */       boolean $$1 = this.inCombat;
/* 174 */       this.takingDamage = false;
/* 175 */       this.inCombat = false;
/* 176 */       this.combatEndTime = this.mob.tickCount;
/*     */       
/* 178 */       if ($$1) {
/* 179 */         this.mob.onLeaveCombat();
/*     */       }
/* 181 */       this.entries.clear();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\damagesource\CombatTracker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */