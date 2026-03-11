/*     */ package net.minecraft.client;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.blaze3d.platform.InputConstants;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.resources.language.I18n;
/*     */ import net.minecraft.network.chat.Component;
/*     */ 
/*     */ public class KeyMapping implements Comparable<KeyMapping> {
/*  15 */   private static final Map<String, KeyMapping> ALL = Maps.newHashMap();
/*  16 */   private static final Map<InputConstants.Key, KeyMapping> MAP = Maps.newHashMap(); public static final String CATEGORY_MOVEMENT = "key.categories.movement"; public static final String CATEGORY_MISC = "key.categories.misc"; public static final String CATEGORY_MULTIPLAYER = "key.categories.multiplayer"; public static final String CATEGORY_GAMEPLAY = "key.categories.gameplay"; public static final String CATEGORY_INVENTORY = "key.categories.inventory"; public static final String CATEGORY_INTERFACE = "key.categories.ui"; public static final String CATEGORY_CREATIVE = "key.categories.creative";
/*  17 */   private static final Set<String> CATEGORIES = Sets.newHashSet();
/*     */   
/*     */   private static final Map<String, Integer> CATEGORY_SORT_ORDER;
/*     */   private final String name;
/*     */   private final InputConstants.Key defaultKey;
/*     */   private final String category;
/*     */   private InputConstants.Key key;
/*     */   private boolean isDown;
/*     */   private int clickCount;
/*     */   
/*     */   static {
/*  28 */     CATEGORY_SORT_ORDER = (Map<String, Integer>)Util.make(Maps.newHashMap(), $$0 -> {
/*     */           $$0.put("key.categories.movement", Integer.valueOf(1));
/*     */           $$0.put("key.categories.gameplay", Integer.valueOf(2));
/*     */           $$0.put("key.categories.inventory", Integer.valueOf(3));
/*     */           $$0.put("key.categories.creative", Integer.valueOf(4));
/*     */           $$0.put("key.categories.multiplayer", Integer.valueOf(5));
/*     */           $$0.put("key.categories.ui", Integer.valueOf(6));
/*     */           $$0.put("key.categories.misc", Integer.valueOf(7));
/*     */         });
/*     */   }
/*     */   public static void click(InputConstants.Key $$0) {
/*  39 */     KeyMapping $$1 = MAP.get($$0);
/*  40 */     if ($$1 != null) {
/*  41 */       $$1.clickCount++;
/*     */     }
/*     */   }
/*     */   
/*     */   public static void set(InputConstants.Key $$0, boolean $$1) {
/*  46 */     KeyMapping $$2 = MAP.get($$0);
/*  47 */     if ($$2 != null) {
/*  48 */       $$2.setDown($$1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setAll() {
/*  54 */     for (KeyMapping $$0 : ALL.values()) {
/*  55 */       if ($$0.key.getType() == InputConstants.Type.KEYSYM && $$0.key.getValue() != InputConstants.UNKNOWN.getValue()) {
/*  56 */         $$0.setDown(InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), $$0.key.getValue()));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void releaseAll() {
/*  62 */     for (KeyMapping $$0 : ALL.values()) {
/*  63 */       $$0.release();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void resetToggleKeys() {
/*  68 */     for (KeyMapping $$0 : ALL.values()) {
/*  69 */       if ($$0 instanceof ToggleKeyMapping) { ToggleKeyMapping $$1 = (ToggleKeyMapping)$$0;
/*  70 */         $$1.reset(); }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void resetMapping() {
/*  76 */     MAP.clear();
/*  77 */     for (KeyMapping $$0 : ALL.values()) {
/*  78 */       MAP.put($$0.key, $$0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyMapping(String $$0, int $$1, String $$2) {
/*  90 */     this($$0, InputConstants.Type.KEYSYM, $$1, $$2);
/*     */   }
/*     */   
/*     */   public KeyMapping(String $$0, InputConstants.Type $$1, int $$2, String $$3) {
/*  94 */     this.name = $$0;
/*  95 */     this.key = $$1.getOrCreate($$2);
/*  96 */     this.defaultKey = this.key;
/*  97 */     this.category = $$3;
/*     */     
/*  99 */     ALL.put($$0, this);
/* 100 */     MAP.put(this.key, this);
/* 101 */     CATEGORIES.add($$3);
/*     */   }
/*     */   
/*     */   public boolean isDown() {
/* 105 */     return this.isDown;
/*     */   }
/*     */   
/*     */   public String getCategory() {
/* 109 */     return this.category;
/*     */   }
/*     */   
/*     */   public boolean consumeClick() {
/* 113 */     if (this.clickCount == 0) {
/* 114 */       return false;
/*     */     }
/* 116 */     this.clickCount--;
/* 117 */     return true;
/*     */   }
/*     */   
/*     */   private void release() {
/* 121 */     this.clickCount = 0;
/* 122 */     setDown(false);
/*     */   }
/*     */   
/*     */   public String getName() {
/* 126 */     return this.name;
/*     */   }
/*     */   
/*     */   public InputConstants.Key getDefaultKey() {
/* 130 */     return this.defaultKey;
/*     */   }
/*     */   
/*     */   public void setKey(InputConstants.Key $$0) {
/* 134 */     this.key = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(KeyMapping $$0) {
/* 139 */     if (this.category.equals($$0.category)) {
/* 140 */       return I18n.get(this.name, new Object[0]).compareTo(I18n.get($$0.name, new Object[0]));
/*     */     }
/* 142 */     return ((Integer)CATEGORY_SORT_ORDER.get(this.category)).compareTo(CATEGORY_SORT_ORDER.get($$0.category));
/*     */   }
/*     */   
/*     */   public static Supplier<Component> createNameSupplier(String $$0) {
/* 146 */     KeyMapping $$1 = ALL.get($$0);
/* 147 */     if ($$1 == null) {
/* 148 */       return () -> Component.translatable($$0);
/*     */     }
/* 150 */     Objects.requireNonNull($$1); return $$1::getTranslatedKeyMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean same(KeyMapping $$0) {
/* 155 */     return this.key.equals($$0.key);
/*     */   }
/*     */   
/*     */   public boolean isUnbound() {
/* 159 */     return this.key.equals(InputConstants.UNKNOWN);
/*     */   }
/*     */   
/*     */   public boolean matches(int $$0, int $$1) {
/* 163 */     if ($$0 == InputConstants.UNKNOWN.getValue()) {
/* 164 */       return (this.key.getType() == InputConstants.Type.SCANCODE && this.key.getValue() == $$1);
/*     */     }
/* 166 */     return (this.key.getType() == InputConstants.Type.KEYSYM && this.key.getValue() == $$0);
/*     */   }
/*     */   
/*     */   public boolean matchesMouse(int $$0) {
/* 170 */     return (this.key.getType() == InputConstants.Type.MOUSE && this.key.getValue() == $$0);
/*     */   }
/*     */   
/*     */   public Component getTranslatedKeyMessage() {
/* 174 */     return this.key.getDisplayName();
/*     */   }
/*     */   
/*     */   public boolean isDefault() {
/* 178 */     return this.key.equals(this.defaultKey);
/*     */   }
/*     */   
/*     */   public String saveString() {
/* 182 */     return this.key.getName();
/*     */   }
/*     */   
/*     */   public void setDown(boolean $$0) {
/* 186 */     this.isDown = $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\KeyMapping.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */