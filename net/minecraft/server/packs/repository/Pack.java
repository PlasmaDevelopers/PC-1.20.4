/*     */ package net.minecraft.server.packs.repository;
/*     */ import com.mojang.brigadier.arguments.StringArgumentType;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.List;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.chat.HoverEvent;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.server.packs.FeatureFlagsMetadataSection;
/*     */ import net.minecraft.server.packs.OverlayMetadataSection;
/*     */ import net.minecraft.server.packs.PackResources;
/*     */ import net.minecraft.server.packs.PackType;
/*     */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*     */ import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
/*     */ import net.minecraft.util.InclusiveRange;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class Pack {
/*  24 */   private static final Logger LOGGER = LogUtils.getLogger(); private final String id; private final ResourcesSupplier resources; private final Component title;
/*     */   private final Info info;
/*     */   private final Position defaultPosition;
/*     */   private final boolean required;
/*     */   private final boolean fixedPosition;
/*     */   private final PackSource packSource;
/*     */   
/*     */   public static interface ResourcesSupplier {
/*     */     PackResources openPrimary(String param1String);
/*     */     
/*     */     PackResources openFull(String param1String, Pack.Info param1Info); }
/*     */   
/*     */   public static final class Info extends Record { final Component description;
/*     */     private final PackCompatibility compatibility;
/*     */     private final FeatureFlagSet requestedFeatures;
/*     */     private final List<String> overlays;
/*     */     
/*  41 */     public Info(Component $$0, PackCompatibility $$1, FeatureFlagSet $$2, List<String> $$3) { this.description = $$0; this.compatibility = $$1; this.requestedFeatures = $$2; this.overlays = $$3; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/packs/repository/Pack$Info;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #41	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  41 */       //   0	7	0	this	Lnet/minecraft/server/packs/repository/Pack$Info; } public Component description() { return this.description; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/packs/repository/Pack$Info;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #41	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/packs/repository/Pack$Info; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/packs/repository/Pack$Info;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #41	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/packs/repository/Pack$Info;
/*  41 */       //   0	8	1	$$0	Ljava/lang/Object; } public PackCompatibility compatibility() { return this.compatibility; } public FeatureFlagSet requestedFeatures() { return this.requestedFeatures; } public List<String> overlays() { return this.overlays; }
/*     */      }
/*     */   
/*     */   @Nullable
/*     */   public static Pack readMetaAndCreate(String $$0, Component $$1, boolean $$2, ResourcesSupplier $$3, PackType $$4, Position $$5, PackSource $$6) {
/*  46 */     int $$7 = SharedConstants.getCurrentVersion().getPackVersion($$4);
/*  47 */     Info $$8 = readPackInfo($$0, $$3, $$7);
/*  48 */     return ($$8 != null) ? create($$0, $$1, $$2, $$3, $$8, $$5, false, $$6) : null;
/*     */   }
/*     */   
/*     */   public static Pack create(String $$0, Component $$1, boolean $$2, ResourcesSupplier $$3, Info $$4, Position $$5, boolean $$6, PackSource $$7) {
/*  52 */     return new Pack($$0, $$2, $$3, $$1, $$4, $$5, $$6, $$7);
/*     */   }
/*     */   
/*     */   private Pack(String $$0, boolean $$1, ResourcesSupplier $$2, Component $$3, Info $$4, Position $$5, boolean $$6, PackSource $$7) {
/*  56 */     this.id = $$0;
/*  57 */     this.resources = $$2;
/*  58 */     this.title = $$3;
/*  59 */     this.info = $$4;
/*  60 */     this.required = $$1;
/*  61 */     this.defaultPosition = $$5;
/*  62 */     this.fixedPosition = $$6;
/*  63 */     this.packSource = $$7;
/*     */   }
/*     */   @Nullable
/*     */   public static Info readPackInfo(String $$0, ResourcesSupplier $$1, int $$2) {
/*     */     
/*  68 */     try { PackResources $$3 = $$1.openPrimary($$0); 
/*  69 */       try { PackMetadataSection $$4 = (PackMetadataSection)$$3.getMetadataSection((MetadataSectionSerializer)PackMetadataSection.TYPE);
/*  70 */         if ($$4 == null)
/*  71 */         { LOGGER.warn("Missing metadata in pack {}", $$0);
/*  72 */           Info info1 = null;
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
/*  85 */           if ($$3 != null) $$3.close();  return info1; }  FeatureFlagsMetadataSection $$5 = (FeatureFlagsMetadataSection)$$3.getMetadataSection((MetadataSectionSerializer)FeatureFlagsMetadataSection.TYPE); FeatureFlagSet $$6 = ($$5 != null) ? $$5.flags() : FeatureFlagSet.of(); InclusiveRange<Integer> $$7 = getDeclaredPackVersions($$0, $$4); PackCompatibility $$8 = PackCompatibility.forVersion($$7, $$2); OverlayMetadataSection $$9 = (OverlayMetadataSection)$$3.getMetadataSection((MetadataSectionSerializer)OverlayMetadataSection.TYPE); List<String> $$10 = ($$9 != null) ? $$9.overlaysForVersion($$2) : List.<String>of(); Info info = new Info($$4.description(), $$8, $$6, $$10); if ($$3 != null) $$3.close();  return info; } catch (Throwable throwable) { if ($$3 != null) try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception $$11)
/*  86 */     { LOGGER.warn("Failed to read pack {} metadata", $$0, $$11);
/*     */       
/*  88 */       return null; }
/*     */   
/*     */   }
/*     */   private static InclusiveRange<Integer> getDeclaredPackVersions(String $$0, PackMetadataSection $$1) {
/*  92 */     int $$2 = $$1.packFormat();
/*  93 */     if ($$1.supportedFormats().isEmpty()) {
/*  94 */       return new InclusiveRange(Integer.valueOf($$2));
/*     */     }
/*     */     
/*  97 */     InclusiveRange<Integer> $$3 = $$1.supportedFormats().get();
/*  98 */     if (!$$3.isValueInRange(Integer.valueOf($$2))) {
/*  99 */       LOGGER.warn("Pack {} declared support for versions {} but declared main format is {}, defaulting to {}", new Object[] { $$0, $$3, Integer.valueOf($$2), Integer.valueOf($$2) });
/* 100 */       return new InclusiveRange(Integer.valueOf($$2));
/*     */     } 
/*     */     
/* 103 */     return $$3;
/*     */   }
/*     */   
/*     */   public Component getTitle() {
/* 107 */     return this.title;
/*     */   }
/*     */   
/*     */   public Component getDescription() {
/* 111 */     return this.info.description();
/*     */   }
/*     */   
/*     */   public Component getChatLink(boolean $$0) {
/* 115 */     return (Component)ComponentUtils.wrapInSquareBrackets(this.packSource.decorate((Component)Component.literal(this.id))).withStyle($$1 -> $$1.withColor($$0 ? ChatFormatting.GREEN : ChatFormatting.RED).withInsertion(StringArgumentType.escapeIfRequired(this.id)).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.empty().append(this.title).append("\n").append(this.info.description))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PackCompatibility getCompatibility() {
/* 123 */     return this.info.compatibility();
/*     */   }
/*     */   
/*     */   public FeatureFlagSet getRequestedFeatures() {
/* 127 */     return this.info.requestedFeatures();
/*     */   }
/*     */   
/*     */   public PackResources open() {
/* 131 */     return this.resources.openFull(this.id, this.info);
/*     */   }
/*     */   
/*     */   public String getId() {
/* 135 */     return this.id;
/*     */   }
/*     */   
/*     */   public boolean isRequired() {
/* 139 */     return this.required;
/*     */   }
/*     */   
/*     */   public boolean isFixedPosition() {
/* 143 */     return this.fixedPosition;
/*     */   }
/*     */   
/*     */   public Position getDefaultPosition() {
/* 147 */     return this.defaultPosition;
/*     */   }
/*     */   
/*     */   public PackSource getPackSource() {
/* 151 */     return this.packSource;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 156 */     if (this == $$0) {
/* 157 */       return true;
/*     */     }
/* 159 */     if (!($$0 instanceof Pack)) {
/* 160 */       return false;
/*     */     }
/*     */     
/* 163 */     Pack $$1 = (Pack)$$0;
/*     */     
/* 165 */     return this.id.equals($$1.id);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 170 */     return this.id.hashCode();
/*     */   }
/*     */   
/*     */   public enum Position {
/* 174 */     TOP,
/* 175 */     BOTTOM;
/*     */ 
/*     */     
/*     */     public <T> int insert(List<T> $$0, T $$1, Function<T, Pack> $$2, boolean $$3) {
/* 179 */       Position $$4 = $$3 ? opposite() : this;
/* 180 */       if ($$4 == BOTTOM) {
/* 181 */         int $$5 = 0;
/* 182 */         while ($$5 < $$0.size()) {
/* 183 */           Pack $$6 = $$2.apply($$0.get($$5));
/* 184 */           if ($$6.isFixedPosition() && $$6.getDefaultPosition() == this) {
/* 185 */             $$5++;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 190 */         $$0.add($$5, $$1);
/* 191 */         return $$5;
/*     */       } 
/* 193 */       int $$7 = $$0.size() - 1;
/* 194 */       while ($$7 >= 0) {
/* 195 */         Pack $$8 = $$2.apply($$0.get($$7));
/* 196 */         if ($$8.isFixedPosition() && $$8.getDefaultPosition() == this) {
/* 197 */           $$7--;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 202 */       $$0.add($$7 + 1, $$1);
/* 203 */       return $$7 + 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public Position opposite() {
/* 208 */       return (this == TOP) ? BOTTOM : TOP;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\repository\Pack.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */