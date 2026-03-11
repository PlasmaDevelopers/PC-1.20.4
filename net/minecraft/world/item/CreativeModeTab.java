/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CreativeModeTab
/*     */ {
/*     */   private final Component displayName;
/*  22 */   String backgroundSuffix = "items.png";
/*     */   boolean canScroll = true;
/*     */   boolean showTitle = true;
/*     */   boolean alignedRight = false;
/*     */   private final Row row;
/*     */   private final int column;
/*     */   private final Type type;
/*     */   @Nullable
/*     */   private ItemStack iconItemStack;
/*  31 */   private Collection<ItemStack> displayItems = ItemStackLinkedSet.createTypeAndTagSet();
/*  32 */   private Set<ItemStack> displayItemsSearchTab = ItemStackLinkedSet.createTypeAndTagSet();
/*     */   @Nullable
/*     */   private Consumer<List<ItemStack>> searchTreeBuilder;
/*     */   private final Supplier<ItemStack> iconGenerator;
/*     */   private final DisplayItemsGenerator displayItemsGenerator;
/*     */   
/*     */   CreativeModeTab(Row $$0, int $$1, Type $$2, Component $$3, Supplier<ItemStack> $$4, DisplayItemsGenerator $$5) {
/*  39 */     this.row = $$0;
/*  40 */     this.column = $$1;
/*  41 */     this.displayName = $$3;
/*  42 */     this.iconGenerator = $$4;
/*  43 */     this.displayItemsGenerator = $$5;
/*  44 */     this.type = $$2;
/*     */   }
/*     */   
/*     */   public static Builder builder(Row $$0, int $$1) {
/*  48 */     return new Builder($$0, $$1);
/*     */   }
/*     */   
/*     */   public Component getDisplayName() {
/*  52 */     return this.displayName;
/*     */   }
/*     */   
/*     */   public ItemStack getIconItem() {
/*  56 */     if (this.iconItemStack == null) {
/*  57 */       this.iconItemStack = this.iconGenerator.get();
/*     */     }
/*  59 */     return this.iconItemStack;
/*     */   }
/*     */   
/*     */   public String getBackgroundSuffix() {
/*  63 */     return this.backgroundSuffix;
/*     */   }
/*     */   
/*     */   public boolean showTitle() {
/*  67 */     return this.showTitle;
/*     */   }
/*     */   
/*     */   public boolean canScroll() {
/*  71 */     return this.canScroll;
/*     */   }
/*     */   
/*     */   public int column() {
/*  75 */     return this.column;
/*     */   }
/*     */   
/*     */   public Row row() {
/*  79 */     return this.row;
/*     */   }
/*     */   
/*     */   public boolean hasAnyItems() {
/*  83 */     return !this.displayItems.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean shouldDisplay() {
/*  87 */     return (this.type != Type.CATEGORY || hasAnyItems());
/*     */   }
/*     */   
/*     */   public boolean isAlignedRight() {
/*  91 */     return this.alignedRight;
/*     */   }
/*     */   
/*     */   public Type getType() {
/*  95 */     return this.type;
/*     */   }
/*     */   
/*     */   public void buildContents(ItemDisplayParameters $$0) {
/*  99 */     ItemDisplayBuilder $$1 = new ItemDisplayBuilder(this, $$0.enabledFeatures);
/* 100 */     ResourceKey<CreativeModeTab> $$2 = (ResourceKey<CreativeModeTab>)BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(this).orElseThrow(() -> new IllegalStateException("Unregistered creative tab: " + this));
/* 101 */     this.displayItemsGenerator.accept($$0, $$1);
/*     */ 
/*     */ 
/*     */     
/* 105 */     this.displayItems = $$1.tabContents;
/* 106 */     this.displayItemsSearchTab = $$1.searchTabContents;
/* 107 */     rebuildSearchTree();
/*     */   }
/*     */   
/*     */   public Collection<ItemStack> getDisplayItems() {
/* 111 */     return this.displayItems;
/*     */   }
/*     */   
/*     */   public Collection<ItemStack> getSearchTabDisplayItems() {
/* 115 */     return this.displayItemsSearchTab;
/*     */   }
/*     */   
/*     */   public boolean contains(ItemStack $$0) {
/* 119 */     return this.displayItemsSearchTab.contains($$0);
/*     */   }
/*     */   
/*     */   public void setSearchTreeBuilder(Consumer<List<ItemStack>> $$0) {
/* 123 */     this.searchTreeBuilder = $$0;
/*     */   }
/*     */   
/*     */   public void rebuildSearchTree() {
/* 127 */     if (this.searchTreeBuilder != null)
/* 128 */       this.searchTreeBuilder.accept(Lists.newArrayList(this.displayItemsSearchTab)); 
/*     */   }
/*     */   public static final class ItemDisplayParameters extends Record { final FeatureFlagSet enabledFeatures; private final boolean hasPermissions; private final HolderLookup.Provider holders;
/*     */     
/* 132 */     public ItemDisplayParameters(FeatureFlagSet $$0, boolean $$1, HolderLookup.Provider $$2) { this.enabledFeatures = $$0; this.hasPermissions = $$1; this.holders = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/item/CreativeModeTab$ItemDisplayParameters;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #132	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 132 */       //   0	7	0	this	Lnet/minecraft/world/item/CreativeModeTab$ItemDisplayParameters; } public FeatureFlagSet enabledFeatures() { return this.enabledFeatures; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/item/CreativeModeTab$ItemDisplayParameters;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #132	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/item/CreativeModeTab$ItemDisplayParameters; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/item/CreativeModeTab$ItemDisplayParameters;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #132	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/item/CreativeModeTab$ItemDisplayParameters;
/* 132 */       //   0	8	1	$$0	Ljava/lang/Object; } public boolean hasPermissions() { return this.hasPermissions; } public HolderLookup.Provider holders() { return this.holders; }
/*     */      public boolean needsUpdate(FeatureFlagSet $$0, boolean $$1, HolderLookup.Provider $$2) {
/* 134 */       return (!this.enabledFeatures.equals($$0) || this.hasPermissions != $$1 || this.holders != $$2);
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Type
/*     */   {
/* 144 */     CATEGORY,
/* 145 */     INVENTORY,
/* 146 */     HOTBAR,
/* 147 */     SEARCH;
/*     */   }
/*     */   
/*     */   public enum Row {
/* 151 */     TOP,
/* 152 */     BOTTOM; }
/*     */   
/*     */   public static class Builder {
/*     */     private static final CreativeModeTab.DisplayItemsGenerator EMPTY_GENERATOR = ($$0, $$1) -> {
/*     */       
/*     */       };
/*     */     private final CreativeModeTab.Row row;
/*     */     private final int column;
/* 160 */     private Component displayName = (Component)Component.empty();
/*     */     private Supplier<ItemStack> iconGenerator = () -> ItemStack.EMPTY;
/* 162 */     private CreativeModeTab.DisplayItemsGenerator displayItemsGenerator = EMPTY_GENERATOR;
/*     */     private boolean canScroll = true;
/*     */     private boolean showTitle = true;
/*     */     private boolean alignedRight = false;
/* 166 */     private CreativeModeTab.Type type = CreativeModeTab.Type.CATEGORY;
/* 167 */     private String backgroundSuffix = "items.png";
/*     */     
/*     */     public Builder(CreativeModeTab.Row $$0, int $$1) {
/* 170 */       this.row = $$0;
/* 171 */       this.column = $$1;
/*     */     }
/*     */     
/*     */     public Builder title(Component $$0) {
/* 175 */       this.displayName = $$0;
/* 176 */       return this;
/*     */     }
/*     */     
/*     */     public Builder icon(Supplier<ItemStack> $$0) {
/* 180 */       this.iconGenerator = $$0;
/* 181 */       return this;
/*     */     }
/*     */     
/*     */     public Builder displayItems(CreativeModeTab.DisplayItemsGenerator $$0) {
/* 185 */       this.displayItemsGenerator = $$0;
/* 186 */       return this;
/*     */     }
/*     */     
/*     */     public Builder alignedRight() {
/* 190 */       this.alignedRight = true;
/* 191 */       return this;
/*     */     }
/*     */     
/*     */     public Builder hideTitle() {
/* 195 */       this.showTitle = false;
/* 196 */       return this;
/*     */     }
/*     */     
/*     */     public Builder noScrollBar() {
/* 200 */       this.canScroll = false;
/* 201 */       return this;
/*     */     }
/*     */     
/*     */     protected Builder type(CreativeModeTab.Type $$0) {
/* 205 */       this.type = $$0;
/* 206 */       return this;
/*     */     }
/*     */     
/*     */     public Builder backgroundSuffix(String $$0) {
/* 210 */       this.backgroundSuffix = $$0;
/* 211 */       return this;
/*     */     }
/*     */     
/*     */     public CreativeModeTab build() {
/* 215 */       if ((this.type == CreativeModeTab.Type.HOTBAR || this.type == CreativeModeTab.Type.INVENTORY) && this.displayItemsGenerator != EMPTY_GENERATOR) {
/* 216 */         throw new IllegalStateException("Special tabs can't have display items");
/*     */       }
/*     */       
/* 219 */       CreativeModeTab $$0 = new CreativeModeTab(this.row, this.column, this.type, this.displayName, this.iconGenerator, this.displayItemsGenerator);
/* 220 */       $$0.alignedRight = this.alignedRight;
/* 221 */       $$0.showTitle = this.showTitle;
/* 222 */       $$0.canScroll = this.canScroll;
/* 223 */       $$0.backgroundSuffix = this.backgroundSuffix;
/* 224 */       return $$0;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ItemDisplayBuilder implements Output {
/* 229 */     public final Collection<ItemStack> tabContents = ItemStackLinkedSet.createTypeAndTagSet();
/* 230 */     public final Set<ItemStack> searchTabContents = ItemStackLinkedSet.createTypeAndTagSet();
/*     */     private final CreativeModeTab tab;
/*     */     private final FeatureFlagSet featureFlagSet;
/*     */     
/*     */     public ItemDisplayBuilder(CreativeModeTab $$0, FeatureFlagSet $$1) {
/* 235 */       this.tab = $$0;
/* 236 */       this.featureFlagSet = $$1;
/*     */     }
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
/*     */ 
/*     */     
/*     */     public void accept(ItemStack $$0, CreativeModeTab.TabVisibility $$1) {
/*     */       // Byte code:
/*     */       //   0: aload_1
/*     */       //   1: invokevirtual getCount : ()I
/*     */       //   4: iconst_1
/*     */       //   5: if_icmpeq -> 18
/*     */       //   8: new java/lang/IllegalArgumentException
/*     */       //   11: dup
/*     */       //   12: ldc 'Stack size must be exactly 1'
/*     */       //   14: invokespecial <init> : (Ljava/lang/String;)V
/*     */       //   17: athrow
/*     */       //   18: aload_0
/*     */       //   19: getfield tabContents : Ljava/util/Collection;
/*     */       //   22: aload_1
/*     */       //   23: invokeinterface contains : (Ljava/lang/Object;)Z
/*     */       //   28: ifeq -> 42
/*     */       //   31: aload_2
/*     */       //   32: getstatic net/minecraft/world/item/CreativeModeTab$TabVisibility.SEARCH_TAB_ONLY : Lnet/minecraft/world/item/CreativeModeTab$TabVisibility;
/*     */       //   35: if_acmpeq -> 42
/*     */       //   38: iconst_1
/*     */       //   39: goto -> 43
/*     */       //   42: iconst_0
/*     */       //   43: istore_3
/*     */       //   44: iload_3
/*     */       //   45: ifeq -> 82
/*     */       //   48: new java/lang/IllegalStateException
/*     */       //   51: dup
/*     */       //   52: aload_1
/*     */       //   53: invokevirtual getDisplayName : ()Lnet/minecraft/network/chat/Component;
/*     */       //   56: invokeinterface getString : ()Ljava/lang/String;
/*     */       //   61: aload_0
/*     */       //   62: getfield tab : Lnet/minecraft/world/item/CreativeModeTab;
/*     */       //   65: invokevirtual getDisplayName : ()Lnet/minecraft/network/chat/Component;
/*     */       //   68: invokeinterface getString : ()Ljava/lang/String;
/*     */       //   73: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
/*     */       //   78: invokespecial <init> : (Ljava/lang/String;)V
/*     */       //   81: athrow
/*     */       //   82: aload_1
/*     */       //   83: invokevirtual getItem : ()Lnet/minecraft/world/item/Item;
/*     */       //   86: aload_0
/*     */       //   87: getfield featureFlagSet : Lnet/minecraft/world/flag/FeatureFlagSet;
/*     */       //   90: invokevirtual isEnabled : (Lnet/minecraft/world/flag/FeatureFlagSet;)Z
/*     */       //   93: ifeq -> 182
/*     */       //   96: getstatic net/minecraft/world/item/CreativeModeTab$1.$SwitchMap$net$minecraft$world$item$CreativeModeTab$TabVisibility : [I
/*     */       //   99: aload_2
/*     */       //   100: invokevirtual ordinal : ()I
/*     */       //   103: iaload
/*     */       //   104: tableswitch default -> 182, 1 -> 132, 2 -> 157, 3 -> 171
/*     */       //   132: aload_0
/*     */       //   133: getfield tabContents : Ljava/util/Collection;
/*     */       //   136: aload_1
/*     */       //   137: invokeinterface add : (Ljava/lang/Object;)Z
/*     */       //   142: pop
/*     */       //   143: aload_0
/*     */       //   144: getfield searchTabContents : Ljava/util/Set;
/*     */       //   147: aload_1
/*     */       //   148: invokeinterface add : (Ljava/lang/Object;)Z
/*     */       //   153: pop
/*     */       //   154: goto -> 182
/*     */       //   157: aload_0
/*     */       //   158: getfield tabContents : Ljava/util/Collection;
/*     */       //   161: aload_1
/*     */       //   162: invokeinterface add : (Ljava/lang/Object;)Z
/*     */       //   167: pop
/*     */       //   168: goto -> 182
/*     */       //   171: aload_0
/*     */       //   172: getfield searchTabContents : Ljava/util/Set;
/*     */       //   175: aload_1
/*     */       //   176: invokeinterface add : (Ljava/lang/Object;)Z
/*     */       //   181: pop
/*     */       //   182: return
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #241	-> 0
/*     */       //   #242	-> 8
/*     */       //   #246	-> 18
/*     */       //   #248	-> 44
/*     */       //   #249	-> 48
/*     */       //   #250	-> 53
/*     */       //   #252	-> 65
/*     */       //   #255	-> 82
/*     */       //   #256	-> 96
/*     */       //   #258	-> 132
/*     */       //   #259	-> 143
/*     */       //   #260	-> 154
/*     */       //   #261	-> 157
/*     */       //   #262	-> 171
/*     */       //   #265	-> 182
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	183	0	this	Lnet/minecraft/world/item/CreativeModeTab$ItemDisplayBuilder;
/*     */       //   0	183	1	$$0	Lnet/minecraft/world/item/ItemStack;
/*     */       //   0	183	2	$$1	Lnet/minecraft/world/item/CreativeModeTab$TabVisibility;
/*     */       //   44	139	3	$$2	Z
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected enum TabVisibility
/*     */   {
/* 269 */     PARENT_AND_SEARCH_TABS,
/* 270 */     PARENT_TAB_ONLY,
/* 271 */     SEARCH_TAB_ONLY;
/*     */   }
/*     */ 
/*     */   
/*     */   public static interface Output
/*     */   {
/*     */     default void accept(ItemStack $$0) {
/* 278 */       accept($$0, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
/*     */     }
/*     */     
/*     */     default void accept(ItemLike $$0, CreativeModeTab.TabVisibility $$1) {
/* 282 */       accept(new ItemStack($$0), $$1);
/*     */     }
/*     */     
/*     */     default void accept(ItemLike $$0) {
/* 286 */       accept(new ItemStack($$0), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
/*     */     }
/*     */     
/*     */     default void acceptAll(Collection<ItemStack> $$0, CreativeModeTab.TabVisibility $$1) {
/* 290 */       $$0.forEach($$1 -> accept($$1, $$0));
/*     */     }
/*     */     
/*     */     default void acceptAll(Collection<ItemStack> $$0) {
/* 294 */       acceptAll($$0, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
/*     */     }
/*     */     
/*     */     void accept(ItemStack param1ItemStack, CreativeModeTab.TabVisibility param1TabVisibility);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface DisplayItemsGenerator {
/*     */     void accept(CreativeModeTab.ItemDisplayParameters param1ItemDisplayParameters, CreativeModeTab.Output param1Output);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\CreativeModeTab.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */