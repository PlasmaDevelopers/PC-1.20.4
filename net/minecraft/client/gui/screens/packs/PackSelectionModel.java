/*     */ package net.minecraft.client.gui.screens.packs;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.OptionInstance;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.repository.Pack;
/*     */ import net.minecraft.server.packs.repository.PackCompatibility;
/*     */ import net.minecraft.server.packs.repository.PackRepository;
/*     */ import net.minecraft.server.packs.repository.PackSource;
/*     */ 
/*     */ 
/*     */ public class PackSelectionModel
/*     */ {
/*     */   private final PackRepository repository;
/*     */   final List<Pack> selected;
/*     */   final List<Pack> unselected;
/*     */   final Function<Pack, ResourceLocation> iconGetter;
/*     */   final Runnable onListChanged;
/*     */   private final Consumer<PackRepository> output;
/*     */   
/*     */   public PackSelectionModel(Runnable $$0, Function<Pack, ResourceLocation> $$1, PackRepository $$2, Consumer<PackRepository> $$3) {
/*  31 */     this.onListChanged = $$0;
/*  32 */     this.iconGetter = $$1;
/*  33 */     this.repository = $$2;
/*  34 */     this.selected = Lists.newArrayList($$2.getSelectedPacks());
/*     */     
/*  36 */     Collections.reverse(this.selected);
/*  37 */     this.unselected = Lists.newArrayList($$2.getAvailablePacks());
/*  38 */     this.unselected.removeAll(this.selected);
/*  39 */     this.output = $$3;
/*     */   }
/*     */   
/*     */   public Stream<Entry> getUnselected() {
/*  43 */     return this.unselected.stream().map($$0 -> new UnselectedPackEntry($$0));
/*     */   }
/*     */   
/*     */   public Stream<Entry> getSelected() {
/*  47 */     return this.selected.stream().map($$0 -> new SelectedPackEntry($$0));
/*     */   }
/*     */   
/*     */   void updateRepoSelectedList() {
/*  51 */     this.repository.setSelected((Collection)Lists.reverse(this.selected).stream().map(Pack::getId).collect(ImmutableList.toImmutableList()));
/*     */   }
/*     */   
/*     */   public void commit() {
/*  55 */     updateRepoSelectedList();
/*  56 */     this.output.accept(this.repository);
/*     */   }
/*     */   
/*     */   public void findNewPacks() {
/*  60 */     this.repository.reload();
/*     */     
/*  62 */     this.selected.retainAll(this.repository.getAvailablePacks());
/*     */     
/*  64 */     this.unselected.clear();
/*  65 */     this.unselected.addAll(this.repository.getAvailablePacks());
/*  66 */     this.unselected.removeAll(this.selected);
/*     */   }
/*     */   
/*     */   public static interface Entry {
/*     */     ResourceLocation getIconTexture();
/*     */     
/*     */     PackCompatibility getCompatibility();
/*     */     
/*     */     String getId();
/*     */     
/*     */     Component getTitle();
/*     */     
/*     */     Component getDescription();
/*     */     
/*     */     PackSource getPackSource();
/*     */     
/*     */     default Component getExtendedDescription() {
/*  83 */       return getPackSource().decorate(getDescription());
/*     */     }
/*     */     
/*     */     boolean isFixedPosition();
/*     */     
/*     */     boolean isRequired();
/*     */     
/*     */     void select();
/*     */     
/*     */     void unselect();
/*     */     
/*     */     void moveUp();
/*     */     
/*     */     void moveDown();
/*     */     
/*     */     boolean isSelected();
/*     */     
/*     */     default boolean canSelect() {
/* 101 */       return !isSelected();
/*     */     }
/*     */     
/*     */     default boolean canUnselect() {
/* 105 */       return (isSelected() && !isRequired());
/*     */     }
/*     */     
/*     */     boolean canMoveUp();
/*     */     
/*     */     boolean canMoveDown();
/*     */   }
/*     */   
/*     */   private abstract class EntryBase implements Entry {
/*     */     private final Pack pack;
/*     */     
/*     */     public EntryBase(Pack $$0) {
/* 117 */       this.pack = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     protected abstract List<Pack> getSelfList();
/*     */     
/*     */     protected abstract List<Pack> getOtherList();
/*     */     
/*     */     public ResourceLocation getIconTexture() {
/* 126 */       return PackSelectionModel.this.iconGetter.apply(this.pack);
/*     */     }
/*     */ 
/*     */     
/*     */     public PackCompatibility getCompatibility() {
/* 131 */       return this.pack.getCompatibility();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getId() {
/* 136 */       return this.pack.getId();
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getTitle() {
/* 141 */       return this.pack.getTitle();
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getDescription() {
/* 146 */       return this.pack.getDescription();
/*     */     }
/*     */ 
/*     */     
/*     */     public PackSource getPackSource() {
/* 151 */       return this.pack.getPackSource();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isFixedPosition() {
/* 156 */       return this.pack.isFixedPosition();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isRequired() {
/* 161 */       return this.pack.isRequired();
/*     */     }
/*     */     
/*     */     protected void toggleSelection() {
/* 165 */       getSelfList().remove(this.pack);
/* 166 */       this.pack.getDefaultPosition().insert(getOtherList(), this.pack, Function.identity(), true);
/* 167 */       PackSelectionModel.this.onListChanged.run();
/* 168 */       PackSelectionModel.this.updateRepoSelectedList();
/* 169 */       updateHighContrastOptionInstance();
/*     */     }
/*     */     
/*     */     private void updateHighContrastOptionInstance() {
/* 173 */       if (this.pack.getId().equals("high_contrast")) {
/* 174 */         OptionInstance<Boolean> $$0 = (Minecraft.getInstance()).options.highContrast();
/* 175 */         $$0.set(Boolean.valueOf(!((Boolean)$$0.get()).booleanValue()));
/*     */       } 
/*     */     }
/*     */     
/*     */     protected void move(int $$0) {
/* 180 */       List<Pack> $$1 = getSelfList();
/* 181 */       int $$2 = $$1.indexOf(this.pack);
/* 182 */       $$1.remove($$2);
/* 183 */       $$1.add($$2 + $$0, this.pack);
/* 184 */       PackSelectionModel.this.onListChanged.run();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canMoveUp() {
/* 189 */       List<Pack> $$0 = getSelfList();
/* 190 */       int $$1 = $$0.indexOf(this.pack);
/* 191 */       return ($$1 > 0 && !((Pack)$$0.get($$1 - 1)).isFixedPosition());
/*     */     }
/*     */ 
/*     */     
/*     */     public void moveUp() {
/* 196 */       move(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canMoveDown() {
/* 201 */       List<Pack> $$0 = getSelfList();
/* 202 */       int $$1 = $$0.indexOf(this.pack);
/* 203 */       return ($$1 >= 0 && $$1 < $$0.size() - 1 && !((Pack)$$0.get($$1 + 1)).isFixedPosition());
/*     */     }
/*     */ 
/*     */     
/*     */     public void moveDown() {
/* 208 */       move(1);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SelectedPackEntry extends EntryBase {
/*     */     public SelectedPackEntry(Pack $$0) {
/* 214 */       super($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<Pack> getSelfList() {
/* 219 */       return PackSelectionModel.this.selected;
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<Pack> getOtherList() {
/* 224 */       return PackSelectionModel.this.unselected;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSelected() {
/* 229 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void select() {}
/*     */ 
/*     */     
/*     */     public void unselect() {
/* 238 */       toggleSelection();
/*     */     }
/*     */   }
/*     */   
/*     */   private class UnselectedPackEntry extends EntryBase {
/*     */     public UnselectedPackEntry(Pack $$0) {
/* 244 */       super($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<Pack> getSelfList() {
/* 249 */       return PackSelectionModel.this.unselected;
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<Pack> getOtherList() {
/* 254 */       return PackSelectionModel.this.selected;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSelected() {
/* 259 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void select() {
/* 264 */       toggleSelection();
/*     */     }
/*     */     
/*     */     public void unselect() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\packs\PackSelectionModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */