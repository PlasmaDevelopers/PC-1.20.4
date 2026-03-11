/*    */ package net.minecraft.world.level.chunk.storage;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import java.io.BufferedOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.zip.DeflaterOutputStream;
/*    */ import java.util.zip.GZIPInputStream;
/*    */ import java.util.zip.GZIPOutputStream;
/*    */ import java.util.zip.InflaterInputStream;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.FastBufferedInputStream;
/*    */ 
/*    */ public class RegionFileVersion
/*    */ {
/* 18 */   private static final Int2ObjectMap<RegionFileVersion> VERSIONS = (Int2ObjectMap<RegionFileVersion>)new Int2ObjectOpenHashMap();
/*    */   static {
/* 20 */     VERSION_GZIP = register(new RegionFileVersion(1, $$0 -> new FastBufferedInputStream(new GZIPInputStream($$0)), $$0 -> new BufferedOutputStream(new GZIPOutputStream($$0))));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 25 */     VERSION_DEFLATE = register(new RegionFileVersion(2, $$0 -> new FastBufferedInputStream(new InflaterInputStream($$0)), $$0 -> new BufferedOutputStream(new DeflaterOutputStream($$0))));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 30 */     VERSION_NONE = register(new RegionFileVersion(3, $$0 -> $$0, $$0 -> $$0));
/*    */   }
/*    */   public static final RegionFileVersion VERSION_GZIP;
/*    */   public static final RegionFileVersion VERSION_DEFLATE;
/*    */   public static final RegionFileVersion VERSION_NONE;
/*    */   private final int id;
/*    */   private final StreamWrapper<InputStream> inputWrapper;
/*    */   private final StreamWrapper<OutputStream> outputWrapper;
/*    */   
/*    */   private RegionFileVersion(int $$0, StreamWrapper<InputStream> $$1, StreamWrapper<OutputStream> $$2) {
/* 40 */     this.id = $$0;
/* 41 */     this.inputWrapper = $$1;
/* 42 */     this.outputWrapper = $$2;
/*    */   }
/*    */   
/*    */   private static RegionFileVersion register(RegionFileVersion $$0) {
/* 46 */     VERSIONS.put($$0.id, $$0);
/* 47 */     return $$0;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static RegionFileVersion fromId(int $$0) {
/* 52 */     return (RegionFileVersion)VERSIONS.get($$0);
/*    */   }
/*    */   
/*    */   public static boolean isValidVersion(int $$0) {
/* 56 */     return VERSIONS.containsKey($$0);
/*    */   }
/*    */   
/*    */   public int getId() {
/* 60 */     return this.id;
/*    */   }
/*    */   
/*    */   public OutputStream wrap(OutputStream $$0) throws IOException {
/* 64 */     return this.outputWrapper.wrap($$0);
/*    */   }
/*    */   
/*    */   public InputStream wrap(InputStream $$0) throws IOException {
/* 68 */     return this.inputWrapper.wrap($$0);
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   private static interface StreamWrapper<O> {
/*    */     O wrap(O param1O) throws IOException;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\storage\RegionFileVersion.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */