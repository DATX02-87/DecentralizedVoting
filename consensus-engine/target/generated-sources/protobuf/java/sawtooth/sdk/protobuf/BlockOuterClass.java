// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: block.proto

package sawtooth.sdk.protobuf;

public final class BlockOuterClass {
  private BlockOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_BlockHeader_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_BlockHeader_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Block_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Block_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\013block.proto\032\013batch.proto\"\225\001\n\013BlockHead" +
      "er\022\021\n\tblock_num\030\001 \001(\004\022\031\n\021previous_block_" +
      "id\030\002 \001(\t\022\031\n\021signer_public_key\030\003 \001(\t\022\021\n\tb" +
      "atch_ids\030\004 \003(\t\022\021\n\tconsensus\030\005 \001(\014\022\027\n\017sta" +
      "te_root_hash\030\006 \001(\t\"J\n\005Block\022\016\n\006header\030\001 " +
      "\001(\014\022\030\n\020header_signature\030\002 \001(\t\022\027\n\007batches" +
      "\030\003 \003(\0132\006.BatchB$\n\025sawtooth.sdk.protobufP" +
      "\001Z\tblock_pb2b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          sawtooth.sdk.protobuf.BatchOuterClass.getDescriptor(),
        }, assigner);
    internal_static_BlockHeader_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_BlockHeader_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_BlockHeader_descriptor,
        new java.lang.String[] { "BlockNum", "PreviousBlockId", "SignerPublicKey", "BatchIds", "Consensus", "StateRootHash", });
    internal_static_Block_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_Block_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Block_descriptor,
        new java.lang.String[] { "Header", "HeaderSignature", "Batches", });
    sawtooth.sdk.protobuf.BatchOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
