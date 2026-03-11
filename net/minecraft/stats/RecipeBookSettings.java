/*     */ package net.minecraft.stats;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.EnumMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.world.inventory.RecipeBookType;
/*     */ 
/*     */ public final class RecipeBookSettings {
/*  14 */   private static final Map<RecipeBookType, Pair<String, String>> TAG_FIELDS = (Map<RecipeBookType, Pair<String, String>>)ImmutableMap.of(RecipeBookType.CRAFTING, 
/*  15 */       Pair.of("isGuiOpen", "isFilteringCraftable"), RecipeBookType.FURNACE, 
/*  16 */       Pair.of("isFurnaceGuiOpen", "isFurnaceFilteringCraftable"), RecipeBookType.BLAST_FURNACE, 
/*  17 */       Pair.of("isBlastingFurnaceGuiOpen", "isBlastingFurnaceFilteringCraftable"), RecipeBookType.SMOKER, 
/*  18 */       Pair.of("isSmokerGuiOpen", "isSmokerFilteringCraftable"));
/*     */   private final Map<RecipeBookType, TypeSettings> states;
/*     */   
/*     */   private static final class TypeSettings {
/*     */     boolean open;
/*     */     boolean filtering;
/*     */     
/*     */     public TypeSettings(boolean $$0, boolean $$1) {
/*  26 */       this.open = $$0;
/*  27 */       this.filtering = $$1;
/*     */     }
/*     */     
/*     */     public TypeSettings copy() {
/*  31 */       return new TypeSettings(this.open, this.filtering);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object $$0) {
/*  36 */       if (this == $$0) {
/*  37 */         return true;
/*     */       }
/*     */       
/*  40 */       if ($$0 instanceof TypeSettings) { TypeSettings $$1 = (TypeSettings)$$0;
/*  41 */         return (this.open == $$1.open && this.filtering == $$1.filtering); }
/*     */       
/*  43 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  48 */       int $$0 = this.open ? 1 : 0;
/*  49 */       $$0 = 31 * $$0 + (this.filtering ? 1 : 0);
/*  50 */       return $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  55 */       return "[open=" + this.open + ", filtering=" + this.filtering + "]";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private RecipeBookSettings(Map<RecipeBookType, TypeSettings> $$0) {
/*  62 */     this.states = $$0;
/*     */   }
/*     */   
/*     */   public RecipeBookSettings() {
/*  66 */     this((Map<RecipeBookType, TypeSettings>)Util.make(Maps.newEnumMap(RecipeBookType.class), $$0 -> {
/*     */             for (RecipeBookType $$1 : RecipeBookType.values()) {
/*     */               $$0.put($$1, new TypeSettings(false, false));
/*     */             }
/*     */           }));
/*     */   }
/*     */   
/*     */   public boolean isOpen(RecipeBookType $$0) {
/*  74 */     return ((TypeSettings)this.states.get($$0)).open;
/*     */   }
/*     */   
/*     */   public void setOpen(RecipeBookType $$0, boolean $$1) {
/*  78 */     ((TypeSettings)this.states.get($$0)).open = $$1;
/*     */   }
/*     */   
/*     */   public boolean isFiltering(RecipeBookType $$0) {
/*  82 */     return ((TypeSettings)this.states.get($$0)).filtering;
/*     */   }
/*     */   
/*     */   public void setFiltering(RecipeBookType $$0, boolean $$1) {
/*  86 */     ((TypeSettings)this.states.get($$0)).filtering = $$1;
/*     */   }
/*     */   
/*     */   public static RecipeBookSettings read(FriendlyByteBuf $$0) {
/*  90 */     Map<RecipeBookType, TypeSettings> $$1 = Maps.newEnumMap(RecipeBookType.class);
/*  91 */     for (RecipeBookType $$2 : RecipeBookType.values()) {
/*  92 */       boolean $$3 = $$0.readBoolean();
/*  93 */       boolean $$4 = $$0.readBoolean();
/*  94 */       $$1.put($$2, new TypeSettings($$3, $$4));
/*     */     } 
/*  96 */     return new RecipeBookSettings($$1);
/*     */   }
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/* 100 */     for (RecipeBookType $$1 : RecipeBookType.values()) {
/* 101 */       TypeSettings $$2 = this.states.get($$1);
/* 102 */       if ($$2 == null) {
/* 103 */         $$0.writeBoolean(false);
/* 104 */         $$0.writeBoolean(false);
/*     */       } else {
/* 106 */         $$0.writeBoolean($$2.open);
/* 107 */         $$0.writeBoolean($$2.filtering);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static RecipeBookSettings read(CompoundTag $$0) {
/* 113 */     Map<RecipeBookType, TypeSettings> $$1 = Maps.newEnumMap(RecipeBookType.class);
/* 114 */     TAG_FIELDS.forEach(($$2, $$3) -> {
/*     */           boolean $$4 = $$0.getBoolean((String)$$3.getFirst());
/*     */           boolean $$5 = $$0.getBoolean((String)$$3.getSecond());
/*     */           $$1.put($$2, new TypeSettings($$4, $$5));
/*     */         });
/* 119 */     return new RecipeBookSettings($$1);
/*     */   }
/*     */   
/*     */   public void write(CompoundTag $$0) {
/* 123 */     TAG_FIELDS.forEach(($$1, $$2) -> {
/*     */           TypeSettings $$3 = this.states.get($$1);
/*     */           $$0.putBoolean((String)$$2.getFirst(), $$3.open);
/*     */           $$0.putBoolean((String)$$2.getSecond(), $$3.filtering);
/*     */         });
/*     */   }
/*     */   
/*     */   public RecipeBookSettings copy() {
/* 131 */     Map<RecipeBookType, TypeSettings> $$0 = Maps.newEnumMap(RecipeBookType.class);
/* 132 */     for (RecipeBookType $$1 : RecipeBookType.values()) {
/* 133 */       TypeSettings $$2 = this.states.get($$1);
/* 134 */       $$0.put($$1, $$2.copy());
/*     */     } 
/* 136 */     return new RecipeBookSettings($$0);
/*     */   }
/*     */   
/*     */   public void replaceFrom(RecipeBookSettings $$0) {
/* 140 */     this.states.clear();
/* 141 */     for (RecipeBookType $$1 : RecipeBookType.values()) {
/* 142 */       TypeSettings $$2 = $$0.states.get($$1);
/* 143 */       this.states.put($$1, $$2.copy());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 149 */     return (this == $$0 || ($$0 instanceof RecipeBookSettings && this.states.equals(((RecipeBookSettings)$$0).states)));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 154 */     return this.states.hashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\stats\RecipeBookSettings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */