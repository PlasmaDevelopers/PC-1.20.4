/*     */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.DataFixer;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.InvalidPathException;
/*     */ import java.nio.file.NoSuchFileException;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.FileUtil;
/*     */ import net.minecraft.ResourceLocationException;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.gametest.framework.StructureUtils;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtAccounter;
/*     */ import net.minecraft.nbt.NbtIo;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.resources.FileToIdConverter;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.util.datafix.DataFixTypes;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.storage.LevelResource;
/*     */ import net.minecraft.world.level.storage.LevelStorageSource;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ public class StructureTemplateManager
/*     */ {
/*  51 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final String STRUCTURE_DIRECTORY_NAME = "structures";
/*     */   
/*     */   private static final String STRUCTURE_FILE_EXTENSION = ".nbt";
/*     */   private static final String STRUCTURE_TEXT_FILE_EXTENSION = ".snbt";
/*  57 */   private final Map<ResourceLocation, Optional<StructureTemplate>> structureRepository = Maps.newConcurrentMap();
/*     */   
/*     */   private final DataFixer fixerUpper;
/*     */   private ResourceManager resourceManager;
/*     */   private final Path generatedDir;
/*     */   private final List<Source> sources;
/*     */   private final HolderGetter<Block> blockLookup;
/*  64 */   private static final FileToIdConverter LISTER = new FileToIdConverter("structures", ".nbt");
/*     */   private static final class Source extends Record { private final Function<ResourceLocation, Optional<StructureTemplate>> loader; private final Supplier<Stream<ResourceLocation>> lister;
/*  66 */     Source(Function<ResourceLocation, Optional<StructureTemplate>> $$0, Supplier<Stream<ResourceLocation>> $$1) { this.loader = $$0; this.lister = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager$Source;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #66	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  66 */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager$Source; } public Function<ResourceLocation, Optional<StructureTemplate>> loader() { return this.loader; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager$Source;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #66	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager$Source; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager$Source;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #66	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager$Source;
/*  66 */       //   0	8	1	$$0	Ljava/lang/Object; } public Supplier<Stream<ResourceLocation>> lister() { return this.lister; }
/*     */      }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StructureTemplateManager(ResourceManager $$0, LevelStorageSource.LevelStorageAccess $$1, DataFixer $$2, HolderGetter<Block> $$3) {
/*  73 */     this.resourceManager = $$0;
/*  74 */     this.fixerUpper = $$2;
/*  75 */     this.generatedDir = $$1.getLevelPath(LevelResource.GENERATED_DIR).normalize();
/*  76 */     this.blockLookup = $$3;
/*  77 */     ImmutableList.Builder<Source> $$4 = ImmutableList.builder();
/*  78 */     $$4.add(new Source(this::loadFromGenerated, this::listGenerated));
/*  79 */     if (SharedConstants.IS_RUNNING_IN_IDE) {
/*  80 */       $$4.add(new Source(this::loadFromTestStructures, this::listTestStructures));
/*     */     }
/*  82 */     $$4.add(new Source(this::loadFromResource, this::listResources));
/*  83 */     this.sources = (List<Source>)$$4.build();
/*     */   }
/*     */   
/*     */   public StructureTemplate getOrCreate(ResourceLocation $$0) {
/*  87 */     Optional<StructureTemplate> $$1 = get($$0);
/*     */     
/*  89 */     if ($$1.isPresent()) {
/*  90 */       return $$1.get();
/*     */     }
/*     */     
/*  93 */     StructureTemplate $$2 = new StructureTemplate();
/*  94 */     this.structureRepository.put($$0, Optional.of($$2));
/*  95 */     return $$2;
/*     */   }
/*     */   
/*     */   public Optional<StructureTemplate> get(ResourceLocation $$0) {
/*  99 */     return this.structureRepository.computeIfAbsent($$0, this::tryLoad);
/*     */   }
/*     */   
/*     */   public Stream<ResourceLocation> listTemplates() {
/* 103 */     return this.sources.stream().flatMap($$0 -> (Stream)$$0.lister().get()).distinct();
/*     */   }
/*     */   
/*     */   private Optional<StructureTemplate> tryLoad(ResourceLocation $$0) {
/* 107 */     for (Source $$1 : this.sources) {
/*     */       try {
/* 109 */         Optional<StructureTemplate> $$2 = $$1.loader().apply($$0);
/* 110 */         if ($$2.isPresent()) {
/* 111 */           return $$2;
/*     */         }
/* 113 */       } catch (Exception exception) {}
/*     */     } 
/*     */     
/* 116 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   public void onResourceManagerReload(ResourceManager $$0) {
/* 120 */     this.resourceManager = $$0;
/* 121 */     this.structureRepository.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Optional<StructureTemplate> loadFromResource(ResourceLocation $$0) {
/* 130 */     ResourceLocation $$1 = LISTER.idToFile($$0);
/* 131 */     return load(() -> this.resourceManager.open($$0), $$1 -> LOGGER.error("Couldn't load structure {}", $$0, $$1));
/*     */   }
/*     */   
/*     */   private Stream<ResourceLocation> listResources() {
/* 135 */     Objects.requireNonNull(LISTER); return LISTER.listMatchingResources(this.resourceManager).keySet().stream().map(LISTER::fileToId);
/*     */   }
/*     */   
/*     */   private Optional<StructureTemplate> loadFromTestStructures(ResourceLocation $$0) {
/* 139 */     return loadFromSnbt($$0, Paths.get(StructureUtils.testStructuresDir, new String[0]));
/*     */   }
/*     */   
/*     */   private Stream<ResourceLocation> listTestStructures() {
/* 143 */     return listFolderContents(Paths.get(StructureUtils.testStructuresDir, new String[0]), "minecraft", ".snbt");
/*     */   }
/*     */   
/*     */   private Optional<StructureTemplate> loadFromGenerated(ResourceLocation $$0) {
/* 147 */     if (!Files.isDirectory(this.generatedDir, new java.nio.file.LinkOption[0])) {
/* 148 */       return Optional.empty();
/*     */     }
/*     */     
/* 151 */     Path $$1 = createAndValidatePathToStructure(this.generatedDir, $$0, ".nbt");
/* 152 */     return load(() -> new FileInputStream($$0.toFile()), $$1 -> LOGGER.error("Couldn't load structure from {}", $$0, $$1));
/*     */   }
/*     */   
/*     */   private Stream<ResourceLocation> listGenerated() {
/* 156 */     if (!Files.isDirectory(this.generatedDir, new java.nio.file.LinkOption[0])) {
/* 157 */       return Stream.empty();
/*     */     }
/*     */     
/*     */     try {
/* 161 */       return Files.list(this.generatedDir).filter($$0 -> Files.isDirectory($$0, new java.nio.file.LinkOption[0])).flatMap($$0 -> listGeneratedInNamespace($$0));
/* 162 */     } catch (IOException $$0) {
/* 163 */       return Stream.empty();
/*     */     } 
/*     */   }
/*     */   
/*     */   private Stream<ResourceLocation> listGeneratedInNamespace(Path $$0) {
/* 168 */     Path $$1 = $$0.resolve("structures");
/* 169 */     return listFolderContents($$1, $$0.getFileName().toString(), ".nbt");
/*     */   }
/*     */   
/*     */   private Stream<ResourceLocation> listFolderContents(Path $$0, String $$1, String $$2) {
/* 173 */     if (!Files.isDirectory($$0, new java.nio.file.LinkOption[0])) {
/* 174 */       return Stream.empty();
/*     */     }
/*     */     
/* 177 */     int $$3 = $$2.length();
/* 178 */     Function<String, String> $$4 = $$1 -> $$1.substring(0, $$1.length() - $$0);
/*     */     
/*     */     try {
/* 181 */       return Files.walk($$0, new java.nio.file.FileVisitOption[0]).filter($$1 -> $$1.toString().endsWith($$0))
/* 182 */         .mapMulti(($$3, $$4) -> {
/*     */             try {
/*     */               $$4.accept(new ResourceLocation($$0, $$1.apply(relativize($$2, $$3))));
/* 185 */             } catch (ResourceLocationException $$5) {
/*     */               LOGGER.error("Invalid location while listing pack contents", (Throwable)$$5);
/*     */             } 
/*     */           });
/* 189 */     } catch (IOException $$5) {
/* 190 */       LOGGER.error("Failed to list folder contents", $$5);
/* 191 */       return Stream.empty();
/*     */     } 
/*     */   }
/*     */   
/*     */   private String relativize(Path $$0, Path $$1) {
/* 196 */     return $$0.relativize($$1).toString().replace(File.separator, "/");
/*     */   }
/*     */   
/*     */   private Optional<StructureTemplate> loadFromSnbt(ResourceLocation $$0, Path $$1) {
/* 200 */     if (!Files.isDirectory($$1, new java.nio.file.LinkOption[0])) {
/* 201 */       return Optional.empty();
/*     */     }
/*     */     
/* 204 */     Path $$2 = FileUtil.createPathToResource($$1, $$0.getPath(), ".snbt"); 
/* 205 */     try { BufferedReader $$3 = Files.newBufferedReader($$2); 
/* 206 */       try { String $$4 = IOUtils.toString($$3);
/* 207 */         Optional<StructureTemplate> optional = Optional.of(readStructure(NbtUtils.snbtToStructure($$4)));
/* 208 */         if ($$3 != null) $$3.close();  return optional; } catch (Throwable throwable) { if ($$3 != null) try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (NoSuchFileException $$5)
/* 209 */     { return Optional.empty(); }
/* 210 */     catch (IOException|com.mojang.brigadier.exceptions.CommandSyntaxException $$6)
/* 211 */     { LOGGER.error("Couldn't load structure from {}", $$2, $$6);
/* 212 */       return Optional.empty(); }
/*     */   
/*     */   }
/*     */   private Optional<StructureTemplate> load(InputStreamOpener $$0, Consumer<Throwable> $$1) {
/*     */     
/* 217 */     try { InputStream $$2 = $$0.open(); 
/* 218 */       try { Optional<StructureTemplate> optional = Optional.of(readStructure($$2));
/* 219 */         if ($$2 != null) $$2.close();  return optional; } catch (Throwable throwable) { if ($$2 != null) try { $$2.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (FileNotFoundException $$3)
/* 220 */     { return Optional.empty(); }
/* 221 */     catch (Throwable $$4)
/* 222 */     { $$1.accept($$4);
/* 223 */       return Optional.empty(); }
/*     */   
/*     */   }
/*     */   
/*     */   private StructureTemplate readStructure(InputStream $$0) throws IOException {
/* 228 */     CompoundTag $$1 = NbtIo.readCompressed($$0, NbtAccounter.unlimitedHeap());
/* 229 */     return readStructure($$1);
/*     */   }
/*     */   
/*     */   public StructureTemplate readStructure(CompoundTag $$0) {
/* 233 */     StructureTemplate $$1 = new StructureTemplate();
/*     */     
/* 235 */     int $$2 = NbtUtils.getDataVersion($$0, 500);
/* 236 */     $$1.load(this.blockLookup, DataFixTypes.STRUCTURE.updateToCurrentVersion(this.fixerUpper, $$0, $$2));
/* 237 */     return $$1;
/*     */   }
/*     */   
/*     */   public boolean save(ResourceLocation $$0) {
/* 241 */     Optional<StructureTemplate> $$1 = this.structureRepository.get($$0);
/* 242 */     if ($$1.isEmpty()) {
/* 243 */       return false;
/*     */     }
/*     */     
/* 246 */     StructureTemplate $$2 = $$1.get();
/*     */     
/* 248 */     Path $$3 = createAndValidatePathToStructure(this.generatedDir, $$0, ".nbt");
/*     */     
/* 250 */     Path $$4 = $$3.getParent();
/* 251 */     if ($$4 == null) {
/* 252 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 256 */       Files.createDirectories(Files.exists($$4, new java.nio.file.LinkOption[0]) ? $$4.toRealPath(new java.nio.file.LinkOption[0]) : $$4, (FileAttribute<?>[])new FileAttribute[0]);
/* 257 */     } catch (IOException $$5) {
/* 258 */       LOGGER.error("Failed to create parent directory: {}", $$4);
/* 259 */       return false;
/*     */     } 
/*     */     
/* 262 */     CompoundTag $$6 = $$2.save(new CompoundTag());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 270 */     try { OutputStream $$7 = new FileOutputStream($$3.toFile()); 
/* 271 */       try { NbtIo.writeCompressed($$6, $$7);
/* 272 */         $$7.close(); } catch (Throwable throwable) { try { $$7.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable $$8)
/* 273 */     { return false; }
/*     */ 
/*     */     
/* 276 */     return true;
/*     */   }
/*     */   
/*     */   public Path getPathToGeneratedStructure(ResourceLocation $$0, String $$1) {
/* 280 */     return createPathToStructure(this.generatedDir, $$0, $$1);
/*     */   }
/*     */   
/*     */   public static Path createPathToStructure(Path $$0, ResourceLocation $$1, String $$2) {
/*     */     try {
/* 285 */       Path $$3 = $$0.resolve($$1.getNamespace());
/* 286 */       Path $$4 = $$3.resolve("structures");
/* 287 */       return FileUtil.createPathToResource($$4, $$1.getPath(), $$2);
/* 288 */     } catch (InvalidPathException $$5) {
/* 289 */       throw new ResourceLocationException("Invalid resource path: " + $$1, $$5);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Path createAndValidatePathToStructure(Path $$0, ResourceLocation $$1, String $$2) {
/* 294 */     if ($$1.getPath().contains("//")) {
/* 295 */       throw new ResourceLocationException("Invalid resource path: " + $$1);
/*     */     }
/*     */     
/* 298 */     Path $$3 = createPathToStructure($$0, $$1, $$2);
/*     */     
/* 300 */     if (!$$3.startsWith($$0) || !FileUtil.isPathNormalized($$3) || !FileUtil.isPathPortable($$3)) {
/* 301 */       throw new ResourceLocationException("Invalid resource path: " + $$3);
/*     */     }
/*     */     
/* 304 */     return $$3;
/*     */   }
/*     */   
/*     */   public void remove(ResourceLocation $$0) {
/* 308 */     this.structureRepository.remove($$0);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface InputStreamOpener {
/*     */     InputStream open() throws IOException;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\StructureTemplateManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */